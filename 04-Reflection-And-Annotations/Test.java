import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Testklasse
 */
@Hauptverantwortlicher("Felix")
@Komponenten(klassen = {"Set", "Set$Node","Bee", "U", "V", "W", "Plant", "X", "Y", "Z", "Simulation", "Test"},
        interfaces = {},
        annotationen = {"Hauptverantwortlicher", "Komponenten", "Zusicherung", "Zusicherungen"})
public class Test {

    /**
     * Detaillierte Testausgaben
     *
     * @param args Argumente (wird nicht verwendet)
     */
    @Hauptverantwortlicher("Felix")
    public static void main(String[] args) {

        System.out.print("""
                \n======================================
                ===            KONTEXT A           ===
                ======================================
                """);

        // --- SET IMPLEMENTIERUNG ---

        System.out.print("""
                \n======================================
                ===              SET               ===
                ======================================
                """);

        // Testobjekte
        System.out.println("\n= Erstelle 4 Testobjekte und leeres Set =");
        Set set = new Set();
        String o1 = "Testobjekt 1";
        String o2 = "Testobjekt 2";
        String o3 = "Testobjekt 3";
        String o4 = "Testobjekt 4";
        Object[] objects = new Object[] {o1, o2, o3, o4};

        // Leeres Set
        System.out.println("\n= Leeres Set =");
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Enthält es ein Objekt?");
        for (Object o : objects) {
            System.out.println(o.toString() + ":  " + set.contains(o));
        }

        // Objekt hinzufügen
        System.out.println("\n= Objekt hinzufügen =");
        set.add(o1);
        set.add(o2);
        set.add(o4);
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Enthält es ein Objekt?");
        for (Object o : objects) {
            System.out.println(o.toString() + ":  " + set.contains(o));
        }

        // Objekt mehrfach hinzufügen
        System.out.println("\n= Objekt mehrfach hinzufügen =");
        System.out.println("Füge Testobjekt 1 erneut hinzu (dürfte trotzdem nur einmal enthalten sein):");
        set.add(o1);
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Objekte in der Menge:");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }

        // Objekte aus Menge lesen
        System.out.println("\n= Objekte aus Menge lesen =");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }

        // Objekte aus Menge lesen - Index 0
        System.out.println("\n= Objekte aus Menge lesen - Index 0 (ganz vorne) =");
        System.out.println(set.get(0));

        // Objekte aus Menge lesen - Index 0
        System.out.println("\n= Objekte aus Menge lesen - Index size-1 (ganz hinten) =");
        System.out.println(set.get(set.size()-1));

        // Objekte aus Menge lesen - ungültiger Index
        System.out.println("\n= Objekte aus Menge lesen - ungültiger Index =");
        System.out.println(set.get(5));

        // Objekte aus Menge lesen - negativer Index
        System.out.println("\n= Objekte aus Menge lesen - negativer Index =");
        System.out.println(set.get(-2));

        // Objekt-Gleichheit beruht auf Identität
        System.out.println("\n= Objekt-Gleichheit beruht auf Identität =");
        System.out.println("Füge exakt gleichen String \"Testobjekt 1\" nochmal als anderes Objekt hinzu.");
        Object o5 = new String("Testobjekt 1"); // ohne newString kein eigenes Objekt
        set.add(o5);
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Objekte in der Menge:");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }

        // Objekt aus Menge löschen
        System.out.println("\n= Objekt aus Menge löschen =");
        boolean o5Deleted = set.remove(o5);
        boolean o2Deleted = set.remove(o2);
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Objekte in der Menge:");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }
        System.out.println("Testobjekt 2 gelöscht? " + o2Deleted);
        System.out.println("Testobjekt 1 (Kopie) gelöscht? " + o5Deleted);

        // Objekt aus leerer Menge löschen
        System.out.println("\n= Objekt aus leerer Menge löschen =");
        Set empty = new Set();
        boolean deleted = empty.remove(o5);
        System.out.println("Ergebnis des Löschens: " + deleted);
        System.out.println("Größe des Sets: " + empty.size());

        // Nicht vorhandenes Element löschen
        System.out.println("\n= Nicht vorhandenes Element löschen =");
        o2Deleted = set.remove(o2);
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Objekte in der Menge:");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }
        System.out.println("Testobjekt 2 nochmal gelöscht? " + o2Deleted);

        // contains, get und size verändern die Menge nicht
        System.out.println("\n= contains, get und size verändern die Menge nicht =");
        System.out.println("contains(Testobjekt 3)?: " + set.contains(o3));
        System.out.println("get(Index 2)?: " + set.get(2));
        System.out.println("size()?: " + set.size());
        System.out.println("Anzahl der Elemente: " + set.size());
        System.out.println("Objekte in der Menge:");
        for (int i = 0; i < set.size(); i++) {
            System.out.println(set.get(i).toString());
        }

        // Sehr viele Objekte hinzufügen
        System.out.println("\n= Sehr viele Objekte hinzufügen =");
        System.out.println("Füge 1000 Objekte hinzu:");
        for (int i = 0; i < 1000; i++) {
            set.add(new Object());
        }
        System.out.println("Anzahl der Elemente: " + set.size());

        // --- BEE & PLANT ZUSAMMENSPIEL (DYNAMISCHES BINDEN) ---

        System.out.print("""
                \n======================================
                ===          BEE & PLANT           ===
                ======================================
                """);

        Bee beeU = new U();
        Bee beeV = new V();
        Bee beeW = new W();

        Plant plantX = new X();
        Plant plantY = new Y();
        Plant plantZ = new Z();

        // Bevorzugte Pflanzen
        System.out.println("\n= Bevorzugte Pflanzen =");
        System.out.println("Bienenart U bevorzugt Pflanzenart X: " + beeU.prefersPlant(plantX));
        System.out.println("Bienenart U bevorzugt Pflanzenart Y: " + beeU.prefersPlant(plantY));
        System.out.println("Bienenart U bevorzugt Pflanzenart Z: " + beeU.prefersPlant(plantZ));
        System.out.println();
        System.out.println("Bienenart V bevorzugt Pflanzenart X: " + beeV.prefersPlant(plantX));
        System.out.println("Bienenart V bevorzugt Pflanzenart Y: " + beeV.prefersPlant(plantY));
        System.out.println("Bienenart V bevorzugt Pflanzenart Z: " + beeV.prefersPlant(plantZ));
        System.out.println();
        System.out.println("Bienenart W bevorzugt Pflanzenart X: " + beeW.prefersPlant(plantX));
        System.out.println("Bienenart W bevorzugt Pflanzenart Y: " + beeW.prefersPlant(plantY));
        System.out.println("Bienenart W bevorzugt Pflanzenart Z: " + beeW.prefersPlant(plantZ));

        // Alternativ nutzbare Pflanzen
        System.out.println("\n= Alternativ nutzbare Pflanzen =");
        System.out.println("Bienenart U kann Pflanzenart X alternativ nutzen: " + beeU.alternativePlant(plantX));
        System.out.println("Bienenart U kann Pflanzenart Y alternativ nutzen: " + beeU.alternativePlant(plantY));
        System.out.println("Bienenart U kann Pflanzenart Z alternativ nutzen: " + beeU.alternativePlant(plantZ));
        System.out.println();
        System.out.println("Bienenart V kann Pflanzenart X alternativ nutzen: " + beeV.alternativePlant(plantX));
        System.out.println("Bienenart V kann Pflanzenart Y alternativ nutzen: " + beeV.alternativePlant(plantY));
        System.out.println("Bienenart V kann Pflanzenart Z alternativ nutzen: " + beeV.alternativePlant(plantZ));
        System.out.println();
        System.out.println("Bienenart W kann Pflanzenart X alternativ nutzen: " + beeW.alternativePlant(plantX));
        System.out.println("Bienenart W kann Pflanzenart Y alternativ nutzen: " + beeW.alternativePlant(plantY));
        System.out.println("Bienenart W kann Pflanzenart Z alternativ nutzen: " + beeW.alternativePlant(plantZ));

        // Nicht nutzbare Kombis
        System.out.println("\n= Nicht nutzbare Kombinationen (Zähler bleiben 0) =");

        Bee testU = new U();
        Bee testV = new V();
        Bee testW = new W();
        Plant testX = new X();
        Plant testY = new Y();
        Plant testZ = new Z();

        // U besucht Z, nicht nutzbar daher sollte Zähler 0 bleiben
        System.out.println("\n= Biene U besucht Pflanze Z (nicht nutzbar) =");
        testU.visit(testZ);
        testU.visit(testZ);
        testU.visit(testZ);
        System.out.println("U.collectedFromZ() nach genau 3 Besuchen: " + testU.collectedFromZ() + " (erwartet: 0)");
        System.out.println("Z.visitedByU() nach genau 3 Besuchen: " + testZ.visitedByU() + " (erwartet: 0)");

        // V besucht X, nicht nutzbar daher sollte Zähler 0 bleiben
        System.out.println("\n= Biene V besucht Pflanze X (nicht nutzbar) =");
        testV.visit(testX);
        testV.visit(testX);
        System.out.println("V.collectedFromX() nach genau 2 Besuchen: " + testV.collectedFromX() + " (erwartet: 0)");
        System.out.println("X.visitedByV() nach genau 2 Besuchen: " + testX.visitedByV() + " (erwartet: 0)");

        // W besucht Y, nicht nutzbar daher sollte Zähler 0 bleiben
        System.out.println("\n= Biene W besucht Pflanze Y (nicht nutzbar) =");
        testW.visit(testY);
        System.out.println("W.collectedFromY() nach genau 1 Besuch: " + testW.collectedFromY() + " (erwartet: 0)");
        System.out.println("Y.visitedByW() nach genau 1 Besuch: " + testY.visitedByW() + " (erwartet: 0)");

        // Aktivitäts- und Blühzeiträume
        System.out.println("\n= Aktivitäts- und Blühzeiträume =");

        // Aktivitätszeitraum
        System.out.println("\n= Aktivitätszeitraum der Bienen =");

        // U hat Aktivitätszeitraum von 9
        Bee freshU = new U();
        System.out.println("Neue Biene U ist aktiv: " + freshU.active() + " (erwartet: true)");
        for (int i = 0; i < 8; i++) {
            freshU.oneDayOver();
        }
        System.out.println("Biene U nach 8 Tagen aktiv: " + freshU.active() + " (erwartet: true, 1 Tag übrig)");
        freshU.oneDayOver();
        System.out.println("Biene U nach 9 Tagen aktiv: " + freshU.active() + " (erwartet: false)");

        //Blühzeitraum
        System.out.println("\n= Blühzeitraum der Pflanzen =");

        // X hat Blühzeitraum von 9
        Plant freshX = new X();
        System.out.println("Neue Pflanze X blüht: " + freshX.inBloom() + " (erwartet: true)");
        for (int i = 0; i < 8; i++) {
            freshX.oneDayOver();
        }
        System.out.println("Pflanze X nach 8 Tagen blüht: " + freshX.inBloom() + " (erwartet: true, 1 Tag übrig)");
        freshX.oneDayOver();
        System.out.println("Pflanze X nach 9 Tagen blüht: " + freshX.inBloom() + " (erwartet: false)");

        // Abbruchbedingung der Simulation
        System.out.println("\n= Abbruchbedingung der Simulation =");

        // Abbruchbedingung
        System.out.println("\n= Abbruchbedingung der Simulation =");

        System.out.println("Wenn nur Bienen U und Pflanzen Z vorhanden sind, können keine Besuche stattfinden.");
        Bee onlyU = new U();
        Plant onlyZ = new Z();
        System.out.println("U.prefersPlant(Z): " + onlyU.prefersPlant(onlyZ) + " (erwartet: false)");
        System.out.println("U.alternativePlant(Z): " + onlyU.alternativePlant(onlyZ) + " (erwartet: false)");
        System.out.println("Simulation sollte abbrechen");

        System.out.println("\nWenn nur Bienen V und Pflanzen X vorhanden sind, können keine Besuche stattfinden.");
        Bee onlyV = new V();
        Plant onlyX = new X();
        System.out.println("V.prefersPlant(X): " + onlyV.prefersPlant(onlyX) + " (erwartet: false)");
        System.out.println("V.alternativePlant(X): " + onlyV.alternativePlant(onlyX) + " (erwartet: false)");
        System.out.println("Simulation sollte abbrechen");

        System.out.println("\nWenn nur Bienen W und Pflanzen Y vorhanden sind, können keine Besuche stattfinden.");
        Bee onlyW = new W();
        Plant onlyY = new Y();
        System.out.println("W.prefersPlant(Y): " + onlyW.prefersPlant(onlyY) + " (erwartet: false)");
        System.out.println("W.alternativePlant(Y): " + onlyW.alternativePlant(onlyY) + " (erwartet: false)");
        System.out.println("Simulation sollte abbrechen");

        // Korrektes Zählen der Pflanzenbesuche
        System.out.println("\n= Korrektes Zählen der Pflanzenbesuche =");

        // Pflanzenbesuche
        System.out.println("\n= Pflanzenbesuche =");
            // Biene U: Pflanze X=1, Pflanze Y=2, Pflanze Z=3
        System.out.println("Biene U: Pflanze X=1, Pflanze Y=2, Pflanze Z=0 (nicht nutzbar)");
        beeU.visit(plantX);
        System.out.println(beeU.collectedFromX());
        beeU.visit(plantY);
        beeU.visit(plantY);
        System.out.println(beeU.collectedFromY());
        beeU.visit(plantZ);
        beeU.visit(plantZ);
        beeU.visit(plantZ);
        System.out.println(beeU.collectedFromZ());
        System.out.println();
            // Biene V: Pflanze X=3, Pflanze Y=1, Pflanze Z=2
        System.out.println("Biene V: Pflanze X=0 (nicht nutzbar), Pflanze Y=1, Pflanze Z=2");
        beeV.visit(plantX);
        beeV.visit(plantX);
        beeV.visit(plantX);
        System.out.println(beeV.collectedFromX());
        beeV.visit(plantY);
        System.out.println(beeV.collectedFromY());
        beeV.visit(plantZ);
        beeV.visit(plantZ);
        System.out.println(beeV.collectedFromZ());
        System.out.println();
            // Biene W: Pflanze X=2, Pflanze Y=3, Pflanze Z=1
        System.out.println("Biene W: Pflanze X=2, Pflanze Y=0 (nicht nutzbar), Pflanze Z=1");
        beeW.visit(plantX);
        beeW.visit(plantX);
        System.out.println(beeW.collectedFromX());
        beeW.visit(plantY);
        beeW.visit(plantY);
        beeW.visit(plantY);
        System.out.println(beeW.collectedFromY());
        beeW.visit(plantZ);
        System.out.println(beeW.collectedFromZ());
        System.out.println();
            // Pflanze X: Biene U=1, Biene V=3, Biene W=2
        System.out.println("Pflanze X: Biene U=1, Biene V=0 (nicht nutzbar), Biene W=2");
        System.out.println(plantX.visitedByU());
        System.out.println(plantX.visitedByV());
        System.out.println(plantX.visitedByW());
        System.out.println();
            // Pflanze Y: Biene U=2, Biene V=1, Biene W=3
        System.out.println("Pflanze Y: Biene U=2, Biene V=1, Biene W=0 (nicht nutzbar)");
        System.out.println(plantY.visitedByU());
        System.out.println(plantY.visitedByV());
        System.out.println(plantY.visitedByW());
        System.out.println();
            // Pflanze Z: Biene U=3, Biene V=2, Biene W=1
        System.out.println("Pflanze Z: Biene U=0 (nicht nutzbar), Biene V=2, Biene W=1");
        System.out.println(plantZ.visitedByU());
        System.out.println(plantZ.visitedByV());
        System.out.println(plantZ.visitedByW());

        // --- SIMULATIONEN ---

        System.out.print("""
                \n======================================
                ===          SIMULATIONEN          ===
                ======================================
                """);

        System.out.print("""
                \n**********************************
                *          SIMULATION 1          *
                **********************************
                """);
        Simulation sim = new Simulation();
        sim.simulate();

        System.out.print("""
                \n**********************************
                * SIMULATION 2 (gleiche Instanz) *
                **********************************
                """);
        sim.simulate();

        System.out.print("""
                \n**********************************
                *   SIMULATION 2 (neue Instanz)  *
                **********************************
                """);
        Simulation sim2 = new Simulation();
        sim2.simulate();

        System.out.print("""
                \n======================================
                ===            KONTEXT B           ===
                ======================================
                """);

        // Wird öfter verwendet
        Class<Test> c = Test.class;
        String[] klassen = c.getAnnotation(Komponenten.class).klassen();
        String[] interfaces = c.getAnnotation(Komponenten.class).interfaces();
        String[] annotationen = c.getAnnotation(Komponenten.class).annotationen();

        // Die folgenden drei Arrays werden verwendet, um die Hauptverantwortlichkeiten zu zählen.
        // Die Reihenfolge der Gruppenmitglieder ist alphabetisch: Christine, Felix, Yutaka.
        String[] gruppenmitglieder = {"Christine", "Felix", "Yutaka"};
        // Zählt die Hauptverantwortlichkeit für Komponenten (Klassen, Interfaces und Annotationen)
        int[] anzK = new int[3];
        // Zählt die Hauptverantwortlichkeit für Methoden und Konstruktoren
        int[] anzM = new int[3];
        // Zählt die Hauptverantwortlichkeit für Zusicherungen
        int[] anzZ = new int[3];

        // Ausgabe der Namen aller zur Lösung dieser Aufgabe selbst geschriebenen
        // Klassen, Interfaces und Annotationen
        System.out.print("""
                \n======================================
                ===            PUNKT 1:           ===
                ===    NAMEN ALLER KOMPONENTEN    ===
                ======================================
                """);
        System.out.println("""
                Die Namen aller zur Lösung dieser Aufgabe selbst geschriebenen Klassen, Interfaces und Annotationen:
                """);

        // Für Klassen
        System.out.println("Klassen:");
        for (String s : klassen) {
            System.out.println("\t" + s);
        }

        // Für Interfaces
        System.out.println("\nInterfaces:");
        for (String s : interfaces) {
            System.out.println("\t" + s);
        }

        // Für Annotationen
        System.out.println("\nAnnotationen:");
        for (String s : annotationen) {
            System.out.println("\t" + s);
        }

        // Ausgabe der Zuordnung zwischen den Namen aller zur Lösung dieser
        // Aufgabe selbst geschriebenen Klassen, Interfaces und Annotationen
        // zum Namen jeweils eines Gruppenmitglieds, das für die Entwicklung der
        // Einheit hauptverantwortlich ist.

        System.out.print("""
                \n======================================
                ===            PUNKT 2:           ===
                ===      HAUPTVERANTWORTLICHE     ===
                ======================================
                """);
        System.out.println("""
                Zuordnung zwischen den Namen aller zur Lösung dieser Aufgabe selbst geschriebenen Klassen, Interfaces und Annotationen und dem jeweiligen Hauptverantwortlichen:
                """);

        // Für Klassen
        for (String s : klassen) {
            try {
                Class<?> tmp = Class.forName(s);
                Hauptverantwortlicher v = tmp.getAnnotation(Hauptverantwortlicher.class);
                System.out.println("Klasse: " + s + " => Hauptverantwortlicher: " + v.value());
                // Zählen der Hauptverantwortlichkeiten
                for (int i = 0; i < anzK.length; i++) {
                    if (gruppenmitglieder[i].equals(v.value())) {
                        anzK[i]++;
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // Für Interfaces
        for (String s : interfaces) {
            try {
                Class<?> tmp = Class.forName(s);
                Hauptverantwortlicher v = tmp.getAnnotation(Hauptverantwortlicher.class);
                System.out.println("Interface: " + s + " => Hauptverantwortlicher: " + v.value());
                // Zählen der Hauptverantwortlichkeiten
                for (int i = 0; i < anzK.length; i++) {
                    if (gruppenmitglieder[i].equals(v.value())) {
                        anzK[i]++;
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // Für Annotationen
        for (String s : annotationen) {
            try {
                Class<?> tmp = Class.forName(s);
                Hauptverantwortlicher v = tmp.getAnnotation(Hauptverantwortlicher.class);
                System.out.println("Annotation: " + s + " => Hauptverantwortlicher: " + v.value());
                // Zählen der Hauptverantwortlichkeiten
                for (int i = 0; i < anzK.length; i++) {
                    if (gruppenmitglieder[i].equals(v.value())) {
                        anzK[i]++;
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        // Für selbst geschriebene Klassen und Interfaces Ausgabe der Signaturen
        // aller darin enthaltenen Methoden und Konstruktoren sowie alle
        // dafür geltenden Zusicherungen (getrennt nach Vor- und Nachbedingungen
        // auf Methoden und Konstruktoren, sowie Invarianten und
        // History-Constraints auf Klassen und Interfaces), einschließlich der
        // Zusicherungen, die aus Obertypen übernommen („geerbt“) wurden.
        System.out.print("""
                \n======================================
                ===            PUNKT 3:           ===
                ===           SIGNATUREN          ===
                ======================================
                """);
        System.out.println("""
                Signaturen der Methoden und Konstruktoren in allen selbst geschriebenen Klassen und Interfaces inklusive aller Zusicherungen:
                """);

        // Für Klassen
        for (String s : klassen) {
            try {
                Class<?> tmp = Class.forName(s);
                // Klasse ausgeben
                System.out.println("Klasse: " + s + "\n");
                Class<?> current = tmp;
                ArrayList<Zusicherung> zusicherungen = new ArrayList<>();
                ArrayList<Zusicherung> zusicherungenGeerbt = new ArrayList<>();
                ArrayList<Zusicherung> hinzufuegenZ = zusicherungen;
                ArrayList<Class<?>> superClassesAndInterfaces = new ArrayList<>();
                while (current != null) {
                    // Zusicherungen im Klassenkopf
                    Zusicherung[] zusicherungenFromCurrent = current.getAnnotationsByType(Zusicherung.class);
                    for (Zusicherung zusicherung : zusicherungenFromCurrent) {
                        // Geerbte Zusicherungen, die in der Unterklasse erneut hingeschrieben wurden, nicht doppelt ausgeben.
                        if (!zusicherung.geerbt()) {
                            hinzufuegenZ.add(zusicherung);
                            // Zählen der Hauptverantwortlichkeiten
                            for (int i = 0; i < anzK.length; i++) {
                                // Zusicherung nur zählen, wenn sie in dieser Klasse deklariert wurde (wurde nur einmal geschrieben)
                                if (current.equals(tmp) && gruppenmitglieder[i].equals(zusicherung.author())) {
                                    anzZ[i]++;
                                }
                            }
                        }
                    }
                    // Zusicherungen an Feldern
                    Field[] fields = current.getDeclaredFields();
                    for (Field field : fields) {
                        Zusicherung[] zusicherungenF = field.getAnnotationsByType(Zusicherung.class);
                        for (Zusicherung z : zusicherungenF) {
                            // Geerbte Zusicherungen, die in der Unterklasse erneut hingeschrieben wurden, nicht doppelt ausgeben.
                            if (!z.geerbt()) {
                                hinzufuegenZ.add(z);
                                // Zählen der Hauptverantwortlichkeiten
                                for (int i = 0; i < anzK.length; i++) {
                                    // Zusicherung nur zählen, wenn sie in dieser Klasse deklariert wurde (wurde nur einmal geschrieben)
                                    if (current.equals(tmp) && gruppenmitglieder[i].equals(z.author())) {
                                        anzZ[i]++;
                                    }
                                }
                            }
                        }
                    }
                    superClassesAndInterfaces.addAll(Arrays.asList(current.getInterfaces())); // Auch Zusicherungen von implementierten Interfaces
                    superClassesAndInterfaces.add(current.getSuperclass()); // Auch Zusicherungen der Oberklasse
                    current = superClassesAndInterfaces.removeFirst();
                    hinzufuegenZ = zusicherungenGeerbt;
                }

                // Zusicherungen der Klasse ausgeben
                System.out.println("\tZusicherung(en) der Klasse: ");
                // Eigene
                for (Zusicherung z : zusicherungen) {
                    System.out.println("\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                }
                // Geerbte
                if (!zusicherungenGeerbt.isEmpty()) System.out.println("\tGeerbt:");
                for (Zusicherung z : zusicherungenGeerbt) {
                    System.out.println("\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                }
                System.out.println();

                // Methoden der Klasse ausgeben
                System.out.println("\tMethode(n):");
                ArrayList<Method> methods = new ArrayList<>();
                current = tmp;
                // Hinzufügen der aller Methoden (inklusive der geerbten)
                while (current != null) {
                    Method[] methodsFromCurrent = current.getDeclaredMethods();
                    for (Method method : methodsFromCurrent) {
                        loop:
                        if (!methods.contains(method)) {
                            for (Method m : methods){
                                // Überschriebene Methoden der Oberklasse nicht ausgeben
                                if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                                && m.getReturnType().equals(method.getReturnType())) {
                                    break loop;
                                }
                            }
                            methods.add(method);
                        }
                    }
                    superClassesAndInterfaces.addAll(Arrays.asList(current.getInterfaces()));  // Auch Default-Methoden von implementierten Interfaces
                    superClassesAndInterfaces.add(current.getSuperclass()); // Auch Methoden der Oberklasse
                    current = superClassesAndInterfaces.removeFirst();
                }


                for (Method m : methods) {
                    //Ausgabe der Methodensignatur
                    System.out.println("\t\t" + m);

                    // Hauptverantwortlichen für Methode suchen (für die Ausgabe in Punkt 5)
                    Hauptverantwortlicher v = m.getAnnotation(Hauptverantwortlicher.class);
                    // Zählen der Hauptverantwortlichkeiten
                    if (v != null) {
                        for (int i = 0; i < anzM.length; i++) {
                            // Geerbte Methoden nicht nochmal zählen (wurden nur 1x geschrieben)
                            if (m.getDeclaringClass().equals(tmp) && gruppenmitglieder[i].equals(v.value())) {
                                anzM[i]++;
                            }
                        }
                    }

                    // Zusicherungen der jeweiligen Methode ausgeben
                    Zusicherung[] zusicherungenM = m.getAnnotationsByType(Zusicherung.class);
                    if (zusicherungenM.length > 0) {
                        System.out.println("\t\tZusicherung(en) der Methode: ");
                        for (Zusicherung z : zusicherungenM) {
                            System.out.println("\t\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                            // Zählen der Hauptverantwortlichkeiten
                            for (int i = 0; i < anzK.length; i++) {
                                // Geerbte Zusicherungen nicht nochmal zählen (wurden nur 1x geschrieben)
                                if (m.getDeclaringClass().equals(tmp) && gruppenmitglieder[i].equals(z.author())) {
                                    anzZ[i]++;
                                }
                            }
                        }
                    }
                }
                System.out.println("\n\tKonstruktor:");
                // Konstruktoren müssen separat ausgegeben werden
                Constructor<?>[] constructors = tmp.getConstructors();
                for (Constructor<?> constructor : constructors) {
                    //Ausgabe der Signatur des Konstruktors
                    System.out.println("\t\t" + constructor);

                    // Zusicherungen des jeweiligen Konstruktors ausgeben
                    Zusicherung[] zusicherungenC = constructor.getAnnotationsByType(Zusicherung.class);
                    if (zusicherungenC.length > 0) {
                        System.out.println("\t\tZusicherung(en) des Konstruktors: ");
                        for (Zusicherung z : zusicherungenC) {
                            System.out.println("\t\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                            // Zählen der Hauptverantwortlichkeiten
                            for (int i = 0; i < anzK.length; i++) {
                                if (gruppenmitglieder[i].equals(z.author())) {
                                    anzZ[i]++;
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println();
        }

        // Für Interfaces
        for (String s : interfaces) {
            try {
                Class<?> tmp = Class.forName(s);
                // Interface ausgeben
                System.out.println("Interface: " + s + "\n");
                Class<?> current = tmp;
                ArrayList<Zusicherung> zusicherungen = new ArrayList<>();
                ArrayList<Zusicherung> zusicherungenGeerbt = new ArrayList<>();
                ArrayList<Zusicherung> hinzufuegenZ = zusicherungen;
                ArrayList<Class<?>> ifs = new ArrayList<>();
                while (current != null) {
                    // Zusicherungen im Interfacekopf
                    Zusicherung[] zusicherungenFromCurrent = current.getAnnotationsByType(Zusicherung.class);
                    for (Zusicherung zusicherung : zusicherungenFromCurrent) {
                        // Geerbte Zusicherungen, die in implementierenden Interfaces erneut hingeschrieben wurden, nicht doppelt ausgeben.
                        if (!zusicherung.geerbt()) {
                            hinzufuegenZ.add(zusicherung);
                            // Zählen der Hauptverantwortlichkeiten
                            for (int i = 0; i < anzK.length; i++) {
                                // Zusicherung nur zählen, wenn sie in diesem Interface deklariert wurde (wurde nur einmal geschrieben)
                                if (current.equals(tmp) && gruppenmitglieder[i].equals(zusicherung.author())) {
                                    anzZ[i]++;
                                }
                            }
                        }
                    }
                    // Zusicherungen an Feldern
                    Field[] fields = current.getDeclaredFields();
                    for (Field field : fields) {
                        Zusicherung[] zusicherungenF = field.getAnnotationsByType(Zusicherung.class);
                        for (Zusicherung z : zusicherungenF) {
                            // Geerbte Zusicherungen, die in implementierenden Interfaces erneut hingeschrieben wurden, nicht doppelt ausgeben.
                            if (!z.geerbt()) {
                                hinzufuegenZ.add(z);
                                // Zählen der Hauptverantwortlichkeiten
                                for (int i = 0; i < anzK.length; i++) {
                                    // Zusicherung nur zählen, wenn sie in diesem Interface deklariert wurde (wurde nur einmal geschrieben)
                                    if (current.equals(tmp) && gruppenmitglieder[i].equals(z.author())) {
                                        anzZ[i]++;
                                    }
                                }
                            }
                        }
                    }
                    ifs.addAll(Arrays.asList(current.getInterfaces())); // Auch geerbte Zusicherungen von beerbten Interfaces
                    if (!ifs.isEmpty()) {
                        current = ifs.removeFirst();
                    } else {
                        current = null;
                    }
                    hinzufuegenZ = zusicherungenGeerbt;
                }

                // Zusicherungen des Interfaces ausgeben
                System.out.println("\tZusicherung(en) des Interfaces: ");
                // Eigene
                for (Zusicherung z : zusicherungen) {
                    System.out.println("\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                }
                // Geerbte
                if (!zusicherungenGeerbt.isEmpty()) System.out.println("\tGeerbt:");
                for (Zusicherung z : zusicherungenGeerbt) {
                    System.out.println("\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                }
                System.out.println();

                // Methoden des Interfaces ausgeben
                System.out.println("\tMethode(n):");
                ArrayList<Method> methods = new ArrayList<>();
                current = tmp;
                // Hinzufügen der aller Methoden (inklusive der geerbten)
                while (current != null) {
                    Method[] methodsFromCurrent = current.getDeclaredMethods();
                    for (Method method : methodsFromCurrent) {
                        loop:
                        if (!methods.contains(method)) {
                            for (Method m : methods){
                                // Überschriebene Methoden von implementierten Interfaces nicht ausgeben
                                if (m.getName().equals(method.getName()) && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                                        && m.getReturnType().equals(method.getReturnType())) {
                                    break loop;
                                }
                            }
                            methods.add(method);
                        }
                    }
                    ifs.addAll(Arrays.asList(current.getInterfaces())); // Auch geerbte Methoden von beerbten Interfaces
                    if (!ifs.isEmpty()) {
                        current = ifs.removeFirst();
                    } else {
                        current = null;
                    }
                }


                for (Method m : methods) {
                    //Ausgabe der Methodensignatur
                    System.out.println("\t\t" + m);

                    // In Interfaces Methoden-Zähler nicht hochzählen

                    // Zusicherungen der jeweiligen Methode ausgeben
                    Zusicherung[] zusicherungenM = m.getAnnotationsByType(Zusicherung.class);
                    if (zusicherungenM.length > 0) {
                        System.out.println("\t\tZusicherung(en) der Methode: ");
                        for (Zusicherung z : zusicherungenM) {
                            System.out.println("\t\t\tTyp: " + z.typ() + "; Zusicherung: " + z.zusicherung() + "; Autor: " + z.author());
                            // Zählen der Hauptverantwortlichkeiten
                            for (int i = 0; i < anzK.length; i++) {
                                // Geerbte Zusicherungen nicht nochmal zählen (wurden nur 1x geschrieben)
                                if (m.getDeclaringClass().equals(tmp) && gruppenmitglieder[i].equals(z.author())) {
                                    anzZ[i]++;
                                }
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println();
        }

        // Für jedes Gruppenmitglied Ausgabe der Anzahl der Klassen, Interfaces und
        // Annotationen, für die dieses Gruppenmitglied hauptverantwortlich
        // ist (als eine einzige ganze Zahl).
        System.out.print("""
                \n======================================
                ===            PUNKT 4:           ===
                ===       ANZAHL KOMPONENTEN      ===
                ======================================
                """);
        System.out.println("""
                Anzahl der Klassen, Interfaces und Annotationen, für die jedes Gruppenmitglied hauptverantwortlich ist:
                """);

        // Ausgabe der oben gesammelten Daten
        for (int i = 0; i < 3; i++){
            System.out.println(gruppenmitglieder[i] + ": " + anzK[i]);
        }

        // Für jedes Gruppenmitglied Ausgabe der Anzahl der Methoden und
        // Konstruktoren in den Klassen (nur Klassen), für die dieses
        // Gruppenmitglied hauptverantwortlich ist (als eine einzige ganze Zahl).
        System.out.print("""
                \n======================================
                ===            PUNKT 5:           ===
                ===        ANZAHL METHODEN        ===
                ======================================
                """);
        System.out.println("""
                Anzahl der Methoden und Konstruktoren in den Klassen, für die jedes Gruppenmitglied hauptverantwortlich ist:
                """);

        // Ausgabe der oben gesammelten Daten
        for (int i = 0; i < 3; i++){
            System.out.println(gruppenmitglieder[i] + ": " + anzM[i]);
        }

        // Für jedes Gruppenmitglied Ausgabe der Anzahl der Zusicherungen in den
        // Klassen und Interfaces (samt Methoden), für die dieses Gruppenmitglied
        // hauptverantwortlich ist (als eine einzige ganze Zahl).
        System.out.print("""
                \n======================================
                ===            PUNKT 6:           ===
                ===      ANZAHL ZUSICHERUNGEN     ===
                ======================================
                """);
        System.out.println("""
                Anzahl der Zusicherungen in den Klassen und Interfaces (samt Methoden), für die jedes Gruppenmitglied hauptverantwortlich ist:
                """);

        // Ausgabe der oben gesammelten Daten
        for (int i = 0; i < 3; i++){
            System.out.println(gruppenmitglieder[i] + ": " + anzZ[i]);
        }

    }

}
