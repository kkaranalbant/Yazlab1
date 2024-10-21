/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.model;

import yazlab.service.IngredientService;
import java.sql.SQLException ;

/**
 *
 * @author kaan
 */
public class RecipeIngredient extends BaseEntity {

    private Long recipeId;

    private Long ingredientId;

    private Float usingAmount;

    private Unit unit;

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Float getUsingAmount() {
        return usingAmount;
    }

    public void setUsingAmount(Float usingAmount) {
        this.usingAmount = usingAmount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString () {
        try {
            return IngredientService.getInstance().getById(ingredientId).getName() ;
        }
        catch (SQLException ex) {
            return null ;
        }
    }
    
}
