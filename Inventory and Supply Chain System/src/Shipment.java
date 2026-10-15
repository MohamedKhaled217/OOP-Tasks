import java.time.LocalDateTime;

public class Shipment {
    private static int count = 1;

    private String shipmentId;
    private PurchaseOrder order;
    private ShippingStrategy shippingStrategy;
    private String origin;
    private String destination;
    private double distance;
    private LocalDateTime shipDate;
    private LocalDateTime estimatedDelivery;
    private LocalDateTime actualDelivery;
    private String trackingNumber;


    public Shipment(PurchaseOrder order, ShippingStrategy shippingStrategy) {
        this.order = order;
        this.shippingStrategy = shippingStrategy;
        this.shipmentId = "SHIP-" + count;
        count++;
    }

    public double calculateShippingCost(){
        double weight = 0;
        for(var item : this.order.getOrderItems()){
            weight += item.getQuantity() * item.getProduct().getWeight();
        }
        return this.shippingStrategy.calculateCost(weight , distance);
    }

    public int getEstimatedDeliveryDate(){
        return this.shippingStrategy.getEstimatedDays(distance);
    }

    public void markAsDelivered(){
        this.actualDelivery = LocalDateTime.now();
        order.completeOrder();
    }

    public void getShipmentInfo(){
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                SHIPMENT INFORMATION                  ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║ %-18s : %-31s ║%n", "Shipment ID", shipmentId);
        System.out.printf("║ %-18s : %-31s ║%n", "Shipping Method", shippingStrategy.getShippingMethod());
        System.out.printf("║ %-18s : %-31s ║%n", "Origin", origin != null ? origin : "N/A");
        System.out.printf("║ %-18s : %-31s ║%n", "Destination", destination != null ? destination : "N/A");
        System.out.printf("║ %-18s : %-31.2f ║%n", "Distance (km)", distance);
        System.out.printf("║ %-18s : %-31s ║%n", "Ship Date", shipDate != null ? shipDate.toString() : "N/A");
        System.out.printf("║ %-18s : %-31s ║%n", "Est. Delivery", estimatedDelivery != null ? estimatedDelivery.toString() : "N/A");
        System.out.printf("║ %-18s : %-31s ║%n", "Actual Delivery", actualDelivery != null ? actualDelivery.toString() : "Not delivered");
        System.out.printf("║ %-18s : %-31s ║%n", "Tracking Number", trackingNumber != null ? trackingNumber : "N/A");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.println("║  ORDER ITEMS                                         ║");
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  %-20s  %-8s  %-10s       ║%n", "Product", "Qty", "Weight(kg)");
        System.out.println("║  ────────────────────  ────────  ──────────       ║");
        for (OrderItem item : order.getOrderItems()) {
            System.out.printf("║  %-20s  %-8d  %-10.2f       ║%n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getWeight() * item.getQuantity());
        }
        System.out.println("╠══════════════════════════════════════════════════════╣");
        System.out.printf("║  %-31s : $%-16.2f ║%n", "TOTAL SHIPPING COST", calculateShippingCost());
        System.out.printf("║  %-31s : %-17d ║%n", "EST. DELIVERY DAYS", getEstimatedDeliveryDate());
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}
