# Code Review: Task 3 — Rental Agency System

**Reviewed:** Vehicle, Customer, Rental, RentalAgency, test  
**Overall rating: 8.2 / 10**

---

## Summary

The rental system is in good shape. The earlier logic bugs and typos are fixed: rentals are marked active, `getAvailableVehicles()` returns readable strings, `getActiveRentals()` returns early when empty, and naming/equality are consistent. What remains are optional improvements (validation, consistency, and small style choices).

---

## 1. Architecture & OOP Design

| Aspect | Rating | Notes |
|--------|--------|--------|
| **Separation of concerns** | Good | Vehicle, Customer, Rental, and Agency each have clear roles. |
| **Encapsulation** | Good | IDs and sensitive data (e.g. license) are private; getters used where needed. |
| **Composition** | Good | Rental composes Customer + Vehicle; Agency holds vehicles, customers, rentals. |
| **Cohesion** | Good | Methods on each class match their responsibility. |

---

## 2. Vehicle.ts

### Strengths

- `vehicleId` private, exposed via `getVehicleId()` (typo fixed).
- `rent()` / `returnVehicle()` correctly toggle `isAvailable`.
- `calculateRentalCost(days)` uses `dailyRate * days` and guards against negative `days` with `if (days < 0) return 0`.

### Optional improvements

- **`getVehicleInfo()`** — Could include **year** (e.g. for fleet display) since it’s in the constructor.
- **`returnVehicle()`** — Logs to console inside a state-changing method. For easier testing and reuse, you could only update state and let the caller (e.g. agency) handle logging.

No bugs found.

---

## 3. Rental.ts

### Strengths

- Constructor validates `startDate <= end Date` and throws otherwise.
- `getRentalDuration()` correctly computes days from date difference.
- `getTotalCost()` delegates to `vehicle.calculateRentalCost(getRentalDuration())`.
- `completeRental()` sets `isActive = false`, calls `vehicle.returnVehicle()`, and uses correct spelling ("completed").
- Uses `getVehicleId()` for rental info.

No bugs found. (Rentals are now set active in `RentalAgency.createRental`.)

---

## 4. Customer.ts

### Strengths

- Clean constructor and encapsulation (`customerId`, `driversLicenseNumber` private).
- `getCustomerInfo()` does not expose the license number — good for privacy.

No bugs found.

---

## 5. RentalAgency.ts

### Strengths

- **getAvailableVehicles()** — Correctly returns `available.map((v) => v.getVehicleInfo()).join("\n")` (readable string).
- **createRental** — Sets `rental.isActive = true` after creating the rental, then pushes and calls `vehicle.rent()`.
- **getActiveRentals()** — Returns early when `active.length === 0` after logging "No Active Rentals", so the header is not printed when empty.
- **getCustomerRentals** and **displayFleet** use `===` for comparisons.
- **displayFleet** uses `getVehicleId()`.

### Optional robustness

- **createRental** — Does not check if the vehicle is **available** before calling `vehicle.rent()`, so the same vehicle could be rented twice. Consider `if (!vehicle.isAvailable) throw new Error("Vehicle not available");`.
- **createRental** — Does not check if the customer is in `this.customers`. You could enforce that the customer is registered if that’s a business rule.

No bugs found.

---

## 6. test.ts

### Strengths

- Covers agency creation, fleet, customers, creating rentals, completing a rental, and customer rentals in a realistic order.

### Optional / cosmetic

- **Make vs model** — Constructor is `(vehicleId, model, make, year, dailyRate)`. You call `new Vehicle("V001", "Toyota", "Camry", ...)`, so in code **model = "Toyota"** and **make = "Camry"**. Convention is usually Make = Toyota, Model = Camry. So either swap constructor params to `(vehicleId, make, model, year, dailyRate)` and keep the test, or swap test args to `("V001", "Camry", "Toyota", ...)` so display shows Manufacturer: Toyota, Model: Camry. Purely a naming/convention choice.
- **getCustomerRentals** returns `void` but the test assigns it to `customerRentals`. That variable is always `undefined`. You can remove the variable or have the method return something if you need it later.

No bugs found.

---

## 7. What Was Fixed (since last review)

| Item | Status |
|------|--------|
| Rental never marked active | Fixed — `createRental` sets `rental.isActive = true`. |
| getAvailableVehicles() returning `[object Object]` | Fixed — now maps to `getVehicleInfo()` then joins. |
| getActiveRentals() printing header when empty | Fixed — early return when no active rentals. |
| getVeichleId typo | Fixed — `getVehicleId()` everywhere. |
| "compeleted" typo | Fixed — "completed" in Rental. |
| Loose equality (==) | Fixed — `===` in getCustomerRentals and displayFleet. |
| Negative days in calculateRentalCost | Fixed — `if (days < 0) return 0`. |

---

## 8. Ratings Breakdown

| Category | Score (1–10) | Comment |
|----------|--------------|--------|
| **Correctness** | 8.5 | Core logic correct; optional validation (double rent, registered customer) could be added. |
| **OOP design** | 8.5 | Good classes, encapsulation, composition. |
| **Naming & style** | 8 | Consistent; make/model order in Vehicle is a convention choice. |
| **Maintainability** | 8 | Clear structure; optional guards would make behavior even clearer. |
| **Completeness** | 8 | Core flow and edge cases (negative days, empty lists) handled. |

**Overall: 8.2 / 10**

---

## 9. Optional Improvement Checklist

1. **Vehicle:** Add `year` to `getVehicleInfo()` if you want it in fleet/availability output.
2. **RentalAgency createRental:** Check `vehicle.isAvailable` before renting; optionally check customer is in `this.customers`.
3. **test.ts:** Align Vehicle constructor order with make/model convention, or swap test arguments so display matches (Manufacturer: Toyota, Model: Camry).
4. **test.ts:** Remove unused `customerRentals` variable or have `getCustomerRentals` return data if you need it.

---

## 10. Conclusion

The rental system is correct and well-structured. The previous bugs (active flag, getAvailableVehicles, getActiveRentals, typos, equality, negative days) are all addressed. Remaining items are optional improvements for robustness and convention. No blocking issues.
