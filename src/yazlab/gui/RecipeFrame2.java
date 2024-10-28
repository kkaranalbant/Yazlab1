/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author yunus
 */
package yazlab.gui;

//import javax.swing.*;
//import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import yazlab.model.Category;
import yazlab.model.Ingredient;
import yazlab.service.IngredientService;
import yazlab.service.RecipeIngredientService;
import yazlab.service.RecipeService;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import yazlab.exception.IngredientException;
import yazlab.exception.RecipeException;
import yazlab.exception.RecipeIngredientException;
import yazlab.model.Recipe;
import yazlab.model.RecipeIngredient;
import yazlab.model.Unit;
import yazlab.request.FilterRequest;
import yazlab.request.RecipeAddingRequest;
import yazlab.request.RecipeIngredientAddingRequest;
import yazlab.request.RecipeIngredientAddingRequestGui;
import yazlab.service.UnitAndPriceConversation;

public class RecipeFrame2 extends JFrame {

    private JButton addRecipeButton;
    private JButton removeRecipeButton;
    private JButton showRecipesButton;
    private JButton updateRecipesButton;

    private IngredientService ingredientService;
    private RecipeService recipeService;
    private RecipeIngredientService recipeIngredientService;

    public RecipeFrame2() {

        ingredientService = IngredientService.getInstance();
        recipeService = RecipeService.getInstance();
        recipeIngredientService = RecipeIngredientService.getInstance();

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
        JComboBox<Category> categoryComboBox = new JComboBox<>(Category.values());

        JLabel prepTimeLabel = new JLabel("Preparation Time (min):");
        JTextField prepTimeField = new JTextField(5);

        JLabel instructionsLabel = new JLabel("Instructions:");
        JTextArea instructionsArea = new JTextArea(5, 20);
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

        // Ingredients section
        JLabel ingredientLabel = new JLabel("Select Ingredient:");
        JComboBox<String> ingredientComboBox = new JComboBox();
        try {
            List<Ingredient> ingredients = ingredientService.getAll();
            for (Ingredient ingredient : ingredients) {
                ingredientComboBox.addItem(ingredient.getName());
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JTextField quantityField = new JTextField(5);
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox<Unit> units = new JComboBox(Unit.values());

        JButton addIngredientButton = new JButton("Add Ingredient");
        JComboBox<RecipeIngredient> ingredientsToRemove = new JComboBox();

        // Add Ingredient action listener
        addIngredientButton.addActionListener(e -> {
            try {
                String selectedIngredient = (String) ingredientComboBox.getSelectedItem();
                String quantityStr = quantityField.getText();
                Float quantity = Float.parseFloat(quantityStr);
                RecipeIngredientAddingRequestGui recipeIngredientAddingRequestGui = new RecipeIngredientAddingRequestGui(
                        ingredientService.getByName(selectedIngredient).getId(), (Unit) units.getSelectedItem(), quantity);
                RecipeIngredient recipeIngredient = recipeIngredientService.createRecipeIngredientForGui(recipeIngredientAddingRequestGui);
                ingredientsToRemove.addItem(recipeIngredient);
                ingredientComboBox.removeItem(selectedIngredient);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // New Remove Ingredient button
        JButton removeIngredientButton = new JButton("Remove Ingredient");
        removeIngredientButton.addActionListener(e -> {
            try {
                RecipeIngredient selectedIngredient = (RecipeIngredient) ingredientsToRemove.getSelectedItem();
                if (selectedIngredient != null) {
                    ingredientsToRemove.removeItem(selectedIngredient);
                    ingredientComboBox.addItem(ingredientService.getById(selectedIngredient.getIngredientId()).getName());
                    JOptionPane.showMessageDialog(null, "Ingredient removed successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "No ingredient selected to remove.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // Save Recipe button
        JButton saveRecipeButton = new JButton("Save Recipe");
        saveRecipeButton.addActionListener(e -> {
            String name = nameField.getText();
            Category category = (Category) categoryComboBox.getSelectedItem();
            String prepTimeStr = prepTimeField.getText();
            String instructions = instructionsArea.getText();

            try {
                Integer prepTime = Integer.parseInt(prepTimeStr);
                recipeService.addRecipe(new RecipeAddingRequest(name, category, prepTime, instructions));
                Recipe recipe = recipeService.getByName(name);
                List<RecipeIngredient> recipeIngredients = new LinkedList();
                ComboBoxModel<RecipeIngredient> model = ingredientsToRemove.getModel();
                for (int i = 0; i < model.getSize(); i++) {
                    RecipeIngredient recipeIngredient = model.getElementAt(i);
                    recipeIngredient.setRecipeId(recipe.getId());
                    recipeIngredientService.create(new RecipeIngredientAddingRequest(
                            name, ingredientService.getById(recipeIngredient.getIngredientId()).getName(),
                            recipeIngredient.getUsingAmount(), recipeIngredient.getUnit()));
                }
                ingredientsToRemove.removeAllItems();
                ingredientComboBox.removeAllItems();
                for (Ingredient ingredient : ingredientService.getAll()) {
                    ingredientComboBox.addItem(ingredient.getName());
                }
            } catch (SQLException | NumberFormatException | RecipeException | IngredientException | RecipeIngredientException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

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
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(categoryLabel, gbc);
        gbc.gridx = 1;
        panel.add(categoryComboBox, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(prepTimeLabel, gbc);
        gbc.gridx = 1;
        panel.add(prepTimeField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(instructionsLabel, gbc);
        gbc.gridx = 1;
        panel.add(instructionsScrollPane, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(ingredientLabel, gbc);
        gbc.gridx = 1;
        panel.add(ingredientComboBox, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx = 1;
        panel.add(quantityField, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(unitLabel, gbc);
        gbc.gridx = 1;
        panel.add(units, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(addIngredientButton, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(new JLabel("Ingredients to Remove:"), gbc);
        gbc.gridx = 1;
        panel.add(ingredientsToRemove, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        panel.add(removeIngredientButton, gbc);

        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        panel.add(saveRecipeButton, gbc);

        // Add the panel to the dialog
        addRecipeDialog.add(panel);

        panel.setPreferredSize(new Dimension(500, 500));

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
                    try {
                        recipeService.deleteRecipeByName(recipeName);
                    } catch (SQLException | RecipeException ex) {

                    }
                    JOptionPane.showMessageDialog(removeDialog,
                            "Recipe \"" + recipeName + "\" has been removed.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
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

        // Filter fields
        JPanel filterPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Name label and text field
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        filterPanel.add(nameLabel);
        filterPanel.add(nameField);

        // Max Price label and text field
        JLabel maxPriceLabel = new JLabel("Max Price:");
        JTextField maxPriceField = new JTextField();
        filterPanel.add(maxPriceLabel);
        filterPanel.add(maxPriceField);

        // Max Ingredient Amount label and text field
        JLabel maxIngredientAmountLabel = new JLabel("Max Ingredient Amount:");
        JTextField maxIngredientAmountField = new JTextField();
        filterPanel.add(maxIngredientAmountLabel);
        filterPanel.add(maxIngredientAmountField);

        // Max Preparation Time label and text field
        JLabel maxPrepTimeLabel = new JLabel("Max Preparation Time:");
        JTextField maxPrepTimeField = new JTextField();
        filterPanel.add(maxPrepTimeLabel);
        filterPanel.add(maxPrepTimeField);

        // Category ComboBox
        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("All");
        // Populate ComboBox with category options
        for (Category category : Category.values()) {
            categoryComboBox.addItem(category.name());
        }
        filterPanel.add(categoryLabel);
        filterPanel.add(categoryComboBox);

        // Recipe list and recipe information display area
        JComboBox<Recipe> recipeList = new JComboBox<>();
        JTextArea recipeInfo = new JTextArea();

        try {
            for (Recipe recipe : recipeService.getAll()) {
                recipeList.addItem(recipe);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        // Recipe selection event listener
        recipeList.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                Recipe selectedRecipe = (Recipe) recipeList.getSelectedItem();
                String infoMessage = null;
                try {
                    if (selectedRecipe == null) {
                        return;
                    }
                    StringBuilder sb = new StringBuilder();
                    sb.append("Name : ").append(selectedRecipe.getName()).append("\n")
                            .append("Preparation Time (In Minute) : ").append(selectedRecipe.getPreperationTimeInMinute())
                            .append("\n").append("Instruction : ").append(selectedRecipe.getInstructions()).append("\n")
                            .append("Category : ").append(selectedRecipe.getCategory().name()).append("\n")
                            .append("Total Price : ").append(recipeService.getTotalPriceOfRecipe(selectedRecipe))
                            .append("\n");

                    for (RecipeIngredient recipeIngredient : recipeIngredientService.getByRecipeId(selectedRecipe.getId())) {
                        Ingredient ingredient = ingredientService.getById(recipeIngredient.getIngredientId());
                        sb.append("Ingredient Name : ").append(ingredient.getName()).append("\n")
                                .append("Using Amount : ").append(recipeIngredient.getUsingAmount()).append("\n")
                                .append("Using Unit : ").append(recipeIngredient.getUnit().name()).append("\n")
                                .append("Price : ").append(UnitAndPriceConversation.calculatePrice(ingredient.getUnit(), ingredient.getUnitPrice(), recipeIngredient.getUnit(), recipeIngredient.getUsingAmount()))
                                .append("\n");

                        if (!UnitAndPriceConversation.isSufficentUsingAmount(ingredient.getUnit(), ingredient.getAmount(), recipeIngredient.getUnit(), recipeIngredient.getUsingAmount())) {
                            sb.append("There is less in the warehouse than the specified amount").append("\n");
                            recipeList.setRenderer(new DefaultListCellRenderer() {
                                @Override
                                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                                    Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                                    if (selectedRecipe.equals(value)) {
                                        component.setForeground(Color.RED);
                                    } else {
                                        component.setForeground(Color.BLACK);
                                    }
                                    return component;
                                }
                            });
                        }
                    }
                    infoMessage = sb.toString();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
                recipeInfo.setText(infoMessage);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton sortByPriceButton = new JButton("Sort by Price");
        JButton sortByPrepTimeButton = new JButton("Sort by Preparation Time");
        JButton sortByIngredientAmountButton = new JButton("Sort by Ingredient Amount");
        JButton filterButton = new JButton("Filter");

        buttonPanel.add(sortByPriceButton);
        buttonPanel.add(sortByPrepTimeButton);
        buttonPanel.add(sortByIngredientAmountButton);
        buttonPanel.add(filterButton);

        // Main panel layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel(new BorderLayout());

        leftPanel.add(recipeList, BorderLayout.CENTER);
        centerPanel.add(new JScrollPane(recipeInfo), BorderLayout.CENTER);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        showDialog.setLayout(new BorderLayout());
        showDialog.add(filterPanel, BorderLayout.NORTH);  // Filter fields panel at the top
        showDialog.add(mainPanel, BorderLayout.CENTER);    // Recipe list and info in the center
        showDialog.add(buttonPanel, BorderLayout.SOUTH);   // Buttons at the bottom

        // Display dialog
        showDialog.pack();
        showDialog.setVisible(true);

        sortByPriceButton.addActionListener(e -> {
            try {
                List<String> recipeNames = new LinkedList();
                ComboBoxModel<Recipe> model = recipeList.getModel();
                for (int i = 0; i < model.getSize(); i++) {
                    recipeNames.add(model.getElementAt(i).getName());
                }
                List<Recipe> orderedRecipes = recipeService.getRecipesByPriceOrdered(recipeNames);
                recipeList.removeAllItems();
                for (Recipe recipe : orderedRecipes) {
                    recipeList.addItem(recipe);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // Sort by Preparation Time Action ******************************
        sortByPrepTimeButton.addActionListener(e -> {
            try {
                List<String> recipeNames = new LinkedList();
                ComboBoxModel<Recipe> model = recipeList.getModel();
                for (int i = 0; i < model.getSize(); i++) {
                    recipeNames.add(model.getElementAt(i).getName());
                }
                List<Recipe> orderedRecipes = recipeService.getRecipesByPreparationTimeOrdered(recipeNames);
                recipeList.removeAllItems();
                for (Recipe recipe : orderedRecipes) {
                    recipeList.addItem(recipe);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // Sort by Ingredient Amount Action *******************************
        sortByIngredientAmountButton.addActionListener(e -> {
            try {
                List<String> recipeNames = new LinkedList();
                ComboBoxModel<Recipe> model = recipeList.getModel();
                for (int i = 0; i < model.getSize(); i++) {
                    recipeNames.add(model.getElementAt(i).getName());
                }
                List<Recipe> orderedRecipes = recipeService.getRecipesByIngredientAmountOrdered(recipeNames);
                recipeList.removeAllItems();
                for (Recipe recipe : orderedRecipes) {
                    recipeList.addItem(recipe);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        filterButton.addActionListener(e -> {
            String name = nameField.getText();
            try {
                String maxPriceString = maxPriceField.getText();
                String maxPreparationTimeString = maxPrepTimeField.getText();
                String maxIngredientAmountString = maxIngredientAmountField.getText();
                Integer maxPreparationTime = null;
                Integer maxIngredientAmount = null;
                Float maxPrice = null;

                if (maxPriceString.isBlank() || maxPriceString.isEmpty()) {
                    maxPrice = -1F;
                } else {
                    maxPrice = Float.parseFloat(maxPriceString);
                }

                if (maxPreparationTimeString.isBlank() || maxPreparationTimeString.isEmpty()) {
                    maxPreparationTime = -1;
                } else {
                    maxPreparationTime = Integer.parseInt(maxPreparationTimeString);
                }
                if (maxIngredientAmountString.isBlank() || maxIngredientAmountString.isEmpty()) {
                    maxIngredientAmount = -1;
                } else {
                    maxIngredientAmount = Integer.parseInt(maxIngredientAmountString);
                }
                String categoryName = (String) categoryComboBox.getSelectedItem();
                List<Recipe> filteredRecipes = recipeService.filter(new FilterRequest(name, maxPreparationTime, categoryName, maxPrice, maxIngredientAmount));
                recipeList.removeAllItems();
                for (Recipe recipe : filteredRecipes) {
                    recipeList.addItem(recipe);
                }
            } catch (NumberFormatException | SQLException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });

        // Show dialog
    }

    private void openUpdateRecipePanel() {

        String originalName = JOptionPane.showInputDialog("Please Enter Recipe Name");
        JDialog updateDialog = new JDialog(this, "Update Recipe", true); // true for modal
        updateDialog.setSize(500, 500); // Adjusted size for better visibility
        updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        updateDialog.setLocationRelativeTo(this); // Center the dialog

        // Create a panel for the dialog
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        // Create and style the components
        JLabel nameLabel = new JLabel("Recipe Name:");
        JTextField nameField = new JTextField(20);
        nameField.setText(originalName);

        JLabel categoryLabel = new JLabel("Category:");
        JComboBox<Category> categoryComboBox = new JComboBox<>(Category.values());
        try {
            categoryComboBox.setSelectedItem(recipeService.getByName(originalName).getCategory());
        } catch (SQLException | RecipeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JLabel prepTimeLabel = new JLabel("Preparation Time (min):");
        JTextField prepTimeField = new JTextField(5);
        try {
            prepTimeField.setText(String.valueOf(recipeService.getByName(originalName).getPreperationTimeInMinute()));

        } catch (SQLException | RecipeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JLabel instructionsLabel = new JLabel("Instructions:");
        JTextArea instructionsArea = new JTextArea(5, 20);
        try {
            instructionsArea.setText(recipeService.getByName(originalName).getInstructions());
        } catch (SQLException | RecipeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        JScrollPane instructionsScrollPane = new JScrollPane(instructionsArea);

        // Ingredients section
        JLabel ingredientLabel = new JLabel("Select Ingredient:");
        JComboBox<String> ingredientsToAddComboBox = new JComboBox<>();
        try {
            Recipe recipe = recipeService.getByName(originalName);
            List<Ingredient> ingredients = ingredientService.getAll();
            for (Ingredient ingredient : ingredients) {
                boolean notAddedIngredient = true;
                for (RecipeIngredient recipeIngredient : recipeIngredientService.getByRecipeId(recipe.getId())) {
                    if (recipeIngredient.getIngredientId().longValue() == ingredient.getId().longValue()) {
                        notAddedIngredient = false;
                        break;
                    }
                }
                if (notAddedIngredient) {
                    ingredientsToAddComboBox.addItem(ingredient.getName());
                }
            }
        } catch (SQLException | RecipeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        JTextField quantityField = new JTextField(5);
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox<Unit> units = new JComboBox<>(Unit.values());

        JLabel usingAmountLabel = new JLabel("Using Amount :");
        JTextField usingAmountField = new JTextField(5);

        JButton addIngredientButton = new JButton("Add Ingredient");

        JComboBox<RecipeIngredient> ingredientsToRemove = new JComboBox<>();
        try {
            Recipe recipe = recipeService.getByName(originalName);
            for (RecipeIngredient recipeIngredient : recipeIngredientService.getByRecipeId(recipe.getId())) {
                ingredientsToRemove.addItem(recipeIngredient);
            }
        } catch (SQLException | RecipeException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        JButton removeIngredientButton = new JButton("Remove Ingredient");

        removeIngredientButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RecipeIngredient recipeIngredientToRemove = (RecipeIngredient) ingredientsToRemove.getSelectedItem();
                try {
                    String ingredientName = ingredientService.getById(recipeIngredientToRemove.getIngredientId()).getName();
                    ingredientsToAddComboBox.addItem(ingredientName);
                    ingredientsToRemove.removeItem(recipeIngredientToRemove);
                    recipeIngredientService.deleteByRecipeIdAndIngredientId(recipeIngredientToRemove.getRecipeId(), recipeIngredientToRemove.getIngredientId());
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        });

        addIngredientButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ingredientName = (String) ingredientsToAddComboBox.getSelectedItem();
                    Ingredient ingredient = ingredientService.getByName(ingredientName);
                    RecipeIngredient recipeIngredient = recipeIngredientService.createRecipeIngredientForGui(new RecipeIngredientAddingRequestGui(ingredient.getId(), (Unit) units.getSelectedItem(), Float.parseFloat(usingAmountField.getText())));
                    recipeIngredient.setRecipeId(recipeService.getByName(originalName).getId());
                    ingredientsToAddComboBox.removeItem(ingredientName);
                    ingredientsToRemove.addItem(recipeIngredient);
                    recipeIngredientService.create(new RecipeIngredientAddingRequest(originalName, ingredientName, Float.parseFloat(usingAmountField.getText()), (Unit) units.getSelectedItem()));
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        });

        JButton updateButton = new JButton("Update");

        // Use GridBagConstraints for layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(categoryComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(prepTimeLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(prepTimeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(instructionsLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(instructionsScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ingredientLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(ingredientsToAddComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(usingAmountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(usingAmountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(unitLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(units, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        panel.add(addIngredientButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        panel.add(ingredientsToRemove, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        panel.add(removeIngredientButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        panel.add(updateButton, gbc);

        // Action listener for the update button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String recipeName = nameField.getText();
                if (!recipeName.isEmpty()) {
                    Category category = (Category) categoryComboBox.getSelectedItem();
                    try {
                        RecipeAddingRequest recipeAddingRequest = new RecipeAddingRequest(nameField.getText(), category, Integer.parseInt(prepTimeField.getText()), instructionsArea.getText());
                        recipeService.updateRecipe(recipeAddingRequest, recipeService.getByName(originalName).getId());
                        JOptionPane.showMessageDialog(null, "Successful");
                    } catch (SQLException | NumberFormatException | RecipeException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
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
        updateDialog.setVisible(true);
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
