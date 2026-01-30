import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Eine Beobachtung einer Wildbiene. Alle solitär lebenden Bienen sind Wildbienen.
 * Honigbienen sind keine Wildbienen.
 * Hummeln sind auch Wildbienen.
 * Ist bei Beobachtung eines Individuums bekannt,
 * dass es aus einer oder keiner Zucht stammt, wird das bei der Objekterzeugung angegeben.<p>
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
 *   von Beobachtungen (außer bei remove())<br>
 * - getZucht() liefert für eine Beobachtung immer denselben Wert.<br>
 * - wild() liefert für das gleiche Individuum und denselben Parameter immer
 *   die gleiche Menge von Beobachtungen (außer bei remove()).<p>
 *
 * History Constraints: Erbt alle History Constraints aus Bee. Dies sind:<br>
 * Server-kontrolliert:
 * - Aus Observation: Nach remove() bleibt valid() dauerhaft false.<br>
 * - Aus Observation: Entfernte Beobachtungen erscheinen nicht mehr in Iteratoren.<br>
 * Client-kontrolliert:
 * - Für den Iterator: hasNext() muss erfolgreich aufgerufen worden sein, bevor next() aufgerufen wird
 *
 * Untertypbeziehungen:<br>
 * - Subtyp von Bee, da alle Wildbienen Bienen sind.<br>
 * - Keine Beziehung zu den sozialen Bienen. Begründung siehe SocialBee.<br>
 * - Alle anderen offenen Typen außer HoneyBee sind indirekt Untertypen der Wildbienen.<br>
 * - Begründung fehlende Beziehung zu Honigbienen siehe HoneyBee.
 */
public interface WildBee extends Bee {

    /**
     * Gibt zurück, ob die beobachtete Wildbiene aus Zucht stammt
     * Nachbedingung:
     * - Gibt true zurück, wenn Wildbiene aus einer Zucht stammt
     * - Gibt false zurück, wenn Wildbiene aus keiner Zucht stammt.
     * - Gibt null zurück, wenn nichts darüber bekannt ist
     * @return true = aus Zucht, false = nicht aus Zucht, null = unbekannt
     */
    Boolean getZucht();

    /**
     * Die Methode gibt einen Iterator über jede Beobachtung des gleichen
     * Individuums zurück, wenn diese Beobachtung keine bzw. eine (ab
     * hängig vom Parameterwert) Abstammung aus einer Zucht angibt
     * Vorbedingung: Liste aller Beobachtungen darf nicht null sein
     * Nachbedingung:
     * - Iterator über Beobachtungen des gleichen Individuums
     * - Wenn zucht == true: nur Beobachtungen mit getZucht() == true
     * - Wenn zucht == false: nur Beobachtungen mit getZucht() == false
     * - Wenn zucht == null: nur Beobachtungen mit getZucht() == null
     * - aufsteigend sortiert, entspricht sameBee()
     * - Enthält keine entfernten Beobachtungen
     * @param zucht gibt an, ob die Abstammung aus einer Zucht ist
     * @return Iterator über alle Beobachtungen des gleichen Individuums
     * @throws IllegalArgumentException wenn die Liste aller Beobachtungen null ist
     */
    default Iterator<WildBee> wild (Boolean zucht){

        // Basisiterator mit sameBee() holen
        Iterator<Bee> base = sameBee();

        /*
         * Anonyme Iterator-Klasse:
         * Erzeugt einen Iterator über alle Beobachtungen einer Wildbiene,
         * aus denen ein vorgegebener Zuchtstatus (aus Zucht o. wild) hervorgeht.
         * Dazu filtert der Iterator dem sameBee()-Iterator als Basis nur die Beobachtungen,
         * die dieselbe Wildbiene betreffen & auf den Zuchtstatus hinweisen.
         */
        return new Iterator<WildBee>() {

            /**
             *  Cache für nächste Beobachtung
             */
            private WildBee nextMatch;

            /**
             * Ob schon eine weitere Beobachtung im Cache ist.
             */
            private boolean nextReady = false;

            /**
             * Sucht die nächste Beobachtung der Iteration,
             * d.h. eine Beobachtung, die sich auf dasselbe Individuum bezieht
             * und aus der angegebene Zuchtstatus hervorgeht.
             * Nachbedingung: Im Cache ist eine weitere Beobachtung, sofern es eine gibt.
             */
            private void findNext() {
                // Falls Cache noch vorhanden kein Suchen nötig
                if (nextReady) return;
                // Sonst: Basis-Iterator weiter laufen lassen
                while (base.hasNext()) {
                    Bee b = base.next();
                    // Nur Wildbienen
                    if (!(b instanceof WildBee wb)) continue;
                    // Nur Beobachtungen, die dem gewünschten Zuchtstatus entsprechen
                    if (wb.getZucht() == zucht) {
                        nextMatch = wb;
                        nextReady = true;
                        return;
                    }
                }
                nextMatch = null;
                nextReady = true;
            }

            /**
             * Liefert zurück, ob die Iteration über eine weitere Beobachtung entspr. des Zuchtstatus
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
             * Nachbedingung: Gibt nächste Beobachtung dieses Individuums zurück, aus der Lebensweise entspr. Zuchtstatus hervorgeht.
             * @return Nächste Beobachtung, aus der Lebensweise entspr. Zuchtstatus hervorgeht.
             * @throws NoSuchElementException wenn es keine nächste Beobachtung gibt.
             */
            @Override
            public WildBee next() {
                if (!hasNext())
                    throw new java.util.NoSuchElementException();
                WildBee result = nextMatch;
                nextReady = false;
                nextMatch = null;
                return result;
            }
        };
    }
}
