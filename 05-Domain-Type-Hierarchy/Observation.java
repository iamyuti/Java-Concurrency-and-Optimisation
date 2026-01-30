import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Dieses Interface repräsentiert eine Beobachtung im Kontext des Projekts
 * und ist damit der Obertyp für alle anderen Typen im System.<p>
 * Jede Beobachtung wird mit Datum, Uhrzeit und einem beschreibenden Kommentar erfasst.
 * Beobachtungen können logisch entfernt werden, bleiben aber im Speicher existent.<p>
 *
 * Invarianten:<br>
 * - Datum und Uhrzeit sind nicht null.<br>
 * - Kommentar ist nicht null.<br>
 * - Nach Erzeugung ändern sich Datum, Uhrzeit und Kommentar nicht.<br>
 * - Alle Beobachtungen werden bei Erzeugung im System (Liste aller Beobachtungen) registriert.<p>
 *
 * History Constraints:<br>
 * Server-kontrolliert:
 * - Nach remove() bleibt valid() dauerhaft false.<br>
 * - Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<p>
 *
 * Untertypbeziehungen: Pollinator, Wasp und alle weiteren Typen von spezifischen Beobachtungen
 */
public interface Observation {

    // --- GETTER ---

    /**
     * Getter: Liefert Datum und Uhrzeit der Observation zurück.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung: Gibt unveränderbare LocalDateTime zurück;
     * @return Datum und Uhrzeit der Beobachtung
     */
    LocalDateTime getDateTime();

    /**
     * Getter: Liefert den Kommentar zur Observation zurück.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung: Gibt unveränderbare String zurück
     * @return beschreibender Kommentar zur Beobachtung
     */
    String getComment();

    // --- METHODEN ---

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
    void remove();

    /**
     * Diese Methode prüft, ob die Beobachtung noch im Datenbestand ist.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Gibt true zurück, wenn die Beobachtung in OBSERVATIONS enthalten ist.
     * - Gibt false zurück, wenn remove() aufgerufen wurde.
     * @return true, wenn Beobachtung gültig; false, wenn entfernt
     */
    boolean valid();

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
    Iterator<Observation> earlier();

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
    Iterator<Observation> later();
}
