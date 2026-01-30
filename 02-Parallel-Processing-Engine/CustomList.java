import java.util.ArrayList;
import java.util.List;

// Diese Klasse dient als Wrapper für eine ArrayList mit dem Typ Stelle
public class CustomList extends ArrayList<Stelle> {

    // Konstruktor für eine bereits vorhandene List
    public CustomList(List<Stelle> l) {
        super(l);
    }

    // Konstruktor für eine neue List
    public CustomList() {
        super();
    }
}
