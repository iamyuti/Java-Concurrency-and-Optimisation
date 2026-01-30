import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;

// Klasse zur Ausführung des BeesAlgorithm.
public class ExecuteBA {

    // ---------- PIPELINE METHODEN ----------

    // Sendet ein BAParameter-Objekt
    private static void sendBAParams(OutputStream out, BAParameter params) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(out)) {
            oos.writeObject(params);
            oos.flush();
        } // try schließt danach die Pipeline automatisch
    }

    // Liest übergebene Ergebnisse ein
    private static CustomList readResult(InputStream in) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(in)) {
            return (CustomList) ois.readObject();
        } // try schließt danach die Pipeline automatisch
    }

    // ---------- HILFSFUNKTIONEN --------

    // Comparator basiert auf übergebener Vergleichs-Funktion
    public static Comparator<Stelle> stellenComparator(BiFunction<Double, Double, Double> c) {
        return (s1, s2) -> compareByChoiceFunction(s1.wert(), s2.wert(), c);
    }

    // Baut aus einer "besserer-von-zwei"-Funktion (wie Math::min / Math::max)
    // einen Comparator, der den Comparator-Contract garantiert einhält.
    // D.h. auch bei gleichen (nicht selben) Werten eine deterministische Reihenfolge zurückgibt.
    private static int compareByChoiceFunction(Double a, Double b, BiFunction<Double, Double, Double> c) {

        // NaN robust behandeln: NaN immer "schlechter" (am Ende)
        boolean na = Double.isNaN(a);
        boolean nb = Double.isNaN(b);
        if (na && nb) return 0;
        if (na) return 1;
        if (nb) return -1;

        // Primäre Präferenz aus c in beiden Richtungen bestimmen
        Double ab = c.apply(a, b);
        Double ba = c.apply(b, a);

        boolean aPreferred = ab != null && ab.equals(a) && ba != null && ba.equals(a);
        boolean bPreferred = ab != null && ab.equals(b) && ba != null && ba.equals(b);

        if (aPreferred && !bPreferred) return -1;
        if (bPreferred && !aPreferred) return 1;

        // Gleichstand oder inkonsistente choice-Funktion: deterministisch tie-breaken
        return Double.compare(a, b);
    }

    // ---------- BEES ALGORITHM ----------

    /*
     * Diese Methode erzeugt alle zur Ausführung nötigen Prozesse und nimmt am Ende die Ergebnisse entgegen,
     * fasst diese zusammen und gibt sie dann aus.
     * @param f Die zu untersuchende Funktion
     * @param w Die zu untersuchenden Wertebereiche (Grenzen) aller Argumente
     * @param c Vergleichsfunktion, die die bessere von zwei Zahlen ermittelt
     * @param b Anzahl der Bienen in einem Block
     * @param k Anzahl der Threads in einem Prozess
     * @param t Anzahl der Suchschritte nach denen abgebrochen wird
     * @param n Anzahl der Kundschafterinnen
     * @param m Anzahl der Felder, die (weiter) untersucht werden
     * @param e Anzahl exzellenter Felder, die sehr genau untersucht werden
     * @param p Anzahl der für ein exzellentes Feld rekrutierten Bienen
     * @param q Anzahl der für ein anderes Feld rekrutierten Bienen
     * @param s Größe der Felder relativ zum untersuchten Bereich
     * @param r Anzahl der am Ende zurückzugebenden besten gefundenen Stellen
     * @param title Titel der Suche
     * @param description Beschreibung der Suche
     */
    public static void search(SerializableFunction<double[],Double> f, double[][][] w, SerializableBiFunction<Double, Double, Double> c,
                              int b, int k, int t, int n, int m, int e, int p, int q, double s, int r, String title, String description) throws IOException, InterruptedException, ClassNotFoundException {

        // --- Aufteilung Wertebereiche auf die Prozesse ---

        int anzProzesse = w.length;

        // Liste mit allen Prozessen
        List<Process> prozesse = new ArrayList<>(anzProzesse);

        // Prozesse erzeugen
        for (int i = 0; i < anzProzesse; i++) {
            // Fehlermeldungen in eigener Datei ablegen (für Debugging)
            File errFile = new File("worker-" + i + ".err");

            ProcessBuilder pb = new ProcessBuilder(
                    "java", // neue JVM starten
                    "BAProcess", // Klasse BAProcess laden
                    Integer.toString(i) // Prozess-Index
            );

            pb.redirectError(errFile); // Fehlermeldungen in eigener Datei ablegen (für Debugging)
            Process proc = pb.start(); // Prozess starten
            prozesse.add(proc); // Prozess merken

            // Parameter serialisieren (BAParameter implementiert Serializable)
            BAParameter params = new BAParameter(
                    f, w[i],   // nur w[i] senden (Argumentbereiche für den i-ten Prozess)
                    c, b, k, t, n, m, e, p, q, s, r
            );

            sendBAParams(proc.getOutputStream(), params); // Parameter über Pipeline senden
        }

        // --- Sammeln der Ergebnisse ---

        // Ergebnisliste
        CustomList results = new CustomList();

        for (Process proc : prozesse) {
            // Ergebnisse vom BAProzess lesen & in Ergebnisliste zusammenfassen
            CustomList res = readResult(proc.getInputStream());
            results.addAll(res);

            // Warten bis Prozess beendet ist
            try {
                int exit = proc.waitFor();
                if (exit != 0) {
                    throw new IOException("Worker-Prozess endete mit Exit-Code " + exit);
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                throw new IOException("Warten auf Worker unterbrochen.", ie);
            }

        }

        // ANMERKUNG:
        // Die obere Variante sammelt die Prozesse sequentiell ein.
        // Die unten auskommentierte Variante "lauscht" parallel auf alle Prozesse.
        // Allerdings bringt dies in unseren Anwendungsfällen keinen Laufzeitvorteil.
        // Die untere Variante verhindert außerdem ein Blockieren der Pipelines durch zu große Datenmengen,
        // falls nicht rasch genug gelesen wird.
        // Da unsere Prozesse in jedem Fall nur eine Liste mit den r besten Ergebnissen zurückgeben und
        // r typischerweise klein ist, ist der Overhead durch Buffer-Absicherung in diesem Fall nicht nötig.
        // Wir lassen die alternative Implementierung trotzdem auskommentiert stehen, sodass sie bei Bedarf
        // (z.B. Änderung der Anwendungsfälle) eingesetzt werden kann.
        /*
        // Per Collector-Threads alle Prozesse parallel verwalten (verhindert blockierende Buffer)
        List<Thread> collectors = new ArrayList<>(); // Collector-Threads

        // Synchronisierte Ergebnisliste
        List<Stelle> results = Collections.synchronizedList(new ArrayList<>());

        // Ein Thread pro Prozess, der ständig liest
        for (Process proc : prozesse) {
            Thread thr = new Thread(() -> {
                try {
                    // Lesen, damit stdout-Buffer nicht blockiert
                    CustomList res = readResult(proc.getInputStream());

                    // Warten, bis Prozess beendet ist
                    int exit = proc.waitFor();
                    if (exit != 0) {
                        throw new IOException("Worker-Prozess endete mit Exit-Code " + exit);
                    }

                    results.addAll(res); // synchronizedList synchronisiert Einfügen

                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Collector-Thread unterbrochen", ie);
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException("Fehler beim Einsammeln von Prozess " + proc, ex);
                }
            });
            thr.start();
            collectors.add(thr);
        }

        for (Thread thr : collectors) {
            thr.join();
        }
        */

        // --- Ergebnisse zusammenfassen ---

        // Ergebnisse sortieren
        results.sort(stellenComparator(c));

        // Beste r Ergebnisse
        CustomList topResults = new CustomList (results.subList(0, Math.min(r, results.size())));

        // --- Ausgabe / Rückgabe ---
        printErgebnisse(topResults, title, description);
    }

    // Gibt ein Ergebnis schön in der Konsole aus.
    private static void printErgebnisse(CustomList erg, String title, String description) {
        int padding = 4; // Leerzeichen links und rechts vom Titel
        int innerWidth = title.length() + 2 * padding;
        int totalWidth = innerWidth + 6; // "=== " + " ==="

        String border = "=".repeat(totalWidth);
        String line = "===" +
                " ".repeat(padding) +
                title +
                " ".repeat(padding) +
                "===";

        System.out.println();
        System.out.println(border);
        System.out.println(line);
        System.out.println(border);
        System.out.println(description);

        erg.forEach(System.out::println);
    }
}
