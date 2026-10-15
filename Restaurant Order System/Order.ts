import { MenuItem } from "./MenuItem";
import { OrderItem } from "./OrderItem";

export class Order {
  private static counter = 0;
  private orderId: string = "";
  private orderItems: OrderItem[] = [];
  private orderTime!: Date;
  public status: string = "Pending";
  private tax: number = 0.08;
  constructor(public tableNumber: number) {
    Order.counter++;
    this.orderId = `ORD${Order.counter}`;
    this.orderTime = new Date();
  }

  addItem(menuItem: MenuItem, quantity: number, instructions: string): string {
    const newOrderItem = new OrderItem(menuItem, quantity, instructions);
    this.orderItems.push(newOrderItem);
    return `Item Added to Order${this.orderId} Successfully`;
  }

  removeItem(itemId: string): string {
    this.orderItems = this.orderItems.filter(
      (o) => o.menuItem.getItemId() !== itemId,
    );
    return `Item Removed from Order${this.orderId} Successfully`;
  }

  getSubtotal(): number {
    let total = 0.0;
    this.orderItems.forEach((o) => (total += o.getSubtotal()));
    return total;
  }

  getTax(): number {
    return this.tax * this.getSubtotal();
  }

  calculateTip(percentage: number): number {
    return percentage * this.getSubtotal();
  }

  getTotal(): number {
    return this.getTax() + this.getSubtotal();
  }

  updateStatus(newStatus: string): string {
    let oldStatus = this.status;
    this.status = newStatus;
    return `Order Status Changed from ${oldStatus} to ${newStatus}\n`;
  }

  getOrderSummary(): string {
    let OrderItems = this.orderItems
      .map(
        (x) =>
          `- ${x.menuItem.name} x${x.quantity} - $${x.getSubtotal()}\n  Special: ${x.specialInstructions}`,
      )
      .join("\n");
    return `=== Order Summary ===
Order ID: ${this.orderId}
Table: ${this.tableNumber}
Time: ${this.orderTime.toLocaleString()}
Status: ${this.status}\n
Items:
${OrderItems}`;
  }

  getOrderId(): string {
    return this.orderId;
  }

  getOrderItems(): [string, number][] {
    let items = this.orderItems.map(
      (x) => [x.menuItem.getItemId(), x.quantity] as [string, number],
    );
    return items;
  }
}
