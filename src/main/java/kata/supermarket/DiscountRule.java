package kata.supermarket;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface DiscountRule {
    BigDecimal calculateDiscount(List<Item> items);
}
