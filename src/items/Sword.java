package items;

public class Sword extends Item {

    private int damage;
    private String rarity;
    private String tier;

    public Sword(String name, int damage, String rarity, String tier) {
        super(name);
        this.damage = damage;
        this.rarity = rarity;
        this.tier = tier;
    }

    public int getDamage() {
        return damage;
    }

    public String getRarity() {
        return rarity;
    }

    public String getTier() {
        return tier;
    }
}
