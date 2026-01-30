import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * === KONKRETE KLASSE FÜR TESTUNG ===
 * === NICHT ZUM EIGENTLICHEN SYSTEM GEHÖREND ===
 * === TESTET: Interface Wasp ===
 *
 * Eine Beobachtung eines Tiers einer Wespenart. Zu dieser
 * gehören unter anderem auch alle Bienen. Jede Biene ist eine Wespe,
 * aber bei weitem nicht alle Wespen sind Bienen.
 * Viele Wespenarten sind keine Bestäuber.<p>
 *
 * Invarianten: Erbt alle Invarianten von Observation. Dies sind:<p>
 * - Aus Observation: Datum und Uhrzeit sind nicht null.<br>
 * - Aus Observation: Kommentar ist nicht null.<br>
 * - Aus Observation: Nach Erzeugung ändern sich Datum, Uhrzeit und Kommentar nicht.<br>
 * - Aus Observation: Alle Beobachtungen werden bei Erzeugung im System (Liste aller Beobachtungen) registriert.<p>
 *
 * History Constraints: Erbt alle History Constraints von Observation. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<p>
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von Observation<br>
 * - Kein Subtyp von Pollinator, da viele Wespenarten keine Bestäuber sind.<br>
 * - Pollinator ist auch kein Untertyp von Wasp, weil sonst alle Bestäuberuntertypen auch Wespen wären
 *    (Für FlowerFly ist dies aber z.B. explizit ausgeschlossen.)<br>
 * - Über Bee indirekter Obertyp aller offenen Typen (außer FlowerFly, Begründung siehe dort).<br>
 */
public class WaspTest implements Wasp {

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

    // --- KONSTRUKTOR ---

    /**
     * Konstruktor für eine Observation einer Wespe (zur Testung)
     * Vorbedingung: Alle Argumente nicht null
     * Nachbedingung: Das Objekt ist initialisiert
     * @param observations Liste aller Observationen (System) in dem die Beobachtung registriert wird
     * @param dateTime Datum & Uhrzeit der Observation
     * @param comment Kommentar zur Observation
     */
    public WaspTest(ObservationList observations, LocalDateTime dateTime, String comment) {
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
               WASP
                   Beobachtung eines Tiers einer Wespenart.
                   Viele Wespen sind keine Bestäuber.
                   Alle Bienen sind Wespen, aber bei weitem nicht alle Wespen sind Bienen.
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
}

