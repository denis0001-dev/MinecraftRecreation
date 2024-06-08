package net.minecraftrecreation.world.entity;

public class Player {
    public static final int ACTION_COOLDOWN = 20;

    private int BlockPlaceCooldown = -1;
    private int BlockBreakCooldown = -1;

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

    public boolean isAlive() {
        return alive;
    }

    public void damage(double damage) {
        setHealth(health - damage);
    }

    public void heal(double heal) {
        setHealth(health + heal);
    }

    public void kill() {
        setHealth(0);
    }

    public void update() {
        // Block placement cooldown
        if (BlockPlaceCooldown != -1) BlockPlaceCooldown--;
        if (BlockPlaceCooldown == 0) BlockPlaceCooldown = -1;

        // Block break cooldown
        if (BlockBreakCooldown != -1) BlockBreakCooldown--;
        if (BlockBreakCooldown == 0) BlockBreakCooldown = -1;
    }

    public boolean blockPlaceCooldownActive() {
        return BlockPlaceCooldown != -1;
    }

    public void blockPlaceCooldown() {
        BlockPlaceCooldown += ACTION_COOLDOWN;
    }

    public boolean blockBreakCooldownActive() {
        return BlockBreakCooldown != -1;
    }

    public void blockBreakCooldown() {
        BlockBreakCooldown += ACTION_COOLDOWN;
    }

    public int getBlockPlaceCooldown() {
        return BlockPlaceCooldown;
    }

    public int getBlockBreakCooldown() {
        return BlockBreakCooldown;
    }
}
