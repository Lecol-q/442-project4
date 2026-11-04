package edu.towson.cis.cosc442.project4.coffeemaker;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void setCoffee_boundaryValues() {
        inventory.setCoffee(-1);
        assertEquals(0, inventory.getCoffee());

        inventory.setCoffee(0);
        assertEquals(0, inventory.getCoffee());

        inventory.setCoffee(1);
        assertEquals(1, inventory.getCoffee());
    }

    @Test
    void setMilk_boundaryValues() {
        inventory.setMilk(-1);
        assertEquals(0, inventory.getMilk());

        inventory.setMilk(0);
        assertEquals(0, inventory.getMilk());

        inventory.setMilk(1);
        assertEquals(1, inventory.getMilk());
    }

    @Test
    void setSugar_boundaryValues() {
        inventory.setSugar(-1);
        assertEquals(0, inventory.getSugar());

        inventory.setSugar(0);
        assertEquals(0, inventory.getSugar());

        inventory.setSugar(1);
        assertEquals(1, inventory.getSugar());
    }

    @Test
    void setChocolate_boundaryValues() {
        inventory.setChocolate(-1);
        assertEquals(0, inventory.getChocolate());

        inventory.setChocolate(0);
        assertEquals(0, inventory.getChocolate());

        inventory.setChocolate(1);
        assertEquals(1, inventory.getChocolate());
    }

    @Test
    void constructor_initializesInventoryToFifteen() {
        assertEquals(15, inventory.getCoffee());
        assertEquals(15, inventory.getMilk());
        assertEquals(15, inventory.getSugar());
        assertEquals(15, inventory.getChocolate());
    }

    @Test
    void enoughIngredients_returnsTrueWhenIngredientsAreAvailable() {
        Recipe recipe = createRecipe(2, 2, 2, 2);

        assertTrue(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredients_returnsTrueAtExactIngredientAmount() {
        Recipe recipe = createRecipe(15, 15, 15, 15);

        assertTrue(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredients_returnsFalseWhenCoffeeIsInsufficient() {
        Recipe recipe = createRecipe(16, 1, 1, 1);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredients_returnsFalseWhenMilkIsInsufficient() {
        Recipe recipe = createRecipe(1, 16, 1, 1);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredients_returnsFalseWhenSugarIsInsufficient() {
        Recipe recipe = createRecipe(1, 1, 16, 1);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void enoughIngredients_returnsFalseWhenChocolateIsInsufficient() {
        Recipe recipe = createRecipe(1, 1, 1, 16);

        assertFalse(inventory.enoughIngredients(recipe));
    }

    @Test
    void toString_returnsFormattedInventory() {
        String expected =
            "Coffee: 15\n" +
            "Milk: 15\n" +
            "Sugar: 15\n" +
            "Chocolate: 15\n";

        assertEquals(expected, inventory.toString());
    }

    private Recipe createRecipe(int coffee, int milk, int sugar, int chocolate) {
        Recipe recipe = new Recipe();
        recipe.setAmtCoffee(coffee);
        recipe.setAmtMilk(milk);
        recipe.setAmtSugar(sugar);
        recipe.setAmtChocolate(chocolate);
        return recipe;
    }

    @Test
    void setSugar_acceptsZero() {
        inventory.setSugar(0);

        assertEquals(0, inventory.getSugar());
    }
}
