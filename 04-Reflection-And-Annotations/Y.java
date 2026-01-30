/**
 * Repräsentiert eine Pflanze der Art X.
 * Für diese Pflanze gilt:
 *  - Der Blühzeitraum beträgt 8 Tage.
 *  - Die Pflanze wird von Bienenart V bevorzugt.
 *  - Die Pflanze ist von Bienenart U alternativ nutzbar.
 *  - Die Pflanze ist für die Bienenart W niemals nutzbar.
 *  Erbt alle @Zusicherungen aus der Klasse Plant.
 */
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Aus Plant: Eine der 3 Methoden visitedByU, visitedByV & visitedByW (anhängig von der Art) gibt immer 0 zurück.",
        geerbt = true)
@Zusicherung(author = "Felix", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Blühzeitraum ist zwischen 0 und 8.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart wird von der Bienenart V bevorzugt.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist von der Bienenart U alternativ nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist für die Bienenart W niemals nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "visitedByW gibt immer 0 zurück.")
@Hauptverantwortlicher("Christine")
public class Y extends Plant {

    /**
     * Erstellt eine Pflanze vom Typ Y mit 8 Tagen Blühzeitraum.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze erstellt und Blühzeitraum=8.")
    @Hauptverantwortlicher("Christine")
    public Y() {
        super(8);
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art V bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart von V bevorzugt wird.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isPreferredBy(V bee) {
        return true;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art U als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart für U alternativ nutzbar ist.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isAlternativeFor(U bee) {
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
