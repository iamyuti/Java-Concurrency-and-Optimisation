import java.util.Comparator;
import java.util.List;
import java.util.function.*;
import java.util.stream.Stream;

public final class BeesAlgorithm {

    private BeesAlgorithm() {}

    // Führt BeesAlgorithm aus
    public static List<Stelle> search(BAParameter parameter){

        // ------- Unsere funktionalen Werkzeuge -------

        // Zufällige Stelle innerhalb des gesamten Suchbereichs erzeugen
        Supplier<Stelle> zufaelligeStelle = Funktionen.stellenGenerator(
                parameter.w(),
                parameter.f()
        );

        // Stellen nach "besser" sortieren
        Comparator<Stelle> vergleich = Funktionen.stellenComparator(parameter.c());

        // Feld um eine Stelle erzeugen (flower patch)
        Function<Stelle, Feld> feldUmStelle = Funktionen.feldErzeuger(
                parameter.w(),
                parameter.s()
        );

        // Startinitialisierung (1. Generation), n Bienen fliegen als Kundschafterinnen an zufällige Stellen
        Stream<Stelle> start = Stream.generate(zufaelligeStelle)
                .limit(parameter.n());

        // ---------------------------------------------

        // Ein Schritt; wir wollen schrittweise bessere Stellen finden
        UnaryOperator<Stream<Stelle>> schritt = stellen -> {

            // sortiert die Liste = vergleicht Ergebnisse miteinander (bestes Ergebnis vorne)
            List<Stelle> sortiert = stellen
                    .sorted(vergleich)
                    .toList(); // Listen-Repräsentation, damit die Werte 3x konsumiert werden können

            // lokale Suche auf die e besten Felder mit zusätzlichen p rekrutierten Bienen
            Stream<Stelle> exzellent = sortiert.stream()
                    .limit(parameter.e())
                    .map(feldUmStelle) // Stream<Feld>
                    // lokaleSuche gibt je bestes Ergebnis pro Feld zurück
                    .map(feld -> lokaleSuche(feld, parameter.p() , parameter));

            // lokale Suche auf die nächsten m-e Felder mit zusätzlichen q rekrutierten Bienen
            Stream<Stelle> normal = sortiert.stream()
                    .skip(parameter.e())
                    .limit(parameter.m() - parameter.e())
                    .map(feldUmStelle) // Stream<Feld>
                    // lokaleSuche gibt je bestes Ergebnis pro Feld zurück
                    .map(feld -> lokaleSuche(feld, parameter.q() , parameter));

            // globale Suche auf neue zufällige Felder
            Stream<Stelle> global = Stream.generate(zufaelligeStelle)
                    .limit(parameter.n() - parameter.m());

            // alle Ströme (exzellent, normal, global) kombinieren, das ist die neue Generation
            return Stream.of(exzellent, normal, global)
                    .flatMap(Function.identity()); // Wieder Stream<Stelle>

        };

        // Den Schritt t mal auf die Startpopulation ausführen
        Stream<Stelle> result = Funktionen.wiederhole(parameter.t(), schritt)
                .apply(start);

        // Die r besten Stellen zurückgeben
        return result.sorted(vergleich)
                .limit(parameter.r())
                .toList();
    }


    private static Stelle lokaleSuche(Feld feld, int anzahlBienen, BAParameter parameter) {
        // zufällige Stellen innerhalb des Feldes erzeugen
        Supplier<Stelle> imFeld = Funktionen.stellenGenerator(
                feld.grenzen(),
                parameter.f()
        );

        return Stream.concat(
                Stream.of(feld.zentrum()), // zurzeit beste (ursprüngliche Kundschafterin)
                Stream.generate(imFeld).limit(anzahlBienen) // andere Kandidaten (rekrutierte Bienen)
            )
            .min(Funktionen.stellenComparator(parameter.c())) // beste gefundene Stelle
            .orElse(feld.zentrum()); // falls kein neues bestes gefunden, dann altes
    }
}