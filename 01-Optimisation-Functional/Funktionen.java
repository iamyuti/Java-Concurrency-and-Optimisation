import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// Sammlung an Funktionen/ Funktionalen Formen
public final class Funktionen {

    private Funktionen() {}

    // Erstellt zufällige Stelle innerhalb double[][] grenzen
    public static Supplier<Stelle> stellenGenerator(double[][] grenzen, Function<double[], Double> f) {
        // Hier Randfall hinsichtlich referentieller Transparenz: Random als lokaler Zustand!
        // Bleibt aber lokal & ist zwecks Zufallszahlgenerierung nötig
        // Effizienzverlust (häufige lokale Instanziierung) wird zwecks Kapselung in Kauf genommen
        final Random random = new Random();

        return () -> {
            // Für jedes Argument einen neuen Wert erzeugen
            double[] args = Arrays.stream(grenzen).mapToDouble(
                            // je Argument 1 zufälliger Wert innerhalb oberer und untere Grenze
                            argument -> random.nextDouble(argument[0], argument[1]))
                    .toArray();

            return new Stelle(args, f.apply(args));
        };
    }

    // Comparator basiert auf übergebener Vergleichs-Funktion
    public static Comparator<Stelle> stellenComparator(BiFunction<Double, Double, Double> c) {
        return (s1, s2) -> {
            final Double w1 = s1.wert();
            final Double w2 = s2.wert();
            // Vergleichsfunktion gibt besseren Wert zurück
            final Double bessererWert = c.apply(w1, w2);

            // Entsprechendes Comparator-Ergebnis
            return bessererWert.equals(w1) ? -1 : bessererWert.equals(w2) ? 1 : 0;
        };
    }

    // Rückgegebene Funktion erstellt Feld um Stelle
    public static Function<Stelle, Feld> feldErzeuger(double[][] globaleGrenzen, double s) {

        // Vorberechnung der halben Feldbreiten (pro Dimension 1x = effizienter)
        final double[] halbeBreiten = Arrays.stream(globaleGrenzen)
                // Je Dimension ein Wert mit der halben Breite des s-skalierten (s x 100 %) Anteils an der Gesamtbreite
                .mapToDouble(grenze -> (grenze[1] - grenze[0]) * s / 2.0)
                .toArray();

        return stelle -> {
            // Koordinaten der Stelle (Pro Dimension 1 Wert)
            final double[] args = stelle.args();

            // Feldgrenzen
            final double[][] feldGrenzen = IntStream.range(0, globaleGrenzen.length)
                    // Pro Dimension 2 Werte: obere und untere Grenze
                    .mapToObj(i -> new double[]{
                            Math.max(globaleGrenzen[i][0], args[i] - halbeBreiten[i]),
                            Math.min(globaleGrenzen[i][1], args[i] + halbeBreiten[i])
                    })
                    .toArray(double[][]::new);

            return new Feld(stelle, feldGrenzen);
        };
    }

    // t-malige Ausführung des UnaryOperators f
    public static <T> UnaryOperator<T> wiederhole(int t, UnaryOperator<T> f) {
        return startWert -> Stream.iterate(startWert, f) // f mehrfach anwenden
                .skip(t) // erste t-1 Zwischenergebnisse überspringen
                .findFirst() // t-tes Ergebnis (entspricht t-facher Anwendung von f) nehmen
                .orElse(startWert);
    }

}