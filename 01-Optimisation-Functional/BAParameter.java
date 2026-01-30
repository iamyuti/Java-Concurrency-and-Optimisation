import java.util.function.BiFunction;
import java.util.function.Function;

// Bündelt alle Parameter des BeesAlgorithm
public record BAParameter(Function<double[],Double> f, // Die zu untersuchende Funktion,
                          double[][] w, // Die zu untersuchenden Wertebereiche (Grenzen) aller Argumente
                          BiFunction<Double, Double, Double> c, // Vergleichsfunktion, die die bessere von zwei Zahlen ermittelt
                          // Anmerkung: implizit geht aus der Aufgabenstellung sowie der originalen Definition von BeesAlgorithms hervor,
                          // dass c auf einer totalen Ordnung basiert.
                          // Andernfalls wäre es nicht möglich, die e "besten" Felder o.ä. zu wählen.
                          int t, // Anzahl der Suchschritte nach denen abgebrochen wird
                          int n, // Anzahl der Kundschafterinnen
                          int m, // Anzahl der Felder, die (weiter) untersucht werden
                          int e, // Anzahl exzellenter Felder, die sehr genau untersucht werden
                          int p, // Anzahl der für ein exzellentes Feld rekrutierten Bienen
                          int q, // Anzahl der für ein anderes Feld rekrutierten Bienen
                          double s, // Größe der Felder relativ zum untersuchten Bereich
                          int r) { // Anzahl der am Ende zurückzugebenden besten gefundenen Stellen

    // Konstruktor
    public BAParameter {
        // Validierung aller Parameter
        if (f == null)
            throw new IllegalArgumentException("Die zu untersuchende Funktion darf nicht null sein!");
        if (w == null || w.length == 0)
            throw new IllegalArgumentException("Der zu untersuchende Wertebereiche aller Argumente darf nicht leer sein!");
        if (c == null)
            throw new IllegalArgumentException("Die Vergleichsfunktion darf nicht null sein!");
        if (t <= 0)
            throw new IllegalArgumentException("Die Anzahl der Suchschritte nach denen abgebrochen wird muss größer 0 sein!");
        if (n < 0)
            throw new IllegalArgumentException("Die Anzahl der Kundschafterinnen darf nicht kleiner 0 sein!");
        if (m < 0 || m >= n)
            throw new IllegalArgumentException("Die Anzahl der Felder, die (weiter) untersucht werden muss mindestens 0 und < n sein!");
        if (e < 0 || e >= m)
            throw new IllegalArgumentException("Die Anzahl der exzellenten Felder muss mindestens 0 und < m sein!");
        if (p < 0)
            throw new IllegalArgumentException("Die Anzahl der für ein exzellentes Feld rekrutierten Bienen darf nicht kleiner 0 sein!");
        if (q < 0 || q >= p)
            throw new IllegalArgumentException("Die Anzahl der für ein anderes Feld rekrutierten Bienen muss mindestens 0 und < p sein!");
        if (s <= 0.0 || s > 1)
            throw new IllegalArgumentException("Die Größe der Felder relativ zum untersuchten Bereich muss größer 0 und <= 1 sein!");
        if (r <= 0)
            throw new IllegalArgumentException("Die Anzahl der am Ende zurückzugebenden besten gefundenen Stellen muss größer 0 sein!");
    }
}