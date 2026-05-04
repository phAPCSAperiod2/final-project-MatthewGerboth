import java.util.ArrayList;
import java.util.Scanner;

import items.Item;
import items.Potion;
import items.Shield;
import items.Sword;

/**
 * Represents the player character in the game. The player has health,
 * gold, equipment, and an inventory. The Player class also handles
 * combat actions, healing, inventory management, and item usage.
 */
public class Player {

    /** The player's name. */
    private String name;

    /** The player's current and maximum health. */
    private int health;
    private int maxHealth;

    /** The player's base attack damage (before equipment bonuses). */
    private int baseDamage;

    /** The amount of gold the player currently holds. */
    private int gold;

    /** The sword currently equipped by the player (may be null). */
    private Sword equippedSword;

    /** The shield currently equipped by the player (may be null). */
    private Shield equippedShield;

    /** The player's inventory, containing items such as potions and equipment. */
    private ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Constructs a player with a default name ("Player").
     */
    public Player() {
        this("Player");
    }

    /**
     * Constructs a player with the specified name and initializes
     * default stats and starting values.
     *
     * @param name the player's chosen name
     */
    public Player(String name) {
        this.name = name;

        maxHealth = 100;
        health = maxHealth;
        baseDamage = 5;
        gold = 0;
    }

    /** @return the player's name */
    public String getName() {
        return name;
    }

    /**
     * Calculates the player's total attack damage, including bonuses
     * from an equipped sword.
     *
     * @return the player's attack value
     */
    public int getAttack() {
        int dmg = baseDamage;

        if (equippedSword != null) {
            dmg += equippedSword.getDamage();
        }

        return dmg;
    }

    /**
     * Performs an attack on the given enemy and returns the damage dealt.
     *
     * @param enemy the enemy being attacked
     * @return the amount of damage dealt
     */
    public int attackEnemy(Enemy enemy) {
        int dmg = getAttack();
        enemy.takeDamage(dmg);
        return dmg;
    }

    /**
     * Reduces the player's health by the specified damage amount.
     * Health cannot drop below zero.
     *
     * @param dmg the damage taken
     */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    /**
     * Heals the player by the specified amount, without exceeding max health.
     *
     * @param amount the amount of health restored
     */
    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    /** @return the player's current health */
    public int getHealth() { return health; }

    /** @return the player's maximum health */
    public int getMaxHealth() { return maxHealth; }

    /**
     * Adds gold to the player's total.
     *
     * @param amount the amount of gold gained
     */
    public void addGold(int amount) {
        gold += amount;
    }

    /** @return the player's current gold */
    public int getGold() {
        return gold;
    }

    /**
     * Equips the specified sword, replacing any previously equipped weapon.
     *
     * @param sword the sword to equip
     */
    public void equipSword(Sword sword) {
        this.equippedSword = sword;
        System.out.println("You equipped the " + sword.getName() + "!");
    }

    /**
     * Equips the specified shield, replacing any previously equipped shield.
     *
     * @param shield the shield to equip
     */
    public void equipShield(Shield shield) {
        this.equippedShield = shield;
        System.out.println("You equipped the " + shield.getName() + "!");
    }

    /** @return the currently equipped sword (may be null) */
    public Sword getEquippedSword() { return equippedSword; }

    /** @return the currently equipped shield (may be null) */
    public Shield getEquippedShield() { return equippedShield; }

    /**
     * Searches the inventory for a potion, uses the first one found,
     * heals the player, and removes the potion from the inventory.
     *
     * @return true if a potion was used, false if none were found
     */
    public boolean usePotion() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Potion) {
                Potion p = (Potion) inventory.get(i);
                heal(p.getHealAmount());
                System.out.println("You used a " + p.getName() +
                                   " and healed " + p.getHealAmount() + " HP.");
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of inventory items grouped by name, with counts appended
     * for duplicates (e.g., "Potion (heals 20) x3").
     *
     * @return a list of stacked inventory item descriptions
     */
    public ArrayList<String> getStackedInventory() {
        ArrayList<String> result = new ArrayList<>();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> counts = new ArrayList<>();

        for (Item item : inventory) {
            String name;

            if (item instanceof Potion) {
                Potion p = (Potion) item;
                name = p.getName() + " (heals " + p.getHealAmount() + ")";
            } else {
                name = item.getName();
            }

            int index = names.indexOf(name);
            if (index == -1) {
                names.add(name);
                counts.add(1);
            } else {
                counts.set(index, counts.get(index) + 1);
            }
        }

        for (int i = 0; i < names.size(); i++) {
            if (counts.get(i) > 1) {
                result.add(names.get(i) + " x" + counts.get(i));
            } else {
                result.add(names.get(i));
            }
        }

        return result;
    }

    /**
     * Displays the player's inventory in a 3×3 grid format.
     * Empty slots are shown as "Empty".
     */
    public void displayInventoryGrid() {
        ArrayList<String> stacked = getStackedInventory();

        int rows = 3;
        int cols = 3;

        String[][] grid = new String[rows][cols];

        for (int i = 0; i < rows * cols; i++) {
            if (i < stacked.size()) {
                grid[i / cols][i % cols] = stacked.get(i);
            } else {
                grid[i / cols][i % cols] = "Empty";
            }
        }

        System.out.println("\n=== INVENTORY ===");
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                System.out.print("[ " + grid[r][c] + " ] ");
            }
            System.out.println();
        }
    }

    /**
     * Opens the inventory menu, allowing the player to select and use items.
     * Potions can be consumed; other items cannot be used directly.
     * This method does not consume a combat turn.
     */
    public void openInventoryMenu() {
        displayInventoryGrid();

        ArrayList<String> stacked = getStackedInventory();

        if (stacked.size() == 0) {
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("\nChoose an item number to use (1–" + stacked.size() + "), or 0 to exit:");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 0) {
            System.out.println("Closing inventory...");
            return;
        }

        if (choice < 1 || choice > stacked.size()) {
            System.out.println("Invalid choice.");
            return;
        }

        String selected = stacked.get(choice - 1);

        // Find the actual item in the inventory
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            String name;
            if (item instanceof Potion) {
                Potion p = (Potion) item;
                name = p.getName() + " (heals " + p.getHealAmount() + ")";
            } else {
                name = item.getName();
            }

            if (selected.startsWith(name)) {
                if (item instanceof Potion) {
                    Potion p = (Potion) item;
                    heal(p.getHealAmount());
                    System.out.println("You used a " + p.getName() +
                                       " and healed " + p.getHealAmount() + " HP.");
                    inventory.remove(i);
                } else {
                    System.out.println("You can't use that item right now.");
                }
                return;
            }
        }
    }

    /**
     * Adds an item to the player's inventory.
     *
     * @param item the item to add
     */
    public void addItem(Item item) {
        inventory.add(item);
    }
}
