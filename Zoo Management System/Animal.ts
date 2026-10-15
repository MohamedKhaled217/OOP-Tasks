export abstract class Animal {
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
  ) {}

  abstract makeSound(): string;
  abstract getHabitat(): string;

  getAnimalInfo(): string {
    return `${this.animalId} - ${this.name} (${this.species}) - Age: ${this.age}`;
  }

  calculateWeeklyCost(): number {
    return this.dailyFoodCost * 7.0;
  }
}
