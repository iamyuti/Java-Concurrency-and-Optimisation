# Generics & Iterators

Custom generic set implementations with ordering relationships and iteration support.

## Concepts

- **Generics** - Type-safe collections with bounded type parameters
- **Iterator Pattern** - Custom `Iterator<T>` implementations
- **Ordering** - Graph-based ordering relationships between elements
- **Immutability** - Separate mutable and immutable variants
- **Covariance** - `Ordered<? super E, ?>` for flexible constraints

## Set Variants

| Class | Mutable | Ordered | Description |
|-------|---------|---------|-------------|
| `ISet<E>` | ❌ | ✅ | Immutable set with ordering |
| `MSet<E>` | ✅ | ✅ | Mutable set with ordering |
| `OSet<E>` | ❌ | ✅ | Ordered immutable set |
| `OMSet<E>` | ✅ | ✅ | Ordered mutable set |

## Files

| File | Purpose |
|------|---------|
| `ISet.java` | Immutable generic set with graph-based ordering |
| `MSet.java` | Mutable variant allowing element removal |
| `OSet.java` | Ordered set with sorted iteration |
| `OMSet.java` | Ordered mutable set |
| `OrdSet.java` | Interface defining ordering operations |
| `Ordered.java` | Constraint interface for allowed orderings |
| `Modifiable.java` | Interface for mutable operations |
| `Bee.java` | Test class: bee observation record |
| `HoneyBee.java` | Test class: extends Bee, implements Modifiable |
| `WildBee.java` | Test class: wild bee with location data |
| `Num.java` | Test class: numeric wrapper implementing Modifiable |

## Key Operations

```java
ISet<String> set = new ISet<>(null);

// Establish ordering: "A" comes before "B"
set.setBefore("A", "B");
set.setBefore("B", "C");

// Query elements between two values
Iterator<String> between = set.before("A", "C");
// Returns iterator over elements after A and before C

// Iterate all elements
for (String element : set) {
    System.out.println(element);
}
```

## Internal Structure

The set uses a **linked list** for element storage combined with a **directed graph** for ordering relationships:

- `Node<E>` - Holds element and links to next node + successor edges
- `Edge<E>` - Represents ordering relationship (x → y means x before y)
- Cycle detection via DFS with marking

## Run

```bash
javac *.java && java Test
```
