import java.util.ArrayList;
import java.util.List;

public class Supplier {
    private String supplierId;
    private String companyName ;
    private String contactPerson  ;
    private String email ;
    private String phone;
    private String address;
    private double rating;
    private List<Product> productsCatalog;


    public Supplier(String supplierId, String companyName, String contactPerson, String email, String phone, String address) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.productsCatalog = new ArrayList<>();
    }

    public void addProductToCatalog(Product product){
        if(product == null){
            throw new IllegalArgumentException("You must provide product to be added");
        }
        this.productsCatalog.add(product);
        System.out.println(product.getName() + " is added to catalog");
    }

    public void printSupplierInfo(){
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           SUPPLIER INFORMATION           ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf("║ %-12s : %-25s ║%n", "Supplier ID", supplierId);
        System.out.printf("║ %-12s : %-25s ║%n", "Company", companyName);
        System.out.printf("║ %-12s : %-25s ║%n", "Contact", contactPerson);
        System.out.printf("║ %-12s : %-25s ║%n", "Email", email);
        System.out.printf("║ %-12s : %-25s ║%n", "Phone", phone);
        System.out.printf("║ %-12s : %-25s ║%n", "Address", address);
        System.out.printf("║ %-12s : %-25.1f ║%n", "Rating", rating);
        System.out.printf("║ %-12s : %-25d ║%n", "Catalog Size", productsCatalog.size());
        System.out.println("╚══════════════════════════════════════════╝");
    }

    public boolean canSupply(Product product){
        if(product == null) throw new IllegalArgumentException("please provide the product");
        return this.productsCatalog.contains(product);
    }
}
