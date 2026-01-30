import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Eine Beobachtung einer Biene einer Art, die solitär leben kann.
 * Solitäre Bienen bilden keinen Staat.
 * Geht aus einer Beobachtung eines Individuums eine solitäre
 * (also nicht kommunale oder soziale) Lebensweise hervor,
 * wird das bei der Objekterzeugung angegeben, außer wenn die Art
 * niemals kommunal oder sozial lebt.<p>
 *
 * Invariante: Erbt alle Invarianten von WildBee. Dies sind:<br>
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
 * - getSolitary() liefert für eine Beobachtung immer denselben Wert.<br>
 * - solitary() liefert für das gleiche Individuum immer die gleiche Menge
 *   von Beobachtungen (außer bei remove())<p>
 *
 * History Constraints: Erbt alle History Constraints von WildBee. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 * Client-kontrolliert:
 * - Für den Iterator: hasNext() muss erfolgreich aufgerufen worden sein, bevor next() aufgerufen wird
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von WildBee, da alle solitären Bienen Wildbienen sind.<br>
 * - Keine Beziehung zu SocialBee: Begründung siehe SocialBee (Solitäre und Soziale Lebensweise schließen sich aus.)
 */
public interface SolitaryBee extends WildBee {

    /**
     * Gibt zurück, ob aus dieser Beobachtung eine solitäre Lebensweise hervorgeht.
     * Nachbedingung:
     * - true, wenn die Beobachtung auf eine eindeutig solitäre Lebensweise hinweist.
     * - false, wenn dem nicht so ist (eindeutig nicht solitär oder unklar)
     * @return true bei eindeutiger solitärer Lebensweise, sonst false
     */
    boolean getSolitary();

    /**
     * Die Methode gibt einen Iterator über jede Beobachtung des gleichen Individuums zurück, aus der eine solitäre
     * Lebensweise dieses Individuums hervorgeht Vorbedingung: Initialisiertes Objekt Nachbedingung: Nur Beobachtungen,
     * aus der eine solitäre (nicht kommunale oder soziale) Lebensweise dieses Individuums hervorgeht (bzw. über alle
     * Beobachtungen des Individuums, wenn die Art niemals kommunal oder sozial lebt).
     * Vorbedingung: Liste aller Beobachtungen darf nicht null sein
     * Nachbedingung:
     * - Iterator enthält nur Beobachtungen, aus denen eine solitäre
     *   (nicht kommunale oder soziale) Lebensweise hervorgeht
     * - Falls die Art NIEMALS kommunal oder sozial lebt:
     *   Iterator über Beobachtungen des gleichen Individuums
     * - aufsteigend sortiert, entspricht sameBee()
     * - Enthält keine entfernten Beobachtungen
     * @return Iterator über alle Beobachtungen, aus denen solitäre Lebensweise hervorgeht.
     * @throws IllegalArgumentException wenn die Liste aller Beobachtungen null ist
     */
    default Iterator<SolitaryBee> solitary() {

        // Basisiterator mit sameBee() holen
        Iterator<Bee> base = sameBee();

        /*
         * Anonyme Iterator-Klasse:
         * Erzeugt einen Iterator über alle Beobachtungen einer solitären Biene,
         * aus denen eindeutig eine solitäre Lebensweise hervorgeht.
         * Dazu filtert der Iterator dem sameBee()-Iterator als Basis nur die Beobachtungen,
         * die dieselbe Biene betreffen & auf eine eindeutig solitäre Lebensweise hinweisen.
         */
        return new Iterator<SolitaryBee>() {

            /**
             *  Cache für nächste Beobachtung
             */
            private SolitaryBee nextMatch;

            /**
             * Ob schon eine weitere Beobachtung im Cache ist.
             */
            private boolean nextReady = false;

            /**
             * Sucht die nächste Beobachtung der Iteration,
             * d.h. eine Beobachtung, die sich auf dasselbe Individuum bezieht
             * und aus der eine eindeutig solitäre Lebensweise hervorgeht.
             * Nachbedingung: Im Cache ist eine weitere Beobachtung, sofern es eine gibt.
             */
            private void findNext() {
                // Falls Cache noch vorhanden kein Suchen nötig
                if (nextReady) return;
                // Sonst: Basis-Iterator weiter laufen lassen
                while (base.hasNext()) {
                    Bee b = base.next();
                    // Nur solitäre Bienen
                    if (!(b instanceof SolitaryBee sb)) continue;
                    // Nur eindeutig solitäre Beobachtungen
                    if (sb.getSolitary()) {
                        nextMatch = sb;
                        nextReady = true;
                        return;
                    }
                }
                nextMatch = null;
                nextReady = true;
            }

            /**
             * Liefert zurück, ob die Iteration über eine weitere eindeutig solitäre Beobachtung
             * bzgl. derselben Biene verfügt.
             * Nachbedingung: Gibt true zurück, wenn next() eine Beobachtung liefern wird.
             * @return true, wenn es eine weitere über next() abrufbare Beobachtung gibt.
             */
            @Override
            public boolean hasNext() {
                findNext();
                return nextMatch != null;
            }

            /**
             * Gibt die nächste Beobachtung der Iteration zurück.
             * Vorbedingung/ Client-kontrollierter History-Constraint: hasNext() muss aufgerufen worden sein und true geliefert haben.
             * Nachbedingung: Gibt nächste Beobachtung dieses Individuums zurück, aus der solitäre Lebensweise hervorgeht.
             * @return Nächste Beobachtung, aus der solitäre Lebensweise hervorgeht.
             * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
             */
            @Override
            public SolitaryBee next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();
                SolitaryBee result = nextMatch;
                nextMatch = null;
                nextReady = false;
                return result;
            }
        };
    }
}
