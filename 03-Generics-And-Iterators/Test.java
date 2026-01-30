import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Testklasse
 */
public class Test {

    /**
     * Detaillierte Testausgaben
     *
     * @param args Argumente (wird nicht verwendet)
     */
    public static void main(String[] args) {


        /* 1. Erzeugen Sie mindestens je ein Objekt sinngemäß folgender Typen:
         ISet<Num>
         ISet<Bee>
         ISet<WildBee>
         OSet<Num>
         OSet<Bee>
         OSet<WildBee>
         MSet<Num,Num>
         MSet<WildBee,Integer>
         ISet<HoneyBee>
         OSet<HoneyBee>
         MSet<HoneyBee,String>
         Befüllen Sie die Container mit einigen Einträgen und Ordnungsbeziehungen.
         Um den Schreibaufwand zu reduzieren, verwenden Sie dafür am besten (generische) Methoden,
         die Inhalte einer Collection in eine andere Collection kopieren (über Iteratoren).
         */

        // Implementierung der Checker-Objekte c

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von Num.
        Ordered<Num, Boolean> numOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x kleiner y, andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true, wenn x kleiner y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x kleiner y, andernfalls null.
             */
            @Override
            public Boolean before(Num x, Num y) {
                //Check Vorbedingungen
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                //Check, ob kleiner, dann return true
                if (Integer.parseInt(x.toString()) < Integer.parseInt(y.toString()))
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(Num x, Num y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von Bee.
        Ordered<Bee, Boolean> beeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x zeitlich gesehen vor y, andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x zeitlich gesehen vor y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x zeitlich gesehen vor y, andernfalls null.
             */
            @Override
            public Boolean before(Bee x, Bee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von LocalDateTime-Objekten für x und y auf Basis von toString()
                LocalDateTime fromX = LocalDateTime.parse(x.toString().split("Zeitpunkt ")[1].split(" und")[0]);
                LocalDateTime fromY = LocalDateTime.parse(y.toString().split("Zeitpunkt ")[1].split(" und")[0]);
                // Vergleich der Uhrzeiten und entsprechende Rückgabe
                if (!fromX.isAfter(fromY))
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(Bee x, Bee y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von WildBee.
        Ordered<WildBee, Boolean> wildBeeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x kürzer als y, andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x kürzer als y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x kürzer als y, andernfalls null.
             */
            @Override
            public Boolean before(WildBee x, WildBee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von int für x und y auf Basis von toString()
                int fromX = Integer.parseInt(x.toString().split("Länge ")[1]);
                int fromY = Integer.parseInt(y.toString().split("Länge ")[1]);
                // Vergleich der Größen und entsprechende Rückgabe
                if (fromX < fromY)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(WildBee x, WildBee y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von HoneyBee.
        Ordered<HoneyBee, Boolean> honeyBeeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x alphabetisch vor y steht, andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x alphabetisch vor y steht, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x alphabetisch vor y steht, andernfalls null.
             */
            @Override
            public Boolean before(HoneyBee x, HoneyBee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von Strings für x und y auf Basis von toString()
                String fromX = x.toString().split("Züchtung ")[1];
                String fromY = y.toString().split("Züchtung ")[1];
                // Alphabetischer Vergleich und entsprechende Rückgabe
                if (fromX.compareToIgnoreCase(fromY) < 0)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(HoneyBee x, HoneyBee y) {
            }
        };

        System.out.print("""
                \n======================================
                ===  TEST 1: CONTAINER ERZEUGEN    ===
                ===  UND MIT WERTEN BEFÜLLEN       ===
                ======================================
                """);

        // Erzeugung der Container-Objekte in der Angabe
        ISet<Num> num1 = new ISet<>(numOrdered);
        ISet<Bee> a1 = new ISet<>(beeOrdered);
        ISet<WildBee> wild1 = new ISet<>(wildBeeOrdered);
        ISet<HoneyBee> c2 = new ISet<>(honeyBeeOrdered);
        OSet<Num> num2 = new OSet<>(numOrdered);
        OSet<Bee> a2 = new OSet<>(beeOrdered);
        OSet<WildBee> c1 = new OSet<>(wildBeeOrdered);
        OSet<HoneyBee> hon1 = new OSet<>(honeyBeeOrdered);
        MSet<Num, Num> num3 = new MSet<>(numOrdered);
        MSet<WildBee, Integer> b1 = new MSet<>(wildBeeOrdered);
        MSet<HoneyBee, String> b2 = new MSet<>(honeyBeeOrdered);

        // Befüllen der Container mit einigen Einträgen und Ordnungsbeziehungen

        // Erstellen der Variablen, die später eingefügt werden
        ArrayList<Num> numbers = new ArrayList<>();
        for (int i = 1; i < 100; i += 10) {
            numbers.add(new Num(i));
        }

        ArrayList<Bee> bees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bees.add(new Bee(LocalDateTime.now()));
        }

        ArrayList<WildBee> wildBees = new ArrayList<>();
        for (int i = 1; i < 100; i += 10) {
            wildBees.add(new WildBee(LocalDateTime.now(), i));
        }

        String[] types = {"Andrena Bucephala", "Apis Dorsata", "Bombini", "Centridini", "Eucerini",
                "Isepeolini", "Lasioglossum Calceatum", "Osmia Cornuta", "Rhathymini", "Townsendiellini"};
        ArrayList<HoneyBee> honeyBees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            honeyBees.add(new HoneyBee(LocalDateTime.now(), types[i]));
        }

        // Befüllen Sie die Container mit einigen Einträgen und Ordnungsbeziehungen.
        // Um den Schreibaufwand zu reduzieren, verwenden Sie
        // dafür am besten (generische) Methoden, die Inhalte einer Collection
        // in eine andere Collection kopieren (über Iteratoren).
        // Einfügen in die Container

        for (int i = 0; i < 7; i++) {
            // Numbers
            num1.setBefore(numbers.get(i), numbers.get(i + 1));
            num2.setBefore(numbers.get(i), numbers.get(i + 1));
            num3.setBefore(numbers.get(i), numbers.get(i + 1));

            //Bees
            a1.setBefore(bees.get(i), bees.get(i + 1));
            a1.setBefore(wildBees.get(i), wildBees.get(i + 1));
            a1.setBefore(honeyBees.get(i), honeyBees.get(i + 1));
            a2.setBefore(bees.get(i), bees.get(i + 1));
            a2.setBefore(wildBees.get(i), wildBees.get(i + 1));
            a2.setBefore(honeyBees.get(i), honeyBees.get(i + 1));

            //WildBees
            wild1.setBefore(wildBees.get(i), wildBees.get(i + 1));
            c1.setBefore(wildBees.get(i), wildBees.get(i + 1));
            b1.setBefore(wildBees.get(i), wildBees.get(i + 1));

            //HoneyBees
            hon1.setBefore(honeyBees.get(i), honeyBees.get(i + 1));
            c2.setBefore(honeyBees.get(i), honeyBees.get(i + 1));
            b2.setBefore(honeyBees.get(i), honeyBees.get(i + 1));
        }

        System.out.println("num1:\n" + num1);
        System.out.println("num2:\n" + num2);
        System.out.println("num2:\n" + num3);
        System.out.println("a1:\n" + a1);
        System.out.println("a2:\n" + a2);
        System.out.println("c1:\n" + c1);
        System.out.println("b1:\n" + b1);
        System.out.println("wild1:\n" + wild1);
        System.out.println("hon1:\n" + hon1);
        System.out.println("c2:\n" + c2);
        System.out.println("b2:\n" + b2);

        /* 2. Wählen Sie je ein in Punkt 1 eingeführtes Objekt:
             a1 vom Typ ISet<Bee>
             a2 vom Typ OSet<Bee>
             b1 vom Typ MSet<WildBee, Integer>
             b2 vom Typ MSet<HoneyBee, String>
             c1 vom Typ OSet<WildBee>
             c2 vom Typ ISet<HoneyBee>
             Lesen Sie über iterator alle Einträge aus c1 aus,
             rufen Sie auf jedem Eintrag length() auf,
             ermitteln Sie über before alle Ordnungsbeziehungen zwischen allen Paaren von Einträgen
             fügen Sie die vorhandenen Ordnungsbeziehungen mittels setBefore in sowohl a1 als auch b1 ein.
             Lesen Sie analog dazu über iterator alle Einträge aus c2 aus
             rufen Sie auf jedem Eintrag sort() auf,
             ermitteln Sie über before alle Ordnungsbeziehungen zwischen allen Paaren von Einträgen
             fügen Sie die vorhandenen Ordnungsbeziehungen mittels setBefore in sowohl a2 als auch b2 ein.
         */
        System.out.print("""
                \n======================================
                ===  TEST 2a: ORDNUNGEN AUS c1     ===
                ===  (OSet<WildBee> -> a1, b1)     ===
                ======================================
                """);

        // Tests für c1
        Iterator<WildBee> i1 = c1.iterator();
        // Verwendung von ArrayList zulässig, da in Test
        ArrayList<WildBee> wildBees1 = new ArrayList<>();
        if (i1 != null) {
            System.out.println("Längen: ");
            while (i1.hasNext()) {
                WildBee w = i1.next();
                System.out.println(w.length());
                // Hinzufügen zur Liste für den späteren Check der Ordnungsbeziehungen
                wildBees1.add(w);
            }
            // Finden aller Ordnungsbeziehungen (geht nur durch einen Vergleich jedem mit jedem)
            System.out.println("Ordnungsbeziehungen: ");
            System.out.println("Es werden zusätzlich zu den explizit definierten Ordnungsbeziehungen alle sich" +
                    "aus der Transitivität dieser ergebenden Ordnungsbeziehungen ausgegeben.");
            for (WildBee tmp : wildBees1) {
                for (WildBee tmp2 : wildBees1) {
                    if (c1.before(tmp, tmp2) != null) {
                        System.out.println("C1: Ordnungsbeziehung zwischen \"" + tmp + "\" und \"" + tmp2 + "\" gefunden.");
                        a1.setBefore(tmp, tmp2);
                        b1.setBefore(tmp, tmp2);
                    }
                }
            }
        } else {
            System.out.println("Der Iterator von c1 ist null!");
        }

        System.out.print("""
                \n======================================
                ===  TEST 2b: ORDNUNGEN AUS c2     ===
                ===  (ISet<HoneyBee> -> a2, b2)    ===
                ======================================
                """);
        // Tests für c2
        Iterator<HoneyBee> i2 = c2.iterator();
        // Verwendung von ArrayList zulässig, da in Test
        ArrayList<HoneyBee> honeyBees1 = new ArrayList<>();
        if (i2 != null) {
            System.out.println("Arten: ");
            while (i2.hasNext()) {
                HoneyBee h = i2.next();
                System.out.println(h.sort());
                // Hinzufügen zur Liste für den späteren Check der Ordnungsbeziehungen
                honeyBees1.add(h);
            }
            // Finden aller Ordnungsbeziehungen (geht nur durch einen Vergleich jedem mit jedem)
            System.out.println("Ordnungsbeziehungen: ");
            System.out.println("Es werden zusätzlich zu den explizit definierten Ordnungsbeziehungen alle sich" +
                    "aus der Transitivität dieser ergebenden Ordnungsbeziehungen ausgegeben.");
            for (HoneyBee tmp : honeyBees1) {
                for (HoneyBee tmp2 : honeyBees1) {
                    if (c2.before(tmp, tmp2) != null) {
                        System.out.println("C2: Ordnungsbeziehung zwischen \"" + tmp + "\" und \"" + tmp2 + "\" gefunden.");
                        a2.setBefore(tmp, tmp2);
                        b2.setBefore(tmp, tmp2);
                    }
                }
            }
        } else {
            System.out.println("Der Iterator von c2 ist null!");
        }

        /* 3. Führen Sie in einigen in Punkt 1 eingeführten Objekten check und checkForced aus,
            wobei Argumente andere Typen haben als die Objekte, in denen die Methoden ausgeführt werden.
            Es soll möglich sein, in Objekten vom Typ OSet<Num> Methoden mit Argumenten
            vom Typ ISet<Num> und MSet<Num, Num> (aber nicht OSet<Bee>) aufzurufen, ebenso wie umgekehrt.
            Es soll auch möglich sein, in Objekten der Typen ISet<WildBee> oder OSet<HoneyBee> Methoden
            mit Argumenten der Typen ISet<Bee> oder OSet<Bee> aufzurufen,
            aber nicht umgekehrt (Verwendung von Untertypbeziehungen).
            Zeigen Sie alle möglichen Kombinationen, die funktionieren.
         */
        System.out.print("""
                \n======================================
                ===  TEST 3: CHECK / CHECKFORCED   ===
                ===  TYPKOMBINATIONEN & WILDCARDS  ===
                ======================================
                """);

        // Für ISet<Num>, OSet<Num> und MSet<Num, Num> ist es jeweils gegenseitig möglich
        num1.check(num2);
        num1.checkForced(num2);
        num1.check(num3);
        num1.checkForced(num3);
        num2.check(num1);
        num2.checkForced(num1);
        num2.check(num3);
        num2.checkForced(num3);
        num3.check(num1);
        num3.checkForced(num1);
        num3.check(num2);
        num3.checkForced(num2);
        // aber nicht OSet<Bee>, folgende funktionieren nicht, daher auskommentiert
        //num_1.check(a2);
        //num2.check(a2);
        //num3.check(a2);
        //a2.check(num_1);
        //a2.check(num2);
        //a2.check(num3);

        // Für ISet<Bee> und OSet<Bee> ist es gegenseitig möglich
        a1.check(a2);
        a1.checkForced(a2);
        a2.check(a1);
        a2.checkForced(a1);

        // Für ISet<WildBee>, OSet<WildBee> und MSet<WildBee,Integer> ist es gegenseitig möglich
        // sowie jeweils mit ISet<Bee> und OSet<Bee>

        wild1.check(b1);
        wild1.checkForced(b1);
        wild1.check(c1);
        wild1.checkForced(c1);
        wild1.check(a1);
        wild1.checkForced(a1);
        wild1.check(a2);
        wild1.checkForced(a2);
        b1.check(wild1);
        b1.checkForced(wild1);
        b1.check(c1);
        b1.checkForced(c1);
        b1.check(a1);
        b1.checkForced(a1);
        b1.check(a2);
        b1.checkForced(a2);
        c1.check(wild1);
        c1.checkForced(wild1);
        c1.check(b1);
        c1.checkForced(b1);
        c1.check(a1);
        c1.checkForced(a1);
        c1.check(a2);
        c1.checkForced(a2);

        // aber nicht andersrum: folgende funktionieren nicht, daher auskommentiert
        //a1.check(wild1);
        //a1.check(hon1);
        //a2.check(wild1);
        //a2.check(hon1);
        //a1.checkForced(wild1);
        //a1.checkForced(hon1);
        //a2.checkForced(wild1);
        //a2.checkForced(hon1);

        // Für ISet<HoneyBee>, OSet<HoneyBee> und MSet<HoneyBee,String> ist es gegenseitig möglich
        // sowie jeweils mit ISet<Bee> und OSet<Bee>
        hon1.check(b2);
        hon1.checkForced(b2);
        hon1.check(c2);
        hon1.checkForced(c2);
        hon1.check(a1);
        hon1.checkForced(a1);
        hon1.check(a2);
        hon1.checkForced(a2);
        b2.check(hon1);
        b2.checkForced(hon1);
        b2.check(c2);
        b2.checkForced(c2);
        b2.check(a1);
        b2.checkForced(a1);
        b2.check(a2);
        b2.checkForced(a2);
        c2.check(hon1);
        c2.checkForced(hon1);
        c2.check(b2);
        c2.checkForced(b2);
        c2.check(a1);
        c2.checkForced(a1);
        c2.check(a2);
        c2.checkForced(a2);

        System.out.println("Klappt. Kein Compiler-Fehler.\nDetaillierte Testung Funktionalität s.u.");

        /* 4. Falls zwischen ISet, OSet und MSet (paarweise) Untertypbeziehungen bestehen,
            führen Sie Testfälle aus, die überprüfen, ob entsprechende Zusicherungen erfüllt sind.
            Wenn es zwischen zwei oder drei der Typen keine Untertypbeziehung(en) gibt, begründen Sie,
            warum es keine solche(n) Untertypbeziehung(en) geben kann.
         */

        // OSet und MSet stehen durch Vererbung in Untertypbeziehung zueinander, beide implementieren OrdSet<E, OMSet<E>>.
        // Auch alle anderen öffentlichen Bestandteile sind bzgl. Varianz kompatibel (da sie gleich sind).
        // MSet ist dabei Untertyp von OSet, da MSet "spezifischer" ist, d.h. Elemente werden stärker eingeschränkt.
        // OSet könnte die Garantien von MSet nicht einhalten, da auf seinen Elementen keine Modifiable-Operationen
        // garantiert werden könnten.
        // Dies könnte man als eine Nachbedingung der Methode before(x,y) sehen:
        // OSet: "Objekt, das sowohl den Typ Ordered<E, Boolean>, Modifiable<E, OMSet<E>> hat, andernfalls null."
        // MSet: "Objekt, das sowohl den Typ Ordered<E, Boolean>, Modifiable<E, OMSet<E>> hat, andernfalls null.
        //        UND E (des zurückgegebenen Objekts) implementiert Modifiable"
        // Damit ist die Nachbedingung von MSet stärker, MSet muss also Untertyp von OSet sein, nicht andersrum.
        // Andersherum wäre diese Beziehung auch nicht möglich, da OSet plus() und minus() nicht implementieren kann,
        // da seine Elemente nicht vom Typ Modifiable sind.
        // Für das Ausschließen aller anderen Subtypbeziehungen reicht schon die Sicht auf strukturelle Ersetzbarkeit:
        // Rückgabetypen von Methoden müssten kovariant sein.
        // Es sind keine Beziehungen zwischen ISet sowie MSet bzw. OSet möglich,
        // da ISet OrdSet<E, Iterator<E> implementiert, d.h. before(x,y) einen Iterator<E> zurückgibt.
        // Iterator<E> und OMSet<E> sind nicht kompatibel (keines ist Subtyp vom anderen).
        // Analog zum oben beschriebenen Transfer auf die Nachbedingung von before(x,y), kann hier
        // keine Konstellation gefunden werden, in der die Nachbedingung einer Klasse die Nachbedingung der anderen impliziert.
        // Sie schließen schlichtweg einander aus.
        // Das von uns eingeführte OMSet steht in keiner Beziehung zu einem der anderen Sets:
        // Auch hier kollidieren die Rückgabetypen der Methode before, welche in OMSet einen Boolean zurückgeben soll,
        // In den anderen Klassen aber jeweils andere nicht-kompatible Typen (die in keiner Beziehung zu Boolean
        // stehen, weswegen eine Subtypbeziehung in keine Richtung möglich ist).

        System.out.print("""
                \n======================================
                ===   TESTS UNTERTYPBEZIEHIUNGEN   ===
                ======================================
                """);

        System.out.println("\n--- MSet ist Subtyp von OSet---");
        System.out.println("""
                MSet muss überall verwendbar sein, wo OSet verwendbar ist (Ersetzbarkeit).
                Erstelle 2 Variablen vom Typ OSet, weise je Instanz von OSet und MSet zu.
                Teste nun alle Methoden von OSet.
                Sowohl OSet als auch MSet sollten gleiche (bzw. konsistente Ergebnisse entsprechende Ersetzbarkeit) liefern.
                - Ergebnistypen sollten kovariant sein.
                - Parametertypen kontravariant (bzw. in Java ohnehin invariant).
                - In MSet sollten die gleichen oder weniger Exception geworfen werden, an den gleichen Stellen.
                - Zusicherungen sollten entsprechend ihres Typen kovariant/kontravariant/invariant sein.
                Strukturelle Ersetzbarkeit wird ohnehin durch den Compiler festgestellt.
                Bzgl. nominaler Ersetzbarkeit vergleichen wir Namen, Beschreibungen & Zusicherungen der Methoden.
                Da in MSet alle Methoden direkt durch Vererbung aus OSet übernommen und nicht abgeändert wurden,
                haben wir hier Invarianz, welche der Ersetzbarkeit nicht im Weg steht.
                Wir testen nun die Verwendung von MSet als OSet, um eventuelle Fehler in der Ersetzbarkeit aufzudecken:
                """);

        Num num_1 = new Num(1);
        Num num_2 = new Num(2);
        Num num_3 = new Num(3);
        Num num_4 = new Num(4);
        Num num_5 = new Num(5);

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von Num.
        Ordered<Num, Boolean> numCheck = new Ordered<>() {

            /**
             * Spezifiziert für Testzwecke folgende Ordnungsbeziehung: 1 -> 2 -> 3 -> 4 -> 1
             * Diese erzeugt einen Zyklus, das ist aber für die Testung so vorgesehen (s.u.)
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true, wenn x & y der Ordnungsbeziehung entsprechen.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x kleiner y, andernfalls null.
             */
            @Override
            public Boolean before(Num x, Num y) {
                //Check Vorbedingungen
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                //Check, ob kleiner, dann return true
                if (x == num_1 && y == num_2 || x == num_2 && y == num_3 || x == num_3 && y == num_4 || x == num_4 && y == num_1)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(Num x, Num y) {
            }
        };

        OSet<Num> numO = new OSet<>(numCheck);
        MSet<Num, Num> numM = new MSet<>(numCheck);
        OSet<Num> numMAsO = numO; // Test, ob numO korrekt zuweisbar.
        numMAsO = numM; // kompiliert nur, wenn "MSet<...> extends OSet<E>" korrekt ist

        System.out.println("--- Teste setBefore(x,y) ---");
        System.out.println("MSet sollte die gleichen (oder weniger) Exceptions werfen.");
        System.out.println("OSet:");
        numO.setBefore(num_1, num_2);
        numO.setBefore(num_2, num_3);
        numO.setBefore(num_3, num_4);
        try {
            numO.setBefore(num_1, num_1);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        try {
            numO.setBefore(num_4, num_1);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        try {
            numO.setBefore(num_4, num_5);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        System.out.println(numO);

        System.out.println("MSet:");
        numMAsO.setBefore(num_1, num_2);
        numMAsO.setBefore(num_2, num_3);
        numMAsO.setBefore(num_3, num_4);
        try {
            numMAsO.setBefore(num_1, num_1);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        try {
            numMAsO.setBefore(num_4, num_1);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        try {
            numMAsO.setBefore(num_4, num_5);
        } catch (IllegalArgumentException e) {
            System.out.println("Korrekt erkannt: " + e.getMessage());
        }
        System.out.println(numMAsO);

        System.out.println("\n--- before(x,y) ---");
        System.out.println("MSet sollte Untertyp (oder gleichen Typ) wie OSet als Ergebnis liefern.");

        System.out.println("OSet:");
        System.out.println("Ist 1 vor 2? (Ordnungsbeziehung durch setBefore) " + (numO.before(num_1, num_2)!=null));
        System.out.println("Ist 1 vor 3? (Transitivität) " + (numO.before(num_1, num_3)!=null));
        System.out.println("Ist 3 vor 2? (Gilt nicht) " + (numO.before(num_3, num_2)!=null));
        System.out.println("Erzeugt OMSet (Modifiable & Ordered) mit Einträgen zwischen 1 und 4:");
        OMSet<Num> omSet = numO.before(num_1, num_4);
        System.out.println(omSet);

        System.out.println("MSet:");
        System.out.println("Ist 1 vor 2? (Ordnungsbeziehung durch setBefore) " + (numMAsO.before(num_1, num_2)!=null));
        System.out.println("Ist 1 vor 3? (Transitivität) " + (numMAsO.before(num_1, num_3)!=null));
        System.out.println("Ist 3 vor 2? (Gilt nicht) " + (numMAsO.before(num_3, num_2)!=null));
        System.out.println("Erzeugt OMSet (Modifiable & Ordered) mit Einträgen zwischen 1 und 4:");
        OMSet<Num> omSet2 = numMAsO.before(num_1, num_4);
        System.out.println(omSet2);

        System.out.println("\n--- Teste check(c) ---\nCheck-Objekt:");
        ISet<Num> cSet = new ISet<>(null);
        cSet.setBefore(num_1, num_2);
        cSet.setBefore(num_2, num_3);
        cSet.setBefore(num_4, num_3);
        System.out.println(cSet + "\n");
        System.out.println("OSet:");
        try {
            numO.check(cSet);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        System.out.println("MSet:");
        try {
            numMAsO.check(cSet);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }

        System.out.println("\n--- Teste checkForced(c) ---");
        System.out.println("OSet:");
        numO.checkForced(cSet);
        System.out.println(numO);
        System.out.println("MSet:");
        numMAsO.checkForced(cSet);
        System.out.println(numMAsO);

        System.out.println("\n--- Teste iterator() ---");
        System.out.println("OSet:");
        Iterator<Num> itO = numO.iterator();
        if (itO.hasNext()) {
            itO.next();
            try {
                itO.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (itO.hasNext()) {
            System.out.println(itO.next().toString());
        }
        System.out.println("MSet:");
        Iterator<Num> itM = numMAsO.iterator();
        if (itM.hasNext()) {
            itM.next();
            try {
                itM.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (itM.hasNext()) {
            System.out.println(itM.next().toString());
        }

        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des OSets: " + numO.size());
        System.out.println("Größe des MSets: " + numMAsO.size());

        System.out.println("""
                
                Gleiche konsistente Verhaltensweisen zeigen sich auch für andere Typargumente,
                also z.B. Wildbee oder Honeybee.
                Siehe Tests der Funktionalitäten. (Würde sich sonst hier doppeln)""");


        System.out.print("""
                \n======================================
                ===     TESTS FUNKTIONALITÄTEN     ===
                ======================================
                """);
        Num zehn = new Num(10);
        Num zwanzig = new Num(20);
        Num dreissig = new Num(30);
        Num vierzig = new Num(40);
        Num fuenfzig = new Num(50);

        Bee bee1 = new Bee(LocalDateTime.of(2020, 1, 1, 1, 15));
        Bee bee2 = new Bee(LocalDateTime.of(2020, 2, 1, 1, 15));
        Bee bee3 = new Bee(LocalDateTime.of(2020, 3, 1, 1, 15));
        Bee bee4 = new Bee(LocalDateTime.of(2020, 4, 1, 1, 15));
        WildBee wildBee1 = new WildBee(LocalDateTime.of(2020, 1, 1, 1, 15), 10);
        WildBee wildBee2 = new WildBee(LocalDateTime.of(2020, 2, 1, 1, 15), 20);
        WildBee wildBee3 = new WildBee(LocalDateTime.of(2020, 3, 1, 1, 15), 30);
        WildBee wildBee4 = new WildBee(LocalDateTime.of(2020, 4, 1, 1, 15), 40);
        HoneyBee honeyBee1 = new HoneyBee(LocalDateTime.of(2020, 1, 1, 1, 15), "a");
        HoneyBee honeyBee2 = new HoneyBee(LocalDateTime.of(2020, 2, 1, 1, 15), "b");
        HoneyBee honeyBee3 = new HoneyBee(LocalDateTime.of(2020, 3, 1, 1, 15), "c");
        HoneyBee honeyBee4 = new HoneyBee(LocalDateTime.of(2020, 4, 1, 1, 15), "d");

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von Num.
        numOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x kleiner y oder x=50 und y=40 (für Zyklus-Test), andernfalls null<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true, wenn x kleiner y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x kleiner y, andernfalls null.
             */
            @Override
            public Boolean before(Num x, Num y) {
                //Check Vorbedingungen
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                //Check, ob kleiner, dann return true
                if (Integer.parseInt(x.toString()) < Integer.parseInt(y.toString()) || x == fuenfzig && y == vierzig)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(Num x, Num y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von Bee.
        beeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x zeitlich gesehen vor y x=bee2 und y=bee1 (für Zyklus-Test), andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x zeitlich gesehen vor y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x zeitlich gesehen vor y, andernfalls null.
             */
            @Override
            public Boolean before(Bee x, Bee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von LocalDateTime-Objekten für x und y auf Basis von toString()
                LocalDateTime fromX = LocalDateTime.parse(x.toString().split("Zeitpunkt ")[1].split(" und")[0]);
                LocalDateTime fromY = LocalDateTime.parse(y.toString().split("Zeitpunkt ")[1].split(" und")[0]);
                // Vergleich der Uhrzeiten und entsprechende Rückgabe
                if (!fromX.isAfter(fromY) || x == bee2 && y == bee1)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(Bee x, Bee y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von WildBee.
        wildBeeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x kürzer als y x=wildBee2 und y=wildBee2 (für Zyklus-Test), andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x kürzer als y, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x kürzer als y, andernfalls null.
             */
            @Override
            public Boolean before(WildBee x, WildBee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von int für x und y auf Basis von toString()
                int fromX = Integer.parseInt(x.toString().split("Länge ")[1]);
                int fromY = Integer.parseInt(y.toString().split("Länge ")[1]);
                // Vergleich der Größen und entsprechende Rückgabe
                if (fromX < fromY  || x == wildBee2 && y == wildBee1)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(WildBee x, WildBee y) {
            }
        };

        // Diese anonyme innere Klasse dient der Überprüfung der Ordnung von HoneyBee.
        honeyBeeOrdered = new Ordered<>() {

            /**
             * Gibt true zurück, wenn x alphabetisch vor y steht x=honeyBee2 und y=honeyBee1 (für Zyklus-Test), andernfalls null.<br>
             * Vorbedingung: x und y not null.<br>
             * Nachbedingung: x und y bleiben unverändert und das Ergebnis ist true,
             * wenn x alphabetisch vor y steht, andernfalls null.
             * @param x Der erste Wert
             * @param y Der zweite Wert
             * @return Das Ergebnis ist true, wenn x alphabetisch vor y steht, andernfalls null.
             */
            @Override
            public Boolean before(HoneyBee x, HoneyBee y) {
                if (x == null || y == null)
                    throw new IllegalArgumentException("x und/oder y dürfen nicht null sein!");
                // Erstellen von Strings für x und y auf Basis von toString()
                String fromX = x.toString().split("Züchtung ")[1];
                String fromY = y.toString().split("Züchtung ")[1];
                // Alphabetischer Vergleich und entsprechende Rückgabe
                if (fromX.compareToIgnoreCase(fromY) < 0 || x == honeyBee2 && y == honeyBee1)
                    return true;
                return null;
            }

            /**
             * Diese Methode wird nicht benötigt und ist daher nicht implementiert.
             */
            @Override
            public void setBefore(HoneyBee x, HoneyBee y) {
            }
        };

        ISet<Num> checkSet = new ISet<>(null);
        checkSet.setBefore(zehn, zwanzig);
        checkSet.setBefore(dreissig, zwanzig);
        checkSet.setBefore(vierzig, fuenfzig);

        // =================== I SET =====================

        System.out.print("""
                \n---------------------
                ---     I SET     ---
                ---------------------
                """);


        System.out.println("mit natürlicher Zahlen-Ordnung (aufsteigend)\n");
        System.out.println("=== Mit Num ===\n");
        ISet<Num> iSet = new ISet<>(numOrdered);

        System.out.println("--- Test setBefore(x,y) ---");
        iSet.setBefore(zehn, zwanzig);
        iSet.setBefore(zwanzig, dreissig);
        iSet.setBefore(dreissig, vierzig);
        iSet.setBefore(vierzig, fuenfzig);
        try {
            iSet.setBefore(zwanzig, zehn);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet.setBefore(zwanzig, zwanzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet.setBefore(fuenfzig, vierzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n"+ iSet);

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("Ist 10 vor 20? (Ordnungsbeziehung durch setBefore) " + (iSet.before(zehn, zwanzig)!=null));
        System.out.println("Ist 10 vor 40? (Transitivität) " + (iSet.before(zehn, vierzig)!=null));
        System.out.println("Ist 40 vor 10? (Gilt nicht) " + (iSet.before(vierzig, zehn)!=null));
        System.out.println("Elemente nach 10 und vor 50?");
        Iterator<Num> it = iSet.before(zehn, fuenfzig);
        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }

        System.out.println("\n--- Teste check(c) ---\nCheck-Objekt:");
        System.out.println(checkSet);
        try {
            iSet.check(checkSet);
        } catch (IllegalArgumentException e) {
            System.out.println("\nNicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        System.out.println("\n--- Teste checkForced(c) ---");
        iSet.checkForced(checkSet);
        System.out.println(iSet);

        System.out.println("\n--- Teste iterator() ---");
        it = iSet.iterator();
        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Sets: " + iSet.size());

        System.out.println("\n=== Mit Bee, WildBee, HoneyBee ===");

        ISet<Bee> iSet_Bee = new ISet<>(null);
        ISet<WildBee> iSet_WildBee = new ISet<>(wildBeeOrdered);
        ISet<HoneyBee> iSet_HoneyBee = new ISet<>(honeyBeeOrdered);

        System.out.println("\n--- Test setBefore(x,y) ---");
        System.out.println("=Bee=");
        iSet_Bee.setBefore(bee1, bee2);
        iSet_Bee.setBefore(bee2, bee3);
        iSet_Bee.setBefore(bee3, bee4);
        iSet_Bee.setBefore(wildBee1, wildBee2);
        iSet_Bee.setBefore(wildBee2, wildBee3);
        iSet_Bee.setBefore(wildBee3, wildBee4);
        iSet_Bee.setBefore(honeyBee1, honeyBee2);
        iSet_Bee.setBefore(honeyBee2, honeyBee3);
        iSet_Bee.setBefore(honeyBee3, honeyBee4);
        iSet_Bee.setBefore(wildBee1, bee2);
        iSet_Bee.setBefore(bee2, wildBee3);
        iSet_Bee.setBefore(bee1, honeyBee3);
        iSet_Bee.setBefore(honeyBee3, bee3);
        try {
            iSet_Bee.setBefore(bee1, bee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_Bee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_Bee.setBefore(wildBee2, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=WildBee=");
        iSet_WildBee.setBefore(wildBee1, wildBee2);
        iSet_WildBee.setBefore(wildBee2, wildBee3);
        iSet_WildBee.setBefore(wildBee3, wildBee4);
        try {
            iSet_WildBee.setBefore(wildBee1, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_WildBee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_WildBee.setBefore(wildBee2, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=HoneyBee=");
        iSet_HoneyBee.setBefore(honeyBee1, honeyBee2);
        iSet_HoneyBee.setBefore(honeyBee2, honeyBee3);
        iSet_HoneyBee.setBefore(honeyBee3, honeyBee4);
        try {
            iSet_HoneyBee.setBefore(honeyBee1, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_HoneyBee.setBefore(honeyBee4, honeyBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            iSet_HoneyBee.setBefore(honeyBee2, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("=Bee=");
        System.out.println("Ist Biene 1 vor Biene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_Bee.before(bee1, bee2)!=null));
        System.out.println("Ist Biene 1 vor Biene 4? (Transitivität) " + (iSet_Bee.before(bee1, bee4)!=null));
        System.out.println("Ist Biene 4 vor 1? (Gilt nicht) " + (iSet_Bee.before(bee4, bee1)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_Bee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (iSet_Bee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (iSet_Bee.before(wildBee4, wildBee1)!=null));
        System.out.println("Bienen nach 1 vor 4?");
        Iterator<Bee> itBee = iSet_Bee.before(bee1, bee4);
        while (itBee.hasNext()) {
            System.out.println(itBee.next().toString());
        }
        System.out.println("Wildbienen nach 1 vor 4?");
        Iterator<Bee> itWildBeeBee = iSet_Bee.before(wildBee1, wildBee4);
        while (itWildBeeBee.hasNext()) {
            System.out.println(itWildBeeBee.next().toString());
        }
        System.out.println("Honigbienen nach 1 vor 4?");
        Iterator<Bee> itHoneyBeeBee = iSet_Bee.before(honeyBee1, honeyBee4);
        while (itHoneyBeeBee.hasNext()) {
            System.out.println(itHoneyBeeBee.next().toString());
        }
        System.out.println("=WildBee=");
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_WildBee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (iSet_WildBee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (iSet_WildBee.before(wildBee4, wildBee1)!=null));
        System.out.println("Wildbienen nach 1 vor 4?");
        Iterator<WildBee> itWildBee = iSet_WildBee.before(wildBee1, wildBee4);
        while (itWildBee.hasNext()) {
            System.out.println(itWildBee.next().toString());
        }
        System.out.println("=HoneyBee=");
        System.out.println("Ist Honigbiene 1 vor Honigbiene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_HoneyBee.before(honeyBee1, honeyBee2)!=null));
        System.out.println("Ist Honigbiene 1 vor Honigbiene 4? (Transitivität) " + (iSet_HoneyBee.before(honeyBee1, honeyBee4)!=null));
        System.out.println("Ist Honigbiene 4 vor 1? (Gilt nicht) " + (iSet_HoneyBee.before(honeyBee4, honeyBee1)!=null));
        System.out.println("Honigbienen nach 1 vor 4?");
        Iterator<HoneyBee> itHoneyBee = iSet_HoneyBee.before(honeyBee1, honeyBee4);
        while (itHoneyBee.hasNext()) {
            System.out.println(itHoneyBee.next().toString());
        }

        System.out.println("\n--- Teste check(c) & checkForced(c) ---\nCheck-Objekt:");
        ISet<Bee> checkSetBee = new ISet<>(null);
        checkSetBee.setBefore(bee1, bee2);
        checkSetBee.setBefore(bee3, bee4);
        checkSetBee.setBefore(wildBee2, wildBee3);
        checkSetBee.setBefore(wildBee3, wildBee4);
        checkSetBee.setBefore(honeyBee2, honeyBee3);
        checkSetBee.setBefore(honeyBee3, honeyBee4);
        checkSetBee.setBefore(wildBee1, bee2);
        checkSetBee.setBefore(bee2, wildBee3);
        checkSetBee.setBefore(bee1, honeyBee3);
        checkSetBee.setBefore(honeyBee3, bee3);
        System.out.println(checkSetBee);
        System.out.println("=Bee=");
        try {
            iSet_Bee.check(checkSetBee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        iSet_Bee.checkForced(checkSetBee);
        System.out.println(iSet_Bee);
        System.out.println("=WildBee=");
        try {
            iSet_WildBee.check(iSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        iSet_WildBee.checkForced(iSet_Bee);
        System.out.println(iSet_WildBee);
        System.out.println("=HoneyBee=");
        try {
            iSet_HoneyBee.check(iSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        iSet_HoneyBee.checkForced(iSet_Bee);
        System.out.println(iSet_HoneyBee);

        System.out.println("\n--- Teste iterator() ---");
        System.out.println("=Bee=");
        itBee = iSet_Bee.iterator();
        while (itBee.hasNext()) {
            System.out.println(itBee.next().toString());
        }
        System.out.println("=HoneyBee=");
        itHoneyBee = iSet_HoneyBee.iterator();
        while (itHoneyBee.hasNext()) {
            System.out.println(itHoneyBee.next().toString());
        }
        System.out.println("=WildBee=");
        itWildBee = iSet_WildBee.iterator();
        while (itWildBee.hasNext()) {
            System.out.println(itWildBee.next().toString());
        }

        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Bienen-Sets: " + iSet_Bee.size());
        System.out.println("Größe des Wildbienen-Sets: " + iSet_WildBee.size());
        System.out.println("Größe des Honigbienen-Sets: " + iSet_HoneyBee.size());

        // =================== O SET =====================

        System.out.print("""
                \n---------------------
                ---     O SET     ---
                ---------------------
                """);
        System.out.println("mit natürlicher Zahlen-Ordnung (aufsteigend)\n");
        OSet<Num> oSet = new OSet<>(numOrdered);

        System.out.println("--- Test setBefore(x,y) ---");
        oSet.setBefore(zehn, zwanzig);
        oSet.setBefore(zwanzig, dreissig);
        oSet.setBefore(dreissig, vierzig);
        oSet.setBefore(vierzig, fuenfzig);
        try {
            oSet.setBefore(zwanzig, zehn);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet.setBefore(zwanzig, zwanzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet.setBefore(fuenfzig, vierzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n"+ oSet);

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("Ist 10 vor 20? (Ordnungsbeziehung durch setBefore) " + (oSet.before(zehn, zwanzig)!=null));
        System.out.println("Ist 10 vor 40? (Transitivität) " + (oSet.before(zehn, vierzig)!=null));
        System.out.println("Ist 40 vor 10? (Gilt nicht) " + (oSet.before(vierzig, zehn)!=null));
        System.out.println("Erzeugt OMSet (Modifiable & Ordered) mit Einträgen zwischen 10 und 50:");
        OMSet<Num> om_Set = oSet.before(zehn, fuenfzig);
        System.out.println(omSet + "\nWird weiter unten getestet.");


        System.out.println("\n--- Teste check(c) ---\nCheck-Objekt:");
        System.out.println(checkSet);
        try {
            oSet.check(checkSet);
        } catch (IllegalArgumentException e) {
            System.out.println("\nNicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        System.out.println("\n--- Teste checkForced(c) ---");
        oSet.checkForced(checkSet);
        System.out.println(oSet);

        System.out.println("\n--- Teste iterator() ---");
        it = oSet.iterator();
        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }
        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Sets: " + oSet.size());

        System.out.println("\n=== Mit Bee, WildBee, HoneyBee ===");
        OSet<Bee> oSet_Bee = new OSet<>(null);
        OSet<WildBee> oSet_WildBee = new OSet<>(wildBeeOrdered);
        OSet<HoneyBee> oSet_HoneyBee = new OSet<>(honeyBeeOrdered);

        System.out.println("\n--- Test setBefore(x,y) ---");
        System.out.println("=Bee=");
        oSet_Bee.setBefore(bee1, bee2);
        oSet_Bee.setBefore(bee2, bee3);
        oSet_Bee.setBefore(bee3, bee4);
        oSet_Bee.setBefore(wildBee1, wildBee2);
        oSet_Bee.setBefore(wildBee2, wildBee3);
        oSet_Bee.setBefore(wildBee3, wildBee4);
        oSet_Bee.setBefore(honeyBee1, honeyBee2);
        oSet_Bee.setBefore(honeyBee2, honeyBee3);
        oSet_Bee.setBefore(honeyBee3, honeyBee4);
        oSet_Bee.setBefore(wildBee1, bee2);
        oSet_Bee.setBefore(bee2, wildBee3);
        oSet_Bee.setBefore(bee1, honeyBee3);
        oSet_Bee.setBefore(honeyBee3, bee3);
        try {
            oSet_Bee.setBefore(bee1, bee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_Bee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_Bee.setBefore(wildBee2, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=WildBee=");
        oSet_WildBee.setBefore(wildBee1, wildBee2);
        oSet_WildBee.setBefore(wildBee2, wildBee3);
        oSet_WildBee.setBefore(wildBee3, wildBee4);
        try {
            oSet_WildBee.setBefore(wildBee1, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_WildBee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_WildBee.setBefore(wildBee2, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=HoneyBee=");
        oSet_HoneyBee.setBefore(honeyBee1, honeyBee2);
        oSet_HoneyBee.setBefore(honeyBee2, honeyBee3);
        oSet_HoneyBee.setBefore(honeyBee3, honeyBee4);
        try {
            oSet_HoneyBee.setBefore(honeyBee1, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_HoneyBee.setBefore(honeyBee4, honeyBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            oSet_HoneyBee.setBefore(honeyBee2, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("=Bee=");
        System.out.println("Ist Biene 1 vor Biene 2? (Ordnungsbeziehung durch setBefore) " + (oSet_Bee.before(bee1, bee2)!=null));
        System.out.println("Ist Biene 1 vor Biene 4? (Transitivität) " + (oSet_Bee.before(bee1, bee4)!=null));
        System.out.println("Ist Biene 4 vor 1? (Gilt nicht) " + (oSet_Bee.before(bee4, bee1)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (oSet_Bee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (oSet_Bee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (oSet_Bee.before(wildBee4, wildBee1)!=null));
        System.out.println("Bienen nach 1 vor 4?");
        OMSet<Bee> omBee = oSet_Bee.before(bee1, bee4);
        System.out.println(omBee);
        System.out.println("Wildbienen nach 1 vor 4?");
        OMSet<Bee> omWildBeeBee = oSet_Bee.before(wildBee1, wildBee4);
        System.out.println(omWildBeeBee);
        System.out.println("Honigbienen nach 1 vor 4?");
        OMSet<Bee> omHoneyBeeBee = oSet_Bee.before(honeyBee1, honeyBee4);
        System.out.println(omHoneyBeeBee);
        System.out.println("=WildBee=");
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (oSet_WildBee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (oSet_WildBee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (oSet_WildBee.before(wildBee4, wildBee1)!=null));
        System.out.println("Wildbienen nach 1 vor 4?");
        OMSet<WildBee> omWildBee = oSet_WildBee.before(wildBee1, wildBee4);
        System.out.println(omWildBee);
        System.out.println("=HoneyBee=");
        System.out.println("Ist Honigbiene 1 vor Honigbiene 2? (Ordnungsbeziehung durch setBefore) " + (oSet_HoneyBee.before(honeyBee1, honeyBee2)!=null));
        System.out.println("Ist Honigbiene 1 vor Honigbiene 4? (Transitivität) " + (oSet_HoneyBee.before(honeyBee1, honeyBee4)!=null));
        System.out.println("Ist Honigbiene 4 vor 1? (Gilt nicht) " + (oSet_HoneyBee.before(honeyBee4, honeyBee1)!=null));
        System.out.println("Honigbienen nach 1 vor 4?");
        OMSet<HoneyBee> omHoneyBee = oSet_HoneyBee.before(honeyBee1, honeyBee4);
        System.out.println(omHoneyBee);

        System.out.println("\n--- Teste check(c) & checkForced(c) ---\nCheck-Objekt:");
        System.out.println(checkSetBee);
        System.out.println("=Bee=");
        try {
            oSet_Bee.check(checkSetBee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        oSet_Bee.checkForced(checkSetBee);
        System.out.println(oSet_Bee);
        System.out.println("=WildBee=");
        try {
            oSet_WildBee.check(oSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        iSet_WildBee.checkForced(oSet_Bee);
        System.out.println(oSet_WildBee);
        System.out.println("=HoneyBee=");
        try {
            oSet_HoneyBee.check(oSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        oSet_HoneyBee.checkForced(oSet_Bee);
        System.out.println(oSet_HoneyBee);

        System.out.println("\n--- Teste iterator() ---");
        System.out.println("=Bee=");
        itBee = oSet_Bee.iterator();
        while (itBee.hasNext()) {
            System.out.println(itBee.next().toString());
        }
        System.out.println("=HoneyBee=");
        itHoneyBee = oSet_HoneyBee.iterator();
        while (itHoneyBee.hasNext()) {
            System.out.println(itHoneyBee.next().toString());
        }
        System.out.println("=WildBee=");
        itWildBee = oSet_WildBee.iterator();
        while (itWildBee.hasNext()) {
            System.out.println(itWildBee.next().toString());
        }

        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Bienen-Sets: " + oSet_Bee.size());
        System.out.println("Größe des Wildbienen-Sets: " + oSet_WildBee.size());
        System.out.println("Größe des Honigbienen-Sets: " + oSet_HoneyBee.size());

        // =================== M SET =====================

        System.out.print("""
                \n---------------------
                ---     M SET     ---
                ---------------------
                """);
        System.out.println("mit natürlicher Zahlen-Ordnung (aufsteigend)\n");
        MSet<Num, Num> mSet = new MSet<>(numOrdered);

        System.out.println("--- Test setBefore(x,y) ---");
        mSet.setBefore(zehn, zwanzig);
        mSet.setBefore(zwanzig, dreissig);
        mSet.setBefore(dreissig, vierzig);
        mSet.setBefore(vierzig, fuenfzig);
        try {
            mSet.setBefore(zwanzig, zehn);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet.setBefore(zwanzig, zwanzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet.setBefore(fuenfzig, vierzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n"+ mSet);

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("Ist 10 vor 20? (Ordnungsbeziehung durch setBefore) " + (mSet.before(zehn, zwanzig)!=null));
        System.out.println("Ist 10 vor 40? (Transitivität) " + (mSet.before(zehn, vierzig)!=null));
        System.out.println("Ist 40 vor 10? (Gilt nicht) " + (mSet.before(vierzig, zehn)!=null));
        System.out.println("Erzeugt OMSet (Modifiable & Ordered) mit Einträgen zwischen 10 und 50:");
        OMSet<Num> omMSet = mSet.before(zehn, fuenfzig);
        System.out.println(omMSet + "\nWird weiter unten getestet.");


        System.out.println("\n--- Teste check(c) ---\nCheck-Objekt:");
        System.out.println(checkSet);
        try {
            mSet.check(checkSet);
        } catch (IllegalArgumentException e) {
            System.out.println("\nNicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        System.out.println("\n--- Teste checkForced(c) ---");
        mSet.checkForced(checkSet);
        System.out.println(oSet);

        System.out.println("\n--- Teste iterator() ---");
        it = mSet.iterator();
        if (it.hasNext()) {
            it.next();
            try {
                it.remove();
                System.out.println("FEHLER: remove() hätte nicht funktionieren dürfen!");
            } catch (UnsupportedOperationException e) {
                System.out.println("remove() korrekt nicht implementiert: " + e);
            }
        }
        while (it.hasNext()) {
            System.out.println(it.next().toString());
        }

        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Sets: " + mSet.size());

        mSet.check(null);
        System.out.println("\n--- Teste plus() ---");
        mSet.plus(new Num(5));
        System.out.println(mSet);

        System.out.println("\n--- Teste minus() ---");
        mSet.minus(new Num(3));
        System.out.println(mSet);

        System.out.println("\n=== Mit WildBee, HoneyBee ===");
        MSet<WildBee, Integer> mSet_WildBee = new MSet<>(wildBeeOrdered);
        MSet<HoneyBee, String> mSet_HoneyBee = new MSet<>(honeyBeeOrdered);

        System.out.println("\n--- Test setBefore(x,y) ---");
        System.out.println("=WildBee=");
        mSet_WildBee.setBefore(wildBee1, wildBee2);
        mSet_WildBee.setBefore(wildBee2, wildBee3);
        mSet_WildBee.setBefore(wildBee3, wildBee4);
        try {
            mSet_WildBee.setBefore(wildBee1, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet_WildBee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet_WildBee.setBefore(wildBee2, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=HoneyBee=");
        mSet_HoneyBee.setBefore(honeyBee1, honeyBee2);
        mSet_HoneyBee.setBefore(honeyBee2, honeyBee3);
        mSet_HoneyBee.setBefore(honeyBee3, honeyBee4);
        try {
            mSet_HoneyBee.setBefore(honeyBee1, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet_HoneyBee.setBefore(honeyBee4, honeyBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht erlaubte Ordnungsbeziehung korrekt erkannt: " + e.getMessage());
        }
        try {
            mSet_HoneyBee.setBefore(honeyBee2, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n--- Test before(x,y) ---");

        System.out.println("=WildBee=");
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_WildBee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (iSet_WildBee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (iSet_WildBee.before(wildBee4, wildBee1)!=null));
        System.out.println("Wildbienen nach 1 vor 4?");
        omWildBee = mSet_WildBee.before(wildBee1, wildBee4);
        System.out.println(omWildBee);
        System.out.println("=HoneyBee=");
        System.out.println("Ist Honigbiene 1 vor Honigbiene 2? (Ordnungsbeziehung durch setBefore) " + (iSet_HoneyBee.before(honeyBee1, honeyBee2)!=null));
        System.out.println("Ist Honigbiene 1 vor Honigbiene 4? (Transitivität) " + (iSet_HoneyBee.before(honeyBee1, honeyBee4)!=null));
        System.out.println("Ist Honigbiene 4 vor 1? (Gilt nicht) " + (iSet_HoneyBee.before(honeyBee4, honeyBee1)!=null));
        System.out.println("Honigbienen nach 1 vor 4?");
        omHoneyBee = mSet_HoneyBee.before(honeyBee1, honeyBee4);
        System.out.println(omHoneyBee);

        System.out.println("\n--- Teste check(c) & checkForced(c) ---\nCheck-Objekt:");
        System.out.println("=WildBee=");
        try {
            mSet_WildBee.check(oSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        mSet_WildBee.checkForced(oSet_Bee);
        System.out.println(oSet_WildBee);
        System.out.println("=HoneyBee=");
        try {
            mSet_HoneyBee.check(oSet_Bee);
        } catch (IllegalArgumentException e) {
            System.out.println("Nicht passendes Check-Objekt korrekt erkannt:  " + e.getMessage());
        }
        mSet_HoneyBee.checkForced(iSet_Bee);
        System.out.println(oSet_HoneyBee);

        System.out.println("\n--- Teste iterator() ---");
        System.out.println("=WildBee=");
        itWildBee = mSet_WildBee.iterator();
        while (itWildBee.hasNext()) {
            System.out.println(itWildBee.next().toString());
        }
        System.out.println("=HoneyBee=");
        itHoneyBee = mSet_HoneyBee.iterator();
        while (itHoneyBee.hasNext()) {
            System.out.println(itHoneyBee.next().toString());
        }

        System.out.println("\n--- Teste size() ---");
        System.out.println("Größe des Wildbienen-Sets: " + mSet_WildBee.size());
        System.out.println("Größe des Honigbienen-Sets: " + mSet_HoneyBee.size());

        System.out.println("\n--- Teste plus() ---");
        mSet_WildBee.check(null);
        mSet_HoneyBee.check(null);
        System.out.println("=WildBee=");
        mSet_WildBee.plus(3);
        System.out.println(mSet_WildBee);
        System.out.println("=HoneyBee=");
        mSet_HoneyBee.plus("a");
        System.out.println(mSet_HoneyBee);

        System.out.println("\n--- Teste minus() ---");
        System.out.println("=WildBee=");
        mSet_WildBee.minus(1);
        System.out.println(mSet_WildBee);
        System.out.println("=HoneyBee=");
        mSet_HoneyBee.minus("a");
        System.out.println(mSet_HoneyBee);

        // =================== OM SET =====================

        System.out.print("""
                \n---------------------
                ---    OM SET     ---
                ---------------------
                """);
        System.out.println(om_Set);

        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("Ist 10 vor 20? " + om_Set.before(zehn, zwanzig));
        System.out.println("Ist 20 vor 30? " + om_Set.before(zwanzig, dreissig));

        System.out.println("\n--- Test setBefore(x,y) ---");
        try {
            om_Set.setBefore(dreissig, zwanzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }
        try {
            om_Set.setBefore(vierzig, fuenfzig);
        } catch (IllegalArgumentException e) {
            System.out.println("Fehlendes Element korrekt erkannt: " + e.getMessage());
        }

        System.out.println("\n--- Test add(50) ---");
        OMSet<Num> addSet = om_Set.add(fuenfzig);
        System.out.println(addSet);

        System.out.println("\n--- Test add(30) (schon enthalten) ---");
        OMSet<Num> addSet2 = om_Set.add(dreissig);
        System.out.println(addSet2);

        System.out.println("\n--- Test subtract(30) ---");
        OMSet<Num> subSet = om_Set.subtract(dreissig);
        System.out.println(subSet);

        System.out.println("--- Originales Set sollte unverändert bleiben: ---");
        System.out.println(om_Set);

        System.out.println("\n=== Mit Bee, WildBee, HoneyBee ===");
        OMSet<Bee> omSet_Bee = new OMSet<>();
        OMSet<WildBee> omSet_WildBee = new OMSet<>();
        OMSet<HoneyBee> omSet_HoneyBee = new OMSet<>();

        System.out.println("\n--- Test add(x) ---");
        System.out.println("=Bee=");
        omSet_Bee = omSet_Bee.add(bee1).add(bee2).add(bee3).add(bee4).add(wildBee1).add(wildBee2).add(wildBee3).
                add(wildBee4).add(honeyBee1).add(honeyBee2).add(honeyBee3).add(honeyBee4);
        System.out.println(omSet_Bee);
        System.out.println("=WildBee=");
        omSet_WildBee = omSet_WildBee.add(wildBee1).add(wildBee2).add(wildBee3).add(wildBee4);
        System.out.println(omSet_WildBee);
        System.out.println("=HoneyBee=");
        omSet_HoneyBee = omSet_HoneyBee.add(honeyBee1).add(honeyBee2).add(honeyBee3).add(honeyBee4);
        System.out.println(omSet_HoneyBee);

        System.out.println("\n--- Test setBefore(x,y) ---");
        System.out.println("=Bee=");
        omSet_Bee.setBefore(bee1, bee2);
        omSet_Bee.setBefore(bee2, bee3);
        omSet_Bee.setBefore(bee3, bee4);
        omSet_Bee.setBefore(wildBee1, wildBee2);
        omSet_Bee.setBefore(wildBee2, wildBee3);
        omSet_Bee.setBefore(wildBee3, wildBee4);
        omSet_Bee.setBefore(honeyBee1, honeyBee2);
        omSet_Bee.setBefore(honeyBee2, honeyBee3);
        omSet_Bee.setBefore(honeyBee3, honeyBee4);
        omSet_Bee.setBefore(wildBee1, bee2);
        omSet_Bee.setBefore(bee2, wildBee3);
        omSet_Bee.setBefore(bee1, honeyBee3);
        omSet_Bee.setBefore(honeyBee3, bee3);
        try {
            omSet_Bee.setBefore(bee1, bee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=WildBee=");
        omSet_WildBee.setBefore(wildBee1, wildBee2);
        omSet_WildBee.setBefore(wildBee2, wildBee3);
        omSet_WildBee.setBefore(wildBee3, wildBee4);
        try {
            omSet_WildBee.setBefore(wildBee1, wildBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            omSet_WildBee.setBefore(wildBee4, wildBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }

        System.out.println("=HoneyBee=");
        omSet_HoneyBee.setBefore(honeyBee1, honeyBee2);
        omSet_HoneyBee.setBefore(honeyBee2, honeyBee3);
        omSet_HoneyBee.setBefore(honeyBee3, honeyBee4);
        try {
            omSet_HoneyBee.setBefore(honeyBee1, honeyBee1);
        } catch (IllegalArgumentException e) {
            System.out.println("Gleichen Wert korrekt erkannt: " + e.getMessage());
        }
        try {
            omSet_HoneyBee.setBefore(honeyBee4, honeyBee3);
        } catch (IllegalArgumentException e) {
            System.out.println("Zyklus korrekt erkannt: " + e.getMessage());
        }
        System.out.println("\n--- Test before(x,y) ---");
        System.out.println("=Bee=");
        System.out.println("Ist Biene 1 vor Biene 2? (Ordnungsbeziehung durch setBefore) " + (omSet_Bee.before(bee1, bee2)!=null));
        System.out.println("Ist Biene 1 vor Biene 4? (Transitivität) " + (omSet_Bee.before(bee1, bee4)!=null));
        System.out.println("Ist Biene 4 vor 1? (Gilt nicht) " + (omSet_Bee.before(bee4, bee1)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (omSet_Bee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (omSet_Bee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (omSet_Bee.before(wildBee4, wildBee1)!=null));
        System.out.println("=WildBee=");
        System.out.println("Ist Wildbiene 1 vor Wildbiene 2? (Ordnungsbeziehung durch setBefore) " + (omSet_WildBee.before(wildBee1, wildBee2)!=null));
        System.out.println("Ist Wildbiene 1 vor Wildbiene 4? (Transitivität) " + (omSet_WildBee.before(wildBee1, wildBee4)!=null));
        System.out.println("Ist Wildbiene 4 vor 1? (Gilt nicht) " + (omSet_WildBee.before(wildBee4, wildBee1)!=null));
        System.out.println("Wildbienen nach 1 vor 4?");
        System.out.println("=HoneyBee=");
        System.out.println("Ist Honigbiene 1 vor Honigbiene 2? (Ordnungsbeziehung durch setBefore) " + (omSet_HoneyBee.before(honeyBee1, honeyBee2)!=null));
        System.out.println("Ist Honigbiene 1 vor Honigbiene 4? (Transitivität) " + (omSet_HoneyBee.before(honeyBee1, honeyBee4)!=null));
        System.out.println("Ist Honigbiene 4 vor 1? (Gilt nicht) " + (omSet_HoneyBee.before(honeyBee4, honeyBee1)!=null));


        System.out.println("\n--- Test subtract(x) ---");
        System.out.println("=Bee=");
        OMSet<Bee> omSet_Bee_sub = omSet_Bee.subtract(bee1).subtract(wildBee2).subtract(honeyBee3);
        System.out.println(omSet_Bee_sub);
        System.out.println("Altes Set unverändert:");
        System.out.println(omSet_Bee);
        System.out.println("=WildBee=");
        OMSet<WildBee> omSet_WildBee_sub = omSet_WildBee.subtract(wildBee2).subtract(wildBee1);
        System.out.println(omSet_WildBee_sub);
        System.out.println("Altes Set unverändert:");
        System.out.println(omSet_WildBee);
        System.out.println("=HoneyBee=");
        OMSet<HoneyBee> omSet_HoneyBee_sub = omSet_HoneyBee.subtract(honeyBee1).subtract(honeyBee4);
        System.out.println(omSet_HoneyBee_sub);
        System.out.println("Altes Set unverändert:");
        System.out.println(omSet_HoneyBee);
    }
}
