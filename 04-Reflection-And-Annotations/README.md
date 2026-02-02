# Reflection & Annotations

Custom annotation processing and runtime introspection for a simulation framework.

## Concepts

- **Custom Annotations** - `@Zusicherung`, `@Hauptverantwortlicher`
- **Retention Policies** - Runtime-accessible metadata (`RetentionPolicy.RUNTIME`)
- **Reflection API** - Introspecting classes, methods, and fields at runtime
- **Design by Contract** - Pre-conditions, post-conditions, invariants

## Custom Annotations

### @Zusicherung (Assertion)

Documents contracts for methods and fields:

```java
@Zusicherung(
    author = "Developer",
    typ = Zusicherung.Zusicherungstyp.VORBEDINGUNG,  // PRE-CONDITION
    zusicherung = "Parameter must not be null"
)
public void process(Object param) { ... }
```

**Assertion Types:**
- `VORBEDINGUNG` - Pre-condition (must be true before execution)
- `NACHBEDINGUNG` - Post-condition (guaranteed after execution)
- `INVARIANTE` - Invariant (always true)
- `SCHC` - Strengthened history constraint
- `CCHC` - Covariant history constraint

### @Hauptverantwortlicher (Responsible)

Marks authorship/responsibility:

```java
@Hauptverantwortlicher("TeamMember")
public class Simulation { ... }
```

## Files

| File | Purpose |
|------|---------|
| `Simulation.java` | Main simulation with annotated methods |
| `Zusicherung.java` | Annotation for design-by-contract |
| `Hauptverantwortlicher.java` | Annotation for responsibility |
| `Bee.java` | Abstract bee with plant preferences |
| `Plant.java` | Abstract plant with bloom periods |
| `Set.java` | Custom set implementation |
| `U.java`, `V.java`, `W.java` | Concrete bee species |
| `X.java`, `Y.java`, `Z.java` | Concrete plant species |

## Simulation Model

Bees visit plants based on preferences and availability:

```
Day Loop:
  1. Generate random bees and plants
  2. Each active bee makes visits:
     - Preferred plants first
     - Alternative plants if needed
  3. Track visit statistics
  4. Update bloom/activity periods

End: Output statistics per species
```

## Run

```bash
javac *.java && java Test
```
