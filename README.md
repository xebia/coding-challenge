# Brief

Welcome to the Pascal's Triangle assignment. We hope you'll have fun!

## How to create Pascal's Triangle

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

- Create a function `triangleRow` that takes a number and returns a list of numbers containing the values of Pascal’s triangle **for that row**.
- Run the test suite and make sure that all tests pass.

### Running the tests

You can run all the tests by entering the following in your terminal:

```sh
$ gradle test
```

### Evaluation Criteria

- Java best practices
- Show us your work through your commit history
- Completeness: did you complete the features? Are all the tests running?
- Correctness: does the functionality act in sensible, thought-out ways?
- Maintainability: is it written in a clean, maintainable way?

### CodeSubmit

Please organize, design, and document your code as if it were going into production - then push your changes
to the master branch. After you have pushed your code, you may submit the assignment on the assignment page.

All the best and happy coding,

The Xebia Team.
