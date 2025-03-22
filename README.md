# MiniGit Java Project

## Overview
The **MiniGit Java Project** is a simplified version of a version control system, inspired by Git. It allows users to track file changes, commit snapshots, and restore previous versions using Java.

## Features
- **File Tracking**: Add and track files in the repository.
- **Commit System**: Save snapshots of the project at different stages.
- **Version Retrieval**: Restore previous commits and view commit history.
- **Client Class**: Provides a command-line interface for interacting with the MiniGit system.
- **Testing Class**: Includes unit tests to validate the functionality of file tracking, commits, and retrieval.

## Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/yourusername/minigit.git
   ```
2. Navigate to the project directory:
   ```sh
   cd minigit
   ```

## Usage
Compile and run the client program to use MiniGit:
```sh
javac Client.java
java Client
```

### Basic Commands
- **Add a file**:
  ```sh
  java Client add <filename>
  ```
- **Commit changes**:
  ```sh
  java Client commit -m "Commit message"
  ```
- **View commit history**:
  ```sh
  java Client log
  ```
- **Restore a previous commit**:
  ```sh
  java Client checkout <commit-id>
  ```

## Testing
To run unit tests, execute:
```sh
javac Testing.java
java Testing
```

## Contributing
If you'd like to contribute:
1. Fork the repository.
2. Create a feature branch (`git checkout -b feature-name`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature-name`).
5. Create a pull request.

## Contact
For questions or suggestions, reach out via GitHub Issues or contact me at rsamir9@hotmail.com.

