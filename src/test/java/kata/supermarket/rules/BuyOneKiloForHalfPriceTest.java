package kata.supermarket.rules;

import kata.supermarket.Item;
import kata.supermarket.WeighedProduct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyOneKiloForHalfPriceTest {
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void discountOfHalfPricePerKgForAGroupOfItems(String description, String expectedDiscount, String discountableGroup, List<Item> items) {
        BuyOneKiloForHalfPrice buyOneKiloForHalfPrice = new BuyOneKiloForHalfPrice(discountableGroup);
        BigDecimal discount = buyOneKiloForHalfPrice.calculateDiscount(items);
        assertEquals(new BigDecimal(expectedDiscount), discount);
    }

    private static Stream<Arguments> discountOfHalfPricePerKgForAGroupOfItems() {
        return Stream.of(
                Arguments.of("No discount applicable for group", "0.00", "vegetables", Collections.singletonList(aPackOfPickAndMix(BigDecimal.TEN))),
                Arguments.of("Discount applicable for carrots", "0.50", "vegetables", Collections.singletonList(aPackOfCarrots(BigDecimal.ONE))),
                Arguments.of("Discount applicable for carrots and onion", "20.50", "vegetables", Arrays.asList(aPackOfCarrots(BigDecimal.ONE), aPackOfOnion(BigDecimal.TEN))),
                Arguments.of("Discount applicable for carrots and onion and not sweets", "20.50", "vegetables", Arrays.asList(aPackOfCarrots(BigDecimal.ONE), aPackOfOnion(BigDecimal.TEN), aPackOfPickAndMix(BigDecimal.TEN)))
        );
    }

    private static Item aPackOfCarrots(BigDecimal weightInKilos) {
        return createWeighedItem("carrots", "vegetables", BigDecimal.valueOf(1), weightInKilos);
    }

    private static Item aPackOfOnion(BigDecimal weightInKilos) {
        return createWeighedItem("onion", "vegetables", BigDecimal.valueOf(4), weightInKilos);
    }

    private static Item aPackOfPickAndMix(BigDecimal weightInKilos) {
        return createWeighedItem("pickAndMix", "sweets", BigDecimal.valueOf(5), weightInKilos);
    }

    private static Item createWeighedItem(String productId, String group, BigDecimal pricePerKilo, BigDecimal weightInKilos) {
        final WeighedProduct weighedProduct = new WeighedProduct(productId, group, pricePerKilo);
        return weighedProduct.weighing(weightInKilos);
    }

}