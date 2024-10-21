/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.service;

import yazlab.exception.RecipeException;
import yazlab.repository.query.RecipeRepo;
import yazlab.request.RecipeAddingRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import yazlab.model.Category;
import yazlab.model.Recipe;
import yazlab.model.RecipeIngredient;
import yazlab.request.FilterRequest;

/**
 *
 * @author kaan
 */
public class RecipeService {

    private static RecipeService recipeService;

    private RecipeRepo recipeRepo;

    private RecipeIngredientService recipeIngredientService;

    private IngredientService ingredientService;

    private RecipeService() {
        recipeRepo = RecipeRepo.getInstance();
        ingredientService = IngredientService.getInstance();
    }

    public static RecipeService getInstance() {
        if (recipeService == null) {
            recipeService = new RecipeService();
        }
        return recipeService;
    }

    public void addRecipe(RecipeAddingRequest recipeAddingRequest) throws SQLException, RecipeException {
        if (recipeAddingRequest.name() == null || recipeAddingRequest.name().isEmpty() || recipeAddingRequest.category() == null
                || recipeAddingRequest.preparationTime() == null
                || recipeAddingRequest.instruction() == null || recipeAddingRequest.instruction().isEmpty()) {

            throw new RecipeException("Please Enter Complete Informations");

        }
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.getByName(recipeAddingRequest.name()));
        if (!recipes.isEmpty()) {
            throw new RecipeException("This Recipe is Already Registered");
        }
        recipeRepo.create(recipeAddingRequest.name(), recipeAddingRequest.category(), recipeAddingRequest.preparationTime(), recipeAddingRequest.instruction());
    }

    public void deleteRecipeByName(String name) throws SQLException, RecipeException {
        if (name == null) {
            throw new RecipeException("Please Enter Complete Informations");
        }
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.getByName(name));
        if (recipes.isEmpty()) {
            throw new RecipeException("No Recipe by This Name is Registered");
        }
        recipeIngredientService = RecipeIngredientService.getInstance();
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByRecipeId(recipes.get(0).getId());
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            recipeIngredientService.deleteById(recipeIngredient.getId());
        }
        recipeRepo.deleteByName(name);
    }

    public Recipe getByName(String name) throws SQLException, RecipeException {
        if (name == null) {
            throw new RecipeException("Please Enter Complete Informations");
        }
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.getByName(name));
        if (recipes.isEmpty()) {
            throw new RecipeException("No Recipe by This Name is Registered");
        }
        return recipes.get(0);
    }

    public Recipe getById(Long id) throws SQLException {
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.makeGettingQueryById(id));
        return recipes.get(0);
    }

    public List<Recipe> getAll() throws SQLException {
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.getAll());
        return recipes;
    }

    public void updateRecipe(RecipeAddingRequest recipeAddingRequest, Long recipeId) throws SQLException, RecipeException {
        if (recipeAddingRequest.name() == null || recipeAddingRequest.category() == null
                || recipeAddingRequest.preparationTime() == null
                || recipeAddingRequest.instruction() == null) {

            throw new RecipeException("Please Enter Complete Informations");

        }
        if (isInDatabaseByName(recipeAddingRequest.name()) && !getById(recipeId).getName().equalsIgnoreCase(recipeAddingRequest.name())) {
            throw new RecipeException("You Can Not Pick This Name");
        }

        recipeRepo.makeUpdatingQuery(recipeAddingRequest.name(), recipeAddingRequest.category(), recipeAddingRequest.preparationTime(), recipeAddingRequest.instruction(), recipeId);

    }

    public boolean isInDatabaseByName(String name) throws SQLException {
        List<Recipe> recipes = recipeRepo.transform(recipeRepo.getByName(name));
        return !recipes.isEmpty();
    }

    public List<Recipe> filter(FilterRequest filterRequest) throws SQLException {
        boolean containsName = filterRequest.name() != null && filterRequest.name() != "" && !filterRequest.name().isEmpty();
        boolean containsPreparationTime = filterRequest.maxPreparationTimeInMinute().intValue() != -1;
        boolean containsPrice = filterRequest.maxPrice().floatValue() != -1F;
        boolean containsCategoryFilter = filterRequest.categoryName() != null && !filterRequest.categoryName().equals("All");
        boolean containsMaxIngredientAmount = filterRequest.maxIngredientAmount().intValue() != -1;
        List<Recipe> recipes = new LinkedList();
        if (containsName && containsPreparationTime && containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByNameAndPreparationTimeAndCategory(filterRequest.name(), filterRequest.maxPreparationTimeInMinute(), filterRequest.categoryName()));
        } else if (containsName && !containsPreparationTime && containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByNameAndCategory(filterRequest.name(), filterRequest.categoryName()));
        } else if (!containsName && containsPreparationTime && containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByPreparationTimeAndCategory(filterRequest.maxPreparationTimeInMinute(), filterRequest.categoryName()));
        } else if (!containsName && !containsPreparationTime && containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByCategory(Category.valueOf(filterRequest.categoryName())));
        } else if (!containsName && !containsPreparationTime && !containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getAll());
        } else if (containsName && !containsPreparationTime && !containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByName(filterRequest.name()));
        } else if (!containsName && containsPreparationTime && !containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByMaxPreparationTime(filterRequest.maxPreparationTimeInMinute()));
        } else if (containsName && containsPreparationTime && !containsCategoryFilter) {
            recipes = recipeRepo.transform(recipeRepo.getByNameAndPreparationTime(filterRequest.name(), filterRequest.maxPreparationTimeInMinute()));
        }
        if (containsPrice) {
            List<Recipe> recipesToRemove = new ArrayList();
            for (Recipe recipe : recipes) {
                Float recipePrice = 0F;
                List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByRecipeId(recipe.getId());
                for (RecipeIngredient recipeIngredient : recipeIngredients) {
                    Float recipeIngredientPrice = recipeIngredient.getUsingAmount() * ingredientService.getById(recipeIngredient.getIngredientId()).getUnitPrice();
                    recipePrice += recipeIngredientPrice;
                }
                if (recipePrice > filterRequest.maxPrice()) {
                    recipesToRemove.add(recipe);
                }
            }
            recipes.removeAll(recipesToRemove);
        }
        if (containsMaxIngredientAmount) {
            List<Recipe> recipesToRemove = new LinkedList();
            for (Recipe recipe : recipes) {
                int ingredientAmount = recipeIngredientService.getByRecipeId(recipe.getId()).size();
                if (ingredientAmount > filterRequest.maxIngredientAmount()) {
                    recipesToRemove.add(recipe);
                }
            }
            recipes.removeAll(recipesToRemove);
        }
        return recipes;
    }

    public List<Recipe> getRecipesByPriceOrdered(List<String> recipeNames) throws SQLException {
        Map<String, Float> recipeNameAndPriceMap = new HashMap();
        for (String recipeName : recipeNames) {
            Recipe recipe = getByName(recipeName);
            Float totalRecipePrice = getTotalPriceOfRecipe(recipe);
            recipeNameAndPriceMap.put(recipeName, totalRecipePrice);
        }
        Map<String, Float> sortedMap = sortMap(recipeNameAndPriceMap, Float.class);
        return transformMapToRecipeList(sortedMap);
    }

    public List<Recipe> getRecipesByIngredientAmountOrdered(List<String> recipeNames) throws SQLException {
        Map<String, Integer> recipeNameIngredientAmountMap = new HashMap();
        for (int i = 0; i < recipeNames.size(); i++) {
            Recipe recipe = getByName(recipeNames.get(i));
            recipeNameIngredientAmountMap.put(recipe.getName(), recipeIngredientService.getByRecipeId(recipe.getId()).size());
        }
        Map<String, Integer> sortedMap = sortMap(recipeNameIngredientAmountMap, Integer.class);
        return transformMapToRecipeList(sortedMap);
    }

    public List<Recipe> getRecipesByPreparationTimeOrdered(List<String> recipeNames) throws SQLException {
        Map<String, Integer> recipeNamePreparationTimeMap = new HashMap();
        for (int i = 0; i < recipeNames.size(); i++) {
            List<Recipe> recipe = recipeRepo.transform(recipeRepo.getByName(recipeNames.get(i)));
            recipeNamePreparationTimeMap.put(recipe.get(0).getName(), recipe.get(0).getPreperationTimeInMinute());
        }
        Map<String, Integer> sortedMap = sortMap(recipeNamePreparationTimeMap, Integer.class);
        return transformMapToRecipeList(sortedMap);
    }

    public Float getTotalPriceOfRecipe(Recipe recipe) throws SQLException {
        recipeIngredientService = RecipeIngredientService.getInstance();
        List<RecipeIngredient> recipeIngredients = recipeIngredientService.getByRecipeId(recipe.getId());
        Float totalRecipePrice = 0F;
        for (RecipeIngredient recipeIngredient : recipeIngredients) {
            Float recipeIngredientPrice = recipeIngredient.getUsingAmount() * ingredientService.getById(recipeIngredient.getIngredientId()).getUnitPrice();
            totalRecipePrice += recipeIngredientPrice;
        }
        return totalRecipePrice;
    }

    private <T extends Number> Map<String, T> sortMap(Map<String, T> notSortedMap, Class<T> clazz) {
        Collection<T> values = notSortedMap.values();
        Iterator<T> iterator = values.iterator();
        int counter = 0;
        Map<String, T> sortedMap = new HashMap();
        if (clazz.equals(Float.class)) {
            Float[] valueArray = new Float[values.size()];
            while (iterator.hasNext()) {
                valueArray[counter] = iterator.next().floatValue();
                counter++;
            }
            Arrays.sort(valueArray);
            for (int i = 0; i < valueArray.length; i++) {
                sortedMap.put(getKeyAndRemoveFromMap(notSortedMap, (T) valueArray[i], clazz), (T) valueArray[i]);
            }
        } else {
            Integer[] valueArray = new Integer[values.size()];
            while (iterator.hasNext()) {
                valueArray[counter] = iterator.next().intValue();
                counter++;
            }
            Arrays.sort(valueArray);
            for (int i = 0; i < valueArray.length; i++) {
                String key = getKeyAndRemoveFromMap(notSortedMap, (T) valueArray[i], clazz);
                sortedMap.put(key, (T) valueArray[i]);
            }
//            for (Map.Entry<String, T> entry : sortedMap.entrySet()) {
//                System.out.println(entry.getKey() + " : " + entry.getValue());
//            }
        }
        return sortedMap;
    }

    private <T extends Number> String getKeyAndRemoveFromMap(Map<String, T> map, T value, Class<T> clazz) {
        String result = null;
        if (clazz.equals(Float.class)) {
            for (Map.Entry<String, T> entry : map.entrySet()) {
                if (entry.getValue().floatValue() == value.floatValue()) {
                    result = entry.getKey();
                    map.remove(result);
                    break;
                }
            }
        } else {
            for (Map.Entry<String, T> entry : map.entrySet()) {
                if (entry.getValue().intValue() == value.intValue()) {
                    result = entry.getKey();
                    map.remove(result);
                    break;
                }
            }
        }
        return result;
    }

    private <T extends Number> List<Recipe> transformMapToRecipeList(Map<String, T> map) throws SQLException {
        List<Recipe> result = new LinkedList();
        for (Map.Entry<String, T> entry : map.entrySet()) {
            Recipe recipe = getByName(entry.getKey());
            result.add(recipe);
        }
        return result;
    }

}
