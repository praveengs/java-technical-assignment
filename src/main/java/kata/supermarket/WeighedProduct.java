package kata.supermarket;

import java.math.BigDecimal;

public class WeighedProduct extends Product{

    private final BigDecimal pricePerKilo;

    public WeighedProduct(String productId, final BigDecimal pricePerKilo) {
        super(productId);
        this.pricePerKilo = pricePerKilo;
    }

    BigDecimal pricePerKilo() {
        return pricePerKilo;
    }

    public Item weighing(final BigDecimal kilos) {
        return new ItemByWeight(this, kilos);
    }
}
