import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Hauptverantwortlicher("Felix")
public @interface Hauptverantwortlicher {
    String value();
}
