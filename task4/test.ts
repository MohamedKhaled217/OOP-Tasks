import { Restaurant } from "./Restaurant";
import { Menu } from "./Menu";
import { Order } from "./Order";
import { MenuItem } from "./MenuItem";
import { OrderItem } from "./OrderItem";

// Create restaurant
let restaurant = new Restaurant("Tasty Bites", 0.08);

// Create menu items
let burger = new MenuItem(
  "M001",
  "Classic Burger",
  "Beef patty with lettuce, tomato, cheese",
  12.99,
  "Main Course",
);
let fries = new MenuItem(
  "M002",
  "French Fries",
  "Crispy golden fries",
  4.99,
  "Appetizer",
);
let salad = new MenuItem(
  "M003",
  "Caesar Salad",
  "Fresh romaine with caesar dressing",
  8.99,
  "Appetizer",
);
let soda = new MenuItem(
  "M004",
  "Soft Drink",
  "Coca-Cola, Sprite, or Fanta",
  2.99,
  "Beverage",
);
let cake = new MenuItem(
  "M005",
  "Chocolate Cake",
  "Rich chocolate layer cake",
  6.99,
  "Dessert",
);

// Add items to menu
restaurant.menu.addMenuItem(burger);
restaurant.menu.addMenuItem(fries);
restaurant.menu.addMenuItem(salad);
restaurant.menu.addMenuItem(soda);
restaurant.menu.addMenuItem(cake);

// Display menu
console.log(restaurant.menu.displayMenu());

// Create order for table 5
let order1 = restaurant.createOrder(5);
order1.addItem(burger, 2, "No onions");
order1.addItem(fries, 2, "Extra crispy");
order1.addItem(soda, 2, "No ice");

// Create order for table 2
let order2 = restaurant.createOrder(2);
order2.addItem(burger, 5, "No onions");
order2.addItem(fries, 2, "Extra crispy");
order2.addItem(soda, 1, "No ice");

// Display order summary
console.log(order1.getOrderSummary());

console.log(order2.getOrderSummary());

// Calculate with tip
let subtotal = order1.getSubtotal();
let tax = order1.getTax();
let tip = order1.calculateTip(0.15); // 15% tip
let total = order1.getTotal() + tip;

console.log("\nSubtotal: $" + subtotal);
console.log("Tax (8%): $" + tax);
console.log("Tip (15%): $" + tip);
console.log("Total: $" + total);

// Update order status
order1.updateStatus("Preparing");
console.log("\nOrder status: " + order1.status);

order1.updateStatus("Ready");
console.log("Order status: " + order1.status);

// Complete order
restaurant.completeOrder(order1.getOrderId());
console.log("Order status: " + order1.status);

// Get revenue
console.log("\nTotal Revenue: $" + restaurant.getTotalRevenue());

restaurant.getPopularItems(2);
