export class Customer {
  constructor(
    private customerId: string,
    public name: string,
    public phone: string,
    public email: string,
    private driversLicenseNumber: string,
  ) {}

  getCustomerInfo(): string {
    return `========== Customer Info ==========\n Name: ${this.name}\n Phone Number: ${this.phone}\n Email: ${this.email}`;
  }

  getCustomerId(): string {
    return this.customerId;
  }
}
