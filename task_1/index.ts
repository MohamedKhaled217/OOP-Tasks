import { Member } from "./Member";
import { Book } from "./Book";
import { Library } from "./Library";

// Create a library
let library = new Library("City Central Library");

// Create books
let book1 = new Book("Design Patterns", "Gang of Four", "978-0201633610");
let book2 = new Book("Clean Code", "Robert Martin", "978-0132350884");
let book3 = new Book("The Pragmatic Programmer", "Andy Hunt", "978-0135957059");

let book4 = new Book(
  "Harry Potter and the Chamber of Secrets",
  "JK Rowling",
  "978-0123456789",
);

let book5 = new Book(
  "Harry Potter and the Prisoner of Azkaban ",
  "JK Rowling",
  "978-0123456779",
);

// Add books to library
library.addBook(book1);
library.addBook(book2);
library.addBook(book3);
library.addBook(book4);
library.addBook(book5);

// Register members
let member1 = new Member("Alice Johnson", "M001");
let member2 = new Member("Bob Smith", "M002");
let member3 = new Member("Mohamed Khaled", "M021");

library.registerMember(member1);
library.registerMember(member2);
library.registerMember(member3);

// Display available books
library.displayAvailableBooks();

// Member borrows a book
library.lendBook(member1, "978-0201633610");

// Member borrows a unavailable book
library.lendBook(member2, "978-0201633610");

// Member borrows a book
library.lendBook(member2, "978-0132350884");

// Display available books again
library.displayAvailableBooks();

// Member returns a book
library.receiveBook(member1, "978-0201633610");

// Display available books again
library.displayAvailableBooks();

// ===============================================================================

// Test the Limit of Books Each Member Have

// library.lendBook(member3 , "978-0123456789");
// library.lendBook(member3 , "978-0123456779");
// library.lendBook(member3 , "978-0135957059");
// library.lendBook(member3 , "978-0132350884");

// library.displayAvailableBooks()
