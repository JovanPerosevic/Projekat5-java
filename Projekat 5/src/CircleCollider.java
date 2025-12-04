public class CircleCollider implements Collidable {
    private int centerX;
    private int centerY;
    private int radius;

    public CircleCollider(int centerX, int centerY, int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be greater than 0");
        }
        this.radius = radius;
    }

    @Override
    public boolean intersects(Collidable other) {
        if (other instanceof CircleCollider) {
            CircleCollider circle = (CircleCollider) other;
            int dx = this.centerX - circle.centerX;
            int dy = this.centerY - circle.centerY;
            int distanceSquared = dx * dx + dy * dy;
            int radiusSum = this.radius + circle.radius;
            return distanceSquared <= radiusSum * radiusSum;
        } else if (other instanceof RectangleCollider) {
            RectangleCollider rect = (RectangleCollider) other;

            // Find the closest point on the rectangle to the circle's center
            int closestX = Math.max(rect.getX(), Math.min(this.centerX, rect.getX() + rect.getWidth()));
            int closestY = Math.max(rect.getY(), Math.min(this.centerY, rect.getY() + rect.getHeight()));

            // Calculate the distance between the circle's center and this closest point
            int dx = this.centerX - closestX;
            int dy = this.centerY - closestY;
            int distanceSquared = dx * dx + dy * dy;

            return distanceSquared <= this.radius * this.radius;
        }
        return false;
    }
}