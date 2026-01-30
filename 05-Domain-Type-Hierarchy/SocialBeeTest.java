import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * === KONKRETE KLASSE FÜR TESTUNG ===
 * === NICHT ZUM EIGENTLICHEN SYSTEM GEHÖREND ===
 * === TESTET: Interface SocialBee ===
 *
 * Eine Beobachtung einer Biene einer sozialen Art. Im Gegen-
 * satz zu solitär oder kommunal lebenden Bienen gehen soziale Bienen
 * bei der Brutpflege arbeitsteilig vor, bilden also einen Staat.
 * Individuen mancher Arten können sowohl sozial als auch
 * solitär leben, aber nie beides zum gleichen Zeitpunkt.<br>
 *
 * Invarianten: Erbt alle Invarianten von Bee. Dies sind:<br>
 * - Aus Observation: Datum und Uhrzeit sind nicht null.<br>
 * - Aus Observation: Kommentar ist nicht null.<br>
 * - Aus Observation: Nach Erzeugung ändern sich Datum, Uhrzeit und Kommentar nicht.<br>
 * - Aus Observation: Alle Beobachtungen werden bei Erzeugung im System (Liste aller Beobachtungen) registriert.<br>
 * - Aus Bee: getChip() liefert für eine Beobachtung immer denselben Wert
 * - Aus Bee: getPreviousObservation() liefert für eine Beobachtung immer dieselbe Referenz
 * - Aus Bee: Chip-Markierungen ändern sich nicht nach Erstellung.<br>
 * - Aus Bee: Chip-Markierungen sind eindeutig für ein bestimmtes Individuum.<br>
 * - Aus Bee: previousObservation-Referenzen ändern sich nicht nach Erstellung.<br>
 * - Aus Bee: sameBee() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove()).<br>
 * - getSocial() liefert für eine Beobachtung immer denselben Wert.<br>
 * - social() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove())<p>
 *
 * History Constraints: Erbt alle History Constraints aus Bee. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 *
 * Untertypbeziehungen:
 * - Subtyp von Bee.<br>
 * - Keine Beziehung zu Wildbiene:
 *   Honigbiene ist eindeutig eine soziale Biene, aber explizit keine Wildbiene.<br>
 * - SocialBee kann nicht Subtyp von WildBee sein, sonst wäre HoneyBee eine Wildbiene.
 * Alle solitären Bienen sind Wildbienen. Wäre Wildbiene ein Subtyp von SocialBee, wären alle solitären
 * Bienen auch soziale Bienen. Aber sie soziale Lebensweise steht im Gegensatz zur solitären Lebensweise.
 * Solitäre Bienen bilden keinen Staat. Soziale Bienen bilden einen Staat.
 * Auch wenn einige Arten auf beide Lebensweisen leben können, wird niemals gelten,
 * dass alle sozialen Bienen auch solitär sind oder andersrum.<br>
 * - WildBee kann nicht Subtyp von SocialBee sein.<br>
 * - Aus dem gleichen Grund kann SolitaryBee kein Subtyp von SocialBee sein oder andersrum.
 */
public class SocialBeeTest implements SocialBee {

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

    /**
     * Marker, ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht.
     */
    private final boolean social;


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

    /**
     * Gibt zurück, ob aus dieser Beobachtung eine soziale Lebensweise hervorgeht.
     * Nachbedingung:
     * - true, wenn die Beobachtung auf eine eindeutig soziale Lebensweise hinweist.
     * - false, wenn dem nicht so ist (eindeutig nicht sozial oder unklar)
     * @return true bei eindeutiger sozialer Lebensweise, sonst false
     */
    public boolean getSocial() {
        return social;
    }


    // --- KONSTRUKTOR ---

    /**
     * Konstruktor für eine Observation einer sozialen Biene (zur Testung)
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale Lebensweise hervorgeht.
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param social Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     */
    public SocialBeeTest(ObservationList observations, LocalDateTime dateTime, String comment, boolean social) {
        this(observations, dateTime, comment, null,null, social);
    }

    /**
     * Konstruktor für eine Observation einer sozialen Biene (zur Testung)
     * mit Chip-Zahl sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale Lebensweise hervorgeht.
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param chip Chip-Zahl des beobachteten Individuums
     * @param social Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     */
    public SocialBeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip, boolean social) {
        this(observations, dateTime, comment, chip,null, social);
    }

    /**
     * Konstruktor für eine Observation einer sozialen Biene (zur Testung)
     * mit Verweis auf vorangegangene Beobachtung des gleichen Individuums
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale Lebensweise hervorgeht.
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     * @param social Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     */
    public SocialBeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Bee previousBee, boolean social) {
        this(observations, dateTime, comment, null, previousBee, social);
    }

    /**
     * Konstruktor für eine Observation einer sozialen Biene (zur Testung)
     * mit Chip-Zahl & Verweis auf vorangegangene Beobachtung des gleichen Individuums
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale Lebensweise hervorgeht.
     * Vorbedingungen:
     * <li>Liste aller Beobachtungen, Zeitstempel & Kommentar nicht null.</li>
     * <li>Falls die Markierung und die Referenz auf eine frühere Beobachtung angegeben werden, müssen sich diese auf dasselbe Individuum zeigen.</li>
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     * @param social Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     */
    public SocialBeeTest(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip, Bee previousBee, boolean social) {
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
        this.social = social;
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
               SOCIAL BEE
                    Eine Beobachtung einer Biene einer sozialen Art.
                    Soziale Bienen bilden einen Staat.
                    Die soziale Lebensweise steht im Gegensatz zur solitären oder kommunalen Lebensweise.
                    Individuen mancher Arten können sowohl sozial als auch solitär leben,
                    aber nie beides zum gleichen Zeitpunkt.                                                                                      \s
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
     * Vorbedingung: {@code from <= to}<br>, from, to dürfen nicht null sein
     * Nachbedingung:
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
     * @throws IllegalArgumentException falls {@code from > to}, from oder to null, Liste aller Beobachtungen null
     */
    public Iterator<Bee> sameBee(boolean isAscending, LocalDateTime from, LocalDateTime to) {
        return OBSERVATIONS.sameBee(from, to, isAscending,  this);
    }
}

