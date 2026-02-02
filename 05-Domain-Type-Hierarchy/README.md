# Domain Type Hierarchy

Polymorphic type hierarchy modeling pollinators with comprehensive test coverage.

## Concepts

- **Inheritance** - Multi-level class hierarchy with abstract classes
- **Polymorphism** - Interface-based design (`Pollinator`)
- **Liskov Substitution** - Behavioral subtyping ensures substituability
- **Testing** - Dedicated test classes for each type

## Type Hierarchy

```
Pollinator (interface)
│
├── Bee (abstract)
│   │
│   ├── Honeybee
│   │
│   └── WildBee (abstract)
│       │
│       ├── Bumblebee
│       │
│       ├── SolitaryBee (abstract)
│       │   ├── OsmiaCornuta
│       │   └── AndrenaBucephala
│       │
│       └── CommunalBee (abstract)
│           └── LasioglossumCalceatum
│
├── Wasp
│
└── FlowerFly
```

## Design Principles

**Behavioral Subtyping:**
- Subclasses can substitute their parent without breaking behavior
- Each level adds specific behavior while honoring parent contracts

**Abstract vs Concrete:**
- `Bee`, `WildBee`, `SolitaryBee`, `CommunalBee` = abstract (templates)
- `Honeybee`, `Bumblebee`, `OsmiaCornuta`, etc. = concrete (instantiable)

## Files

| File | Purpose |
|------|---------|
| `Pollinator.java` | Root interface for all pollinators |
| `Bee.java` | Abstract base for all bees |
| `WildBee.java` | Abstract class for wild bee species |
| `SocialBee.java` | Abstract class for social bees |
| `SolitaryBee.java` | Abstract class for solitary bees |
| `CommunalBee.java` | Abstract class for communal bees |
| `Honeybee.java` | Concrete honey bee |
| `Bumblebee.java` | Concrete bumble bee |
| `OsmiaCornuta.java` | Concrete mason bee (SolitaryBee) |
| `AndrenaBucephala.java` | Concrete mining bee (SolitaryBee) |
| `LasioglossumCalceatum.java` | Concrete sweat bee (CommunalBee) |
| `Wasp.java` | Concrete wasp (implements Pollinator) |
| `FlowerFly.java` | Concrete flower fly (implements Pollinator) |
| `Observation.java` | Record for pollinator observations |
| `ObservationList.java` | Data structure for observations |
| `*Test.java` | Test classes for each type |

## Test Coverage

Each type has a dedicated test class verifying:
- Constructor behavior
- Method contracts
- Inheritance relationships
- Edge cases

```java
// Example: Testing Liskov Substitution
Pollinator p = new Honeybee();      // Works
Bee b = new Bumblebee();            // Works
WildBee w = new OsmiaCornuta();     // Works
```

## Run

```bash
javac *.java && java Test
```
