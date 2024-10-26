/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.model;

/**
 *
 * @author kaan
 */
public enum Unit {

    LITER,
    MILILITER,
    KILOGRAM,
    MILIGRAM,
    GRAM;

    public static String[] getNames() {
        String[] result = new String[5];
        Unit[] units = Unit.values();
        for (int i = 0; i < 5; i++) {
            result[i] = units[i].name();
        }
        return result;
    }

}
