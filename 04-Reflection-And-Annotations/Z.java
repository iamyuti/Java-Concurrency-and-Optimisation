/**
 * Repräsentiert eine Pflanze der Art Z.
 *  - Der Blühzeitraum beträgt 10 Tage.
 *  - Die Pflanze wird von Bienenart W bevorzugt.
 *  - DIe Pflanze ist von Bienenart V alternativ nutzbar.
 *  - Die Pflanze ist für die Bienenart U niemals nutzbar.
 *  Erbt alle @Zusicherungen aus der Klasse Plant.
 */
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Aus Plant: Eine der 3 Methoden visitedByU, visitedByV & visitedByW (anhängig von der Art) gibt immer 0 zurück.",
        geerbt = true)
@Zusicherung(author = "Felix", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Blühzeitraum ist zwischen 0 und 10.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart wird von der Bienenart W bevorzugt.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist von der Bienenart V alternativ nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Diese Pflanzenart ist für die Bienenart U niemals nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "visitedByU gibt immer 0 zurück.")
@Hauptverantwortlicher("Christine")
public class Z extends Plant {

    /**
     * Erstellt eine Pflanze vom Typ Z mit 10 Tagen Blühzeitraum.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze erstellt und Blühzeitraum=10.")
    @Hauptverantwortlicher("Christine")
    public Z() {
        super(10);
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art W bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart von W bevorzugt wird.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isPreferredBy(W bee) {
        return true;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art V als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, da diese Pflanzenart für V alternativ nutzbar ist.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean isAlternativeFor(V bee) {
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
