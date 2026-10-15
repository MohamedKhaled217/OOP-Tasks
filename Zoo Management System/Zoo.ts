import { Animal } from "./Animal";
import { Zookeeper } from "./Zookeeper";

export class Zoo {
  public animals: Animal[] = [];
  public zookeepers: Zookeeper[] = [];

  constructor(public zooName: string) {}

  addAnimal(animal: Animal): void {
    this.animals.push(animal);
  }

  removeAnimal(animalId: string) {
    if (this.animals.length === 0) return `No Animals Available`;
    this.animals = this.animals.filter((x) => x.animalId !== animalId);
  }

  assignAnimalToKeeper(animal: Animal, keeper: Zookeeper) {
    if (this.animals.length === 0) return `No Animals Available`;

    // let found = false;
    // this.animals.forEach((x) => {
    //   if (x.animalId === animal.animalId) {
    //     found = true;
    //   }
    // });
    
    let found = this.animals.some((x) => x.animalId === animal.animalId);
    if (!found) return `Animal is not in the zoo`;
    keeper.addAnimal(animal);
    return `Animal assigned to keeper`;
  }

  getAnimalsByHabitat(habitat: string): string {
    if (this.animals.length === 0) return `No Animals Available`;
    return this.animals
      .filter((x) => x.getHabitat() === habitat)
      .map((x) => `${x.name} (${x.species})`)
      .join("\n");
  }
  getAnimalsBySpecies(species: string): string {
    if (this.animals.length === 0) return `No Animals Available`;
    return this.animals
      .filter((x) => x.species === species)
      .map((x) => `${x.name} (${x.species})`)
      .join("\n");
  }

  calculateTotalWeeklyCost(): number {
    let cost = 0;
    this.animals.forEach((x) => (cost += x.calculateWeeklyCost()));
    return cost * 1.0;
  }

  displayAllAnimals(): string {
    if (this.animals.length === 0) return `No Animals Available`;
    return this.animals.map((x) => x.getAnimalInfo()).join("\n");
  }

  getZooStatistics(): string {
    let AgeAvg = 0.0;
    let Habitats = new Set<string>();

    this.animals.forEach((x) => {
      AgeAvg += x.age;
      Habitats.add(x.getHabitat());
    });

    return `=== ${this.zooName} Statistics ===
    Total Animals: ${this.animals.length}
    Total Zookeepers: ${this.zookeepers.length}
    Habitats Represented: ${Habitats.size}
    Total Weekly Maintenance: $${this.calculateTotalWeeklyCost()}
    Average Animal Age: ${isNaN(AgeAvg / this.animals.length) ? 0 : AgeAvg / this.animals.length} years`;
  }
}
