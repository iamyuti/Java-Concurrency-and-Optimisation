import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Hauptverantwortlicher("Felix")
public @interface Zusicherungen {
    Zusicherung[] value();
}
