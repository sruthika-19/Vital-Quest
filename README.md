# Vital-Quest# VITAL QUEST – Mental Wellness & Scenario Analysis System

**Tagline:** Understand the factors. Analyze the situation. Reflect on wellbeing.

## Overview
VITAL QUEST is a Java Swing-based educational mini-project developed for the B.E. Computer Science and Engineering Java Programming Laboratory. It is an interactive scenario-analysis application where users examine fictional student wellbeing situations, identify relevant lifestyle factors, formulate assessments, and receive rule-based educational feedback. 

*Disclaimer: This is an educational awareness tool and does not provide psychological or medical diagnosis.*

## Key Features
* **Scenario Library:** Navigate multiple fictional cases (e.g., "The Overload Loop", "Always Connected").
* **Interactive Deduction:** Select relevant factors and choose calibrated confidence levels.
* **Rule-Based Engine:** Evaluates reasoning and assessment accuracy independently of selected evidence.
* **Scoreboard:** Multithreaded background saving tracks investigator scores using local CSV file storage.
* **Custom UI:** Modern, dark-themed medical dashboard built entirely with custom Java Swing `Graphics2D` rendering.

## Java Concepts Demonstrated
This project implements exactly 16 core Java concepts, including:
* **Object-Oriented Programming:** Classes, Objects, Inheritance, Polymorphism, and Abstraction (`User` -> `Student`).
* **Interfaces:** The `Analyzeable` contract for scenario behavior.
* **Collections & Generics:** Extensive use of `ArrayList`, `HashSet`, `HashMap`, and a custom generic `Repository<T>`.
* **Multithreading:** `SwingWorker` manages background file writing without freezing the UI.
* **Exception & String Handling:** Robust input validation and string normalization for reasoning checks.

## How to Compile and Run
Ensure you have the Java Development Kit (JDK) installed. Open your terminal in the root project folder.

**1. Compile the project:**
```bash
javac -d bin src/main/*.java src/model/*.java src/engine/*.java src/gui/*.java src/storage/*.java src/interfaces/*.java src/util/*.java