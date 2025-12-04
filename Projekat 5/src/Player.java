public class Player extends GameObject {
    private String name;
    private int health;

    public Player(int x, int y, Collidable collidable, String name, int health) {
        super(x, y, collidable);
        setName(name);
        setHealth(health);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }

        // Trim and remove multiple spaces
        name = name.trim().replaceAll("\\s+", " ");

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        // Capitalize first letter of each word
        String[] words = name.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) {
                capitalized.append(" ");
            }
            String word = words[i];
            if (word.length() > 0) {
                capitalized.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    capitalized.append(word.substring(1).toLowerCase());
                }
            }
        }

        this.name = capitalized.toString();
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

    public void takeDamage(int damage) {
        this.health -= damage;
        if (this.health < 0) {
            this.health = 0;
        }
    }

    @Override
    public String getDisplayName() {
        return name;
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

        return String.format("Player[%s] @ (%d,%d) %s HP=%d",
                             name, getX(), getY(), colliderInfo, health);
    }
}