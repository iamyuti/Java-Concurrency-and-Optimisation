import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Hauptverantwortlicher("Felix")
public @interface Komponenten {
    String[] klassen();
    String[] interfaces();
    String[] annotationen();
}
