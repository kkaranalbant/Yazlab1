package yazlab.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame {

    private JPanel panel;

    public MainPanel() {
        // Pencere başlığını belirleme
        setTitle("Recipe Management");

        // Pencerenin boyutunu belirleme
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel oluşturma
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10)); // Grid layout ile 4 satırlı panel

        // Butonları oluşturma
        JButton addRecipeButton = new JButton("Recipe Menu");
        JButton addIngredientButton = new JButton("Ingredient Menu");

        // Butonlara aksiyon ekleme (şu an sadece mesaj gösteriyor)
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

        // Butonları panele ekleme
        panel.add(addRecipeButton);
        panel.add(addIngredientButton);

        // Paneli pencereye ekleme
        add(panel);
    }

    public static void main(String[] args) {
        // GUI başlatma
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainPanel frame = new MainPanel();
                frame.setVisible(true);
            }
        });
    }
}
