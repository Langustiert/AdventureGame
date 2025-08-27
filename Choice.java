import java.util.ArrayList;

public class Choice {
    String key;
    String text;
    String info;
    Runnable effect;

    public Choice(String key, String text, Runnable effect) {
        this.key = key;
        this.text = text;
        this.effect = effect;

        this.info = text;
    }

    public Choice(String key, String text, String info, Runnable effect) {
        this.key = key;
        this.text = text;
        this.info = info;
        this.effect = effect;
    }
}
