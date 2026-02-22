# Code Review: Library Management System

**Project:** OOP Task 1 – Library with Books and Members  
**Reviewed:** `Book.ts`, `Member.ts`, `Library.ts`, `tsconfig.json`

---

## Overall Rating

| Category           | Score (1–5) | Notes                          |
|--------------------|-------------|--------------------------------|
| OOP design         | 4/5         | Clear classes, good separation |
| Correctness        | 3.5/5       | One bug in `Member.getInfo`    |
| Consistency        | 3.5/5       | Mixed styles, magic numbers    |
| Maintainability    | 4/5         | Readable, could split demo     |
| TypeScript usage   | 4/5         | Good typing, a few gaps        |

**Overall: 3.8 / 5** – Solid OOP solution with a few bugs and style improvements to make.

---

## What Works Well

### 1. **Clear domain model**
- **Book**, **Member**, and **Library** have distinct responsibilities.
- Relationships are clear: Library has books and members; Member has borrowed books; Book has availability.

### 2. **Sensible business rules**
- Borrow limit of 3 books per member is enforced in `Library.lendBook`.
- Availability is checked before lending and before receiving.
- Empty stock is handled before searching by ISBN.

### 3. **TypeScript usage**
- Types are used for method parameters and properties.
- Constructor parameter properties in `Book` are used cleanly.
- Arrays are properly typed (`Book[]`, `Member[]`).

### 4. **Demo / scenario coverage**
- Demo in `Library.ts` covers: add books, register members, lend, return, and edge cases (e.g. borrowing an already borrowed book).
- Commented block for testing the 3-book limit shows you thought about that scenario.

### 5. **Configuration**
- `tsconfig.json` is consistent: CommonJS, `moduleResolution: "node"`, strict options. Good base for a small Node project.

---

## Issues and Recommendations

### 1. **Bug: `Member.getInfo()` (Member.ts)**

```ts
console.log(
  `Name : ${this.name} , Id : ${this.memberId} , Borrowd Books : ${this.borrowedBooks.forEach((book) => console.log(book.getInfo()))}`,
);
```

- **Problems:**  
  - `forEach` returns `undefined`, so the string becomes `"Borrowd Books : undefined"`.  
  - Typo: **"Borrowd"** → **"Borrowed"**.  
  - Mixing a single `console.log` with inner `console.log` from `book.getInfo()` is confusing and prints in an odd order.

- **Suggestion:** Build a string for borrowed books and log once, e.g.:

```ts
getInfo(): void {
  const bookList = this.borrowedBooks.map((b) => b.title).join(", ") || "none";
  console.log(`Name: ${this.name}, Id: ${this.memberId}, Borrowed books: ${bookList}`);
}
```

(If you still want one line per book, log the header first, then loop and call `book.getInfo()` in a separate loop instead of inside the template literal.)

---

### 2. **Hardcoded library name (Library.ts)**

```ts
console.log(`Available books in City Central Library:`);
```

- **Problem:** The library name is already stored in `this.Name` but not used here.
- **Suggestion:** Use `this.Name` so it works for any library instance, e.g.  
  `console.log(\`Available books in ${this.Name}:\`);`

---

### 3. **Class method style in `Book` (Book.ts)**

- You use a mix of:
  - Arrow properties: `getInfo = (): void => ...`, `borrow = (): void => ...`
  - Regular method: `returnBook(): void { ... }`
- **Problem:** Arrow class fields create a new function per instance and don’t behave like normal prototype methods (e.g. for overriding). Mixing styles makes the class harder to reason about.
- **Suggestion:** Use regular methods everywhere for consistency and slightly better memory/override behavior:

```ts
getInfo(): void {
  console.log(`- ${this.title} by ${this.author} (ISBN: ${this.isbn})`);
}
borrow(): void {
  this.isAvailable = false;
}
```

---

### 4. **Magic number: borrow limit**

- The limit `3` appears directly in `Library.lendBook`. For readability and future changes, use a named constant, e.g. at the top of the file or on the class:

```ts
private static readonly MAX_BOOKS_PER_MEMBER = 3;
// ...
if (member.borrowedBooks.length >= Library.MAX_BOOKS_PER_MEMBER) { ... }
```

---

### 5. **No check that member belongs to the library**

- `lendBook(member, isbn)` and `receiveBook(member, isbn)` don’t check that `member` is in `this.Members`.
- **Suggestion:** Add a guard, e.g. `if (!this.Members.includes(member)) { ... return; }` (or a helper like `hasMember(member)`), so only registered members can borrow/return.

---

### 6. **Return types and API design**

- `lendBook` has no explicit return type; `Member.returnBook` has no return type. For consistency and clarity, give all public methods an explicit return type (e.g. `void`).
- Using `console.log` for “errors” (e.g. book not available) is fine for a demo. For a reusable API you might later add return values (e.g. `boolean`) or throw in invalid cases so callers can handle them programmatically.

---

### 7. **Separation of demo and domain logic**

- The bottom half of `Library.ts` is demo code (creating library, books, members, calling methods). Keeping it in the same file works, but for maintainability you could move it to e.g. `index.ts` or `demo.ts` and leave `Library.ts` as a pure domain module. Optional improvement.

---

## Summary

- **Strengths:** Good OOP structure, clear domain, sensible rules (borrow limit, availability), and a thoughtful demo. TypeScript and tsconfig are used well.
- **Must-fix:** `Member.getInfo()` bug (and typo) so “Borrowed books” is correct and readable.
- **Should-fix:** Use `this.Name` in `displayAvailableBooks`, consider member registration check in lend/return, and replace the magic number 3 with a constant.
- **Nice-to-have:** Consistent method style in `Book`, explicit return types, and splitting the demo into a separate file.

With the bug fix and the small improvements above, this would comfortably sit at **4+ / 5** for an OOP task.
