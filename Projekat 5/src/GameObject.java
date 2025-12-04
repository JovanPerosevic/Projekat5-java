public abstract class GameObject {
    protected int x;
    protected int y;
    protected Collidable collidable;

    public GameObject(int x, int y, Collidable collidable) {
        this.x = x;
        this.y = y;
        this.collidable = collidable;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Collidable getCollidable() {
        return collidable;
    }

    public void setCollidable(Collidable collidable) {
        this.collidable = collidable;
    }

    public boolean intersects(GameObject other) {
        if (this.collidable == null || other.collidable == null) {
            return false;
        }
        return this.collidable.intersects(other.collidable);
    }

    public abstract String getDisplayName();
}