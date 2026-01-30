import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.SplittableRandom;
import java.util.concurrent.atomic.AtomicInteger;

// Ein Worker-Prozess, der einen Teil des Wertebereichs für den BeesAlgorithm abarbeitet.
public class BAProcess {

    // ---------- PIPELINE METHODEN ----------

    // Liest übergebene BAParameter ein
    private static BAParameter readBAParams() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(System.in)) {
            return (BAParameter) ois.readObject();
        } // try schließt danach die Pipeline automatisch
    }

    // Sendet Ergebnisliste des Algorithmus
    private static void sendResult(CustomList result) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(System.out)) {
            oos.writeObject(result);
            oos.flush();
        } // try schließt danach die Pipeline automatisch
    }

    // ---------- LOG für DEBUG ----------

    // Schreibt die Log-Nachrichten nach Std-err = dedizierte .err Datei des Prozesses
//    private static void log(String message) {
//        System.err.println(message);
//    }

    // ---------- SUCHEN FÜR BEES ALGORITHM --------

    // Führt eine globale Suche aus
    private static void globalSearch(SerializableFunction<double[],Double> f, double[][] w, int b, CustomList results, SplittableRandom rnd) {
        // Liste für Thread-eigene Ergebnisse
        CustomList searchResult = new CustomList();

        for (int i = 0; i < b; i++) {
            // Ergebnis berechnen
            double[] args = new double[w.length];
            for (int d = 0; d < w.length; d++) {
                double lo = w[d][0], hi = w[d][1];
                args[d] = lo + (hi - lo) * rnd.nextDouble();
            }
            double val = f.apply(args);

            // Ergebnis speichern
            searchResult.add(new Stelle(args, val));
        }
        results.addAll(searchResult);
    }

    // Führt lokale Suche aus
    private static void localSearch(SerializableFunction<double[],Double> f, double[][] w, double s, Comparator<Stelle> c, Stelle stelle, int b,
                                    Stelle[] bestResults, int idx, SplittableRandom rnd){

        //String thrName = Thread.currentThread().getName(); // für Identifikation Thread in Log // LOG für DEBUG/TEST anschalten

        // Lokale Feldgrenzen berechnen (flower patch)
        int dim = w.length;
        double[][] g = new double[dim][2];
        double[] center = stelle.args();

        for (int d = 0; d < dim; d++) {
            double range = w[d][1] - w[d][0];
            double rad = s * range;          // s relativ
            double lo = center[d] - rad;
            double hi = center[d] + rad;

            if (lo < w[d][0]) lo = w[d][0];
            if (hi > w[d][1]) hi = w[d][1];

            g[d][0] = lo;
            g[d][1] = hi;
        }

        // Startwert
        Stelle best = bestResults[idx];

        // b neue Stellen im Feld erzeugen
        for (int i = 0; i < b; i++) {
            double[] args = new double[dim];

            for (int d = 0; d < dim; d++) {
                args[d] = g[d][0] + (g[d][1] - g[d][0]) * rnd.nextDouble();
            }

            double val = f.apply(args);
            Stelle cand = new Stelle(args, val);

            if (c.compare(cand, best) < 0) {
                best = cand;
            }
        }

        // SYNCHRONISIERTE RÜCKGABE DES ERGEBNISSES
        // ANMERKUNG: Eine Alternative Implementierung ohne Synchronisation erfolgte mithilfe eines zweidimensionalen
        // Arrays, dessen erste Dimension für die Felder und die zweite für die Threads steht.
        // So hat jeder Thread ein dediziertes eigenes Feld für das Ablegen seiner Ergebnisse und es benötigt keine
        // Synchronisation. Allerdings musste in jener Implementierung der Hauptprozess das Zusammenfassen & Auswählen
        // der besten Ergebnisse übernehmen. Durch diesen Koordinationsaufwand bot die Lösung gegenüber der
        // synchronisierten Lösung keinen Laufzeitvorteil, weswegen hier die synchronisierte Variante (auch zwecks des
        // Lernziels Synchronisation) gewählt wird.

        // Nur wenn überhaupt eine neue bessere Stelle gefunden wurde, lock sperren
        if (c.compare(best, bestResults[idx]) < 0) {

            // Synchronisation auf Methoden-Parameter (Compiler-Warnung).
            // Auf die übergebene Stelle wird nicht geschrieben.
            // Es wird je die gleiche Stelle an jene Threads übergeben, die auf dem gleichen Feld arbeiten.
            // Somit fungiert die Stelle als kontrolliertes Lock-Objekt.
            synchronized (stelle) { // Kein anderer Thread, der auf diesem Feld arbeitet, kann dazwischen funken

                //log(thrName + ": Blockiere " + idx); // logs für Debug anschalten

                // Erneut checken, dass nicht inzwischen ein besserer Wert geschrieben wurde...
                if (c.compare(best, bestResults[idx]) < 0) {

                    // ...dann neue beste Stelle in gemeinsamen Speicher schreiben
                    //log(thrName + ": Ersetze " + bestResults[idx] + " durch " + best); // logs für Debug anschalten
                    bestResults[idx] = best;
                }

                //log(thrName + ": Gebe frei " + idx); // logs für Debug anschalten
            }
        }
    }

    // ---------- BEES ALGORITHM ----------

    public static void main(String[] args) throws Exception {
        //int procIndex = Integer.parseInt(args[0]); // Prozessindex // für LOG für TEST/DEBUG anschalten

        // Parameter einlesen
        BAParameter params = readBAParams();

        //log( // LOG für TEST/DEBUG anschalten
        //        "Prozess " + procIndex + "\n" +
        //                "Wertebereiche: " + Arrays.deepToString(params.w()) + "\n" +
        //                "Anzahl Bienen n = " + params.n() + "\n" +
        //                "Anzahl Threads pro Prozess = " + params.k() + "\n" +
        //                "Bienen pro Block = " + params.b() + "\n" +
        //                "e = " + params.e() + " exzellente Felder\n" +
        //                "p = " + params.p() + " -> " + (params.p() / params.b()) + " Bloecke pro exzellentem Feld\n" +
        //                "m-e = " + (params.m() - params.e()) + " gute Felder\n" +
        //                "q = " + params.q() + " -> " + (params.q() / params.b()) + " Bloecke pro gutem Feld"
        //);

        // Comparator für Sortierung der Stellen
        Comparator<Stelle> vergleich = ExecuteBA.stellenComparator(params.c());

        // Ergebnisliste
        CustomList results = new CustomList();

        // Anzahl Threads
        int k = params.k();
        Thread[] threads = new Thread[k];

        // Pro Thread ein Random
        SplittableRandom[] randoms = new SplittableRandom[k];
        for (int thr = 0; thr < k; thr++)
            randoms[thr] = new SplittableRandom();

        // --- BeesAlgorithm, mit mehreren Threads ---

            //log(
            //        """
            //                ===================================
            //                              START             \s
            //                ==================================="""
            //);
            //log("STARTE GLOBALE SUCHE: " + params.n() / params.b() + " Bloecke"); // LOG für TEST/DEBUG anschalten

        // --- START: Parallele globale Suche mit n Kundschafterinnen, mit k Threads & je Blockgröße b ---

        final AtomicInteger blocksGlobal = new AtomicInteger(params.n() / params.b()); // Anzahl Blöcke
        // Array mit Ergebnissen aller Threads der ersten globalen Suche (pro Block eine Liste)
        CustomList[] globalresultsStart =  new CustomList[blocksGlobal.get()];
        for (int i = 0; i < globalresultsStart.length; i++) {
            globalresultsStart[i] = new CustomList();
        }

        // k Threads
        for (int thr = 0; thr < params.k(); thr++) {

            final int thrID = thr;
            threads[thr] = new Thread(() -> {

                //String thrName = Thread.currentThread().getName(); // für Identifikation Thread in Log
                //log("Starte Thread " + thrName); // LOG für TEST/DEBUG anschalten

                // globale Suche
                int block;
                while ((block = blocksGlobal.getAndDecrement()) > 0) {

                    //log("Thread " + thrName + " - Block " + (block)); // LOG für TEST/DEBUG anschalten

                    globalSearch(params.f(), params.w(), params.b(), globalresultsStart[block-1], randoms[thrID]);
                }
            });
            threads[thr].start();
        }

        // Warten bis alle Threads fertig sind
        for (Thread t : threads)
            t.join();

        // Ergebnisse aller Threads zusammenfassen
        for (CustomList globalResult : globalresultsStart) {
            results.addAll(globalResult);
        }
        results.sort(vergleich);

        //log("--> Anzahl Ergebnisse: " + results.size()); // LOG für TEST/DEBUG anschalten

        // --- ITERATIONEN LOKALE & GLOBALE SUCHEN

        final int blocksGlobalInitial = (params.n() - params.m()) / params.b();
        final int blocksExcellentInitial = params.p() / params.b() * params.e();
        final int blocksGoodInitial = params.q() / params.b() * (params.m() - params.e());

        // Synchronisation: gemeinsame Counter & Ergebnis-Collections für alle Threads
        AtomicInteger blocksLocalExcellent = new AtomicInteger(0); // Anzahl Blöcke für e beste (exzellente) Felder gesamt
        final int blocksPerExcellentField = params.p() / params.b(); // Anzahl Blöcke pro exzellentem Feld
        AtomicInteger blocksLocalGood = new AtomicInteger(0); // Anzahl Blöcke für m-e nächstbeste (gute) Felder gesamt
        final int blocksPerGoodField = params.q() / params.b(); // Anzahl Blöcke pro gutem Feld
        Stelle[] localExcellentResults = new Stelle[params.e()]; // Bestes Ergebnis pro exzellentem Feld
        Stelle[] localGoodResults = new Stelle[params.m() - params.e()]; // Bestes Ergebnis pro gutem Feld
        final CustomList[] globalResults = new CustomList[blocksGlobalInitial]; // Ergebnisse globale Suche
        for (int i = 0; i < globalResults.length; i++) {
            globalResults[i] = new CustomList();
        }

        // t Iterationen
        for (int durchlauf = 1; durchlauf <= params.t(); durchlauf++) {

            //log(
            //        "===================================\n" +
            //        "         DURCHLAUF: " + durchlauf + "\n" +
            //        "==================================="
            //); // LOG für TEST/DEBUG anschalten

            // REKRUTIERUNGSPHASE: Anlegen Blöcke
            blocksGlobal.set(blocksGlobalInitial); // n-m lokale Suchen, je Blockgröße b
            blocksLocalExcellent.set(blocksExcellentInitial); // Pro exzellentem Feld Suche mit p Bienen, je Blockgröße b
            blocksLocalGood.set(blocksGoodInitial); // Pro gutem Feld Suche mit q Bienen, je Blockgröße b

            // Bisherige beste e Ergebnisse
            for (int i = 0; i < params.e(); i++) {
                localExcellentResults[i] = results.get(i);
            }
            // Bisherige beste nächstbeste m-e Ergebnisse
            for (int i = 0; i < params.m() - params.e(); i++) {
                localGoodResults[i] = results.get(params.e() + i);
            }
            // globale Listen einfach leeren
            for (CustomList res : globalResults) {
                res.clear();
            }

            // PARALLELE LOKALE UND GLOBALE SUCHEN
            // k Threads
            // ANMERKUNG: Die Threads werden in jeder Iteration neu erzeugt.
            // Eine alternative Implementierung ohne ständiges Neu-Erzeugen beinhaltet einen zusätzlichen recrutor-Thread,
            // der die sequentielle Rekrutierungsphase ausführt und je danach die Worker-Threads aufweckt.
            // Die Worker-Threads signalisieren dann wieder dem recrutor-Thread, wenn sie fertig sind.
            // Allerdings erwies sich der Overhead durch Synchronisation (warten & aufwecken, Monitor-Synchro)
            // als ineffizient.
            for (int thr = 0; thr < params.k(); thr++) {

                final int thrID = thr;
                threads[thr] = new Thread(() -> {

                    //String thrName = Thread.currentThread().getName(); // für Identifikation Thread in Log
                    //log("Starte Thread " + thrName); // LOG für TEST/DEBUG anschalten

                    // LOKALE SUCHE AUF E BESTE (EXZELLENTE) FELDER
                    int block = blocksLocalExcellent.getAndDecrement(); // nächsten Block schnappen

                    // Solange noch Blöcke da sind: bearbeiten
                    while (block > 0) {

                        int taskId = block - 1; // idx ist null-indiziert, daher - 1
                        int idxField = taskId / blocksPerExcellentField; // Zu welchem der exzellenten Felder gehört dieser Block
                        Stelle stelle = results.get(idxField); // Feld holen

                        //int blockInField = (taskId % blocksPerExcellentField) + 1; // Für log Nummer des Blocks pro Feld
                        //log("Thread " + thrName + ": Feld " + (idxField + 1) + " - Block " + blockInField); // LOG für TEST/DEBUG anschalten

                        // Lokale Suche
                        localSearch(params.f(), params.w(), params.s(), vergleich, stelle, params.b(), localExcellentResults, idxField, randoms[thrID]);

                        block = blocksLocalExcellent.getAndDecrement(); // nächsten Block schnappen
                    }

                    //log("Thread " + thrName + ": Fertig mit e beste Felder, jetzt m naechstbeste"); // LOG für TEST/DEBUG anschalten

                    // LOKALE SUCHE AUF NÄCHSTBESTE (GUTE) M-E FELDER
                    block = blocksLocalGood.getAndDecrement(); // nächsten Block schnappen

                    // Solange noch Blöcke da sind: bearbeiten
                    while (block > 0) {
                        int taskId = block - 1; // idx ist null-indiziert, daher - 1
                        int idxField = taskId / blocksPerGoodField; // Zu welchem der guten Felder gehört dieser Block
                        Stelle stelle = results.get(params.e() + idxField); // Feld holen

                        //int blockInField = (taskId % blocksPerGoodField) + 1; // Für log Nummer des Blocks pro Feld
                        //log("Thread " + thrName + ": Feld " + (params.e() + idxField + 1) + " - Block " + blockInField); // LOG für TEST/DEBUG anschalten

                        // Lokale Suche
                        localSearch(params.f(), params.w(), params.s(), vergleich, stelle, params.b(), localGoodResults, idxField, randoms[thrID]);

                        block = blocksLocalGood.getAndDecrement(); // nächsten Block schnappen
                    }

                    //log("Thread " + thrName + ": Fertig mit m naechstbeste Felder, jetzt globale Suche"); // LOG für TEST/DEBUG anschalten

                    // GLOBALE SUCHE
                    // Solange noch Blöcke da sind: bearbeiten
                    while ((block = blocksGlobal.getAndDecrement()) > 0) { // nächsten Block schnappen

                        //log("Thread " + thrName + " - Block " + block); // LOG für TEST/DEBUG anschalten

                        // Globale Suche
                        globalSearch(params.f(), params.w(), params.b(), globalResults[block-1], randoms[thrID]);
                    }

                    //log("Thread " + thrName + ": Fertig mit globaler Suche"); // LOG für TEST/DEBUG anschalten

                });
                threads[thr].start();
            }

            // Warten bis alle Threads fertig sind
            for (Thread t : threads)
                t.join();

            //log(
            //"Local excellent: " + Arrays.toString(localExcellentResults) + "\n" +
            //                "Local good     : " + Arrays.toString(localGoodResults) + "\n" +
            //                "--> excellent  : " + localExcellentResults.length + "\n" +
            //                "--> good       : " + localGoodResults.length + "\n" +
            //); // LOG für TEST/DEBUG anschalten

            // Ergebnisse zusammenfassen
            results.clear();
            for (CustomList res : globalResults)
                results.addAll(res);
            results.addAll(Arrays.asList(localExcellentResults));
            results.addAll(Arrays.asList(localGoodResults));
            results.sort(vergleich);

            //log("--> Anzahl Ergebnisse: " + results.size()); // LOG für TEST/DEBUG anschalten

        }

        // Ergebnisliste mit besten r Ergebnissen zurückgeben
        results.sort(vergleich); // sichergehen, dass es sortiert ist
        CustomList topR = new CustomList(results.subList(0, Math.min(params.r(), results.size())));
        sendResult(topR);
    }
}
