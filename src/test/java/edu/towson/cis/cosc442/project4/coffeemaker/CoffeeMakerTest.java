package edu.towson.cis.cosc442.project4.coffeemaker;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoffeeMakerTest {

    private CoffeeMaker coffeeMaker;
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        coffeeMaker = new CoffeeMaker();
        inventory = new Inventory();
    }

    private Recipe createRecipe(String name, int price,
            int coffee, int milk, int sugar, int chocolate) {

        Recipe recipe = new Recipe();
        recipe.setName(name);
        recipe.setPrice(price);
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        return recipe;
    }

    // ADD RECIPE

    @Test
    void addRecipe_addsUniqueRecipe() {
        Recipe recipe = createRecipe("Latte", 10, 2, 2, 1, 0);

        assertTrue(coffeeMaker.addRecipe(recipe));
    }

    @Test
    void addRecipe_rejectsDuplicateRecipe() {
        Recipe first = createRecipe("Latte", 10, 2, 2, 1, 0);
        Recipe duplicate = createRecipe("Latte", 15, 3, 3, 2, 0);

        assertTrue(coffeeMaker.addRecipe(first));
        assertFalse(coffeeMaker.addRecipe(duplicate));
    }

    @Test
    void addRecipe_rejectsRecipeWhenCapacityIsFull() {
        for (int i = 0; i < 4; i++) {
            Recipe recipe = createRecipe(
                "Recipe" + i, 10, 1, 1, 1, 1
            );

            assertTrue(coffeeMaker.addRecipe(recipe));
        }

        Recipe fifthRecipe =
            createRecipe("Recipe5", 10, 1, 1, 1, 1);

        assertFalse(coffeeMaker.addRecipe(fifthRecipe));
    }

    // DELETE RECIPE

    @Test
    void deleteRecipe_deletesExistingRecipe() {
        Recipe recipe = createRecipe("Latte", 10, 2, 2, 1, 0);
        coffeeMaker.addRecipe(recipe);

        assertTrue(coffeeMaker.deleteRecipe(recipe));

        assertNull(coffeeMaker.getRecipeForName("Latte").getName());
    }

    @Test
    void deleteRecipe_returnsFalseWhenRecipeDoesNotExist() {
        Recipe recipe = createRecipe("Latte", 10, 2, 2, 1, 0);

        assertFalse(coffeeMaker.deleteRecipe(recipe));
    }

    // EDIT RECIPE

    @Test
    void editRecipe_updatesExistingRecipe() {
        Recipe original = createRecipe("Latte", 10, 2, 2, 1, 0);
        Recipe updated = createRecipe("Latte", 15, 3, 3, 2, 0);

        coffeeMaker.addRecipe(original);

        assertTrue(coffeeMaker.editRecipe(original, updated));

        Recipe result = coffeeMaker.getRecipeForName("Latte");

        assertEquals(15, result.getPrice());
        assertEquals(3, result.getAmtCoffee());
    }

    @Test
    void editRecipe_returnsFalseWhenRecipeDoesNotExist() {
        Recipe original = createRecipe("Missing", 10, 1, 1, 1, 1);
        Recipe updated = createRecipe("Missing", 15, 2, 2, 2, 2);

        assertFalse(coffeeMaker.editRecipe(original, updated));
    }

    @Test
    void editRecipe_doesNotAllowRecipeNameToChange() {
        Recipe original = createRecipe("Latte", 10, 2, 2, 1, 0);
        Recipe updated = createRecipe("Mocha", 15, 3, 3, 2, 1);

        coffeeMaker.addRecipe(original);

        assertFalse(coffeeMaker.editRecipe(original, updated));
    }

    // ADD INVENTORY

    @Test
    void addInventory_addsPositiveAndZeroQuantities() {
        coffeeMaker.addInventory(2, 3, 0, 4);

        inventory = coffeeMaker.checkInventory();

        assertEquals(17, inventory.getCoffee());
        assertEquals(18, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(19, inventory.getChocolate());
    }

    @Test
    void addInventory_rejectsNegativeCoffee() {
        coffeeMaker.addInventory(-1, 0, 0, 0);

        assertEquals(15, inventory.getCoffee());
    }

    @Test
    void addInventory_rejectsNegativeMilk() {
        coffeeMaker.addInventory(0, -1, 0, 0);

        assertEquals(15, inventory.getMilk());
    }

    @Test
    void addInventory_rejectsNegativeSugar() {
        coffeeMaker.addInventory(0, 0, -1, 0);

        assertEquals(15, inventory.getSugar());
    }

    @Test
    void addInventory_rejectsNegativeChocolate() {
        coffeeMaker.addInventory(0, 0, 0, -1);

        assertEquals(15, inventory.getChocolate());
    }

    // MAKE COFFEE

    @Test
    void makeCoffee_returnsPaymentWhenPaymentIsBelowPrice() {
        Recipe recipe = createRecipe("Latte", 10, 1, 1, 1, 1);

        assertEquals(9, coffeeMaker.makeCoffee(recipe, 9));
    }

    @Test
    void makeCoffee_returnsZeroChangeWhenPaymentEqualsPrice() {
        Recipe recipe = createRecipe("Latte", 10, 1, 1, 1, 1);

        assertEquals(0, coffeeMaker.makeCoffee(recipe, 10));
    }

    @Test
    void makeCoffee_returnsChangeWhenPaymentExceedsPrice() {
        Recipe recipe = createRecipe("Latte", 10, 1, 1, 1, 1);

        assertEquals(5, coffeeMaker.makeCoffee(recipe, 15));
    }

    @Test
    void makeCoffee_returnsPaymentWhenIngredientsAreInsufficient() {
        Recipe recipe = createRecipe("Latte", 10, 1, 1, 1, 1);

        inventory.setCoffee(0);

        assertEquals(15, coffeeMaker.makeCoffee(recipe, 15));
    }

    @Test
    void makeCoffee_deductsIngredientsAfterSuccessfulPurchase() {
        Recipe recipe = createRecipe("Latte", 10, 2, 3, 1, 2);

        assertEquals(0, coffeeMaker.makeCoffee(recipe, 10));

        inventory = coffeeMaker.checkInventory();

        assertEquals(13, inventory.getCoffee());
        assertEquals(12, inventory.getMilk());
        assertEquals(14, inventory.getSugar());
        assertEquals(13, inventory.getChocolate());
    }

    // RECIPE LOOKUP

    @Test
    void getRecipeForName_returnsRecipeWhenItExists() {
        Recipe recipe = createRecipe("Latte", 10, 2, 2, 1, 0);
        coffeeMaker.addRecipe(recipe);

        assertEquals("Latte",
            coffeeMaker.getRecipeForName("Latte").getName());
    }

    @Test
    void getRecipeForName_returnsEmptyRecipeWhenItDoesNotExist() {
        Recipe result = coffeeMaker.getRecipeForName("Missing");

        assertNull(result.getName());
    }

    // OTHER METHODS

    @Test
    void getRecipes_returnsRecipeArray() {
        Recipe recipe = createRecipe("Latte", 10, 2, 2, 1, 0);
        coffeeMaker.addRecipe(recipe);

        Recipe[] recipes = coffeeMaker.getRecipes();

        assertNotNull(recipes);
        assertEquals(4, recipes.length);
    }

    @Test
    void checkInventory_returnsInventory() {
        inventory = coffeeMaker.checkInventory();

        assertNotNull(inventory);
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }
}
