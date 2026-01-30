import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Hauptverantwortlicher("Felix")
@Repeatable(Zusicherungen.class)
public @interface Zusicherung {
    String author();
    Zusicherungstyp typ();
    String zusicherung();
    boolean geerbt() default false; // Für Kopie von Zusicherungen aus Oberklassen/ Interfaces

    enum Zusicherungstyp { VORBEDINGUNG, NACHBEDINGUNG, INVARIANTE, SCHC, CCHC }
}
