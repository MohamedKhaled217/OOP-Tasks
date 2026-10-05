import Enums.ReservationStatus;
import Enums.RoomStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Reservation {
    private String reservationId;

    public Guest getGuest() {
        return guest;
    }

    private Guest guest;
    private Room room;
    private Enums.ReservationStatus status;
    private LocalDate checkInDate;
    private LocalDate checkOutDate ;
    private ArrayList<Service> services = new ArrayList<>(); ;
    private int totalGuests;

    private static int counter = 0;

    public String getReservationId() {
        return reservationId;
    }

    public Reservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate, int totalGuests) {
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.totalGuests = totalGuests;

        counter++;
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yyyyMMdd");
        String idFormat = checkInDate.format(formatter);
        this.reservationId = "RES-" + idFormat + "-" + counter;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public long getNumberOfNights(){
        return ChronoUnit.DAYS.between(this.checkInDate, this.checkOutDate);
    }

    public double getRoomCost(){
        return this.getNumberOfNights() * room.getPrice();
    }
    public double getServicesCost(){
        double calc = 0.0;
        for(Service s : services) calc+=s.getPrice();
        return calc;
    }
    public double getTotal(){
        return this.getRoomCost() + this.getServicesCost();
    }
    public void addService(Service service){
        this.services.add(service);
    }

    public void checkIn(){
        this.status = ReservationStatus.CheckedIn;
        this.room.changeStatus(RoomStatus.Occupied);
    }

    public void checkOut(){
        this.status = ReservationStatus.CheckedOut;
        this.room.changeStatus(RoomStatus.Available);
    }

    public void cancel(){
        this.status = ReservationStatus.Cancelled;
        this.room.changeStatus(RoomStatus.Available);
    }

    public void getReservationDetails(){
        System.out.println("\n************ Reservation Details ************");
        System.out.println("  Reservation ID : " + reservationId);
        System.out.println("  Guest          : " + guest.getGuestId());
        System.out.println("  Room           : " + room.getRoomNumber() + " (" + room.getType() + ")");
        System.out.println("  Check-In Date  : " + checkInDate);
        System.out.println("  Check-Out Date : " + checkOutDate);
        System.out.println("  Nights         : " + getNumberOfNights());
        System.out.println("  Total Guests   : " + totalGuests);
        System.out.println("  Status         : " + status);
        System.out.println("  Services       :");
        if (services.isEmpty()) {
            System.out.println("    - None");
        } else {
            for (Service s : services) {
                System.out.println("    - " + s.getName() + " ($" + s.getPrice() + ")");
            }
        }
        System.out.println("  ------------------------------------");
        System.out.println("  Room Cost      : $" + getRoomCost());
        System.out.println("  Services Cost  : $" + getServicesCost());
        System.out.println("  Total          : $" + getTotal());
        System.out.println("*********************************************");
    }
}
