# ZARA Mini Scripting Engine

A custom-built, lightweight interpreter for the **ZARA** programming language written entirely in pure Java. This project was developed as part of an Advanced Object-Oriented Programming (OOP) group assignment to demonstrate the fundamental inner workings of interpreters and language processing at every level.

## What is ZARA?
ZARA (Zero-ceremony Arithmetic and Reasoning Assembler) is a minimalist, dynamically typed toy language. It is designed to be as simple to read as plain English, relying on structural indentation (similar to Python) and minimal syntax to execute logic.

### Language Features Supported
- **Variables & Arithmetic:** `+`, `-`, `*`, `/` and standard order of operations.
- **Relational Comparisons:** `>`, `<`, `==`.
- **Conditional Logic:** `when` statements for precise flow control.
- **Repetition:** `loop` blocks for iterating specific counts.
- **Types:** Native tracking and formatting for Numbers (Doubles) and Strings.
- **Bonus Extensions:** Nested code blocks, Line-specific Error Messaging, and Equality Checking.

---

## The Interpreter Pipeline

Like all programming languages, ZARA's engine is built as a three-step pipeline. Each component takes the output of the previous step, processing the source code until execution. 

1. **Tokenizer (Lexer)**
    - *Files:* `Tokenizer.java`, `Token.java`, `TokenType.java`
    - *Role:* Reads the raw `.zara` text character-by-character and breaks it down into a flat list of identifiable chunks called "Tokens" (e.g. `SET`, `IDENTIFIER`, `NUMBER`). 
    - *Highlight:* Calculates indentation depth natively dynamically emitting `INDENT` and `DEDENT` structure tokens allowing for nested loop blocks.

2. **Parser**
    - *Files:* `Parser.java`
    - *Role:* Reads the flat Token list and arranges it into a complex hierarchy of objects called an **Abstract Syntax Tree (AST)**. The tree automatically guarantees that multiplication operations happen before addition, adhering to standard arithmetic order of operations.

3. **Evaluator (AST & Execution)**
    - *Files:* `Interpreter.java`, `Environment.java`, `Expression.java`, `Instruction.java`, and Node implementations.
    - *Role:* Walks through the generated AST tree and executes the program. It interacts heavily with the `Environment` memory map space to store variable assignments and look-up existing states during logical operations.

---

## How to Compile and Run

The ZARA Scripting Engine doesn't require any external dependencies—just standard Java (JDK 8 or above). 

### 1. Compile the Source Code
Navigate into the root of this project through your terminal or command prompt, and run:
```bash
javac src/zara/*.java
```

### 2. Run a ZARA Script
Pass a `.zara` file using the compiled `Interpreter` class driver:
```bash
java -cp out zara.Interpreter prog1.zara
```

*Inside this repository are 4 sample programs (`prog1.zara` up to `prog4.zara`) to demonstrate everything the language is capable of.*

---

## Language Syntax & Sample Programs

ZARA removes noise and replaces typical boilerplate with 4 explicit keywords: `set`, `show`, `when`, and `loop`.

#### Variables & Assignments (`set`)
```zara
set x = 10
set y = 3
set result = x + y * 2
show result
```

#### Outputing Strings (`show`)
```zara
set name = "Sitare"
show name
show "Hello from ZARA"
```

#### Conditional Logic (`when`)
A `when` condition evaluates to true or false. Notice the trailing colon `:` followed by an indented block.
```zara
set score = 85
when score > 50:
    show "Pass"
```

#### Iteration (`loop`)
A `loop` executes a block code a fixed number of times iteratively.
```zara
set i = 1
loop 4:
    show i
    set i = i + 1
```


