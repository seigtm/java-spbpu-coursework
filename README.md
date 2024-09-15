# Java Coursework: GUI Application with Multiple Functionalities

## Overview

This repository contains a **Java-based graphical application** developed as part of a **coursework project**. The application provides an intuitive **Java Swing**-based GUI that integrates four key Java programming concepts:

1. **Strategy Pattern**: Implements different behaviors for a hero character.
2. **Custom Annotations**: Demonstrates the use of Java annotations to dynamically invoke methods.
3. **Translator**: A text translation feature using a dictionary file or manual input.
4. **Stream API Exercises**: A collection of tasks that utilize Java's Stream API for data manipulation.

Users can easily switch between these functionalities using the graphical interface, with all results displayed in **non-editable text areas**.

## Features

- **Strategy Pattern**: Dynamically switch between different hero movement strategies (walking, flying, etc.).
- **Custom Annotations**: Invoke annotated methods automatically based on defined parameters.
- **Translator**: Translate text based on a user-defined dictionary or manual input, with support for the longest phrase match.
- **Stream API Exercises**: Perform various operations using the Stream API, such as calculating averages, converting strings, and more.

## Getting Started

### Clonning repository

To clone this repository to your local machine:

```bash
git clone https://github.com/spbstu-java/course-work-seigtm.git
```

### Prerequisites

Before you can run the application, make sure you have the following installed:

- Java Development Kit (JDK).
- (_Optional_) An Integrated Development Environment (IDE) like IntelliJ IDEA or Eclipse.
- (_Optional_) CMake for building the project from the command line.

### Running the Application

- Compile the source code using your preferred IDE or through the command line using CMake:

  ```bash
  cmake -S . -B ./build
  cmake --build ./build
  ```

- Run the application by either executing it through your IDE or via the command line:

  ```bash
  java -jar ./build/app.jar
  ```

- Using the graphical interface
  - Select one of the four main functionalities from the menu (Strategy, Annotations, Translator, Stream).
  - Input the required data as prompted by the interface.
  - Results will be displayed in the non-editable text area to prevent modification.

## License

This project is licensed under the **MIT License** - see the [`LICENSE`](https://github.com/spbstu-java/labs-seigtm/tree/main/LICENSE) file for details.

## Acknowledgments

This project was developed as part of a coursework to demonstrate the use of design patterns, annotations, and Stream API in Java, with a focus on implementing a practical and user-friendly graphical application.
