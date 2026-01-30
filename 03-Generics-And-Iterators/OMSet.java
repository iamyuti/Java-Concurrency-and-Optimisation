/**
 * Dies ist ein Container für Elemente vom Typ E.
 * Der Container ist geordnet (implementiert Ordered<E, Boolean>).
 * Der Container ist modifizierbar (implementiert Modifiable<E, OMSet<E>>).
 * Invariante: Alle Ordnungsbeziehungen müssen zyklenfrei sein.
 * @param <E> Der Typ der Elemente, die gespeichert werden.
 */
public class OMSet<E> implements Ordered<E, Boolean>, Modifiable<E, OMSet<E>> {

    // --- INNERE KLASSEN ---

    /**
     * Klasse für einen Knoten im Container.
     * Ein Knoten enthält ein Element.
     * Alle Knoten sind über nextElement in einer verketteten Liste verkettet,
     * sodass alle Elemente im Set gefunden werden können.
     * Die Ordnung in der verketteten Liste beschreibt NICHT die Ordnungsbeziehung der Elemente.
     * Über eine Kante (Edge) ist ein Knoten mit seinen Nachfolgern entsprechend
     * der Ordnungsbeziehung verbunden.
     *
     * @param <E> Elementtyp des Sets
     */
    private static final class Node<E> {

        /**
         * Element des Knotens
         */
        private final E element;

        /**
         * Nächstes Element in der Liste (nicht Ordnungsbeziehung)
         */
        private final Node<E> nextElement;

        /**
         * Kopf der Liste aller Nachfolger (Kanten)
         */
        private Edge<E> succ;

        /**
         * Markierung für die Suche eines Knoten (BFS/DFS)
         */
        private int mark;

        /**
         * Erzeugt einen Knoten.<br>
         * Vorbedingungen: element darf nicht null sein.<br>
         * Nachbedingung: Knoten wurde erzeugt, enthält Element und zeigt auf nächsten Knoten.
         *
         * @param element Element, das der Knoten enthalten soll
         * @param nextElement nächster Knoten
         * @throws IllegalArgumentException wenn element null ist.
         */
        private Node(E element, Node<E> nextElement) {
            if (element == null) {
                throw new IllegalArgumentException("Element darf nicht null sein!");
            }
            this.element = element;
            this.nextElement = nextElement;
        }
    }

    /**
     * Kante zu einem Nachfolger in der Ordnung.
     * Jede Kante zeigt auf einen Knoten sowie die nächste Kante in der Ordnungsbeziehung.
     *
     * @param <E> Elementtyp des Sets
     */
    private static final class Edge<E> {

        /**
         * Knoten, auf den die Kante zeigt
         */
        private final Node<E> node;

        /**
         * Nächste Kante
         */
        private final Edge<E> nextEdge;

        /**
         * Erzeugt eine neue Kante.<br>
         * Vorbedingung: node darf nicht null sein.<br>
         * Nachbedingung: Kante erzeugt, zeigt auf Knoten sowie Folgekante.
         *
         * @param node Knoten, auf den die Kante zeigt
         * @param nextEdge Nächste Kante in der Ordnungsbeziehung
         * @throws IllegalArgumentException wenn node null ist
         */
        private Edge(Node<E> node, Edge<E> nextEdge) {
            if (node == null) {
                throw new IllegalArgumentException("Knoten darf nicht null sein!");
            }
            this.node = node;
            this.nextEdge = nextEdge;
        }
    }

    // --- VARIABLEN ---

    /**
     * Startknoten der Elementliste
     */
    private Node<E> first;

    /**
     * Größe der Liste
     */
    private int size;

    /**
     * Globaler Suchlauf-Zähler, d.h. wird in jeder Suche hochgesetzt, wenn besucht wurde.
     * Server-kontrollierter History-Constraint: dieser Zähler kann sich nur erhöhen.
     */
    private int currentMark;

    // --- KONSTRUKTOR ---

    /**
     * Konstruktor: Erstellt ein neues OMSet.<br>
     * Nachbedingung: Objekt ist initialisiert.
     */
    public OMSet() {
        this.first = null;
        this.size = 0;
        this.currentMark = 0;
    }

    // --- HILFSMETHODEN ---

    /**
     * Findet ein Element in der Liste.<br>
     * Vorbedingung: element != null.<br>
     * Nachbedingung: Gibt den Knoten zum Element zurück
     *                oder null, wenn es nicht in der Liste enthalten ist.
     *
     * @param element Das zu findende Element.
     * @return passenden Knoten oder null, wenn nicht enthalten.
     * @throws IllegalArgumentException wenn element null ist.
     */
    private Node<E> findNode(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element darf nicht null sein!");
        }
        Node<E> cur = first;
        while (cur != null) {
            if (cur.element == element) { // Identität zählt: Elemente gleich, wenn identisch
                return cur;
            }
            cur = cur.nextElement;
        }
        return null;
    }

    /**
     * Fügt ein Element vorne in die Liste ein, sofern es noch nicht vorhanden ist.<br>
     * Vorbedingung: element != null.<br>
     * Nachbedingung: Gibt den Knoten zum Element zurück.
     *
     * @param element das zu findende oder einzufügende Element.
     * @return den Knoten zum Element.
     * @throws IllegalArgumentException wenn element null ist.
     */
    private Node<E> findOrAdd(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element darf nicht null sein!");
        }
        Node<E> node = findNode(element);
        if (node != null) {
            return node;
        }
        // Neues Element vorne einfügen
        first = new Node<>(element, first);
        size++;
        return first;
    }

    /**
     * Prüft, ob ein Knoten von einem anderen aus über Kanten erreichbar ist.
     * D.h. ob zwei Knoten in einer gerichteten Ordnungsbeziehung stehen.
     * Nutzt dafür Depth First Search.<br>
     * Vorbedingung: from & to != null.<br>
     * Nachbedingung: Es wird zurückgegeben ob to von from aus über Kanten erreichbar ist.
     *
     * @param from Knoten ab dem gesucht wird.
     * @param to Knoten, nach dem gesucht wird.
     * @return true, wenn die Knoten über Kanten verbunden sind.
     * @throws IllegalArgumentException wenn from oder to == null
     */
    private boolean reachable( Node<E> from, Node<E> to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Knoten dürfen nicht null sein!");
        }
        currentMark++;
        // Gefunden
        if (from == to) {
            return true;
        }
        // Schon besucht
        if (from.mark == currentMark) {
            return false;
        }
        from.mark = currentMark;

        // Alle Kanten durchsuchen
        Edge<E> edge = from.succ;
        while (edge != null) {
            if (reachable(edge.node, to)) {
                return true;
            }
            edge = edge.nextEdge;
        }
        return false;
    }

    // --- METHODEN ---

    /**
     * Gibt die Inhalte des Containers samt Ordnungsbeziehungen in kompakter Form aus.<br>
     * Nachbedingung: Verständliche String-Darstellung des Containers erzeugt.
     *
     * @return Darstellung des Containers als Text.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int maxLineLength = 80;
        int currentLength = 0;

        sb.append("Set - Anzahl der Elemente: ").append(size).append("\nElemente:\n");

        Node<E> n = first;

        boolean hasElement = false;
        // Elemente
        while (n != null) {
            String text = n.element + ", ";
            hasElement = true;


            // Zeilenumbruch falls nötig
            if (currentLength + text.length() > maxLineLength) {
                sb.append("\n");
                currentLength = 0;
            }
            currentLength += text.length();
            sb.append(text);


            n = n.nextElement;
        }

        // letztes ", " entfernen, falls es Elemente gab
        if (hasElement) {
            sb.setLength(sb.length() - 2);
        } else {
            sb.append("Keine.");
        }

        sb.append("\nOrdnungsbeziehungen:\n");
        currentLength = 0;

        n = first;

        boolean hasEdge = false;
        boolean hasLine = false;
        // Kanten
        while (n != null) {
            Edge<E> edge = n.succ;
            while (edge != null) {
                String text = n.element + " -> " + edge.node.element + ", ";
                hasEdge = true;
                sb.append(text);
                // Zeilenumbruch falls nötig
                if (currentLength + text.length() > maxLineLength) {
                    hasLine = true;
                    sb.append("\n");
                    currentLength = 0;
                }

                currentLength += text.length();

                edge = edge.nextEdge;
            }
            n = n.nextElement;
        }
        if (hasEdge) {
            sb.setLength(sb.length() - 2);
            if (hasLine) {
                sb.setLength(sb.length() - 1);
            }
        } else {
            sb.append("Keine.");
        }
        return sb.toString();
    }

    /**
     * Die Methode gibt null oder true vom Typ Boolean zurück,
     * wenn x in der durch this bestimmten Ordnung vor y kommt, andernfalls null.<br>
     * Vorbedingung: x und y not null und E ermöglicht eine Ordnung.<br>
     * Nachbedingung: this, x und y bleiben unverändert.
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @return true, sofern x in der Ordnung vor y kommt (sonst null).
     * Das Ergebnis hängt nur von Werten in this, x und y ab.
     */
    @Override
    public Boolean before(E x, E y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("x und y dürfen nicht null sein!");
        }

        // Bei Gleichheit wird null zurückgegeben, da x nicht vor y liegt
        if (x == y) {
            return null;
        }

        // Knoten finden
        Node<E> nx = findNode(x);
        Node<E> ny = findNode(y);

        // Wenn einer der Knoten nicht enthalten, dann auch keine Ordnungsbeziehung
        if (nx == null || ny == null) {
            return null;
        }

        // Prüfen, ob x überhaupt vor y kommt
        if (!reachable(nx, ny)) {
            return null;
        }

        // Wenn x vor y kommt
        return true;
    }

    /**
     * Ein Aufruf ändert this so ab, dass nach Ausführung x in der Ordnung vor y steht.
     * Das heißt das before(x, y) ein Ergebnis ungleich null liefert.<br>
     * Verlangt, dass beide Parameter schon im Container sind und die gewünschte Ordnungsbeziehung möglich ist;
     * andernfalls wird eine IllegalArgumentException ausgelöst.
     * <p>
     * Vorbedingungen:<br>
     * - x und y sind nicht identisch.<br>
     * - x & y sind schon im Container.<br>
     * – this.before(y, x) gibt null zurück (Ordnungsbeziehung besteht nicht schon andersherum).<br>
     * Nachbedingungen:<br>
     * - Schon enthaltene Objekte werden nicht noch einmal eingefügt.<br>
     * - Besteht zwischen den beiden Parametern im Container noch keine Ordnungsbeziehung,
     *   wird eine solche Beziehung hergestellt.<br>
     * - before(x, y) liefert ein Ergebnis ungleich null.<br>
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @throws IllegalArgumentException In folgenden Fällen:<br>
     *                                  – x und y sind identisch.<br>
     *                                  - x und/oder y sind nicht im Container enthalten.<br>
     *                                  – this.before(y, x) gibt einen Wert ungleich null zurück (Würde Zyklus erzeugen).
     */
    @Override
    public void setBefore(E x, E y) {
        if (x == y) {
            throw new IllegalArgumentException("x und y dürfen nicht identisch sein");
        }
        if (this.before(y,x) != null) {
            throw new IllegalArgumentException("Ordnungsbeziehung besteht bereits anders herum (Zyklus): " + y + " -> " + x);
        }
        if (findNode(x) == null || findNode(y) == null ) {
            throw new IllegalArgumentException("Beide Elemente müssen im Set enthalten sein!");
        }

        // Knoten vorne in Liste einfügen, falls nicht vorhanden
        Node<E> nx = findNode(x);
        Node<E> ny = findNode(y);

        // Wenn x bereits vor y liegt, nichts tun (Beziehung existiert schon)
        if (reachable(nx, ny)) {
            return;
        }

        // neue Kante x -> y eintragen
        nx.succ = new Edge<>(ny, nx.succ);
    }

    /**
     * (aus Modifiable) gibt ein Objekt des gleichen Typs wie
     * this zurück, das gegebenenfalls um einen Eintrag erweitert ist.
     * Eine Erweiterung ist genau dann möglich, wenn der Parameter
     * von add noch nicht in this vorkommt.<br>
     * Vorbedingung: hinzuzufügendes Element e darf nicht null sein.<br>
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param e Das Objekt um das this erweitert wird.
     * @return Kopie von this, die dieselben Elemente und Ordnungsbeziehungen wie this enthält,
     *         erweitert um x, sofern x nicht vorher bereits enthalten war.
     *         This, wenn this nicht um x erweiterbar ist.
     * @throws IllegalArgumentException wenn e null ist.
     */
    @Override
    public OMSet<E> add(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Element darf nicht null sein!");
        }

        // Prüfen, ob Element schon enthalten ist
        if (findNode(e) != null) {
            return this; // Element schon enthalten -> unverändert zurückgeben (Identität)
        }

        // Neue Kopie erzeugen
        OMSet<E> copy = new OMSet<>();

        // 1) Alle Knoten kopieren
        Node<E> cur = this.first;
        while (cur != null) {
            copy.findOrAdd(cur.element);
            cur = cur.nextElement;
        }

        // 2) Neuelement hinzufügen
            copy.findOrAdd(e);

        // 3) Alle Kanten kopieren
        cur = this.first;
        while (cur != null) {
            Node<E> fromCopy = copy.findNode(cur.element);

            Edge<E> edge = cur.succ;
            while (edge != null) {
                Node<E> toCopy = copy.findNode(edge.node.element);
                fromCopy.succ = new Edge<>(toCopy, fromCopy.succ);
                edge = edge.nextEdge;
            }
            cur = cur.nextElement;
        }
        return copy;
    }

    /**
     * (aus Modifiable) gibt ein Objekt des gleichen Typs wie
     * this zurück, das gegebenenfalls einen Eintrag weniger enthält.
     * Der Parameter von subtract wird genau dann entfernt,
     * wenn er in this enthalten ist; in diesem Fall müssen auch alle
     * Ordnungsbeziehungen auf diesem Element entfernt werden.<br>
     * Vorbedingung: hinzuzufügendes Element e darf nicht null sein.<br>
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param e Das Objekt, das auch this entfernt wird.
     * @return Kopie von this, die dieselben Elemente und Ordnungsbeziehungen wie this enthält,
     *         außer das Element x und Ordnungsbeziehungen this betreffend, sofern x enthalten war.
     *         This, wenn x nicht enthalten war.
     * @throws IllegalArgumentException wenn e null ist.
     */
    @Override
    public OMSet<E> subtract(E e) {
        if (e == null) {
            throw new IllegalArgumentException("Element darf nicht null sein!");
        }

        Node<E> removeNode = findNode(e);
        if (removeNode == null) {
            return this; // Element nicht enthalten -> unverändert zurückgeben (Identität)
        }

        OMSet<E> copy = new OMSet<>();

        // 1) Alle Knoten kopieren, außer e
        Node<E> cur = this.first;
        while (cur != null) {
            if (cur.element != e) {
                copy.findOrAdd(cur.element);
            }
            cur = cur.nextElement;
        }

        // 2) Kanten kopieren, aber alle Kanten, in denen e vorkommt, weglassen
        cur = this.first;
        while (cur != null) {
            if (cur.element != e) {
                Node<E> fromCopy = copy.findNode(cur.element);
                Edge<E> edge = cur.succ;
                while (edge != null) {
                    if (edge.node.element != e) {
                        Node<E> toCopy = copy.findNode(edge.node.element);
                        fromCopy.succ = new Edge<>(toCopy, fromCopy.succ);
                    }
                    edge = edge.nextEdge;
                }
            }
            cur = cur.nextElement;
        }
        return copy;
    }
}
