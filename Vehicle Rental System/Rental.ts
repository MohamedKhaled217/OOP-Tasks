import { Vehicle } from "./Vehicle";
import { Customer } from "./Customer";

export class Rental {
  public isActive: boolean = false;

  constructor(
    private rentalId: string,
    public customer: Customer,
    public vehicle: Vehicle,
    public startDate: Date,
    public endDate: Date,
  ) {
    if (startDate.getTime() > endDate.getTime()) {
      throw new Error("Start or End Date is not valid");
    }
  }

  getRentalDuration(): number {
    let diff = this.endDate.getTime() - this.startDate.getTime();
    return diff / (1000 * 60 * 60 * 24);
  }

  getTotalCost(): number {
    return this.vehicle.calculateRentalCost(this.getRentalDuration());
  }

  completeRental(): void {
    console.log(`${this.rentalId} is completed!`);
    this.isActive = false;
    this.vehicle.returnVehicle();
  }

  getRentalInfo(): string {
    return `====== Rental Info ======\n Rental ID: ${this.rentalId}\n Customer :${this.customer.name}\n Vehicle Id: ${this.vehicle.getVehicleId()} `;
  }

  getRentalId(): string {
    return this.rentalId;
  }
}
