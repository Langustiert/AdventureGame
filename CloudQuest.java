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

        s.add(new Choice("A", "Zeit für einen Architektur-Workshop (↑ Wissen, - kleines Budget)", () -> {
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

        s.add(new Choice("1", "IaaS: Virtuelle Maschinen & Netzwerke selbst managen",
                () -> { player.adjust(+8, -15, +5, -5, +5); player.addItem("IaaS-Playbook"); }));
        s.add(new Choice("2", "PaaS: Managed Runtimes & Datenbanken",
                () -> { player.adjust(+12, -10, +3, +8, +5); player.addItem("PaaS-Vertrag"); }));
        s.add(new Choice("3", "SaaS: Fertige App einkaufen (z. B. CRM)",
                () -> { player.adjust(+6, -8, +2, +10, +4); player.addItem("SaaS-Lizenzen"); }));
        s.add(new Choice("4", "FaaS/Serverless: Funktionen pro Aufruf bezahlen",
                () -> { player.adjust(+14, -6, +2, +12, +6); player.addItem("FaaS-Baukasten"); }));

        playScene(s);
        randomEvent("Ein Provider bietet Gutschrift an", () -> player.adjust(+0, +10, 0, 0, 0));
    }

    void deploymentModels() {
        Scene s = new Scene("Kapitel 2: Bereitstellungsmodelle",
                "Wo läuft deine Lösung? Public Cloud ist flexibel; Private Cloud gibt Kontrolle; Hybrid/Multi-Cloud balanciert Risiken.");

        s.add(new Choice("A", "Public Cloud", () -> player.adjust(+8, -8, +2, +10, +6)));
        s.add(new Choice("B", "Private Cloud im eigenen Rechenzentrum", () -> player.adjust(+6, -20, +8, -6, +6)));
        s.add(new Choice("C", "Hybrid Cloud (Workloads splitten)", () -> player.adjust(+10, -12, +6, +4, +8)));
        s.add(new Choice("D", "Multi-Cloud (Vendor-Lock-in reduzieren)",
                () -> { player.adjust(+12, -14, +5, +2, +10); player.addItem("Abstraktionsschicht"); }));

        playScene(s);
    }

    void securityAndBackup() {
        Scene s = new Scene("Kapitel 3: Sicherung & Datenschutz",
                "Du musst DSGVO beachten, Daten schützen und Backups einrichten.");

        s.add(new Choice("1", "Ende-zu-Ende-Verschlüsselung aktivieren (↑ Sicherheit, Kosten)",
                () -> player.adjust(+8, -6, +12, -2, +4)));
        s.add(new Choice("2", "3-2-1-Backup-Strategie umsetzen",
                () -> { player.adjust(+10, -8, +10, 0, +10); player.addItem("Immutable Backups"); }));
        s.add(new Choice("3", "Datenlokation EU erzwingen (DSGVO)",
                () -> player.adjust(+6, -4, +8, -1, +2)));
        s.add(new Choice("4", "Zero-Trust-Zugriff & MFA einführen",
                () -> player.adjust(+8, -5, +12, -1, +4)));

        playScene(s);
        randomEvent("Ransomware-Angriff simuliert — greift dein Backup?",
                () -> player.adjust(+6, 0, +6, -2, +8));
    }

    void scalabilityAndCost() {
        Scene s = new Scene("Kapitel 4: Skalierbarkeit & Ressourcennutzung",
                "Ein Launch-Event steht an. Nutzerzahlen schwanken stark. Wie skalierst du effizient?");

        s.add(new Choice("A", "Auto-Scaling nach Metriken", () -> player.adjust(+10, -6, 0, +12, +8)));
        s.add(new Choice("B", "Vorab große Instanzen reservieren", () -> player.adjust(+4, -12, 0, -2, +10)));
        s.add(new Choice("C", "Observability einführen", () -> { player.adjust(+10, -6, +2, +6, +8); player.addItem("Dashboards"); }));
        s.add(new Choice("D", "Kostenoptimierung", () -> player.adjust(+8, +6, -1, +4, +4)));

        playScene(s);
        randomEvent("Spitzenlast! Deine Architektur wird getestet.",
                () -> player.adjust(+6, 0, 0, +6, +10));
    }

    void useCases() {
        Scene s = new Scene("Kapitel 5: Anwendungsbereiche & Einsatzszenarien",
                "Wähle, welche Produktlinie zuerst live geht — jede verlangt andere Eigenschaften.");

        s.add(new Choice("1", "Echtzeit-Analytics-Dashboard für IoT-Sensoren",
                () -> { player.adjust(+12, -10, +2, +8, +6); player.addItem("Stream-Processing"); }));
        s.add(new Choice("2", "ML-Modell-Serving für Empfehlungen",
                () -> { player.adjust(+14, -12, +2, +10, +6); player.addItem("Feature Store"); }));
        s.add(new Choice("3", "Klassisches Web-CRM als SaaS integrieren",
                () -> player.adjust(+8, -6, +2, +10, +6)));
        s.add(new Choice("4", "Event-getriebene Serverless-API für Mobile App",
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
        System.out.println("\n== " + s.title + " ==");
        System.out.println(wrap(s.description));
        System.out.println();
        for (Choice c : s.choices) {
            System.out.printf("[%s] %s\n", c.key, c.text);
        }
        System.out.print("\nDeine Wahl: ");
        String choice = readChoiceKey(s.choices);
        for (Choice c : s.choices) if (c.key.equalsIgnoreCase(choice)) c.effect.run();
        player.printStats();
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
                if (c.key.equalsIgnoreCase(line)) return c.key;
            }
            System.out.print("Ungültige Eingabe. Bitte wähle ");
            for (int i = 0; i < choices.size(); i++) {
                System.out.print("[" + choices.get(i).key + "]" + (i < choices.size()-1 ? ", " : ": "));
            }
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
        int width = 88;
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
