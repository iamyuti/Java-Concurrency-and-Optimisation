import java.time.LocalDateTime;
import java.util.Iterator;

/**
 *  Eine Beobachtung einer Biene der Art Lasioglossum Calceatum.<p>
 *
 *  Invarianten: Erbt alle Invarianten von SocialBee und von SolitaryBee. Das sind:<br>
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
 *   von Beobachtungen (außer bei remove())<br>
 * - Aus WildBee: getZucht() liefert für eine Beobachtung immer denselben Wert.<br>
 * - Aus WildBee: wild() liefert für das gleiche Individuum und denselben Parameter immer
 *   die gleiche Menge von Beobachtungen (außer bei remove()).<br>
 * - Aus SolitaryBee: getSolitary() liefert für eine Beobachtung immer denselben Wert.<br>
 * - Aus SolitaryBee: solitary() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove())<br>
 * - Aus SocialBee: getSocial() liefert für eine Beobachtung immer denselben Wert.<br>
 * - Aus SocialBee: social() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove())<p>
 *
 * History Constraints: Erbt alle History Constraints von SocialBee und SolitaryBee. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 *
 *  Untertypbeziehungen:<br>
 *  - Dies sind Wildbienen.<br>
 *  - Subtyp von SocialBee, da die Art meist sozial lebt.
 *  - Subtyp von SolitaryBee (welche Subtyp von WildBee ist), da die Art auch solitär leben kann.<br>
 *  - Hat selbst keine Untertypen, da dies eine explizite Art ist. (Siehe AndrenaBucephala)
 */
public class LasioglossumCalceatum implements SocialBee, SolitaryBee {

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
     * Marker, ob die beobachtete Lasioglossum Calceatum aus einer Zucht stammt
     */
    private final Boolean zucht;

    /**
     * Marker, ob aus dieser Beobachtung eindeutig eine solitäre Lebensweise hervorgeht.
     */
    private final boolean solitary;

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
     * Gibt zurück, ob die beobachtete Hummel aus Zucht stammt
     * Nachbedingung:
     * - Gibt true zurück, wenn Hummel aus einer Zucht stammt
     * - Gibt false zurück, wenn Hummel aus keiner Zucht stammt.
     * - Gibt null zurück, wenn nichts darüber bekannt ist
     * @return true = aus Zucht, false = nicht aus Zucht, null = unbekannt
     */
    @Override
    public Boolean getZucht() {
        return this.zucht;
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

    /**
     * Gibt zurück, ob aus dieser Beobachtung eine solitäre Lebensweise hervorgeht.
     * Nachbedingung:
     * - true, wenn die Beobachtung auf eine eindeutig solitäre Lebensweise hinweist.
     * - false, wenn dem nicht so ist (eindeutig nicht solitär oder unklar)
     * @return true bei eindeutiger solitärer Lebensweise, sonst false
     */
    public boolean getSolitary() {
        return solitary;
    }

    // --- KONSTRUKTOR ---

    /**
     * Konstruktor für eine Observation einer Lasioglossum Calceatum
     * und Angabe, ob die Lasioglossum calceatum aus einer Zucht kommt
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale oder solitäre Lebensweise hervorgeht.
     * Vorbedingungen:
     * <li>observations, dateTime und comment dürfen nicht null sein</li>
     * <li>solitary und social dürfen nicht beide true sein</li>
     * Nachbedingungen:
     * <li>Das Objekt ist initialisiert./li>
     * <li>Die neue Beobachtung ist in OBSERVATIONS registriert.</li>
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment  Kommentar zur Observation
     * @param zucht    Ob die Lasioglossum Calceatum aus einer Zucht kommt (null, wenn keine Angabe)
     * @param social   Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     * @param solitary Ob aus dieser Beobachtung eindeutig eine solitäre Lebensweise hervorgeht
     */
    public LasioglossumCalceatum(ObservationList observations, LocalDateTime dateTime, String comment, boolean zucht, boolean social,
            boolean solitary) {
        this(observations, dateTime, comment, null, null, zucht, social, solitary);
    }

    /**
     * Konstruktor für eine Observation einer Lasioglossum Calceatum
     * mit Chip-Zahl und Angabe, ob die Lasioglossum Calceatum aus einer Zucht kommt
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale oder solitäre Lebensweise hervorgeht.
     * Vorbedingungen:
     * <li>observations, dateTime und comment dürfen nicht null sein</li>
     * <li>solitary und social dürfen nicht beide true sein</li>
     * Nachbedingungen:
     * <li>Das Objekt ist initialisiert./li>
     * <li>Die neue Beobachtung ist in OBSERVATIONS registriert.</li>
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment  Kommentar zur Observation
     * @param chip     Chip-Zahl des beobachteten Individuums
     * @param zucht    Ob die Lasioglossum Calceatum aus einer Zucht kommt (null, wenn keine Angabe)
     * @param social   Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     * @param solitary Ob aus dieser Beobachtung eindeutig eine solitäre Lebensweise hervorgeht
     */
    public LasioglossumCalceatum(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip, boolean zucht, boolean social,
            boolean solitary) {
        this(observations, dateTime, comment, chip, null, zucht, social, solitary);
    }

    /**
     * Konstruktor für eine Observation einer Lasioglossum Calceatum
     * mit Verweis auf vorangegangene Beobachtung des gleichen Individuums
     * und Angabe, ob die Lasioglossum Calceatum aus einer Zucht kommt
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale oder solitäre Lebensweise hervorgeht.
     * Vorbedingungen:
     * <li>observations, dateTime und comment dürfen nicht null sein</li>
     * <li>solitary und social dürfen nicht beide true sein</li>
     * Nachbedingungen:
     * <li>Das Objekt ist initialisiert./li>
     * <li>Die neue Beobachtung ist in OBSERVATIONS registriert.</li>
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime    Datum & Uhrzeit der Observation
     * @param comment     Kommentar zur Observation
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     * @param zucht       Ob die Lasioglossum Calceatum aus einer Zucht kommt (null, wenn keine Angabe)
     * @param social      Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     * @param solitary    Ob aus dieser Beobachtung eindeutig eine solitäre Lebensweise hervorgeht
     */
    public LasioglossumCalceatum(ObservationList observations, LocalDateTime dateTime, String comment, Bee previousBee, boolean zucht, boolean social,
            boolean solitary) {
        this(observations, dateTime, comment, null, previousBee, zucht, social, solitary);
    }

    /**
     * Konstruktor für eine Observation einer Lasioglossum Calceatum
     * mit Chip-Zahl & Verweis auf vorangegangene Beobachtung des gleichen Individuums
     * und Angabe, ob die Lasioglossum Calceatum aus einer Zucht kommt
     * sowie Angabe, ob aus dieser Beobachtung eine eindeutig soziale oder solitäre Lebensweise hervorgeht.
     * <p>
     * Vorbedingungen:
     * <li>observations, dateTime und comment dürfen nicht null sein</li>
     * <li>Falls die Markierung und die Referenz auf eine frühere Beobachtung angegeben werden, müssen sich diese auf dasselbe Individuum zeigen.</li>
     * <li>solitary und social dürfen nicht beide true sein</li>
     * Nachbedingungen:
     * <li>Das Objekt ist initialisiert./li>
     * <li>Die neue Beobachtung ist in OBSERVATIONS registriert.</li>
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime    Datum & Uhrzeit der Observation
     * @param comment     Kommentar zur Observation
     * @param chip        Chip-Zahl des beobachteten Individuums
     * @param previousBee Vorangegangene Observation des gleichen Individuums
     * @param zucht       Ob die Lasioglossum Calceatum aus einer Zucht kommt (null, wenn keine Angabe)
     * @param social      Ob aus dieser Beobachtung eindeutig eine soziale Lebensweise hervorgeht
     * @param solitary    Ob aus dieser Beobachtung eindeutig eine solitäre Lebensweise hervorgeht
     * @throws IllegalArgumentException Falls Liste aller Observationen, Zeitstempel oder Kommentar null sind oder die Werte für solitäres und soziales Verhalten gleich sind
     */
    public LasioglossumCalceatum(ObservationList observations, LocalDateTime dateTime, String comment, Integer chip, Bee previousBee, boolean zucht,
            boolean social, boolean solitary) {
        if (observations == null) {
            throw new IllegalArgumentException("Das System (Liste aller Beobachtungen) darf nicht null sein!");
        }
        if (dateTime == null) {
            throw new IllegalArgumentException("Das Datum und die Uhrzeit dürfen nicht null sein!");
        }
        if (comment == null) {
            throw new IllegalArgumentException("Der Kommentar darf nicht null sein!");
        }
        if (social && solitary) {
            throw new IllegalArgumentException("Die Werte für solitäres und soziales Verhalten dürfen beide nicht true sein!");
        }
        this.dateTime = dateTime;
        this.comment = comment;
        this.chip = chip;
        this.previousBee = previousBee;
        this.zucht = zucht;
        this.social = social;
        this.solitary = solitary;
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
               LASIOGLOSSUM CALCEAUM
                       Eine Beobachtung einer Biene der Art Lasioglossum calceatum.
                       Dies sind Wildbienen.
                       Sie leben meist sozial.
                       Die Art kann auch solitär leben.
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
        return OBSERVATIONS.sameBee(from, to, isAscending, this);
    }

}

