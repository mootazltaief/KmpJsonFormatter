# JSON Formatter

A simple desktop application built with Kotlin and Compose Multiplatform for formatting JSON content.

## Features

- Format JSON with proper indentation
- Validate JSON syntax
- Save formatted JSON to a file
- Clean and intuitive user interface

## Technology Stack

- **Kotlin Multiplatform**: For cross-platform code sharing
- **Compose Multiplatform**: For the desktop UI
- **Kotlinx Serialization**: For JSON parsing and formatting

## How to Use

1. Enter your JSON in the input field
2. Click "Format JSON" to validate and format the content
3. If valid, the formatted JSON will appear in the output area
4. Use "Save Formatted JSON" to save the result to a file

## Getting Started

This project uses Gradle for build automation.

```bash
# Clone the repository
git clone https://github.com/mootazltaief/json-formatter.git

# Navigate to the project directory
cd json-formatter

# Run the application
./gradlew run