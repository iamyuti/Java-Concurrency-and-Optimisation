import java.util.Iterator;

/**
 * Dies ist ein Container mit Einträgen des Typs E
 * auf der Basis des Interfaces OrdSet <E, OMSet<E>>.
 * Das zur Überprüfung erlaubter Ordnungsbeziehungen
 * verwendete c wird über den Konstruktor gesetzt.<br>
 * Von OrdSet geerbte Invarianten:<br>
 * - Alle Ordnungsbeziehungen müssen zyklenfrei sein.<br>
 * - Jedes Objekt von OrdSet ist mit einem Objekt c (oder null) verbunden,
 * sodass eine Ordnungsbeziehung zwischen zwei Einträgen x und y nur dann
 * eingeführt werden kann, wenn c gleich null ist (keinerlei Einschränkung) oder
 * c.before(x, y) ein Ergebnis ungleich null zurückgibt;
 * das sind erlaubte Ordnungsbeziehungen.
 *
 * @param <E> Der Typ der Elemente, die gespeichert werden.
 * @param <X> Der Typ der Elemente, die das Objekt vom Typ E verändern.
 */
public class MSet<E extends Modifiable<X,E>, X> extends OSet<E>{

    /**
     * Konstruktor: Erstellt ein neues MSet.<br>
     * Nachbedingung: Objekt ist initialisiert.
     *
     * @param c Wird zur Überprüfung erlaubter Ordnungsbeziehungen verwendet (darf null sein).
     */
    public MSet(Ordered<? super E, ?> c) {
        super(c);
    }

    // --- METHODEN ---

    /**
     * Führt für jedes vom Iterator der Methode iterator() zurückgegebene e
     * folgende Operation aus: this.setBefore(e.add(x), e);<br>
     * Vorbedingung: x nicht null.<br>
     * Nachbedingung: Für jedes vom Iterator zurückgegebene Element befindet sich ein neues Element
     * in diesem Set, das um x erweitert ist. Außerdem gibt es eine Ordnungsbeziehung vom ursprünglichen
     * Element zum erweiterten Element.
     *
     * @param x Das Element, das hinzugefügt wird
     * @throws IllegalArgumentException Falls x null ist.
     */
    public void plus (X x) {
        if (x == null) {
            throw new IllegalArgumentException("x darf nicht null sein.");
        }

        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E e = it.next();
            E added = e.add(x);          // E extends Modifiable<X,E>
            this.setBefore(added, e);    // Ordnungsbeziehung in diesem MSet herstellen
        }
    }

    /**
     * Führt für jedes vom Iterator der Methode iterator() zurückgegebene e
     * folgende Operation aus: this.setBefore(e.subtract(x), e);<br>
     * Vorbedingungen: x nicht null.<br>
     * Nachbedingung: Für jedes vom Iterator zurückgegebene Element befindet sich ein neues Element
     * in diesem Set, aus dem x entfernt ist. Außerdem gibt es eine Ordnungsbeziehung vom ursprünglichen
     * Element zum verkleinerten Element.
     *
     * @param x Das Element, das entfernt wird
     * @throws IllegalArgumentException Falls x null ist.
     */
    public void minus (X x) {
        if (x == null) {
            throw new IllegalArgumentException("x darf nicht null sein.");
        }

        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            E e = it.next();
            E sub = e.subtract(x);       // E extends Modifiable<X,E>
            this.setBefore(sub, e);      // Ordnungsbeziehung in diesem MSet herstellen
        }
    }
}
