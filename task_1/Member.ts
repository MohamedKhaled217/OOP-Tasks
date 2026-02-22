import { Book } from "./Book";

export class Member {
  public borrowedBooks: Book[] = [];
  private memberId: string;
  public name: string;

  constructor(name: string, memberId: string) {
    this.name = name;
    this.memberId = memberId;
  }

  getInfo(): void {
    console.log(
      `Name : ${this.name} , Id : ${this.memberId} , Borrowd Books : ${this.borrowedBooks.forEach((book) => console.log(book.getInfo()))}`,
    );
  }

  borrowBook(book: Book): void {
    this.borrowedBooks.push(book);
    console.log(`${this.name} borrowed: ${book.title}\n`);
  }

  returnBook(book: Book) {
    console.log(`${this.name} returned: ${book.title}\n`);
    this.borrowedBooks = this.borrowedBooks.filter(
      (bookObj) => bookObj.isbn !== book.isbn,
    );
  }
}
