/**
 * Repräsentiert eine Biene der Art W.
 * Für diese Art gilt:
 *  - Der Aktivitätszeitraum beträgt 10 Tage.
 *  - Die bevorzugte Pflanzenart ist Z.
 *  - Die alternativ nutzbare Pflanzenart ist X.
 *  - Die Pflanzenart Y ist für diese Pflanze nicht nutzbar.
 *  Erbt alle @Zusicherungen aus der Klasse Bee.
 */
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Jede Biene hat genau eine bevorzugte und eine alternative Pflanzenart, die sie besuchen kann.",
        geerbt = true)
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die bevorzugte Pflanzenart wird immer zuerst besucht.",
        geerbt = true)
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Eine der 3 Methoden collectedFromX, collectedFromY & collectedFromZ (anhängig von der Art) gibt immer 0 zurück.",
        geerbt = true)
@Zusicherung(author = "Felix", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Aktivitätszeitraum ist zwischen 0 und 10.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die bevorzugte Pflanzenart ist Z.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die alternative Pflanzenart ist X.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die Pflanzenart Y ist nicht nutzbar.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "collectedFromY gibt immer 0 zurück.")
@Hauptverantwortlicher("Christine")
public class W extends Bee {

    // --- KONSTRUKTOR ----

    /**
     * Erstellt eine Biene der Art W mit 10 Tagen Aktivitätszeitraum.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Biene erstellt und Aktivitätszeitraum=10.")
    @Hauptverantwortlicher("Christine")
    public W() {
        super(10);
    }

    /**
     * Gibt zurück, ob die Biene die übergebene Pflanzenart bevorzugt.
     * @param p die zu überprüfende Pflanze
     * @return true, wenn die Pflanze von der Biene bevorzugt wird.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "p != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene die übergebene Pflanze bevorzugt.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean prefersPlant(Plant p) {
        return p.isPreferredBy(this);
    }

    /**
     * Gibt zurück, ob die Biene die übergebene Pflanzenart als Alternative zur bevorzugten Pflanzenart nutzen kann.
     * @param p die zu überprüfende Pflanze
     * @return true, wenn die Pflanze von der Biene alternativ nutzbar ist.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "p != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene die übergebene Pflanzenart als Alternative nutzen kann.")
    @Hauptverantwortlicher("Christine")
    @Override
    boolean alternativePlant(Plant p) {
        return p.isAlternativeFor(this);
    }

    /**
     * Besucht die übergebene Pflanze.
     * @param p zu besuchende Pflanze
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "p != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Zähler der Pflanze bezüglich der Besuche von Bienen dieser Art ist um 1 erhöht.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Zähler dieser Biene bezüglich der Besuche von Pflanzen dieser Art ist um 1 erhöht.")
    @Hauptverantwortlicher("Christine")
    @Override
    void visit(Plant p) {
        p.getVisitedBy(this);
    }
}