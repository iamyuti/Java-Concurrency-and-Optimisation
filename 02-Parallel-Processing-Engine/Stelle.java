import java.io.Serializable;
import java.util.Arrays;

// Datenstruktur zur Darstellung einer bestimmten Stelle und dem dazugehörigen Wert.
// @param args Beschreibung der konkreten Stelle#
// @param wert Der Wert an der durch args beschriebenen Stelle
public record Stelle(double[] args, double wert) implements Serializable {

    // Beschreibt die Stelle
    @Override
    public String toString() {
        return "Stelle " + Arrays.toString(args) + " mit dem Wert: " + wert;
    }
}