import { MenuItem } from "./MenuItem";

export class OrderItem {
  constructor(
    public menuItem: MenuItem,
    public quantity: number,
    public specialInstructions: string,
  ) {
    if (this.quantity <= 0) throw new Error("Quantity must be Positive");
  }

  getSubtotal(): number {
    return this.menuItem.price * 1.0 * this.quantity;
  }

  getOrderItemDetails(): string {
    return `==== Order Details ====\n Item:${this.menuItem}\n Quantity:${this.quantity}\n Special Instructions: ${this.specialInstructions}\n Total Price: ${this.getSubtotal()}`;
  }
}
