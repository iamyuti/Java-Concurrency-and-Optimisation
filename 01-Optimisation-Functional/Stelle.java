import java.util.Arrays;

// Datenstruktur zur Bündelung eindimensionales double-Array + double-Wert
public record Stelle(double[] args, double wert) {
    // String-Darstellung
    @Override
    public String toString() {
        return "Stelle " + Arrays.toString(args) + " mit dem Wert: " + wert;
    }
}