public class Guest {
    private String guestId;
    private String name;
    private String email;
    private String phone;
    private String idNumber;
    private int loyaltyPoints;

    public String getGuestId() {
        return guestId;
    }

    public Guest(String guestId, String name, String email, String phone, String idNumber, int loyaltyPoints) {
        this.guestId = guestId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.idNumber = idNumber;
        this.loyaltyPoints = loyaltyPoints;
    }

    public void getGuestInfo(){
        System.out.println("\n========== Guest Information ==========");
        System.out.println("  Guest ID      : " + guestId);
        System.out.println("  Name          : " + name);
        System.out.println("  Email         : " + email);
        System.out.println("  Phone         : " + phone);
        System.out.println("  ID Number     : " + idNumber);
        System.out.println("  Loyalty Points: " + loyaltyPoints);
        System.out.println("  Discount Rate : " + (getDiscountRate() * 100) + "%");
        System.out.println("=======================================");
    }

    public void addLoyaltyPoints(int points){
        this.loyaltyPoints += points;
    }

    public double getDiscountRate(){
        if (loyaltyPoints >= 1000) {
            return 0.15;
        } else if (loyaltyPoints >= 500) {
            return 0.10;
        } else if (loyaltyPoints >= 100) {
            return 0.05;
        }
        return 0.0;
    }
}
