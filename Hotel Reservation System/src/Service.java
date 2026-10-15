import interfaces.IChargeable;

public class Service implements IChargeable {
    private String serviceId;
    private String name;
    private double price;
    private String description;

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void getDescription() {
        System.out.println("\n. . . . . Service Details . . . . .");
        System.out.println("  Service ID    : " + serviceId);
        System.out.println("  Name          : " + name);
        System.out.println("  Price         : $" + price);
        System.out.println("  Description   : " + description);
        System.out.println(". . . . . . . . . . . . . . . . . .");
    }

    public String getName() {
        return this.name;
    }

    public Service(String serviceId, String name, double price, String description) {
        this.serviceId = serviceId;
        this.name = name;
        this.price = price;
        this.description = description;
    }


}
