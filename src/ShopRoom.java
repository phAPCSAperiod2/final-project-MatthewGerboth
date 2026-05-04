import java.util.Scanner;

import items.Item;
import items.LootPool;
import items.Potion;
import items.Shield;
import items.Sword;

/**
 * Represents a shop room where the player can purchase randomly generated items.
 * Each shop visit generates 2–4 items for sale, with prices based on item stats.
 * The player may buy items, equip them immediately, or discard them.
 */
public class ShopRoom {

    /** The items currently available for purchase. */
    private Item[] shopItems;

    /** The number of items available in the shop. */
    private int itemCount;

    /**
     * Constructs a new ShopRoom and generates the initial shop inventory.
     */
    public ShopRoom() {
        refreshShop();
    }

    /**
     * Generates a new set of 2–4 random items for the shop.
     * Items are created using the LootPool.
     */
    private void refreshShop() {
        itemCount = (int)(Math.random() * 3) + 2; // 2–4 items
        shopItems = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            shopItems[i] = LootPool.generate("SHOP_ITEM");
        }
    }

    /**
     * Called when the player enters the shop. Refreshes the shop inventory
     * and opens the shop menu for interaction.
     *
     * @param player the player entering the shop
     */
    public void enter(Player player) {
        System.out.println("A merchant greets you with a grin.");
        refreshShop();
        openShop(player);
    }

    /**
     * Displays the shop menu, allowing the player to browse and purchase items.
     * The player may continue shopping until they choose to leave.
     *
     * @param player the player interacting with the shop
     */
    private void openShop(Player player) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SHOP ---");
            System.out.println("Your gold: " + player.getGold());
            System.out.println("Items for sale:");

            for (int i = 0; i < itemCount; i++) {
                if (shopItems[i] != null) {
                    int price = getPrice(shopItems[i]);
                    System.out.println((i + 1) + ") " +
                        getItemInfo(shopItems[i]) + " - " + price + " gold");
                }
            }

            System.out.println((itemCount + 1) + ") Leave shop");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            if (choice == itemCount + 1) {
                System.out.println("You leave the shop.");
                break;
            }

            if (choice >= 1 && choice <= itemCount) {
                if (shopItems[choice - 1] != null) {
                    buyItem(player, choice - 1);
                } else {
                    System.out.println("That item is already sold.");
                }
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    /**
     * Handles the purchase of a selected item. The player must have enough gold.
     * Swords and shields prompt the player to equip them immediately; potions
     * are added directly to the inventory.
     *
     * @param player the player buying the item
     * @param index  the index of the item in the shop array
     */
    private void buyItem(Player player, int index) {
        Item item = shopItems[index];
        int price = getPrice(item);

        if (player.getGold() < price) {
            System.out.println("You don't have enough gold!");
            return;
        }

        player.addGold(-price); // subtract gold

        Scanner sc = new Scanner(System.in);

        // Sword purchase
        if (item instanceof Sword) {
            Sword s = (Sword)item;
            System.out.println("Buy and equip " + s.getName() + "? (yes/no)");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipSword(s);
                System.out.println("You equipped the sword.");
            } else {
                System.out.println("Sword discarded.");
            }
        }

        // Shield purchase
        else if (item instanceof Shield) {
            Shield sh = (Shield)item;
            System.out.println("Buy and equip " + sh.getName() + "? (yes/no)");
            String choice = sc.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipShield(sh);
                System.out.println("You equipped the shield.");
            } else {
                System.out.println("Shield discarded.");
            }
        }

        // Potion purchase
        else if (item instanceof Potion) {
            player.addItem(item);
            System.out.println("Potion added to inventory.");
        }

        shopItems[index] = null; // mark item as sold
    }

    /**
     * Calculates the price of an item based on its stats.
     * <ul>
     *     <li>Swords: damage × 10, plus rarity bonuses</li>
     *     <li>Shields: defense × 12</li>
     *     <li>Potions: heal amount × 2</li>
     * </ul>
     *
     * @param item the item being priced
     * @return the gold cost of the item
     */
    private int getPrice(Item item) {
        int price = 10;

        if (item instanceof Sword) {
            Sword s = (Sword)item;
            price = s.getDamage() * 10;

            if (s.getRarity().equals("Uncommon")) price += 20;
            if (s.getRarity().equals("Rare")) price += 40;
            if (s.getRarity().equals("Legendary")) price += 80;
        }

        if (item instanceof Shield) {
            Shield sh = (Shield)item;
            price = sh.getDefense() * 12;
        }

        if (item instanceof Potion) {
            Potion p = (Potion)item;
            price = p.getHealAmount() * 2;
        }

        return price;
    }

    /**
     * Returns a readable description of an item for display in the shop menu.
     *
     * @param item the item to describe
     * @return a formatted string describing the item
     */
    private String getItemInfo(Item item) {
        if (item instanceof Sword) {
            Sword s = (Sword)item;
            return s.getName() + " (DMG " + s.getDamage() + ")";
        }

        if (item instanceof Shield) {
            Shield sh = (Shield)item;
            return sh.getName() + " (DEF " + sh.getDefense() + ")";
        }

        if (item instanceof Potion) {
            Potion p = (Potion)item;
            return p.getName() + " (Heal " + p.getHealAmount() + ")";
        }

        return "Unknown item";
    }
}
