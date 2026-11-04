package edu.towson.cis.cosc442.project4.coffeemaker;

import java.util.function.BiConsumer;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RecipeTest {

    static Stream<Arguments> numericProperties() {
        return Stream.of(
            Arguments.of(
                (BiConsumer<Recipe, Integer>) Recipe::setPrice,
                (ToIntFunction<Recipe>) Recipe::getPrice
            ),
            Arguments.of(
                (BiConsumer<Recipe, Integer>) Recipe::setAmtCoffee,
                (ToIntFunction<Recipe>) Recipe::getAmtCoffee
            ),
            Arguments.of(
                (BiConsumer<Recipe, Integer>) Recipe::setAmtMilk,
                (ToIntFunction<Recipe>) Recipe::getAmtMilk
            ),
            Arguments.of(
                (BiConsumer<Recipe, Integer>) Recipe::setAmtSugar,
                (ToIntFunction<Recipe>) Recipe::getAmtSugar
            ),
            Arguments.of(
                (BiConsumer<Recipe, Integer>) Recipe::setAmtChocolate,
                (ToIntFunction<Recipe>) Recipe::getAmtChocolate
            )
        );
    }

    @ParameterizedTest
    @MethodSource("numericProperties")
    void numericProperties_handleNegativeValues(
            BiConsumer<Recipe, Integer> setter,
            ToIntFunction<Recipe> getter) {

        Recipe recipe = new Recipe();

        setter.accept(recipe, -1);

        assertEquals(0, getter.applyAsInt(recipe));
    }

    @ParameterizedTest
    @MethodSource("numericProperties")
    void numericProperties_acceptZero(
            BiConsumer<Recipe, Integer> setter,
            ToIntFunction<Recipe> getter) {

        Recipe recipe = new Recipe();

        setter.accept(recipe, 0);

        assertEquals(0, getter.applyAsInt(recipe));
    }

    @ParameterizedTest
    @MethodSource("numericProperties")
    void numericProperties_acceptPositiveValues(
            BiConsumer<Recipe, Integer> setter,
            ToIntFunction<Recipe> getter) {

        Recipe recipe = new Recipe();

        setter.accept(recipe, 10);

        assertEquals(10, getter.applyAsInt(recipe));
    }

    @Test
    void equals_recipesWithSameName_returnsTrue() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();

        first.setName("Latte");
        second.setName("Latte");

        assertTrue(first.equals(second));
    }

    @Test
    void equals_recipesWithDifferentNames_returnsFalse() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();

        first.setName("Latte");
        second.setName("Mocha");

        assertFalse(first.equals(second));
    }

    @Test
    void equals_nullNames_returnsFalse() {
        Recipe first = new Recipe();
        Recipe second = new Recipe();

        first.setName("Latte");

        assertFalse(first.equals(second));
        assertFalse(second.equals(first));
    }

    @Test
    void toString_returnsRecipeName() {
        Recipe recipe = new Recipe();
        recipe.setName("Latte");

        assertEquals("Latte", recipe.toString());
    }
}