import java.util.ArrayList;

import items.Item;
import items.Potion;
import items.Shield;
import items.Sword;

public class Player {

    // -------------------------
    // INSTANCE VARIABLES
    // -------------------------
    private String name;
    private int health;
    private int maxHealth;
    private int gold;

    private Sword equippedSword;
    private Shield equippedShield;

    private ArrayList<Item> inventory;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public Player(String name) {
        this.name = name;
        this.maxHealth = 100;
        this.health = maxHealth;
        this.gold = 0;

        this.equippedSword = null;
        this.equippedShield = null;

        this.inventory = new ArrayList<Item>();
    }

    // -------------------------
    // GETTERS & SETTERS
    // -------------------------
    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int h) {
        health = h;
        if (health > maxHealth) health = maxHealth;
        if (health < 0) health = 0;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int m) {
        maxHealth = m;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int g) {
        gold = g;
    }

    public Sword getEquippedSword() {
        return equippedSword;
    }

    public Shield getEquippedShield() {
        return equippedShield;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    // -------------------------
    // GOLD MANAGEMENT
    // -------------------------
    public void addGold(int amount) {
        gold += amount;
        if (gold < 0) gold = 0;
    }

    // -------------------------
    // INVENTORY MANAGEMENT
    // -------------------------
    public void addItem(Item item) {
        inventory.add(item);
    }

    public void removePotion(Potion p) {
        inventory.remove(p);
    }

    public Potion getFirstPotion() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Potion) {
                return (Potion)inventory.get(i);
            }
        }
        return null;
    }

    // -------------------------
    // EQUIPMENT
    // -------------------------
    public void equipSword(Sword newSword) {
        equippedSword = newSword;
    }

    public void equipShield(Shield newShield) {
        equippedShield = newShield;
    }

    // -------------------------
    // COMBAT METHODS
    // -------------------------
    public int getAttackDamage() {
        int dmg = 2; // base damage

        if (equippedSword != null) {
            dmg += equippedSword.getDamage();
        }

        return dmg;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public void heal(int amount) {
        health += amount;
        if (health > maxHealth) health = maxHealth;
    }

    // -------------------------
    // STATUS DISPLAY
    // -------------------------
    public void printStatus() {
        System.out.println("\n--- PLAYER STATUS ---");
        System.out.println("Name: " + name);
        System.out.println("HP: " + health + "/" + maxHealth);
        System.out.println("Gold: " + gold);

        if (equippedSword != null) {
            System.out.println("Sword: " + equippedSword.getName() +
                               " (DMG " + equippedSword.getDamage() + ")");
        } else {
            System.out.println("Sword: None");
        }

        if (equippedShield != null) {
            System.out.println("Shield: " + equippedShield.getName() +
                               " (DEF " + equippedShield.getDefense() + ")");
        } else {
            System.out.println("Shield: None");
        }

        System.out.println("Potions: " + countPotions());
    }

    private int countPotions() {
        int count = 0;
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Potion) {
                count++;
            }
        }
        return count;
    }
}
