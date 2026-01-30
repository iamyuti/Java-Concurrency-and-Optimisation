import java.io.Serializable;
import java.util.function.BiFunction;

// Interface zur Markierung einer BiFunktion als serialisierbar
public interface SerializableBiFunction<T, U, R> extends BiFunction<T, U, R>, Serializable {
}
