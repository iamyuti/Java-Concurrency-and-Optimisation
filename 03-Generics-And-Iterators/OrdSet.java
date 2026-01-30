import java.util.Iterator;

/**
 * Dies ist ein Container mit Einträgen des Typs E. Einträge sind
 * partiell geordnet, das heißt, zwischen je zwei Einträgen kann es
 * eine Ordnungsbeziehung geben, muss aber nicht.<br>
 *
 * Invarianten:<br>
 * - Alle Ordnungsbeziehungen müssen zyklenfrei sein.<br>
 * - Jedes Objekt von OrdSet ist mit einem Objekt c vom Typ Ordered (oder null) verbunden,
 * sodass eine Ordnungsbeziehung zwischen zwei Einträgen x und y nur dann
 * eingeführt werden kann, wenn c gleich null ist (keinerlei Einschränkung) oder
 * c.before(x, y) ein Ergebnis ungleich null zurückgibt;
 * das sind erlaubte Ordnungsbeziehungen.
 *
 * @param <E> Der Typ der Elemente, die gespeichert werden.
 * @param <R> Der Typ des Elements, in dem die Elemente vom Typ E geordnet sind.
 */
public interface OrdSet<E, R> extends Iterable<E>, Ordered<E, R> {

    /**
     * Ein Aufruf ändert this so ab, dass nach Ausführung x in der Ordnung vor y steht.
     * Das heißt das before(x, y) ein Ergebnis ungleich null liefert.<br>
     *
     * Vorbedingungen:<br>
     * – x und y sind nicht identisch.<br>
     * – Falls c!=null, muss c.before(x, y) einen Rückgabewert != null haben (Ordnungsbeziehung erlaubt).<br>
     * – this.before(y, x) gibt null zurück (Ordnungsbeziehung besteh nicht schon andersherum).<br>
     * Nachbedingungen:<br>
     * - Die Parameter werden im Container eingetragen, falls sie noch nicht
     *   mit gleicher Identität vorkommen.<br>
     * - Schon enthaltene Objekte werden nicht noch einmal eingefügt.<br>
     * - Besteht zwischen den beiden Parametern im Container noch keine Ordnungsbeziehung,
     * wird eine solche Beziehung hergestellt.<br>
     * - before(x, y) liefert ein Ergebnis ungleich null.<br>
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @throws IllegalArgumentException In folgenden Fällen:<br>
     * – x und y sind identisch,<br>
     * – c!=null und c.before(x,y) gibt null zurück,<br>
     * – this.before(y, x) gibt einen Wert ungleich null zurück.
     */
    @Override
    void setBefore(E x, E y);

    /**
     * Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird, welches ein Untertyp von Ordered<? super E, ?> ist.
     * Dabei werden alle schon bestehenden Ordnungsbeziehungen mit dem neuen c überprüft.<br>
     * Nachbedingung: c ist aktualisiert (sofern keine Exception ausgelöst wird)
     *
     * @param c Das neue Prüfobjekt
     * @throws IllegalArgumentException Scheitert eine Überprüfung, dann bleibt c unverändert und eine
     * IllegalArgumentException wird ausgelöst.
     */
    void check(Ordered<? super E, ?> c);

    /**
     * Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird, welches ein Untertyp von Ordered<? super E, ?> ist.
     * Dabei werden alle schon bestehenden Ordnungsbeziehungen mit dem neuen c überprüft.<br>
     * Nachbedingungen:<br>
     * - c ist aktualisiert.<br>
     * - Alle für das neue c nicht mehr erlaubten Ordnungsbeziehungen sind entfernt.
     *
     * @param c Das neue Prüfobjekt
     */
    void checkForced(Ordered<? super E, ?> c);

    /**
     * Gibt einen Iterator zurück, der in beliebiger Reihenfolge
     * über alle Einträge im Container läuft.
     * Die Methode remove ist im Iterator nicht implementiert.<br>
     * Vorbedingung: Initialisiertes Objekt.
     *
     * @return Iterator
     */
    @Override
    Iterator<E> iterator();

    /**
     * Diese Methode liefert die Größe des Containers zurück.<br>
     * Vorbedingung: Initialisiertes Objekt.<br>
     * Nachbedingung: Angabe der Anzahl der Elemente im Container.
     *
     * @return Größe des Containers.
     */
    Integer size();
}
