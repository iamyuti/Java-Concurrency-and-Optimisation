import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Eine Beobachtung einer Biene einer kommunalen Art.
 * Solitäre Bienen (siehe SolitaryBee) leben alleine, also in jeweils einem
 * eigenen Nest mit ihrem Nachwuchs. Kommunale Bienen leben dagegen in
 * Nestern, die sich mehrere ausgewachsene Bienen teilen.
 * Alle kommunalen Bienen können auch alleine, also solitär leben.
 * Die Zuordnung zum Typ CommunalBee hängt nur von der Bienenart ab,
 * zu der die beobachtete Biene gehört, also davon,
 * ob Bienen dieser Art auch kommunal leben können, nicht davon,
 * ob das beobachtete Individuum tatsächlich kommunal lebt.
 * Geht aus der Beobachtung eines Individuums eine kommunale Lebensweise
 * hervor, wird das bei der Objekterzeugung angegeben.<p>
 *
 * Invarianten: Erbt alle Invarianten von SolitaryBee. Dies sind:<br>
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
 * - communal() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove())<p>
 *
 * History Constraints: Erbt alle History Constraints von SolitaryBee. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von SolitaryBee, da alle kommunalen Bienen auch solitär leben können.
 *   Solitäre Bienen sind aber nicht kommunal, daher Beziehung nicht andersrum.<br>
 * - Keine Beziehung zu SocialBee; kommunale Bienen Leben alleine, soziale Bienen in Staaten.
 * Dies schließt sich aus. Auch wenn einige Arten ggf. auf verschiedene Weisen Leben können,
 * wird niemals gelten, dass alle kommunale Bienen auch sozial Leben können oder andersrum.
 */
public interface CommunalBee extends SolitaryBee {

    /**
     * Die Methode gibt einen Iterator über jede Beobachtung des gleichen Individuums
     * zurück, aus der eine kommunale Lebensweise dieses Individuums hervorgeht.
     * Vorbedingung: initialisiertes Objekt
     * Nachbedingung:
     * - Iterator über Beobachtungen des gleichen Individuums
     * - Nur Beobachtungen, aus der eine kommunale Lebensweise dieses Individuums hervorgeht.
     * - Aufsteigend sortiert nach Beobachtungszeit (durch sameBee() garantiert)
     * - Enthält keine entfernten Beobachtungen
     * @return Iterator für alle Beobachtungen mit kommunaler Lebensweise
     * @throws IllegalArgumentException wenn das System (Liste aller Beobachtungen) null ist
     */
    Iterator<CommunalBee> communal();
}
