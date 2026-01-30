/**
 * Stellt eine Pflanze irgendeiner Art dar.
 * Jede Pflanze hat
 *  - einen Blühzeitraum (Anzahl an Tagen, für die die Pflanze in Blüte steht).
 */
@Hauptverantwortlicher("Christine")
@Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
        zusicherung = "Eine der 3 Methoden visitedByU, visitedByV & visitedByW (anhängig von der Art) gibt immer 0 zurück.")
public abstract class Plant {

    // --- VARIABLEN ----

    /**
     * Zähler für die Besuche einer Biene der Art U
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "visitedByU  >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert visitedByU kann nur erhöht werden.")
    private int visitedByU;

    /**
     * Zähler für die Besuche einer Biene der Art V
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "visitedByV  >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert visitedByV kann nur erhöht werden.")
    private int visitedByV;

    /**
     * Zähler für die Besuche einer Biene der Art W
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "visitedByW  >= 0")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.SCHC,
            zusicherung = "Der Wert visitedByW kann nur erhöht werden.")
    private int visitedByW;

    /**
     * Anzahl der Tage, die die Pflanze noch in Blüte steht (restlicher Blühzeitraum)
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "Blühzeitraum >= 0.")
    private int bloomingDaysLeft;

    // --- KONSTRUKTOR ----

    /**
     * Erstelle eine Pflanze mit dem übergebenen Blühzeitraum.
     * @param bloomingDays Anzahl der Tage, die die Pflanze blüht
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bloomingDaysLeft >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze erstellt und Blühzeitraum entspricht übergebener Anzahl an Tagen.")
    @Hauptverantwortlicher("Christine")
    public Plant(int bloomingDays) {
        bloomingDaysLeft = bloomingDays;
        visitedByU = 0;
        visitedByV = 0;
        visitedByW = 0;
    }

    // --- METHODEN ----

    /**
     * Gibt zurück, ob die Pflanze noch in Blüte steht.
     *
     * @return true, wenn die Pflanze in Blüte steht, sonst false
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Pflanze in Blüte steht.")
    @Hauptverantwortlicher("Christine")
    boolean inBloom() {
        return bloomingDaysLeft > 0;
    }

    /**
     * Signalisiert dieser Pflanze, dass ein Tag vergangen ist.
     * Verringert den Blühzeitraum um 1, sofern er nicht bereits 0 ist (Pflanze inaktiv).
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Der Blühzeitraum wurde um 1 verringert, sofern er > 0 war.")
    @Hauptverantwortlicher("Christine")
    void oneDayOver() {
        if (this.inBloom()) {
            bloomingDaysLeft--;
        }
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art U bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Pflanzenart U diese Pflanzenart bevorzugt, sonst false.
     *         Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Bienenart U diese Pflanzenart bevorzugt.")
    @Hauptverantwortlicher("Christine")
    boolean isPreferredBy(U bee) {
        return false;
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art V bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Pflanzenart V diese Pflanzenart bevorzugt, sonst false.
     *               Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Bienenart V diese Pflanzenart bevorzugt.")
    @Hauptverantwortlicher("Christine")
    boolean isPreferredBy(V bee) {
        return false;
    }

    /**
     * Gibt zurück, ob die Pflanze von der Bienen Art W bevorzugt wird.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Pflanzenart W diese Pflanzenart bevorzugt, sonst false.
     *               Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Bienenart W diese Pflanzenart bevorzugt.")
    @Hauptverantwortlicher("Christine")
    boolean isPreferredBy(W bee) {
        return false;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art U als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Art U diese Pflanzenart als Alternative nutzen kann, sonst false.
     *               Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene der Art U diese als Alternative nutzen kann.")
    @Hauptverantwortlicher("Christine")
    boolean isAlternativeFor(U bee) {
        return false;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art V als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Art V diese Pflanzenart als Alternative nutzen kann, sonst false.
     *               Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene der Art V diese als Alternative nutzen kann.")
    @Hauptverantwortlicher("Christine")
    boolean isAlternativeFor(V bee) {
        return false;
    }

    /**
     * Gibt zurück, ob die Pflanze von der übergebenen Bienen Art W als Alternative zur bevorzugten Art nutzbar ist.
     *
     * @param bee die übergebene Biene.
     * @return true, wenn die Biene der Art W diese Pflanzenart als Alternative nutzen kann, sonst false.
     *               Für die allgemeine Biene (keine spezifische Art) ist das immer false.
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt zurück, ob die Biene der Art W diese als Alternative nutzen kann.")
    @Hauptverantwortlicher("Christine")
    boolean isAlternativeFor(W bee) {
        return false;
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
    abstract void hasVisited(Bee bee);

    // ANMERKUNG zu den Methoden getVisited:
    // Anstelle einer Prüfung mit if (this.isPreferredBy(bee) || this.isAlternativeFor(bee)) vor Hochzählen des Zählers,
    // wäre es auch möglich, die jeweilige Methode in der Unterklasse zu überschreiben und leer zu lassen,
    // wenn kein Hochzählen des Zählers gewünscht ist (die Pflanze nicht nutzbar ist).
    // Der Vorteil wäre, dass die Aufruffolge mit hasVisited endet und nicht erneut Aufrufe / doppelt dynamisches Binden
    // durch isPreferred bzw. isAlternativeFor Aufrufe zwischen Bee und Plant hin und her sendet.
    // Der Nachteil ist aber, dass in jeder Unterklasse eine weitere Methode (leer) implementiert werden müsste.
    // Außerdem hat die Implementierung mit einer Abfrage über if (this.isPreferredBy(bee) || this.isAlternativeFor(bee))
    // folgenden entscheidenden Vorteil:
    // Um eine Vorliebe zu ändern, muss nur an einer einzigen Stelle Code verändert werden. Man muss dafür lediglich
    // in der entsprechenden Plant-Unterklasse den Rückgabewert für isPreferred bzw. isAlternative ändern.
    // Alle daraus resultierenden Abläufe (Hochdrehen der Zähler etc.) ändern sich dadurch automatisch.

    /**
     * Simuliert den Besuch durch eine Biene vom Typ U
     * @param bee die besuchende Biene
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "visitedByU um 1 erhöht, wenn diese Pflanzenart für die Bienenart U nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void getVisitedBy(U bee) {
        if (this.isPreferredBy(bee) || this.isAlternativeFor(bee)) this.visitedByU++;
        hasVisited(bee);
    }

    /**
     * Simuliert den Besuch durch eine Biene vom Typ V
     * @param bee die besuchende Biene
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "visitedByV um 1 erhöht, wenn diese Pflanzenart für die Bienenart V nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void getVisitedBy(V bee) {
        if (this.isPreferredBy(bee) || this.isAlternativeFor(bee)) this.visitedByV++;
        hasVisited(bee);
    }

    /**
     * Simuliert den Besuch durch eine Biene vom Typ W
     * @param bee die besuchende Biene
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "visitedByW unverändert, wenn diese Pflanzenart für die Bienenart W nicht nutzbar ist.")
    @Hauptverantwortlicher("Christine")
    void getVisitedBy(W bee) {
        if (this.isPreferredBy(bee) || this.isAlternativeFor(bee)) visitedByW++;
        hasVisited(bee);
    }

    /**
     *  Gibt zurück, wie oft Blüten der Pflanze
     *  von der Bienenart U besucht wurden.
     * @return Anzahl der Blütenbesuche durch Bienenart U
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche durch Bienenart U zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn diese Pflanzenart für die Bienenart U nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int visitedByU() {
        return this.visitedByU;
    }

    /**
     *  Gibt zurück, wie oft Blüten der Pflanze
     *  von der Bienenart V besucht wurden.
     * @return Anzahl der Blütenbesuche durch Bienenart V
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche durch Bienenart V zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn diese Pflanzenart für die Bienenart V nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int visitedByV() {
        return this.visitedByV;
    }

    /**
     *  Gibt zurück, wie oft Blüten der Pflanze
     *  von der Bienenart W besucht wurden.
     * @return Anzahl der Blütenbesuche durch Bienenart W
     */
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt Anzahl der Blütenbesuche durch Bienenart W zurück.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist >= 0.")
    @Zusicherung(author = "Christine", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Wenn diese Pflanzenart für die Bienenart W nicht nutzbar ist, ist der Rückgabewert immer 0.")
    @Hauptverantwortlicher("Christine")
    int visitedByW() {
        return this.visitedByW;
    }

}
