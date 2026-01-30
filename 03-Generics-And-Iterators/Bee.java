import java.time.LocalDateTime;

/**
 * Stellt die Beobachtungen einer Bienen dar.
 */
public class Bee {


    /**
     * Datum und Uhrzeit der Beobachtung
     * Invariante: ist niemals null
     */
    private final LocalDateTime zeit;

    /**
     * Konstruktor für die Beobachtung einer Biene.<br>
     * Vorbedingungen: Zeit nicht null.<br>
     * Nachbedingungen: Das Objekt ist initialisiert.
     *
     * @param zeit Datum und Uhrzeit der Beobachtung
     * @throws IllegalArgumentException wenn zeit null
     */
    public Bee(LocalDateTime zeit) {
        if (zeit == null) {
            throw new IllegalArgumentException("Das Datum und die Uhrzeit der Beobachtung dürfen nicht null sein!");
        }
        this.zeit = zeit;
    }

    /**
     * Gibt eine Beschreibung der Beobachtung zurück.<br>
     * Nachbedingung: Das Ergebnis hat immer folgendes Format: "Beobachtung einer Biene zum Zeitpunkt" und dann die Zeit.
     *
     * @return Beschreibung
     */
    @Override
    public String toString() {
        return "Biene zum Zeitpunkt " + zeit;
    }
}
