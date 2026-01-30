/**
 * Stellt eine Biene irgendeiner Art dar.
 * Jede Biene hat
 *  - einen Aktivitätszeitraum (Anzahl an Tagen, für die die Biene aktiv ist).
 *  - eine bevorzugte Pflanzenart, die immer zuerst besucht wird.
 *  - eine alternative Pflanzenart, die nur besucht wird, wenn die bevorzugte Pflanzenart nicht verfügbar ist.
 */
@Hauptverantwortlicher("Christine")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Jede Biene hat genau eine bevorzugte und eine alternative Pflanzenart, die sie besuchen kann.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Die bevorzugte Pflanzenart wird immer zuerst besucht.")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Eine der 3 Methoden collectedFromX, collectedFromY & collectedFromZ (anhängig von der Art) gibt immer 0 zurück.")
public abstract class Bee {

    // --- VARIABLEN ----

    /**
     * Anzahl der Tage, die die Biene noch aktiv ist (restlicher Aktivitätszeitraum)
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "Aktivitätszeitraum >= 0.")
    private int activeDaysLeft;

    /**
     * Zähler für die Anzahl Besuche bei einer Pflanze der Art X
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "collectedFromX >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert collectedFromX kann nur erhöht werden.")
    private int collectedFromX;

    /**
     * Zähler für die Anzahl Besuche bei einer Pflanze der Art Y
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "collectedFromY >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert collectedFromY kann nur erhöht werden.")
    private int collectedFromY;

    /**
     * Zähler für die Anzahl Besuche bei einer Pflanze der Art Z
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "collectedFromZ >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert collectedFromZ kann nur erhöht werden.")
    private int collectedFromZ;

    // --- KONSTRUKTOR ----

    /**
     * Erstelle eine Biene mit dem übergebenen Aktivitätszeitraum.
     * @param activeDays Anzahl der Tage, die die Biene aktiv ist
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "activeDays > 0")
    @Hauptverantwortlicher("Christine")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze erstellt und Aktivitätszeitraum entspricht übergebener Anzahl an Tagen.")
    public Bee(int activeDays) {
        activeDaysLeft = activeDays;
        collectedFromX = 0;
        collectedFromY = 0;
        collectedFromZ = 0;
    }

    // --- METHODEN ----

    /**
     * Gibt zurück, ob die Biene noch aktiv ist, d.h. ihr Aktivitätszeitraum noch nicht abgelaufen ist.
     * @return true, wenn die Biene aktiv ist, sonst false
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene aktiv ist.")
    @Hauptverantwortlicher("Christine")
    boolean active() {
        return activeDaysLeft > 0;
    }

    /**
     * Signalisiert dieser Biene, dass ein Tag vergangen ist.
     * Verringert den Aktivitätszeitraum um 1, sofern er nicht bereits 0 ist (Biene inaktiv).
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Der Aktivitätszeitraum wurde um 1 verringert, sofern er > 0 war.")
    @Hauptverantwortlicher("Christine")
    void oneDayOver() {
        if(this.active()) {
            activeDaysLeft--;
        }
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
    abstract boolean prefersPlant(Plant p);

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
    abstract boolean alternativePlant(Plant p);

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
    abstract void visit(Plant p);

    // ANMERKUNG zu den Methoden collectedFrom:
    // Anstelle einer Prüfung mit if (this.prefersPlant(plant) || this.alternativePlant(plant)) vor Hochzählen des Zählers,
    // wäre es auch möglich, die jeweilige Methode in der Unterklasse zu überschreiben und leer zu lassen,
    // wenn kein Hochzählen des Zählers gewünscht ist (die Pflanze nicht nutzbar ist).
    // Der Vorteil wäre, dass die Aufruffolge hier zu Ende wäre und nicht erneut Aufrufe / doppelt dynamisches Binden
    // durch prefersPlant bzw. alternativePlant Aufrufe zwischen Bee und Plant hin und her sendet.
    // Der Nachteil ist aber, dass in jeder Unterklasse eine weitere Methode (leer) implementiert werden müsste.
    // Außerdem hat die Implementierung mit einer Abfrage über if (this.prefersPlant(plant) || this.alternativePlant(plant))
    // folgenden entscheidenden Vorteil:
    // Um eine Vorliebe zu ändern, muss nur an einer einzigen Stelle Code verändert werden. Man muss dafür lediglich
    // in der entsprechenden Plant-Unterklasse den Rückgabewert für isPreferred bzw. isAlternative ändern.
    // Alle daraus resultierenden Abläufe (Hochdrehen der Zähler etc.) ändern sich dadurch automatisch.

    /**
     * Simuliert den erfolgreichen Besuch einer Pflanze der Art X.
     * @param plant die besuchte Pflanze
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "plant != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "collectedFromX um 1 erhöht, sofern die Pflanzenart X für diese Bienenart nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void hasVisited(X plant) {
        if (this.prefersPlant(plant) || this.alternativePlant(plant)) collectedFromX++;
    }

    /**
     * Simuliert den erfolgreichen Besuch einer Pflanze der Art Y.
     * @param plant die besuchte Pflanze
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "plant != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "collectedFromY um 1 erhöht, sofern die Pflanzenart Y für diese Bienenart nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void hasVisited(Y plant) {
        if (this.prefersPlant(plant) || this.alternativePlant(plant)) collectedFromY++;
    }

    /**
     * Simuliert den erfolgreichen Besuch einer Pflanze der Art Z.
     * @param plant die besuchte Pflanze
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "plant != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "collectedFromZ um 1 erhöht, sofern die Pflanzenart Z für diese Bienenart nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void hasVisited(Z plant) {
        if (this.prefersPlant(plant) || this.alternativePlant(plant)) collectedFromZ++;
    }

    /**
     *  Gibt zurück, durch wie viele Blütenbesuche der Pflanzenart X
     *  die Biene Blütennektar und Pollen gesammelt hat.
     * @return Anzahl der Blütenbesuche bei Pflanzenart X
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche bei Pflanzenart X zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn die Pflanzenart X von dieser Bienenart nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int collectedFromX() {
        return this.collectedFromX;
    }

    /**
     *  Gibt zurück, durch wie viele Blütenbesuche der Pflanzenart Y
     *  die Biene Blütennektar und Pollen gesammelt hat.
     * @return Anzahl der Blütenbesuche bei Pflanzenart Y
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche bei Pflanzenart Y zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn die Pflanzenart Y von dieser Bienenart nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int collectedFromY() {
        return this.collectedFromY;
    }

    /**
     *  Gibt zurück, durch wie viele Blütenbesuche der Pflanzenart Z
     *  die Biene Blütennektar und Pollen gesammelt hat.
     * @return Anzahl der Blütenbesuche bei Pflanzenart Z
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche bei Pflanzenart Z zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn die Pflanzenart Z von dieser Bienenart nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int collectedFromZ() {
        return this.collectedFromZ;
    }

}
