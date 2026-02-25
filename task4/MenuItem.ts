export class MenuItem {
  public isAvailable: boolean = true;
  constructor(
    private itemId: string,
    public name: string,
    public description: string,
    public price: number,
    public category: string,
  ) {
    if (price <= 0) throw new Error("Price Must Be Positive");
    if (
      category !== "Appetizer" &&
      category !== "Main Course" &&
      category !== "Dessert" &&
      category !== "Beverage"
    )
      throw new Error("Category is not valid");
  }

  getItemId(): string {
    return this.itemId;
  }

  getItemInfo(): string {
    return `==== Item Info ====\n Id:${this.getItemId()}\n Name: ${this.name}\n Price: ${this.price}\n`;
  }
}

// Simple Test

// let burger = new MenuItem(
//   "B001",
//   "Cheese Burger",
//   "Extra Cheese Burger",
//   60,
//   "Main Course",
// );

// console.log(burger.getItemInfo());
