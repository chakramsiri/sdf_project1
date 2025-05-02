<<<<<<< HEAD
# Arbitrary Precision Arithmetic Library

## Project: SDF Project 1 – Infinite Precision Arithmetic

This project implements a custom arithmetic library for performing operations on arbitrary-precision integers and floating-point numbers.



### Features

- **AInteger**: Class for handling integers of arbitrary size.
  - Supports: addition, subtraction, multiplication, division.
- **AFloat**: Class for handling floating-point numbers with arbitrary precision.
  - Supports: addition, subtraction, multiplication, division.
- Command-line interface via `MyInfArith` to perform operations.


### How to Run
first open the current project directory and run the commands
- Java
- Python
- Maven

### Usage

```bash
java MyInfArith <type> <operation> <operand1> <operand2>
```
```bash
python3 run_mainproject.py <type> <operation> <operand1> <operand2>
```
```bash
mvn compile exec:java -Dexec.args="<type> <operation> <operand1> <operand2>"
```