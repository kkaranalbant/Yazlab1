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
import yazlab.model.RecipeIngredient;
import yazlab.model.Unit;
import yazlab.repository.connection.DbValues;

/**
 *
 * @author kaan
 */
public class RecipeIngredientRepo extends BaseQuerier {

    private static RecipeIngredientRepo recipeIngredientRepo;

    private RecipeIngredientRepo() {

    }

    public static RecipeIngredientRepo getInstance() {
        if (recipeIngredientRepo == null) {
            recipeIngredientRepo = new RecipeIngredientRepo();
        }
        return recipeIngredientRepo;
    }

    @Override
    public ResultSet makeGettingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getShowingByIdSqlCommand());
        pStatement.setLong(1, id);
        return pStatement.executeQuery();
    }

    @Override
    public void makeDeletingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getDeletingByIdSqlCommand());
        pStatement.setLong(1, id);
        pStatement.executeUpdate();
    }

    public void deleteByRecipeId(Long recipeId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getDeletingByRecipeIdColumnSqlCommand());
        pStatement.setLong(1, recipeId);
        pStatement.executeUpdate();
    }

    public void deleteByIngredientId(Long ingredientId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getDeletingByIngredientIdColumnSqlCommand());
        pStatement.setLong(1, ingredientId);
        pStatement.executeUpdate();
    }

    public void deleteByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getDeletingByRecipeIdAndIngredientIdColumnsSqlCommand());
        pStatement.setLong(1, recipeId);
        pStatement.setLong(2, ingredientId);
        pStatement.executeUpdate();
    }

    public ResultSet getAll() throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getShowingAllSqlCommand());
        return pStatement.executeQuery();
    }

    public ResultSet getByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getShowingByRecipeIdAndIngredientIdSqlCommand());
        pStatement.setLong(1, recipeId);
        pStatement.setLong(2, ingredientId);
        return pStatement.executeQuery();
    }

    public void update(Long recipeId, Long ingredientId, Float usingAmount, Unit unit, Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getUpdatingByIdSqlCommand());
        pStatement.setLong(1, recipeId);
        pStatement.setLong(2, ingredientId);
        pStatement.setFloat(3, usingAmount);
        pStatement.setString(4, unit.name());
        pStatement.setLong(5, id);
        pStatement.executeUpdate();
    }

    public void create(Long recipeId, Long ingredientId, Float usingAmount, Unit unit) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getCreatingSqlCommand());
        pStatement.setLong(1, recipeId);
        pStatement.setLong(2, ingredientId);
        pStatement.setFloat(3, usingAmount);
        pStatement.setString(4, unit.name());
        pStatement.executeUpdate();
    }

    public ResultSet getByRecipeId(Long recipeId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getShowingByRecipeIdSqlCommand());
        pStatement.setLong(1, recipeId);
        return pStatement.executeQuery() ;
    }

    public ResultSet getByIngredientId(Long ingredientId) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.RecipeIngredients.getShowingByIngredientIdSqlCommand());
        pStatement.setLong(1, ingredientId);
        return pStatement.executeQuery() ;
    }


    @Override
    public List<RecipeIngredient> transform(ResultSet resultSet) throws SQLException {
        List<RecipeIngredient> result = new LinkedList();
        while (resultSet.next()) {
            RecipeIngredient recipeIngredient = new RecipeIngredient();
            recipeIngredient.setId(resultSet.getLong(DbValues.TableValues.getPrimaryKeyColumnName()));
            recipeIngredient.setIngredientId(resultSet.getLong(DbValues.TableValues.RecipeIngredients.getIngredientIdColumnName()));
            recipeIngredient.setRecipeId(resultSet.getLong(DbValues.TableValues.RecipeIngredients.getRecipeIdColumnName()));
            recipeIngredient.setUnit(Unit.valueOf(resultSet.getString(DbValues.TableValues.RecipeIngredients.getUnitColumnName())));
            recipeIngredient.setUsingAmount(resultSet.getFloat(DbValues.TableValues.RecipeIngredients.getUsingAmountColumnName()));
            result.add(recipeIngredient);
        }
        return result;
    }

}
