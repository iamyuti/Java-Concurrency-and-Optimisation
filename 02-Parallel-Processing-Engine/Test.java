import java.io.IOException;

// Testklasse
public class Test {

    // ANMERKUNG ZUM LOG/DEBUG
    // Zwecks einfacherer Testung lassen wir die Log-Ausgaben in die .err-files
    // auskommentiert stehen. Sie können so einfach bei Bedarf "angeschaltet" werden.

    // Tests
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {

        // ANMERKUNG: Laut Angabe sollen sich in Test.java genau 3 Test befinden.
        // Zur besseren Testung (Vergleich der Laufzeiten etc.) haben wir weitere Testläufe geschrieben,
        // die bei Bedarf wieder eingesetzt werden können.
        // Sie werden hier zwecks Erfüllung der Angabe auskommentiert.

        // DEBUG/TEST: max. 20Sek
        long testTimeStart= System.nanoTime();

        // --- ERHÖHUNG DER SUCHTIEFE DURCH AUFTEILUNG DES WERTEBEREICHS AUF MEHRERE PROZESSE ---

        // TEST 1: Rosenbrock-Funktion "Bananental" (4 Prozesse, k=1, Siehe Angabe).
        // Durch die Aufteilung auf 4 Prozesse erhalten wir eine höhere Suchtiefe, die nötig ist,
        // um das "schwer auffindbare" Minimum der Rosenbrock-Funktion zu finden.
        long start = System.nanoTime();
        ExecuteBA.search(
                x -> {
                    double res = 0.0;
                    for (int i = 0; i < x.length - 1; i++) {
                        double a = 1.0 - x[i];
                        double b = x[i + 1] - x[i] * x[i];
                        res += a * a + 100.0 * b * b;
                    }
                    return res;
                },
                new double[][][]{

                        // --- Exploitation nahe Optimum ---
                        { { 0.50, 1.50 }, { 0.50, 1.50 }, { 0.50, 1.50 }, { 0.50, 1.50 } }, // P0: breit um 1
                        { { 0.80, 1.20 }, { 0.80, 1.20 }, { 0.80, 1.20 }, { 0.80, 1.20 } }, // P1: eng um 1

                        // --- Exploration: x0 splitten, Rest moderat eingeschränkt ---
                        { { -5.0, -1.0 }, { -2.0, 2.0 }, { -2.0, 2.0 }, { -2.0, 2.0 } }, // P2
                        { {  1.0,  5.0 }, { -2.0, 2.0 }, { -2.0, 2.0 }, { -2.0, 2.0 } }  // P3
                },
                Math::min,
                40, 1, 80, 4000, 160, 120, 400, 200, 0.12, 5,
                "ROSENBROCK 4D (4 Prozesse, k=1)",
                "Rosenbrock 4D. Globales Minimum bei (1,1,1,1) mit Wert 0.\n" +
                        "2 Prozesse suchen gezielt nahe Optimum, 2 Prozesse explorieren grob mit reduziertem Suchraum."
        );
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit: %.3f s%n", seconds);

        // --- ERHÖHUNG DER LAUFZEITEFFIZIENZ DURCH PARALLELE ABARBEITUNG BEI RECHENLASTIGEREN FUNKTIONEN ---

        // Als Tests werden komplexe Funktionen mit künstlichem CPU-Burn gewählt.
        // So sollen komplexe Funktionen mit großem Rechenaufwand pro Schritt simuliert werden,
        // sodass die Vorteile der Parallelität (im Vergleich zum Mehraufwand durch Synchronisation etc.) überwiegen.
        // Vergleich:
        // Auf einem PC, auf dem diese Tests mit dem sequentiellen Code (von Aufgabe 7) 4min 20sek dauern,
        // dauern sie hier 60sek

        // TEST 2: Himmelblau-Funktion (1 Prozess, k=6, siehe Angabe)
        start = System.nanoTime();
        ExecuteBA.search(
                funktion -> {
                    double x = funktion[0], y = funktion[1];

                    double base = Math.pow(x * x + y - 11, 2) + Math.pow(x + y * y - 7, 2);

                    double burn = 0.0;
                    for (int i = 1; i <= 50; i++) {
                        double t = i * 1e-3;
                        burn += Math.sin(x + t) * Math.cos(y - t)
                                + Math.sin(y + 2 * t) * Math.cos(x - 2 * t);
                    }

                    return base + 1e-6 * burn;
                },
                new double[][][]{
                        {{-10.0, 10.0}, {-10.0, 10.0}}
                },
                Math::min,
                40, 6, 120, 800, 200, 120, 320, 160, 1e-6, 10,
                "HIMMELBLAU heavy (1 Prozess, k=6)",
                "Himmelblau + CPU-Burn, damit Parallelität messbar wird.\n" +
                        "Globale Minima ~0 bei: (3,2), (-2.805,3.131), (-3.779,-3.283), (3.584,-1.848)"
        );
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit: %.3f s%n", seconds);

        /*
        // Durch die Aufteilung auf mehr Prozesse erhalten wir höhere Suchdichte und können die Anzahl der Suchen
        // pro Prozess (Parameter n, m, e, p, q) verringern, bei gleichbleibend guten Ergebnissen.
        // Somit verringern wir erneut die Laufzeit:

        // TEST 2b: Himmelblau-Funktion (8 Prozesse, k=6, zusätzlicher Test)
        start = System.nanoTime();
        ExecuteBA.search(
                funktion -> {
                    double x = funktion[0], y = funktion[1];

                    double base = Math.pow(x * x + y - 11, 2) + Math.pow(x + y * y - 7, 2);

                    double burn = 0.0;
                    for (int i = 1; i <= 50; i++) {
                        double t = i * 1e-3;
                        burn += Math.sin(x + t) * Math.cos(y - t)
                                + Math.sin(y + 2 * t) * Math.cos(x - 2 * t);
                    }

                    return base + 1e-6 * burn;
                },
                new double[][][]{
                        {{-10.0, -7.5}, {-10.0, 10.0}}, // Prozess 0
                        {{-7.5, -5.0},  {-10.0, 10.0}}, // Prozess 1
                        {{-5.0, -2.5},  {-10.0, 10.0}}, // Prozess 2
                        {{-2.5, 0.0},   {-10.0, 10.0}}, // Prozess 3
                        {{0.0, 2.5},    {-10.0, 10.0}}, // Prozess 4
                        {{2.5, 5.0},    {-10.0, 10.0}}, // Prozess 5
                        {{5.0, 7.5},    {-10.0, 10.0}}, // Prozess 6
                        {{7.5, 10.0},   {-10.0, 10.0}}  // Prozess 7
                },
                Math::min,
                4, 6, 120, 100, 20, 12, 32, 16, 1e-6, 10,
                "HIMMELBLAU heavy (8 Prozesse, k=6)",
                "Himmelblau + CPU-Burn, mehr Prozesse, weniger Suchen pro Prozess.\n" +
                        "Globale Minima ~0 bei: (3,2), (-2.805,3.131), (-3.779,-3.283), (3.584,-1.848)"
        );
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit: %.3f s%n", seconds);
         */

        // --- ERHÖHUNG DER LAUFZEITEFFIZIENZ DURCH PARALLELE ABARBEITUNG BEI HÖHEREN PARAMETER WERTEN ---

        // Zusätzlich wählen wir "leichte" Funktionen (einzelner Berechnungsschritt ist nicht aufwändig)
        // und erhöhen die Last durch eine Erhöhung der Zahl an Bienen (Suchen) & Felder.
        // Auch so kommt die Effizienzsteigerung durch parallele Aufteilung zum Tragen.

        // Die Sinus-Funktion ist gut geeignet, da sie viele gleich verteilte Optima hat.

        // TEST 3: Sinus-Funktion (2 Prozesse, k=3, siehe Angabe)
        start = System.nanoTime();
        ExecuteBA.search(funktion -> Math.sin(Math.toRadians(funktion[0])), // Sinusfunktion im Gradmaß
                new double[][][] {
                        { { -1800.0, -200.0 } }, // ungleich große Wertebereiche
                        { { -200.0, 1800.0 } }
                },
                Math::max,
                10, 3, 8, 20000, 3000, 2000, 1500, 1100, 0.1, 10, "TEST MAXIMALWERTE SINUS (2 Prozesse, k=3)",
                "Berechnung der Maximalwerte einer Sinus-Funktion in einem Wertebereich zwischen -1800.0 und 1800.0 mit den folgenden 10 besten Ergebnissen:\n" +
                        "Maximalwert sollte ~1 sein und bei -1710, -1350, -990, -630, -270, 90, 450, 810, 1170 oder 1530 Grad liegen");
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit: %.3f s%n", seconds);

        /*
        // Wir testen die Auswirkungen unterschiedlicher Anzahlen an Threads k:

        // TEST 4a: Sinus-Funktion (4 Prozesse, k=1, zusätzlicher Test)
        start = System.nanoTime();
        ExecuteBA.search(funktion -> Math.sin(Math.toRadians(funktion[0])), // Sinusfunktion im Gradmaß
                new double[][][]{
                        {{-1800.0, -800.0}}, // 1000
                        {{-800.0, -200.0}},  // 600
                        {{-200.0, 200.0}},  // 400
                        {{200.0, 1800.0}} // 1600
                }, // sehr groß
                Math::max,
                10, 1, 8, 20000, 3000, 2000, 1500, 1100, 0.1, 10, "TEST MAXIMALWERTE SINUS - 1 THREAD",
                "Berechnung der Maximalwerte einer Sinus-Funktion in einem Wertebereich zwischen -1800.0 und 1800.0 mit den folgenden 10 besten Ergebnissen:\n" +
                "Maximalwert sollte ~1 sein und bei −1710, −1350, −990, −630, −270, 90, 450, 810, 1170 oder 1530 Grad liegen");
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit (k=1): %.3f s%n", seconds);

        // Da bei der Aufteilung auf Prozesse die Parameter n, m, e, p und q nicht gesplittet werden (sieh FORUM),
        // bringt diese Aufteilung keinen direkten Laufzeit-Vorteil. Die Arbeit wird nicht aufgeteilt, sondern mit gleichen
        // Parameter k-mal nebeneinander ausgeführt. Dies erhöht die Suchdichte und bringt daher genauere Ergebnisse.
        // (indirekt wird so die Laufzeit bei gleichbleibenden Ergebnissen verbessert, siehe Test 2b)
        // Die Aufteilung der Blöcke auf Threads stellt allerdings eine Arbeitsaufteilung im Sinne von
        // direkter schnellerer Abarbeitung dar.
        // Beispielhaft wird daher hier der Test zum SINUS - ursprünglich mit k=1, also nur einem Thread -
        // mit 10 Threads (k=10) ausgeführt.
        // Man sieht, dass das Ergebnis schneller bestimmt werden kann.

        // TEST 4b: Sinus-Funktion (4 Prozesse, k=10, zusätzlicher Test)
        start = System.nanoTime();
        ExecuteBA.search(funktion -> Math.sin(Math.toRadians(funktion[0])), // Sinusfunktion im Gradmaß
                new double[][][]{
                        {{-1800.0, -800.0}}, // 1000
                        {{-800.0, -200.0}},  // 600
                        {{-200.0, 200.0}},  // 400
                        {{200.0, 1800.0}} // 1600
                }, // sehr groß
                Math::max,
                10, 10, 8, 20000, 3000, 2000, 1500, 1100, 0.1, 10, "TEST MAXIMALWERTE SINUS - 10 THREADS",
                "Berechnung der Maximalwerte einer Sinus-Funktion in einem Wertebereich zwischen -1800.0 und 1800.0 mit den folgenden 10 besten Ergebnissen:\n" +
                        "Maximalwert sollte ~1 sein und bei −1710, −1350, −990, −630, −270, 90, 450, 810, 1170 oder 1530 Grad liegen");
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit (k=10): %.3f s%n", seconds);

        // Zu viele Threads hingegen erhöhen den Verwaltungsaufwand (anlegen, umschalten etc.):

        // TEST 4c: Sinus-Funktion (4 Prozesse, k=100, zusätzlicher Test)
        start = System.nanoTime();
        ExecuteBA.search(funktion -> Math.sin(Math.toRadians(funktion[0])), // Sinusfunktion im Gradmaß
                new double[][][]{
                        {{-1800.0, -800.0}}, // 1000
                        {{-800.0, -200.0}},  // 600
                        {{-200.0, 200.0}},  // 400
                        {{200.0, 1800.0}} // 1600
                }, // sehr groß
                Math::max,
                10, 100, 8, 20000, 3000, 2000, 1500, 1100, 0.1, 10, "TEST MAXIMALWERTE SINUS - 100 THREADS",
                "Berechnung der Maximalwerte einer Sinus-Funktion in einem Wertebereich zwischen -1800.0 und 1800.0 mit den folgenden 10 besten Ergebnissen:\n" +
                        "Maximalwert sollte ~1 sein und bei −1710, −1350, −990, −630, −270, 90, 450, 810, 1170 oder 1530 Grad liegen");
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit (k=100): %.3f s%n", seconds);

        // TEST 5: Ackley-Funktion (2 Prozesse, k=3, zusätzlicher Test)
        start = System.nanoTime();
        ExecuteBA.search(
                x -> {
                    int D = x.length;
                    double sum1 = 0.0, sum2 = 0.0;

                    for (double v : x) {
                        sum1 += v * v;
                        sum2 += Math.cos(2.0 * Math.PI * v);
                    }

                    return -20.0 * Math.exp(-0.2 * Math.sqrt(sum1 / D))
                            - Math.exp(sum2 / D)
                            + Math.E + 20.0;

                },
                new double[][][]{

                        // P0: Exploitation um das Optimum (0,...,0)
                        { {-4.0, 4.0}, {-4.0, 4.0}, {-4.0, 4.0}, {-4.0, 4.0}, {-4.0, 4.0}, {-4.0, 4.0} },
                        // P1: Exploration im großen Raum
                        { {-12.0, 12.0}, {-12.0, 12.0}, {-12.0, 12.0}, {-12.0, 12.0}, {-12.0, 12.0}, {-12.0, 12.0} }
                },
                Math::min,
                40, 3, 50, 8000, 720, 560, 800, 400, 0.01, 5,
                "ACKLEY 6D heavy (2 Prozesse, k=3, Fokus um 0)",
                "Ackley 6D + CPU-Burn. Globales Minimum bei 0-Vektor mit Wert ~0."
        );
        end = System.nanoTime();
        seconds = (end - start) / 1_000_000_000.0;
        System.out.printf("Laufzeit: %.3f s%n", seconds);
        */

        // DEBUG/TEST: max. 20Sek
        long testTimeEnd = System.nanoTime();
        double testTime = (testTimeEnd - testTimeStart) / 1_000_000_000.0;
        System.out.printf("\nTestzeit: %.3f s%n", testTime);


    }
}
