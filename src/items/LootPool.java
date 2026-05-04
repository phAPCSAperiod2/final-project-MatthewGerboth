package items;

/**
 * The LootPool class is responsible for generating random loot items
 * such as potions, shields, swords, and gold amounts. It is used by
 * rooms, shops, and other game systems that require randomized rewards.
 */
public class LootPool {

    /**
     * Generates a random Item based on a simple loot pool.
     * The pool includes:
     * <ul>
     *     <li>0 → Potion</li>
     *     <li>1 → Shield</li>
     *     <li>2 → Sword</li>
     *     <li>3 → (null, used for gold handled separately)</li>
     * </ul>
     *
     * @param roomType the type of room generating loot (currently unused,
     *                 but allows future expansion for room‑specific loot tables)
     * @return a randomly generated Item, or null if gold should be awarded
     */
    public static Item generate(String roomType) {

        int pool = (int)(Math.random() * 4);

        if (pool == 0) {
            return generatePotion();
        }

        if (pool == 1) {
            return generateShield();
        }

        if (pool == 2) {
            return generateSword();
        }

        return null; // gold handled separately
    }

    /**
     * Generates a random amount of gold between 10 and 30 (inclusive).
     *
     * @return the generated gold amount
     */
    public static int generateGoldAmount() {
        return (int)(Math.random() * 21) + 10;
    }

    /**
     * Generates a random Potion with a name and heal amount.
     * Possible potions:
     * <ul>
     *     <li>Small Potion → 10 HP</li>
     *     <li>Potion → 20 HP</li>
     *     <li>Large Potion → 40 HP</li>
     * </ul>
     *
     * @return a randomly selected Potion
     */
    private static Potion generatePotion() {
        String[] names = {"Small Potion", "Potion", "Large Potion"};
        int[] heals = {10, 20, 40};

        int i = (int)(Math.random() * names.length);
        return new Potion(names[i], heals[i]);
    }

    /**
     * Generates a random Shield with a name and defense value.
     * Possible shields:
     * <ul>
     *     <li>Wooden Shield → 2 DEF</li>
     *     <li>Iron Shield → 4 DEF</li>
     *     <li>Steel Shield → 6 DEF</li>
     * </ul>
     *
     * @return a randomly selected Shield
     */
    private static Shield generateShield() {
        String[] names = {"Wooden Shield", "Iron Shield", "Steel Shield"};
        int[] defense = {2, 4, 6};

        int i = (int)(Math.random() * names.length);
        return new Shield(names[i], defense[i]);
    }

    /**
     * Generates a random Sword with:
     * <ul>
     *     <li>Rarity (Common, Uncommon, Rare, Legendary)</li>
     *     <li>Tier (Bronze, Iron, Steel)</li>
     *     <li>Damage based on rarity + random bonus</li>
     *     <li>Randomly constructed name (prefix + base + suffix)</li>
     * </ul>
     *
     * Rarity chances:
     * <ul>
     *     <li>50% Common</li>
     *     <li>30% Uncommon</li>
     *     <li>15% Rare</li>
     *     <li>5% Legendary</li>
     * </ul>
     *
     * @return a fully generated Sword object
     */
    private static Sword generateSword() {

        String rarity = "";
        double r = Math.random();

        if (r < 0.50) rarity = "Common";
        if (r >= 0.50 && r < 0.80) rarity = "Uncommon";
        if (r >= 0.80 && r < 0.95) rarity = "Rare";
        if (r >= 0.95) rarity = "Legendary";

        String tier = "";
        int t = (int)(Math.random() * 3);

        if (t == 0) tier = "Bronze";
        if (t == 1) tier = "Iron";
        if (t == 2) tier = "Steel";

        int baseDamage = 0;

        if (rarity.equals("Common")) baseDamage = 3;
        if (rarity.equals("Uncommon")) baseDamage = 5;
        if (rarity.equals("Rare")) baseDamage = 8;
        if (rarity.equals("Legendary")) baseDamage = 12;

        int bonus = (int)(Math.random() * 4); // +0 to +3
        int damage = baseDamage + bonus;

        String[] prefixes = {"Rusty", "Sharp", "Heavy", "Ancient", "Glowing"};
        String[] bases = {"Sword", "Blade", "Saber", "Longsword", "Edge"};
        String[] suffixes = {"of Fury", "of Night", "of Power", "of Kings", "of Doom"};

        String prefix = prefixes[(int)(Math.random() * prefixes.length)];
        String base = bases[(int)(Math.random() * bases.length)];
        String suffix = suffixes[(int)(Math.random() * suffixes.length)];

        String name = prefix + " " + base + " " + suffix;

        return new Sword(name, damage, rarity, tier);
    }
}
