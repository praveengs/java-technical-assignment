package kata.supermarket;

public abstract class Product {
    private final String productId;

    protected Product(final String productId) {
        this.productId = productId;
    }

    public String productId() {
        return productId;
    }
}
