package items;

/**
 * Represents a defensive item that increases the player's
 * defense stat when equipped. Shields vary in strength
 * depending on their defense value.
 */
public class Shield extends Item {

    /** The amount of defense this shield provides. */
    private int defense;

    /**
     * Constructs a new Shield with the given name and defense value.
     *
     * @param name    the display name of the shield
     * @param defense the defense value provided by the shield
     */
    public Shield(String name, int defense) {
        super(name);
        this.defense = defense;
    }

    /**
     * Returns the defense value of this shield.
     *
     * @return the shield's defense amount
     */
    public int getDefense() {
        return defense;
    }
}
