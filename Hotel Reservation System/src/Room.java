import interfaces.IChargeable;
import Enums.*;
import java.util.ArrayList;

public class Room implements IChargeable {
    private String roomNumber;
    private  Enums.RoomType type;
    private  Enums.RoomStatus status = RoomStatus.Available;

    public RoomStatus getStatus() {
        return status;
    }

    public RoomType getType() {
        return type;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    private int floor;
    private double pricePerNight;
    private int maxOccupancy;
    private ArrayList<String> amenities = new ArrayList<>();

    public Room(String roomNumber ,  Enums.RoomType type,int floor,double pricePerNight,int maxOccupancy){
        this.roomNumber = roomNumber;
        this.type= type;
        this.floor = floor;
        this.pricePerNight = pricePerNight;
        this.maxOccupancy = maxOccupancy;
    }

    public boolean isAvailable(){
        return this.status == Enums.RoomStatus.Available;
    }

    public void changeStatus(Enums.RoomStatus newStatus){
        this.status = newStatus;
    }

    @Override
    public double getPrice() {
        return this.pricePerNight;
    }

    @Override
    public void getDescription() {
        System.out.println("\n----------- Room Details -----------");
        System.out.println("  Room Number   : " + roomNumber);
        System.out.println("  Type          : " + type);
        System.out.println("  Floor         : " + floor);
        System.out.println("  Price/Night   : $" + pricePerNight);
        System.out.println("  Max Occupancy : " + maxOccupancy);
        System.out.println("  Status        : " + status);
        System.out.println("  Amenities     : " + (amenities.isEmpty() ? "None" : amenities));
        System.out.println("------------------------------------");
    }
}
