public interface ShippingStrategy {
    double calculateCost(double weight , double distance);
    int getEstimatedDays(double distance);
    String getShippingMethod();
}
