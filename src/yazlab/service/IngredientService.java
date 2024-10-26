/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.service;

import yazlab.exception.IngredientException;
import yazlab.repository.query.IngredientRepo;
import yazlab.request.IngredientAddingRequest;
import java.sql.SQLException;
import java.util.List;
import yazlab.model.Ingredient;
import yazlab.model.RecipeIngredient;

/**
 *
 * @author kaan
 */
public class IngredientService {

    private static IngredientService ingredientService;

    private IngredientRepo ingredientRepo;

    private RecipeIngredientService recipeIngredientService;

    private IngredientService() {
        ingredientRepo = IngredientRepo.getInstance();
    }

    public static IngredientService getInstance() {
        if (ingredientService == null) {
            ingredientService = new IngredientService();
        }
        return ingredientService;
    }

    public void addIngredient(IngredientAddingRequest ingredientAddingRequest) throws SQLException, IngredientException {
        if (ingredientAddingRequest.name() == null || ingredientAddingRequest.unit() == null
                || ingredientAddingRequest.unitPrice() == null || ingredientAddingRequest.amount() == null) {
            throw new IngredientException("Please Enter All Ingredient Informations");
        }
        if (ingredientAddingRequest.amount() < 0) {
            throw new IngredientException("Amount Must Be Bigger Than Zero");
        }
        if (ingredientAddingRequest.unitPrice() < 0) {
            throw new IngredientException("Unit Price Must Be Bigger Than Zero");
        }
        if (isAlreadyInDatabase(ingredientAddingRequest.name())) {
            throw new IngredientException("This Ingredient Has Already Added to Database");
        }
        ingredientRepo.create(ingredientAddingRequest.name(), ingredientAddingRequest.amount(), ingredientAddingRequest.unit(), ingredientAddingRequest.amount());

    }

    public void removeIngredientByName(String name) throws SQLException, IngredientException {
        if (name == null) {
            throw new IngredientException("Please Enter Name Value");
        }
        if (!isAlreadyInDatabase(name)) {
            throw new IngredientException("No Ingredient by This Name is Registered");
        }
        Ingredient ingredient = getByName(name);
        recipeIngredientService = RecipeIngredientService.getInstance();
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByIngredientId(ingredient.getId());
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            recipeIngredientService.deleteById(recipeIngredient.getId());
        }
        ingredientRepo.makeDeleteByName(name);
    }

    public Ingredient getByName(String name) throws SQLException, IngredientException {
        if (name == null) {
            throw new IngredientException("Please Enter Name Value");
        }
        if (!isAlreadyInDatabase(name)) {
            throw new IngredientException("No Ingredient by This Name is Registered");
        }
        return ingredientRepo.transform(ingredientRepo.getByName(name)).get(0);
    }

    public List<Ingredient> getAll() throws SQLException {
        return ingredientRepo.transform(ingredientRepo.getAll());
    }

    public Ingredient getById(Long id) throws SQLException {
        return ingredientRepo.transform(ingredientRepo.makeGettingQueryById(id)).getFirst();
    }

    public void updateIngredient(IngredientAddingRequest ingredientAddingRequest, Long id) throws SQLException, IngredientException {
        if (ingredientAddingRequest.name() == null || ingredientAddingRequest.unit() == null
                || ingredientAddingRequest.unitPrice() == null || ingredientAddingRequest.amount() == null) {
            throw new IngredientException("Please Enter All Ingredient Information");
        }
        if (!isAlreadyInDatabase(id)) {
            throw new IngredientException("No Ingredient by This Name is Registered");
        }
        ingredientRepo.makeUpdatingQuery(ingredientAddingRequest.name(), ingredientAddingRequest.amount(), ingredientAddingRequest.unit(), ingredientAddingRequest.unitPrice(), id);
    }

    private boolean isAlreadyInDatabase(String name) throws SQLException {
        List<Ingredient> results = ingredientRepo.transform(ingredientRepo.getByName(name));
        return !results.isEmpty();
    }

    private boolean isAlreadyInDatabase(Long id) throws SQLException {
        List<Ingredient> results = ingredientRepo.transform(ingredientRepo.makeGettingQueryById(id));
        return !results.isEmpty();
    }

}
