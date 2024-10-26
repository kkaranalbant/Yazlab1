/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.request;

import yazlab.model.Unit;

/**
 *
 * @author kaan
 */
public record IngredientAddingRequest (String name , Float amount , Unit unit , Float unitPrice) {
    
}
