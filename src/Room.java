import java.util.Scanner;

import items.Item;
import items.LootPool;
import items.Potion;
import items.Shield;
import items.Sword;

public class Room {

    private String roomType;
    private String description;
    private boolean visited;
    private boolean cleared;
    private Item loot;
    private static int count = 0;

    // -------------------------
    // CONSTRUCTOR
    // -------------------------
    public Room() {
        count++;

        // Every 5th room is a boss
        if (count % 5 == 0) {
            roomType = "BOSS";
        } else {
            roomType = generateRandomType();
        }

        description = generateRandomDescription(roomType);
        visited = false;
        cleared = false;
        loot = null;
    }

    // -------------------------
    // RANDOM ROOM TYPE
    // -------------------------
    private String generateRandomType() {
        int r = (int)(Math.random() * 3); // 0–2

        if (r == 0) return "MONSTER";
        if (r == 1) return "HEAL";
        return "SHOP";
    }

    // -------------------------
    // ENTER ROOM
    // -------------------------
    public void enter(Player player) {

        visited = true;

        // COMBAT ROOMS
        if (roomType.equals("MONSTER") || roomType.equals("BOSS")) {
            CombatSystem.startCombat(player);

            if (player.getHealth() <= 0) {
                System.out.println("You died in battle...");
                return;
            }

            cleared = true;
        }

        // HEAL ROOM
        if (roomType.equals("HEAL")) {
            int heal = (int)(Math.random() * 16) + 10; // 10–25 heal
            player.heal(heal);
            System.out.println("A warm light heals you for +" + heal + " HP.");
            cleared = true;
        }

        // SHOP ROOM
        if (roomType.equals("SHOP")) {
            ShopRoom shop = new ShopRoom();
            shop.enter(player);
            cleared = true;
        }

        // LOOT (only once)
        if (loot == null) {
            loot = LootPool.generate(roomType);

            if (loot == null) {
                // Gold drop
                int gold = LootPool.generateGoldAmount();
                player.addGold(gold);
                System.out.println("You found " + gold + " gold!");
            } else {
                giveLootToPlayer(player, loot);
            }
        }
    }

    // -------------------------
    // GIVE LOOT TO PLAYER
    // -------------------------
    private void giveLootToPlayer(Player player, Item item) {
        Scanner scanner = new Scanner(System.in);

        // Sword
        if (item instanceof Sword) {
            Sword s = (Sword)item;

            System.out.println("\nYou found a sword:");
            System.out.println(s.getName() + " (DMG " + s.getDamage() + ", " + s.getRarity() + ", " + s.getTier() + ")");
            System.out.println("Equip it? (yes/no)");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipSword(s);
                System.out.println("You equipped the sword.");
            } else {
                System.out.println("You leave the sword behind.");
            }
            return;
        }

        // Shield
        if (item instanceof Shield) {
            Shield sh = (Shield)item;

            System.out.println("\nYou found a shield:");
            System.out.println(sh.getName() + " (DEF " + sh.getDefense() + ")");
            System.out.println("Equip it? (yes/no)");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipShield(sh);
                System.out.println("You equipped the shield.");
            } else {
                System.out.println("You leave the shield behind.");
            }
            return;
        }

        // Potion
        if (item instanceof Potion) {
            Potion p = (Potion)item;
            System.out.println("\nYou found a potion: " + p.getName() + " (Heals " + p.getHealAmount() + ")");
            player.addItem(p);
            System.out.println("Potion added to inventory.");
        }
    }

    // -------------------------
    // ROOM DESCRIPTION
    // -------------------------
    private String generateRandomDescription(String type) {

        if (type.equals("MONSTER")) {
            String[] desc = {
                "You hear growling in the darkness.",
                "A foul stench fills the air.",
                "Something moves in the shadows."
            };
            return desc[(int)(Math.random() * desc.length)];
        }

        if (type.equals("HEAL")) {
            String[] desc = {
                "A warm light fills the room.",
                "You feel a soothing presence.",
                "A healing aura surrounds you."
            };
            return desc[(int)(Math.random() * desc.length)];
        }

        if (type.equals("SHOP")) {
            String[] desc = {
                "A merchant greets you with a grin.",
                "You find a small traveling shop.",
                "A trader waves you over."
            };
            return desc[(int)(Math.random() * desc.length)];
        }

        if (type.equals("BOSS")) {
            String[] desc = {
                "The air grows heavy… a powerful presence awaits.",
                "A massive shadow looms ahead.",
                "You feel overwhelming danger."
            };
            return desc[(int)(Math.random() * desc.length)];
        }

        return "An empty room.";
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public String getType() {
        return roomType;
    }

    public String describe() {
        return description;
    }

    public boolean isCleared() {
        return cleared;
    }

    public Item getLoot() {
        return loot;
    }
}
