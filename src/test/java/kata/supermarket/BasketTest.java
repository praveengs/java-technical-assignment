package kata.supermarket;

import kata.supermarket.rules.BuyOneGetOneDiscountRule;
import kata.supermarket.rules.BuyTwoForOnePoundDiscountRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BasketTest {

    @DisplayName("basket provides its total value when containing...")
    @MethodSource
    @ParameterizedTest(name = "{0}")
    void basketProvidesTotalValue(String description, List<DiscountRule> discountRules, String expectedTotal, Iterable<Item> items) {
        final Basket basket = new Basket(discountRules);
        items.forEach(basket::add);
        assertEquals(new BigDecimal(expectedTotal), basket.total());
    }

    static Stream<Arguments> basketProvidesTotalValue() {
        return Stream.of(
                noItems(),
                aSingleItemPricedPerUnit(),
                multipleItemsPricedPerUnit(),
                aSingleItemPricedByWeight(),
                multipleItemsPricedByWeight(),
                multipleItemsWithDiscount()
        );
    }

    private static Arguments aSingleItemPricedByWeight() {
        return Arguments.of("a single weighed item", new ArrayList<>(), "1.25", Collections.singleton(twoFiftyGramsOfAmericanSweets()));
    }

    private static Arguments multipleItemsPricedByWeight() {
        return Arguments.of("multiple weighed items", new ArrayList<>(), "1.85",
                Arrays.asList(twoFiftyGramsOfAmericanSweets(), twoHundredGramsOfPickAndMix())
        );
    }

    private static Arguments multipleItemsWithDiscount() {
        return Arguments.of("items with discounts", createAllDiscountRules(), "1.49",
                Arrays.asList(aPintOfMilk(), aPintOfMilk(),
                       aCake(), aCake()
                ));
    }

    private static List<DiscountRule> createAllDiscountRules() {
        return Arrays.asList(new BuyOneGetOneDiscountRule("Milk"),
                new BuyTwoForOnePoundDiscountRule("Cake"));
    }

    private static Arguments multipleItemsPricedPerUnit() {
        return Arguments.of("multiple items priced per unit", new ArrayList<>(), "2.04",
                Arrays.asList(aPackOfDigestives(), aPintOfMilk()));
    }

    private static Arguments aSingleItemPricedPerUnit() {
        return Arguments.of("a single item priced per unit", new ArrayList<>(), "0.49", Collections.singleton(aPintOfMilk()));
    }

    private static Arguments noItems() {
        return Arguments.of("no items", new ArrayList<>(), "0.00", Collections.emptyList());
    }

    private static Item aPintOfMilk() {
        return new UnitProduct("Milk", new BigDecimal("0.49")).oneOf();
    }

    private static Item aCake() {
        return new UnitProduct("Cake", new BigDecimal("2.50")).oneOf();
    }

    private static Item aPackOfDigestives() {
        return new UnitProduct("Digestive", new BigDecimal("1.55")).oneOf();
    }

    private static WeighedProduct aKiloOfAmericanSweets() {
        return new WeighedProduct("AmericanSweets", new BigDecimal("4.99"));
    }

    private static Item twoFiftyGramsOfAmericanSweets() {
        return aKiloOfAmericanSweets().weighing(new BigDecimal(".25"));
    }

    private static WeighedProduct aKiloOfPickAndMix() {
        return new WeighedProduct("PickAndMix", new BigDecimal("2.99"));
    }

    private static Item twoHundredGramsOfPickAndMix() {
        return aKiloOfPickAndMix().weighing(new BigDecimal(".2"));
    }
}