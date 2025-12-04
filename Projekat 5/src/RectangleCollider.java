public class RectangleCollider implements Collidable {
    private int x;
    private int y;
    private int width;
    private int height;

    public RectangleCollider(int x, int y, int width, int height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    @Override
    public boolean intersects(Collidable other) {
        if (other instanceof RectangleCollider) {
            RectangleCollider rect = (RectangleCollider) other;
            return this.x < rect.x + rect.width &&
                   this.x + this.width > rect.x &&
                   this.y < rect.y + rect.height &&
                   this.y + this.height > rect.y;
        } else if (other instanceof CircleCollider) {
            CircleCollider circle = (CircleCollider) other;
            return circle.intersects(this);
        }
        return false;
    }
}