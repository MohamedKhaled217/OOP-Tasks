import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {
    private String orderId;
    private Supplier supplier;
    private LocalDateTime orderDate;
    private LocalDateTime expectedDeliveryDate;
    private List<OrderItem>orderItems;
    private OrderStatus status;
    private double totalCost;
    private Warehouse warehouse;

    public PurchaseOrder(String orderId, Supplier supplier, LocalDateTime orderDate , Warehouse warehouse) {
        this.orderId = orderId;
        this.supplier = supplier;
        this.orderDate = orderDate;
        this.orderItems = new ArrayList<>();
        this.warehouse = warehouse;
        this.status = OrderStatus.Pending;
    }

    public void addItem(Product product, int quantity){
        this.orderItems.add(new OrderItem(product,quantity));
    }

    public double calculateTotal(){
        double res = 0;
        for(var x : this.orderItems){
            res+= x.getTotal();
        }
        return res;
    }

    public void approveOrder(){
        this.status = OrderStatus.Approved;
    }

    public void completeOrder(){
        if(this.status == OrderStatus.Cancelled || this.status == OrderStatus.Delivered){
            System.out.println("The order is cancelled or delivered before");
            return;
        }
        for(var x : this.orderItems){
            String sku = x.getProduct().getSku();
            if(warehouse.getStockItem(sku) != null)
                warehouse.updateStock(sku , x.getQuantity());
            else {
                StockItem item = new StockItem(x.getProduct() , x.getQuantity());
                warehouse.addStockItem(item);
            }
        }
        this.status = OrderStatus.Delivered;
    }

    public void printOrderSummary(){
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                  ORDER SUMMARY                       ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║ %-16s : %-33s ║%n", "Order ID", orderId);
        System.out.printf("║ %-16s : %-33s ║%n", "Supplier", supplier != null ? supplier.toString() : "N/A");
        System.out.printf("║ %-16s : %-33s ║%n", "Order Date", orderDate);
        System.out.printf("║ %-16s : %-33s ║%n", "Expected Delivery", expectedDeliveryDate != null ? expectedDeliveryDate.toString() : "N/A");
        System.out.printf("║ %-16s : %-33s ║%n", "Status", status != null ? status : "N/A");
        System.out.printf("║ %-16s : %-33s ║%n", "Warehouse", warehouse != null ? warehouse.getWarehouseId() : "N/A");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  ITEMS                                               ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  %-20s  %-8s  %-10s  %-8s ║%n", "Product", "Qty", "Unit Price", "Total");
        System.out.println("║  ────────────────────  ────────  ──────────  ──────── ║");
        for (OrderItem item : orderItems) {
            System.out.printf("║  %-20s  %-8d  $%-9.2f  $%-7.2f ║%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getUnitPrice(),
                    item.getTotal());
        }
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  %-38s  $%-7.2f ║%n", "GRAND TOTAL", calculateTotal());
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }
}

