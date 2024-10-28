/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import yazlab.model.Ingredient;
import yazlab.model.Unit;
import yazlab.request.IngredientAddingRequest;
import yazlab.service.IngredientService;
import yazlab.exception.IngredientException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.util.List;
import java.awt.*;
import javax.swing.*;


public class IngredientPanel extends JFrame {
    private IngredientService ingredientService;

    public IngredientPanel() {
        ingredientService = IngredientService.getInstance();
        setTitle("Ingredient Panel");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Start in maximized mode

        // Set the background panel with an image path
        BackgroundPanel backgroundPanel = new BackgroundPanel("ingm2.jpeg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Create buttons
        JButton addButton = createStyledButton("Add Ingredient");
        JButton removeButton = createStyledButton("Remove Ingredient");
        JButton updateButton = createStyledButton("Update Ingredient");
        JButton showButton = createStyledButton("Show Ingredients");

        // Set layout for background panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        backgroundPanel.add(addButton, gbc);

        gbc.gridy++;
        backgroundPanel.add(removeButton, gbc);

        gbc.gridy++;
        backgroundPanel.add(updateButton, gbc);

        gbc.gridy++;
        backgroundPanel.add(showButton, gbc);

        // Add action listeners
        addButton.addActionListener(e -> openAddIngredientPanel());
        removeButton.addActionListener(e -> removeIngredient());
        showButton.addActionListener(e -> showIngredients());
        updateButton.addActionListener(e -> updateIngredient());

        // Add the background panel to the frame
        setContentPane(backgroundPanel);
        setVisible(true);
    }

    // Method to create a styled button
    // Method to create a styled button
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16)); // Increased font size
        button.setBackground(new Color(60, 179, 113)); // SeaGreen color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Increased padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Set preferred size (width, height)
        button.setPreferredSize(new Dimension(200, 60)); // Adjust these values as needed

        // Add mouse listener for hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 150, 80)); // Darker shade on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 179, 113)); // Reset to original color
            }
        });

        return button;
    }


    private void openAddIngredientPanel() {
        JFrame addFrame = new JFrame("Add Ingredient");
        addFrame.setSize(400, 300); // Adjusted size
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set the background panel with an image path
        BackgroundPanel backgroundPanel = new BackgroundPanel("ingb1.jpg");
        backgroundPanel.setLayout(new GridBagLayout());

        // Create and style the components
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(15); // Specify columns for width
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(15);
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox<String> unitComboBox = new JComboBox<>(Unit.getNames());
        JLabel unitPriceLabel = new JLabel("Unit Price:");
        JTextField unitPriceField = new JTextField(15);

        JButton addIngredientButton = new JButton("Add Ingredient"); // Reusing your styled button method

        // Use GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        backgroundPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        backgroundPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        backgroundPanel.add(unitLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(unitComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        backgroundPanel.add(unitPriceLabel, gbc);

        gbc.gridx = 1;
        backgroundPanel.add(unitPriceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Make button span both columns
        backgroundPanel.add(addIngredientButton, gbc);

        // Action listener for the button
        addIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    Float amount = Float.parseFloat(amountField.getText());
                    String unitString = (String) unitComboBox.getSelectedItem();
                    Unit unit = Unit.valueOf(unitString);
                    Float unitPrice = Float.parseFloat(unitPriceField.getText());

                    IngredientAddingRequest ingredientAddingRequest = new IngredientAddingRequest(name, amount, unit, unitPrice);
                    ingredientService.addIngredient(ingredientAddingRequest);
                } catch (SQLException | IngredientException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        addFrame.setContentPane(backgroundPanel); // Set the background panel as content
        addFrame.setVisible(true);
    }


    private void removeIngredient() {
        String ingredientName = JOptionPane.showInputDialog("Enter the name of the ingredient to remove:");
        if (ingredientName != null && !ingredientName.isEmpty()) {
            try {
                ingredientService.removeIngredientByName(ingredientName);
            } catch (SQLException | IngredientException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private void showIngredients() {
        try {
            List<Ingredient> ingredients = ingredientService.getAll(); // Fetch all ingredients
            openShowIngredientsPanel(ingredients); // Open the ingredients panel
        } catch (SQLException | IngredientException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
    }

    private void updateIngredient() {
        String ingredientName = JOptionPane.showInputDialog("Enter the name of the ingredient to update:");
        if (ingredientName != null && !ingredientName.isEmpty()) {
            try {
                Ingredient ingredient = ingredientService.getByName(ingredientName);
                openUpdateIngredientPanel(ingredient);
            } catch (SQLException | IngredientException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        }
    }

    private void openUpdateIngredientPanel(Ingredient ingredient) {
        JFrame updateFrame = new JFrame("Update Ingredient");
        updateFrame.setSize(300, 200);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(ingredient.getName());
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField(ingredient.getAmount().toString());
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox<String> unitComboBox = new JComboBox<>(Unit.getNames());
        unitComboBox.setSelectedItem(ingredient.getUnit().name());
        JLabel unitPriceLabel = new JLabel("Unit Price:");
        JTextField unitPriceField = new JTextField(ingredient.getUnitPrice().toString());

        JButton updateIngredientButton = new JButton("Update Ingredient");

        updateFrame.add(nameLabel);
        updateFrame.add(nameField);
        updateFrame.add(amountLabel);
        updateFrame.add(amountField);
        updateFrame.add(unitLabel);
        updateFrame.add(unitComboBox);
        updateFrame.add(unitPriceLabel);
        updateFrame.add(unitPriceField);
        updateFrame.add(new JLabel());
        updateFrame.add(updateIngredientButton);

        updateIngredientButton.addActionListener(e -> {
            try {
                String name = nameField.getText();
                Float amount = Float.parseFloat(amountField.getText());
                String unitName = (String) unitComboBox.getSelectedItem();
                Unit unit = Unit.valueOf(unitName);
                Float unitPrice = Float.parseFloat(unitPriceField.getText());
                IngredientAddingRequest ingredientUpdatingRequest = new IngredientAddingRequest(name, amount, unit, unitPrice);
                ingredientService.updateIngredient(ingredientUpdatingRequest, ingredient.getId());
                updateFrame.dispose(); // Close the update frame
            } catch (SQLException | IngredientException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        updateFrame.setVisible(true);
    }

    private void openShowIngredientsPanel(List<Ingredient> ingredients) {
        JFrame showFrame = new JFrame("Show Ingredients");
        showFrame.setSize(400, 200);
        showFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        showFrame.setLayout(new GridLayout(3, 2));

        JLabel comboBoxLabel = new JLabel("Select Ingredient:");
        JComboBox<Ingredient> ingredientComboBox = new JComboBox<>();
        for (Ingredient ingredient : ingredients) {
            ingredientComboBox.addItem(ingredient);
        }

        JLabel detailsLabel = new JLabel("Ingredient Details:");
        JTextField detailsField = new JTextField();

        ingredientComboBox.addActionListener(e -> {
            Ingredient ingredient = (Ingredient) ingredientComboBox.getSelectedItem();
            if (ingredient != null) {
                StringBuilder sb = new StringBuilder();
                sb.append("Name: ").append(ingredient.getName()).append("\n")
                        .append("Amount: ").append(ingredient.getAmount()).append("\n")
                        .append("Unit Type: ").append(ingredient.getUnit().name()).append("\n")
                        .append("Unit Price: ").append(ingredient.getUnitPrice()).append("\n");
                detailsField.setText(sb.toString());
            }
        });

        showFrame.add(comboBoxLabel);
        showFrame.add(ingredientComboBox);
        showFrame.add(detailsLabel);
        showFrame.add(detailsField);
        showFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new IngredientPanel();
    }

    public class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        // Constructor
        public BackgroundPanel(String imagePath) {
            // Load the background image
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the background image to fill the panel
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }
}
