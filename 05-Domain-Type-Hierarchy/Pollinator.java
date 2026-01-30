
/**
 * Eine Beobachtung irgendeines Insekts, das als üblicher Bestäuber
 * von Blütenpflanzen bekannt ist. Dieses Interface ist ein Obertyp
 * von Bienen aller Art, Schwebfliegen sowie vieler Arten
 * von Käfern und Schmetterlingen.<p>
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
 * - Keine Beziehung zu Wasp. Begründung siehe Wasp.<br>
 * - Ansonsten indirekter Obertyp aller anderen offenen Typen.
 */
public interface Pollinator extends Observation {
}
