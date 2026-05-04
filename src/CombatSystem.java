import java.util.Scanner;

/**
 * Handles all turn-based combat interactions between the player and an enemy.
 * This system manages player actions, enemy responses, and the overall flow
 * of a battle. Combat continues until the enemy is defeated, the player runs
 * away, or the player's health reaches zero.
 *
 * <p><b>Important mechanic:</b> Using a potion or opening the inventory
 * does <i>not</i> consume the player's turn. The enemy only attacks when
 * the player performs an offensive action (Attack).</p>
 */
public class CombatSystem {

    /**
     * Starts a combat encounter between the player and a newly generated enemy.
     * Displays health values, prompts the player for actions, and processes
     * turn outcomes until the battle ends.
     *
     * @param player the player participating in the combat
     */
    public static void startCombat(Player player) {

        Scanner scanner = new Scanner(System.in);
        Enemy enemy = new Enemy();

        System.out.println("\nA " + enemy.getName() + " appears!");
        System.out.println("Enemy HP: " + enemy.getHealth());

        // Main combat loop
        while (player.getHealth() > 0 && enemy.isAlive()) {

            // Display current health
            System.out.println("\nYour HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());

            // Player action menu
            System.out.println("\nChoose an action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Potion");
            System.out.println("3. Inventory");
            System.out.println("4. Run");

            String choice = scanner.nextLine();

            boolean playerTookTurn = false;

            // -------------------------
            // PLAYER ATTACK
            // -------------------------
            if (choice.equals("1")) {
                int dmg = player.attackEnemy(enemy);
                System.out.println("You dealt " + dmg + " damage!");

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    break;
                }

                playerTookTurn = true;
            }

            // -------------------------
            // USE POTION (no turn consumed)
            // -------------------------
            else if (choice.equals("2")) {
                boolean used = player.usePotion();
                if (!used) {
                    System.out.println("You have no potions!");
                }
                continue; // enemy does NOT attack
            }

            // -------------------------
            // OPEN INVENTORY (no turn consumed)
            // -------------------------
            else if (choice.equals("3")) {
                player.openInventoryMenu();
                continue; // enemy does NOT attack
            }

            // -------------------------
            // RUN AWAY
            // -------------------------
            else if (choice.equals("4")) {
                System.out.println("You ran away!");
                return;
            }

            // -------------------------
            // ENEMY COUNTERATTACK
            // -------------------------
            if (playerTookTurn) {
                System.out.println(enemy.getName() + " attacks!");
                enemy.attackPlayer(player);

                if (player.getHealth() <= 0) {
                    System.out.println("You were defeated...");
                    return;
                }
            }
        }
    }
}
