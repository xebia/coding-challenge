# Pascal's Triangle — example solutions

Five ways to implement `List<Integer> triangleRow(int n)` in `src/main/java/PascalsTriangle.java`.

All five were verified to agree on rows 0–6, compiled at `--release 11` to match `pom.xml`:

```plaintext
0: [1]
1: [1, 1]
2: [1, 2, 1]
3: [1, 3, 3, 1]
4: [1, 4, 6, 4, 1]
5: [1, 5, 10, 10, 5, 1]
6: [1, 6, 15, 20, 15, 6, 1]
```

---

## 1. Recursive on the previous row

The most direct reading of the spec — row *n* is built from row *n−1*.

```java
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        if (n == 0) return singletonList(1);

        List<Integer> previous = triangleRow(n - 1);
        List<Integer> row = new ArrayList<>(n + 1);
        row.add(1);
        for (int i = 1; i < n; i++) {
            row.add(previous.get(i - 1) + previous.get(i));
        }
        row.add(1);
        return row;
    }
}
```

The edges are added explicitly rather than treating out-of-range as 0. If you prefer the
spec's "a value outside the previous row is counted as 0" phrasing literally, replace the
loop with:

```java
for (int i = 0; i <= n; i++) {
    int left  = i == 0 ? 0 : previous.get(i - 1);
    int above = i == previous.size() ? 0 : previous.get(i);
    row.add(left + above);
}
```

**Trade-offs:** O(n²) time, O(n²) allocation (a new list per row), recursion depth n.

---

## 2. Single list, mutated in place

One `ArrayList` for all rows. Iterating **right to left** is the trick — going left to
right would overwrite `row[i-1]` before you need it.

```java
import java.util.ArrayList;
import java.util.List;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        List<Integer> row = new ArrayList<>(n + 1);
        row.add(1);
        for (int r = 1; r <= n; r++) {
            row.add(1);                                   // grow to length r+1
            for (int i = r - 1; i > 0; i--) {              // skip index 0, it stays 1
                row.set(i, row.get(i - 1) + row.get(i));
            }
        }
        return row;
    }
}
```

**Trade-offs:** O(n²) time, O(n) space, no recursion depth to worry about. This is the one
I'd ship.

---

## 3. Closed form — binomial coefficients

Row *n* is C(n,0)…C(n,n), computed multiplicatively so no factorial overflows early.

```java
import java.util.ArrayList;
import java.util.List;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        List<Integer> row = new ArrayList<>(n + 1);
        long c = 1;
        for (int k = 0; k <= n; k++) {
            row.add((int) c);
            c = c * (n - k) / (k + 1);   // exact: c is always divisible here
        }
        return row;
    }
}
```

**Trade-offs:** O(n) time and gives you any single row without computing the ones before
it. The division is exact at every step, so no rounding error — but the cast to `int`
overflows around n = 34 regardless of approach, since C(34,17) > 2³¹.

---

## 4. Naive per-cell recursion

Closest to the maths, and also the one to avoid — it recomputes the same cells
exponentially often (~2ⁿ calls).

```java
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        return IntStream.rangeClosed(0, n).map(k -> cell(n, k)).boxed().collect(toList());
    }

    private int cell(int row, int col) {
        if (col == 0 || col == row) return 1;
        return cell(row - 1, col - 1) + cell(row - 1, col);
    }
}
```

**Trade-offs:** exponential time. Add a `Map<Long, Integer>` memo keyed on
`(long) row << 32 | col` and it collapses to O(n²) — a reasonable talking point if this is
an interview.

---

## 5. Streams

```java
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

class PascalsTriangle {

    List<Integer> triangleRow(int n) {
        return Stream.iterate(singletonList(1), PascalsTriangle::next)
                .limit(n + 1)
                .reduce((a, b) -> b)
                .orElseThrow();
    }

    private static List<Integer> next(List<Integer> prev) {
        return IntStream.rangeClosed(0, prev.size())
                .map(i -> (i == 0 ? 0 : prev.get(i - 1))
                        + (i == prev.size() ? 0 : prev.get(i)))
                .boxed()
                .collect(toList());
    }
}
```

**Trade-offs:** `reduce((a, b) -> b)` to take the last element is clunky; on Java 11 there
is no cleaner "last" operator. The `next` function itself is nice and reusable though — if
the API were `Stream<List<Integer>> triangle()` instead of a single row, this would be the
natural shape.

---

## Negative input

None of the five handle `n < 0`, and the tests don't cover it. Measured behaviour:

| Implementation      | `n = -1`                 | `n = -5`                   |
| ------------------- | ------------------------ | -------------------------- |
| 1. recursive        | `StackOverflowError`     | `StackOverflowError`       |
| 2. single list      | `[1]`                    | `IllegalArgumentException` |
| 3. binomial         | `[]`                     | `IllegalArgumentException` |
| 4. per-cell         | `[]`                     | `[]`                       |
| 5. streams          | `NoSuchElementException` | `IllegalArgumentException` |

Note how inconsistent this is — the `IllegalArgumentException`s in #2, #3 and #5 are
incidental, thrown by `new ArrayList<>(n + 1)` and `Stream.limit(n + 1)` rejecting a
negative argument, which only happens once `n <= -2`. At `n = -1` those same
implementations quietly return a wrong-but-plausible `[1]` or `[]`.

An explicit guard is cheap, makes all five behave alike, and is the kind of thing a
reviewer looks for:

```java
if (n < 0) throw new IllegalArgumentException("row must be >= 0, was " + n);
```
