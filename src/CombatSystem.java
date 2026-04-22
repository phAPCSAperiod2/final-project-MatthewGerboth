import java.util.Scanner;

import items.Potion;

public class CombatSystem {

    public static void startCombat(Player player) {

        Enemy enemy = new Enemy();
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nA " + enemy.getName() + " appears!");
        System.out.println("Enemy HP: " + enemy.getHealth());

        while (enemy.isAlive() && player.getHealth() > 0) {

            System.out.println("\n--- Your Turn ---");
            System.out.println("1) Attack");
            System.out.println("2) Use Potion");
            System.out.print("Choose: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            // PLAYER ATTACK
            if (choice == 1) {
                int dmg = player.getAttackDamage();
                System.out.println("You strike for " + dmg + " damage!");
                enemy.takeDamage(dmg);

                if (!enemy.isAlive()) {
                    System.out.println("You defeated the " + enemy.getName() + "!");
                    break;
                }
            }

            // PLAYER HEAL
            else if (choice == 2) {
                Potion p = player.getFirstPotion();

                if (p == null) {
                    System.out.println("You have no potions!");
                } else {
                    player.heal(p.getHealAmount());
                    player.removePotion(p);
                    System.out.println("You healed for " + p.getHealAmount() + " HP!");
                }
            }

            // ENEMY TURN
            System.out.println("\n--- Enemy Turn ---");
            int enemyDmg = enemy.attackPlayer();

            if (player.getEquippedShield() != null) {
                enemyDmg -= player.getEquippedShield().getDefense();
                if (enemyDmg < 0) enemyDmg = 0;
            }

            System.out.println(enemy.getName() + " attacks for " + enemyDmg + " damage!");
            player.takeDamage(enemyDmg);

            if (player.getHealth() <= 0) {
                System.out.println("You were defeated...");
                return;
            }

            System.out.println("Your HP: " + player.getHealth());
            System.out.println("Enemy HP: " + enemy.getHealth());
        }
    }
}
