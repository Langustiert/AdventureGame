import java.util.*;
import com.google.gson.Gson;
import java.io.FileReader;

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

        List<Scene> scenes = loadScenesFromJson("chapters.json");
        for (Scene scene : scenes) {
            playScene(scene);
        }

        ending();
    }

    List<Scene> loadScenesFromJson(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            ChaptersFile file = gson.fromJson(reader, ChaptersFile.class);

            List<Scene> scenes = new ArrayList<>();
            for (ChapterData cd : file.chapters) {
                Scene scene = new Scene(cd.title, cd.description);

                for (ChoiceData ch : cd.choices) {
                    scene.add(new Choice(
                            ch.key,
                            ch.text,
                            ch.info,
                            () -> {
                                if (ch.effect != null) {
                                    player.adjust(
                                            ch.effect.getOrDefault("knowledge", 0),
                                            ch.effect.getOrDefault("budget", 0),
                                            ch.effect.getOrDefault("security", 0),
                                            ch.effect.getOrDefault("agility", 0),
                                            ch.effect.getOrDefault("reliability", 0)
                                    );
                                    if (ch.items != null)
                                        player.addItems(ch.items);
                                }
                            }
                    ));
                }
                scenes.add(scene);
            }
            return scenes;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Laden der Kapitel: " + e.getMessage(), e);
        }
    }

    // --- Szenen ---
    void splash() {
        System.out.println("============================");
        System.out.println("      C L O U D Q U E S T   ");
        System.out.println("============================\n");
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
