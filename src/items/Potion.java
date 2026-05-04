package items;

/**
 * Represents a healing potion item that restores a specific
 * amount of health when used by the player. Potions vary in
 * strength depending on their heal amount.
 */
public class Potion extends Item {

    /** The amount of HP this potion restores when used. */
    private int healAmount;

    /**
     * Constructs a new Potion with the given name and heal value.
     *
     * @param name       the display name of the potion
     * @param healAmount the amount of health restored when used
     */
    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    /**
     * Returns how much health this potion restores.
     *
     * @return the heal amount of the potion
     */
    public int getHealAmount() {
        return healAmount;
    }
}
