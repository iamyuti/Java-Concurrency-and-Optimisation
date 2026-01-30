import java.time.LocalDateTime;
import java.util.*;

/**
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
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von Bestäuber, da alle Bienen Bestäuber sind.<br>
 * - Subtyp von Wasp, da alle Bienen Wespen sind.<br>
 * - Alle anderen offenen Typen sind Subtypen dieses Typs.<p>
 */
public interface Bee extends Pollinator, Wasp {

    /**
     * GETTER: Gibt das Objekt einer früheren Beobachtung des gleichen Individuums zurück,
     * sofern dieser Bezug bekannt ist.<p>
     *
     * Nachbedingung: Gibt Referenz auf frühere Observation zurück, falls bekannt, sonst null
     * @return frühere Beobachtung des gleichen Individuums, sofern eine bekannt
     *         sonst null
     */
    Bee getPreviousObservation();

    /**
     * Getter: Gibt die Chipzahl dieses Objektes zurück, sofern eine bekannt ist.<p>
     *
     * Nachbedingung: Gibt Chip-Nummer zurück, falls vorhanden, sonst null
     * @return numerische Markierung, sofern eine bekannt
     *         sonst null
     */
    Integer getChip();

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
    Iterator<Bee> sameBee();

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
    Iterator<Bee> sameBee(boolean isAscending);

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums
     * im vorgegebenen Zeitraum zurück.
     * Aufsteigend sortiert nach Beobachtungszeitraum
     * <p>
     * Vorbedingung: {@code from <= to}<br>, from, to dürfen nicht null sein
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
    Iterator<Bee> sameBee(LocalDateTime from, LocalDateTime to);

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums
     * im vorgegebenen Zeitraum zurück.
     * Sortierung ist abhängig vom Parameter {@code isAscending}
     * <p>
     * Vorbedingung: {@code from <= to}<br>, from, to dürfen nicht null sein
     * <p>
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
     * @throws IllegalArgumentException falls {@code from > to}, from oder to null, System null
     */
    Iterator<Bee> sameBee(boolean isAscending, LocalDateTime from, LocalDateTime to);

    }
