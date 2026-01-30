import java.util.Iterator;
import java.util.NoSuchElementException;

/**
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
 * Client-kontrolliert:
 *  * - Für den Iterator: hasNext() muss erfolgreich aufgerufen worden sein, bevor next() aufgerufen wird
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
public interface SocialBee extends Bee {

    /**
     * Gibt zurück, ob aus dieser Beobachtung eine soziale Lebensweise hervorgeht.
     * Nachbedingung:
     * - true, wenn die Beobachtung auf eine eindeutig soziale Lebensweise hinweist.
     * - false, wenn dem nicht so ist (eindeutig nicht sozial oder unklar)
     * @return true bei eindeutiger sozialer Lebensweise, sonst false
     */
    boolean getSocial();

    /**
     * Diese Methode gibt einen Iterator über alle Beobachtungen des gleichen Individuums
     * mit sozialer Lebensweise zurück.
     * Vorbedingung: Initialisiertes Objekt
     * Nachbedingung:
     * - Iterator enthält nur Beobachtungen mit sozialer Lebensweise:
     *    * Arten die ausschließlich sozial leben: alle Beobachtungen des Individuums
     *    * Arten die flexibel sind: nur Beobachtungen mit explizit sozialer Lebensweise
     * - enthält keine entfernten Beobachtungen
     * - Änderungen am Datenbestand werden reflektiert
     * @return Iterator über alle Beobachtungen des gleichen Individuums
     * @throws IllegalArgumentException wenn die Liste aller Beobachtungen null ist
     */
    default Iterator<SocialBee> social() {

        // Basisiterator mit sameBee() holen
        Iterator<Bee> base = this.sameBee();

        /*
         * Anonyme Iterator-Klasse:
         * Erzeugt einen Iterator über alle Beobachtungen einer sozialen Biene,
         * aus denen eindeutig eine soziale Lebensweise hervorgeht.
         * Dazu filtert der Iterator dem sameBee()-Iterator als Basis nur die Beobachtungen,
         * die dieselbe Biene betreffen & auf eine eindeutig soziale Lebensweise hinweisen.
         */
        return new Iterator<SocialBee>() {

            /**
             *  Cache für nächste Beobachtung
             */
            private SocialBee nextMatch;

            /**
             * Ob schon eine weitere Beobachtung im Cache ist.
             */
            private boolean nextReady = false;

            /**
             * Sucht die nächste Beobachtung der Iteration,
             * d.h. eine Beobachtung, die sich auf dasselbe Individuum bezieht
             * und aus der eine eindeutig soziale Lebensweise hervorgeht.
             * Nachbedingung: Im Cache ist eine weitere Beobachtung, sofern es eine gibt.
             */
            private void findNext() {
                // Falls Cache noch vorhanden kein Suchen nötig
                if (nextReady)
                    return;
                // Sonst: Basis-Iterator weiter laufen lassen
                while (base.hasNext()) {
                    Bee b = base.next();
                    // Nur soziale Bienen
                    if (!(b instanceof SocialBee sb))
                        continue;
                    // Nur eindeutig soziale Beobachtungen
                    if (sb.getSocial()) {
                        nextMatch = sb;
                        nextReady = true;
                        return;
                    }
                }
                nextMatch = null;
                nextReady = true;
            }

            /**
             * Liefert zurück, ob die Iteration über eine weitere eindeutig soziale Beobachtung
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
             * Nachbedingung: Gibt nächste Beobachtung dieses Individuums zurück, aus der soziale Lebensweise hervorgeht.
             * @return Nächste Beobachtung, aus der soziale Lebensweise hervorgeht.
             * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
             */
            @Override
            public SocialBee next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();
                SocialBee result = nextMatch;
                nextMatch = null;
                nextReady = false;
                return result;
            }
        };
    }
}
