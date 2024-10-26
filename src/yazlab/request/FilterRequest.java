/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.request;

/**
 *
 * @author kaan
 */
public record FilterRequest(String name, Integer maxPreparationTimeInMinute, String categoryName, Float maxPrice , Integer maxIngredientAmount) {

}
