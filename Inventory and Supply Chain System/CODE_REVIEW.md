# 🔍 Code Review (v2) — Inventory & Supply Chain System

> **Second pass** — reflects the current state of the code after fixes were applied from the first review.

---

## ✅ Issues Fixed Since v1

Great work — you addressed **9 out of 15** issues from the first review:

| # | Issue | Status |
|---|---|---|
| 1 | `removeStock()` logic order bug | ✅ Fixed — checks `== 0` first, then `isLowStock()` |
| 2 | `PurchaseOrder` constructor ignored `orderDate` param | ✅ Fixed — now uses the parameter |
| 3 | `PurchaseOrder.status` never initialized | ✅ Fixed — set to `OrderStatus.Pending` in constructor |
| 4 | `orderItems` was package-private | ✅ Fixed — now `private` |
| 5 | `productsCatalog` was package-private | ✅ Fixed — now `private` |
| 7 | `FreightShipping.calculateCost()` discount logic wrong | ✅ Fixed — now correctly subtracts `Calc * discount` |
| 10 | `Product.calculateShippingCost()` wrong responsibility | ✅ Fixed — method removed |
| 11 | `Shipment.count` started at 0 (inconsistent with PO-1) | ✅ Fixed — now starts at 1 |
| 9 | `Warehouse.capacity` never enforced | ✅ Fixed — `addStockItem()` now checks capacity |
| 13 | `get*Info()` naming for void methods | ⚠️ Partially fixed — `Product`, `Supplier`, `PurchaseOrder` renamed to `print*`, but `Shipment.getShipmentInfo()` still uses `get` prefix |

---

## 🐛 Remaining Issues (from v1)

### 1. `ExpressShipping` and `FreightShipping` create `new StandardShipping()` on every call
**Files:** `ExpressShipping.java` (line 11, 16), `FreightShipping.java` (line 13)

Both classes instantiate `new StandardShipping()` inside `getEstimatedDays()` (and `ExpressShipping.calculateCost()` does it too). This is wasteful — the same instance can be reused as a field. More importantly, it creates a **hidden coupling**: if `StandardShipping`'s constructor changes (e.g., starts requiring parameters), both subclasses break silently.

**Suggested fix:** Store a `private static final StandardShipping STANDARD = new StandardShipping();` field, or better yet — use inheritance (`extends StandardShipping`) and call `super` methods.

---

### 2. `ExpressShipping.getEstimatedDays()` and `FreightShipping.getEstimatedDays()` can return zero or negative
**Files:** `ExpressShipping.java` (line 11), `FreightShipping.java` (line 13)

Both do `StandardShipping.getEstimatedDays(distance) - 2`. For distances ≤ 400 km, `StandardShipping` returns 1 or 2 days, so subtracting 2 gives **-1 or 0 days** — which is physically impossible. Also, `FreightShipping` (bulk/slow shipping) being *faster* than Express makes no semantic sense.

```java
// distance = 100 km → standard returns 1 → express returns -1 days 🤯
return new StandardShipping().getEstimatedDays(distance) - 2;
```

**Suggested fix:** Use `Math.max(1, ...)` to guarantee at least 1 day, and give `FreightShipping` a *longer* delivery time (e.g., `+ 3`), not a shorter one.

---

### 3. `Warehouse.addProduct()` overwrites quantity instead of adding
**File:** `Warehouse.java` (line 31)

When a product already exists, you call `it.setQuantity(quantity)` which **replaces** the current stock with the new amount instead of *adding* to it. If a warehouse has 100 units and you call `addProduct(p, 50, 10)`, the stock drops to 50.

```java
// This REPLACES, not ADDS:
it.setQuantity(quantity);

// Should be:
it.addStock(quantity);
```

---

### 4. `Product<T>` raw type usage throughout the codebase
**Files:** `StockItem.java` (line 13), `OrderItem.java` (line 2), `Supplier.java` (line 12), `Warehouse.java` (line 28)

`Product<T>` is declared generic, but **every single usage** across the codebase uses the raw type `Product` without a type argument. This generates compiler warnings and completely defeats the purpose of the generic. Either:
- Use `Product<String>` (or whatever your ID type is) everywhere, **or**
- Remove the generic entirely and make `productId` a `String`

---

### 5. No `toString()`, `equals()`, or `hashCode()` overrides
**Files:** `Supplier.java`, `Product.java`

`SupplyChainManager.registerSupplier()` (line 45) calls `suppliers.contains(supplier)` — but `Supplier` doesn't override `equals()`, so this only detects the **exact same object reference**. Two `Supplier` instances with the same `supplierId` would not be considered equal.

Same issue with `Supplier.canSupply()` calling `productsCatalog.contains(product)` — relies on `Product.equals()` which defaults to reference equality.

Also, `Product.printProductInfo()` calls `supplier.toString()` (line 32), but without a `toString()` override, this prints something like `Supplier@1a2b3c4d`.

---

### 6. `Shipment.getShipmentInfo()` — still uses `get` prefix for a void method
**File:** `Shipment.java` (line 42)

You renamed the other three methods (`printProductInfo`, `printSupplierInfo`, `printOrderSummary`) but missed this one. Should be `printShipmentInfo()` for consistency.

---

### 7. `Main.java` is still the IntelliJ template — no demo code
**File:** `Main.java`

Still contains the auto-generated `Hello and welcome!` template. There's no way to verify the system works end-to-end.

---

## 🆕 New Issues Found in v2

### 8. `orderWarehouses` map is populated but never read
**File:** `SupplyChainManager.java` (lines 15–16, 70)

You declare an `IdentityHashMap<PurchaseOrder, Warehouse>` and populate it in `createPurchaseOrder()` (line 70), but **no method ever reads from it**. This is dead code. Since you now pass `warehouse` directly into the `PurchaseOrder` constructor, this map is redundant.

---

### 9. `demandHistory` map is declared but never used
**File:** `SupplyChainManager.java` (lines 19–20)

`Map<String, List<Integer>> demandHistory` is declared and initialized but **never populated or read** anywhere in the class. This is dead code — either implement demand tracking or remove it.

---

### 10. `PurchaseOrder.getOrderItems()` returns the mutable internal list
**File:** `PurchaseOrder.java` (lines 84–86)

```java
public List<OrderItem> getOrderItems() {
    return orderItems;  // ← exposes internal list
}
```

Any caller can `order.getOrderItems().clear()` and wipe out all items. This breaks encapsulation. You correctly use `Collections.unmodifiableList()` in `SupplyChainManager` — apply the same pattern here.

---

### 11. `StockItem.removeStock()` throws if observer is null — should be optional
**File:** `StockItem.java` (lines 60–61, 75–76)

If no observer was registered and stock hits zero, `removeStock()` throws `IllegalArgumentException("out of stock Observer is not initialized")`. Observers should be **optional** — the stock operation itself should still succeed. The `throw` makes it impossible to use `StockItem` without registering observers.

```java
// Current: crashes if no observer set
if(stockDepletedObserver == null){
    throw new IllegalArgumentException("out of stock Observer is not initialized");
}

// Better: silently skip notification
if(stockDepletedObserver != null){
    stockDepletedObserver.sendAlert(data);
}
```

---

### 12. `StockItem.reorderLevel` defaults to 0 — `isLowStock()` is misleading
**File:** `StockItem.java` (lines 15, 42–44)

`reorderLevel` is an `int` field (defaults to 0) and is only set via `setReorderLevel()`. If the caller forgets to set it, `isLowStock()` returns `quantity < 0`, which is never true under normal conditions. This silently disables the low-stock detection. Consider requiring `reorderLevel` in the constructor.

---

### 13. `StockItem` has no `getQuantity()` — no way to read stock level externally
**File:** `StockItem.java`

There's a `setQuantity()` but **no `getQuantity()`** method. External code cannot check the current stock level of an item. This also means there's no way to display the quantity in `Warehouse` without adding a getter.

---

### 14. `Warehouse.getStockItem()` prints to console on not-found — side effect in a getter
**File:** `Warehouse.java` (lines 48–54)

```java
public StockItem getStockItem(String sku){
    if(this.stockItems.containsKey(sku)){
        return this.stockItems.get(sku);
    }
    System.out.println("this product is not exist");  // ← side effect in a getter
    return null;
}
```

A getter method should not print to the console. Either return `null` silently (and let the caller handle it), or throw an exception. Also, grammar: "does not exist" not "is not exist".

---

### 15. Enum constants don't follow Java naming convention
**File:** `OrderStatus.java` (line 2)

```java
Pending , Approved,Shipped,Delivered,Cancelled
```

Java convention for enum constants is `UPPER_SNAKE_CASE`: `PENDING`, `APPROVED`, `SHIPPED`, `DELIVERED`, `CANCELLED`.

---

## 👏 What You Did Well

### 1. **Strategy Pattern — Excellently Applied**
Your `ShippingStrategy` interface with `StandardShipping`, `ExpressShipping`, and `FreightShipping` is a textbook, clean application of the Strategy pattern. The interface is minimal and focused with exactly the right methods.

### 2. **Observer Pattern with Functional Interface**
Using `@FunctionalInterface` for `StockObserver` is a smart, modern Java approach. It allows callers to pass lambdas for stock alerts, which is elegant and flexible. The `StockEventData` record is a great use of Java records for immutable event data.

### 3. **Good Input Validation Throughout**
You consistently validate inputs with `IllegalArgumentException` across almost all methods (`StockItem`, `OrderItem`, `Warehouse.searchProducts`, etc.). This is defensive programming done right.

### 4. **Good Use of Java Streams**
Methods like `getLowStockItems()`, `searchProducts()`, `getProductsByCategory()`, and `getTopValueProducts()` all use streams fluently and idiomatically. The `SupplyChainManager` methods also use streams well.

### 5. **Unmodifiable List Returns in SupplyChainManager**
Returning `Collections.unmodifiableList(...)` from getters in `SupplyChainManager` is a great practice — it protects internal state from being modified by callers.

### 6. **Clean Class Decomposition**
The overall class structure makes sense: each class has a clear, focused responsibility.

### 7. **`StockEventData` as a Record**
Using a Java `record` for `StockEventData` is the right modern choice — immutable, concise, auto-generated `equals`/`hashCode`/`toString`.

### 8. **Good Fix Follow-Through**
You addressed 9 of the 15 original issues, which shows attentiveness. Especially the `removeStock()` logic fix and the capacity enforcement — those required careful thought.

---

## 📊 Overall Rating

| Category | v1 Score | v2 Score | Notes |
|---|---|---|---|
| **OOP Design** | 7/10 | **7.5/10** | Capacity now enforced, access modifiers fixed; dead fields and raw generics remain |
| **Correctness** | 5/10 | **7/10** | Critical `removeStock()` bug fixed, discount logic fixed; `addProduct` overwrite + negative days remain |
| **Java Conventions** | 6/10 | **6.5/10** | Methods partially renamed; raw generics, enum naming still off |
| **Defensive Programming** | 8/10 | **8/10** | Unchanged — still strong |
| **Design Patterns** | 9/10 | **9/10** | Unchanged — still excellent |
| **Code Completeness** | 5/10 | **5.5/10** | Capacity enforced; Main still empty, dead fields added |

### **Overall: 7 / 10** ⭐ *(up from 6.5)*

You made meaningful progress by fixing the most critical bugs. The biggest remaining wins would be:
1. **Fix `addProduct()` to add stock** instead of overwriting (easy)
2. **Guard `getEstimatedDays()` against negative values** (easy)
3. **Remove dead code** (`orderWarehouses`, `demandHistory`) or implement them (easy)
4. **Either parameterize `Product<String>` everywhere or drop the generic** (medium)
5. **Write a Main class demo** to prove the system works end-to-end (medium)

Fix those 5 and you're at an **8.5+**.
