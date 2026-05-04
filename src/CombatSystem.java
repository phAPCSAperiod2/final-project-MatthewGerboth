import java.util.Scanner;

public class CombatSystem {

    public static void startCombat(Player player) {

        Scanner scanner = new Scanner(System.in);
        Enemy enemy = new Enemy();

        System.out.println("\nA " + enemy.getName() + " appears!");
        System.out.println("Enemy HP: " + enemy.getHealth());

        while (player.getHealth() > 0 && enemy.isAlive()) {

            System.out.println("\nYour HP: " + player.getHealth() + "/" + player.getMaxHealth());
            System.out.println(enemy.getName() + " HP: " + enemy.getHealth() + "/" + enemy.getMaxHealth());

            System.out.println("\nChoose an action:");
            System.out.println("1. Attack");
            System.out.println("2. Use Potion");
            System.out.println("3. Inventory");
            System.out.println("4. Run");

            String choice = scanner.nextLine();

            boolean playerTookTurn = false;

            if (choice.equals("1")) {
                int dmg = player.attackEnemy(enemy);
                System.out.println("You dealt " + dmg + " damage!");

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    break;
                }

                playerTookTurn = true;
            }


            else if (choice.equals("2")) {
                boolean used = player.usePotion();
                if (!used) {
                    System.out.println("You have no potions!");
                }
                continue;
            }


            else if (choice.equals("3")) {
                player.openInventoryMenu();
                continue;
            }


            else if (choice.equals("4")) {
                System.out.println("You ran away!");
                return;
            }


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
