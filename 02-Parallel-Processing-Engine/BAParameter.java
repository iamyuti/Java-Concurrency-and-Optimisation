import java.io.Serializable;

/*
 * Bündelt alle Parameter des BeesAlgorithm
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
 */
public record BAParameter(SerializableFunction<double[],Double> f, double[][] w, SerializableBiFunction<Double, Double, Double> c,
                          int b, int k, int t, int n, int m, int e, int p, int q, double s, int r) implements Serializable {

    // Konstruktor für BAParameter zur Validierung der Parameter
    public BAParameter {
        if (f == null)
            throw new IllegalArgumentException("Die zu untersuchende Funktion darf nicht null sein!");
        if (w == null || w.length == 0)
            throw new IllegalArgumentException("Der zu untersuchende Wertebereiche aller Argumente darf nicht leer sein!");
        if (c == null)
            throw new IllegalArgumentException("Die Vergleichsfunktion darf nicht null sein!");
        if (b <= 0)
            throw new IllegalArgumentException("Die Anzahl der Bienen in einem Block muss größer 0 sein!");
        if (k <= 0)
            throw new IllegalArgumentException("Die Anzahl der Threads in einem Prozess muss größer 0 sein!");
        if (t <= 0)
            throw new IllegalArgumentException("Die Anzahl der Suchschritte nach denen abgebrochen wird muss größer 0 sein!");
        if (n < 0)
            throw new IllegalArgumentException("Die Anzahl der Kundschafterinnen darf nicht kleiner 0 sein!");
        if (n % b != 0)
            throw new IllegalArgumentException("Die Anzahl der Kundschafterinnen muss ein vielfaches der Anzahl der Bienen in einem Block sein!");
        if (m < 0 || m >= n)
            throw new IllegalArgumentException("Die Anzahl der Felder, die (weiter) untersucht werden muss mindestens 0 und < n sein!");
        if (m % b != 0)
            throw new IllegalArgumentException("Die Anzahl der Felder, die (weiter) untersucht werden muss ein vielfaches der Anzahl der Bienen in einem Block sein!");
        if (e < 0 || e >= m)
            throw new IllegalArgumentException("Die Anzahl der exzellenten Felder muss mindestens 0 und < m sein!");
        if (e % b != 0)
            throw new IllegalArgumentException("Die Anzahl der der exzellenten Felder muss ein vielfaches der Anzahl der Bienen in einem Block sein!");
        if (p < 0)
            throw new IllegalArgumentException("Die Anzahl der für ein exzellentes Feld rekrutierten Bienen darf nicht kleiner 0 sein!");
        if (p % b != 0)
            throw new IllegalArgumentException("Die Anzahl der für ein exzellentes Feld rekrutierten Bienen muss ein vielfaches der Anzahl der Bienen in einem Block sein!");
        if (q < 0 || q >= p)
            throw new IllegalArgumentException("Die Anzahl der für ein anderes Feld rekrutierten Bienen muss mindestens 0 und < p sein!");
        if (q % b != 0)
            throw new IllegalArgumentException("Die Anzahl der für ein anderes Feld rekrutierten Bienen muss ein vielfaches der Anzahl der Bienen in einem Block sein!");
        if (s <= 0.0 || s > 1)
            throw new IllegalArgumentException("Die Größe der Felder relativ zum untersuchten Bereich muss größer 0 und <= 1 sein!");
        if (r <= 0)
            throw new IllegalArgumentException("Die Anzahl der am Ende zurückzugebenden besten gefundenen Stellen muss größer 0 sein!");
    }
}