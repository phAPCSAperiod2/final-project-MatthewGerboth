package items;

public class LootPool {

    // Main loot generator
    public static Item generate(String roomType) {

        int pool = (int)(Math.random() * 4);
        // 0 = potion, 1 = shield, 2 = sword, 3 = gold

        if (pool == 0) {
            return generatePotion();
        }

        if (pool == 1) {
            return generateShield();
        }

        if (pool == 2) {
            return generateSword();
        }

        // Gold is NOT an item object
        return null;
    }

    // Gold amount generator
    public static int generateGoldAmount() {
        return (int)(Math.random() * 21) + 10; // 10–30 gold
    }

    // -----------------------------
    // POTION GENERATOR
    // -----------------------------
    private static Potion generatePotion() {
        String[] names = {"Small Potion", "Potion", "Large Potion"};
        int[] heals = {10, 20, 40};

        int i = (int)(Math.random() * names.length);
        return new Potion(names[i], heals[i]);
    }

    // -----------------------------
    // SHIELD GENERATOR
    // -----------------------------
    private static Shield generateShield() {
        String[] names = {"Wooden Shield", "Iron Shield", "Steel Shield"};
        int[] defense = {2, 4, 6};

        int i = (int)(Math.random() * names.length);
        return new Shield(names[i], defense[i]);
    }

    // -----------------------------
    // SWORD GENERATOR (random rarity, tier, name, damage)
    // -----------------------------
    private static Sword generateSword() {

        // ---------- RARITY ----------
        String rarity = "";
        double r = Math.random();

        if (r < 0.50) rarity = "Common";
        if (r >= 0.50 && r < 0.80) rarity = "Uncommon";
        if (r >= 0.80 && r < 0.95) rarity = "Rare";
        if (r >= 0.95) rarity = "Legendary";

        // ---------- TIER ----------
        String tier = "";
        int t = (int)(Math.random() * 3);

        if (t == 0) tier = "Bronze";
        if (t == 1) tier = "Iron";
        if (t == 2) tier = "Steel";

        // ---------- DAMAGE ----------
        int baseDamage = 0;

        if (rarity.equals("Common")) baseDamage = 3;
        if (rarity.equals("Uncommon")) baseDamage = 5;
        if (rarity.equals("Rare")) baseDamage = 8;
        if (rarity.equals("Legendary")) baseDamage = 12;

        int bonus = (int)(Math.random() * 4); // +0 to +3
        int damage = baseDamage + bonus;

        // ---------- NAME ----------
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
