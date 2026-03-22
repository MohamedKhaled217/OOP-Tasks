import { Animal } from "./Animal";

export class Lion extends Animal {
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
    public maneColor: string,
    public prideSize: number,
  ) {
    super(animalId, name, species, age, healthStatus, dailyFoodCost);
  }

  makeSound(): string {
    return "Roar!";
  }

  getHabitat(): string {
    return "Savanna";
  }
}

export class Elephant extends Animal {
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
    public tuskLength: number,
    public weight: number,
  ) {
    super(animalId, name, species, age, healthStatus, dailyFoodCost);
  }

  makeSound(): string {
    return "Trumpet!";
  }

  getHabitat(): string {
    return "Grassland";
  }
}

export class Monkey extends Animal {
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
    public tailLength: number,
    public favoriteFood: string,
  ) {
    super(animalId, name, species, age, healthStatus, dailyFoodCost);
  }

  makeSound(): string {
    return "Ooh ooh ah ah!";
  }

  getHabitat(): string {
    return "Rainforest";
  }
}
