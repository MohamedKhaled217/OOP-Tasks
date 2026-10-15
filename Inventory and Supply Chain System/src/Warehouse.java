import java.util.*;

public class Warehouse {
    private String warehouseId;
    private String name;
    private String location;
    private int capacity;
    private Map<String , StockItem>stockItems;
    private String manager;

    public Warehouse(String warehouseId, String name, String location, int capacity) {
        this.warehouseId = warehouseId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.stockItems = new HashMap<>();
    }

    public void addStockItem(StockItem item){
        if(item == null) throw new IllegalArgumentException("Provide stock item");
        if(capacity == this.stockItems.size()) {
            System.out.println("Warehouse is full");
            return;
        }
        this.stockItems.put(item.getProduct().getSku() , item);
    }

    public void addProduct(Product product , int quantity , int reorderLevel){
            if(this.stockItems.containsKey(product.getSku())){
                StockItem it = this.stockItems.get(product.getSku());
                it.setQuantity(quantity);
                it.setReorderLevel(reorderLevel);
            }
            else{
                StockItem item = new StockItem(product,quantity);
                item.setReorderLevel(reorderLevel);
                addStockItem(item);
            }
    }

    public void updateStock(String sku, int quantityChange){
        if(quantityChange < 0) throw new IllegalArgumentException("provide suitable quantity");
        StockItem item = this.stockItems.get(sku);
        if( item != null) item.addStock(quantityChange);
        else System.out.println("Item is not exist in stock");
        }

    public StockItem getStockItem(String sku){
        if(this.stockItems.containsKey(sku)){
            return this.stockItems.get(sku);
        }
        System.out.println("this product is not exist");
        return null;
    }

    public double getTotalInventoryValue(){
        double res = 0;
        for(StockItem item : stockItems.values()){
            res+= item.getStockValue();
        }
        return res;
    }

    public List<StockItem> getLowStockItems(){
        List<StockItem> items = this.stockItems.values().stream()
                .filter(StockItem::isLowStock)
                .toList();
        return items;
    }

    public List<StockItem> searchProducts(String keyword){
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("Keyword cannot be empty");
        }
        String newKeyword = keyword.toLowerCase();
        List<StockItem>items = this.stockItems.values()
                .stream().
                filter(item -> item.containsKeyword(newKeyword))
                .toList();
        return items;
    }
    public List<StockItem> getProductsByCategory(String category){
        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException("category cannot be empty");
        }
        String normalizedCategory = category.toLowerCase();
        List<StockItem>items = this.stockItems.values()
                .stream().
                filter(item -> item.getProduct().getCategory().toLowerCase().equals(normalizedCategory))
                .toList();
        return items;
    }

    public List<StockItem> getTopValueProducts(int count){
        if(count <= 0) throw new IllegalArgumentException("count can not be less or equal zero");
        List<StockItem>items = this.stockItems.values()
                .stream()
                .sorted(Comparator.comparingDouble(StockItem::getStockValue).reversed())
                .limit(count)
                .toList();
        return items;
    }

    public String getWarehouseId() {
        return warehouseId;
    }
}
