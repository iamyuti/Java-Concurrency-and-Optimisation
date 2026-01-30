/**
 * Diese Klasse stellt eine Menge dar.
 * Die Elementtypen sind allgemein gehalten (Object).
 * Dies ergibt sich aus der homogenen Übersetzung von Set<T>, wobei die erste Schranke von T Object ist.
 * Intern ist die Menge als verkettete Liste realisiert.
 */
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "head ist genau dann null, wenn size gleich 0 ist.")
@Zusicherung(author="Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die Anzahl der von head aus erreichbaren Knoten ist gleich size.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Jedes Element kommt maximal einmal in der Menge vor.")
@Zusicherung(author="Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
        zusicherung = "Der Aufruf von contains, size oder get verändern die Menge nicht.")
@Zusicherung(author="Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
        zusicherung = "Nach add(element) gilt: size ist um 1 erhöht genau dann, wenn element vorher nicht enthalten war.")
@Hauptverantwortlicher("Christine")
public class Set {

    /**
     * Ein Knoten der Liste
     */
    @Hauptverantwortlicher("Christine")
    private static final class Node {

        /**
         * Element, das der Knoten enthält
         */
        @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
                zusicherung = "element ist niemals null.")
        final Object element;

        /**
         * Der nächste Knoten in der Liste
         */
        Node next;

        /**
         * Erstellt einen neuen Knoten
         * @param element Element, dass der Knoten enthält
         * @param next Folgeknoten dieses Knotens
         */
        @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
                zusicherung = "element != null")
        @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
                zusicherung = "Knoten ist erstellt und enthält Element sowie Verweis auf nächsten Knoten.")
        @Hauptverantwortlicher("Christine")
        public Node(Object element, Node next) {
            this.element = element;
            this.next = next;
        }
    }

    /**
     * Kopf der intern geführten Liste
     */
    private Node head;

    /**
     * Größe der Menge, d.h. Anzahl der Elemente im Set
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "size >= 0.")
    private int size;

    /**
     * Konstruktor für ein neues Set.
     */
    @Zusicherung(author = "Felix", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "head ist mit null und size mit 0 initialisiert.")
    @Hauptverantwortlicher("Felix")
    public Set(){
        head = null;
        size = 0;
    }

    /**
     * Fügt ein Element zur Menge hinzu.
     * Ist das Element schon in der Menge enthalten, passiert nichts.
     * @param element Einzufügendes Element
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "element != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "element ist in das Set eingefügt, sofern es noch nicht enthalten war.")
    @Hauptverantwortlicher("Christine")
    public void add(Object element) {
        if (contains(element))
            return;
        head = new Node(element, head);
        size++;
    }

    /**
     * Gibt zurück, ob ein Element in der Menge enthalten ist.
     * @param element zu prüfendes Element
     * @return true, wenn das Element in der Menge enthalten ist, sonst false
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "element != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück, wenn das Element in der Menge enthalten ist, sonst false.")
    @Hauptverantwortlicher("Christine")
    public boolean contains(Object element) {
        for (Node n = head; n != null; n = n.next) {
            if (n.element == element)
                return true;
        }
        return false;
    }

    /**
     * Gibt die aktuelle Größe der Menge, d.h. die Anzahl der enthaltenen Elemente zurück.
     * @return Anzahl der in der Menge enthaltenen Elemente
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der in der Menge enthaltenen Elemente zurück.")
    @Hauptverantwortlicher("Christine")
    public int size() {
        return size;
    }

    /**
     * Gibt das Element zurück, das an dem übergebenen Index in der Liste steht.
     * @param index Index des Elements in der Liste, das zurückgegeben wird.
     * @return Element an der übergebenen Indexposition oder null, wenn es kein Element an dem Index gibt.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "index >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "index muss < dem Ergebnis von size() sein.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Element an übergebener Indexposition zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Bei einem ungültigen Index (<0 oder >= size), wird null zurückgegeben.")
    @Hauptverantwortlicher("Christine")
    public Object get(int index) {
        if (index < 0)
            return null;
        Node n = head;
        int i = 0;
        while (i < index && n != null) {
            n = n.next;
            i++;
        }
        return n == null ? null : n.element;
    }

    /**
     * Löscht ein Element aus der Menge
     * @param element zu löschendes Element
     * @return true, wenn das Element gelöscht wurde
     *         false, wenn das Element nicht vorhanden war
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "element != null.")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Liefert true zurück, wenn element aus der Menge gelöscht wurde, false" +
                    "wenn es nicht in der Menge war.")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "element ist nicht in der Menge enthalten.")
    @Hauptverantwortlicher("Yutaka")
    public boolean remove(Object element) {
        // leere Menge
        if (head == null)
            return false;

        // erstes Element löschen
        if (head.element == element) {
            head = head.next;
            size--;
            return true;
        }

        // sonst durchsuchen
        Node prev = head;
        Node curr = head.next;

        while (curr != null) {
            if (curr.element == element) {
                prev.next = curr.next;
                size--;
                return true;
            }
            prev = curr;
            curr = curr.next;
        }

        return false;
    }
}

