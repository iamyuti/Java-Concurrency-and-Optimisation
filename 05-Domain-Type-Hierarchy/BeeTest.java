import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * === KONKRETE KLASSE FÜR TESTUNG ===
 * === NICHT ZUM EIGENTLICHEN SYSTEM GEHÖREND ===
 * === TESTET: Interface Bee ===
 *
 * Eine Beobachtung einer Biene irgendeiner Art. Alle Bienen zählen
 * zu den Stechimmen (Wespen), ernähren sich durch Pollen und
 * Nektar von Blütenpflanzen (und treten dabei als Bestäuber auf)
 * und versorgen auch ihren Nachwuchs mit Pollen und/oder Nektar.<p>
 *
 * Es können gewisse Information bei der Objekterzeugung
 * (auch gleichzeitig) angegeben werden:<br>
 * 1. Ist bekannt, dass die Beobachtung sich auf das gleiche
 * Individuum bezieht wie eine frühere Beobachtung, wird das Objekt
 * der früheren Beobachtung angegeben.<br>
 * 2. Ist das Individuum mit einer Zahl markiert (z. B. über einen
 * winzigen Chip), wird diese Markierung angegeben.<p>
 *
 * Die Bezeichnung "gleiches Individuum" bezieht sich auf folgende Definition:
 * Zwei Beobachtungen beziehen sich auf dasselbe Individuum, wenn sie dieselbe
 * Chip-Markierung haben (getChip() != null und gleich) oder über die
 * previousObservation-Kette verbunden sind.
 *
 * Invarianten: Erbt alle Invarianten von Pollinator und Wasp. Dies sind:<br>
 * - Aus Observation: Datum und Uhrzeit sind nicht null.<br>
 * - Aus Observation: Kommentar ist nicht null.<br>
 * - Aus Observation: Nach Erzeugung ändern sich Datum, Uhrzeit und Kommentar nicht.<br>
 * - Aus Observation: Alle Beobachtungen werden bei Erzeugung im System (Liste aller Beobachtungen) registriert.<br>
 * - getChip() liefert für eine Beobachtung immer denselben Wert
 * - getPreviousObservation() liefert für eine Beobachtung immer dieselbe Referenz
 * - Chip-Markierungen ändern sich nicht nach Erstellung.<br>
 * - Chip-Markierungen sind eindeutig für ein bestimmtes Individuum.<br>
 * - previousObservation-Referenzen ändern sich nicht nach Erstellung.<br>
 * - sameBee() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove()).<p>
 *
 * History Constraints: Erbt alle History Constraints von Pollinator und Wasp. Dies sind:<br>
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von Bestäuber, da alle Bienen Bestäuber sind.<br>
 * - Subtyp von Wasp, da alle Bienen Wespen sind.<br>
 * - Alle anderen offenen Typen sind Subtypen dieses Typs.<p>
 */
public class BeeTest implements Bee {

    // --- KONSTANTEN ---

    /**
     * Liste aller Beobachtungen im System
     */
    private final ObservationList OBSERVATIONS;

    /**
     * Datum und Uhrzeit der Observation
     */
    private final LocalDateTime dateTime;

    /**
     * Der Kommentar zur Observation
     */
    private final String comment;

    /**
     * Referenz auf eine vorangegangene Observation des gleichen Individuums
     * Invariante: zeigt auf eine Beobachtung des gleichen Individuums
     */
    private final Bee previousBee;

    /**
     * Chip-Marker des Individuums, auf das sich diese Observation bezieht
     * Invarianten: ist eindeutig für ein Individuum (alle Beobachtungen
     * des gleichen Individuums haben dieselbe Chip-Markierung, falls vorhanden)
     */
    private final Integer chip;

    // --- GETTER ---

    /**
     * Getter: Liefert Datum und Uhrzeit der Observation zurück.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung: Gibt unveränderbare LocalDateTime zurück;
     * @return Datum und Uhrzeit der Beobachtung
     */
    @Override
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    /**
     * Getter: Liefert den Kommentar zur Observation zurück.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung: Gibt unveränderbare String zurück
     * @return beschreibender Kommentar zur Beobachtung
     */
    @Override
    public String getComment() {
        return comment;
    }

    /**
     * GETTER: Gibt das Objekt einer früheren Beobachtung des gleichen Individuums zurück,
     * sofern dieser Bezug bekannt ist.<p>
     *
     * Nachbedingung: Gibt Referenz auf frühere Observation zurück, falls bekannt, sonst null
     * @return frühere Beobachtung des gleichen Individuums, sofern eine bekannt
     *         sonst null
     */
    @Override
    public Bee getPreviousObservation() {
        return previousBee;
    }

    /**
     * Getter: Gibt die Chipzahl dieses Objektes zurück, sofern eine bekannt ist.<p>
     *
     * Nachbedingung: Gibt Chip-Nummer zurück, falls vorhanden, sonst null
     * @return numerische Markierung, sofern eine bekannt
     *         sonst null
     */
    @Override
    public Integer getChip() {
        return chip;
    }

    // --- KONSTRUKTOR ---

    /**
     * Konstruktor für eine Observation einer Biene (zur Testung)
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System), in dem die Biene registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     */
    public BeeTest(ObservationList observations, LocalDateTime dateTime, String comment) {
        this(observations, dateTime, comment, null,null);
    }

    /**
     * Konstruktor für eine Observation einer Biene (zur Testung) mit Chip-Zahl.
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System), in dem die Biene registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param chip Chip-Zahl des beobachteten Individuums
     */
    public BeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip) {
        this(observations, dateTime, comment, chip,null);
    }

    /**
     * Konstruktor für eine Observation einer Biene (zur Testung)
     * mit Verweis auf vorangegangene Beobachtung des gleichen Individuums.
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System), in dem die Biene registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     */
    public BeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Bee previousBee) {
        this(observations, dateTime, comment, null, previousBee);
    }

    /**
     * Konstruktor für eine Observation einer Biene (zur Testung)
     * mit Chip-Zahl & Verweis auf vorangegangene Beobachtung des gleichen Individuums.
     * Vorbedingungen:
     * <li>Alle Argumente nicht null
     * <li>Falls die Markierung und die Referenz auf eine frühere Beobachtung angegeben werden, müssen sich diese auf dasselbe Individuum zeigen.</li>
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System), in dem die Biene registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     */
    public BeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip, Bee previousBee) {
        if (observations == null) {
            throw new IllegalArgumentException("Das System (Liste aller Beobachtungen) darf nicht null sein!");
        }
        if (dateTime == null) {
            throw new IllegalArgumentException("Das Datum und die Uhrzeit dürfen nicht null sein!");
        }
        if (comment == null) {
            throw new IllegalArgumentException("Der Kommentar darf nicht null sein!");
        }
        this.dateTime = dateTime;
        this.comment = comment;
        this.chip = chip;
        this.previousBee = previousBee;
        this.OBSERVATIONS = observations;
        OBSERVATIONS.add(this);
    }

    // --- METHODEN ---

    /**
     * Liefert die Zusicherungen einer Observation implementierenden Klasse in textueller Form aus.
     * Nachbedingung: Ausgabe aller Zusicherungen der Klasse
     */
    @Override
    public String toString() {
        return """
               BEE
                    Eine Beobachtung einer Biene irgendeiner Art.
                    Alle Bienen zählen zu den Stechimmen (Wespen).
                    Alle Bienen sind Bestäuber.
              """;
    }

    /**
     * Diese Methode entfernt die Beobachtung this logisch (nicht real)
     * aus dem Datenbestand, sodass das Objekt von keinem Iterator im gesamten
     * System mehr zurückgegeben wird. Aber alle im entfernten Objekt
     * aufgerufenen Methoden funktionieren nach wie vor.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Das Objekt wird von keinem Iterator im gesamten System mehr zurückgegeben.
     * - Das Objekt bleibt bestehen, auch wenn es aus der Liste entfernt wurde,
     *      d.h. sämtliche Methoden sind noch über das Objekt aufrufbar.
     * - valid() liefert false für dieses Objekt
     */
    public void remove() {
        OBSERVATIONS.remove(this);
    }

    /**
     * Diese Methode prüft, ob die Beobachtung noch im Datenbestand ist.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Gibt true zurück, wenn die Beobachtung in OBSERVATIONS enthalten ist.
     * - Gibt false zurück, wenn remove() aufgerufen wurde.
     * @return true, wenn Beobachtung gültig; false, wenn entfernt
     */
    public boolean valid() {
        return OBSERVATIONS.contains(this);
    }

    /**
     * Diese Methode retourniert einen Iterator über alle zeitlich früheren Beobachtungen
     * verglichen mit this, mit zeitlich näher liegenden Beobachtungen zuerst.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Iterator über alle Beobachtungen mit dateTime < this.getDateTime()
     * - Absteigend nach Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * - Enthält this nicht
     * @return Iterator über alle zeitlich früheren Beobachtungen, näher liegende zuerst
     */
    public Iterator<Observation> earlier() {
        return OBSERVATIONS.iteratorFrom(this, false);
    }

    /**
     * Diese Methode retourniert einen Iterator über alle zeitlich späteren Beobachtungen
     * verglichen mit this, mit zeitlich näher liegenden Beobachtungen zuerst.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Iterator über alle Beobachtungen mit dateTime > this.getDateTime()
     * - Aufsteigend nach Zeitpunkt sortiert
     * - Enthält keine entfernten Beobachtungen
     * - Enthält this nicht
     * @return Iterator über alle zeitlich späteren Beobachtungen, näher liegende zuerst
     */
    public Iterator<Observation> later() {
        // Sortieren nach Datum
        return OBSERVATIONS.iteratorFrom(this, true);
    }

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums zurück.
     * Aufsteigend sortiert nach Beobachtungszeitraum
     * <p>
     * Nachbedingung:
     * <li>Iterator über alle Beobachtungen des gleichen Individuums</li>
     * <li>aufsteigend sortiert nach Zeitpunkt</li>
     * <li>enthält keine entfernten Beobachtungen</li>
     *
     * @return Iterator über alle Beobachtungen des gleichen Individuums
     */
    public Iterator<Bee> sameBee() {
        return sameBee(true);
    }

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums zurück.
     * Sortierung ist abhängig vom Parameter {@code isAscending}
     * <p>
     * Nachbedingung:
     * <li>Iterator über alle Beobachtungen des gleichen Individuums</li>
     * <li>falls {@code isAscending == true}, dann aufsteigend sortiert (älteste zuerst)</li>
     * <li>falls {@code isAscending == false}, dann absteigend sortiert (neueste zuerst)</li>
     * <li>enthält keine entfernten Beobachtungen</li>
     *
     * @param isAscending Boolean für die Bestimmung der Sortierreihenfolge
     * @return Iterator über alle Beobachtungen des gleichen Individuums
     */
    public Iterator<Bee> sameBee(boolean isAscending){
        return sameBee(isAscending, LocalDateTime.MIN, LocalDateTime.MAX);
    }


    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums
     * im vorgegebenen Zeitraum zurück.
     * Aufsteigend sortiert nach Beobachtungszeitraum
     * <p>
     * Vorbedingung: {@code from <= to}, from, to dürfen nicht null sein
     * <p>
     * Nachbedingung:
     * <li>Iterator über alle Beobachtungen des gleichen Individuums</li>
     * <li>aufsteigend sortiert nach Zeitpunkt</li>
     * <li>enthält keine entfernten Beobachtungen</li>
     * <li>nur Beobachtungen zwischen from und to</li>
     *
     * @param from Beginn des Zeitraums
     * @param to Ende des Zeitraums
     * @return Iterator über alle Beobachtungen des gleichen Individuums im Zeitraum [from, to]
     * @throws IllegalArgumentException falls {@code from > to}, from oder to null
     */
    public Iterator<Bee> sameBee(LocalDateTime from, LocalDateTime to){
        return sameBee(true, from, to);
    }

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums
     * im vorgegebenen Zeitraum zurück.
     * Sortierung ist abhängig vom Parameter {@code isAscending}
     * <p>
     * Vorbedingungen: {@code from <= to}<br>, from, to dürfen nicht null sein
     * Nachbedingungen:
     * <li>Iterator über alle Beobachtungen des gleichen Individuums</li>
     * <li>falls {@code isAscending == true}, dann aufsteigend sortiert (älteste zuerst)</li>
     * <li>falls {@code isAscending == false}, dann absteigend sortiert (neueste zuerst)</li>
     * <li>nur Beobachtungen zwischen from und to</li>
     * <li>enthält keine entfernten Beobachtungen</li>
     *
     * @param isAscending Boolean für die Bestimmung der Sortierreihenfolge
     * @param from Beginn des Zeitraums
     * @param to Ende des Zeitraums
     * @return Iterator über alle Beobachtungen des gleichen Individuums im Zeitraum [from, to]
     * @throws IllegalArgumentException falls {@code from > to}, from oder to null, System null
     */
    public Iterator<Bee> sameBee(boolean isAscending, LocalDateTime from, LocalDateTime to) {
        return OBSERVATIONS.sameBee(from, to, isAscending, this);
    }
}
