import java.io.Serializable;
import java.util.function.Function;

// Interface zur Markierung einer Funktion als serialisierbar
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
