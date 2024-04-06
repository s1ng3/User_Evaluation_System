# 📝 User Evaluation System

This project is a Java application that processes applicant data, adjusts scores based on certain conditions, and generates a JSON output with specific statistics.

## 🌟 Features

- 📖 Reads applicant data from a CSV file
- ✅ Validates and processes the data
- 🔄 Adjusts applicant scores based on delivery date and time
- 📊 Generates a JSON output with the number of unique applicants, average score, and top applicants

## 📚 Classes

- `Main`: The main class that reads the CSV file and writes the JSON output
- `Applicant`: Represents an applicant with fields for name, email, delivery date and time, and score
- `ApplicantsProcessor`: Processes the applicants, adjusts scores, and generates the JSON output

## 🚀 Usage

1. Ensure you have a Java Runtime Environment (JRE) installed on your machine.
2. Compile the Java files using a Java compiler (e.g., `javac`).
3. Run the `Main` class using the Java interpreter (e.g., `java`).
4. The JSON output will be written to a file named `output.json`.

## 📦 Dependencies

- Apache Commons CSV for parsing CSV files
- Jackson for generating JSON output

## 👤 Author

Tudor-Cristian Sîngerean
