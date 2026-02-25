import { Vehicle } from "./Vehicle";
import { Customer } from "./Customer";
import { Rental } from "./Rental";

export class RentalAgency {
  public vehicles: Vehicle[] = [];
  public customers: Customer[] = [];
  public rentals: Rental[] = [];
  constructor(public agencyName: string) {}

  addVehicle(vehicle: Vehicle): void {
    this.vehicles.push(vehicle);
  }

  registerCustomer(customer: Customer): void {
    this.customers.push(customer);
  }

  getAvailableVehicles(): string {
    let available = this.vehicles.filter((v) => v.isAvailable === true);
    return available.map((v) => v.getVehicleInfo()).join("\n");
  }
  createRental(
    rentalId: string,
    customer: Customer,
    vehicle: Vehicle,
    days: number,
  ): Rental {
    const today = new Date();
    const end = new Date();
    end.setDate(today.getDate() + days);
    const rental = new Rental(rentalId, customer, vehicle, today, end);
    rental.isActive = true;
    this.rentals.push(rental);
    vehicle.rent();
    return rental;
  }

  completeRental(rentalId: string): void {
    const rental = this.rentals.find((r) => r.getRentalId() === rentalId);
    if (!rental) throw new Error("Rental is not exist");
    rental.completeRental();
    console.log(`Rental Total Cost: ${rental.getTotalCost()}`);
  }

  getActiveRentals(): void {
    let active = this.rentals.filter((r) => r.isActive === true);
    if (active.length === 0) {
      console.log(` No Active Rentals`);
      return;
    }
    console.log(`==== Active Rentals ====`);
    for (let x of active) {
      console.log(x.getRentalId());
    }
  }
  getCustomerRentals(customerId: string): void {
    const customer = this.customers.find(
      (c) => c.getCustomerId() === customerId,
    );
    if (customer === undefined) {
      console.log(`Customer Not Found`);
      return;
    }
    let rentalNum = this.rentals.filter(
      (r) => r.customer.getCustomerId() === customerId,
    );
    console.log(
      `${customer.name}'s rental history: ${rentalNum.length} rental(s)`,
    );
  }
  displayFleet(): void {
    if (this.vehicles.length === 0) {
      console.log("Fleet is Empty");
      return;
    }
    console.log(`=== ${this.agencyName} Rentals - Fleet Status ===`);
    let array = this.vehicles.map(
      (v) =>
        `${v.getVehicleId()} - ${v.year} ${v.model} ${v.make} - $${v.dailyRate}/day - ${v.isAvailable ? "Available" : "Rented"}`,
    );
    console.log(array.join("\n"));
    console.log("\n");
  }
}
