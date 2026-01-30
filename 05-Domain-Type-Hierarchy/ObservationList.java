import java.time.LocalDateTime;
import java.util.*;

/**
 * Doppelt verkettete Liste für Observation-Objekte, sortiert nach getDateTime().<br>
 * - Beim Einfügen wird automatisch aufsteigend nach Zeitpunkt der Beobachtung sortiert.<br>
 * - Der Iterator kann vorwärts oder rückwärts laufen und ab einem bestimmten Element/Zeitpunkt starten.<br>
 * - Neue Einfügungen am Ende werden erkannt.<br>
 * - Entfernte Elemente werden mit einer flag markiert, unlinked (logisch entfernt) und vom Iterator sauber übersprungen.<p>
 *
 * Invarianten:
 * - Die Liste ist nach getDateTime() immer aufsteigend sortiert.<br>
 * - Jede Beobachtung kommt nur einmal in der Liste vor (keine Duplikate)
 * - head und tail sind genau dann null, wenn die Liste leer ist.
 * - Für jeden Knoten gilt: observation != null
 * - Entfernte Knoten (removed == true) bleiben im Datenbestand, werden aber von Iteratoren übersprungen
 *
 * History Constraints:
 * Server-kontrolliert:
 * - Entfernte Beobachtungen (removed == true) werden nie wieder verfügbar (Flag wieder nie zurückgesetzt)
 *
 * Untertypbeziehungen: ObservationList ist eine Hilfsklasse und daher kein Untertyp der anderen Interfaces oder Klassen.
 */
public class ObservationList implements Iterable<Observation> {

    // --- INNERE KLASSE : NODE ---

    /**
     * Ein Knoten der Liste, der als Element eine Beobachtung (Observation) enthält.
     *
     * Invariante:
     * - observation ist niemals null
     *
     * History Constraints:
     * - Sobald removed == true gesetzt wird, bleibt es true
     *
     * @param <Observation> Enthält Beobachtungen
     */
    static private final class Node<Observation> {

        /**
         * Beobachtung, die der Knoten enthält
         */
        Observation observation;

        /**
         * Nächste und vorherige Beobachtung in der Liste
         */
        Node<Observation> prev, next;

        /**
         * Flag, falls Knoten gelöscht wurde
         */
        boolean removed;

        /**
         * Konstruktor für einen Knoten
         * Vorbedingung: Beobachtung darf nicht null sein.
         * Nachbedingung: Knoten ist nicht leer, d.h. enthält eine Beobachtung.
         * @param observation die Beobachtung, die der Knoten enthält
         * @throws IllegalArgumentException wenn null als Observation übergeben wird.
         */
        Node(Observation observation) {
            if (observation == null) {
                throw new IllegalArgumentException("Observation darf nicht null sein!");
            }
            this.observation = observation;
        }
    }

    // --- VARIABLEN ---

    /**
     * Start- & Endknoten der Liste
     */
    private Node<Observation> head, tail;

    // --- HILFSMETHODEN ---

    /**
     * Vergleicht die Zeitpunkte zweier Beobachtungen
     * Vorbedingung:
     * - a und b dürfen nicht null sein
     * - a.getDateTime() und b.getDateTime() dürfen nicht null sein
     * Nachbedingung:
     * - gibt negativer Wert zurück, wenn a.getDateTime() < b.getDateTime()
     * - gibt positiver Wert zurück, wenn a.getDateTime() > b.getDateTime()
     * - gibt 0 zurück, wenn a.getDateTime() == b.getDateTime()
     * @param a Erste Beobachtung
     * @param b Zweite Beobachtung
     * @return negativ wenn a früher, 0 wenn gleich, positiv wenn a später
     * @throws IllegalArgumentException wenn null als eine Observation übergeben wird
     * @throws IllegalArgumentException wenn eine der Observationen null über getDateTime() zurückgibt
     */
    private static int cmpDateTime(Observation a, Observation b) {
        if (a == null || b == null) {
            throw new IllegalArgumentException("Observationen dürfen nicht null sein!");
        }
        if (a.getDateTime() == null || b.getDateTime() == null) {
            throw new IllegalArgumentException("Zeitpunkte dürfen nicht null sein!");
        }
        return a.getDateTime().compareTo(b.getDateTime());
    }

    /**
     * Findet den zu einer Beobachtung gehörenden Knoten in der Liste
     * Vorbedingung: als Beobachtung darf nicht null übergeben werden
     * Nachbedingung: zugehöriger Knoten wird zurückgegeben, oder null, wenn es keinen Knoten zu der Beobachtung gibt
     * @param o die zu findende Beobachtung
     * @return den zugehörigen Knoten oder null, wenn es keinen Knoten zu der Beobachtung gibt
     * @throws IllegalArgumentException wenn die Beobachtung null ist
     */
    private Node<Observation> findNodeByValue(Observation o) {
        if (o == null) {
            throw new IllegalArgumentException("Observation darf nicht null sein!");
        }

        for (Node<Observation> node = head; node != null; node = node.next) {
            if (Objects.equals(o, node.observation)) return node;
        }
        return null;
    }

    /**
     * Findet den ersten Knoten, dessen Beobachtung sich nach bzw. direkt zu dem angegebenen Zeitpunkt befindet
     * Nachbedingung: Es wird ein Knoten oder der Kopf der Liste zurückgegeben.
     * @param t der zu prüfende Zeitpunkt
     * @return den zugehörigen Knoten bzw. Kopf der Liste, falls kein Zeitpunkt (null) übergeben wir
     */
    private Node<Observation> firstNodeAtOrAfter(LocalDateTime t) {
        if (t == null) return head;
        Node<Observation> node = head;
        while (node != null && node.observation.getDateTime().isBefore(t)) node = node.next;
        return node;
    }

    /**
     * Findet den letzten Knoten, dessen Beobachtung sich vor bzw. direkt zu dem angegebenen Zeitpunkt befindet
     * Nachbedingung: Es wird ein Knoten oder der Kopf der Liste zurückgegeben.
     * @param t der zu prüfende Zeitpunkt
     * @return den zugehörigen Knoten bzw. Ende der Liste, falls kein Zeitpunkt (null) übergeben wird
     */
    private Node<Observation> lastNodeAtOrBefore(LocalDateTime t) {
        if (t == null) return tail;
        Node<Observation> node = tail;
        while (node != null && node.observation.getDateTime().isAfter(t)) node = node.prev;
        return node;
    }

    // --- METHODEN ---

    /**
     * Fügt eine Beobachtung in die Liste ein.
     * Das Einfügen erfolgt sortiert nach dem Zeitpunkt der Beobachtung.
     * Vorbedingung: o darf nicht null.
     *               o.getDateTime() darf nicht null sein
     * Nachbedingung:
     * - Falls o noch nicht in der Liste war: o ist eingefügt, sortiert nach getDateTime()
     * - Falls o bereits in der Liste war: Liste bleibt unverändert
     * - Sortierung bleibt erhalten
     * @param o Die einzufügende Beobachtung
     * @throws IllegalArgumentException wenn o oder o.getDateTime() null ist
     * @throws RuntimeException wenn durch einen Fehler der aktuelle Knoten null ist
     */
    public void add(Observation o) {
        if (o == null) {
            throw new IllegalArgumentException("Observation darf nicht null sein!");
        }
        if (o.getDateTime() == null) {
            throw new IllegalArgumentException("Zeitpunkt darf nicht null sein!");
        }
        if (this.contains(o)) return;

        // Neuen Knoten erstellen, Element ist die einzufügende Beobachtung
        Node<Observation> n = new Node<>(o);

        // Falls Liste leer: Neuer Knoten wird erster & letzter Knoten
        if (head == null) {
            head = tail = n;
            return;
        }

        // Fall 1: Einfügen am Ende (Bei Beobachtungen realistisch gesehen am häufigsten)
        if (cmpDateTime(tail.observation, o) <= 0) {
            tail.next = n;
            n.prev = tail;
            tail = n;
            return;
        }

        // Fall 2: Einfügen am Anfang (Bei Beobachtungen realistisch gesehen selten)
        if (cmpDateTime(o, head.observation) <= 0) {
            n.next = head;
            head.prev = n;
            head = n;
            return;
        }

        // Fall 3: Einfügen irgendwo dazwischen
        Node<Observation> cur = tail;
        while (cur != null && cmpDateTime(o, cur.observation) < 0) {
            cur = cur.prev;
        }
        // cur.observation <= o, also nach cur einfügen
        if (cur == null) {
            throw new RuntimeException("Aktueller Knoten ist null!");
        }
        Node<Observation> after = cur.next;
        cur.next = n;
        n.prev = cur;
        n.next = after;
        after.prev = n;
    }

    /**
     * Entfernt einen Knoten aus der Liste & markiert ihn als entfernt.
     * Die Referenzen des Knotens auf eben jenen Vorgänger & Nachfolger bleiben bestehen.
     * Dies ermöglicht weiterlaufen des Iterators, auch wenn das aktuelle Element entfernt wurde (s.u.).
     * Vorbedingung: Zu entfernender Knoten darf nicht null sein
     * Nachbedingung:
     * - Falls node in der Liste war: node.removed == true, node ist aus der Verkettung entfernt
     * - Falls node nicht in der Liste war: keine Änderung
     * - Die Referenzen node.prev und node.next bleiben erhalten.
     * @param node Zu entfernender Knoten
     * @throws IllegalArgumentException wenn der übergebene Knoten null ist.
     */
    private void unlink(Node<Observation> node) {
        if (node == null) {
            throw new IllegalArgumentException("Knoten darf nicht null sein!");
        }
        if (!this.contains(node.observation)) return;

        // Vorgänger- & Nachfolgerknoten
        Node<Observation> p = node.prev, n = node.next;

        // Neu-Verkettung
        if (p == null) head = n; else p.next = n;
        if (n == null) tail = p; else n.prev = p;

        // Knoten als gelöscht markieren
        node.removed = true;
    }

    /**
     * Entfernt eine Beobachtung aus der Liste.
     * Das bedeutet, dass der die Beobachtung enthaltene Knoten als entfernt markiert wird.
     * & der vorherige & folgende Knoten miteinander verkettet werden.
     * Vorbedingung: o darf nicht null sein
     * Nachbedingung:
     * - Falls o in der Liste war: o.removed == true, Node ist aus der Verkettung entfernt
     * - Falls o nicht in der Liste war: keine Änderung
     * @param o die zu entfernende Beobachtung
     * @throws IllegalArgumentException wenn die übergebene Observation null ist.
     */
    public void remove(Observation o) {
        if (o == null) {
            throw new IllegalArgumentException("Observation darf nicht null sein!");
        }
        Node<Observation> node = findNodeByValue(o);
        if (node != null ) {
            unlink(node);
        }
    }

    /**
     * Prüft, ob die Beobachtung in der Liste enthalten ist
     * Vorbedingung: Beobachtung ist nicht null
     * Nachbedingung:
     * - true, falls o in der Liste ist (unabhängig von removed-Flag)
     * - false, falls o nicht in der Liste ist
     * @param o zu überprüfende Beobachtung
     * @return true, wenn die Beobachtung in der Liste enthalten ist.
     * @throws IllegalArgumentException wenn die übergebene Observation null ist.
     */
    public boolean contains(Observation o) {
        if (o == null) {
            throw new IllegalArgumentException("Observation darf nicht null sein!");
        }
        return findNodeByValue(o) != null;
    }

    // =============================================== ITERATOREN =====================================================

    /**
     * Gibt einen Iterator für die ObservationList zurück.
     * Der Iterator iteriert aufsteigend (nach Zeitpunkten sortiert) durch die Liste.
     * Es wird dafür ein Objekt der Klasse ObservationIterator erzeugt.
     * Dieser reagiert dynamisch auf Änderungen in der Liste
     * Nachbedingung:
     * - Iterator über alle Beobachtungen
     * - Aufsteigend nach Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * @return dynamischer ObservationIterator
     */
    @Override
    public Iterator<Observation> iterator() {
        return new ObservationIterator(head, true);
    }

    /**
     * Gibt einen Iterator mit wählbarer Richtung (aufsteigend/absteigend) für die ObservationList zurück.
     * Es wird dafür ein Objekt der Klasse ObservationIterator erzeugt.
     * Dieser reagiert dynamisch auf Änderungen in der Liste
     * Nachbedingung:
     * - Iterator über alle Beobachtungen
     * - Wenn forward = true, dann aufsteigend nach Zeitpunkt sortiert
     *                = false, dann absteigend nah Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * @param forward true = Liste wird aufsteigend iteriert
     *                false = Liste wird absteigend iteriert
     * @return dynamischer ObservationIterator
     */
    public Iterator<Observation> iterator(boolean forward) {
        return new ObservationIterator(forward ? head : tail, forward);
    }

    /**
     * Gibt einen Iterator mit wählbarer Richtung (aufsteigend/absteigend) für die ObservationList.
     * Der Iterator iteriert über alle Elemente ab einer bestimmten Beobachtung (exklusive).
     * Zeitlich davor/danach (je nach Richtung) gelegene Beobachtungen werden nicht berücksichtigt.
     * Es wird dafür ein Objekt der Klasse ObservationIterator erzeugt.
     * Dieser reagiert dynamisch auf Änderungen in der Liste
     * Vorbedingung: angegebene Start-Beobachtung darf nicht null sein
     *               angegebene Start-Beobachtung muss in der Liste enthalten sein.
     * Nachbedingung:
     * - Iterator über alle Beobachtungen, ab der angegebenen Beobachtung (exklusive)
     * - Wenn forward = true, dann aufsteigend nach Zeitpunkt sortiert
     *                = false, dann absteigend nah Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * @param forward true = Liste wird aufsteigend iteriert
     *                false = Liste wird absteigend iteriert
     * @param start Beobachtung, ab der iteriert wird (nicht inklusive)
     * @return dynamischer ObservationIterator ab Start-Beobachtung
     * @throws IllegalArgumentException, wenn die Start-Beobachtung null oder nicht in der Liste enthalten ist
     */
    public Iterator<Observation> iteratorFrom(Observation start, boolean forward) {
        Node<Observation> n = findNodeByValue(start);
        if (n == null) {
            throw new IllegalArgumentException("Start-Observation darf nicht null sein!");
        }
        if (!this.contains(start)) {
            throw new IllegalArgumentException("Start-Observation nicht in der Liste!");
        }
        // Startknoten selbst überspringen
        n = forward ? n.next : n.prev;
        return new ObservationIterator(n, forward);
    }


    /**
     * Gibt einen Iterator mit wählbarer Richtung (aufsteigend/absteigend) für die ObservationList.
     * Der Iterator iteriert über alle Elemente ab einem bestimmten Zeitpunkt (inklusive).
     * Zeitlich davor/danach (je nach Richtung) gelegene Beobachtungen werden nicht berücksichtigt.
     * Es wird dafür ein Objekt der Klasse ObservationIterator erzeugt.
     * Vorbedingung: angegebener Zeitpunkt darf nicht null sein
     * Nachbedingung:
     * - Iterator über alle Beobachtungen, ab dem angegebenen Zeitpunkt (inklusive)
     * - Wenn forward = true, dann aufsteigend nach Zeitpunkt sortiert
     *                = false, dann absteigend nah Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * @param forward true = Liste wird aufsteigend iteriert
     *                false = Liste wird absteigend iteriert
     * @param t Zeitpunkt, ab dem iteriert wird (inklusive)
     * @return dynamischer ObservationIterator ab Start-Zeitpunkt
     * @throws IllegalArgumentException, wenn als Zeitpunkt null übergeben wird
     */
    public Iterator<Observation> iteratorFrom(LocalDateTime t, boolean forward) {
        if (t == null) {
            throw new IllegalArgumentException("Start-Observation darf nicht null sein!");
        }
        Node<Observation> n = forward ? firstNodeAtOrAfter(t) : lastNodeAtOrBefore(t);
        return new ObservationIterator(n, forward);
    }

    /**
     * Erstellt einen Iterator über alle Beobachtungen einer bestimmten Biene.<br>
     * Vorbedingungen:
     *  <li>Zeitpunkte, Beobachtungsliste & Bienen dürfen nicht null sein.</li>
     *  <li>From muss vor to sein.</li>
     * Vorbedingung: from, to & me nicht null, from <= to
     * Nachbedingung: Iterator über alle Beobachtungen desselben Individuums
     *      <li>falls {@code isAscending == true}, dann aufsteigend sortiert (älteste zuerst)</li>
     *      <li>falls {@code isAscending == false}, dann absteigend sortiert (neueste zuerst)</li>
     *      <li>nur Beobachtungen zwischen from und to</li>
     *      <li>enthält keine entfernten Beobachtungen</li>
     * @param from Zeitpunkt, ab dem iteriert wird
     * @param to Zeitpunkt, bis zu dem iteriert wird
     * @param isAscending ob die Iteration aufsteigend erfolgt
     * @param me Biene, auf die sich die Iteration bezieht
     * @return Iterator über alle Bobachtungen desselben Individuums
     * @throws IllegalArgumentException wenn Zeitpunkte, Beobachtungsliste oder Bienen null sind, oder from nach to liegt.
     */
    public Iterator<Bee> sameBee(LocalDateTime from, LocalDateTime to, boolean isAscending, Bee me) {
        return new SameBeeIterator(from, to, isAscending, this, me);
    }

    // === INNERE KLASSE: DYNAMISCHER ITERATOR ALLER BEOBACHTUNGEN ===

    /**
     * Diese Klasse repräsentiert einen Iterator einer ObservationList.
     * Der Iterator reagiert dynamisch auf Änderungen in der ObservationList,
     * erkennt also nach der Instanziierung gelöschte und hinzugefügte Beobachtungen.
     */
    private static class ObservationIterator implements Iterator<Observation> {

        // --- KONSTANTEN ---

        /**
         * Marker, ob der Iterator aufsteigend (true) oder absteigend (false) iteriert
         */
        private final boolean forward;

        // --- VARIABLEN ---

        /**
         * Startpunkt (Fixpunkt) der Iteration (für den ersten Schritt)
         */
        private final ObservationList.Node<Observation> startNode;

        /**
         * Zuletzt per next() gelieferter Knoten
         */
        private ObservationList.Node<Observation> lastReturned;

        /**
         * Der nächste zu liefernde Knoten (wird vor hasNext() gecacht).
         */
        private ObservationList.Node<Observation> nextNode;

        // --- KONSTRUKTOR ---

        /**
         * Erstellt einen ObservationIterator
         * Vorbedingung: Der übergebene Startknoten muss ein valider Knoten (nicht null) sein.
         * @param start Knoten, ab dem der Iterator iteriert (Startknoten)
         * @param forward true = Iterator läuft, ab Knoten vorwärts (aufsteigend durch die Liste)
         * @throws IllegalArgumentException wenn der übergebene Startknoten null ist
         */
        private ObservationIterator(ObservationList.Node<Observation> start, boolean forward) {
            if (start == null) {
                throw new IllegalArgumentException("Startknoten darf nicht null sein!");
            }
            this.forward = forward;
            // Falls der übergebene Knoten bereits als gelöscht markiert wurde, wird er übersprungen
            this.startNode = skipRemoved(start, forward);
            this.lastReturned = null;
            // Nächster Knoten wird erst in hasNext() berechnet (lazy)
            this.nextNode = null;
        }

        // --- METHODEN ---

        /**
         * Überspringt alle gelöschten Knoten ab dem übergebenen Knoten (inklusive)
         * Nachbedingung: Gibt nächsten nicht gelöschten Knoten zurück, oder null, wenn keiner vorhanden.
         * @param node Startknoten, ab dem iteriert bzw. übersprungen wird
         * @param forward true = es wird aufsteigend (nach Zeitpunkt) iteriert
         *                false = es wird absteigend (nach Zeitpunkt) iteriert
         * @return erster nicht-gelöschter Knoten nach übergebenem Knoten
         *         sonst null
         */
        private ObservationList.Node<Observation> skipRemoved(ObservationList.Node<Observation> node, boolean forward) {
            // So lange durchgehen, bis nicht gelöschter Knoten gefunden
            while (node != null && node.removed) {
                node = forward ? node.next : node.prev;
            }
            return node;
        }

        /**
         * Gibt zurück, ob die Iteration noch eine weitere Beobachtung hat.
         * Das bedeutet, wenn true zurückgegeben wird, wird next() eine weitere Beobachtung liefern
         * an Stelle einer Exception.
         * Vorbedingung: Alle Knoten in der Liste müssen Beobachtungen enthalten (node.observation != null)
         * Nachbedingung: Gibt true zurück, wenn next() eine Beobachtung liefern wird.
         * @return true, wenn es ein weiteres über next() abrufbare Beobachtung gibt.
         * @throws RuntimeException wenn ein Knoten in der Liste null als Beobachtungswert enthält
         */
        @Override
        public boolean hasNext() {
            // Falls per nextNode noch ein weiterer ungelöschte Knoten gecacht wurde, den nehmen
            if (nextNode != null && !nextNode.removed)
                return true;
            nextNode = null;

            // Ansonsten nächsten Kandidaten bestimmen
            ObservationList.Node<Observation> candidate;
            if (lastReturned == null) {
                // erster Schritt: ab Startknoten
                candidate = startNode;
            } else {
                // danach: relativ zum letzten zurückgegebenen Knoten
                candidate = forward ? lastReturned.next : lastReturned.prev;
            }

            // Gelöschte Knoten überspringen ...
            candidate = skipRemoved(candidate, forward);

            // Falls sich so ein ungelöschte nächster Knoten finden lässt:
            // cachen & true zurückgeben
            if (candidate != null) {
                if (candidate.observation == null) {
                    throw new RuntimeException("Knoten in der Liste enthält nur null-Wert statt Observation!");
                }
                nextNode = candidate;
                return true;
            }

            // Sonst kein weiterer ungelöschte Knoten vorhanden
            return false;
        }

        /**
         * Gibt die nächste Beobachtung der Iteration zurück.
         * Vorbedingung/ Client-kontrollierter History-Constraint: hasNext() muss aufgerufen worden sein und true geliefert haben.
         * Nachbedingung: Gibt nächste Beobachtung im System zurück.
         * @return Nächste Beobachtung
         * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
         */
        @Override
        public Observation next() {
            if (!hasNext())
                throw new NoSuchElementException();

            // Nachdem der nächste Knoten zurückgegeben wird, wird er der zuletzt zurückgegebene sein
            lastReturned = nextNode;
            // Beobachtung aus Knoten extrahieren
            Observation result = lastReturned.observation;
            nextNode = null; // erzwingt Neubestimmung in hasNext()
            return result;
        }
    }

    // === INNERE KLASSE: ITERATOR DER BEOBACHTUNGEN DERSELBEN BIENE ===

    /**
     * Erzeugt einen Iterator über alle Beobachtungen einer Biene.
     * Dazu filtert der Iterator aus dem Basis-Iterator nur die Beobachtungen,
     * die dieselbe Biene betreffen.<p>
     * Invarianten:<br>
     * - Zeitpunkte, Beobachtungsliste & Bienen sind niemals null.<br>
     *  - From muss vor to sein<p>
     * Untertypbeziehungen: SameBeeIterator ist eine Hilfsklasse und daher kein Untertyp der anderen Interfaces oder Klassen.
     * Aufgrund der Parametrisierung mit Bee ist keine Subtypbeziehung zu ObservationIterator möglich.
     * Es wäre allerdings grundsätzlich möglich, diese Parametrisierung zu Iterator<Observation> zu ändern
     * und so eine Subtypbeziehung herzustellen. Allerdings könnten dann auf über den SameBee Iterator abgerufenen
     * Elementen auch nur Methoden des Typs Observation aufgerufen werden, da eine eindeutige Zuordnung zum Typ
     * Bee nicht mehr möglich wäre. Auf komplexere generische Implementierungen wird im Rahmen der Aufgabe verzichtet.
     */
    private static class SameBeeIterator implements Iterator<Bee>{

        // --- KONSTANTEN ---

        /**
         * Die Biene, nach der der Iterator filtert
         */
        private final Bee me;

        /**
         * Zeitpunkt, bis zu dem gefiltert werden soll
         */
        private final LocalDateTime to;

        /**
         * Zeitpunkt, ab dem gefiltert werden soll.
         */
        private final LocalDateTime from;

        /**
         * Basisiterator über alle betreffenden Beobachtungen des Systems
         */
        private final Iterator<Observation> base;

        /**
         * Liste aller Beobachtungen des Systems
         */
        private final ObservationList observations;

        // --- VARIABLEN ---

        /**
         * Cache für nächste Beobachtung
         */
        private Bee cachedNext;

        /**
         * Ob schon eine weitere Beobachtung im Cache ist.
         */
        private boolean cached;

        // --- KONSTRUKTOR ---

        /**
         * Erzeugt einen neuen Iterator
         * Vorbedingungen: Zeitpunkte, Beobachtungsliste & Bienen dürfen nicht null sein
         *                 From muss vor to sein
         * Nachbedingungen:
         * <li>Iterator über alle Beobachtungen des gleichen Individuums</li>
         * <li>falls {@code isAscending == true}, dann aufsteigend sortiert (älteste zuerst)</li>
         * <li>falls {@code isAscending == false}, dann absteigend sortiert (neueste zuerst)</li>
         * <li>nur Beobachtungen zwischen from und to</li>
         * <li>enthält keine entfernten Beobachtungen</li>
         *
         * @param from Zeitpunkt, ab dem der Iterator Beobachtungen filtert
         * @param to Zeitpunkt, bis zu dem der Iterator Beobachtungen filtert
         * @param isAscending ob der Iterator aufsteigend oder absteigend laufen soll
         * @param observations Liste aller Observationen des Systems
         * @param me Biene, um die es in dem Iterator geht.
         * @throws IllegalArgumentException wenn Zeitpunkte, Beobachtungsliste oder Bienen null sind, oder from nach to liegt.
         */
        private SameBeeIterator(LocalDateTime from, LocalDateTime to, boolean isAscending, ObservationList observations, Bee me) {
            if (observations == null) {
                throw new IllegalArgumentException("Das System (Liste aller Beobachtungen) darf nicht null sein!");
            }
            if (to == null || from == null) {
                throw new IllegalArgumentException("Zeitpunkte dürfen nicht null sein!");
            }
            if (me == null) {
                throw new IllegalArgumentException("Biene darf nicht null sein!");
            }
            if (from.isAfter(to)) throw new IllegalArgumentException("from muss <= to sein");

            // Basisiterator aus der ObservationList holen
            LocalDateTime startForward = (from.equals(LocalDateTime.MIN)) ? from : from.minusNanos(1);
            LocalDateTime startBackward = (to.equals(LocalDateTime.MAX)) ? to : to.plusNanos(1);

            this.to = to;
            this.from = from;
            this.observations = observations;
            this.base = isAscending
                    ? observations.iteratorFrom(startForward, true)  // vorwärts ab <= from
                    : observations.iteratorFrom(startBackward, false); // rückwärts ab >= to
            this.me = me;
        }

        // --- METHODEN ---

        /**
         * Prüft, ob diese Observation und eine andere sich auf das gleiche Individuum beziehen.
         * Zwei Observationen gehören zum gleichen Individuum wenn:<br>
         * - Gleicher Chip (beide Chips != null)<br>
         * - über previous-Kette (Referenzen auf vorherige Individuen) verbunden<br>
         * - gleicher Chip in der previous-Komponente* gefunden<p>
         * Vorbedingung: other != null<br>
         * Nachbedingung: Gibt true zurück wenn gleiches Individuum sonst false
         *
         * @param other Observation einer Biene
         * @return true, wenn sich beide Observationen auf das gleiche Individuum beziehen.
         * @throws IllegalArgumentException wenn other == null
         */
        private boolean isSameIndividualAs(Bee other) {
            if (other == null) {
                throw new IllegalArgumentException("Bienen-Observation darf nicht null sein!");
            }
            if (me == other || me.equals(other)) {
                return true;
            }

            // a) Direkte Chip-Gleichheit
            Integer thisChip = me.getChip();
            Integer otherChip = other.getChip();
            if (thisChip != null && thisChip.equals(otherChip)) {
                return true;
            }

            // b) Direkte Verbindung über previous-Referenzen (beidseitig)
            if (reachableViaPrevious(me, other) || reachableViaPrevious(other, me)) {
                return true;
            }

            // c) Chips aus der gesamten (vorwärts+rückwärts) Kette vergleichen
            thisChip = chipsInComponent(observations, me);
            otherChip = chipsInComponent(observations, other);
            return thisChip != null && thisChip.equals(otherChip);
        }

        /**
         * Sammelt die Chipzahl eines Individuums über die gesamten (vorwärts + rückwärts) verbundenen Komponente.
         * Eine Komponente umfasst in der Graphentheorie alle über Kanten direkt oder indirekt
         * miteinander verbundenen Knoten, d.h. hier: alle Bienen, die von einer Start-Biene aus
         * über previousObservation-Verbindungen (vorwärts oder rückwärts) erreichbar sind.
         * Dazu werden sowohl die previous-Kette rückwärts als auch
         * alle Beobachtungen, die (transitiv) per previous auf bereits besuchte zeigen, traversiert.
         * So wird herausgefunden, ob irgendwo in der Komponente eine Chipzahl angegeben wurde.
         * Vorbedingung: Jedes Individuum ist mit genau maximal einem bestimmten Chip markiert.
         * Zu testende Biene ist nicht null
         * Nachbedingung: Rückgabe der Chipzahl, mit dem das Indiviuum markiert ist, oder null wenn es keinen Chip gibt.
         *
         * @param observations Liste aller Observationen (System)
         * @param bee          Biene, ab der gesucht wird.
         * @return Chip-Zahl des Individuums, sofern es einen Chip hat, sonst null
         * @throws IllegalArgumentException wenn übergebene Biene null ist
         */
        private static Integer chipsInComponent(ObservationList observations, Bee bee) {
            if (bee == null) {
                throw new IllegalArgumentException("Biene darf nicht null sein!");
            }

            // Bereits traversierte Bienen
            HashSet<Bee> visited = new HashSet<>();
            // Warteschlange noch zu traversierender Bienen
            ArrayList<Bee> queue = new ArrayList<>();
            queue.add(bee);
            // Solange weiter traversieren, bis keine neuen Informationen mehr gefunden werden
            while (!queue.isEmpty()) {
                // Aktuelle erste Biene aus Warteschlange ziehen
                Bee cur = queue.removeLast();
                if (cur == null || visited.contains(cur)) continue;
                visited.add(cur);
                // Chip der Biene
                Integer c = cur.getChip();
                if (c != null) {
                    return c;
                }
                // Für Rückwärts: Referenz auf vorherige Bienen zur Warteschlange hinzufügen
                queue.add(cur.getPreviousObservation());
                // Für Vorwärts: alle Bienen, die direkt auf die aktuelle zeigen hinzufügen
                for (Observation o : observations) {
                    if (o instanceof Bee b) {
                        if (b.getPreviousObservation() == cur) {
                            queue.add(b);
                        }
                    }
                }
            }
            return null;
        }

        /**
         * Prüft, ob zwei Bienen-Observationen über Referenzen auf vorherige Observationen verbunden sind.
         * Ob target von start aus über die previous-Kette oder über den gleich Chip erreichbar ist.<p>
         * <p>
         * Vorbedingung: Beide zu testenden Observationen dürfen nicht null sein.<br>
         * Nachbedingung:
         * <li>Gibt true zurück, wenn target von start erreichbar ist, sonst false.</li>
         * <li>Findet nur jene Objekte, die sich aus einem direkten Vergleich ergeben.</li>
         * @param start  erste Observation einer Biene (von der aus gesucht wird)
         * @param target zweite Observation einer Biene (nach der gesucht wird)
         * @return true, wenn die Observationen miteinander verkettet sind.
         * @throws IllegalArgumentException wenn eine der zu testenden Observationen null ist
         */
        private static boolean reachableViaPrevious(Bee start, Bee target) {
            if (start == null || target == null) {
                throw new IllegalArgumentException("Bienen-Observation darf nicht null sein!");
            }

            Bee current = start.getPreviousObservation();
            Integer targetChip = target.getChip();

            // Set für Zyklus-Schutz (bereits geprüfte Bienen)
            HashSet<Bee> visited = new HashSet<>();
            visited.add(start);

            // Kette an Referenzen durchgehen, bis wir zur Zielbiene kommen
            while (current != null && !visited.contains(current)) {
                visited.add(current);
                // direkte Referenz
                if (current == target || current.equals(target)) {
                    return true;
                }
                // gleicher Chip
                if (current.getChip() != null && targetChip != null) {
                    if (current.getChip().equals(targetChip)) {
                        return true;
                    }
                }
                current = current.getPreviousObservation();
            }
            return false;
        }

        /**
         * Prüft, ob sich ein Zeitpunkt innerhalb der Grenzen from & to (inklusive) befindet.
         * Vorbedingung: Zeitpunkt darf nicht null sein, from & to dürfen nicht null sein.
         * Nachbedingung: true, wenn Zeitpunkt t im zu iterierenden Zeitraum des Iterators ist
         * @param t zu prüfender Zeitpunkt
         * @return true, wenn sich der Zeitpunkt in dem Zeitraum from-to befindet
         * @throws IllegalArgumentException wenn Zeitpunkt null ist
         */
        private boolean inRange(LocalDateTime t) {
            if (t == null) {
                throw new IllegalArgumentException("Zeitpunkt darf nicht null sein!");
            }
            return !t.isBefore(from) && !t.isAfter(to); // inklusives [from, to]
        }

        /**
         * "Füllt" jede Runde den Cache auf, indem weitere Beobachtungen des Basis-Iterators
         * abgerufen & gefiltert werden, bis eine passende Beobachtung gefunden wurde.
         * Eine passende Beobachtung bezieht sich auf dieselbe Biene & liegt im angegebenen Zeitraum des Iterators
         * Nachbedingung: Im Cache ist eine weitere Beobachtung, sofern es eine gibt.
         */
        private void fill() {
            // Falls Cache noch vorhanden kein Suchen nötig
            if (cached) return;
            cached = true;
            // Sonst: Basis-Iterator weiter laufen lassen
            while (base.hasNext()) {
                Observation o = base.next();
                // Nur Bienen filtern
                if (!(o instanceof Bee b)) continue;
                // Im Zeitraum?
                LocalDateTime t = o.getDateTime();
                if (t == null) continue;
                if (!inRange(t)) continue;
                // Selbe Biene?
                if (!isSameIndividualAs(b)) continue;
                // Gefundene Biene cachen
                cachedNext = b;
                return;
            }
            cachedNext = null;
        }

        /**
         * Liefert zurück, ob die Iteration über eine weitere Beobachtung bzgl. derselben Biene verfügt.
         * Das bedeutet, wenn true zurückgegeben wird, wird next() eine weitere Beobachtung liefern
         * an Stelle einer Exception.
         * Nachbedingung: Gibt true zurück, wenn next() eine Beobachtung liefern wird.
         * @return true, wenn es eine weitere über next() abrufbare Beobachtung gibt.
         */
        @Override
        public boolean hasNext() {
            fill(); // hier je dynamisch neue Observationen/Bienen abrufen
            if (cachedNext == null) {
                cached = false;
            }
            return cachedNext != null;
        }

        /**
         * Gibt die nächste Beobachtung der Iteration zurück.
         * Vorbedingung/ Client-kontrollierter History-Constraint: hasNext() muss aufgerufen worden sein und true geliefert haben.
         * Nachbedingung: Gibt nächste Beobachtung dieses Individuums zurück
         * @return Nächste Beobachtung derselben Biene
         * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
         */
        @Override
        public Bee next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Bee out = cachedNext;
            // Cache leeren: wird beim nächsten hasNext() NEU berechnet
            cached = false;
            cachedNext = null;
            return out;
        }
    }


    // GENERISCHE IMPLEMENTIERUNG VON SAMEBEE, MIT DER DIE GETTER NICHT ÖFFENTLICH SEIN MÜSSTEN.

    /*
     * Erzeugt einen Iterator über alle Beobachtungen einer Biene.
     * Dazu filtert der Iterator aus dem Basis-Iterator nur die Beobachtungen,
     * die dieselbe Biene betreffen.<p>
     * Invarianten:<br>
     * - Die Klassenvariablen sind niemals null.<br>
     * - From muss vor to sein<p>
     * Untertypbeziehungen: Aufgrund Parametrisierung keine Subtypbeziehung zu ObservationIterator möglich.
     */
    /*
    private static class SameBeeIterator<E extends Observation> implements Iterator<E> {

        // --- KONSTANTEN ---

        /**
         * Die Biene, nach der der Iterator filtert
         * Invariante: ist niemals null
         *//*
        private final E me;

        /**
         * Zeitpunkt, bis zu dem gefiltert werden soll
         * Invariante: ist niemals null
         *//*
        private final LocalDateTime to;

        /**
         * Zeitpunkt, ab dem gefiltert werden soll.
         * Invariante: ist niemals null
         *//*
        private final LocalDateTime from;

        /**
         * Basisiterator über alle betreffenden Beobachtungen des Systems
         *//*
        private final Iterator<Observation> base;

        /**
         * Liste aller Beobachtungen des Systems
         * Invariante: ist niemals null
         *//*
        private final ObservationList observations;

        /**
         * Typ der Biene
         *//*
        private final Class<E> beeType;

        /**
         * Funktion zum Extrahieren der Chipzahl
         *//*
        Function<E, Integer> chipExtractor;

        /**
         * Funktion um extrahieren der vorherigen Boebachtung des gleichen Individuums
         *//*
        Function<E, E> prevBee;

        // --- VARIABLEN ---

        /**
         * Cache für nächste Beobachtung
         *//*
        private E cachedNext;

        /**
         * Ob schon eine weitere Beobachtung im Cache ist.
         *//*
        private boolean cached;

        // --- KONSTRUKTOR ---

        /**
         * Erzeugt einen neuen Iterator
         * Vorbedingungen: Parameter dürfen nicht null sein
         *                 From muss vor to sein
         * @param from Zeitpunkt, ab dem der Iterator Beobachtungen filtert
         * @param to Zeitpunkt, bis zu dem der Iterator Beobachtungen filtert
         * @param isAscending ob der Iterator aufsteigend oder absteigend laufen soll
         * @param observations Liste aller Observationen des Systems
         * @param me Biene, um die es in dem Iterator geht.
         * @param beeType Typ der Biene (wird einfachhalber direkt übergeben)
         * @param chipExtractor Funktion zum Auslesen der Chip ID eines Individuums
         * @param prevBee Funktion zum Auslesen der vorangegangenen Beobachtung eines Individuums
         * @throws IllegalArgumentException wenn einer der übergebenen Parameter null ist.
         *//*
        private SameBeeIterator(LocalDateTime from, LocalDateTime to, boolean isAscending, ObservationList observations, E me, Class<E> beeType, Function<E, Integer> chipExtractor,  Function<E, E> prevBee) {
            if (observations == null) {
                throw new IllegalArgumentException("Das System (Liste aller Beobachtungen) darf nicht null sein!");
            }
            if (to == null || from == null) {
                throw new IllegalArgumentException("Zeitpunkte dürfen nicht null sein!");
            }
            if (me == null) {
                throw new IllegalArgumentException("Biene darf nicht null sein!");
            }
            if (beeType == null) {
                throw new IllegalArgumentException("Typ darf nicht null sein!");
            }
            if (chipExtractor == null || prevBee == null) {
                throw new IllegalArgumentException("Funktionen dürfen nicht null sein!");
            }
            if (from.isAfter(to)) throw new IllegalArgumentException("from muss <= to sein");

            // Basisiterator aus der ObservationList holen
            LocalDateTime startForward = (from.equals(LocalDateTime.MIN)) ? from : from.minusNanos(1);
            LocalDateTime startBackward = (to.equals(LocalDateTime.MAX)) ? to : to.plusNanos(1);

            this.to = to;
            this.from = from;
            this.observations = observations;
            this.chipExtractor = chipExtractor;
            this.beeType = beeType;
            this.prevBee = prevBee;
            this.base = isAscending
                    ? observations.iteratorFrom(startForward, true)  // vorwärts ab <= from
                    : observations.iteratorFrom(startBackward, false); // rückwärts ab >= to
            this.me = me;
        }

        // --- METHODEN ---

        /**
         * Prüft, ob diese Observation und eine andere sich auf das gleiche Individuum beziehen.
         * Zwei Observationen gehören zum gleichen Individuum wenn:<br>
         * - Gleicher Chip (beide Chips != null)<br>
         * - über previous-Kette (Referenzen auf vorherige Individuen) verbunden<br>
         * - gleicher Chip in der previous-Komponente* gefunden<p>
         * Vorbedingung: other != null<br>
         * Nachbedingung: Gibt true zurück wenn gleiches Individuum sonst false
         *
         * @param other Observation einer Biene
         * @return true, wenn sich beide Observationen auf das gleiche Individuum beziehen.
         * @throws IllegalArgumentException wenn other == null
         *//*
        private boolean isSameIndividualAs(E other) {
            if (other == null) {
                throw new IllegalArgumentException("Bienen-Observation darf nicht null sein!");
            }
            if (me == other || me.equals(other)) {
                return true;
            }

            // a) Direkte Chip-Gleichheit
            Integer thisChip = chipExtractor.apply(me);
            Integer otherChip = chipExtractor.apply(other);
            if (thisChip != null && thisChip.equals(otherChip)) {
                return true;
            }

            // b) Direkte Verbindung über previous-Referenzen (beidseitig)
            if (reachableViaPrevious(me, other) || reachableViaPrevious(other, me)) {
                return true;
            }

            // c) Chips aus der gesamten (vorwärts+rückwärts) Kette vergleichen
            thisChip = chipsInComponent(observations, me);
            otherChip = chipsInComponent(observations, other);
            return thisChip != null && thisChip.equals(otherChip);
        }

        /**
         * Sammelt die Chipzahl eines Individuums über die gesamten (vorwärts + rückwärts) verbundenen Komponente.
         * Eine Komponente umfasst in der Graphentheorie alle über Kanten direkt oder indirekt
         * miteinander verbundenen Knoten, d.h. hier: alle Bienen, die von einer Start-Biene aus
         * über previousObservation-Verbindungen (vorwärts oder rückwärts) erreichbar sind.
         * Dazu werden sowohl die previous-Kette rückwärts als auch
         * alle Beobachtungen, die (transitiv) per previous auf bereits besuchte zeigen, traversiert.
         * So wird herausgefunden, ob irgendwo in der Komponente eine Chipzahl angegeben wurde.
         * So wird herausgefunden, ob irgendwo in der Komponente eine Chipzahl angegeben wurde.
         * Vorbedingung: Jedes Individuum ist mit genau maximal einem bestimmten Chip markiert.
         * Zu testende Biene ist nicht null
         *
         * @param observations Liste aller Observationen (System)
         * @param bee Biene, ab der gesucht wird.
         * @return Chip-Zahl des Individuums, sofern es einen Chip hat, sonst null
         * @throws IllegalArgumentException wenn übergebene Biene null ist
         *//*
        private Integer chipsInComponent(ObservationList observations, E bee) {
            if (bee == null) {
                throw new IllegalArgumentException("Biene darf nicht null sein!");
            }

            // Bereits traversierte Bienen
            HashSet<E> visited = new HashSet<>();
            // Warteschlange noch zu traversierender Bienen
            ArrayList<E> queue = new ArrayList<>();
            queue.add(bee);
            // Solange weiter traversieren, bis keine neuen Informationen mehr gefunden werden
            while (!queue.isEmpty()) {
                // Aktuelle erste Biene aus Warteschlange ziehen
                E cur = queue.removeLast();
                if (cur == null || visited.contains(cur)) continue;
                visited.add(cur);
                // Chip der Biene
                Integer c = chipExtractor.apply(cur);
                if (c != null) {
                    return c;
                }
                // Für Rückwärts: Referenz auf vorherige Bienen zur Warteschlange hinzufügen
                queue.add(prevBee.apply(cur));
                // Für Vorwärts: alle Bienen, die direkt auf die aktuelle zeigen hinzufügen
                for (Observation o : observations) {
                    if (!beeType.isInstance(o)) {
                        continue;
                    }
                    E b = beeType.cast(o);
                    if (prevBee.apply(b) == cur) {
                        queue.add(b);
                    }
                }
            }
            return null;
        }

        /**
         * Prüft, ob zwei Bienen-Observationen über Referenzen auf vorherige Observationen verbunden sind.
         * Ob target von start aus über die previous-Kette oder über den gleich Chip erreichbar ist.<p>
         * <p>
         * Vorbedingung: Beide zu testenden Observationen dürfen nicht null sein.<br>
         * Nachbedingung:
         * <li>Gibt true zurück, wenn target von start erreichbar ist, sonst false.</li>
         * <li>Findet nur jene Objekte, die sich aus einem direkten Vergleich ergeben.</li>
         *
         * @param start erste Observation einer Biene (von der aus gesucht wird)
         * @param target zweite Observation einer Biene (nach der gesucht wird)
         * @return true, wenn die Observationen miteinander verkettet sind.
         * @throws IllegalArgumentException wenn eine der zu testenden Observationen null ist
         *//*
        private boolean reachableViaPrevious(E start, E target) {
            if (start == null || target == null) {
                throw new IllegalArgumentException("Bienen-Observation darf nicht null sein!");
            }

            E current = prevBee.apply(start);
            Integer targetChip = chipExtractor.apply(target);

            // Set für Zyklus-Schutz (bereits geprüfte Bienen)
            HashSet<E> visited = new HashSet<>();
            visited.add(start);

            // Kette an Referenzen durchgehen, bis wir zur Zielbiene kommen
            while (current != null && !visited.contains(current)) {
                visited.add(current);
                // direkte Referenz
                if (current == target || current.equals(target)) {
                    return true;
                }
                // gleicher Chip
                if (chipExtractor.apply(current) != null && targetChip != null) {
                    if (chipExtractor.apply(current).equals(targetChip)) {
                        return true;
                    }
                }
                current = prevBee.apply(current);
            }
            return false;
        }

        /**
         * Prüft, ob sich ein Zeitpunkt innerhalb der Grenzen from & to (inklusive) befindet.
         * Vorbedingung: Zeitpunkt darf nicht null sein, from & to dürfen nicht null sein.
         *
         * @param t zu prüfender Zeitpunkt
         * @return true, wenn sich der Zeitpunkt in dem Zeitraum from-to befindet
         * @throws IllegalArgumentException wenn zeitpunkt null ist
         *//*
        private boolean inRange(LocalDateTime t) {
            if (t == null) {
                throw new IllegalArgumentException("Zeitpunkt darf nicht null sein!");
            }
            return !t.isBefore(from) && !t.isAfter(to); // inklusives [from, to]
        }

        /**
         * "Füllt" jede Runde den Cache auf, indem weitere Beobachtungen des Basis-Iterators
         * abgerufen & gefiltert werden, bis eine passende Beobachtung gefunden wurde.
         * Eine passende Beobachtung bezieht sich auf dieselbe Biene & liegt im angegebenen Zeitraum des Iterators
         * Nachbedingung: Im Cache ist eine weitere Beobachtung, sofern es eine gibt.
         *//*
        private void fill() {
            // Falls Cache noch vorhanden kein Suchen nötig
            if (cached) return;
            cached = true;
            // Sonst: Basis-Iterator weiter laufen lassen
            while (base.hasNext()) {
                Observation o = base.next();
                // Nur Bienen filtern
                if (!beeType.isInstance(o)) continue;
                E b = beeType.cast(o);
                // Im Zeitraum?
                LocalDateTime t = o.getDateTime();
                if (t == null) continue;
                if (!inRange(t)) continue;
                // Selbe Biene?
                if (!isSameIndividualAs(b)) continue;
                // Gefundene Biene cachen
                cachedNext = b;
                return;
            }
            cachedNext = null;
        }

        /**
         * Liefert zurück, ob die Iteration über eine weitere Beobachtung bzgl. derselben Biene verfügt.
         * Das bedeutet, wenn true zurückgegeben wird, wird next() eine weitere Beobachtung liefern
         * an Stelle einer Exception.
         * History-Constraint: hasNext() muss vor jedem Aufruf von next() erfolgreich aufgerufen worden sein.
         *
         * @return true, wenn es eine weitere über next() abrufbare Beobachtung gibt.
         *//*
        @Override
        public boolean hasNext() {
            fill(); // hier je dynamisch neue Observationen/Bienen abrufen
            if (cachedNext == null) {
                cached = false;
            }
            return cachedNext != null;
        }

        /**
         * Gibt die nächste Beobachtung der Iteration zurück.
         * History-Constraint: hasNext() muss vor jedem Aufruf von next() aufgerufen werden & true liefern.
         *
         * @return Nächste
         * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
         *//*
        @Override
        public E next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            E out = cachedNext;
            // Cache leeren: wird beim nächsten hasNext() NEU berechnet
            cached = false;
            cachedNext = null;
            return out;
        }
    }
     */
}
