package items;

/**
 * Represents a melee weapon that the player can equip.
 * Swords vary in power based on their damage, rarity, and tier.
 * These attributes are typically generated randomly by the LootPool.
 */
public class Sword extends Item {

    /** The base damage dealt by this sword. */
    private int damage;

    /** The rarity classification of the sword (e.g., Common, Rare, Legendary). */
    private String rarity;

    /** The material tier of the sword (e.g., Bronze, Iron, Steel). */
    private String tier;

    /**
     * Constructs a new Sword with the specified attributes.
     *
     * @param name   the display name of the sword
     * @param damage the damage value dealt by the sword
     * @param rarity the rarity category of the sword
     * @param tier   the material tier of the sword
     */
    public Sword(String name, int damage, String rarity, String tier) {
        super(name);
        this.damage = damage;
        this.rarity = rarity;
        this.tier = tier;
    }

    /**
     * Returns the damage value of this sword.
     *
     * @return the sword's damage
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Returns the rarity classification of this sword.
     *
     * @return the sword's rarity
     */
    public String getRarity() {
        return rarity;
    }

    /**
     * Returns the material tier of this sword.
     *
     * @return the sword's tier
     */
    public String getTier() {
        return tier;
    }
}
