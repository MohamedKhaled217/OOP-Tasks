import java.time.LocalDateTime;

@FunctionalInterface
interface StockObserver {
    void sendAlert(StockEventData eventData);
}

public class StockItem  {
    private StockObserver stockLevelLowObserver;  // the observer
    private StockObserver stockDepletedObserver;


    private Product product;
    private int quantity ;  // current quantity
    private int reorderLevel; // min quantity before Reorder
    private int reorderQuantity;
    private String location;   // location of warehouse
    private LocalDateTime lastRestockDate;

    public StockItem(Product product, int quantity) {
        if(product == null) throw new IllegalArgumentException("Please provide a product");
        if(quantity < 0) throw new IllegalArgumentException("provide suitable quantity");
        this.product = product;
        this.quantity = quantity;
    }

    public void onStockLevelLow(StockObserver observer){      // first event -> we call this method and use delegate to pass behaviour as param
        this.stockLevelLowObserver = observer;
    }

    public void onOutOfStock(StockObserver observer){       // second event
        this.stockDepletedObserver = observer;
    }

    public void addStock (int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("quantity can not be negative");
        }
        this.quantity+= quantity;
    }

    public boolean isLowStock(){
        return this.quantity < this.reorderLevel;
    }

    public double getStockValue(){
        return this.quantity * this.product.getUnitPrice();
    }

    public void removeStock(int quantity){
        if(quantity <= 0 ){
            throw new IllegalArgumentException("quantity can not be negative or zero");
        }
        if(quantity > this.quantity){
            throw new IllegalArgumentException("Insufficient stock");
        }
        this.quantity-= quantity;

        if(this.quantity == 0 ){
            if(stockDepletedObserver == null){
                throw new IllegalArgumentException("out of stock Observer is not initialized");
            }
            // generate data needed as parameter
            StockEventData data = new StockEventData(
                    product,
                    this.quantity,
                    reorderLevel,
                    LocalDateTime.now());
            // fire the event to be executed based on behavior passed "delegate"
            stockDepletedObserver.sendAlert(data);
            return;
        }

        if(isLowStock()){
            if(stockLevelLowObserver == null){
                throw new IllegalArgumentException("Low level Observer is not initialized");
            }
            // generate data needed as parameter
            StockEventData data = new StockEventData(
                    product,
                    this.quantity,
                    reorderLevel,
                    LocalDateTime.now());
            // fire the event to be executed based on behavior passed "delegate"
            stockLevelLowObserver.sendAlert(data);
            return;
        }
        
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean containsKeyword(String keyword) {
        String name = product.getName().toLowerCase();
        return name.contains(keyword);
    }

    public Product getProduct() {
        return product;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }
}
