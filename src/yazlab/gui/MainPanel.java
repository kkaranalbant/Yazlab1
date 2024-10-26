package yazlab.gui;

import javax.swing.*;
import javax.swing.border.Border; // Import the Border class
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame {

    private JPanel panel;

    public MainPanel() {
        // Set window title
        setTitle("Tarifler");

        // Set window size
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Create panel with custom background
        panel = new BackgroundPanel("/home/yunus/NetBeansProjects/Yazlab1/bg3.jpg");
        panel.setLayout(new GridLayout(4, 1, 10, 10));

        // Create buttons with icons
        RoundedButton addRecipeButton = createButton("Recipe Menu", "/home/yunus/NetBeansProjects/Yazlab1/rcp11.png");
        RoundedButton addIngredientButton = createButton("Ingredient Menu", "/home/yunus/NetBeansProjects/Yazlab1/ing1.png");

        // Add action listeners
        addRecipeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Add Recipe Button Clicked");
            }
        });

        addIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Add Ingredient Button Clicked");
            }
        });

        // Add buttons to panel
        panel.add(addRecipeButton);
        panel.add(addIngredientButton);

        // Add panel to window
        add(panel);
    }

    private RoundedButton createButton(String text, String iconPath) {
        RoundedButton button = new RoundedButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(255, 255, 255, 200)); // White with transparency

        // Set icon if path is valid
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            button.setIcon(icon);
        }

        button.setToolTipText("Click to open " + text);
        return button;
    }

    public static void main(String[] args) {
        // Launch GUI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPanel frame = new MainPanel();
                frame.setVisible(true);
            }
        });
    }

    // Inner class for the background panel
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            // Load the background image
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    // Custom button class with rounded corners
    class RoundedButton extends JButton {
        private static final int RADIUS = 20; // Radius for the rounded corners

        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false); // Remove default button filling
            setFocusPainted(false); // Remove focus border
            setBorderPainted(false); // Remove border
        }

        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                g.setColor(getBackground().darker()); // Darker color when pressed
            } else {
                g.setColor(getBackground());
            }

            g.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS); // Draw rounded rectangle

            super.paintComponent(g); // Draw button text and icon
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(150, 50); // Set preferred button size
        }
    }
}
