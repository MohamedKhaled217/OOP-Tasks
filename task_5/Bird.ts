import { Animal } from "./Animal";

export class Parrot extends Animal {
  public vocabulary: string[] = [];
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
    public canTalk: boolean,
  ) {
    super(animalId, name, species, age, healthStatus, dailyFoodCost);
  }

  makeSound(): string {
    return "Squawk!";
  }

  getHabitat(): string {
    return "Rainforest";
  }

  speak(): string | undefined {
    if (this.vocabulary.length === 0) return `No Learned Vocabs`;
    const val =
      this.vocabulary[Math.floor(Math.random() * this.vocabulary.length)];
    if (!val) return undefined;
    else return val;
  }
}

export class Eagle extends Animal {
  constructor(
    public animalId: string,
    public name: string,
    public species: string,
    public age: number,
    public healthStatus: string,
    public dailyFoodCost: number,
    public wingspan: number,
    public diveSpeed: number,
  ) {
    super(animalId, name, species, age, healthStatus, dailyFoodCost);
  }

  makeSound(): string {
    return "Screech!";
  }

  getHabitat(): string {
    return "Mountains";
  }
}
