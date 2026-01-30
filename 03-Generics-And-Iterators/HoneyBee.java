import java.time.LocalDateTime;

/**
 * Stellt die Beobachtungen einer Honigbiene dar.
 */
public class HoneyBee extends Bee implements Modifiable<String,HoneyBee>{

    /**
     * Name der beobachteten Bienenart oder Züchtung
     * Invariante: ist niemals null
     */
    private final String name;

    /**
     * Konstruktor für die Beobachtung einer Honigbiene.<br>
     * Vorbedingungen: Zeit und Name nicht null.<br>
     * Nachbedingungen: Das Objekt ist initialisiert.
     *
     * @param zeit Datum und Uhrzeit der Beobachtung
     * @param name Name der beobachteten Bienenart
     * @throws IllegalArgumentException wenn zeit oder name null
     */
    public HoneyBee(LocalDateTime zeit, String name) {
        super(zeit);
        if (name == null) {
            throw new IllegalArgumentException("Der Name der Bienenart oder Züchtung darf nicht null sein!");
        }
        this.name = name;

    }

    /**
     * Gibt den Namen der beobachteten Bienenart oder Züchtung zurück.
     *
     * @return Name der beobachteten Bienenart oder Züchtung
     */
    public String sort(){
        return name;
    }

    /**
     * Gibt ein neues Objekt vom Typ HoneyBee zurück,
     * bei dem die Zeichenkette s zum Namen der Beobachtung hinzugefügt wurde.<br>
     * Nachbedingung: this und s bleiben unverändert.
     *
     * @param s Die Zeichenkette, die zum Namen der Beobachtung hinzugefügt wird.
     * @return Gibt ein neues Objekt vom Typ HoneyBee zurück,
     * bei dem die Zeichenkette s zum Namen der Beobachtung hinzugefügt ist.
     * Falls s null oder leer ist, wird ein neues Objekt vom Typ HoneyBee
     * mit dem Namen von this zurückgegeben.
     */
    @Override
    public HoneyBee add(String s) {
        if (s != null && !s.isEmpty())
            return new HoneyBee(LocalDateTime.now(), name + s);
        else
            return this;
    }

    /**
     * Gibt ein neues Objekt vom Typ HoneyBee zurück,
     * bei dem die Zeichenkette s aus dem Namen der Beobachtung entfernt ist,
     * falls er dort vorkommt.<br>
     * Nachbedingung: this und s bleiben unverändert.
     *
     * @param s Die Zeichenkette, die aus dem Namen der Beobachtung entfernt wird.
     * @return Gibt ein neues Objekt vom Typ HoneyBee zurück,
     * bei dem die Zeichenkette s aus dem Namen der Beobachtung entfernt ist,
     * falls er dort vorkommt. Falls s null oder leer ist,
     * wird ein neues Objekt vom Typ HoneyBee mit dem Namen von this zurückgegeben.
     */
    @Override
    public HoneyBee subtract(String s) {
        if (s != null && !s.isEmpty())
            return new HoneyBee(LocalDateTime.now(), name.replace(s,""));
        else
            return this;
    }

    /**
     * Gibt eine Beschreibung der Beobachtung zurück.<br>
     * Nachbedingung: Das Ergebnis hat immer folgendes Format: super.toString() + "und mit der Art oder Züchtung"
     * und dann der Name der beobachteten Bienenart oder Züchtung.
     *
     * @return Beschreibung
     */
    @Override
    public String toString() {
        return super.toString() + " und Art oder Züchtung " + name;
    }
}
