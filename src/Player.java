import java.util.ArrayList;
import java.util.Scanner;

import items.Item;
import items.Potion;
import items.Shield;
import items.Sword;

public class Player {

    private String name;

    private int health;
    private int maxHealth;
    private int baseDamage;

    private int gold;

    private Sword equippedSword;
    private Shield equippedShield;

    private ArrayList<Item> inventory = new ArrayList<>();

   
    public Player() {
        this("Player"); // default name
    }


    public Player(String name) {
        this.name = name;

        maxHealth = 100;
        health = maxHealth;
        baseDamage = 5;
        gold = 0;
    }

    public String getName() {
        return name;
    }


    public int getAttack() {
        int dmg = baseDamage;

        if (equippedSword != null) {
            dmg += equippedSword.getDamage();
        }

        return dmg;
    }

    public int attackEnemy(Enemy enemy) {
        int dmg = getAttack();
        enemy.takeDamage(dmg);
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

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }


    public void addGold(int amount) {
        gold += amount;
    }

    public int getGold() {
        return gold;
    }


    public void equipSword(Sword sword) {
        this.equippedSword = sword;
        System.out.println("You equipped the " + sword.getName() + "!");
    }

    public void equipShield(Shield shield) {
        this.equippedShield = shield;
        System.out.println("You equipped the " + shield.getName() + "!");
    }

    public Sword getEquippedSword() { return equippedSword; }
    public Shield getEquippedShield() { return equippedShield; }

    public boolean usePotion() {
        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i) instanceof Potion) {
                Potion p = (Potion) inventory.get(i);
                heal(p.getHealAmount());
                System.out.println("You used a " + p.getName() + " and healed " + p.getHealAmount() + " HP.");
                inventory.remove(i);
                return true;
            }
        }
        return false;
    }


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
                    System.out.println("You used a " + p.getName() + " and healed " + p.getHealAmount() + " HP.");
                    inventory.remove(i);
                } else {
                    System.out.println("You can't use that item right now.");
                }
                return;
            }
        }
    }


    public void addItem(Item item) {
        inventory.add(item);
    }
}
