import java.util.Iterator;

/**
 * Dies ist ein Container mit Einträgen des Typs E
 * auf der Basis des Interfaces OrdSet<E, Iterator<E>>.
 * Das zur Überprüfung erlaubter Ordnungsbeziehungen
 * verwendete c wird über den Konstruktor gesetzt.<br>
 * Von OrdSet geerbte Invarianten:<br>
 * - Alle Ordnungsbeziehungen müssen zyklenfrei sein.<br>
 * - Jedes Objekt von OrdSet ist mit einem Objekt c (oder null) verbunden,
 * sodass eine Ordnungsbeziehung zwischen zwei Einträgen x und y nur dann
 * eingeführt werden kann, wenn c gleich null ist (keinerlei Einschränkung) oder
 * c.before(x, y) ein Ergebnis ungleich null zurückgibt;
 * das sind erlaubte Ordnungsbeziehungen.
 * @param <E> Der Typ der Elemente, die gespeichert werden.
 */
public class ISet<E> implements OrdSet<E, Iterator<E>>{

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
         * Vorbedingung: element darf nicht null sein.<br>
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
        private Edge<E> nextEdge;

        /**
         * Erzeugt eine neue Kante.<br>
         * Vorbedingung: node darf nicht null sein.<br>
         * Nachbedingung: Kante erzeugt, zeigt auf Knoten sowie Folgekante
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
     * Dient der Überprüfung erlaubter Ordnungsbeziehungen
     */
    private Ordered<? super E, ?> c;

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
     * Konstruktor: Erstellt ein neues ISet.<br>
     * Nachbedingung: Objekt ist initialisiert.
     *
     * @param c Wird zur Überprüfung erlaubter Ordnungsbeziehungen verwendet (darf null sein).
     */
    public ISet(Ordered<? super E, ?> c) {
        this.c = c;
        this.first = null;
        this.size = 0;
        this.currentMark = 0;
    }

    // --- HILFSMETHODEN ---

    /**
     * Findet ein Element in der Liste.<br>
     * Vorbedingung: element != null.<br>
     * Nachbedingung: Gibt den Knoten zum Element zurück.
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
    private boolean reachable(Node<E> from, Node<E> to) {
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

        // Elemente
        boolean hasElement = false;
        for (E e : this) {
            String text = e.toString() + ", ";

            if (currentLength + text.length() > maxLineLength) {
                sb.append("\n");
                currentLength = 0;
            }
            sb.append(text);
            currentLength += text.length();
            hasElement = true;
        }

        // letztes ", " entfernen, falls es Elemente gab
        if (hasElement) {
            sb.setLength(sb.length() - 2);
        } else {
            sb.append("Keine.");
        }

        sb.append("\nOrdnungsbeziehungen:\n");
        currentLength = 0;

        Node<E> n = first;
        boolean hasEdge = false;
        boolean hasLine = false;
        // Kanten
        while (n != null) {
            Edge<E> edge = n.succ;
            while (edge != null) {
                String text = n.element + " -> " + edge.node.element + ", ";
                sb.append(text);
                if (currentLength + text.length() > maxLineLength) {
                    sb.append("\n");
                    hasLine = true;
                    currentLength = 0;
                }

                currentLength += text.length();
                hasEdge = true;

                edge = edge.nextEdge;
            }
            n = n.nextElement;
        }

        // letztes ", " nur entfernen, wenn es überhaupt Kanten gab
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
     * Die Methode gibt einen Iterator zurück, der über
     * alle Einträge iteriert, die in der Ordnung nach x und vor y stehen,
     * sofern x in der Ordnung vor y kommt (sonst null). Die Methode
     * remove ist im Iterator nicht implementiert.<br>
     * Vorbedingung: x und y not null und E ermöglicht eine Ordnung.<br>
     * Nachbedingung: this, x und y bleiben unverändert.
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @return Iterator, der über alle Einträge iteriert, die in der Ordnung nach x und vor y stehen,
     * sofern x in der Ordnung vor y kommt (sonst null).
     * Das Ergebnis hängt nur von Werten in this, x und y ab.
     * @throws IllegalArgumentException wenn x oder y null
     */
    @Override
    public Iterator<E> before(E x, E y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("x und y dürfen nicht null sein!");
        }

        // Bei Gleichheit wird null zurückgegeben, da x nicht vor y liegt
        if (x == y) {
            return null;
        }

        // Knoten zu x und y suchen
        final Node<E> nx = findNode(x);
        final Node<E> ny = findNode(y);

        // Wenn x oder y nicht im Set: keine Ordnungsbeziehung -> null
        if (nx == null || ny == null) {
            return null;
        }

        // Prüfen, ob x überhaupt vor y kommt; wenn nicht -> null
        if (!reachable(nx, ny)) {
            return null;
        }

        // Jetzt Iterator über alle Elemente, die strikt zwischen x und y liegen:
        // d.h. nx < n < ny
        return new Iterator<>() {

            // Durchläuft die verkettete Liste aller Nodes
            private Node<E> current = first;

            // Nächstes Element, das die Bedingung erfüllt
            private E nextElement = advance();

            private E advance() {
                while (current != null) {
                    Node<E> n = current;
                    current = current.nextElement;

                    // x und y selbst sind NICHT "zwischen" x und y
                    if (n == nx || n == ny) {
                        continue;
                    }

                    // Bedingung für "zwischen x und y":
                    // x < n UND n < y
                    if (reachable(nx, n) && reachable(n, ny)) {
                        return n.element;
                    }
                }
                return null;
            }

            @Override
            public boolean hasNext() {
                return nextElement != null;
            }

            @Override
            public E next() {
                if (nextElement == null) {
                    throw new java.util.NoSuchElementException();
                }
                E result = nextElement;
                nextElement = advance();
                return result;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove nicht unterstützt");
            }
        };
    }

    /**
     * Ein Aufruf ändert this so ab, dass nach Ausführung x in der Ordnung vor y steht.
     * Das heißt das before(x, y) ein Ergebnis ungleich null liefert.<br>
     * <p>
     * Vorbedingungen:<br>
     * – x und y sind nicht identisch.<br>
     * – Falls c!=null, muss c.before(x, y) einen Rückgabewert != null haben (Ordnungsbeziehung erlaubt).<br>
     * – this.before(y, x) gibt null zurück (Ordnungsbeziehung besteht nicht schon andersherum).<br>
     * Nachbedingungen:<br>
     * - Die Parameter werden im Container eingetragen, falls sie noch nicht
     *   mit gleicher Identität vorkommen.<br>
     * - Schon enthaltene Objekte werden nicht noch einmal eingefügt.<br>
     * - Besteht zwischen den beiden Parametern im Container noch keine Ordnungsbeziehung,
     * wird eine solche Beziehung hergestellt.
     * - before(x, y) liefert ein Ergebnis ungleich null.<br>
     *
     * @param x Der erste Wert
     * @param y Der zweite Wert
     * @throws IllegalArgumentException In folgenden Fällen:<br>
     *                                  – x und y sind identisch,<br>
     *                                  – c!=null und c.before(x,y) gibt null zurück,<br>
     *                                  – this.before(y, x) gibt einen Wert ungleich null zurück (Würde Zyklus erzeugen).
     */
    @Override
    public void setBefore(E x, E y) {
        if (x == y) {
            throw new IllegalArgumentException("x und y dürfen nicht identisch sein");
        }
        if (c != null && c.before(x, y) == null) {
            throw new IllegalArgumentException("Ordnungsbeziehung laut c nicht erlaubt: " + x + " -> " + y);
        }
        if (this.before(y,x) != null) {
            throw new IllegalArgumentException("Ordnungsbeziehung besteht bereits anders herum (Zyklus): " + y + " -> " + x);
        }

        // Knoten vorne in Liste einfügen, falls nicht vorhanden
        Node<E> nx = findOrAdd(x);
        Node<E> ny = findOrAdd(y);

        // Wenn x bereits vor y liegt, nichts tun (Beziehung existiert schon)
        if (reachable(nx, ny)) {
            return;
        }

        // neue Kante x -> y eintragen
        nx.succ = new Edge<>(ny, nx.succ);
    }

    /**
     * Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird, welches ein Untertyp von Ordered<? super E, ?> ist.
     * Dabei werden alle schon bestehenden Ordnungsbeziehungen mit dem neuen c überprüft.<br>
     * Nachbedingung: c ist aktualisiert (sofern keine Exception ausgelöst wird)
     *
     * @param newC Das neue Prüfobjekt
     * @throws IllegalArgumentException Scheitert eine Überprüfung, dann bleibt c unverändert und eine
     *                                  IllegalArgumentException wird ausgelöst.
     */
    @Override
    public void check(Ordered<? super E, ?> newC) {
        // Prüfen, ob alle bestehenden Kanten mit newC erlaubt wären
        if (newC != null) {
            // Je Knoten
            Node<E> from = first;
            while (from != null) {
                // Alle Kanten
                Edge<E> edge = from.succ;
                while (edge != null) {
                    E x = from.element;
                    E y = edge.node.element;

                    // neue Ordnung erlaubt x -> y?
                    if (newC.before(x, y) == null) {
                        // c bleibt unverändert
                        throw new IllegalArgumentException(
                                "Bestehende Ordnungsbeziehung (" + x + " -> " + y +
                                        ") ist mit neuem c nicht erlaubt");
                    }
                    edge = edge.nextEdge;
                }
                from = from.nextElement;
            }
        }

        // Wenn alles ok ist -> c aktualisieren
        this.c = newC;
    }

    /**
     * Setzt das Objekt c (kann auch null sein), das für künftige Prüfungen
     * erlaubter Ordnungsbeziehungen verwendet wird, welches ein Untertyp von Ordered<? super E, ?> ist.
     * Dabei werden alle schon bestehenden Ordnungsbeziehungen mit dem neuen c überprüft.<br>
     * Nachbedingungen:<br>
     * - c ist aktualisiert.<br>
     * - Alle für das neue c nicht mehr erlaubten Ordnungsbeziehungen sind entfernt.
     *
     * @param newC Das neue Prüfobjekt
     */
    @Override
    public void checkForced(Ordered<? super E, ?> newC) {
        // c auf jeden Fall setzen
        this.c = newC;

        // Wenn kein Constraint-Objekt: alles erlaubt, nichts zu tun
        if (newC == null) {
            return;
        }

        // Alle Kanten durchgehen und verbotene entfernen
        Node<E> from = first;
        // JeKnoten
        while (from != null) {
            Edge<E> prev = null;
            Edge<E> edge = from.succ;
            // Alle Kanten
            while (edge != null) {
                E x = from.element;
                E y = edge.node.element;
                // Beziehung erlaubt?
                boolean allowed = newC.before(x, y) != null;
                if (!allowed) {
                    // Nein: Kante entfernen
                    if (prev == null) {
                        // erste Kante in der Liste
                        from.succ = edge.nextEdge;
                    } else {
                        prev.nextEdge = edge.nextEdge;
                    }
                    // weiter zur nächsten Kante (ohne prev zu verschieben)
                    edge = (prev == null) ? from.succ : prev.nextEdge;
                } else {
                    // Ja: Kante bleibt
                    prev = edge;
                    edge = edge.nextEdge;
                }
            }
            from = from.nextElement;
        }
    }

    /**
     * Gibt einen Iterator zurück, der in beliebiger Reihenfolge
     * über alle Einträge im Container läuft.
     * Die Methode remove ist im Iterator nicht implementiert.<br>
     * Vorbedingung: Initialisiertes Objekt.
     *
     * @return Iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            /**
             * Startelement des Iterators = Startknoten der Liste
             */
            private Node<E> current = first;

            /**
             * Gibt zurück, ob die Iteration über ein weiteres Element verfügt,
             * d.h. next() ein weiteres Element liefern würde.<br>
             * Nachbedingung: true, wenn next() ein weiteres Element liefert.
             *
             * @return Wahrheitswert, ob die Iteration über ein weiteres Element verfügt.
             */
            @Override
            public boolean hasNext() {
                return current != null;
            }

            /**
             * Gibt das nächste Element der Iteration zurück.<br>
             * Client-kontrollierter History-Constraint/ Vorbedingung:
             * hasNext() muss vor jedem Aufruf von next() erfolgreich aufgerufen worden sein.
             *
             * @return nächstes Element der Iteration
             * @throws java.util.NoSuchElementException wenn es kein weiteres Element in der Iteration gibt.
             */
            @Override
            public E next() {
                if (current == null) {
                    throw new java.util.NoSuchElementException();
                }
                E e = current.element;
                current = current.nextElement;
                return e;
            }

            /**
             * Optionale Operation - nicht implementiert:
             * Entfernt das zuletzt durch diesen Iterator zurückgegebene Element aus der zugrundeliegenden Collection.
             * Falls die zugrundeliegende Collection während der Iteration strukturell verändert
             * wurde und diese Veränderung nicht durch diese Methode stattgefunden hat,
             * kann der Iterator ein unbestimmtes Verhalten zeigen.
             * Client-kontrollierter History Constraint:
             * Diese Methode kann nur einmal pro Aufruf von next() aufgerufen werden.
             * Vorbedingung: Zugrundeliegende Collection darf während Iteration nicht verändert worden sein.
             * Nachbedingung: Element aus Collection entfernt.
             *
             * @throws UnsupportedOperationException da nicht implementiert.
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("remove nicht unterstützt");
            }
        };
    }

    /**
     * Diese Methode liefert die Größe (Anzahl der Elemente) des Containers zurück.<br>
     * Vorbedingung: Initialisiertes Objekt.<br>
     * Nachbedingung: Angabe der Anzahl der Elemente im Container.
     *
     * @return Größe des Containers.
     */
    @Override
    public Integer size() {
        return size;
    }
}
