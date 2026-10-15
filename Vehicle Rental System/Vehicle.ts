export class Vehicle {
  public isAvailable: boolean = true;
  constructor(
    private vehicleId: string,
    public model: string,
    public make: string,
    public year: number,
    public dailyRate: number,
  ) {}

  getVehicleInfo(): string {
    return `===== ${this.vehicleId} Info ======\n Manufacturer : ${this.make}\n Model : ${this.model}\n Availability:${this.isAvailable}`;
  }

  rent(): void {
    this.isAvailable = false;
  }

  returnVehicle(): void {
    this.isAvailable = true;
    console.log(
      `Vehicle ${this.year} ${this.model} ${this.make} is available now!`,
    );
  }

  calculateRentalCost(days: number): number {
    if (days < 0) return 0;
    return this.dailyRate * days;
  }

  getVehicleId(): string {
    return this.vehicleId;
  }
}
