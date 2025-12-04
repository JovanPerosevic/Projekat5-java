/**
 * Projekat 5 uzeo sam od proslog projekta kod
 * - Jovan Perosevic 24/0047
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("    GAME COLLISION SYSTEM TEST");
        System.out.println("========================================\n");

        RectangleCollider playerCollider = new RectangleCollider(10, 5, 32, 32);
        Player player = new Player(10, 5, playerCollider, " peTar petrović ", 85);

        System.out.println("Player created:");
        System.out.println(player);
        System.out.println();

        Game game = new Game(player);

        System.out.println("Adding first enemy manually...");
        RectangleCollider goblinCollider = new RectangleCollider(12, 5, 16, 16);
        MeleeEnemy goblin = new MeleeEnemy(12, 5, goblinCollider, "Goblin", 20, 60);
        game.addEnemy(goblin);
        System.out.println("Added: " + goblin);
        System.out.println();

        System.out.println("Adding second enemy via parsing...");
        String enemyString = "Goblin King;15,10;R20;25;boss;100";
        try {
            Enemy parsedEnemy = Game.parseEnemy(enemyString);
            game.addEnemy(parsedEnemy);
            System.out.println("Added: " + parsedEnemy);
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing enemy: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Adding third enemy (not colliding)...");
        CircleCollider orcCollider = new CircleCollider(100, 100, 15);
        Enemy orc = new Enemy(100, 100, orcCollider, "Orc", 15, 50);
        game.addEnemy(orc);
        System.out.println("Added: " + orc);
        System.out.println();

        System.out.println("Adding fourth enemy via user input (parsing)...");
        System.out.println("Expected format: type;x,y;collider;damage;enemyType;health");
        System.out.println("  - collider: 'widthxheight' (e.g., '20x20') or 'Rradius' (e.g., 'R10')");
        System.out.println("  - enemyType: 'boss', 'melee', or a number for health");
        System.out.println("Example: 'Troll;25,30;24x24;35;melee;80'");
        System.out.println();

        java.util.Scanner scanner = new java.util.Scanner(System.in);
        System.out.print("Enter enemy string: ");
        String userInput = scanner.nextLine();

        try {
            Enemy userEnemy = Game.parseEnemy(userInput);
            game.addEnemy(userEnemy);
            System.out.println("Successfully added: " + userEnemy);
        } catch (IllegalArgumentException e) {
            System.out.println("Error parsing enemy: " + e.getMessage());
        }
        System.out.println();

        System.out.println("========================================");
        game.printGameState();
        System.out.println();

        System.out.println("========================================");
        System.out.println("Searching for enemies containing 'gob':");
        java.util.List<Enemy> foundEnemies = game.findByType("gob");
        if (foundEnemies.isEmpty()) {
            System.out.println("No enemies found");
        } else {
            for (Enemy e : foundEnemies) {
                System.out.println("  - " + e);
            }
        }
        System.out.println();

        System.out.println("========================================");
        System.out.println("Checking collisions with player:");
        java.util.List<Enemy> collidingEnemies = game.collidingWithPlayer();
        if (collidingEnemies.isEmpty()) {
            System.out.println("No enemies colliding with player");
        } else {
            System.out.println("Enemies colliding with player:");
            for (Enemy e : collidingEnemies) {
                System.out.println("  - " + e);
            }
        }
        System.out.println();

        System.out.println("========================================");
        System.out.println("BEFORE resolveCollisions():");
        System.out.println(player);
        System.out.println();

        System.out.println("Resolving collisions...");
        game.resolveCollisions();
        System.out.println();

        System.out.println("AFTER resolveCollisions():");
        System.out.println(player);
        System.out.println();

        System.out.println("========================================");
        game.printEventLog();
        System.out.println();

        System.out.println("========================================");
        System.out.println("FINAL GAME STATE:");
        game.printGameState();
        System.out.println();

        System.out.println("========================================");
        System.out.println("TESTING EXCEPTION HANDLING:");
        System.out.println();

        System.out.println("Test 1: Parsing empty string");
        try {
            Game.parseEnemy("");
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Test 2: Invalid format (missing parts)");
        try {
            Game.parseEnemy("Goblin;12,5");
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Test 3: Invalid number format");
        try {
            Game.parseEnemy("Goblin;abc,def;16x16;20;melee");
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Test 4: Negative collider dimension");
        try {
            new RectangleCollider(0, 0, -10, 20);
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Test 5: Empty player name");
        try {
            Player testPlayer = new Player(0, 0, playerCollider, "   ", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Test 6: Health out of range");
        try {
            Player testPlayer = new Player(0, 0, playerCollider, "Test", 150);
        } catch (IllegalArgumentException e) {
            System.out.println("  Exception caught: " + e.getMessage());
        }
        System.out.println();

        System.out.println("========================================");
        System.out.println("    TEST COMPLETED SUCCESSFULLY!");
        System.out.println("========================================");
    }
}