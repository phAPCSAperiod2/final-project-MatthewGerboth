import java.util.Scanner;

public class ShopRoom {

    private Item[] shopItems;
    private int itemCount;

    public ShopRoom() {
        refreshShop();
    }

    private void refreshShop() {
        // Random number of items: 2–4
        itemCount = (int)(Math.random() * 3) + 2;
        shopItems = new Item[itemCount];

        for (int i = 0; i < itemCount; i++) {
            shopItems[i] = LootPool.generate("SHOP_ITEM");
        }
    }

    public void enter(Player player) {
        System.out.println("A merchant greets you with a grin.");
        refreshShop(); // ← shop refreshes every time you enter
        openShop(player);
    }

    private void openShop(Player player) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- SHOP ---");
            System.out.println("Your gold: " + player.getGold());
            System.out.println("Items for sale:");

            for (int i = 0; i < itemCount; i++) {
                if (shopItems[i] != null) {
                    int price = getPrice(shopItems[i]);
                    System.out.println((i + 1) + ") " + getItemInfo(shopItems[i]) + " - " + price + " gold");
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

    private void buyItem(Player player, int index) {
        Item item = shopItems[index];
        int price = getPrice(item);

        if (player.getGold() < price) {
            System.out.println("You don't have enough gold!");
            return;
        }

        player.addGold(-price); // subtract gold

        Scanner sc = new Scanner(System.in);

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

        else if (item instanceof Potion) {
            player.addItem(item);
            System.out.println("Potion added to inventory.");
        }

        shopItems[index] = null; // remove from shop
    }

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
