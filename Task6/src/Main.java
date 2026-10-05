import Enums.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create hotel
        Hotel hotel = new Hotel("Grand Plaza Hotel", "123 Main Street, City");

        // Add rooms
        hotel.addRoom(new Room("101", RoomType.Single, 1, 89.99, 1));
        hotel.addRoom(new Room("201", RoomType.Double, 2, 129.99, 2));
        hotel.addRoom(new Room("301", RoomType.Suite, 3, 249.99, 4));
        hotel.addRoom(new Room("401", RoomType.Deluxe, 4, 349.99, 3));

        // Add services
        hotel.addService(new Service("S001", "Room Service", 25.00, "24-hour room service"));
        hotel.addService(new Service("S002", "Spa Treatment", 100.00, "90-minute massage"));
        hotel.addService(new Service("S003", "Airport Shuttle", 50.00, "Round trip airport transfer"));
        hotel.addService(new Service("S004", "Breakfast Buffet", 20.00, "Continental breakfast"));

        // Register guests
        Guest guest1 = new Guest("G001", "Alice Johnson", "alice@email.com",
                "555-0123", "ID123456", 250);
        Guest guest2 = new Guest("G002", "Bob Smith", "bob@email.com",
                "555-0456", "ID789012", 100);

        hotel.RegisterGuest(guest1);
        hotel.RegisterGuest(guest2);

        // Check available rooms
        LocalDate checkIn = LocalDate.now().plusDays(7);
        LocalDate checkOut = checkIn.plusDays(3);

        ArrayList<Room> availableRooms = hotel.getAvailableRooms(checkIn, checkOut);
        System.out.println("Available rooms for " + checkIn + " to " + checkOut + ":");
        for (Room room : availableRooms) {
            System.out.println("- Room " + room.getRoomNumber() + " (" + room.getType() + ") - $" + room.getPrice() + "/night");
        }

        // Create reservation
        Room selectedRoom = findRoomByType(availableRooms, RoomType.Suite);
        Reservation reservation = hotel.createReservation(guest1, selectedRoom, checkIn, checkOut, 2);

        System.out.println("\nReservation created: " + reservation.getReservationId());

        // Add services to reservation
        reservation.addService(findServiceByName(hotel.getAvailableServices(), "Breakfast Buffet"));
        reservation.addService(findServiceByName(hotel.getAvailableServices(), "Airport Shuttle"));

        // Display reservation details
        reservation.getReservationDetails();

        // Calculate total
        System.out.println("\nReservation Summary:");
        System.out.println("Room Cost (" + reservation.getNumberOfNights() + " nights): $" + reservation.getRoomCost());
        System.out.println("Services Cost: $" + reservation.getServicesCost());
        System.out.println("Guest Discount: " + (guest1.getDiscountRate() * 100) + "%");
        System.out.println("Total: $" + reservation.getTotal());

        // Check in
        hotel.CheckInGuest(reservation.getReservationId());
        System.out.println("\nGuest checked in. Room " + selectedRoom.getRoomNumber() + " status: " + selectedRoom.getStatus());

        // Hotel status
        hotel.displayHotelStatus();

        // Check out
        hotel.CheckOutGuest(reservation.getReservationId());
        System.out.println("\nGuest checked out. Final bill: $" + reservation.getTotal());

        // Calculate revenue
        LocalDate today = LocalDate.now();
        double revenue = hotel.getRevenue(today, today.plusDays(30));
        System.out.println("\nProjected 30-day revenue: $" + revenue);
    }

    private static Room findRoomByType(ArrayList<Room> rooms, RoomType type) {
        for (Room room : rooms) {
            if (room.getType() == type) {
                return room;
            }
        }
        return null;
    }

    private static Service findServiceByName(ArrayList<Service> services, String name) {
        for (Service service : services) {
            if (service.getName().equals(name)) {
                return service;
            }
        }
        return null;
    }
}
