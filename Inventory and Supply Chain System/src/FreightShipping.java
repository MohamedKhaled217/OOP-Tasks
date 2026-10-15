public class FreightShipping implements ShippingStrategy{
private double feePer100Kg = 70;
private double feePerKm = 1;
private double discount = 0.3;  // if weight more than 2 tons
private double baseFee = 200;
    @Override
    public String getShippingMethod() {
        return "Freight Shipping";
    }

    @Override
    public int getEstimatedDays(double distance) {
        return new StandardShipping().getEstimatedDays(distance) - 2;
    }

    @Override
    public double calculateCost(double weight, double distance) {
        double Calc = baseFee + ((weight / 100 ) *70) + (distance*feePerKm);
        if(weight > 2000) Calc -= Calc * discount;
        return Calc;
    }
}
