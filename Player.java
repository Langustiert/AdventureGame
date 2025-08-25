import java.util.ArrayList;
import java.util.List;

public class Player {
    String name;
    int knowledge = 0;
    int budget = 100;
    int security = 50;
    int agility = 50;
    int reliability = 50;
    List<String> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
    }

    public void addItem(String item) {
        if (!inventory.contains(item)) inventory.add(item);
    }

    public void adjust(int know, int bud, int sec, int agi, int rel) {
        knowledge = Math.max(0, knowledge + know);
        budget = Math.max(0, budget + bud);
        security = clamp(security + sec);
        agility = clamp(agility + agi);
        reliability = clamp(reliability + rel);
    }

    private int clamp(int v) {
        return Math.max(0, Math.min(100, v));
    }

    public boolean isBankrupt() {
        return budget <= 0;
    }

    public void printStats() {
        System.out.println("\n— Status —");
        System.out.printf("Wissen: %d | Budget: %d | Sicherheit: %d | Agilität: %d | Zuverlässigkeit: %d\n",
                knowledge, budget, security, agility, reliability);
        if (!inventory.isEmpty()) {
            System.out.println("Inventar: " + String.join(", ", inventory));
        }
    }
}
