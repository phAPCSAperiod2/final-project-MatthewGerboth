/**
 * Represents an enemy that the player can encounter in a room.
 * Enemy stats scale based on the number of rooms the player has entered,
 * making the game progressively more difficult.
 *
 * <p>Every 5th room generates a boss enemy with doubled stats.</p>
 */
public class Enemy {

    /** The enemy's name (may include a "BOSS" prefix). */
    private String name;

    /** The current health of the enemy. */
    private int health;

    /** The maximum health of the enemy. */
    private int maxHealth;

    /** The attack damage the enemy deals to the player. */
    private int attack;

    /**
     * Constructs a new Enemy whose stats scale based on the current room count.
     * Difficulty increases in stages, and every 5th room produces a boss enemy.
     */
    public Enemy() {
        int room = Room.getRoomCount();

        // Random base name
        String[] names = {"Goblin", "Skeleton", "Zombie", "Bandit", "Wolf"};
        name = names[(int)(Math.random() * names.length)];

        int baseHealth = 10;
        int baseAttack = 2;

        // Difficulty scaling by room range
        if (room <= 3) {
            baseHealth = 10 + (int)(Math.random() * 6);
            baseAttack = 2 + (int)(Math.random() * 2);
        }
        else if (room <= 8) {
            baseHealth = 20 + (int)(Math.random() * 11);
            baseAttack = 4 + (int)(Math.random() * 3);
        }
        else if (room <= 15) {
            baseHealth = 35 + (int)(Math.random() * 16);
            baseAttack = 6 + (int)(Math.random() * 4);
        }
        else {
            baseHealth = 50 + (int)(Math.random() * 26);
            baseAttack = 8 + (int)(Math.random() * 5);
        }

        // Boss every 5 rooms
        if (room % 5 == 0) {
            name = "BOSS " + name;
            baseHealth *= 2;
            baseAttack *= 2;
        }

        maxHealth = baseHealth;
        health = maxHealth;
        attack = baseAttack;
    }

    /** @return the enemy's name */
    public String getName() { return name; }

    /** @return the enemy's current health */
    public int getHealth() { return health; }

    /** @return the enemy's maximum health */
    public int getMaxHealth() { return maxHealth; }

    /** @return the enemy's attack damage */
    public int getAttack() { return attack; }

    /**
     * Reduces the enemy's health by the specified damage amount.
     * Health cannot drop below zero.
     *
     * @param dmg the amount of damage taken
     */
    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    /**
     * Checks whether the enemy is still alive.
     *
     * @return true if health is above zero, false otherwise
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Performs an attack on the player, dealing this enemy's attack value.
     *
     * @param player the player being attacked
     */
    public void attackPlayer(Player player) {
        player.takeDamage(attack);
    }
}
