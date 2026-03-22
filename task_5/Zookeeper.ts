import { Animal } from "./Animal";

export class Zookeeper {
  public assignedAnimals: Animal[] = [];
  constructor(
    public employeeId: string,
    public name: string,
    public specialization: string,
  ) {}

  feedAnimal(animal: Animal): string {
    return `${this.name} fed ${animal.name} (${animal.species})`;
  }

  checkHealth(animal: Animal): string {
    return `${this.name} checked health of ${animal.name} (${animal.species}) - Status: ${animal.healthStatus}`;
  }

  getWorkload(): string {
    return `${this.name}'s workload: ${this.assignedAnimals.length} animals`;
  }

  addAnimal(animal: Animal): void {
    this.assignedAnimals.push(animal);
  }
}
