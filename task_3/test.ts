import { Vehicle } from "./Vehicle";
import { Customer } from "./Customer";
import { Rental } from "./Rental";
import { RentalAgency } from "./RentalAgency";

// Create rental agency
let agency = new RentalAgency("Prime Car Rentals");

// Add vehicles to fleet
let car1 = new Vehicle("V001", "Toyota", "Camry", 2022, 45.0);
let car2 = new Vehicle("V002", "Honda", "Accord", 2023, 50.0);
let car3 = new Vehicle("V003", "Tesla", "Model 3", 2023, 85.0);

agency.addVehicle(car1);
agency.addVehicle(car2);
agency.addVehicle(car3);

// Register customers
let customer1 = new Customer(
  "C001",
  "Alice Johnson",
  "555-0123",
  "alice@email.com",
  "DL123456",
);
let customer2 = new Customer(
  "C002",
  "Bob Smith",
  "555-0456",
  "bob@email.com",
  "DL789012",
);

agency.registerCustomer(customer1);
agency.registerCustomer(customer2);

// Display available vehicles
agency.displayFleet();

// Create rentals
let rental1 = agency.createRental("R001", customer1, car1, 5);
console.log("Rental created: " + rental1.getRentalId());
console.log("Total Cost: $" + rental1.getTotalCost());

let rental2 = agency.createRental("R002", customer2, car3, 3);
console.log("Rental created: " + rental2.getRentalId());
console.log("Total Cost: $" + rental2.getTotalCost());

// Display available vehicles after rentals
console.log("After rentals:");
agency.displayFleet();

// Complete a rental
agency.completeRental(rental1!.getRentalId());
// print("Rental " + rental1.rentalId + " completed!");

// Display available vehicles
agency.displayFleet();

// Display customer rental history
let customerRentals = agency.getCustomerRentals("C001");
// print("Alice's rental history: " + customerRentals.length + " rental(s)");
