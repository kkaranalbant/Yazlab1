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

public class IngredientPanel extends JFrame {

    private IngredientService ingredientService;

    public IngredientPanel() {
        ingredientService = IngredientService.getInstance();
        setTitle("Ingredient Panel");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(2, 2));

        JButton addButton = new JButton("Add Ingredient");
        JButton removeButton = new JButton("Remove Ingredient");
        JButton updateButton = new JButton("Update Ingredient");
        JButton showButton = new JButton("Show Ingredients");

        add(addButton);
        add(removeButton);
        add(updateButton);
        add(showButton);

        // Add Ingredient button action
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddIngredientPanel();
            }
        });

        // Remove Ingredient button action
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ingredientName = JOptionPane.showInputDialog("Enter the name of the ingredient to remove:");
                if (ingredientName != null && !ingredientName.isEmpty()) {
                    try {
                        ingredientService.removeIngredientByName(ingredientName);
                    } catch (SQLException | IngredientException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.util.List<Ingredient> ingredients = ingredientService.getAll(); // Veritabanından tüm ingredientları al
                    openShowIngredientsPanel(ingredients); // Yeni paneli aç
                } catch (SQLException | IngredientException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });

        // Update Ingredient button action
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ingredientName = JOptionPane.showInputDialog("Enter the name of the ingredient to update:");
                if (ingredientName != null && !ingredientName.isEmpty()) {
                    try {
                        Ingredient ingredient = ingredientService.getByName(ingredientName);
                        openUpdateIngredientPanel(ingredient);
                    } catch (SQLException | IngredientException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        });

        setVisible(true);
    }

    private void openAddIngredientPanel() {
        JFrame addFrame = new JFrame("Add Ingredient");
        addFrame.setSize(300, 200);
        addFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addFrame.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JLabel unitLabel = new JLabel("Unit:");
        JComboBox unitComboBox = new JComboBox(Unit.getNames());
        JLabel unitPriceLabel = new JLabel("Unit Price:");
        JTextField unitPriceField = new JTextField();

        JButton addIngredientButton = new JButton("Add Ingredient");

        addFrame.add(nameLabel);
        addFrame.add(nameField);
        addFrame.add(amountLabel);
        addFrame.add(amountField);
        addFrame.add(unitLabel);
        addFrame.add(unitComboBox);
        addFrame.add(unitPriceLabel);
        addFrame.add(unitPriceField);
        addFrame.add(new JLabel());
        addFrame.add(addIngredientButton);

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

        addFrame.setVisible(true);
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
        JComboBox unitComboBox = new JComboBox(Unit.getNames());
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

        updateIngredientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    Float amount = Float.parseFloat(amountField.getText());
                    String unitName = (String) unitComboBox.getSelectedItem();
                    Unit unit = Unit.valueOf(unitName);
                    Float unitPrice = Float.parseFloat(unitPriceField.getText());
                    IngredientAddingRequest ingredientUpdatingRequest = new IngredientAddingRequest(name, amount, unit, unitPrice);
                    ingredientService.updateIngredient(ingredientUpdatingRequest, ingredient.getId());
                } catch (SQLException | IngredientException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
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
            ingredientComboBox.addItem(ingredient); // ComboBox'a ingredient adlarını ekliyoruz
        }

        JLabel detailsLabel = new JLabel("Ingredient Details:");
        JTextField detailsField = new JTextField();

        ingredientComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ingredient ingredient = (Ingredient) ingredientComboBox.getSelectedItem();
                StringBuilder sb = new StringBuilder();
                sb.append("Name : ").append(ingredient.getName()).append("\n")
                        .append("Amount : ").append(ingredient.getAmount()).append("\n")
                        .append("Unit Type : ").append(ingredient.getUnit().name()).append("\n")
                        .append("Unit Price : ").append(ingredient.getUnitPrice()).append("\n");
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
}
