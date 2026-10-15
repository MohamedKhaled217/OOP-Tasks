public class OrderItem {
    private Product product;
    private int quantity;
    private double pricePerUnit;

    public OrderItem(Product product, int quantity) {
        if(product == null) throw new IllegalArgumentException("Must provide a product");
        if(quantity <= 0) throw new IllegalArgumentException("Quantity must be above zero");
        this.product = product;
        this.quantity = quantity;
        this.pricePerUnit = this.product.getUnitPrice();
    }

    public double getTotal(){
        return this.quantity * this.pricePerUnit;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
