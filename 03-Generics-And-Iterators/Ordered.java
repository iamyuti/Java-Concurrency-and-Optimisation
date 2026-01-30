/**
 * Dies ist ein generisches Interface für geordnete Objekte.
 * @param <E> Der Typ der Elemente, die geordnet werden.
 * @param <R> Der Typ des Elements, in dem die Elemente vom Typ E geordnet sind.
 */
public interface Ordered<E, R> {

    /**
     * Gibt etwas vom Typ R zurück, wenn x in der durch this bestimmten Ordnung
     * vor y kommt, andernfalls null.
     * Vorbedingung: x und y not null und E ermöglicht eine Ordnung.<br>
     * Nachbedingung: this, x und y bleiben unverändert.
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @return Das Ergebnis ist ungleich null, wenn x in der durch
     * this bestimmten Ordnung vor y kommt, andernfalls null.
     * Das Ergebnis hängt nur von Werten in this, x und y ab.
     */
    R before (E x, E y);

    /**
     * Ein Aufruf ändert this so ab, dass nach Ausführung x in der Ordnung vor y steht.
     * Eine Implementierung kann voraussetzen, dass x und y bestimmte Annahmen
     * hinsichtlich Vorkommen und Ordnung erfüllen und, wenn diese nicht gegeben sind,
     * eine IllegalArgumentException auslösen.<br>
     * Vorbedingung: x und y not null und E ermöglicht eine Ordnung.<br>
     * Nachbedingung: x steht in Ordnung vor y.
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     */
    void setBefore(E x, E y);
}
