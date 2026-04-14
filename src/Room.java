import java.util.Scanner;

public class Room {

    private String roomType;
    private String description;
    private boolean visited;
    private boolean cleared;
    private Item loot;
    private static int count;

    public Room() {
        count++;

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

    private String generateRandomType() {
        int random = (int)(Math.random() * 3) + 1;

        if (random == 1) return "SHOP";
        if (random == 2) return "MONSTER";
        return "HEAL";
    }

    public void enter(Player player) {
        visited = true;

        if (loot == null) {
            loot = LootPool.generate(roomType);

            if (loot == null) {
                int gold = LootPool.generateGoldAmount();
                player.addGold(gold);
                System.out.println("You found " + gold + " gold!");
            } else {
                handleLoot(player, loot);
            }
        }
    }

    private void handleLoot(Player player, Item item) {
        Scanner scanner = new Scanner(System.in);

        // Sword
        if (item instanceof Sword) {
            Sword s = (Sword)item;

            System.out.println("You found a sword:");
            System.out.println("Name: " + s.getName());
            System.out.println("Damage: " + s.getDamage());
            System.out.println("Rarity: " + s.getRarity());
            System.out.println("Tier: " + s.getTier());
            System.out.println("Equip this sword? (yes/no)");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipSword(s);
                System.out.println("You equipped the new sword.");
            } else {
                System.out.println("You leave the sword behind.");
            }
            return;
        }

        // Shield
        if (item instanceof Shield) {
            Shield sh = (Shield)item;

            System.out.println("You found a shield:");
            System.out.println("Name: " + sh.getName());
            System.out.println("Defense: " + sh.getDefense());
            System.out.println("Equip this shield? (yes/no)");

            String choice = scanner.nextLine();

            if (choice.equalsIgnoreCase("yes")) {
                player.equipShield(sh);
                System.out.println("You equipped the new shield.");
            } else {
                System.out.println("You leave the shield behind.");
            }
            return;
        }

        // Potion
        if (item instanceof Potion) {
            Potion p = (Potion)item;

            System.out.println("You found a potion: " + p.getName() + " (Heals " + p.getHealAmount() + ")");
            player.addItem(p);
            System.out.println("Potion added to inventory.");
        }
    }

    public String describe() {
        return description;
    }

    public boolean isCleared() {
        return cleared;
    }

    public String getType() {
        return roomType;
    }

    public Item getLoot() {
        return loot;
    }

    private String generateRandomDescription(String type) {

        if (type.equals("MONSTER")) {
            String[] monsterDesc = {
                "You hear growling in the darkness.",
                "A foul stench fills the air.",
                "Something moves in the shadows."
            };
            return monsterDesc[(int)(Math.random() * monsterDesc.length)];
        }

        if (type.equals("HEAL")) {
            String[] healDesc = {
                "A warm light fills the room.",
                "You feel a soothing presence.",
                "A healing aura surrounds you."
            };
            return healDesc[(int)(Math.random() * healDesc.length)];
        }

        if (type.equals("SHOP")) {
            String[] shopDesc = {
                "A merchant greets you with a grin.",
                "You find a small traveling shop.",
                "A trader waves you over."
            };
            return shopDesc[(int)(Math.random() * shopDesc.length)];
        }

        if (type.equals("BOSS")) {
            String[] bossDesc = {
                "The air grows heavy… a powerful presence awaits.",
                "A massive shadow looms ahead.",
                "You feel overwhelming danger."
            };
            return bossDesc[(int)(Math.random() * bossDesc.length)];
        }

        return "Undefined room type.";
    }
}
