package yazlab.service;

import yazlab.model.Unit;

/**
 *
 * @author kaan
 */
public class UnitAndPriceConversation {

    public static Float calculatePrice(Unit ingredientUnit, Float ingredientUnitPrice, Unit recipeIngredientUnit, Float recipeIngredientUsingAmount) {
        Float convertedUsingAmount = makeUsingAmountConvertingByUnit(recipeIngredientUnit, recipeIngredientUsingAmount);
        Float convertedUnitPrice = makeUnitPriceConvertingByUnit(ingredientUnit, ingredientUnitPrice);
        return convertedUsingAmount * convertedUnitPrice;
    }

    public static boolean isSufficentUsingAmount(Unit ingredientUnit, Float ingredientAmount, Unit recipeIngredientUnit, Float recipeIngredientAmount) {
        if (ingredientUnit.equals(Unit.KILOGRAM)) {
            ingredientAmount *= 1000000;
        } else if (ingredientUnit.equals(Unit.GRAM)) {
            ingredientAmount *= 1000;
        } else if (ingredientUnit.equals(Unit.LITER)) {
            ingredientAmount *= 1000000;
        }
        if (recipeIngredientUnit.equals(Unit.KILOGRAM)) {
            recipeIngredientAmount *= 1000000;
        } else if (recipeIngredientUnit.equals(Unit.GRAM)) {
            recipeIngredientAmount *= 1000;
        } else if (recipeIngredientUnit.equals(Unit.LITER)) {
            recipeIngredientAmount *= 1000000;
        }
        return ingredientAmount >= recipeIngredientAmount;
    }

    private static Float makeUsingAmountConvertingByUnit(Unit unit, Float usingAmount) {
        if (unit.equals(Unit.KILOGRAM)) {
            usingAmount *= 1000000;
        } else if (unit.equals(Unit.GRAM)) {
            usingAmount *= 1000;
        } else if (unit.equals(Unit.LITER)) {
            usingAmount *= 1000000;
        }
        return usingAmount;
    }

    private static Float makeUnitPriceConvertingByUnit(Unit unit, Float unitPrice) {
        if (unit.equals(Unit.KILOGRAM)) {
            unitPrice /= 1000000;
        } else if (unit.equals(Unit.GRAM)) {
            unitPrice /= 1000;
        } else if (unit.equals(Unit.LITER)) {
            unitPrice /= 1000000;
        }
        return unitPrice;
    }

}
