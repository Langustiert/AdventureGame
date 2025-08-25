public class Choice {
    String key;
    String text;
    Runnable effect;

    public Choice(String key, String text, Runnable effect) {
        this.key = key;
        this.text = text;
        this.effect = effect;
    }
}
