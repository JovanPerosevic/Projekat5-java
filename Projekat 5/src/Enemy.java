public class Enemy extends GameObject implements Attack {
    private String type;
    private int damage;
    private int health;

    public Enemy(int x, int y, Collidable collidable, String type, int damage, int health) {
        super(x, y, collidable);
        setType(type);
        setDamage(damage);
        setHealth(health);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }

        type = type.trim();

        if (type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be empty");
        }

        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        if (damage < 0 || damage > 100) {
            throw new IllegalArgumentException("Damage must be between 0 and 100");
        }
        this.damage = damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0 || health > 100) {
            throw new IllegalArgumentException("Health must be between 0 and 100");
        }
        this.health = health;
    }

    @Override
    public int getEffectiveDamage() {
        return damage;
    }

    @Override
    public String getDisplayName() {
        return type;
    }

    @Override
    public String toString() {
        String colliderInfo;
        if (getCollidable() instanceof RectangleCollider) {
            RectangleCollider rect = (RectangleCollider) getCollidable();
            colliderInfo = rect.getWidth() + "x" + rect.getHeight();
        } else if (getCollidable() instanceof CircleCollider) {
            CircleCollider circle = (CircleCollider) getCollidable();
            colliderInfo = "R=" + circle.getRadius();
        } else {
            colliderInfo = "unknown";
        }

        return String.format("Enemy[%s] @ (%d,%d) %s DMG=%d HP=%d",
                             type, getX(), getY(), colliderInfo, damage, health);
    }
}