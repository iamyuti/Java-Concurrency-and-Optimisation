import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/*
 * Test.java = prozedurale/imperative Hülle (Ausgabe + konkrete Testfälle).
 *
 * Nicht-funktionale Teile / Seiteneffekte:
 *   - ausschließlich System.out.print* in printTest(...)
 *
 * Funktionale Teile:
 *   - BeesAlgorithm.search(...) und alle verwendeten Hilfsklassen/Funktionen
 *     sind IO-frei und sollen keine Seiteneffekte haben.
 *
 * Schnittstelle:
 *   - runTest(...) baut nur Parameter, ruft BeesAlgorithm.search(...) auf
 *     und übergibt das Ergebnis zur Ausgabe an printTest(...).
 */
public class Test {

    // Hier Ausgabe = nicht funktionale-Seiteneffekte gekapselt
    private static void printTest(List<Stelle> erg, String title, String description) {
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


    private static void runTest(
            String title,
            String description,
            Function<double[], Double> f,
            double[][] w,
            BiFunction<Double, Double, Double> c,
            int t, int n, int m, int e, int p, int q,
            double s, int r
    ) {
        List<Stelle> erg = BeesAlgorithm.search(
                new BAParameter(f, w, c, t, n, m, e, p, q, s, r)
        );

        // Schnittstelle zur Ausgabe
        printTest(erg, title, description);
    }

    public static void main(String[] args) {

        /*
        Beim Testen sind konkrete Werte einzusetzen.
        Wählen Sie Werte so, dass sich eine Ausführung von Test in etwa 20 bis 60 Sekunden ausgeht.
        In dieser Zeit sind mindestens drei Optimierungsaufgaben mit unterschiedlichen, nicht trivialen Funktionen f,
        Wertebereichen w und Vergleichsfunktionen c auszuführen.
        Werte für die anderen Parameter sind so anzupassen, dass innerhalb des Zeitrahmens möglichst gute Ergebnisse erzielt werden.
        Ergebnisse sind auf möglichst übersichtliche Weise in Form von Ausgabetext darzustellen.
        Für mindestens eine Optimierungsaufgabe soll f mehrere Parameter haben
        und für alle Optimierungsaufgaben soll es mehrere gleich gute Lösungen geben (etwa mehrere Nullstellen
        oder mehrere gleich große Maxima oder Minima).
        */

        // Vergleichsfunktion für Nullstellen
        BiFunction<Double, Double, Double> nullstellen = (a, b) ->
                Math.abs(a) <= Math.abs(b) ? a : b;

        /*
        Eine der mindestens drei Optimierungsaufgaben soll für f eine Sinus-Funktion (mit einem Parameter)
        in einem Wertebereich zwischen -1800.0 und 1800.0 verwenden (wobei ein Kreis 360◦ hat),
        Maximalwerte berechnen und 10 Ergebnisse ausgeben.
        */

        runTest(
                "TEST MAXIMALWERTE SINUS",
                "Berechnung der Maximalwerte einer Sinus-Funktion in einem Wertebereich zwischen -1800.0 und 1800.0 mit den folgenden 10 besten Ergebnissen:\n" +
                        "Maximalwert sollte ~1 sein und bei −1710, −1350, −990, −630, −270, 90, 450, 810, 1170 oder 1530 Grad liegen",
                funktion -> Math.sin(Math.toRadians(funktion[0])), // Sinusfunktion im Gradmaß
                new double[][]{{-1800.0, 1800.0}},
                Math::max,
                30, 30, 15, 5, 10, 5, 0.1, 10
        );

        /*
         Die beiden anderen Optimierungsaufgaben sind selbst zu wählen.
         */

        /*
         * Die verwendeten Optimierungsfunktionen sind nicht trivial.
         *
         * Für f(x,y,z) = (x − y)^2 + (x − z)^2 werden Minima gesucht.
         * Die Funktion besitzt nicht nur ein einzelnes Minimum, sondern eine
         * unendliche Menge gleich guter Lösungen (alle Punkte mit x = y = z).
         * Dadurch entsteht kein punktförmiges Optimum, sondern eine Lösungsmenge (Gerade),
         * die durch den Bees Algorithm exploriert/approximiert werden muss.
         *
         * Für f(x,y) = x^2 − y^2 werden Nullstellen gesucht, wobei die Vergleichsfunktion
         * den kleineren Absolutwert bevorzugt.
         * Die Nullstellen bilden zwei sich schneidende Geraden (x = y und x = −y),
         * also ebenfalls eine nicht-diskrete Menge gleichwertiger Lösungen.
         *
         * Beide Funktionen stellen somit echte Suchprobleme dar und sind für den
         * Einsatz einer Metaheuristik geeignet, da mehrere gleich gute Lösungen
         * existieren und keine einfache analytische Auswahl eines einzelnen Optimums erfolgt.
         */

        runTest("TEST MINIMALWERTE (x−y)^2 + (y−z)^2 ",
                "Berechnung der Minimalwerte der Funktion f(x,y,z)= (x−y)^2 + (y−z)^2 in einem Wertebereich zwischen -10 und 10 für x,y,z mit den folgenden 5 besten Ergebnissen:\n" +
                        "Minimalwerte sollten ~ 0 sein und  bei ~ x = y = z liegen",
                funktion -> {
                    double x = funktion[0];
                    double y = funktion[1];
                    double z = funktion[2];
                    return (x - y) * (x - y) + (y - z) * (y - z);
                },
                new double[][]{{-10,10},{-10,10},{-10,10}},
                Math::min,
                20, 60, 20, 5, 15, 10, 0.05, 5
        );

        /*
        Für mindestens eine Optimierungsaufgabe soll f mehrere Parameter haben
         */

        runTest(
                "TEST NULLSTELLEN x^2 − y^2",
                "Berechnung der Nullstellen der Funktion f(x,y)= x^2 − y^2 in einem Wertebereich zwischen -50 und 50 für x und y mit den folgenden 10 besten Ergebnissen:\n" +
                        "Nullstellen sollten bei ~ x = +-y liegen",
                funktion -> funktion[0]*funktion[0] - funktion[1]*funktion[1],
                new double[][]{{-50.0, 50.0}, {-50.0, 50.0}},
                nullstellen,
                20, 50, 25, 10, 20, 15, 0.05, 10
        );

    }
}
