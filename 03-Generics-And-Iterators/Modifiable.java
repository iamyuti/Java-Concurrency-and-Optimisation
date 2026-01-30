/**
 * Dies ist ein generisches Interface für modifizierbare Objekte.
 * @param <X> Der Typ der Elemente, die das Objekt vom Typ T verändern.
 * @param <T> Der Typ des Elements, das verändert wird.
 */
public interface Modifiable<X, T extends Modifiable<X, T>> {

    /**
     * Gibt etwas zurück, das this um x erweitert.<br>
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param x Das Objekt um das this erweitert wird.
     * @return Gibt etwas zurück, das this um x erweitert. Das Ergebnis ist identisch zu this,
     * wenn this nicht um x erweiterbar ist.
     */
    T add(X x);

    /**
     * Gibt etwas entsprechend this zurück, aus dem x entfernt ist.
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param x Das Objekt um das this reduziert wird.
     * @return Gibt etwas entsprechend this zurück, aus dem x
     * entfernt ist. Das Ergebnis ist identisch zu this,
     * wenn x nicht aus this entfernt werden kann.
     */
    T subtract (X x);
}
