# Brief

Welcome to the Pascal's Triangle assignment. We hope you'll have fun!

## Pascal's Triangle explanation

Pascal's Triangle is an infinite list of growing lists of numbers.

An example of the first 5 rows of Pascal's Triangle is as follows:

```plaintext
1
1 1
1 2 1
1 3 3 1
1 4 6 4 1
```

Each row is constructed as follows:

- In row 0, the triangle starts with a single-element list with value 1.
- Each subsequent row is constructed by adding the value above to the left with the value exactly above.
- A value outside the previous row is counted as 0.

## Objective

The objectives for this assignment is:

- Find the bug in the `triangleRow` function. Look at the result of the test as a hint.
- Remove the bug and make sure the tests pass.

### Running the tests

You can run all the tests by entering the following in your terminal:

```sh
$ ./gradlew test
```

or

```sh
$ gradlew.bat test
```

or

```sh
$ mvn test
```

### Good luck!

All the best and happy coding,

The Xebia Team.
