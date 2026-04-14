public class LootPool {

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

        return null; // gold handled separately
    }

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
