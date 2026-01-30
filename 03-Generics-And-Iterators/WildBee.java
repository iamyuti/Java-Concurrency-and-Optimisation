import java.time.LocalDateTime;

/**
 * Stellt die Beobachtungen einer Wildbiene dar.
 */
public class WildBee extends Bee implements Modifiable<Integer, WildBee>{

    /**
     * Länge der beobachteten Wildbiene in Millimeter
     * Invariante: ist niemals null
     */
    private final Integer length;

    /**
     * Konstruktor für die Beobachtung einer Wildbiene.<br>
     * Vorbedingungen: Zeit und Länge nicht null und Länge > 0.<br>
     * Nachbedingungen: Das Objekt ist initialisiert.
     *
     * @param zeit Datum und Uhrzeit der Beobachtung
     * @param length Länge der beobachteten Wildbiene in Millimeter
     * @throws IllegalArgumentException wenn zeit oder length null
     *                                  wenn length <= 0
     */
    public WildBee(LocalDateTime zeit, Integer length) {
        super(zeit);
        if (length == null || length <= 0) {
            throw new IllegalArgumentException("Die Länge darf nicht null und muss > 0 sein!");
        }
        this.length = length;

    }

    /**
     * Gibt die Länge der beobachteten Wildbiene in Millimeter zurück.
     *
     * @return Länge der beobachteten Wildbiene in Millimeter
     */
    public Integer length() {
        return length;
    }

    /**
     * Gibt eine Beschreibung der Beobachtung zurück. <br>
     * Nachbedingung: Das Ergebnis hat immer folgendes Format: super.toString() + "und mit der Länge" und dann die Länge.
     *
     * @return Beschreibung
     */
    @Override
    public String toString() {
        return super.toString() + " und Länge " + length;
    }

    /**
     * Gibt ein neues Objekt vom Typ WildBee zurück,
     * bei dem die Länge zur bisherigen Länge der Beobachtung addiert wurde.<br>
     * Nachbedingung: this und length bleiben unverändert.
     *
     * @param length Die Länge
     * @return Gibt ein neues Objekt vom Typ WildBee zurück,
     * bei dem die Länge zur bisherigen Länge der Beobachtung addiert wurde.
     * Falls length null oder ≤ 0 ist, wird this zurückgegeben.
     */
    @Override
    public WildBee add(Integer length) {
        if (length != null && length > 0)
            return new WildBee(LocalDateTime.now(), this.length + length);
        else
            return this;
    }

    /**
     * Gibt ein neues Objekt vom Typ WildBee zurück,
     * bei dem die Länge von der bisherigen Länge der Beobachtung subtrahiert wurde.<br>
     * Nachbedingungen:<br>
     * - this und length bleiben unverändert.<br>
     * - Das zurückgegebene Objekt vom Typ WildBee hat als Beobachtungszeitpunkt
     *  den Ausführungszeitpunkt, da dies logisch konsistent mit dem restlichen System ist.
     *
     * @param length Die Länge
     * @return Gibt ein neues Objekt vom Typ WildBee zurück,
     * bei dem die Länge von der bisherigen Länge der Beobachtung subtrahiert wurde.
     * Falls length null, ≤ 0 oder größer als die Länge von this ist, wird this zurückgegeben.
     */
    @Override
    public WildBee subtract(Integer length) {
        if (length != null && length > 0 && length < this.length)
            return new WildBee(LocalDateTime.now(), this.length - length);
        else
            return this;
    }
}
