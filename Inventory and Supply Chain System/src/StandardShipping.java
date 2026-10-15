public class StandardShipping implements ShippingStrategy {
    private double baseFee = 20;
    private double ratePerKg = 5;
    private double ratePerKm = 0.50;
    private double deliverySpeed = 200;
    @Override
    public double calculateCost(double weight, double distance) {
        return baseFee + (ratePerKg * weight) + (ratePerKm * distance);
    }

    @Override
    public int getEstimatedDays(double distance) {
        return (int) Math.ceil(distance / 200.0);
    }

    @Override
    public String getShippingMethod() {
        return "Standard Shipping";
    }
}
