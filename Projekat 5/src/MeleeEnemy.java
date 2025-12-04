public class MeleeEnemy extends Enemy {

    public MeleeEnemy(int x, int y, Collidable collidable, String type, int damage, int health) {
        super(x, y, collidable, type, damage, health);
    }

    // MeleeEnemy uses the base getEffectiveDamage() implementation from Enemy
    // which simply returns damage value
}