import { MenuItem } from "./MenuItem";

export class Menu {
  public menuItems: MenuItem[] = [];
  constructor(public restaurantName: string) {}

  addMenuItem(item: MenuItem): string | undefined {
    if (item) {
      this.menuItems.push(item);
      return `Item Added to ${this.restaurantName}'s Menu\n`;
    } else return undefined;
  }

  removeMenuItem(itemId: string): string {
    this.menuItems = this.menuItems.filter((i) => i.getItemId() !== itemId);
    return `Item has beev Removed from ${this.restaurantName}'s Menu\n`;
  }

  getItemsByCategory(category: string): string {
    let result = this.menuItems.filter((i) => i.category == category);
    if (result.length === 0) {
      return `There is no Items with ${category} in ${this.restaurantName}\n`;
    }
    let arr = result.map(
      (x) => `Id:${x.getItemId()}  Name: ${x.name} Price: ${x.price}`,
    );
    return arr.join("\n");
  }

  searchItems(keyword: string): string {
    let item = this.menuItems.find((i) => i.name == keyword);
    if (!item)
      return `Item isn't available in ${this.restaurantName} restaurant \n`;
    return item.getItemInfo();
  }

  displayMenu() {
    let Appetizer = this.menuItems
      .filter((x) => x.category == "Appetizer")
      .map((x) => `- ${x.name}: ${x.description} - $${x.price}`)
      .join("\n");
    let MainCourse = this.menuItems
      .filter((x) => x.category == "Main Course")
      .map((x) => `- ${x.name}: ${x.description} - $${x.price}`)
      .join("\n");
    let Dessert = this.menuItems
      .filter((x) => x.category == "Dessert")
      .map((x) => `- ${x.name}: ${x.description} - $${x.price}`)
      .join("\n");
    let Beverage = this.menuItems
      .filter((x) => x.category == "Beverage")
      .map((x) => `- ${x.name}: ${x.description} - $${x.price}`)
      .join("\n");
    return `=== ${this.restaurantName} Menu ===

Appetizer:
${Appetizer}

Main Course:
${MainCourse}

Beverage:
${Beverage}

Dessert:
${Dessert}\n`;
  }
}
