/**
 * Repräsentiert eine Pflanze der Art X.
 * Für diese Pflanze gilt:
 *  - Der Blühzeitraum beträgt 9 Tage.
 *  - Die Pflanze wird von Bienenart U bevorzugt.
 *  - Die Pflanze ist von Bienenart W alternativ nutzbar.
 *  - Die Pflanze ist für die Bienenart V niemals nutzbar.
 *  Erbt alle @Zusicherungen aus der Klasse Plant.
 */
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Eine der 3 Methoden visitedByU, visitedByV & visitedByW (anhängig von der Art) gibt immer 0 zurück.",
        geerbt = true)
@Zusicherung(author = "Felix", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Blühzeitraum ist zwischen 0 und 9.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart wird von der Bienenart U bevorzugt.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist von der Bienenart W alternativ nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist für die Bienenart V niemals nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "visitedByV gibt immer 0 zurück.")
@Hauptverantwortlicher("Christine")
public class X extends Plant {

    // --- KONSTRUKTOR ----

    /**
     * Erstellt eine Pflanze vom Typ X mit 9 Tagen Blühzeitraum.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze erstellt und Blühzeitraum=9.")
    @Hauptverantwortlicher("Christine")
    public X() {
        super(9);
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art U bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart von U bevorzugt wird.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isPreferredBy(U bee) {
        return true;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art W als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart für W alternativ nutzbar ist.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isAlternativeFor(W bee) {
        return true;
    }

    /**
     * Signalisiert der Biene, dass sie erfolgreich diese Pflanze besucht hat.
     * @param bee die Biene
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Der Biene wurde ein erfolgreicher Besuch signalisiert," +
                    " wenn diese Pflanzenart für die Bienenart U nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    @Override
    void hasVisited(Bee bee) {
        bee.hasVisited(this);
    }

}
