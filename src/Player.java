public class Player {

    private String name;
    private int gold;

    private Sword equippedSword;
    private Shield equippedShield;

    private ArrayList<Item> inventory = new ArrayList<>();

    public Player(String name) {
        this.name = name;
        this.gold = 0;
        this.equippedSword = null;
        this.equippedShield = null;
    }

    public String getName() {
        return name;
    }

    public void addGold(int amount) {
        gold += amount;
    }

    public int getGold() {
        return gold;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public void equipSword(Sword newSword) {
        if (equippedSword != null) {
            equippedSword = null;
        }
        equippedSword = newSword;
    }

    public void equipShield(Shield newShield) {
        if (equippedShield != null) {
            equippedShield = null;
        }
        equippedShield = newShield;
    }

    public Sword getEquippedSword() {
        return equippedSword;
    }

    public Shield getEquippedShield() {
        return equippedShield;
    }
