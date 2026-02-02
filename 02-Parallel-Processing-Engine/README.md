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
┌────────────────────────────────────────────────────┐
│                     Main Process                   │
│  ┌─────────-─┐  ┌──────-────┐  ┌───────-───┐       │
│  │ Process 1 │  │ Process 2 │  │ Process N │  ...  │
│  └─────┬─────┘  └─────┬─────┘  └─────┬─────┘       │
│        │              │              │             │
│        ▼              ▼              ▼             │
│      ObjectOutputStream (send parameters)          │
│      ObjectInputStream  (receive results)          │
└────────────────────────────────────────────────────┘

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
