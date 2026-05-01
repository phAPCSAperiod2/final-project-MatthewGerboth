public class Enemy {

    private String name;
    private int health;
    private int maxHealth;
    private int attack;

    public Enemy() {
        int room = Room.getRoomCount();

        String[] names = {"Goblin", "Skeleton", "Zombie", "Bandit", "Wolf"};
        name = names[(int)(Math.random() * names.length)];

        int baseHealth = 10;
        int baseAttack = 2;

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

        if (room % 5 == 0) {
            name = "BOSS " + name;
            baseHealth *= 2;
            baseAttack *= 2;
        }

        maxHealth = baseHealth;
        health = maxHealth;
        attack = baseAttack;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getAttack() { return attack; }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void attackPlayer(Player player) {
        player.takeDamage(attack);
    }
}
