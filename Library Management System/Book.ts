export class Book {
  constructor(
    public title: string,
    public author: string,
    public isbn: string,
    public isAvailable: boolean = true,
  ) {}

  getInfo = (): void =>
    console.log(`- ${this.title} by ${this.author} (ISBN: ${this.isbn})`);

  borrow = (): void => {
    this.isAvailable = false;
  };

  returnBook(): void {
    this.isAvailable = true;
  }
}
