/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.model;

/**
 *
 * @author kaan
 */
public enum Category {

    SOUP,
    SALAD,
    RED_MEAT_DISH,
    WHITE_MEAT_DISH,
    FISH_DISH,
    VEGETABLE_DISH,
    PASTA_AND_RICE,
    PASTRY,
    APPETIZER_AND_SNACK,
    DESSERT,
    BREAKFAST_ITEM,
    HOT_DRINK,
    COLD_DRINK;

    public static String[] getNames() {
        String[] results = new String[13];
        int counter = 0;
        for (Category category : Category.values()) {
            results[counter] = category.name();
            counter++;
        }
        return results;
    }

}
