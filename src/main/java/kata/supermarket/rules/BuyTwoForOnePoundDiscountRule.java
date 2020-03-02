package kata.supermarket.rules;

import kata.supermarket.DiscountRule;
import kata.supermarket.Item;
import kata.supermarket.ItemByUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BuyTwoForOnePoundDiscountRule implements DiscountRule {
    /**
     * Product for which this rule is applicable for.
     */
    private final String productId;

    public BuyTwoForOnePoundDiscountRule(final String productId) {
        this.productId = productId;
    }

    @Override
    public BigDecimal calculateDiscount(List<Item> items) {
        AtomicInteger counter = new AtomicInteger();
        int partitionSize = 2;
        return items.parallelStream()
                .filter(item -> item instanceof ItemByUnit)
                .filter(item -> item.product().productId().equals(this.productId))
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / partitionSize)).values()
                .stream()
                .filter(itemByUnits -> itemByUnits.size() == partitionSize)
                .map(itemByUnits -> BigDecimal.ONE.subtract(getSumOfPrices(itemByUnits)).abs())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getSumOfPrices(List<Item> itemByUnits) {
        return itemByUnits.stream().map(Item::price).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
