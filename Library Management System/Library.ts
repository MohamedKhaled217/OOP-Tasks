import { Member } from "./Member";
import { Book } from "./Book";

export class Library {
  public Books: Book[] = [];
  public Members: Member[] = [];
  public Name: string;

  constructor(Name: string) {
    this.Name = Name;
  }

  addBook(book: Book): void {
    this.Books.push(book);
  }

  registerMember(member: Member): void {
    this.Members.push(member);
  }

  lendBook(member: Member, isbn: string) {
    if (this.Books.length < 1) {
      console.log(`Sorry,Books are out of Stock`);
      return;
    }
    if (member.borrowedBooks.length == 3) {
      console.log(`Sorry,You have hit the limit`);
      return;
    }
    let book = this.Books.find((obj) => obj.isbn === isbn);
    if (book && book.isAvailable) {
      member.borrowBook(book);
      book.borrow();
    } else console.log(`Book with isbn : ${isbn} is not available\n`);
  }

  receiveBook(member: Member, isbn: string): void {
    let book = this.Books.find((obj) => obj.isbn === isbn);
    if (book && !book.isAvailable) {
      member.returnBook(book);
      book.returnBook();
    } else console.log(`Book with isbn : ${isbn} is not available\n`);
  }

  displayAvailableBooks(): void {
    console.log(`Available books in City Central Library:`);
    this.Books.forEach((book) => {
      if (book.isAvailable) book.getInfo();
    });
    console.log("\n");
  }
}
