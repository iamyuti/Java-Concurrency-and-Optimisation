
/**
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
public interface Wasp extends Observation {
}
