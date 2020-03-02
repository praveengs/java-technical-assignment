package kata.supermarket.rules;

import kata.supermarket.DiscountRule;
import kata.supermarket.Item;
import kata.supermarket.ItemByUnit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class BuyThreeForPriceOfTwoDiscountRule implements DiscountRule {
    private final String productId;

    public BuyThreeForPriceOfTwoDiscountRule(final String productId) {
        this.productId = productId;
    }

    @Override
    public BigDecimal calculateDiscount(List<Item> items) {
        AtomicInteger counter = new AtomicInteger();
        int partitionSize = 3;
        return items.parallelStream()
                .filter(item -> item instanceof ItemByUnit)
                .filter(item -> item.product().productId().equals(this.productId))
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / partitionSize)).values()
                .stream()
                .filter(itemByUnits -> itemByUnits.size() == partitionSize)
                .map(itemByUnits -> itemByUnits.get(0).price())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal getSumOfPrices(List<Item> itemByUnits) {
        return itemByUnits.stream().map(Item::price).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }
}
