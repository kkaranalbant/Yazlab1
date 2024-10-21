/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.repository.query;

import java.sql.SQLException;
import yazlab.repository.connection.DbValues;
import java.sql.PreparedStatement;
import yazlab.model.Unit;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import yazlab.model.BaseEntity;
import yazlab.model.Ingredient;

/**
 *
 * @author kaan
 */
public class IngredientRepo extends BaseQuerier {
    
    private static IngredientRepo ingredientRepo ;
    
    private IngredientRepo () {
        
    }
    
    public static IngredientRepo getInstance () {
        if (ingredientRepo == null) {
            ingredientRepo = new IngredientRepo () ;
        }
        return ingredientRepo ;
    }

    @Override
    public ResultSet makeGettingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getShowingByIdSqlCommand());
        pStatement.setLong(1, id);
        return pStatement.executeQuery();
    }

    @Override
    public void makeDeletingQueryById(Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getDeletingByIdSqlCommand());
        pStatement.setLong(1, id);
        pStatement.executeUpdate();
    }

    public void makeUpdatingQuery(String name, Float amount, Unit unit, Float unitPrice, Long id) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getUpdatingByIdSqlCommand());
        pStatement.setString(1, name);
        pStatement.setFloat(2, amount);
        pStatement.setString(3, unit.name());
        pStatement.setFloat(4, unitPrice);
        pStatement.setLong(5, id);
        pStatement.executeUpdate();
    }

    public void makeDeleteByName(String name) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getDeletingByNameColumnSqlCommand());
        pStatement.setString(1, name);
        pStatement.executeUpdate();
    }

    public void create(String name, Float amount, Unit unit, Float unitPrice) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getCreatingSqlCommand());
        pStatement.setString(1, name);
        pStatement.setFloat(2, amount);
        pStatement.setString(3, unit.name());
        pStatement.setFloat(4, unitPrice);
        pStatement.executeUpdate();
    }

    public ResultSet getAll() throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getShowingAllSqlCommand());
        return pStatement.executeQuery();
    }

    public ResultSet getByName(String name) throws SQLException {
        PreparedStatement pStatement = getPreparedStatement(DbValues.TableValues.Ingredients.getShowingByNameSqlCommand());
        pStatement.setString(1, name);
        return pStatement.executeQuery();
    }

    @Override
    public List<Ingredient> transform(ResultSet resultSet) throws SQLException {
        List<Ingredient> results = new LinkedList();
        while (resultSet.next()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(resultSet.getLong(DbValues.TableValues.getPrimaryKeyColumnName()));
            ingredient.setName(resultSet.getString(DbValues.TableValues.Ingredients.getNameColumnName()));
            ingredient.setAmount(resultSet.getFloat(DbValues.TableValues.Ingredients.getAmountColumnName()));
            ingredient.setUnit(Unit.valueOf(resultSet.getString(DbValues.TableValues.Ingredients.getUnitColumnName())));
            ingredient.setUnitPrice(resultSet.getFloat(DbValues.TableValues.Ingredients.getUnitPriceColumnName()));
            results.add(ingredient);
        }
        return results;
    }

}
