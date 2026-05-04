package items;
/**
 * Represents a generic item that can appear in the game.
 * Each item has a name, and subclasses may add additional
 * properties or behaviors (e.g., Sword, Shield, Potion).
 */
public class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
