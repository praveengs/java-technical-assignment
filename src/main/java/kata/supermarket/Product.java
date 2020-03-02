package kata.supermarket;

public abstract class Product {
    private final String productId;
    private final String group;

    protected Product(final String productId, String group) {
        this.productId = productId;
        this.group = group;
    }

    public String productId() {
        return productId;
    }

    public String group() {
        return group;
    }
}
