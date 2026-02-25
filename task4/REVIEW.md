# Code Review — OOP Task 4

## Overall rating: 6 / 10

- **Summary:** The project demonstrates solid OOP structure and clear separation between `Menu`, `MenuItem`, `Order`, `OrderItem`, and `Restaurant`. Types and basic validation are present. However, there are functional bugs, inconsistent return types, unsafe initializations, and a few stylistic/semantic issues that reduce robustness.

## File-by-file feedback

- `Menu.ts`:
  - **Issue:** Uses `==` for string comparisons; prefer `===` for strict equality.  
  - **Issue:** `removeMenuItem` message has a typo ("beev").  
  - **Design:** Methods return formatted strings for operations (`addMenuItem`, `removeMenuItem`) instead of booleans or void/error; separate logic from presentation.  
  - **Suggestion:** Replace repeated category filtering in `displayMenu` with a loop or map keyed by category; consider an enum for categories.  
  - **Suggestion:** Return structured data (arrays/objects) from `getItemsByCategory` and leave string formatting to the caller.

- `MenuItem.ts`:
  - **Good:** Validations for `price` and `category` are implemented.  
  - **Suggestion:** Use a `Category` enum/type union instead of repeated string literals.  
  - **Suggestion:** Consider making `itemId` readonly and expose a nicer `toString()` or `getItemInfo()` that formats price (e.g., `price.toFixed(2)`).

- `OrderItem.ts`:
  - **Issue:** `getOrderItemDetails` prints `this.menuItem` directly, which will show `[object Object]`; use `menuItem.name` or `menuItem.getItemInfo()`.  
  - **Suggestion:** Consider using `readonly` for `menuItem` and `specialInstructions` if they won't change.

- `Order.ts`:
  - **Issue:** `tax` is an internal constant (0.08) while `Restaurant` has a `taxRate` parameter — these should be consistent (use restaurant taxRate).  
  - **Issue:** `getTotal()` excludes tip (fine if by design) but naming could clarify: `getTotalBeforeTip()` vs `getTotalWithTip(percentage)`.  
  - **Style:** Variable `OrderItems` in `getOrderSummary` is capitalized; use camelCase.  
  - **Suggestion:** Define a union type for allowed statuses (`'Pending' | 'Preparing' | 'Ready' | 'Completed'`) rather than free-form strings.
  - **Concurrency/uniqueness:** `Order.counter` simple static counter may collide in multi-instance or persisted environments; consider UUIDs if needed.

- `Restaurant.ts`:
  - **Bug:** `menu` is initialized as `new Menu(this.restaurantName)` at field declaration time — `this.restaurantName` is undefined until the constructor runs. Initialize `menu` inside the constructor instead.  
  - **Bug:** `createOrder` pushes a newly created empty order and immediately reads `order.getOrderItems()` to update `freq` — the order has no items yet, so `freq` will not be updated. Frequency tracking must occur when items are added to an order (e.g., in `Order.addItem`) or after items are appended.  
  - **Issue:** `taxRate` constructor param is unused; `Order` uses its own internal `tax`. Unify tax handling so `Restaurant` controls tax rate.  
  - **Suggestion:** `getPopularItems` prints to console and uses `count--` inside `forEach` — `return` inside `forEach` doesn't stop the loop. Use a slice of the sorted array or a simple for-loop.

- `test.ts`:
  - **Good:** Provides a useful usage example and exercises basic flows.  
  - **Note:** Because of the `Restaurant` bugs above, `getPopularItems` and frequency tracking won't work as expected in this test.

## Priority fixes (apply first)
1. Move `menu` initialization into `Restaurant` constructor (avoid using `this.restaurantName` before constructor assigns it).  
2. Fix frequency counting logic: update `freq` when items are added to an order (or when an order is finalized), not immediately after creating an empty order.  
3. Unify tax handling: make `Order` accept taxRate from the `Restaurant` or compute totals in `Restaurant` consistently.  
4. Correct `getOrderItemDetails` to format `menuItem` fields rather than embedding the object.  
5. Replace string category literals with an enum or union type and use `===` for comparisons everywhere.

## Medium/low priority improvements
- Use clearer return types (boolean/void) for state-mutating methods and separate textual messages for UI.  
- Improve naming consistency and formatting (camelCase variables, consistent spacing).  
- Add unit tests around: order totals (tax/tip), frequency counting, menu category filtering, and validation errors.
- Consider formatting prices with two decimals when presenting to users (use `price.toFixed(2)`).

## Example small patch suggestions
- In `Restaurant` constructor:
  - `this.menu = new Menu(this.restaurantName);` (move here from field initializer)
- When `Order.addItem` is called, notify the parent `Restaurant` or expose a hook so `Restaurant` can increment `freq` for the added item.
- In `getPopularItems(count)`, return an array of popular items instead of printing, or iterate using a `for` loop to respect `count`.

## Final thoughts
- The project shows good fundamental design and is easy to follow. With the few critical bug fixes above (menu init, freq counting, tax consistency) and some small refactors (enums, return types), this will be robust and more maintainable.

---

If you want, I can: apply the high-priority fixes as patches, add a few unit tests, or refactor categories into an enum — which would you like me to start with?