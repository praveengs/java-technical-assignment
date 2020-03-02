package kata.supermarket.rules;

import kata.supermarket.Item;
import kata.supermarket.UnitProduct;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuyTwoForOnePoundDiscountRuleTest {

    @MethodSource
    @ParameterizedTest(name = "{0}")
    void discountOfBuyTwoForOnePoundIsApplied(String description, String expectedDiscount, String discountableItem, List<Item> itemByUnits) {
        BuyTwoForOnePoundDiscountRule buyTwoForOnePoundDiscountRule = new BuyTwoForOnePoundDiscountRule(discountableItem);
        BigDecimal discount = buyTwoForOnePoundDiscountRule.calculateDiscount(itemByUnits);
        assertEquals(new BigDecimal(expectedDiscount), discount);
    }

    private static Stream<Arguments> discountOfBuyTwoForOnePoundIsApplied() {
        return Stream.of(
                Arguments.of("One pint of milk", "0.00", "Milk", Collections.singletonList(aPintOfMilk())),
                Arguments.of("2 x one pint milk discounted to 1£", "0.30", "Milk", createMultipleItems(aPintOfMilk(), 2)),
                Arguments.of("3 x one pint milk discounted to 1£ for one set", "0.30", "Milk", createMultipleItems(aPintOfMilk(), 3)),
                Arguments.of("4 x one pint milk discounted for 1 £ for two sets", "0.60", "Milk", createMultipleItems(aPintOfMilk(), 4))
        );
    }

    private static List<Item> createMultipleItems(Item item, int size) {
        List<Item> listOfItems = new ArrayList<>(size);
        IntStream.rangeClosed(1, size)
                .forEach(num -> listOfItems.add(item));
        return listOfItems;
    }

    private static Item aPintOfMilk() {
        return new UnitProduct("Milk", "Diary", new BigDecimal("0.65")).oneOf();
    }
}