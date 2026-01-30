import java.util.Random;

/**
 * Repräsentiert eine Simulation, bei der Bienen verschiedener Arten Pflanzen verschiedener Arten besuchen.
 */
@Hauptverantwortlicher("Yutaka")
public class Simulation {

    /**
     * Menge aller Bienen in der Simulation.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "Das Set bees enthält nur Instanzen vom Typ Bee und ist nie null.")
    private Set bees;

    /**
     * Menge aller Pflanzen in der Simulation.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "Das Set plants enthält nur Instanzen vom Typ Plant und ist nie null.")
    private Set plants;

    /**
     * Zufallsgenerator für die Simulation
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.INVARIANTE,
            zusicherung = "Der Zufallsgenerator random ist nie null.")
    private final Random random = new Random();

    /**
     * Erstell eine neue Simulation mit leeren Mengen für Bienen und Pflanzen.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "bees and plants sind leere Mengen.")
    @Hauptverantwortlicher("Yutaka")
    public Simulation() {
        bees = new Set();
        plants = new Set();
    }

    // ANMERKUNG:
    // Die folgenden 4 Methoden dienen als Hilfestellung für EntwicklerInnen, um keine Typfehler zu produzieren.
    // Sie garantieren keine echte Typsicherheit und könnten daher auch entfernt und durch manuelles
    // Einfügen/ Auslesen aus Set ersetzt werden.

    /**
     * Fügt eine Pflanze zu einem Set hinzu.
     * Ist die Pflanze schon im Set enthalten, passiert nichts.
     * Diese Methode dient der Sicherstellung, dass nur Pflanzen einem bestimmten Set hinzugefügt werden.
     * Sie bietet keine echte Typsicherheit, hilft aber, dass nicht "aus Versehen" falsche Typen in
     * dafür nicht vorgesehene Sets eingefügt werden.
     * @param p Pflanze, die hinzugefügt werden soll
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "p != null")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Pflanze p wurde der Menge plants hinzugefügt, sofern sie nicht schon vorhanden war.")
    @Hauptverantwortlicher("Yutaka")
    private void addPlant(Plant p) {
        plants.add(p);
    }

    /**
     * Fügt eine Biene zu einem Set hinzu.
     * Ist die Biene schon im Set enthalten, passiert nichts.
     * Diese Methode dient der Sicherstellung, dass nur Bienen einem bestimmten Set hinzugefügt werden.
     * Sie bietet keine echte Typsicherheit, hilft aber, dass nicht "aus Versehen" falsche Typen in
     * dafür nicht vorgesehene Sets eingefügt werden.
     * @param b Biene, die hinzugefügt werden soll
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "b != null")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Biene b wurde der Menge bees hinzugefügt, sofern sie nicht schon vorhanden war.")
    @Hauptverantwortlicher("Yutaka")
    private void addBee(Bee b) {
        bees.add(b);
    }

    /**
     * Entnimmt eine Pflanze aus dem übergebenen Set.
     * Diese Methode dient der einfacheren Lesbarkeit, dass mit einem Pflanzen-Set gearbeitet wird
     * sowie als Hilfestellung zur Verwendung der richtigen Typen.
     * Sie bildet das Gegenstück zur Methode addPlant.
     * @param index Index, an dem entnommen wird
     * @return Die Pflanze an index in der Menge, sonst null.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "index >= 0")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt die Pflanze an index zurück, oder null bei ungültigem Index.")
    @Hauptverantwortlicher("Yutaka")
    private Plant getPlant(int index) {
        return (Plant) plants.get(index);
    }

    /**
     * Entnimmt eine Biene aus dem übergebenen Set.
     * Diese Methode dient der einfacheren Lesbarkeit, dass mit einem Bienen-Set gearbeitet wird
     * sowie als Hilfestellung zur Verwendung der richtigen Typen.
     * Sie bildet das Gegenstück zur Methode addBee.
     * @param index Index, an dem entnommen wird
     * @return Die Biene an index in der Menge, sonst null.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "index >= 0")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt die Biene an index zurück, oder null bei ungültigem Index.")
    @Hauptverantwortlicher("Yutaka")
    private Bee getBee(int index) {
       return (Bee) bees.get(index);
    }

    /**
     * Generiert einige Bienen und Pflanzen von jeweils nur zwei Arten
     * und fügt diese zu den übergebenen Mengen hinzu.
     * Die Anzahl der Bienen und Pflanzen sowie ihre Arten werden zufällig bestimmt.
     * (Grenze für die Anzahl zwecks Übersichtlichkeit der Tests = 3)
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "In die Mengen wurden je zufällig generierte Pflanzen bzw. Bienen von 2 Arten hinzugefügt.")
    @Hauptverantwortlicher("Yutaka")
    private void generatePlantsAndBees() {

        // 0 = U + V, 1 = V + W, 2 = U + W
        int generatedBees = random.nextInt(3);

        int numU = 0;
        int numV = 0;
        int numW = 0;

        System.out.println();

        if (generatedBees == 0 || generatedBees == 2) {
            numU = random.nextInt(3) + 1; // max 3
            System.out.println("Generiere " + numU + " neue Bienen der Art U.");
        }
        if (generatedBees == 0 || generatedBees == 1) {
            numV = random.nextInt(3) + 1;
            System.out.println("Generiere " + numV + " neue Bienen der Art V.");
        }
        if (generatedBees == 1 || generatedBees == 2) {
            numW = random.nextInt(3) + 1;
            System.out.println("Generiere " + numW + " neue Bienen der Art W.");
        }

        for (int i = 0; i < numU; i++) {
            addBee(new U());
        }
        for (int i = 0; i < numV; i++) {
            addBee(new V());
        }
        for (int i = 0; i < numW; i++) {
            addBee(new W());
        }

        // 0 = X + Y, 1 = Y + Z, 2 = X + Z
        int generatedPlants = random.nextInt(3);

        int numX = 0;
        int numY = 0;
        int numZ = 0;

        if (generatedPlants == 0 || generatedPlants == 2) {
            numX = random.nextInt(3) + 1; // max 3
            System.out.println("Generiere " + numX + " neue Pflanzen der Art X.");
        }
        if (generatedPlants == 0 || generatedPlants == 1) {
            numY = random.nextInt(3) + 1;
            System.out.println("Generiere " + numY + " neue Pflanzen der Art Y.");
        }
        if (generatedPlants == 1 || generatedPlants == 2) {
            numZ = random.nextInt(3) + 1;
            System.out.println("Generiere " + numZ + " neue Pflanzen der Art Z.");
        }

        for (int i = 0; i < numX; i++) {
            addPlant(new X());
        }
        for (int i = 0; i < numY; i++) {
            addPlant(new Y());
        }
        for (int i = 0; i < numZ; i++) {
            addPlant(new Z());
        }
    }

    /**
     * Prüft, ob es noch aktive Bienen gibt, die mindestens eine blühende Pflanze besuchen können.
     * Eine Biene kann eine Pflanze besuchen, wenn sie diese bevorzugt oder alternativ nutzen kann.
     * @return true, wenn es noch mindestens eine aktive Biene und eine blühende Pflanze gibt. Sonst false.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Gibt true zurück, wenn mindestens eine aktive Biene eine besuchbare blühende Pflanze hat.")
    @Hauptverantwortlicher("Yutaka")
    private boolean activeBeesAndPlant() {
        boolean active = false;
        for (int i = 0; i < bees.size(); i++) {
            Bee b = getBee(i);
            if (!b.active()) {
                continue;
            }
            active = true;
            for (int j = 0; j < plants.size(); j++) {
                Plant p = getPlant(j);
                if (p.inBloom() && (b.prefersPlant(p) || b.alternativePlant(p))) {
                    System.out.println("\nEs gibt noch eine aktive Biene.\nEs gibt noch besuchbare in Blüte stehende Pflanzen für diese Biene.");
                    return true;
                }
            }
        }
        if (active) {
            System.out.println("\nEs gibt noch eine aktive Biene.\nEs gibt für die aktive(n) Biene(n) keine besuchbare in Blüte stehende Pflanzen mehr.");
        } else {
            System.out.println("\nEs gibt keine aktiven Bienen mehr.");
        }
        System.out.println("-> Simulation wird beendet.");
        return false;
    }

    /**
     * Führt die täglichen Pflanzenbesuche der Bienen durch.
     * Jede aktive Biene macht täglich eine zufällig gewählte Anzahl an Pflanzenbesuchen,
     * wobei die Bevorzugungen zu berücksichtigen sind.
     * Bei jedem Besuch wird die zu besuchende Pflanze zufällig gewählt, damit möglichst
     * unterschiedliche Pflanzen der bevorzugten (oder verfügbaren) Art zum Zug kommen.
     *
     * @param day aktueller Tag der Simulation
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "day >= 1")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Alle Besuche wurden entsprechend Vorlieben und Aktivitäts- sowie Blühzeiträumen durchgeführt.")
    @Hauptverantwortlicher("Yutaka")
    private void dailyVisit(int day) {
        // Blühende Pflanzen zählen für sinnvolle Besuchsanzahl
        int bloomingCount = 0;
        for (int j = 0; j < plants.size(); j++) {
            Plant p = getPlant(j);
            if (p.inBloom()) {
                bloomingCount++;
            }
        }
        System.out.println("\n= Tag " + day + " - Anzahl in Blüte stehender Pflanzen: " + bloomingCount + " =\n");

        if (bloomingCount == 0)
            return;

        for (int i = 0; i < bees.size(); i++) {
            Bee b = getBee(i);

            if (b.active()) {
                // zufällige Anzahl Besuche wählen (basierend auf blühende Pflanzen)
                int visits = random.nextInt(bloomingCount) + 1;
                System.out.println("Biene " + (i+1) + " macht " + visits + " Besuche.");

                // aus Plants alle bevorzugten und alternativen blühenden Pflanzen sammeln
                Set preferredPlants  = new Set();
                Set alternativePlants = new Set();
                for (int j = 0; j < plants.size(); j++) {
                    Plant p = getPlant(j);
                    if (p.inBloom()) {
                        if (b.prefersPlant(p)) {
                            preferredPlants.add(p);
                        }
                        if (b.alternativePlant(p)) {
                            alternativePlants.add(p);
                        }
                    }
                }
                // erst bevorzugte, dann alternative Pflanzen besuchen
                if (preferredPlants.size() > 0) {
                    System.out.println("\tBesuche " + visits + " bevorzugte Pflanzen.");
                    visits = performVisits(b, preferredPlants, visits);
                } else {
                    System.out.println("\tKeine bevorzugten Pflanzen verfügbar.");
                }
                if (visits > 0) {
                    if (alternativePlants.size() > 0) {
                        System.out.println("\t-> Besuche " + visits + " alternativ nutzbare Pflanzen.");
                        visits = performVisits(b, alternativePlants, visits);
                    } else {
                        System.out.println("\tAuch keine alternativ nutzbaren Pflanzen verfügbar.\n\t-> Keine Besuche möglich.");
                    }
                } else {
                    System.out.println("\t-> Besuche keine alternativ nutzbaren Pflanzen.");
                }
                System.out.println("\t=> Gesamtzahl Besuche dieser Biene: X=" + b.collectedFromX() + " Y=" + b.collectedFromY() + " Z=" + b.collectedFromZ());
            }
        }
        dayOver();

        System.out.println("= Tag " + day + " zu Ende =");
    }

    /**
     * Hilfsmethode für dailyVisit(): Führt Besuche aus einem Set von Pflanzen durch.
     * Besucht dabei zufällig gewählte Pflanzen aus dem Set, bis es entweder leer ist oder
     * die gewünschte Anzahl an Besuchen erreicht ist.
     * @param bee besuchende Biene
     * @param availablePlants Set der verfügbaren Pflanzen
     * @param visits Anzahl der gewünschten Besuche
     * @return Anzahl der noch übrigen Besuche
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "bee != null")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "availablePlants != null")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "availablePlants.size() > 0")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,
            zusicherung = "visits >= 0")
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Rückgabewert ist die Anzahl der noch nicht durchgeführten Besuche.")
    @Hauptverantwortlicher("Yutaka")
    private int performVisits(Bee bee, Set availablePlants, int visits) {
            while (visits > 0) {
                int index = random.nextInt(availablePlants.size());
                Plant p = (Plant) availablePlants.get(index);
                bee.visit(p);
                visits--;
            }
            return visits;
    }

    /**
     * Signalisiert den Bienen und Pflanzen, dass ein Tag vorüber ist.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Den Bienen und Pflanzen wurde das Vorbeigehen eines Tages signalisiert.")
    @Hauptverantwortlicher("Yutaka")
    private void dayOver() {
        System.out.println();

        int activeBees = 0;
        int changingBees = 0;
        int activePlants = 0;
        int changingPlants = 0;
        boolean active;

        // Tag vorbei: Je active & inBloom-Zähler hochzählen
        for (int i = 0; i < bees.size(); i++) {
            Bee b = getBee(i);
            active = b.active();
            b.oneDayOver();
            if (active != b.active())
                changingBees++;
            if (b.active())
                activeBees++;
        }

        if (changingBees > 0)
            System.out.println(changingBees + " Bienen sind jetzt nicht mehr aktiv.");

        System.out.println("-> " + activeBees + " aktive Bienen übrig.");

        for (int j = 0; j < plants.size(); j++) {
            Plant p = getPlant(j);
            active = p.inBloom();
            p.oneDayOver();
            if (active != p.inBloom())
                changingPlants++;
            if (p.inBloom())
                activePlants++;
        }

        if (changingPlants > 0)
            System.out.println(changingPlants + " Pflanzen blühen jetzt nicht mehr.");

        System.out.println("-> " + activePlants + " in Blüte stehende Pflanzen übrig.\n");
    }

    /**
     * Führt eine Simulation durch
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Simulation wurde durchgeführt und Ergebnisse ausgegeben.")
    @Hauptverantwortlicher("Yutaka")
    public void simulate() {

      /*
        Dazu werden zwei Mengen (Daten sammlungen) benötigt, eine mit Objekten von Bienen,
        eine mit Objekten von Pflanzen, wobei die Mengen nicht zwischen Bienenarten bzw.
        Pflanzenarten unterscheiden (also insgesamt nur zwei Mengen, nicht mehr).
        Anfangs sind beide Mengen leer.
     */
        bees = new Set();
        plants = new Set();
     /*
        Über einen Zeitraum von 7 Tagen werden täglich zu Beginn einige Bienen
        und Pflanzen von jeweils nur zwei Arten erzeugt und zu den Mengen hinzugefügt.
        Die Anzahl der Bienen und Pflanzen sowie ihre Arten werden zufällig bestimmt.
     */

        for (int day = 0; day < 7; day ++) {
            generatePlantsAndBees();
            dailyVisit(day+1);
        }

        System.out.println("\n = Generationsphase vorbei = ");
      /*
        Die täglichen Besuche von Pflanzen werden so lange fort
        gesetzt (über die oben genannten 7 Tage hinaus), bis es keine aktive Biene
        oder keine von den Bienen besuchbare blühende Pflanze mehr gibt.
     */

        int day = 8;
        while(activeBeesAndPlant()) {
            dailyVisit(day);
            day++;
        }

     /*
        Am Ende der Simulation sind über die oben genannten Methoden
        zugreifbare Werte in gesammelter Form auszugeben:
        • für jede Bienenart die Gesamtzahl aller Pflanzenbesuche und die
        durchschnittliche Anzahl der Besuche pro Pflanze,
        • für jede Pflanzenart die Gesamtzahl aller Bienenbesuche und die
        durchschnittliche Anzahl der Besuche pro Biene.
     */
        printResults();
    }

    /**
     * Gibt die Simulationsergebnisse aus.
     * Für jede Bienenart wird die Gesamtzahl aller Pflanzenbesuche und die
     * durchschnittliche Anzahl der Besuche pro Pflanze ausgegeben.
     * Für jede Pflanzenart wird die Gesamtzahl aller Bienenbesuche und die
     * durchschnittliche Anzahl der Besuche pro Biene ausgegeben.
     */
    @Zusicherung(author = "Yutaka", typ = Zusicherung.Zusicherungstyp.NACHBEDINGUNG,
            zusicherung = "Ergebnisse wurden ausgegeben.")
    @Hauptverantwortlicher("Yutaka")
    private void printResults() {
        long totalVisitsByU = 0;
        long totalVisitsByV = 0;
        long totalVisitsByW = 0;

        int plantsVisitedByU = 0;
        int plantsVisitedByV = 0;
        int plantsVisitedByW = 0;

        for (int i = 0; i < plants.size(); i++) {
            Plant p = getPlant(i);

            int u = p.visitedByU();
            int v = p.visitedByV();
            int w = p.visitedByW();

            totalVisitsByU += u;
            totalVisitsByV += v;
            totalVisitsByW += w;

            if (u > 0) {
                plantsVisitedByU++;
            }
            if (v > 0) {
                plantsVisitedByV++;
            }
            if (w > 0) {
                plantsVisitedByW++;
            }
        }

        double avgVisitsPerPlantByU =
                plantsVisitedByU > 0 ? (double) totalVisitsByU / plantsVisitedByU : 0.0;
        double avgVisitsPerPlantByV =
                plantsVisitedByV > 0 ? (double) totalVisitsByV / plantsVisitedByV : 0.0;
        double avgVisitsPerPlantByW =
                plantsVisitedByW > 0 ? (double) totalVisitsByW / plantsVisitedByW : 0.0;

        long totalVisitsOnX = 0;
        long totalVisitsOnY = 0;
        long totalVisitsOnZ = 0;

        int beesVisitedX = 0;
        int beesVisitedY = 0;
        int beesVisitedZ = 0;

        for (int i = 0; i < bees.size(); i++) {
            Bee b = getBee(i);

            int fromX = b.collectedFromX();
            int fromY = b.collectedFromY();
            int fromZ = b.collectedFromZ();

            totalVisitsOnX += fromX;
            totalVisitsOnY += fromY;
            totalVisitsOnZ += fromZ;

            if (fromX > 0) {
                beesVisitedX++;
            }
            if (fromY > 0) {
                beesVisitedY++;
            }
            if (fromZ > 0) {
                beesVisitedZ++;
            }
        }

        double avgVisitsPerBeeOnX =
                beesVisitedX > 0 ? (double) totalVisitsOnX / beesVisitedX : 0.0;
        double avgVisitsPerBeeOnY =
                beesVisitedY > 0 ? (double) totalVisitsOnY / beesVisitedY : 0.0;
        double avgVisitsPerBeeOnZ =
                beesVisitedZ > 0 ? (double) totalVisitsOnZ / beesVisitedZ : 0.0;

        System.out.println();
        System.out.println("=== Bienenarten ===");

        System.out.printf("U: Gesamtzahl Pflanzenbesuche = %d, durchschnittliche Besuche pro Pflanze = %.2f%n",
                totalVisitsByU, avgVisitsPerPlantByU);
        System.out.printf("V: Gesamtzahl Pflanzenbesuche = %d, durchschnittliche Besuche pro Pflanze = %.2f%n",
                totalVisitsByV, avgVisitsPerPlantByV);
        System.out.printf("W: Gesamtzahl Pflanzenbesuche = %d, durchschnittliche Besuche pro Pflanze = %.2f%n",
                totalVisitsByW, avgVisitsPerPlantByW);

        System.out.println();
        System.out.println("=== Pflanzenarten ===");

        System.out.printf("X: Gesamtzahl Bienenbesuche = %d, durchschnittliche Besuche pro Biene = %.2f%n",
                totalVisitsOnX, avgVisitsPerBeeOnX);
        System.out.printf("Y: Gesamtzahl Bienenbesuche = %d, durchschnittliche Besuche pro Biene = %.2f%n",
                totalVisitsOnY, avgVisitsPerBeeOnY);
        System.out.printf("Z: Gesamtzahl Bienenbesuche = %d, durchschnittliche Besuche pro Biene = %.2f%n",
                totalVisitsOnZ, avgVisitsPerBeeOnZ);
    }
}
