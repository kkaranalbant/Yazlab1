/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package yazlab.repository.connection;

/**
 *
 * @author kaan
 */
public class DbValues {

    public static class ConnectionValues {

        private static final String RDMS_NAME;
        private static final String HOST;
        private static final String DB_NAME;
        private static final Integer PORT;
        private static final String USERNAME;
        private static final String PASSWORD;

        static {
            RDMS_NAME = "mysql";
            HOST = "localhost";
            DB_NAME = "yazlab1";
            PORT = 3306;
            USERNAME = "root";
            PASSWORD = "254236";
        }

        public static String getRDMS_NAME() {
            return RDMS_NAME;
        }

        public static String getHOST() {
            return HOST;
        }

        public static String getDB_NAME() {
            return DB_NAME;
        }

        public static Integer getPORT() {
            return PORT;
        }

        public static String getUSERNAME() {
            return USERNAME;
        }

        public static String getPASSWORD() {
            return PASSWORD;
        }
    }

    public static class TableValues {

        private static final String primaryKeyColumnName;

        static {
            primaryKeyColumnName = "id";
        }

        public static class Ingredients {

            private static final String tableName;
            private static final String nameColumnName;
            private static final String amountColumnName;
            private static final String unitColumnName;
            private static final String unitPriceColumnName;

            static {
                tableName = "ingredients";
                nameColumnName = "name";
                amountColumnName = "amount";
                unitColumnName = "unit";
                unitPriceColumnName = "unit_price";
            }

            public static String getCreatingSqlCommand() {
                return "insert into " + tableName + " (" + nameColumnName + "," + amountColumnName + "," + unitColumnName + "," + unitPriceColumnName + ") values (?,?,?,?) ;";
            }

            public static String getDeletingByIdSqlCommand() {
                return "delete from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getDeletingByNameColumnSqlCommand() {
                return "delete from " + tableName + " where " + nameColumnName + " = ? ;";
            }

            public static String getUpdatingByIdSqlCommand() {
                return "update " + tableName + " set " + nameColumnName + " = ? , " + amountColumnName + " = ? , " + unitColumnName + " = ? ," + unitPriceColumnName + " = ? where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingByIdSqlCommand() {
                return "select * from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingByNameSqlCommand() {
                return "select * from " + tableName + " where " + nameColumnName + " = ? ;";
            }

            public static String getShowingAllSqlCommand() {
                return "select * from " + tableName + " where 1 ;";
            }

            public static String getTableName() {
                return tableName;
            }

            public static String getNameColumnName() {
                return nameColumnName;
            }

            public static String getAmountColumnName() {
                return amountColumnName;
            }

            public static String getUnitColumnName() {
                return unitColumnName;
            }

            public static String getUnitPriceColumnName() {
                return unitPriceColumnName;
            }

        }

        public static class Recipes {

            private static final String tableName;
            private static final String nameColumnName;
            private static final String categoryColumnName;
            private static final String preperationTimeColumnName;
            private static final String instructionsColumnName;

            static {
                tableName = "recipes";
                nameColumnName = "name";
                categoryColumnName = "category";
                preperationTimeColumnName = "preperation_time_in_minute";
                instructionsColumnName = "instructions";
            }

            public static String getCreatingSqlCommand() {
                return "insert into " + tableName + " (" + nameColumnName + "," + categoryColumnName + "," + preperationTimeColumnName + "," + instructionsColumnName + ") values (?,?,?,?) ;";
            }

            public static String getDeletingByIdSqlCommand() {
                return "delete from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getDeletingByNameColumnSqlCommand() {
                return "delete from " + tableName + " where " + nameColumnName + " = ? ;";
            }

            public static String getUpdatingByIdSqlCommand() {
                return "update " + tableName + " set " + nameColumnName + " = ? , " + categoryColumnName + " = ? , " + preperationTimeColumnName + " = ? ," + instructionsColumnName + " = ? where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingByIdSqlCommand() {
                return "select * from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingByNameSqlCommand() {
                return "select * from " + tableName + " where " + nameColumnName + " = ? ;";
            }

            public static String getShowingByCategorySqlCommand() {
                return "select * from " + tableName + " where " + categoryColumnName + " = ? ;";
            }

            public static String getShowingAllSqlCommand() {
                return "select * from " + tableName + " where 1 ;";
            }

            public static String getShowingByNameAndPreparationTimeAndCategory() {
                return "select * from " + tableName + " where " + nameColumnName + " = ? and " + preperationTimeColumnName + " <= ? and " + categoryColumnName + " = ? ; ";
            }

            public static String getShowingByNameAndCategory() {
                return "select * from " + tableName + " where " + nameColumnName + " = ? and " + categoryColumnName + " = ? ; ";
            }

            public static String getShowingByNameAndPreparationTime() {
                return "select * from " + tableName + " where " + nameColumnName + " = ? and " + preperationTimeColumnName + " <= ?  ; ";
            }

            public static String getShowingByPreparationTimeAndCategory() {
                return "select * from " + tableName + " where   " + preperationTimeColumnName + " <= ? and " + categoryColumnName + " = ? ; ";
            }
            
            public static String getShowingByMaxPrepartionTimeSqlCommand() {
                return "select * from " + tableName + " where " + preperationTimeColumnName + " <= ? ;";
            }

            public static String getNameColumnName() {
                return nameColumnName;
            }

            public static String getCategoryColumnName() {
                return categoryColumnName;
            }

            public static String getPreperationTimeColumnName() {
                return preperationTimeColumnName;
            }

            public static String getInstructionsColumnName() {
                return instructionsColumnName;
            }

        }

        public static class RecipeIngredients {

            private static final String tableName;
            private static final String recipeIdColumnName;
            private static final String ingredientIdColumnName;
            private static final String usingAmountColumnName;
            private static final String unitColumnName;

            static {
                tableName = "recipe_ingredients";
                recipeIdColumnName = "recipe_id";
                ingredientIdColumnName = "ingredient_id";
                usingAmountColumnName = "using_amount";
                unitColumnName = "unit";
            }

            public static String getCreatingSqlCommand() {
                return "insert into " + tableName + " (" + recipeIdColumnName + "," + ingredientIdColumnName + "," + usingAmountColumnName + "," + unitColumnName + ") values (?,?,?,?) ;";
            }

            public static String getDeletingByIdSqlCommand() {
                return "delete from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getDeletingByRecipeIdColumnSqlCommand() {
                return "delete from " + tableName + " where " + recipeIdColumnName + " = ? ;";
            }

            public static String getDeletingByIngredientIdColumnSqlCommand() {
                return "delete from " + tableName + " where " + ingredientIdColumnName + " = ? ;";
            }

            public static String getDeletingByRecipeIdAndIngredientIdColumnsSqlCommand() {
                return "delete from " + tableName + " where " + recipeIdColumnName + " = ? and " + ingredientIdColumnName + " = ? ;";
            }

            public static String getUpdatingByIdSqlCommand() {
                return "update " + tableName + " set " + recipeIdColumnName + " = ? , " + ingredientIdColumnName + " = ? , " + usingAmountColumnName + " = ? ," + unitColumnName + " = ? where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingByIdSqlCommand() {
                return "select * from " + tableName + " where " + primaryKeyColumnName + " = ? ;";
            }

            public static String getShowingAllSqlCommand() {
                return "select * from " + tableName + " where 1 ;";
            }

            public static String getShowingByRecipeIdAndIngredientIdSqlCommand() {
                return "select * from " + tableName + " where " + recipeIdColumnName + " = ? and " + ingredientIdColumnName + " = ? ;";
            }

            public static String getShowingByRecipeIdSqlCommand() {
                return "select * from " + tableName + " where " + recipeIdColumnName + " = ? ;";
            }

            public static String getShowingByIngredientIdSqlCommand() {
                return "select * from " + tableName + " where " + ingredientIdColumnName + " = ? ;";
            }

            public static String getTableName() {
                return tableName;
            }

            public static String getRecipeIdColumnName() {
                return recipeIdColumnName;
            }

            public static String getIngredientIdColumnName() {
                return ingredientIdColumnName;
            }

            public static String getUsingAmountColumnName() {
                return usingAmountColumnName;
            }

            public static String getUnitColumnName() {
                return unitColumnName;
            }

        }

        public static String getPrimaryKeyColumnName() {
            return primaryKeyColumnName;
        }

    }

}
