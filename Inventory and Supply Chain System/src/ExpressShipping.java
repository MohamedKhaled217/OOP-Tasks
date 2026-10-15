public class ExpressShipping implements ShippingStrategy{
    private double premiumMulti = 1.5;

    @Override
    public String getShippingMethod() {
        return "Express Shipping";
    }

    @Override
    public int getEstimatedDays(double distance) {
        return new StandardShipping().getEstimatedDays(distance) - 2;
    }

    @Override
    public double calculateCost(double weight, double distance) {
        return new StandardShipping().calculateCost(weight,distance) * premiumMulti;
    }
}
