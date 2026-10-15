# Task 5 — Code review feedback

This document reviews the zoo OOP exercise across `Animal.ts`, `Mammal.ts`, `Bird.ts`, `Reptile.ts`, `Zoo.ts`, `Zookeeper.ts`, and `test.ts`.

## Overall rating

**7.5 / 10** — Strong OOP structure (inheritance, polymorphism, sensible split across files) and a clear demo script. Points are deducted mainly for a few real correctness bugs (`getAnimalInfo`, `getAnimalsBySpecies`, empty-zoo statistics) and some API inconsistency, all of which are straightforward to fix. With those addressed, this would sit comfortably in the **8.5–9 / 10** range for a coursework-style exercise.

---

## What works well

- **Clear hierarchy.** `Animal` is abstract with `makeSound()` and `getHabitat()` left to subclasses — good use of polymorphism for the demo in `test.ts`.
- **Separation of files.** Grouping mammals, birds, and reptiles in their own modules keeps the model readable.
- **Zoo responsibilities.** The zoo owns animals and zookeepers, computes weekly cost from `Animal.calculateWeeklyCost()`, and exposes queries like `getAnimalsByHabitat` — sensible aggregate behavior.
- **Zookeeper API.** `feedAnimal`, `checkHealth`, and `getWorkload` return human-readable strings, which fits a small simulation or CLI demo.
- **`test.ts`.** Imports, `const`, `console.log`, and `for...of` give a clear end-to-end scenario (add animals, assign keepers, print sounds and stats).

---

## Bugs and correctness issues

### 1. `Animal.getAnimalInfo()` — broken template string (`Animal.ts`)

The template literal ends with `${this.age}}`, which prints an extra `}` after the age (e.g. `... Age: 5}`). It also no longer includes habitat, even though `getHabitat()` exists and would make the line more informative.

**Suggestion:** Close the interpolation once and append habitat, for example:

`... Age: ${this.age} - Habitat: ${this.getHabitat()}`

### 2. `getAnimalsBySpecies` filters by habitat (`Zoo.ts`)

`getAnimalsBySpecies` uses `.filter((x) => x.getHabitat() === species)`. The parameter is named `species`, but the comparison uses habitat. Species lives on `x.species`.

**Fix:** Compare against `x.species` (and consider case-folding or partial match only if the assignment requires it).

### 3. `getZooStatistics()` with an empty zoo (`Zoo.ts`)

If `this.animals.length === 0`, the code still computes `AgeAvg / this.animals.length`, which is `0 / 0` → **NaN** for average age. Other methods return `"No Animals Available"` for the empty case; statistics should guard the same way.

### 4. Inconsistent return types on mutating methods (`Zoo.ts`)

`removeAnimal` and `assignAnimalToKeeper` sometimes return error strings and sometimes return nothing meaningful on success. Callers cannot rely on a single pattern (void vs string vs boolean). For learning code this is acceptable, but it is easy to forget to check return values — consider `boolean`, a small result type, or throwing for invalid operations.

### 5. `assignAnimalToKeeper` — unnecessary loop (`Zoo.ts`)

Checking membership with `forEach` and a `found` flag works, but `this.animals.some((x) => x.animalId === animal.animalId)` (or `find`) is clearer and stops as soon as a match exists.

---

## Design and style suggestions

### Subclass constructors and duplication

Every concrete animal repeats the same six base parameters (`animalId`, `name`, `species`, `age`, `healthStatus`, `dailyFoodCost`). That mirrors classic Java-style inheritance but is repetitive in TypeScript.

**Optional refinements:** a shared `type AnimalBaseProps = { ... }`, or an options object passed to `super`, to reduce copy-paste and constructor argument mistakes.

### `Snake` and `vocabulary` (`Reptile.ts`)

`Snake` declares `vocabulary: string[]` like `Parrot`. Unless the assignment explicitly asked for it, this blurs species behavior: vocabulary fits parrots better than snakes. If you need “extra state” on only some animals, interfaces or a `Bird`-level property can keep the model honest.

### Local variable naming (`Zoo.ts`)

`AgeAvg` and `Habitats` use PascalCase. In TypeScript/JavaScript, **camelCase** for locals (`ageSum`, `habitats`) is the usual convention; PascalCase is reserved for types/classes.

### `Zookeeper.addAnimal`

The same `Animal` reference can be pushed twice; `assignAnimalToKeeper` does not deduplicate. For a toy project this is fine; a `Set` or a check before `push` would match real-world “assigned once” rules.

### `Parrot.speak()` (`Bird.ts`)

After `length > 0`, `this.vocabulary[index]` can still be typed as possibly undefined under strict `noUncheckedIndexedAccess`. The `if (!val) return undefined` branch handles that; you could simplify to `return val` after a non-null assertion only if you are sure the index is always in range.

### `healthStatus` as `string`

Using a string allows any value. A union type (`"Healthy" | "Sick" | ...`) or an enum would document allowed states and catch typos at compile time.

---

## File-by-file summary

| File           | Role                         | Main feedback                                                |
| -------------- | ---------------------------- | ------------------------------------------------------------ |
| `Animal.ts`    | Abstract base                | Fix `getAnimalInfo` template; consider habitat in output.   |
| `Mammal.ts`    | Lion, Elephant, Monkey       | Clear subclasses; habitat choices are consistent.            |
| `Bird.ts`      | Parrot, Eagle                | Parrot vocabulary + `speak` is a nice touch.                 |
| `Reptile.ts`   | Snake, Crocodile             | Revisit `Snake.vocabulary` unless required.                 |
| `Zoo.ts`       | Aggregate + queries          | Fix `getAnimalsBySpecies`; empty-zoo stats; simplify checks. |
| `Zookeeper.ts` | Staff behavior               | Small, focused; specialization is unused beyond display.      |
| `test.ts`      | Demo / manual test           | Good driver; could log `keeper2` or duplicate assignment edge cases. |

---

## Quick checklist (if you revise)

- [ ] Repair `getAnimalInfo` string (and optionally add habitat).
- [ ] Change `getAnimalsBySpecies` to filter by `species` (not habitat).
- [ ] Handle empty `animals` in `getZooStatistics` (avoid `NaN`).
- [ ] Replace `forEach` + `found` with `some` / `find` where appropriate.
- [ ] Align local variable naming with camelCase.

Overall, the structure matches a solid OOP homework: inheritance, polymorphism (`makeSound`), and composition (zoo ↔ animals ↔ zookeepers). Fixing the items above will make behavior match the method names and avoid subtle runtime issues.
