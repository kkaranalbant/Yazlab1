/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.request;

import yazlab.model.Category;

/**
 *
 * @author kaan
 */
public record RecipeAddingRequest (String name , Category category , Integer preparationTime , String instruction) {
    
}
