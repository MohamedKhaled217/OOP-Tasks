
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SupplyChainManager {

    private final List<Warehouse> warehouses = new ArrayList<>();
    private final List<Supplier> suppliers = new ArrayList<>();
    private final List<PurchaseOrder> purchaseOrders = new ArrayList<>();
    private final List<Shipment> shipments = new ArrayList<>();

    // Which warehouse will receive each purchase order?
    private final Map<PurchaseOrder, Warehouse> orderWarehouses =
            new IdentityHashMap<>();

    // SKU -> recorded daily demand quantities
    private final Map<String, List<Integer>> demandHistory =
            new HashMap<>();

    private int nextOrderId = 1;

    public SupplyChainManager() {
    }

    public void registerWarehouse(Warehouse warehouse) {
        Objects.requireNonNull(warehouse, "Warehouse cannot be null");

        boolean duplicate = warehouses.stream()
                .anyMatch(w -> w.getWarehouseId()
                        .equals(warehouse.getWarehouseId()));

        if (duplicate) {
            throw new IllegalArgumentException(
                    "Warehouse already registered");
        }

        warehouses.add(warehouse);
    }

    public void registerSupplier(Supplier supplier) {
        Objects.requireNonNull(supplier, "Supplier cannot be null");

        if (suppliers.contains(supplier)) {
            throw new IllegalArgumentException(
                    "Supplier already registered");
        }

        suppliers.add(supplier);
    }

    public PurchaseOrder createPurchaseOrder(Supplier supplier, Warehouse warehouse) {

        if (!suppliers.contains(supplier)) {
            throw new IllegalArgumentException(
                    "Supplier is not registered");
        }

        if (!warehouses.contains(warehouse)) {
            throw new IllegalArgumentException(
                    "Warehouse is not registered");
        }

        String id = "PO-" + nextOrderId++;

        PurchaseOrder order = new PurchaseOrder(id, supplier, LocalDateTime.now() , warehouse);

        purchaseOrders.add(order);
        orderWarehouses.put(order, warehouse);

        return order;
    }

    public Shipment processShipment(
            PurchaseOrder order, ShippingStrategy strategy) {

        Objects.requireNonNull(order, "Order cannot be null");
        Objects.requireNonNull(strategy, "Strategy cannot be null");

        if (!purchaseOrders.contains(order)) {
            throw new IllegalArgumentException(
                    "Order is not managed by this system");
        }

        // The order must be approved before shipment.
        if (order.getStatus() != OrderStatus.Approved) {
            throw new IllegalStateException(
                    "Only approved orders can be shipped");
        }

        Shipment shipment = new Shipment(order, strategy);
        shipments.add(shipment);

        return shipment;
    }

    public double calculateTotalInventoryValue() {
        return warehouses.stream()
                .mapToDouble(Warehouse::getTotalInventoryValue)
                .sum();
    }

    public List<StockItem> getLowStockAlert() {
        return warehouses.stream()
                .flatMap(w -> w.getLowStockItems().stream())
                .toList();
    }

    public List<PurchaseOrder> getOrderHistory(
            LocalDateTime startDate, LocalDateTime endDate) {

        if (startDate == null || endDate == null
                || startDate.isAfter(endDate)) {
            throw new IllegalArgumentException(
                    "Invalid date range");
        }

        return purchaseOrders.stream()
                .filter(order -> !order.getOrderDate().isBefore(startDate) && !order.getOrderDate().isAfter(endDate))
                .toList();
    }

    public List<Warehouse> getWarehouses() {
        return Collections.unmodifiableList(warehouses);
    }

    public List<Supplier> getSuppliers() {
        return Collections.unmodifiableList(suppliers);
    }

    public List<PurchaseOrder> getPurchaseOrders() {
        return Collections.unmodifiableList(purchaseOrders);
    }

    public List<Shipment> getShipments() {
        return Collections.unmodifiableList(shipments);
    }
}
