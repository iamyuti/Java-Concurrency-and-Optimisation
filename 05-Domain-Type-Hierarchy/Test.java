import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Testklasse, in der die Simulationen ablaufen
 */
public class Test {

    // --- KEINE UNTERTYPBEZIEHUNG ---
    // OBSERVATION: Obertyp aller Typen, daher nicht selbst Subtyp eines anderen Typen
    // POLLINATOR: Subtyp von Observation und Obertyp aller anderen Typen außer Wasp.
    //            X Keine Beziehung zu Wasp:
    //              "Viele Wespenarten sind keine Bestäuber", d.h. Wasp kann kein Subtyp von Pollinator sein.
    //              Neben Bienen sind auch Schwebfliegen, Käfer & Schmetterlinge Bestäuber, welche keine Wespen sind.
    //              (z.B. in Flowerfly: "...sind Bestäuber... nicht mit Bienen und Wespen verwandt...")
    //              d.h. Pollinator kann kein Subtyp von Wasp sein.
    // WASP: Subtyp von Observation und Obertyp aller anderen Typen außer Pollinator und dessen Subtyp Flowerfly.
    //            X Keine Beziehung zu Pollinator:
    //              Siehe Pollinator
    //            X Keine Beziehung zu Flowerfly:
    //              Siehe Flowerfly.
    // FLOWERFLY: Subtyp von Pollinator & damit von Observation.
    //            X Keine Beziehung zu Wasp oder Bee (und damit all ihrer Untertypen):
    //              Explizit ausgeschlossen durch: "...sind daher nicht mit Bienen und Wespen verwandt."
    //              D.h. Flowerfly kann weder Subtyp von Bee oder Wasp sein, noch andersrum.
    // BEE: Subtyp von Observation, Pollinator & Wasp. Obertyp aller anderen Typen außer Flowerfly.
    //            X Keine Beziehung zu Flowerfly:
    //              Siehe Flowerfly.
    //              Diese nicht bestehende Beziehung + Begründung gilt für alle Untertypen von Bee.
    // Ab hier gilt für die expliziten Arten HoneyBee, Bumblebee, Lassioglossum Calcateum, Osmia Cornuta und Andrena Bucephala,
    // dass diese nie Obertyp eines anderen Typs sind. Dies ergibt sich daraus, dass es sich eben um explizite Arten
    // handelt, d.h. keine andere Art von dieser Art ist, geschweige denn "allgemeinere" Typen immer von dieser Art.
    // WILDBEE: Subtyp von Bee & all ihrer Obertypen. (Indirekter) Obertyp aller Typen außer SocialBee & Honeybee.
    //            X Keine Beziehung zu SocialBee:
    //              Nicht alle sozial lebenden Bienen sind Wildbienen. Ein konkretes Gegenbeispiel ist Honeybee (s.u.)
    //              D.h. SocialBee kann kein Subtyp von Wildbee sein.
    //              Andersrum leben auch nicht alle Wildbienen sozial. Ein konkretes Gegenbeispiel ist OsmiaCornuta,
    //              welche eine "solitär lebende Wildbiene" ist, also nie sozial lebt.
    //              Zudem sind alle solitär lebenden Bienen Wildbienen, können aber niemals Subtyp von SocialBee sein (s.u.)
    //              D.h. Wildbee kann auch kein Subtyp von SocialBee sein.
    //            X Keine Beziehung zu Honeybee:
    //              Wird explizit ausgeschlossen durch "sodass Honigbienen generell nicht zu den Wildbienen zählen."
    //              D.h. Honeybee ist kein Subtyp von Wildbee. Andersherum sind nicht alle Wildbienen Honigbienen,
    //              da es genug Beispiele von Wildbienen gibt, die zu einer anderen Art gehören (z.B. OsmiaCornuta).
    //              Außerdem sind Honigbienen nie Wildbienen, da sie immer aus einer Zucht kommen. Das müsste bei einer
    //              Subtypbeziehung dann auch für alle Wildbienen gelten, was sich ausschließt.
    //              D.h. Wildbee ist auch kein Subtyp von Honeybee.
    // SOLITARY BEE: Subtyp von Wildbee und all ihrer Obertypen. Obertyp von CommunalBee, Andrena Bucephala, Osmia
    //              Cornuta und Lassioglossum Calcateum. Keine Beziehung zu SocialBee, Bumblebee und Honeybee.
    //            X Keine Beziehung zu SocialBee:
    //              Die soziale Lebensweise steht im Gegensatz zur solitären Lebensweise.
    //              Solitäre Bienen bilden keinen Staat. Soziale Bienen bilden einen Staat.
    //              Auch wenn einige Arten auf beide Lebensweisen leben können, wird niemals gelten,
    //              dass ALLE sozialen Bienen auch solitär leben können oder andersrum.
    //              Konkrete Gegenbeispiele sind:
    //              Honeybee, die IMMER sozial lebt und daher nicht solitär leben KANN.
    //              Osmia Cornuta, die IMMER solitär lebt und daher nie sozial.
    //              D.h. SolitaryBee kann kein Subtyp von SocialBee sein und andersrum.
    //            X Keine Beziehung zu Bumblebee & Honeybee:
    //              Alle Hummeln sind staatenbildend (= sozial). D.h. eine Hummeln KANN nicht solitär leben.
    //              Genauso gibt es keine solitär lebenden Honigbienen.
    //              D.h. Bumblebee & Honeybee können kein Subtyp von SolitaryBee sein.
    //              Trivialerweise gilt dies auch andersrum, da es solitär lebende Arten gibt, die keine Hummeln
    //              oder Honigbienen sind z.B. Osmia Cornuta.
    //              D.h. SolitaryBee kann kein Subtyp von Bumblebee oder Honeybee sein.
    // SOCIAL BEE: Subtyp von Bee & ihrer Obertypen. Obertyp von Bumblebee, Lasioglossum Calcateum und Honeybee.
    //             Keine Beziehung zu Wildbee, SolitaryBee, Osmia Cornuta & Andrena Bucephala
    //           X Keine Beziehung zu Wildbee & SolitaryBee: siehe Wildbee & SolitaryBee
    //           X Keine Beziehung zu CommunalBee:
    //             Kommunale Bienen Leben allein, soziale Bienen in Staaten.
    //             Dies schließt sich aus. Auch wenn einige Arten ggf. auf verschiedene Weisen Leben können,
    //             wird niemals gelten, dass alle kommunale Bienen auch sozial Leben können oder andersrum.
    //             Gegenbeispiele sind hier AndrenaBucephala, die kommunal oder solitär leben kann, aber NICHT sozial.
    //             Und Honeybee, die sozial lebt, aber niemals solitär oder kommunal.
    //             D.h. CommunalBee kann kein Subtyp von SocialBee sein oder andersrum.
    //           X Keine Beziehung zu Osmia Cornuta & Andrena Bucephala:
    //             Beide Arten leben niemals sozial, d.h. können kein Untertyp von SocialBee sein.
    //             Trivialerweise gilt dies auch andersrum, da es sozial lebende Arten gibt, die gehörnten
    //             Mauerbienen oder Andrena Bucephala sind, z.B. HoneyBee.
    //             D.h. SocialBee kann kein Subtyp von Osmia Cornuta oder Andrena Bucephala sein.
    // COMMUNAL BEE: Subtyp von SolidaryBee und all ihrer Obertypen, da alle kommunalen Bienen auch solitär leben können.
    //             Solitäre Bienen sind aber nicht kommunal, daher Beziehung nicht andersrum.
    //             Keine Beziehung zu SocialBee und ihrer Untertypen.
    //           X Keine Beziehung zu SocialBee: siehe SocialBee
    //           X Keine Beziehung zu expliziten Typen Bumblebee, Honeybee, Lassioglossum Calcateum, Osmia Cornuta:
    //             Für diese Arten ist explizit angegeben, dass sie IMMER nicht-kommunal leben, d.h. nicht kommunal
    //             leben können: kommunal lebende Honigbienen gibt es nicht, Lasioglossum meist social, kann solitär
    //             Bumblebee immer staatenbildend (sozial), Osmia Cornuta solitär lebend.
    //             D.h. diese Arten können kein Subtyp von CommunalBee sein.
    //             Trivialerweise gilt dies auch wieder andersrum, da nicht alle kommunalen Bienen von einer dieser Arten
    //             sind, z.B. Andrena Bucephala
    // EXPLIZITE ARTEN: HONEYBEE, BUMBLEBEE, OSMIA CORNUTA, LASSIOGLOSSUM CALCATEUM, ANDRENA BUCEPHALA:
    //             Für diese Arten wurde bereits angegeben, wieso sie nicht Subtyp einer bestimmten allgemeineren Art sind:
    //           X HONEYBEE nicht solitär/kommunal, da immer sozial und keine Wildbiene, da explizit ausgeschlossen.
    //           X BUMBLEBEE nicht solitär/kommunal, da immer staatenbildend (sozial)
    //           X OSMIA CORNUTA nicht sozial/kommunal, da immer solitär
    //           X LASSIOGLOSSUM CALCATEUM nicht kommunal, da nur sozial oder solitär
    //           X ANDRENA BUCEPHALA: nicht sozial, da nur kommunal oder solitär
    //             Trivialerweise sind diese Arten keine Subtypen voneinander, da keine der Arten zu einer der anderen
    //             Arten gehört. Sie sind quasi "Blätter" der Hierarchie.
    // OBSERVATIONLIST: Keine Beziehung zu den anderen Interfaces/Klassen, da es sich sowohl strukturell als auch semantisch
    //             um eine vollkommen andere Klasse (repräsentiert keine Beobachtung) handelt.
    //             D.h. ObservationList ist kein Subtyp einer der anderen Klassen.
    //             Andersum repäsentiert keine der anderen Klasse eine Liste, ist daher kein Subtyp von ObservationList.
    // OBSERVATIONITERATOR & SAMEBEEITEARTOR (in ObservationList): Aufgrund der Parametrisierung von SameBeeIterator mit
    //              Bee ist keine Subtypbeziehung zu ObservationIterator (Iterator parametrisiert mit Observation) möglich.
    //              Es wäre allerdings grundsätzlich möglich, diese Parametrisierung zu Iterator<Observation> zu ändern
    //              und so eine Subtypbeziehung herzustellen. Allerdings könnten dann auf über den SameBee Iterator abgerufenen
    //              Elementen auch nur Methoden des Typs Observation aufgerufen werden, da eine eindeutige Zuordnung zum Typ
    //              Bee nicht mehr möglich wäre. Auf komplexere generische Implementierungen wird im Rahmen der Aufgabe verzichtet.


    // --- ANMERKUNG ZUR ÖFFENTLICHEN SICHTBARKEIT DER GETTER-METHODEN ---
    // In allen Subklassen des Interfaces Bee existieren öffentliche Getter
    // (getPreviousBee, getChip, getSocial, getSolitary, getZucht)
    // Dies ist im Sinne des Data-Hiding nicht optimal. Dessen sind wir uns bewusst.
    // Allerdings stellte es sich als sehr komplex heraus, die Iterationsmethoden alle so auszulagern,
    // dass diese mit package-private Gettern auskommen.
    // Man könnte z.B. alle Methoden direkt in den Klassen implementieren, mit Nutzung der expliziten Klassenreferenz
    // anstelle von "Bee", sodass jede Klasse jede genutzte Methode einmal selbst implementiert.
    // Dann sind keine öffentlichen Getter in den Interfaces nötig.
    // Beispielhaft haben wir in dem Interface CommunalBee den öffentlichen Getter getCommunal() entfernt &
    // stattdessen die Implementierung in allen implementierenden Klassen (CommunalBeeTest & AndrenaBucephala)
    // jeweils angepasst auf die explizite Klasse eingesetzt.
    // Analog könnte man mit den Methoden solitary(), social(), wild() und sameBee(), sowie den
    // entsprechenden Gettern verfahren.
    // Dies hätte aber erhebliche Code-Verdopplung und einer Verkomplizierung des Systems bedeutet,
    // weswegen wir uns dafür entschieden haben, dies im Rahmen dieser Aufgabe zu belassen.
    // Analog könnte man auch, anstatt alle Methoden in allen Klassen zu implementieren,
    // dies per Generizität lösen und z.B. einem generischen sameBee-Iterator zusätzlich die Klasse,
    // sowie die Funktionen getChip und previousBee der expliziten Klasse übergeben.
    // Eine entsprechende auskommentierte (nicht final im Detail durchgetestete) Implementierung finden Sie in ObservationList.
    // Um diese zu Nutzen, müsste man in allen Klassen jeweils Konstanten & Methoden die "Bee" nutzen,
    // zur expliziten Klasse ändern (z.B. BeeTest previousBee) und dann in sameBee
    // return OBSERVATIONS.sameBee(from, to, isAscending, this, BeeTest.class, BeeTest::getChip, BeeTest::getPreviousObservation);
    // aufrufen. In dieser Testklasse müssten ebenfalls die Bezeichnungen angepasst werden.
    // Analog könnte eine generische Filter-Methode für social(), solitary(), communal() und wild() implementiert werden.
    // Allerdings würde auch dies den Rahmen der Aufgabe sprengen.

    /**
     * Detaillierte Testausgaben zu neuen Funktionen
     * @param args Argumente (wird nicht verwendet)
     */
    public static void main(String[] args) {

        // === TESTS für Subtypbeziehungen ===
        System.out.print("""
                ======================================
                ===  TESTS für Subtypbeziehungen   ===
                ======================================
                Im folgenden werden alle Typen und ihre Subtypen ausgegeben.
                Da Transitivität gilt, werden nur die direkten Subtypen gelistet.
                Alle Subtypen eines Subtyps des Typen, sind natürlich auch Subtyps des Typen.
                Zu jedem Typ werden seine Zusicherungen ausgegeben.
                Widersprüche in den Beziehungen lassen sich durch Überprüfung der Konsistenz der
                Zusicherungen finden.
                Zusicherungen des Obertyps sollten eine Teilmenge
                der Zusicherungen des Untertyps sein bzw.
                Zusicherungen im Untertyp Zusicherungen im Obertyp nicht widersprechen.
                Anmerkung: Die Klasse ObservationList wird nicht aufgeführt, da sie in keiner
                Subtypbeziehung zu einer der anderen Klassen/Interfaces steht.
                """);

        // Unser System = unsere Beobachtungs-Liste
        ObservationList system = new ObservationList();
        // Objekte aller möglichen Klassen (für Interfaces Test-Klassen)
        ObservationTest observation1 = new ObservationTest(system, LocalDateTime.of(2025, 11, 1, 1, 15), "Observation");
        PollinatorTest pollinator = new PollinatorTest(system, LocalDateTime.of(2025, 11, 1, 1, 16), "Bestäuber");
        WaspTest wasp = new WaspTest(system, LocalDateTime.of(2025, 11, 1, 1, 17), "Wespe");
        FlowerFly flowerFly = new FlowerFly(system, LocalDateTime.of(2025, 11, 1, 1, 18), "Schwebfliege");
        BeeTest bee = new BeeTest(system, LocalDateTime.of(2025, 11, 1, 1, 19), "Biene");
        WildBeeTest wildbee = new WildBeeTest(system, LocalDateTime.of(2025, 11, 1, 1, 20), "Wildbiene", null);
        SocialBeeTest socialbee = new SocialBeeTest(system, LocalDateTime.of(2025, 11, 1, 1, 21), "soziale Biene", false);
        SolitaryBeeTest solidaryBee = new SolitaryBeeTest(system, LocalDateTime.of(2025, 11, 1, 1, 22), "solitäre Biene", false, false);
        Bumblebee bumblebee = new Bumblebee(system, LocalDateTime.of(2025, 11, 1, 1, 23), "Hummel", false);
        CommunalBeeTest communalBee = new CommunalBeeTest(system, LocalDateTime.of(2025, 11, 1, 1, 24), "kommunale Biene", false, false, false);
        LasioglossumCalceatum lasioglossumCalceatum = new LasioglossumCalceatum(system, LocalDateTime.of(2025, 11, 1, 1, 25), "Lasioglossum Calceatum", false, false, false);
        Honeybee honeybee = new Honeybee(system, LocalDateTime.of(2025, 11, 1, 1, 26), "Honigbiene");
        OsmiaCornuta osmiaCornuta = new OsmiaCornuta(system, LocalDateTime.of(2025, 11, 1, 1, 27), "Gehörnte Mauerbiene", false);
        AndrenaBucephala andrenaBucephala = new AndrenaBucephala(system, LocalDateTime.of(2029, 11, 1, 1, 27), "Andrena Bucephala", false, false, false);
        // Anzeigen der Verträge
        // Jede Klasse wird nochmal selbst angezeigt, weil...
        // 1. für Übersichtlichkeit, damit der menschliche Tester besser sehen kann,
        // ob die Zusicherungen eines Untertyps zu den Zusicherungen des Obertyps passen
        // (einfacher, wenn diese direkt nebeneinander stehen)
        // 2. jeder Typ ist auch Subtyp von sich selbst
        System.out.println("\n--- Obertyp aller Klassen/Interfaces: Observation ---");
        System.out.print(observation1);
        System.out.println("\n--- Direkte Subtypen von Observation ---");
        System.out.print(observation1);
        System.out.print(pollinator);
        System.out.print(wasp);
        System.out.println("\n--- Direkte Subtypen von Pollinator ---");
        System.out.print(pollinator);
        System.out.print(flowerFly);
        System.out.print(bee);
        System.out.println("\n--- Direkte Subtypen von Wasp ---");
        System.out.print(wasp);
        System.out.print(bee);
        System.out.println("\n--- Direkte Subtypen von Bee ---");
        System.out.print(bee);
        System.out.print(wildbee);
        System.out.print(socialbee);
        System.out.println("\n--- Direkte Subtypen von WildBee ---");
        System.out.print(wildbee);
        System.out.print(solidaryBee);
        System.out.print(bumblebee);
        System.out.println("\n--- Direkte Subtypen von SocialBee ---");
        System.out.print(socialbee);
        System.out.print(bumblebee);
        System.out.print(honeybee);
        System.out.print(lasioglossumCalceatum);
        System.out.println("\n--- Direkte Subtypen von SolitaryBee ---");
        System.out.print(solidaryBee);
        System.out.print(communalBee);
        System.out.print(lasioglossumCalceatum);
        System.out.print(osmiaCornuta);
        System.out.println("\n--- Direkte Subtypen von CommunalBee ---");
        System.out.print(communalBee);
        System.out.print(andrenaBucephala);

        // Alle wieder aus Datenbestand entfernen, damit folgende Tests übersichtlicher sind
        Iterator<Observation> os = andrenaBucephala.earlier();
        while (os.hasNext()) {
            os.next().remove();
        }
        andrenaBucephala.remove();


        // === TESTS für Observationsmethoden ===
        System.out.print("""
                \n======================================
                === TESTS für Observationsmethoden ===
                ======================================
                Im folgenden werden die Methoden der Klasse Observation getestet.
                Dafür werden 8 Observationen erstellt.
                
                """);

        // Observationen, Benennung entspricht zeitlicher Reihenfolge (DateTime der Beobachtung), nicht Erstellungsreihenfolge
        Observation o4 = new ObservationTest(system, LocalDateTime.of(2028, 11, 1, 1, 15), "4");
        Observation o5 = new ObservationTest(system, LocalDateTime.of(2029, 11, 1, 1, 15), "5");
        Observation o1 = new ObservationTest(system, LocalDateTime.of(2025, 11, 1, 1, 15), "1");
        Observation o7 = new ObservationTest(system, LocalDateTime.of(2031, 11, 1, 1, 15), "7");
        Observation o2 = new ObservationTest(system, LocalDateTime.of(2026, 11, 1, 1, 15), "2");
        Observation o3 = new ObservationTest(system, LocalDateTime.of(2027, 11, 1, 1, 15), "3");
        Observation o6 = new ObservationTest(system, LocalDateTime.of(2030, 11, 1, 1, 15), "6");
        Observation o8 = new ObservationTest(system, LocalDateTime.of(2031, 11, 1, 1, 15), "8");
        // Tests der Observationsmethoden
            // getDateTime() & getComment()
        System.out.println("--- Teste Ausgabe Kommentar & Datum und Uhrzeit von Observation 8 ---");
        System.out.println("Datum & Uhrzeit: " + o8.getDateTime().toString() +
                "\nKommentar: " + o8.getComment());
            // remove() & valid()
        System.out.println("\n--- Teste Entfernen einer Observation aus Datenbestand & Rückgabe, ob Beobachtung entfernt ist  ---");
        System.out.println("Observation 8 im Datenbestand?  " + o8.valid());
        System.out.println("- Entferne Observation... -" );
        o8.remove();
        System.out.println("Observation 8 im Datenbestand?  " + o8.valid());
        // remove() & valid()
        System.out.println("\n--- Teste doppeltes Entfernen  ---");
        try {
            System.out.println("- Entferne  8 noch zweimal... -" );
            o8.remove();
            o8.remove();
        } catch (Exception e) {
            System.out.println("Doppeltes Entfernen warf Fehler: " + e);
        }
        System.out.println("Observation 8 im Datenbestand?  " + o8.valid());
            // earlier()
        System.out.println("\n--- Teste Ausgabe aller Observationen vor Observation 4 (zeitlich) ---");
        Iterator<Observation> i1 = o4.earlier();
        while (i1.hasNext()) {
            System.out.println(i1.next().getComment());
        }
            // later()
        System.out.println("\n--- Teste Ausgabe aller Observationen nach Observation 2 (zeitlich) ---");
        Iterator<Observation> i2 = o2.later();
        while (i2.hasNext()) {
            System.out.println(i2.next().getComment());
        }
            // Dynamisches Einfügen & Löschen nach Instanziierung eines Iterators
        System.out.println("\n--- Teste dynamisches Einfügen eines neuen Elements im noch auszugebenden Bereich ---");
        System.out.println("- Erstelle Iterator für Elemente nach 1, lasse bis Ende laufen -");
        Iterator<Observation> i31 = o1.later();
        while (i31.hasNext()) {
            System.out.println(i31.next().getComment());
        }
        System.out.println("- Füge nun nach Erstellung neues Element am Ende hinzu (Sollte sichtbar sein) -");
        Observation o9 = new ObservationTest(system, LocalDateTime.of(2040, 10, 1, 1, 15), "am Ende");
        while (i31.hasNext()) {
            System.out.println(i31.next().getComment());
        }
        System.out.println("\n--- Teste dynamisches Einfügen eines neuen Elements im noch auszugebenden Bereich ---");
        System.out.println("- Erstelle Iterator für Elemente nach 1, lasse bis 3 laufen -");
        Iterator<Observation> i32 = o1.later();
        if (i32.hasNext()) {
            System.out.println(i32.next().getComment());
        }
        if (i32.hasNext()) {
            System.out.println(i32.next().getComment());
        }
        System.out.println("- Füge nun nach Erstellung neues Element zwischen 5 & 6 ein (Sollte sichtbar sein) -");
        Observation o56 = new ObservationTest(system, LocalDateTime.of(2030, 10, 1, 1, 15), "zwischen 5 & 6");
        System.out.println("- Lasse weiter laufen -");
        while (i32.hasNext()) {
            System.out.println(i32.next().getComment());
        }
        o56.remove();
        System.out.println("- Lösche Element zwischen 5 & 6 wieder für Übersichtlichkeit folgender Tests -");
        System.out.println("\n--- Teste dynamisches Einfügen eines neuen Elements im nicht mehr auszugebenden Bereich ---");
        System.out.println("--- Und gleichzeitiges dynamisches Löschen eines Elements ---");
        System.out.println("- Erstelle Iterator für Elemente vor 5, lasse bis 3 laufen -");
        Iterator<Observation> i4 = o5.earlier();
        if (i4.hasNext()) {
            System.out.println(i4.next().getComment());
        }
        if (i4.hasNext()) {
            System.out.println(i4.next().getComment());
        }
        System.out.println("- Füge nun nach Erstellung neues Element zwischen 3 & 4 ein (Sollte nicht sichtbar sein) -");
        System.out.println("- Und lösche Element 1 -");
        ObservationTest o34 = new ObservationTest(system, LocalDateTime.of(2028, 10, 1, 1, 15), "zwischen 3 & 4");
        o1.remove();
        System.out.println("- Lasse weiter laufen  -");
        while (i4.hasNext()) {
            System.out.println(i4.next().getComment());
        }
        o34.remove();
        System.out.println("\n--- Teste dynamisches Löschen des aktuellen Elements ---");
        System.out.println("- Erstelle Iterator für Elemente vor 7, lasse bis 5 laufen -");
        Iterator<Observation> i5 = o7.earlier();
        if (i5.hasNext()) {
            System.out.println(i5.next().getComment());
        }
        if (i5.hasNext()) {
            System.out.println(i5.next().getComment());
        }
        System.out.println("- Iterator steht jetzt auf Element 4, lösche Element 4 -");
        o4.remove();
        System.out.println("- Lasse weiter laufen -");
        while (i5.hasNext()) {
            System.out.println(i5.next().getComment());
        }
        Observation o4_neu = new ObservationTest(system, LocalDateTime.of(2028, 11, 1, 1, 15), "4");
        Observation o1_neu = new ObservationTest(system, LocalDateTime.of(2025, 11, 1, 1, 15), "1");
        System.out.println("\n--- Teste mehrfaches dynamisches Löschen & Einfügen an verschiedenen Stellen ---");
        System.out.println("- Erstelle neues Element 1 und 4 (für Übersichtlichkeit) -");
        System.out.println("- Erstelle Iterator für Elemente vor 7, lasse bis 5 laufen -");
        Iterator<Observation> i6 = o7.earlier();
        if (i6.hasNext()) {
            System.out.println(i6.next().getComment());
        }
        if (i6.hasNext()) {
            System.out.println(i6.next().getComment());
        }
        System.out.println("- Iterator steht jetzt auf Element 4, lösche Element 4 & 3 und 6 -");
        System.out.println("- Füge Elemente zwischen 2 und 1 ein -");
        Observation o12_f = new ObservationTest(system, LocalDateTime.of(2025, 11, 2, 1, 15), "zwischen 1 und 2, früher");
        Observation o12_s = new ObservationTest(system, LocalDateTime.of(2025, 11, 3, 1, 15), "zwischen 1 und 2, später");
        o4_neu.remove();
        o3.remove();
        o6.remove();
        System.out.println("- Lasse weiter laufen -");
        while (i6.hasNext()) {
            System.out.println(i6.next().getComment());
        }
        o12_f.remove();
        o12_s.remove();
            // Tests mit verschiedenen Subtypen von Observation
        System.out.println("\n--- Teste Iterator mit gemischten Elementen vom Typ Observation und Subklassen ---");
        System.out.println("- Erstelle neues Element 3, 4 & 6 (für Übersichtlichkeit) -");
        Observation o4_neu_neu = new ObservationTest(system, LocalDateTime.of(2028, 11, 1, 1, 15), "4");
        Observation o3_neu = new ObservationTest(system, LocalDateTime.of(2027, 11, 1, 1, 15), "3");
        Observation o6_neu = new ObservationTest(system, LocalDateTime.of(2030, 11, 1, 1, 15), "6");
        System.out.println("- Erstelle Bestäuber, Schwebfliege & Biene zwischen 1 & 2 und Lasioglossum zwischen 4 & 5 -");
        Observation flowerFly12 = new FlowerFly(system, LocalDateTime.of(2025, 12, 1, 1, 18), "Schwebfliege, zwischen 1 & 2");
        Observation bee12 = new BeeTest(system, LocalDateTime.of(2025, 12, 25, 1, 19), "Biene, zwischen 1 & 2, nach Schwebfliege");
        Observation pt = new PollinatorTest(system, LocalDateTime.of(2025, 12, 25, 1, 20), "Bestäuber, zwischen 1 & 2, nach Biene");
        Observation lasioglossumCalceatum45 = new LasioglossumCalceatum(system, LocalDateTime.of(2028, 12, 1, 1, 25), "Lasioglossum Calceatum, zwischen 4 & 5", false, false, false);
        System.out.println("- Erstelle Iterator für Elemente vor 7, lasse bis Lasioglossum laufen -");
        Iterator<Observation> i7 = o7.earlier();
        if (i7.hasNext()) {
            System.out.println(i7.next().getComment());
        }
        if (i7.hasNext()) {
            System.out.println(i7.next().getComment());
        }
        if (i7.hasNext()) {
            System.out.println(i7.next().getComment());
        }
        System.out.println("- Iterator steht jetzt auf Element 4 nach Lasioglossum, lösche Element 4 & 3  -");
        System.out.println("- Füge Honigbiene & Wespe direkt vor Lasioglossum ein -");
        o4_neu_neu.remove();
        o3_neu.remove();
        Observation wp = new WaspTest(system, LocalDateTime.of(2028, 11, 2, 1, 26), "Wespe, vor Lasioglossum");
        Observation honeybee45 = new Honeybee(system, LocalDateTime.of(2028, 11, 2, 1, 26), "Honigbiene, vor Lasioglossum");
        System.out.println("- Lasse weiter laufen -");
        while (i7.hasNext()) {
            System.out.println(i7.next().getComment());
        }

        // === TESTS für Iteratoren ===
        System.out.print("""
                \n======================================
                === TESTS für Iterationsmethoden ===
                ======================================
                Im folgenden werden die Iterationsmethoden der verschiedenen Arten getestet.
                
                """);

        // Unterschiedliche Bienen (auch Subtypen von Bee)
        Bee Bee1 = new BeeTest(system, LocalDateTime.of(2020, 12, 1, 2, 25), "Gleiche Beobachtung 1", 1 );
        Bee Bee2 = new BeeTest(system, LocalDateTime.of(2021, 12, 3, 2, 25), "Gleiche Beobachtung 2", 1);
        Bee anotherBee = new WildBeeTest(system, LocalDateTime.of(2025, 12, 1, 2, 25), "Andere Beobachtung 1", false);
        Bee yetAnotherBee = new Honeybee(system, LocalDateTime.of(2025, 12, 1, 2, 25), "Andere Beobachtung 2", anotherBee);
        Bee againAnotherBee = new BeeTest(system, LocalDateTime.of(2025, 12, 1, 2, 25), "Andere Beobachtung 3", yetAnotherBee);
        Bee Bee3 = new BeeTest(system, LocalDateTime.of(2022, 12, 5, 2, 25), "Gleiche Beobachtung 3", 1);
        Bee Bee4 = new BeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Gleiche Beobachtung 4", 1);
        // Test der Iterationsmethoden
            // sameBee() des Interfaces Bee
        System.out.println("--- Teste Bienen: Auffinden aller 4 Beobachtungen von Bienen des gleichen Individuums (mit Chip) ---");
        Iterator<Bee> sameBees = Bee1.sameBee();
        while (sameBees.hasNext()) {
            System.out.println(sameBees.next().getComment());
        }
        System.out.println("\n--- Teste Bienen: Auffinden aller 4 Beobachtungen von Bienen des gleichen Individuums (mit Chip) - Absteigend ---");
        Iterator<Bee> sameBeesDesc = Bee1.sameBee(false);
        while (sameBeesDesc.hasNext()) {
            System.out.println(sameBeesDesc.next().getComment());
        }
        System.out.println("\n--- Teste Bienen: Auffinden aller 3 Beobachtungen von Bienen des gleichen Individuums (mit Verweis auf vorherige Individuen) ---");
        Iterator<Bee> otherBees = anotherBee.sameBee();
        while (otherBees.hasNext()) {
            System.out.println(otherBees.next().getComment());
        }
        System.out.println("\n--- Teste Bienen: Auffinden aller 3 Beobachtungen von Bienen des gleichen Individuums (mit Verweis auf vorherige Individuen) - Absteigend ---");
        Iterator<Bee> otherBeesDesc = anotherBee.sameBee(false);
        while (otherBeesDesc.hasNext()) {
            System.out.println(otherBeesDesc.next().getComment());
        }
        System.out.println("\n--- Teste Bienen: Auffinden aller 7 Beobachtungen des gleichen Individuums (Verweise & Chips gemischt) --- ");
        System.out.println("--- Test mit Subklasse Andrena Bucephala --- ");
        Bee a1 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 1, nichts",  false, false, false);
        Pollinator a2 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 2, zeigt auf 1", a1,false, false, true);
        WildBee a3 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 3, hat Chip", 12, false, true, false);
        SolitaryBee a4 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 4, zeigt auf 1", a1,false, false, true);
        CommunalBee a5 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 5, zeigt auf 4", a4,false, true, false);
        Bee a6= new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 6, zeigt auf 5, hat Chip", 12,a5,false, true, false);
        Observation a7 = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala 7, zeigt auf 6", a6,false, false, true);
        Iterator<Bee> a = a1.sameBee();
        while (a.hasNext()) {
            System.out.println(a.next().getComment());
        }
            // sameBee() mit dynamischem Löschen und Einfügen
        System.out.println("\n--- Teste Bienen: Auffinden Beobachtungen von Bienen des gleichen Individuums - dynamisches Löschen & Einfügen ---");
        System.out.println("- Erstelle sameBee Iterator und lasse 2 Bienen weit laufen -");
        Iterator<Bee> sameBees3 = Bee1.sameBee(true);
        if (sameBees3.hasNext()) {
            System.out.println(sameBees3.next().getComment());
        }
        if (sameBees3.hasNext()) {
            System.out.println(sameBees3.next().getComment());
        }
        System.out.println("- Iterator steht auf dritter Beobachtung -");
        System.out.println("- Lösche Beobachtung 3 und füge neue danach 5. Beobachtung hinzu -");
        Bee3.remove();
        Bee Bee5 = new BeeTest(system, LocalDateTime.of(2026, 12, 1, 2, 25), "Gleiche Beobachtung 5", 1);
        while (sameBees3.hasNext()) {
            System.out.println(sameBees3.next().getComment());
        }
            // sameBee() mit eingeschränktem Zeitfenster
        System.out.println("\n--- Bienen: Auffinden Beobachtungen von Bienen des gleichen Individuums - eingeschränktes Zeitfenster ---");
        System.out.println("- Füge neue Biene 3 hinzu -");
        Bee Bee3_neu = new BeeTest(system, LocalDateTime.of(2021, 12, 3, 2, 25), "Gleiche Beobachtung 3", Bee1);
        System.out.println("- Gebe Iterator Zeitfenster von exakt 2 (sollte inklusive sein) bis kurz nach 3 -");
        Iterator<Bee> sameBeesTimed = Bee1.sameBee(true, LocalDateTime.of(2021, 12, 3, 2, 25),
                LocalDateTime.of(2022, 12, 6, 2, 25));
        while (sameBeesTimed.hasNext()) {
            System.out.println(sameBeesTimed.next().getComment());
        }
            // wild() des Interfaces WildBee
        System.out.println("\n--- Wildbienen: Alle Beobachtungen eines Individuums, aus denen wilde Lebensweise hervorgeht ---");
        System.out.println("--- Erstelle je 3 Beobachtungen der gleichen Wildbiene für wild / nicht wild ---");
        WildBee wb1 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "1, wild", 1, false);
        WildBee wb2 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "2, zucht", 1, true);
        WildBee wb3 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "3, wild", wb1, false);
        WildBee wb_andere = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, zucht", 2, true);
        WildBee wb4 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "4, unbekannt", 1, null);
        WildBee wb5 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "5, zucht", wb4, true);
        WildBee wb6 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "6, zucht", wb5, true);
        WildBee wb_andere2 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, wild", wb_andere, false);
        WildBee wb7 = new WildBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "7, wild", 1, false);
        System.out.println("- Wild: -");
        Iterator<WildBee> wildBeesWild = wb1.wild(false);
        while (wildBeesWild.hasNext()) {
            System.out.println(wildBeesWild.next().getComment());
        }
        System.out.println("- Zucht: -");
        Iterator<WildBee> wildBeesTamed = wb5.wild(true);
        while (wildBeesTamed.hasNext()) {
            System.out.println(wildBeesTamed.next().getComment());
        }
        System.out.println("--- Subklassen ---");
        WildBee bumblebee1W = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Hummel, Beobachtung 1, wild & natürlich sozial", false);
        WildBee other_bumblebeeW = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Andere Hummel, zucht & natürlich sozial", 13, true);
        Bumblebee bumblebee2W = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Hummel, Beobachtung 2, wild & natürlich sozial", bumblebee1W,false);
        WildBee lasio1W = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 1, wild & sozial", false, true, false);
        WildBee lasio2W = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 2, wild & solitär", lasio1W, false, false, true);
        LasioglossumCalceatum lasio3W = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 3, zucht & sozial", lasio2W,true, true, false);
        LasioglossumCalceatum lasio4W = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 4, zucht & solitär", lasio3W,true, false, true);
        WildBee an1W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 1, wild", false, false, false);
        WildBee an2W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 2, zucht & kommunal", an1W,true, false, true);
        AndrenaBucephala an3W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 3, zucht & solitär", 11, an1W,true, true, false);
        AndrenaBucephala an4W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 4, zucht & kommunal", an3W,true, false, true);
        AndrenaBucephala an5W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 5, wild & solitär", 11,false, true, false);
        WildBee an6W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 6, zucht", 11,true, false, false);
        AndrenaBucephala an7W = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 7, zucht", an6W,true, false, false);
        WildBee os1W = new OsmiaCornuta(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Osmia Cornuta, Beobachtung 1, wild & natürlich solitär", 14, false);
        OsmiaCornuta os2W = new OsmiaCornuta(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Osmia Cornuta, Beobachtung 2, wild & natürlich solitär", 14, false);
        System.out.println("- Erstelle je 2 eindeutig wilde Beobachtungen -");
        System.out.println("- Hummeln (sind Wildbienen) -");
        Iterator<WildBee> bumblebeesWild = bumblebee1W.wild(false);
        while (bumblebeesWild.hasNext()) {
            System.out.println(bumblebeesWild.next().getComment());
        }
        System.out.println("- LasioGlossum (sind Wildbienen) -");
        Iterator<WildBee> lasioWild = lasio1W.wild(false);
        while (lasioWild.hasNext()) {
            System.out.println(lasioWild.next().getComment());
        }
        System.out.println("- AndrenaBucephala (sind kommunale, also auch solitäre Bienen, diese gehören alle zu den Wildbienen) -");
        Iterator<WildBee> andrena = an1W.wild(false);
        while (andrena.hasNext()) {
            System.out.println(andrena.next().getComment());
        }
        System.out.println("- Osmia Cornuta (sind solitäre Bienen und gehören damit zu den Wildbienen) -");
        Iterator<WildBee> osmia = os1W.wild(false);
        while (osmia.hasNext()) {
            System.out.println(osmia.next().getComment());
        }
        // social() des Interfaces SocialBee
        System.out.println("\n--- Soziale Bienen: Alle Beobachtungen eines Individuums, aus denen soziale Lebensweise hervorgeht ---");
        System.out.println("--- Erstelle je 3 eindeutig soziale Beobachtungen ---");
        SocialBee sb1 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "1, unsozial", 6, false);
        SocialBee sb2 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "2, sozial", 6, true);
        SocialBee sb3 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "3, unsozial", sb1, false);
        SocialBee sb_andere = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, sozial", 7, true);
        SocialBee sb4 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "4, unsozial", 6, false);
        SocialBee sb5 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "5, sozial", sb4, true);
        SocialBee sb6 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "6, sozial", sb5, true);
        SocialBee sb_andere2 = new SocialBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, wild", sb_andere, false);
        System.out.println("- Sozial: -");
        Iterator<SocialBee> socials = sb1.social();
        while (socials.hasNext()) {
            System.out.println(socials.next().getComment());
        }
        System.out.println("--- Subklassen ---");
        SocialBee bumblebee1S = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Hummel, Beobachtung 1, wild & natürlich sozial", false);
        SocialBee other_bumblebeeS = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Andere Hummel, zucht & natürlich sozial", 23, true);
        Bumblebee bumblebee2S = new Bumblebee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Hummel, Beobachtung 2, wild & natürlich sozial", bumblebee1S,false);
        SocialBee lasio1S = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 1, wild & sozial", false, true, false);
        SocialBee lasio2S = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 2, wild & solitär", lasio1S, false, false, true);
        LasioglossumCalceatum lasio3S = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 3, zucht & sozial", lasio2S,true, true, false);
        LasioglossumCalceatum lasio4S = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 4, zucht & solitär", lasio3S,true, false, true);
        Honeybee honeybee1 = new Honeybee(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Honigbiene, Beobachtung 1, natürlich sozial");
        Honeybee honeybee_other = new Honeybee(system, LocalDateTime.of(2023, 12, 6, 2, 25), "Andere Honigbiene, natürlich sozial");
        Honeybee honeybee2 = new Honeybee(system, LocalDateTime.of(2023, 12, 7, 2, 25), "Honigbiene, Beobachtung 2, natürlich sozial", honeybee1);
        System.out.println("- Erstelle je 2 eindeutig soziale Beobachtungen -");
        System.out.println("- Honigbienen (immer sozial) -");
        Iterator<SocialBee> honeybees = honeybee1.social();
        while (honeybees.hasNext()) {
            System.out.println(honeybees.next().getComment());
        }
        System.out.println("- Hummeln (immer sozial) -");
        Iterator<SocialBee> bumblebees = bumblebee1S.social();
        while (bumblebees.hasNext()) {
            System.out.println(bumblebees.next().getComment());
        }
        System.out.println("- LasioGlossum (meistens sozial) -");
        Iterator<SocialBee> lasioSocial = lasio1S.social();
        while (lasioSocial.hasNext()) {
            System.out.println(lasioSocial.next().getComment());
        }
            // solitary() des Interfaces SolitaryBee
        System.out.println("\n--- Solitäre Bienen: Alle Beobachtungen eines Individuums, aus denen solitäre Lebensweise hervorgeht ---");
        System.out.println("--- Erstelle je 3 eindeutig solitäre Beobachtungen ---");
        SolitaryBee slb1 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "1, unsolitär", false , false);
        SolitaryBee slb2 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "2, solitär", 8, false,  true);
        SolitaryBee slb3 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "3, unsolitär", slb1, false, false);
        SolitaryBee slb_andere = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, solitär", 9,false,  true);
        SolitaryBee slb4 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "4, unsolitär", 8, slb3, false, false);
        SolitaryBee slb5 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "5, solitär", slb4,false,  true);
        SolitaryBee slb6 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "6, solitär", slb5, false, true);
        SolitaryBee slb_andere2 = new SolitaryBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "andere, unsolitär", slb_andere, false, false);
        System.out.println("- Solitär: -");
        Iterator<SolitaryBee> solitaries = slb1.solitary();
        while (solitaries.hasNext()) {
            System.out.println(solitaries.next().getComment());
        }
        System.out.println("--- Subklassen: ---");
        SolitaryBee os1S = new OsmiaCornuta(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Osmia Cornuta, Beobachtung 1, wild & natürlich solitär", 34, false);
        OsmiaCornuta os2S = new OsmiaCornuta(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Osmia Cornuta, Beobachtung 2, wild & natürlich solitär", 34, false);
        SolitaryBee an1S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 1, wild", false, false, false);
        SolitaryBee an2S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 2, zucht & kommunal", an1S,true, false, true);
        AndrenaBucephala an3S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 3, zucht & solitär", 31, an1S,true, true, false);
        AndrenaBucephala an4S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 4, zucht & kommunal", an3S,true, false, true);
        AndrenaBucephala an5S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 5, wild & solitär", 31,false, true, false);
        SolitaryBee an6S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 6, zucht", 31,true, false, false);
        AndrenaBucephala an7S = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 7, zucht", an6S,true, false, false);
        SolitaryBee lasio1St = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 1, wild & sozial", false, true, false);
        SolitaryBee lasio2St = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 2, wild & solitär", lasio1St, false, false, true);
        LasioglossumCalceatum lasio3St = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 3, zucht & sozial", lasio2St,true, true, false);
        LasioglossumCalceatum lasio4St = new LasioglossumCalceatum(system, LocalDateTime.of(2024, 12, 7, 2, 25), "Lasio Glossum, Beobachtung 4, zucht & solitär", lasio3St,true, false, true);
        System.out.println("- Erstelle je 2 eindeutig solitäre Beobachtungen -");
        System.out.println("- Osmia Cornuta (sind solitäre Bienen) -");
        Iterator<SolitaryBee> osmiaSolitary = os1S.solitary();
        while (osmiaSolitary.hasNext()) {
            System.out.println(osmiaSolitary.next().getComment());
        }
        System.out.println("- Andrena Bucephala (Kommunale Bienen, können daher auch solitär) -");
        Iterator<SolitaryBee> andrenaSolitary = an1S.solitary();
        while (andrenaSolitary.hasNext()) {
            System.out.println(andrenaSolitary.next().getComment());
        }
        System.out.println("- LasioGlossum (kann solitär) -");
        Iterator<SolitaryBee> lasioSolitary = lasio1St.solitary();
        while (lasioSolitary.hasNext()) {
            System.out.println(lasioSolitary.next().getComment());
        }
            // communal() des Interfaces CommunalBee
        System.out.println("\n--- Kommunale Bienen: Alle Beobachtungen eines Individuums, aus denen kommunale Lebensweise hervorgeht ---");
        System.out.println("--- Erstelle je 3 eindeutig kommunale Beobachtungen ---");
        CommunalBee cb1 = new CommunalBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "1, kommunal", false, false, true);
        CommunalBee cb2 = new CommunalBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "2, kommunal", cb1,false, false, true);
        CommunalBee cb3 = new CommunalBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "3, nicht kommunal", false, true, false);
        CommunalBee cb4 = new CommunalBeeTest(system, LocalDateTime.of(2023, 12, 5, 2, 25), "4, kommunal", cb2, false, false, true);
        System.out.println("- Kommunal: -");
        Iterator<CommunalBee> communals = cb1.communal();
        while (communals.hasNext()) {
            System.out.println(communals.next().getComment());
        }
        System.out.println("--- Subklassen: ---");
        CommunalBee an1C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 1, wild", false, false, false);
        CommunalBee an2C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 2, zucht & kommunal", an1C,true, false, true);
        CommunalBee an3C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 3, zucht & solitär", 41, an1C,true, true, false);
        CommunalBee an4C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 4, zucht & kommunal", an3C,true, false, true);
        CommunalBee an5C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 5, wild & solitär", 41,false, true, false);
        CommunalBee an6C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 6, zucht", 41,true, false, false);
        CommunalBee an7C = new AndrenaBucephala(system, LocalDateTime.of(2023, 12, 5, 2, 25), "Andrena Bucephala, Beobachtung 7, zucht", an6C,true, false, false);
        System.out.println("- Erstelle je 2 eindeutig kommunale Beobachtungen -");
        System.out.println("- Andrena Bucephala (Leben meist kommunal) -");
        Iterator<CommunalBee> andrenaCommunal = an1C.communal();
        while (andrenaCommunal.hasNext()) {
            System.out.println(andrenaCommunal.next().getComment());
        }

    }
}
