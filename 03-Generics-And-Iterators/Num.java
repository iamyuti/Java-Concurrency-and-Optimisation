/**
 * Dient Testzwecken und enthält nur eine unveränderliche Zahl,
 * die im Konstruktor gesetzt wird.
 */
public class Num implements Modifiable<Num, Num>{

    /**
     * Speichert die Zahl
     */
    private final Integer zahl;

    /**
     * Konstruktor: erstellt eine neue Num.<br>
     * Nachbedingung: Objekt ist initialisiert.
     *
     * @param zahl Zahl
     */
    public Num(Integer zahl) {
        this.zahl = zahl;
    }

    /**
     * Gibt ein neues Objekt vom Typ Num zurück,
     * bei dem X zur Zahl addiert wird.<br>
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param x Die Zahl, die zur Zahl von this hinzugezählt wird.
     * @return Gibt ein neues Objekt vom Typ Num zurück,
     * bei dem x zur Zahl addiert wird. Falls x null ist,
     * wird ein neues Objekt vom Typ Num mit der Zahl von this zurückgegeben.
     */
    @Override
    public Num add(Num x) {
        if (x != null)
            return new Num(zahl + x.zahl);
        else
            return this;
    }

    /**
     * Gibt ein neues Objekt vom Typ Num zurück,
     * bei dem X von der Zahl subtrahiert wird.<br>
     * Nachbedingung: this und x bleiben unverändert.
     *
     * @param x Die Zahl, die von der Zahl von this subtrahiert wird.
     * @return Gibt ein neues Objekt vom Typ Num zurück,
     * bei dem x von der Zahl subtrahiert wird. Falls x null ist,
     * wird ein neues Objekt vom Typ Num mit der Zahl von this zurückgegeben.
     */
    @Override
    public Num subtract(Num x) {
        if (x != null)
            return new Num(zahl - x.zahl);
        else
            return this;
    }

    /**
     * Gibt die Zahl als Text zurück.<br>
     * Nachbedingung: Die Rückgabe besteht nur aus der Zahl.
     *
     * @return Die Zahl als String.
     */
    @Override
    public String toString() {
        return String.valueOf(zahl);
    }
}
