import java.util.ArrayList;
import java.util.List;

public class Scene {
    String title;
    String description;
    List<Choice> choices = new ArrayList<>();

    public Scene(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Scene add(Choice c) {
        choices.add(c);
        return this;
    }
}
