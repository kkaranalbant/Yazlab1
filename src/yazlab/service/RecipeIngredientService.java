/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.service;

import yazlab.exception.RecipeIngredientException;
import yazlab.repository.query.RecipeIngredientRepo;
import yazlab.request.RecipeIngredientAddingRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import yazlab.exception.IngredientException;
import yazlab.exception.RecipeException;
import yazlab.model.Ingredient;
import yazlab.model.Recipe;
import yazlab.model.RecipeIngredient;
import yazlab.request.RecipeIngredientAddingRequestGui;

/**
 *
 * @author kaan
 */
public class RecipeIngredientService {

    private static RecipeIngredientService recipeIngredientService;

    private RecipeIngredientRepo recipeIngredientRepo;

    private RecipeService recipeService;

    private IngredientService ingredientService;

    private RecipeIngredientService() {
        recipeIngredientRepo = RecipeIngredientRepo.getInstance();
        recipeService = RecipeService.getInstance();
        ingredientService = IngredientService.getInstance();
    }

    public static RecipeIngredientService getInstance() {
        if (recipeIngredientService == null) {
            recipeIngredientService = new RecipeIngredientService();
        }
        return recipeIngredientService;
    }

    public void create(RecipeIngredientAddingRequest recipeIngredientAddingRequest) throws SQLException, RecipeIngredientException, RecipeException, IngredientException {
        System.out.println(recipeIngredientAddingRequest.ingredientName() == null);
        System.out.println(recipeIngredientAddingRequest.recipeName() == null);
        System.out.println(recipeIngredientAddingRequest.unit() == null);
        System.out.println(recipeIngredientAddingRequest.usingAmount() == null);
        if (recipeIngredientAddingRequest.ingredientName() == null
                || recipeIngredientAddingRequest.recipeName() == null
                || recipeIngredientAddingRequest.unit() == null
                || recipeIngredientAddingRequest.usingAmount() == null) {
            throw new RecipeIngredientException("Please Enter All Informations");
        }
        if (recipeIngredientAddingRequest.usingAmount() < 0) {
            throw new RecipeIngredientException("Using Amount Must Be Bigger Than Zero");
        }
        if (isAlredyInDatabaseByRecipeNameAndIngredientName(recipeIngredientAddingRequest.recipeName(), recipeIngredientAddingRequest.ingredientName())) {
            throw new RecipeIngredientException("This Item Has Already Added to Database");
        }
        Recipe recipe = recipeService.getByName(recipeIngredientAddingRequest.recipeName());
        Ingredient ingredient = ingredientService.getByName(recipeIngredientAddingRequest.ingredientName());
        recipeIngredientRepo.create(recipe.getId(), ingredient.getId(), recipeIngredientAddingRequest.usingAmount(), recipeIngredientAddingRequest.unit());
    }

    public RecipeIngredient createRecipeIngredientForGui(RecipeIngredientAddingRequestGui recipeIngredientAddingRequestGui) throws RecipeIngredientException {
        if (recipeIngredientAddingRequestGui.usingAmount().floatValue() <= 0) {
            throw new RecipeIngredientException("Ingredient Using Amount Must Be Bigger Than Zero");
        }
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setIngredientId(recipeIngredientAddingRequestGui.ingredientId());
        recipeIngredient.setUnit(recipeIngredientAddingRequestGui.unit());
        recipeIngredient.setUsingAmount(recipeIngredientAddingRequestGui.usingAmount());
        return recipeIngredient;
    }

    public void update(RecipeIngredientAddingRequest recipeIngredientAddingRequest, Long id) throws SQLException, RecipeIngredientException, RecipeException, IngredientException {
        if (recipeIngredientAddingRequest.ingredientName() == null
                || recipeIngredientAddingRequest.recipeName() == null
                || recipeIngredientAddingRequest.unit() == null
                || recipeIngredientAddingRequest.usingAmount() == null) {
            throw new RecipeIngredientException("Please Enter All Informations");
        }
        if (recipeIngredientAddingRequest.usingAmount() < 0) {
            throw new RecipeIngredientException("Using Amount Must Be Bigger Than Zero");
        }
        Recipe recipe = recipeService.getByName(recipeIngredientAddingRequest.recipeName());
        Ingredient ingredient = ingredientService.getByName(recipeIngredientAddingRequest.ingredientName());
        recipeIngredientRepo.update(recipe.getId(), ingredient.getId(), recipeIngredientAddingRequest.usingAmount(), recipeIngredientAddingRequest.unit(), id);
    }

    public void deleteById(Long id) throws SQLException {
        recipeIngredientRepo.makeDeletingQueryById(id);
    }

    public void deleteByIngredientId(Long ingredientId) throws SQLException {
        recipeIngredientRepo.deleteByIngredientId(ingredientId);
    }

    public void deleteByRecipeId(Long recipeId) throws SQLException {
        recipeIngredientRepo.deleteByRecipeId(recipeId);
    }

    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) throws SQLException {
        recipeIngredientRepo.deleteByRecipeIdAndIngredientId(recipeId, ingredientId);
    }

    public List<RecipeIngredient> getByRecipeId(Long recipeId) throws SQLException {
        List<RecipeIngredient> results = recipeIngredientRepo.transform(recipeIngredientRepo.getByRecipeId(recipeId));
        return results;
    }

    public List<RecipeIngredient> getByIngredientId(Long ingredientId) throws SQLException {
        List<RecipeIngredient> results = recipeIngredientRepo.transform(recipeIngredientRepo.getByIngredientId(ingredientId));
        return results;
    }

    public List<RecipeIngredient> getAll() throws SQLException {
        List<RecipeIngredient> results = recipeIngredientRepo.transform(recipeIngredientRepo.getAll());
        return results;
    }

    public RecipeIngredient getByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) throws SQLException {
        List<RecipeIngredient> results = recipeIngredientRepo.transform(recipeIngredientRepo.getByRecipeIdAndIngredientId(recipeId, ingredientId));
        return results.get(0);
    }

    private boolean isAlredyInDatabaseByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) throws SQLException {
        List<RecipeIngredient> results = recipeIngredientRepo.transform(recipeIngredientRepo.getByRecipeIdAndIngredientId(recipeId, ingredientId));
        return !results.isEmpty();
    }

    private boolean isAlredyInDatabaseByRecipeNameAndIngredientName(String recipeName, String ingredientName) throws SQLException {
        try {
            Recipe recipe = recipeService.getByName(recipeName);
            Ingredient ingredient = ingredientService.getByName(ingredientName);
            return isAlredyInDatabaseByRecipeIdAndIngredientId(recipe.getId(), ingredient.getId());
        } catch (RecipeException | IngredientException ex) {

        }
        return false;
    }

    public List<Recipe> getRecipesByRecipeIngredients(List<RecipeIngredient> recipeIngredients) throws SQLException {
        List<Recipe> recipes = new ArrayList();
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            Long recipeId = recipeIngredient.getRecipeId();
            Recipe recipe = recipeService.getById(recipeId);
            recipes.add(recipe);
        }
        return recipes;
    }

    public List<Ingredient> getIngredientsByRecipeIngredients(List<RecipeIngredient> recipeIngredients) throws SQLException {
        List<Ingredient> ingredients = new ArrayList();
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            Ingredient ingredient = ingredientService.getById(recipeIngredient.getIngredientId());
            ingredients.add(ingredient);
        }
        return ingredients;
    }

}
