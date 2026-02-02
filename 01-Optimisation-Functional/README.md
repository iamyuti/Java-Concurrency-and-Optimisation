# Optimisation (Functional Programming)

Bees Algorithm implementation using pure functional programming paradigms.

## Concepts

- **Streams** - Pipeline-based data processing
- **Lambdas** - `Supplier<T>`, `Function<T,R>`, `UnaryOperator<T>`
- **Function Composition** - Building complex operations from simple functions
- **Immutability** - Using `record` types for immutable data

## How It Works

The Bees Algorithm is a nature-inspired optimisation technique:

1. **Global Search** - Scout bees explore random positions in the search space
2. **Selection** - Best positions are ranked using a comparator function
3. **Local Search** - Top positions get more bees for detailed exploration
4. **Iteration** - Process repeats, converging toward optimal solutions

## Files

| File | Purpose |
|------|---------|
| `BeesAlgorithm.java` | Core algorithm using functional Streams |
| `BAParameter.java` | Immutable record holding all search parameters |
| `Funktionen.java` | Higher-order functions (generators, comparators) |
| `Stelle.java` | Record representing a position with coordinates and value |
| `Feld.java` | Record representing a search field (flower patch) |
| `Test.java` | Test cases: sine maxima, multi-variable minima, zero-crossings |

## Example Usage

```java
// Find maximum of sin(x) in range [-1800°, 1800°]
BeesAlgorithm.search(new BAParameter(
    x -> Math.sin(Math.toRadians(x[0])),  // function to optimize
    new double[][]{{-1800.0, 1800.0}},     // search range
    Math::max,                              // prefer higher values
    30, 30, 15, 5, 10, 5, 0.1, 10          // algorithm parameters
));
```

## Run

```bash
javac *.java && java Test
```
