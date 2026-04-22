public class Enemy {

    private String name;
    private int health;
    private int maxHealth;
    private int attack;

    public Enemy() {
        String[] names = {"Goblin", "Skeleton", "Zombie", "Bandit", "Wolf"};
        name = names[(int)(Math.random() * names.length)];

        maxHealth = (int)(Math.random() * 21) + 20; // 20–40 HP
        health = maxHealth;

        attack = (int)(Math.random() * 6) + 5; // 5–10 attack
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int dmg) {
        health -= dmg;
        if (health < 0) health = 0;
    }

    public int attackPlayer() {
        return attack;
    }
}
