import javax.swing.*;
import java.awt.*;

public class GameGUI extends JFrame {

    private JTextField nameField, healthField, xField, yField;
    private JRadioButton rectOption, circleOption;
    private JTextArea outputArea;

    public GameGUI() {
        setTitle("Igra - Unos Igrača");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        nameField = addField(inputPanel, "Ime igrača:");
        healthField = addField(inputPanel, "Health (0-100):");
        xField = addField(inputPanel, "Pozicija X:");
        yField = addField(inputPanel, "Pozicija Y:");

        rectOption = new JRadioButton("Pravougaonik", true);
        circleOption = new JRadioButton("Krug");

        ButtonGroup group = new ButtonGroup();
        group.add(rectOption);
        group.add(circleOption);

        inputPanel.add(new JLabel("Collider tip:"));
        inputPanel.add(rectOption);
        inputPanel.add(circleOption);

        JButton startButton = new JButton("Pokreni igru");
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        startButton.addActionListener(e -> startGame());

        setVisible(true);
    }

    private JTextField addField(JPanel panel, String label) {
        panel.add(new JLabel(label));
        JTextField field = new JTextField();
        panel.add(field);
        return field;
    }

    private void startGame() {
        try {
            String name = nameField.getText().trim();
            int health = Integer.parseInt(healthField.getText().trim());
            int x = Integer.parseInt(xField.getText().trim());
            int y = Integer.parseInt(yField.getText().trim());

            Collidable collider;
            if (rectOption.isSelected())
                collider = new RectangleCollider(x, y, 32, 32);
            else
                collider = new CircleCollider(x, y, 16);

            Player p = new Player(x, y, collider, name, health);

            Game game = new Game(p);
            game.loadEnemiesFromCSV("enemies.csv");

            game.resolveCollisions();

            outputArea.setText("");
            outputArea.append("PLAYER:\n" + p + "\n\n");

            outputArea.append("ENEMIES:\n");
            for (Enemy e : game.enemies)
                outputArea.append(e + "\n");

            outputArea.append("\nKOLIZIJE:\n");
            for (Enemy e : game.collidingWithPlayer())
                outputArea.append("- " + e.getDisplayName() + "\n");

            outputArea.append("\nLOG:\n");
            for (String l : game.log)
                outputArea.append(l + "\n");

            if (p.getHealth() <= 0)
                JOptionPane.showMessageDialog(this, "Igrač je poražen!");
            else
                JOptionPane.showMessageDialog(this, "Igra je gotova.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Greška: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new GameGUI();
    }
}
