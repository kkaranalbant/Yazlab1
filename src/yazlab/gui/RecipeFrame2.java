/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author yunus
 */

package yazlab.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecipeFrame2 extends JFrame {
    private JButton addRecipeButton;
    private JButton removeRecipeButton;
    private JButton showRecipesButton;
    private JButton updateRecipesButton;

    public RecipeFrame2() {
        // Set up the frame
        setTitle("Recipe Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set the background panel with an image path
        BackgroundPanel backgroundPanel = new BackgroundPanel("rf2.jpeg"); // Replace with your image path
        backgroundPanel.setLayout(new GridBagLayout());

        // Create buttons
        addRecipeButton = createButton("Add Recipe");
        removeRecipeButton = createButton("Remove Recipe");
        showRecipesButton = createButton("Show Recipes");
        updateRecipesButton = createButton("Update Recipes");

        // Add action listeners
        addRecipeButton.addActionListener(e -> openAddRecipePanel());
        removeRecipeButton.addActionListener(e -> openRemoveRecipePanel());
        showRecipesButton.addActionListener(e -> openShowRecipesPanel());
        updateRecipesButton.addActionListener(e -> openUpdateRecipePanel());

        // Set layout for background panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0; // Column
        gbc.gridy = 0; // Row
        gbc.insets = new Insets(10, 10, 10, 10); // Padding
        gbc.weighty = 1; // Distribute space evenly

        // Add buttons to panel with GridBagLayout
        backgroundPanel.add(addRecipeButton, gbc);
        gbc.gridy++;
        backgroundPanel.add(removeRecipeButton, gbc);
        gbc.gridy++;
        backgroundPanel.add(showRecipesButton, gbc);
        gbc.gridy++;
        backgroundPanel.add(updateRecipesButton, gbc);

        // Add the background panel to the frame
        setContentPane(backgroundPanel);
    }

    // Method to create a button
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(60, 179, 113)); // SeaGreen color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 60));

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

    // Methods to open different panels
private void openAddRecipePanel() {
    // Create a JDialog for adding a recipe
    JDialog addRecipeDialog = new JDialog(this, "Add Recipe", true); // true for modal
    addRecipeDialog.setSize(600, 500); // Adjusted size
    addRecipeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    addRecipeDialog.setLocationRelativeTo(this); // Center the dialog

    // Create input fields for recipe information
    JLabel nameLabel = new JLabel("Recipe Name:");
    JTextField nameField = new JTextField(20);
    
    JLabel categoryLabel = new JLabel("Category:");
    String[] categories = {"Main Course", "Dessert", "Salad", "Appetizer", "Drink"};
    JComboBox<String> categoryComboBox = new JComboBox<>(categories);

    JLabel prepTimeLabel = new JLabel("Preparation Time (min):");
    JTextField prepTimeField = new JTextField(5);

    JLabel instructionsLabel = new JLabel("Instructions:");
    JTextArea instructionsArea = new JTextArea(5, 20);
    instructionsArea.setLineWrap(true);
    instructionsArea.setWrapStyleWord(true);
    JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

    // Ingredients section
    JLabel ingredientLabel = new JLabel("Select Ingredient:");
    String[] ingredients = {"Select...", "Tomato", "Flour", "Sugar"}; // Replace with actual ingredient fetching
    JComboBox<String> ingredientComboBox = new JComboBox<>(ingredients);
    JTextField quantityField = new JTextField(5);
    
    JButton addIngredientButton = new JButton("Add Ingredient");
    DefaultListModel<String> ingredientListModel = new DefaultListModel<>();
    JList<String> ingredientList = new JList<>(ingredientListModel);
    JScrollPane ingredientScrollPane = new JScrollPane(ingredientList);

    // Button to add new ingredient
    JButton newIngredientButton = new JButton("Add New Ingredient");
    
    // Action listener for new ingredient button
    newIngredientButton.addActionListener(e -> {
        String newIngredient = JOptionPane.showInputDialog(addRecipeDialog, "Enter new ingredient name:");
        if (newIngredient != null && !newIngredient.trim().isEmpty()) {
            // Add the new ingredient to the database and the combo box
            // ingredientService.addNewIngredient(newIngredient); // Example service call
            ingredientComboBox.addItem(newIngredient);
            JOptionPane.showMessageDialog(addRecipeDialog, "New ingredient added: " + newIngredient);
        }
    });

    // Action listener for add ingredient button
    addIngredientButton.addActionListener(e -> {
        String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
        String quantity = quantityField.getText();
        if (!"Select...".equals(selectedIngredient) && !quantity.trim().isEmpty()) {
            ingredientListModel.addElement(selectedIngredient + " - " + quantity + " units");
            quantityField.setText(""); // Clear quantity field
        } else {
            JOptionPane.showMessageDialog(addRecipeDialog, "Please select an ingredient and enter a quantity.");
        }
    });

    // Button to save the recipe
    JButton saveRecipeButton = new JButton("Save Recipe");
    saveRecipeButton.addActionListener(e -> {
        String name = nameField.getText();
        String category = (String) categoryComboBox.getSelectedItem();
        String prepTime = prepTimeField.getText();
        String instructions = instructionsArea.getText();

        // Example of validation
        if (name.isEmpty() || prepTime.isEmpty() || instructions.isEmpty() || ingredientListModel.isEmpty()) {
            JOptionPane.showMessageDialog(addRecipeDialog, "Please fill all fields and add at least one ingredient.");
            return;
        }

        // Implement the logic to save the recipe to the database
        // Recipe recipe = new Recipe(name, category, Integer.parseInt(prepTime), instructions);
        // recipeService.addRecipe(recipe, ingredientListModel); // Example service call
        JOptionPane.showMessageDialog(addRecipeDialog, "Recipe added successfully!");

        addRecipeDialog.dispose(); // Close the dialog
    });

    // Layout the components in the dialog
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); // Padding

    int row = 0;

    // Add components to the panel
    gbc.gridx = 0; gbc.gridy = row; panel.add(nameLabel, gbc);
    gbc.gridx = 1; panel.add(nameField, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(categoryLabel, gbc);
    gbc.gridx = 1; panel.add(categoryComboBox, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(prepTimeLabel, gbc);
    gbc.gridx = 1; panel.add(prepTimeField, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(instructionsLabel, gbc);
    gbc.gridx = 1; panel.add(instructionsScrollPane, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(ingredientLabel, gbc);
    gbc.gridx = 1; panel.add(ingredientComboBox, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(newIngredientButton, gbc);
    gbc.gridx = 1; panel.add(quantityField, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; panel.add(addIngredientButton, gbc);
    gbc.gridx = 1; panel.add(ingredientScrollPane, gbc);

    row++;
    gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 2; panel.add(saveRecipeButton, gbc);

    // Add the panel to the dialog
    addRecipeDialog.add(panel);
    
    // Set the dialog to be visible
    addRecipeDialog.setVisible(true);
}


private void openRemoveRecipePanel() {
    // Create a JDialog for removing a recipe
    JDialog removeDialog = new JDialog(this, "Remove Recipe", true); // true for modal
    removeDialog.setSize(400, 200); // Adjusted size for better visibility
    removeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    removeDialog.setLocationRelativeTo(this); // Center the dialog

    // Create a panel for the dialog
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    // Create and style the components
    JLabel nameLabel = new JLabel("Enter Recipe Name:");
    JTextField recipeNameField = new JTextField(20); // Wider text field for recipe name
    JButton removeButton = new JButton("Remove");

    // Use GridBagConstraints for layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); // Padding

    // Add components to the panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align label to the left
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Allow the text field to expand
    gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
    panel.add(recipeNameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2; // Make button span both columns
    gbc.fill = GridBagConstraints.NONE; // Reset fill for button
    panel.add(removeButton, gbc);

    // Action listener for the remove button
    removeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recipeName = recipeNameField.getText(); // Get the recipe name
            // Here you can add your logic to remove the recipe from your data structure
            if (!recipeName.isEmpty()) {
                // Assuming you have a method to remove the recipe, call it here
                // For example: recipeService.removeRecipe(recipeName);
                JOptionPane.showMessageDialog(removeDialog,
                    "Recipe \"" + recipeName + "\" has been removed.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                // Close the dialog after successful removal
                removeDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(removeDialog,
                    "Please enter a recipe name.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Add the panel to the dialog
    removeDialog.add(panel);
    removeDialog.setVisible(true); // Show the dialog
}


private void openShowRecipesPanel() {
    // Create a JDialog for showing recipes
    JDialog showDialog = new JDialog(this, "Show Recipes", true); // true for modal
    showDialog.setSize(800, 600); // Adjusted size
    showDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    showDialog.setLocationRelativeTo(this); // Center the dialog

    // Sample data: Replace with actual recipe data retrieval logic
    Object[][] recipesData = {
        {1, "Spaghetti Bolognese", "Main Course", 30, "Cook spaghetti, prepare sauce, combine.", 12.99, 200},
        {2, "Chocolate Cake", "Dessert", 60, "Mix ingredients, bake, frost.", 15.99, 150},
        {3, "Caesar Salad", "Salad", 15, "Chop ingredients, mix dressing, toss.", 9.99, 100}
    };

    // Column names
    String[] columnNames = {"TarifID", "TarifAdi", "Kategori", "HazirlamaSuresi (min)", "Talimatlar", "Price ($)", "Ingredient Amount (g)"};

    // Create a JTable to display recipes
    JTable recipeTable = new JTable(recipesData, columnNames);
    recipeTable.setFillsViewportHeight(true);
    
    // Add the table to a JScrollPane
    JScrollPane scrollPane = new JScrollPane(recipeTable);
    
    // Add sorting and filtering buttons
    JPanel buttonPanel = new JPanel();
    JButton sortByPriceButton = new JButton("Sort by Price");
    JButton sortByPrepTimeButton = new JButton("Sort by Preparation Time");
    JButton sortByIngredientAmountButton = new JButton("Sort by Ingredient Amount");
    JButton filterButton = new JButton("Filter");
    JButton categoryButton = new JButton("Category");

    // Sort by Price Action************************************** sorting için
    sortByPriceButton.addActionListener(e -> {
        recipeTable.repaint(); // Refresh table
    });

    // Sort by Preparation Time Action ******************************
    sortByPrepTimeButton.addActionListener(e -> {
        recipeTable.repaint(); // Refresh table
    });

    // Sort by Ingredient Amount Action *******************************
    sortByIngredientAmountButton.addActionListener(e -> {
        
        recipeTable.repaint(); // Refresh table
    });

    // Filter Action
    filterButton.addActionListener(e -> {
        // Implement filter logic
        // Example: open a dialog for filtering
        JOptionPane.showMessageDialog(showDialog, "Filter functionality to be implemented.");
    });

    // Category Button Action
    categoryButton.addActionListener(e -> {
        // Open a scrollable category selection window
        showCategorySelectionDialog();
    });

    // Add buttons to the button panel
    buttonPanel.add(sortByPriceButton);
    buttonPanel.add(sortByPrepTimeButton);
    buttonPanel.add(sortByIngredientAmountButton);
    buttonPanel.add(filterButton);
    buttonPanel.add(categoryButton);

    // Add components to the dialog
    showDialog.setLayout(new BorderLayout());
    showDialog.add(scrollPane, BorderLayout.CENTER);
    showDialog.add(buttonPanel, BorderLayout.SOUTH);
    
    // Add a close button at the bottom
    JButton closeButton = new JButton("Close");
    closeButton.addActionListener(e -> showDialog.dispose());
    
    JPanel closePanel = new JPanel();
    closePanel.add(closeButton);
    
    showDialog.add(closePanel, BorderLayout.NORTH);
    
    // Set the dialog to be visible
    showDialog.setVisible(true);
}

// Method to show category selection dialog
private void showCategorySelectionDialog() {
    // Create a JDialog for category selection
    JDialog categoryDialog = new JDialog(this, "Select Category", true); // true for modal
    categoryDialog.setSize(400, 300); // Adjusted size
    categoryDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    categoryDialog.setLocationRelativeTo(this); // Center the dialog

    // Sample category data
    String[] categories = {"Main Course", "Dessert", "Salad", "Appetizer", "Drink"};

    // Create a JList for categories
    JList<String> categoryList = new JList<>(categories);
    categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    JScrollPane categoryScrollPane = new JScrollPane(categoryList);
    
    // Add a select button
    JButton selectButton = new JButton("Select");
    selectButton.addActionListener(e -> {
        String selectedCategory = categoryList.getSelectedValue();
        if (selectedCategory != null) {
            // Implement logic to filter recipes by the selected category
            JOptionPane.showMessageDialog(categoryDialog, "Selected Category: " + selectedCategory);
            categoryDialog.dispose();
        } else {
            JOptionPane.showMessageDialog(categoryDialog, "Please select a category.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    });

    // Layout the dialog
    categoryDialog.setLayout(new BorderLayout());
    categoryDialog.add(categoryScrollPane, BorderLayout.CENTER);
    categoryDialog.add(selectButton, BorderLayout.SOUTH);
    
    // Show the dialog
    categoryDialog.setVisible(true);
}


private void openUpdateRecipePanel() {
    // Create a JDialog for updating a recipe
    JDialog updateDialog = new JDialog(this, "Update Recipe", true); // true for modal
    updateDialog.setSize(400, 200); // Adjusted size for better visibility
    updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    updateDialog.setLocationRelativeTo(this); // Center the dialog

    // Create a panel for the dialog
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());

    // Create and style the components
    JLabel nameLabel = new JLabel("Enter Recipe Name:");
    JTextField recipeNameField = new JTextField(20); // Wider text field for recipe name
    JButton updateButton = new JButton("Update");

    // Use GridBagConstraints for layout
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(10, 10, 10, 10); // Padding

    // Add components to the panel
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Align label to the left
    panel.add(nameLabel, gbc);

    gbc.gridx = 1;
    gbc.gridy = 0;
    gbc.weightx = 1.0; // Allow the text field to expand
    gbc.fill = GridBagConstraints.HORIZONTAL; // Fill horizontally
    panel.add(recipeNameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.gridwidth = 2; // Make button span both columns
    gbc.fill = GridBagConstraints.NONE; // Reset fill for button
    panel.add(updateButton, gbc);

    // Action listener for the update button
    updateButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String recipeName = recipeNameField.getText(); // Get the recipe name
            // Here you can add your logic to update the recipe
            if (!recipeName.isEmpty()) {
                // Assuming you have a method to check if the recipe exists and proceed with the update
                // For example: Recipe recipe = recipeService.findRecipe(recipeName);
                // if (recipe != null) { 
                //    // Open another dialog to update the recipe details
                // } else {
                //    JOptionPane.showMessageDialog(updateDialog, "Recipe not found.", "Error", JOptionPane.ERROR_MESSAGE);
                // }

                JOptionPane.showMessageDialog(updateDialog,
                    "You are now ready to update \"" + recipeName + "\".",
                    "Update Recipe",
                    JOptionPane.INFORMATION_MESSAGE);
                // Close the dialog after user confirmation
                updateDialog.dispose();
            } else {
                JOptionPane.showMessageDialog(updateDialog,
                    "Please enter a recipe name.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    });

    // Add the panel to the dialog
    updateDialog.add(panel);
    updateDialog.setVisible(true); // Show the dialog
}

    // Background Panel inner class
    private static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecipeFrame2 frame = new RecipeFrame2();
            frame.setVisible(true);
        });
    }
}
