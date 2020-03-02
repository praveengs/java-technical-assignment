package kata.supermarket.rules;

import kata.supermarket.DiscountRule;
import kata.supermarket.Item;
import kata.supermarket.ItemByWeight;
import kata.supermarket.WeighedProduct;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BuyOneKiloForHalfPrice implements DiscountRule {

    /**
     * Group for which this rule is applicable for.
     */
    private final String group;

    public BuyOneKiloForHalfPrice(final String group) {
        this.group = group;
    }

    @Override
    public BigDecimal calculateDiscount(List<Item> items) {
        final Map<String, List<ItemByWeight>> weighedItemForProductId = getItemsByProductIdForTheGroup(items);

        return weighedItemForProductId.values().parallelStream()
                .map(this::getDiscountForListOfWeighedProduct)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private Map<String, List<ItemByWeight>> getItemsByProductIdForTheGroup(List<Item> items) {
        return items.parallelStream()
                .filter(item -> item instanceof ItemByWeight)
                .filter(item -> item.product().group().equals(this.group))
                .map(item -> (ItemByWeight) item)
                .collect(Collectors.groupingBy(item -> item.product().productId()));
    }

    private BigDecimal getDiscountForListOfWeighedProduct(List<ItemByWeight> itemByWeights) {
        final BigDecimal totalWeightInKilosRoundedDown = itemByWeights.parallelStream()
                .map(ItemByWeight::getWeightInKilos)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_DOWN);
        BigDecimal discount = BigDecimal.ZERO;
        if (BigDecimal.ONE.compareTo(totalWeightInKilosRoundedDown) < 1) {
            discount = ((WeighedProduct) itemByWeights.get(0).product())
                    .pricePerKilo()
                    .multiply(totalWeightInKilosRoundedDown.divide(BigDecimal.valueOf(2), RoundingMode.UNNECESSARY))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        return discount;
    }
}
