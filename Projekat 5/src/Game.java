import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private Player player;
    public ArrayList<Enemy> enemies;
    public ArrayList<String> log;

    public Game(Player player) {
        this.player = player;
        this.enemies = new ArrayList<>();
        this.log = new ArrayList<>();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<String> getEventLog() {
        return log;
    }

    // ===================== COLLSION LOGIC =======================

    public boolean checkCollision(Player p, Enemy e) {
        return p.intersects(e);
    }

    public void decreaseHealth(Player p, Enemy e) {
        int damage = e.getEffectiveDamage();
        int old = p.getHealth();
        p.takeDamage(damage);

        log.add("Player hit by " + e.getType() +
                " for " + damage + " HP (" + old + " → " + p.getHealth() + ")");
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
        log.add("Enemy added: " + e.getDisplayName());
    }

    public List<Enemy> findByType(String query) {
        ArrayList<Enemy> result = new ArrayList<>();
        for (Enemy e : enemies) {
            if (e.getType().toLowerCase().contains(query.toLowerCase()))
                result.add(e);
        }
        return result;
    }

    public List<Enemy> collidingWithPlayer() {
        ArrayList<Enemy> result = new ArrayList<>();
        for (Enemy e : enemies) {
            if (checkCollision(player, e))
                result.add(e);
        }
        return result;
    }

    public void resolveCollisions() {
        List<Enemy> hits = collidingWithPlayer();
        if (hits.isEmpty()) {
            log.add("No collisions.");
            return;
        }

        for (Enemy e : hits) {
            log.add("Collision with: " + e.getDisplayName());
            decreaseHealth(player, e);
        }
    }

    // ===================== CSV PARSING =======================

    public static ArrayList<Enemy> loadEnemiesFromCSV(String filePath) {
        ArrayList<Enemy> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty())
                    continue;

                String[] p = line.split(",");

                if (p.length < 9)
                    throw new IllegalArgumentException("Invalid CSV line: " + line);

                String type = p[0].trim();
                String cls = p[1].trim();
                int x = Integer.parseInt(p[2].trim());
                int y = Integer.parseInt(p[3].trim());
                String collider = p[4].trim();

                Collidable col;

                if (collider.equalsIgnoreCase("rectangle")) {
                    int w = Integer.parseInt(p[5].trim());
                    int h = Integer.parseInt(p[6].trim());
                    col = new RectangleCollider(x, y, w, h);
                } else {
                    int r = Integer.parseInt(p[5].trim());
                    col = new CircleCollider(x, y, r);
                }

                int damage = Integer.parseInt(p[7].trim());
                int health = Integer.parseInt(p[8].trim());

                Enemy e;

                if (cls.equalsIgnoreCase("boss")) {
                    e = new BossEnemy(type, x, y, col, damage, health);
                } else {
                    e = new MeleeEnemy(type, x, y, col, damage, health);
                }

                list.add(e);
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("CSV read error: " + e.getMessage());
        }
        return list;
    }
}
