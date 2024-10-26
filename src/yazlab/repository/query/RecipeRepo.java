/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.repository.query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.LinkedList;
import java.util.List;
import yazlab.model.BaseEntity;
import yazlab.model.Category;
import yazlab.model.Recipe;
import yazlab.repository.connection.DbValues;

/**
 *
 * @author kaan
 */
public class RecipeRepo extends BaseQuerier {

    private static RecipeRepo recipeRepo;

    private RecipeRepo() {

    }

    public static RecipeRepo getInstance() {
        if (recipeRepo == null) {
            recipeRepo = new RecipeRepo();
        }
        return recipeRepo;
    }

    @Override
    public ResultSet makeGettingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByIdSqlCommand());
        pStatement.setLong(1, id);
        return pStatement.executeQuery();
    }

    @Override
    public void makeDeletingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getDeletingByIdSqlCommand());
        pStatement.setLong(1, id);
        pStatement.executeUpdate();
    }

    public void deleteByName(String name) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getDeletingByNameColumnSqlCommand());
        pStatement.setString(1, name);
        pStatement.executeUpdate();
    }

    public void makeUpdatingQuery(String name, Category category, Integer preperationTime, String instruction, Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getUpdatingByIdSqlCommand());
        pStatement.setString(1, name);
        pStatement.setString(2, category.name());
        pStatement.setInt(3, preperationTime);
        pStatement.setString(4, instruction);
        pStatement.setLong(5, id);
        pStatement.executeUpdate();
    }

    public ResultSet getByName(String name) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByNameSqlCommand());
        pStatement.setString(1, name);
        return pStatement.executeQuery();
    }
    
    public ResultSet getByMaxPreparationTime (Integer maxPreparationTime) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByMaxPrepartionTimeSqlCommand());
        pStatement.setInt(1, maxPreparationTime);
        return pStatement.executeQuery();
    }

    public ResultSet getByCategory(Category category) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByCategorySqlCommand());
        pStatement.setString(1, category.name());
        return pStatement.executeQuery();
    }

    public ResultSet getAll() throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingAllSqlCommand());
        return pStatement.executeQuery();
    }

    public void create(String name, Category category, Integer preperationTime, String instruction) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getCreatingSqlCommand());
        pStatement.setString(1, name);
        pStatement.setString(2, category.name());
        pStatement.setInt(3, preperationTime);
        pStatement.setString(4, instruction);
        pStatement.executeUpdate();
    }

    public ResultSet getByNameAndPreparationTimeAndCategory(String name, Integer preparationTime, String categoryName) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByNameAndPreparationTimeAndCategory());
        pStatement.setString(1, name);
        pStatement.setInt(2, preparationTime);
        pStatement.setString(3, categoryName);
        return pStatement.executeQuery();
    }
    
    public ResultSet getByPreparationTimeAndCategory(Integer preparationTime, String categoryName) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByPreparationTimeAndCategory());
        pStatement.setInt(1, preparationTime);
        pStatement.setString(2, categoryName);
        return pStatement.executeQuery();
    }
    
    public ResultSet getByNameAndPreparationTime(String name, Integer preparationTime) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByNameAndPreparationTime());
        pStatement.setString(1, name);
        pStatement.setInt(2, preparationTime);
        return pStatement.executeQuery();
    }
    
    public ResultSet getByNameAndCategory(String name, String categoryName) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Recipes.getShowingByNameAndCategory());
        pStatement.setString(1, name);
        pStatement.setString(2, categoryName);
        return pStatement.executeQuery();
    }

    @Override
    public List<Recipe> transform(ResultSet resultSet) throws SQLException {
        List<Recipe> result = new LinkedList();
        while (resultSet.next()) {
            Recipe recipe = new Recipe();
            recipe.setId(resultSet.getLong(DbValues.TableValues.getPrimaryKeyColumnName()));
            recipe.setCategory(Category.valueOf(resultSet.getString(DbValues.TableValues.Recipes.getCategoryColumnName())));
            recipe.setName(resultSet.getString(DbValues.TableValues.Recipes.getNameColumnName()));
            recipe.setPreperationTimeInMinute(resultSet.getInt(DbValues.TableValues.Recipes.getPreperationTimeColumnName()));
            recipe.setInstructions(resultSet.getString(DbValues.TableValues.Recipes.getInstructionsColumnName()));
            result.add(recipe);
        }
        return result;
    }

}
