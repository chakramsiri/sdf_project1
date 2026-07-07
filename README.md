# Arbitrary Precision Arithmetic Library in Java

A Java library for performing arbitrary-precision arithmetic on integers and floating-point numbers without relying on Java's built-in `BigInteger` or `BigDecimal` arithmetic operations.

This project was developed as part of the **Software Development Fundamentals (CS1023)** course and implements arithmetic using string manipulation and classic arithmetic algorithms.

---

## Features

### Integer Operations
- Addition
- Subtraction
- Multiplication
- Division
- Supports arbitrarily large integers
- Handles positive and negative numbers
- Detects division by zero

### Floating-Point Operations
- Addition
- Subtraction
- Multiplication
- Division
- Arbitrary precision using string-based arithmetic
- Preserves decimal precision
- Truncates division results to 30 decimal places

---

## Project Structure

```
SDF_Project1/
│
├── src/
│   └── main/
│       └── java/
│           ├── arbitraryarithmetic/
│           │   ├── AInteger.java
│           │   └── AFloat.java
│           └── MyInfArith.java
│
├── report/
├── pom.xml
├── run_mainproject.py
├── README.md
└── .gitignore
```

---

## Technologies Used

- Java
- Maven
- Object-Oriented Programming
- String Manipulation
- Git

---

## Design

The project contains two independent classes:

### AInteger

Implements arbitrary-precision integer arithmetic using strings.

Supported operations:

- add()
- subtract()
- multiply()
- divide()

---

### AFloat

Implements arbitrary-precision floating-point arithmetic by operating directly on string representations of decimal numbers.

Supported operations:

- add()
- subtract()
- multiply()
- divide()

---

### MyInfArith

Command-line interface for executing arithmetic operations.

Command format:

```bash
java MyInfArith <int/float> <add/sub/mul/div> <operand1> <operand2>
```

---

## Build

Compile using Maven

```bash
mvn clean compile
```

Or compile manually

```bash
javac -d . src/main/java/arbitraryarithmetic/*.java src/main/java/MyInfArith.java
```

---

## Usage

### Integer Addition

```bash
java MyInfArith int add 23650078224912949497310933240250 42939783262467113798386384401498
```

Output

```
66589861487380063295697317641748
```

---

### Integer Division

```bash
java MyInfArith int div 25 123
```

Output

```
0
```

---

### Floating Point Multiplication

```bash
java MyInfArith float mul 6400251.9377695 2326541.6827934
```

Output

```
14890452913599.9717457253213
```

---

### Floating Point Division

```bash
java MyInfArith float div 5.5 2
```

Output

```
2.75
```

---

## Error Handling

The library handles:

- Division by zero
- Invalid operation names
- Invalid numeric types
- Incorrect command-line usage

Example:

```
Division by zero
```

---

## Implementation Details

- Arithmetic is implemented manually using strings.
- Integer multiplication uses the grade-school multiplication algorithm.
- Division uses long division.
- Floating-point arithmetic is performed by aligning decimal places and operating on integer representations.
- Supports numbers beyond Java primitive data type limits.

---

## Time Complexity

| Operation | Complexity |
|----------|------------|
| Addition | O(n) |
| Subtraction | O(n) |
| Multiplication | O(n²) |
| Division | O(n²) |

---