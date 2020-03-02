package kata.supermarket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * This class has the responsibility of accumulating all the discount rules, and
 * applying them to all incoming items.rules
 */
public class DiscountCalculator {

    private final List<DiscountRule> discountRules;

    public DiscountCalculator(List<DiscountRule> discountRules) {
        this.discountRules = discountRules;
    }

    public BigDecimal calculateDiscounts(final List<Item> items) {
        return discountRules.parallelStream()
                .map(itemDiscountRule -> itemDiscountRule.calculateDiscount(items))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
