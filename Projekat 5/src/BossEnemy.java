public class BossEnemy extends Enemy {

    public BossEnemy(int x, int y, Collidable collidable, String type, int damage, int health) {
        super(x, y, collidable, type, damage, health);
    }

    @Override
    public int getEffectiveDamage() {
        return getDamage() * 2;
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

        return String.format("BossEnemy[%s] @ (%d,%d) %s DMG=%dx2 HP=%d",
                             getType(), getX(), getY(), colliderInfo, getDamage(), getHealth());
    }
}