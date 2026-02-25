import { Menu } from "./Menu";
import { Order } from "./Order";

export class Restaurant {
  public freq: Map<string, number> = new Map();
  public orders: Order[] = [];
  constructor(
    public menu: Menu = new Menu(this.restaurantName),
    public restaurantName: string,
    public taxRate: number,
  ) {}

  createOrder(tableNumber: number): Order {
    let order = new Order(tableNumber);
    this.orders.push(order);
    let orderItems = order.getOrderItems();
    orderItems.forEach((x) => {
      if (this.freq.has(x[0])) this.freq.set(x[0], this.freq.get(x[0])! + x[1]);
      else this.freq.set(x[0], x[1]);
    });
    return order;
  }

  getOrder(orderId: string): Order | undefined {
    let order = this.orders.find((x) => x.getOrderId() === orderId);
    if (order) return order;
    else return undefined;
  }

  getOrdersByStatus(status: string): string {
    let orders = this.orders
      .filter((x) => x.status === status)
      .map((x) => x.getOrderSummary())
      .join("\n");
    return `=== ${status} Orders in ${this.restaurantName} restaurant===
    ${orders}`;
  }

  getActiveOrders(): string {
    let orders = this.orders
      .filter((x) => x.status !== "Completed")
      .map((x) => x.getOrderSummary())
      .join("\n");
    return `=== Active Orders in ${this.restaurantName} restaurant===
    ${orders}`;
  }

  completeOrder(orderId: string): void | undefined {
    let order = this.orders.find((x) => x.getOrderId() == orderId);
    if (order) order.status = "Completed";
    else return undefined;
  }

  getTotalRevenue(): number {
    let revenue = 0.0;
    this.orders.forEach((x) => (revenue += x.getTotal()));
    return revenue;
  }

  getPopularItems(count: number): void {
    let arr = Array.from(this.freq).sort((a, b) => b[1] - a[1]);
    arr.forEach((x) => {
      console.log(`- ${x[0]} has been Ordered ${x[1]} times\n`);
      count--;
      if (count === 0) return;
    });
  }
}
