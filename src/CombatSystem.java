import java.util.Scanner;

public class CombatSystem {

    public static void startCombat(Player player) {

        Scanner scanner = new Scanner(System.in);
        Enemy enemy = new Enemy();

        System.out.println("\nA " + enemy.getName() + " appears!");
        System.out.println("Enemy HP: " + enemy.getHealth());

        // -----------------------------
        // MAIN COMBAT LOOP
        // -----------------------------
        while (player.getHealth() > 0 && enemy.isAlive()) {

            System.out.println("\nYour HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());

            System.out.println("\nChoose an action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Potion");
            System.out.println("3. Run");

            String choice = scanner.nextLine();

            // -----------------------------
            // PLAYER ATTACK
            // -----------------------------
            if (choice.equals("1")) {
                int dmg = player.attackEnemy(enemy);
                System.out.println("You dealt " + dmg + " damage!");

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    break;
                }
            }

            // -----------------------------
            // USE POTION
            // -----------------------------
            else if (choice.equals("2")) {
                boolean used = player.usePotion();
                if (!used) {
                    System.out.println("You have no potions!");
                }
            }

            // -----------------------------
            // RUN AWAY
            // -----------------------------
            else if (choice.equals("3")) {
                System.out.println("You ran away!");
                return;
            }

            // -----------------------------
            // ENEMY ATTACKS BACK
            // -----------------------------
            System.out.println(enemy.getName() + " attacks!");
            enemy.attackPlayer(player);

            if (player.getHealth() <= 0) {
                System.out.println("You were defeated...");
                return;
            }
        }
    }
}
