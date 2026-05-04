import java.util.Scanner;

import items.Item;
import items.LootPool;
import items.Potion;
import items.Shield;
import items.Sword;

/**
 * Represents a room in the dungeon. Rooms can be one of four types:
 * <ul>
 *     <li>MONSTER – triggers a normal combat encounter</li>
 *     <li>BOSS – every 5th room, triggers a boss fight</li>
 *     <li>HEAL – restores a random amount of health</li>
 *     <li>SHOP – allows the player to buy items</li>
 * </ul>
 *
 * Each room also has a description, a cleared state, and optional loot.
 * Loot is generated once per room after the event is resolved.
 */
public class Room {

    /** The type of room (MONSTER, BOSS, HEAL, SHOP). */
    private String roomType;

    /** A randomly generated description based on the room type. */
    private String description;

    /** Whether the player has entered this room before. */
    private boolean visited;

    /** Whether the room's event has been completed. */
    private boolean cleared;

    /** The loot generated for this room (may be null if gold is awarded). */
    private Item loot;

    /** Counts how many rooms have been created, used for difficulty scaling. */
    private static int count = 0;

    /**
     * Constructs a new room and determines its type based on the room count:
     * <ul>
     *     <li>Every 5th room → BOSS</li>
     *     <li>Odd-numbered rooms → MONSTER</li>
     *     <li>Even-numbered rooms → HEAL or SHOP</li>
     * </ul>
     * Also generates a description and initializes state flags.
     */
    public Room() {
        count++;

        if (count % 5 == 0) {
            roomType = "BOSS";
        }
        else if (count % 2 == 1) {
            roomType = "MONSTER";
        }
        else {
            roomType = generateNonMonsterType();
        }

        description = generateRandomDescription(roomType);
        visited = false;
        cleared = false;
        loot = null;
    }

    /**
     * Randomly selects between HEAL and SHOP for even-numbered rooms.
     *
     * @return "HEAL" or "SHOP"
     */
    private String generateNonMonsterType() {
        int r = (int)(Math.random() * 2);
        return (r == 0) ? "HEAL" : "SHOP";
    }

    /**
     * Handles the player's interaction with the room. Depending on the room type,
     * this may trigger combat, healing, or a shop visit. After the event, loot is
     * generated and awarded to the player.
     *
     * @param player the player entering the room
     */
    public void enter(Player player) {

        visited = true;

        // -------------------------
        // MONSTER / BOSS ROOM
        // -------------------------
        if (roomType.equals("MONSTER") || roomType.equals("BOSS")) {
            CombatSystem.startCombat(player);

            if (player.getHealth() <= 0) {
                System.out.println("You died in battle...");
                return;
            }

            cleared = true;
        }

        // -------------------------
        // HEAL ROOM
        // -------------------------
        if (roomType.equals("HEAL")) {
            int heal = (int)(Math.random() * 16) + 10; // 10–25 HP
            player.heal(heal);
            System.out.println("A warm light heals you for +" + heal + " HP.");
            cleared = true;
        }

        // -------------------------
        // SHOP ROOM
        // -------------------------
        if (roomType.equals("SHOP")) {
            ShopRoom shop = new ShopRoom();
            shop.enter(player);
            cleared = true;
        }

        // -------------------------
        // LOOT GENERATION
        // -------------------------
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

    /**
     * Gives the generated loot to the player. Swords and shields prompt the
     * player to equip them; potions are automatically added to inventory.
     *
     * @param player the player receiving the loot
     * @param item   the item found in the room
     */
    private void giveLootToPlayer(Player player, Item item) {
        Scanner scanner = new Scanner(System.in);

        // Sword
        if (item instanceof Sword) {
            Sword s = (Sword)item;

            System.out.println("\nYou found a sword:");
            System.out.println(s.getName() + " (DMG " + s.getDamage() +
                               ", " + s.getRarity() + ", " + s.getTier() + ")");
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
            System.out.println("\nYou found a potion: " + p.getName() +
                               " (Heals " + p.getHealAmount() + ")");
            player.addItem(p);
            System.out.println("Potion added to inventory.");
        }
    }

    /**
     * Generates a random room description based on the room type.
     *
     * @param type the room type
     * @return a description string
     */
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

    /** @return the room type */
    public String getType() {
        return roomType;
    }

    /** @return the room's description */
    public String describe() {
        return description;
    }

    /** @return true if the room's event has been completed */
    public boolean isCleared() {
        return cleared;
    }

    /** @return the loot generated for this room (may be null) */
    public Item getLoot() {
        return loot;
    }

    /**
     * Returns the total number of rooms created so far.
     * Used for difficulty scaling and boss generation.
     *
     * @return the room count
     */
    public static int getRoomCount() {
        return count;
    }
}
