public class Product<T> {
    private T productId;
    private String name;
    private String category;
    private String description;
    private double unitPrice;
    private double weight;
    private String dimensions;
    private String sku;   // stock keeping unit
    private Supplier supplier;

    public Product(T productId, String name, String category, double unitPrice, Supplier supplier) {
        this.productId = productId;
        this.name = name;
        this.category = category;
        this.unitPrice = unitPrice;
        this.supplier = supplier;
    }

    public void printProductInfo(){
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           PRODUCT INFORMATION            ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ %-12s : %-25s ║%n", "Product ID", productId);
        System.out.printf("║ %-12s : %-25s ║%n", "Name", name);
        System.out.printf("║ %-12s : %-25s ║%n", "Category", category);
        System.out.printf("║ %-12s : %-25s ║%n", "Description", description != null ? description : "N/A");
        System.out.printf("║ %-12s : $%-24.2f ║%n", "Unit Price", unitPrice);
        System.out.printf("║ %-12s : %-24.2f kg ║%n", "Weight", weight);
        System.out.printf("║ %-12s : %-25s ║%n", "Dimensions", dimensions != null ? dimensions : "N/A");
        System.out.printf("║ %-12s : %-25s ║%n", "SKU", sku != null ? sku : "N/A");
        System.out.printf("║ %-12s : %-25s ║%n", "Supplier", supplier != null ? supplier.toString() : "N/A");
        System.out.println("╚══════════════════════════════════════════╝");
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public String getName() {
        return name;
    }

    public String getSku() {
        return sku;
    }

    public String getCategory() {
        return category;
    }

    public double getWeight() {
        return weight;
    }
}
