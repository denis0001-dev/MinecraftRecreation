package net.minecraftrecreation.world.entity;

public class Player {
    private boolean alive = true;

    private final String name;
    private double maxHealth = 20;
    private double health = 20;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setMaxHealth(double maxHealth) {
        if (maxHealth < health) {
            setHealth(maxHealth);
        }
        this.maxHealth = maxHealth;
    }

    public void setHealth(double health) {
        if (health <= 0) {
            alive = false;
        }
        this.health = health;
    }
}
