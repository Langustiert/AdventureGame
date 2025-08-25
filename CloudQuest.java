import java.util.*;

public class CloudQuest {
    private final Scanner in = new Scanner(System.in);
    private final Random rng = new Random();
    private Player player;

    public static void main(String[] args) {
        new CloudQuest().run();
    }

    void run() {
        splash();
        System.out.print("Dein Name (CTO): ");
        player = new Player(readLineNonEmpty());
        System.out.println("Willkommen, " + player.name + "! Deine Mission: Von der Idee zur produktionsreifen Cloud-Lösung.\n");

        intro();
        serviceModels();
        deploymentModels();
        securityAndBackup();
        scalabilityAndCost();
        useCases();

        ending();
    }

    // --- Szenen ---
    void splash() {
        System.out.println("============================");
        System.out.println("      C L O U D Q U E S T   ");
        System.out.println("============================\n");
    }

    void intro() {
        Scene s = new Scene("Prolog: Der Auftrag",
                "Dein Startup hat den Zuschlag für eine europaweite Plattform erhalten. " +
                        "Du musst Architektur-Entscheidungen treffen. Jede Wahl beeinflusst Budget, Sicherheit, Agilität und Zuverlässigkeit.");

        s.add(new Choice("A", "Zeit für einen Architektur-Workshop (↑ Wissen, - kleines Budget)", "Workshop", () -> {
            player.adjust(+10, -5, 0, 0, 0);
            player.addItem("Workshop-Notizen");
        }));
        s.add(new Choice("B", "Ohne Workshop direkt los (↑ Tempo, Risiko)", () -> {
            player.adjust(+0, +0, -5, +5, -5);
        }));

        playScene(s);
    }

    void serviceModels() {
        Scene s = new Scene("Kapitel 1: Servicemodelle",
                "Welche Abstraktionsebene wählst du? IaaS bietet volle Kontrolle; PaaS beschleunigt Entwicklung; " +
                        "SaaS konsumiert fertige Software; FaaS (Serverless) skaliert ereignisgetrieben.");

        s.add(new Choice("A", "IaaS: Virtuelle Maschinen & Netzwerke selbst managen", "IaaS → Infrastructure as a Service: Du mietest virtuelle Maschinen, Speicher und Netzwerke. Du hast volle Kontrolle, musst aber auch viel selbst einrichten.",
                () -> { player.adjust(+8, -15, +5, -5, +5); player.addItem("IaaS-Playbook"); }));
        s.add(new Choice("B", "PaaS: Managed Runtimes & Datenbanken", "PaaS → Platform as a Service: Du bekommst fertige Plattformen für Datenbanken und Laufzeitumgebungen. Weniger Arbeit für dich, aber weniger Kontrolle.",
                () -> { player.adjust(+12, -10, +3, +8, +5); player.addItem("PaaS-Vertrag"); }));
        s.add(new Choice("C", "SaaS: Fertige App einkaufen (z. B. CRM)", "SaaS → Software as a Service: Du nutzt fertige Apps wie E-Mail, CRM oder Office. Am schnellsten, aber am wenigsten anpassbar.",
                () -> { player.adjust(+6, -8, +2, +10, +4); player.addItem("SaaS-Lizenzen"); }));
        s.add(new Choice("D", "FaaS/Serverless: Funktionen pro Aufruf bezahlen", "FaaS / Serverless → Function as a Service: Du schreibst nur kleine Funktionen. Die Cloud startet sie automatisch bei Bedarf. Ideal für unregelmäßige Last.",
                () -> { player.adjust(+14, -6, +2, +12, +6); player.addItem("FaaS-Baukasten"); }));

        playScene(s);
        randomEvent("Ein Provider bietet Gutschrift an", () -> player.adjust(+0, +10, 0, 0, 0));
    }

    void deploymentModels() {
        Scene s = new Scene("Kapitel 2: Bereitstellungsmodelle",
                "Wo läuft deine Lösung? Public Cloud ist flexibel; Private Cloud gibt Kontrolle; Hybrid/Multi-Cloud balanciert Risiken.");

        s.add(new Choice("A", "Public Cloud", "Public Cloud → Alle Ressourcen laufen bei einem externen Anbieter (z. B. AWS, Azure, Google). Schnell verfügbar, flexibel, aber weniger Kontrolle.", () -> player.adjust(+8, -8, +2, +10, +6)));
        s.add(new Choice("B", "Private Cloud im eigenen Rechenzentrum", "Private Cloud → Du betreibst die Cloud im eigenen Rechenzentrum. Mehr Sicherheit und Kontrolle, aber hohe Kosten und Wartung.", () -> player.adjust(+6, -20, +8, -6, +6)));
        s.add(new Choice("C", "Hybrid Cloud (Workloads splitten)", "Hybrid Cloud → Kombination aus Public und Private Cloud. Du kannst Workloads flexibel verteilen, aber es ist komplexer.", () -> player.adjust(+10, -12, +6, +4, +8)));
        s.add(new Choice("D", "Multi-Cloud (Vendor-Lock-in reduzieren)", "Multi-Cloud → Du nutzt mehrere Public-Cloud-Anbieter gleichzeitig. So vermeidest du Abhängigkeiten, musst aber alles gut koordinieren.",
                () -> { player.adjust(+12, -14, +5, +2, +10); player.addItem("Abstraktionsschicht"); }));

        playScene(s);
    }

    void securityAndBackup() {
        Scene s = new Scene("Kapitel 3: Sicherung & Datenschutz",
                "Du musst DSGVO beachten, Daten schützen und Backups einrichten.");

        s.add(new Choice("A", "Ende-zu-Ende-Verschlüsselung aktivieren (↑ Sicherheit, Kosten)", "Ende-zu-Ende-Verschlüsselung → Daten werden von Sender bis Empfänger verschlüsselt. Selbst der Cloud-Anbieter kann sie nicht lesen.",
                () -> player.adjust(+8, -6, +12, -2, +4)));
        s.add(new Choice("B", "3-2-1-Backup-Strategie umsetzen", "3-2-1-Backup-Strategie → Drei Kopien deiner Daten, auf zwei verschiedenen Medien, eine Kopie außerhalb (z. B. in einer anderen Cloud).",
                () -> { player.adjust(+10, -8, +10, 0, +10); player.addItem("Immutable Backups"); }));
        s.add(new Choice("C", "Datenlokation EU erzwingen (DSGVO)", "Datenlokation EU → Alle Daten bleiben in europäischen Rechenzentren, damit du die DSGVO einhältst.",
                () -> player.adjust(+6, -4, +8, -1, +2)));
        s.add(new Choice("D", "Zero-Trust-Zugriff & MFA einführen", "Zero-Trust & MFA → Niemand wird automatisch vertraut. Jeder Zugriff wird geprüft und zusätzlich mit Mehrfaktor-Authentifizierung gesichert.",
                () -> player.adjust(+8, -5, +12, -1, +4)));

        playScene(s);
        randomEvent("Ransomware-Angriff simuliert — greift dein Backup?",
                () -> player.adjust(+6, 0, +6, -2, +8));
    }

    void scalabilityAndCost() {
        Scene s = new Scene("Kapitel 4: Skalierbarkeit & Ressourcennutzung",
                "Ein Launch-Event steht an. Nutzerzahlen schwanken stark. Wie skalierst du effizient?");

        s.add(new Choice("A", "Auto-Scaling nach Metriken", "Auto-Scaling → Die Cloud startet automatisch mehr Server, wenn viele Nutzer online sind, und fährt sie wieder runter, wenn es ruhig ist.", () -> player.adjust(+10, -6, 0, +12, +8)));
        s.add(new Choice("B", "Vorab große Instanzen reservieren", "Große Instanzen reservieren → Du buchst von Anfang an sehr viel Kapazität. Sicherheitshalber, aber teuer und oft unnötig.", () -> player.adjust(+4, -12, 0, -2, +10)));
        s.add(new Choice("C", "Observability einführen", "Observability → Du sammelst Metriken, Logs und Traces, um zu sehen, was im System passiert. Hilft beim Erkennen von Problemen.", () -> { player.adjust(+10, -6, +2, +6, +8); player.addItem("Dashboards"); }));
        s.add(new Choice("D", "Kostenoptimierung", "Kostenoptimierung → Du analysierst, wo unnötige Kosten entstehen, und passt Ressourcen an. Spart Geld, erfordert aber Planung.", () -> player.adjust(+8, +6, -1, +4, +4)));

        playScene(s);
        randomEvent("Spitzenlast! Deine Architektur wird getestet.",
                () -> player.adjust(+6, 0, 0, +6, +10));
    }

    void useCases() {
        Scene s = new Scene("Kapitel 5: Anwendungsbereiche & Einsatzszenarien",
                "Wähle, welche Produktlinie zuerst live geht — jede verlangt andere Eigenschaften.");

        s.add(new Choice("A", "Echtzeit-Analytics-Dashboard für IoT-Sensoren", "Echtzeit-Analytics für IoT → Sensoren liefern Datenströme (z. B. aus Fabriken oder Autos), die sofort verarbeitet und visualisiert werden müssen.",
                () -> { player.adjust(+12, -10, +2, +8, +6); player.addItem("Stream-Processing"); }));
        s.add(new Choice("B", "ML-Modell-Serving für Empfehlungen", "ML-Modell-Serving → Trainierte KI-Modelle werden in der Cloud bereitgestellt und beantworten Anfragen in Echtzeit (z. B. Produktempfehlungen).",
                () -> { player.adjust(+14, -12, +2, +10, +6); player.addItem("Feature Store"); }));
        s.add(new Choice("C", "Klassisches Web-CRM als SaaS integrieren", "Web-CRM als SaaS → Ein fertiges Kundenmanagement-System aus der Cloud. Schnell integriert, Standardfunktionen sofort nutzbar.",
                () -> player.adjust(+8, -6, +2, +10, +6)));
        s.add(new Choice("D", "Event-getriebene Serverless-API für Mobile App", "Serverless-API → Eine API, die nur dann läuft, wenn Nutzer sie aufrufen. Spart Kosten und skaliert automatisch mit der Nutzung",
                () -> player.adjust(+12, -8, +2, +12, +6)));

        playScene(s);
    }

    void ending() {
        System.out.println("\n============================");
        System.out.println("         F I N A L E        ");
        System.out.println("============================\n");

        player.printStats();

        int score = player.knowledge + player.security + player.agility + player.reliability + player.budget;
        String outcome;
        if (player.isBankrupt()) {
            outcome = "Das Budget ist erschöpft. Das Board stoppt das Projekt. Lerne aus den Entscheidungen und versuche es erneut!";
        } else if (player.security < 40) {
            outcome = "Eine Prüfung deckt Sicherheitslücken auf. Release verschoben — Nachbessern erforderlich.";
        } else if (player.agility >= 70 && player.reliability >= 70 && player.security >= 60) {
            outcome = "Glanzleistung! Deine Plattform skaliert zuverlässig, hält Compliance ein und das Team liefert schnell.";
        } else if (score >= 260) {
            outcome = "Solide Delivery: ein paar Ecken und Kanten, aber der Launch gelingt und Nutzer sind zufrieden.";
        } else {
            outcome = "Du erreichst einen Beta-Launch. Mit zusätzlicher Optimierung wird es marktreif.";
        }

        System.out.println("\nErgebnis: " + outcome);
        System.out.println("\nDanke fürs Spielen von CloudQuest. Spiele erneut und probiere andere Strategien aus!\n");
    }



    // --- Hilfsfunktionen ---
    void playScene(Scene s) {
        String infoText = "Für genauere Informationen '(choice) info' eingeben. (z.B. 'A info')";
        System.out.println("\n== " + s.title + " ==");
        System.out.println(wrap(s.description+ "\n\n" + infoText));
        showChoices(s);

        while (true) {
            System.out.print("\nDeine Wahl: ");
            String choice = readChoiceKey(s.choices);

            if (choice.endsWith("info")) {
                String base = choice.substring(0, choice.length() - 4); // "Ainfo" -> "A"
                for (Choice c : s.choices) {
                    if (c.key.equalsIgnoreCase(base)) {
                        System.out.println("\nInfo:\n" + wrap(c.info));
                    }
                }
                // nur erneut die Choices anzeigen, nicht die Szenenbeschreibung
                showChoices(s);
                continue;
            }

            // Normale Auswahl
            for (Choice c : s.choices) {
                if (c.key.equalsIgnoreCase(choice)) {
                    c.effect.run();
                }
            }
            player.printStats();
            break; // Szene beenden nach gültiger Auswahl
        }
    }

    void showChoices(Scene s) {
        System.out.println();
        for (Choice c : s.choices) {
            System.out.printf("[%s] %s\n", c.key, c.text);
        }
    }

    void randomEvent(String label, Runnable effect) {
        if (rng.nextBoolean()) {
            System.out.println("\n⚡ Zufallsereignis: " + label);
            effect.run();
            player.printStats();
        }
    }

    String readChoiceKey(List<Choice> choices) {
        while (true) {
            String line = in.nextLine().trim();
            for (Choice c : choices) {
                if (c.key.equalsIgnoreCase(line))
                    return c.key;
                else if (line.equalsIgnoreCase(c.key + " info"))
                    return c.key + "info";
            }
            invalidChoice(choices);
        }
    }

    void invalidChoice(List<Choice> choices) {
        System.out.print("Ungültige Eingabe. Bitte wähle ");
        for (int i = 0; i < choices.size(); i++) {
            System.out.print("[" + choices.get(i).key + "]" + (i < choices.size()-1 ? ", " : ": "));
        }
    }

    String readLineNonEmpty() {
        while (true) {
            String s = in.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.print("Bitte gib einen Namen ein: ");
        }
    }

    static String wrap(String text) {
        int width = 100;
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (String word : text.split(" ")) {
            if (count + word.length() + 1 > width) {
                sb.append('\n');
                count = 0;
            }
            sb.append(word).append(' ');
            count += word.length() + 1;
        }
        return sb.toString();
    }
}
