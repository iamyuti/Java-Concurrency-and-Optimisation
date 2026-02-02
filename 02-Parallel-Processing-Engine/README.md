# Parallel Processing Engine

Multi-process, multi-threaded optimisation engine with inter-process communication.

## Concepts

- **Multi-Threading** - Worker threads with shared state
- **Atomic Operations** - `AtomicInteger` for lock-free work distribution
- **Synchronization** - `synchronized` blocks with double-checked locking
- **IPC** - `ObjectInputStream`/`ObjectOutputStream` for process communication
- **Work Stealing** - Block-based task distribution with atomic counters

## Architecture

```
┌───────────────────────────────────────────────┐
│                     Main Process              │
│  ┌─────────-─┐  ┌──────-────┐  ┌───────-───┐  │
│  │ Process 1 │  │ Process 2 │  │ Process N │  │
│  └─────┬─────┘  └─────┬─────┘  └─────┬─────┘  │
│        │              │              │        │
│        ▼              ▼              ▼        │
│      ObjectOutputStream (send parameters)     │
│      ObjectInputStream  (receive results)     │
└───────────────────────────────────────────────┘

┌───────────────────────────────────────────────┐
│                   Worker Process              │
│  ┌────────┐ ┌────────┐ ┌────────┐ ┌────────┐  │
│  │Thread 1│ │Thread 2│ │Thread 3│ │Thread K│  │
│  └───┬────┘ └───┬────┘ └───┬────┘ └───┬────┘  │
│      │          │          │          │       │
│      └──────────┴────┬─────┴──────────┘       │
│                      ▼                        │
│      AtomicInteger (work block counter)       │
│      synchronized  (result aggregation)       │
└───────────────────────────────────────────────┘
```

## Files

| File | Purpose |
|------|---------|
| `ExecuteBA.java` | Main process: spawns workers, collects results |
| `BAProcess.java` | Worker process: multi-threaded search execution |
| `BAParameter.java` | Serializable parameter object for IPC |
| `Stelle.java` | Serializable position record |
| `SerializableFunction.java` | Functional interface for serialization |
| `Test.java` | Test cases with configurable parallelism |

## Key Implementation Details

**Work Distribution:**
```java
AtomicInteger blocksRemaining = new AtomicInteger(totalBlocks);

// Each thread grabs work until none left
while ((block = blocksRemaining.getAndDecrement()) > 0) {
    processBlock(block);
}
```

**Thread-Safe Result Update:**
```java
synchronized (fieldLock) {
    if (comparator.compare(newResult, bestResult) < 0) {
        bestResult = newResult;  // Double-checked locking
    }
}
```

## Run

```bash
javac *.java && java Test
```

## Example Output

**Test 1: Rosenbrock 4D** (4 processes, 1 thread each)
```
Global minimum at (1,1,1,1) with value 0
Found: [1.0016, 1.0025, 1.0049, 1.0096] → 9.16e-5  ✓
Runtime: 0.670s
```

**Test 2: Himmelblau Function** (1 process, 6 threads) – with CPU-intensive computation (CPU-burn)
```
Global minima ~0 at: (3,2), (-2.805,3.131), (-3.779,-3.283), (3.584,-1.848)
Found: [3.562, -1.895] → 0.065  ✓
Runtime: 1.151s
```

**Test 3: Sine Maximum Search** (2 processes, 3 threads each)
```
Maximum should be 1.0 at 90°, 450°, 810°, etc.
Found: [-1710.0°] → 1.0  ✓
Runtime: 0.931s
```

**Total test time: 2.76s**
