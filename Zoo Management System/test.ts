import { Eagle, Parrot } from "./Bird";
import { Lion, Elephant } from "./Mammal";
import { Snake } from "./Reptile";
import { Zoo } from "./Zoo";
import { Zookeeper } from "./Zookeeper";

const zoo = new Zoo("Safari World");

const lion = new Lion(
  "A001",
  "Simba",
  "African Lion",
  5,
  "Healthy",
  50.0,
  "Golden",
  3,
);
const elephant = new Elephant(
  "A002",
  "Dumbo",
  "African Elephant",
  15,
  "Healthy",
  80.0,
  2.5,
  5000,
);
const parrot = new Parrot("A003", "Polly", "Macaw", 8, "Healthy", 10.0, true);
parrot.vocabulary.push("Hello", "Goodbye", "Pretty bird");
const snake = new Snake("A004", "Kaa", "Python", 10, "Healthy", 15.0, true, 4.5);
const eagle = new Eagle(
  "A005",
  "Freedom",
  "Bald Eagle",
  6,
  "Healthy",
  20.0,
  2.3,
  320,
);

zoo.addAnimal(lion);
zoo.addAnimal(elephant);
zoo.addAnimal(parrot);
zoo.addAnimal(snake);
zoo.addAnimal(eagle);

const keeper1 = new Zookeeper("K001", "John Smith", "Mammals");
const keeper2 = new Zookeeper("K002", "Jane Doe", "Birds and Reptiles");

zoo.zookeepers.push(keeper1, keeper2);

zoo.assignAnimalToKeeper(lion, keeper1);
zoo.assignAnimalToKeeper(elephant, keeper1);
zoo.assignAnimalToKeeper(parrot, keeper2);
zoo.assignAnimalToKeeper(snake, keeper2);
zoo.assignAnimalToKeeper(eagle, keeper2);

console.log(zoo.displayAllAnimals());

console.log("\n=== Animal Sounds ===");
for (const animal of zoo.animals) {
  console.log(`${animal.name} says: ${animal.makeSound()}`);
}

console.log("\n=== Savanna Animals ===");
const savannaAnimals = zoo.getAnimalsByHabitat("Savanna");
for (const line of savannaAnimals.split("\n").filter((s) => s.length > 0)) {
  console.log(`- ${line}`);
}

const weeklyCost = zoo.calculateTotalWeeklyCost();
console.log(`\nTotal Weekly Cost: $${weeklyCost}`);

console.log("\n=== Zookeeper Activities ===");
console.log(keeper1.feedAnimal(lion));
console.log(keeper1.checkHealth(elephant));
console.log(
  `${keeper1.name}'s workload: ${keeper1.assignedAnimals.length} animals`,
);

console.log(zoo.getZooStatistics());
