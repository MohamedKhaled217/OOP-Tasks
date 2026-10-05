import Enums.RoomStatus;

import java.time.LocalDate;
import java.util.ArrayList;

public class Hotel {
    private String hotelName;
    private String address;
    private ArrayList<Room> rooms = new ArrayList<>();
    private ArrayList<Reservation> reservations = new ArrayList<>();
    private ArrayList<Guest> guests = new ArrayList<>();
    private ArrayList<Service> availableServices = new ArrayList<>();


    public Hotel(String hotelName, String address) {
        this.hotelName = hotelName;
        this.address = address;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public void RegisterGuest(Guest guest) {
        this.guests.add(guest);
    }

    public void addService(Service service){
        this.availableServices.add(service);
    }

    public ArrayList<Room> getAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        ArrayList<Room> result = new ArrayList<>();
        for (Room x : rooms) {
            boolean isReserved = false;
            for (Reservation y : reservations) {
                if (y.getRoom().getRoomNumber().equals(x.getRoomNumber())) {
                    if (y.getCheckInDate().isBefore(checkOut) && y.getCheckOutDate().isAfter(checkIn)) {
                        isReserved = true;
                        break;
                    }
                }
            }
            if (!isReserved) result.add(x);
        }
        return result;
    }

    public ArrayList<Room> getAvailableRoomsByType(Enums.RoomType type, LocalDate checkIn, LocalDate checkOut) {
        ArrayList<Room> result = new ArrayList<>();

        for (Room r : this.getAvailableRooms(checkIn, checkOut)) {
            if (r.getType() == type) result.add(r);
        }
        return result;
    }

    public Reservation createReservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut, int guests) {
        Reservation r = new Reservation(guest, room, checkIn, checkOut, guests);
        this.reservations.add(r);
        r.getRoom().changeStatus(RoomStatus.Reserved);
        return r;
    }

    public void cancelReservation(String reservationId) {
        for (var x : reservations) {
            if (x.getReservationId().equals(reservationId)) {
                x.cancel();
            }
        }
    }

    public void CheckInGuest(String reservationId) {
        for (var x : reservations) {
            if (x.getReservationId().equals(reservationId)) {
                x.checkIn();
            }
        }
    }

    public void CheckOutGuest(String reservationId) {
        for (var x : reservations) {
            if (x.getReservationId().equals(reservationId)) {
                x.checkOut();
            }
        }
    }

    public ArrayList<Reservation> getReservationsByGuest(String guestId){
        ArrayList<Reservation> res = new ArrayList<>();
        for(var x : reservations){
            if(x.getGuest().getGuestId() == guestId) res.add(x);
        }
        return res;
    }

    public void getCurrentOccupancy(){
        int count = 0;
        for(var x : rooms){
            if(x.getStatus() == RoomStatus.Occupied)count++;
        }
        double calc = count / rooms.size() * 100;
        System.out.println(calc + "%");
    }

    public double getRevenue(LocalDate from, LocalDate to) {
        double totalRevenue = 0.0;
        for (Reservation r : reservations) {
            if (!r.getCheckOutDate().isBefore(from) && !r.getCheckInDate().isAfter(to)) {
                totalRevenue += r.getTotal();
            }
        }
        return totalRevenue;
    }

    public ArrayList<Service> getAvailableServices() {
        return availableServices;
    }

    public void displayHotelStatus() {
        System.out.println("\n--- Hotel Status: " + hotelName + " ---");
        System.out.println("Address: " + address);
        System.out.println("Total Rooms: " + rooms.size());
        System.out.println("Total Guests: " + guests.size());
        System.out.println("Total Reservations: " + reservations.size());
        System.out.println("\nRoom Details:");
        for (Room room : rooms) {
            System.out.println("  Room " + room.getRoomNumber() + " (" + room.getType() + ") - Status: " + room.getStatus());
        }
    }
}
