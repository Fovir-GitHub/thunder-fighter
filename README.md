# Thunder Fighter

## Introduction

This project aims to develop a classical game Thunder Fighter with `JavaFX`.

## Installation

Navigate to [Latest Release Page](https://github.com/Fovir-GitHub/thunder-fighter/releases/latest) and download `thunder-fighter-${version}.jar`.

Then, open the terminal at the same directory, and run the following command to execute it:

```bash
java -jar ./thunder-fighter-${version}.jar
```

## Project Structure

```text
./
├── .github/
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── thunderfighter/
│       │           ├── core/
│       │           │   ├── abstractor/
│       │           │   ├── collision/
│       │           │   ├── entity/
│       │           │   └── manager/
│       │           ├── game/
│       │           │   ├── aircraft/
│       │           │   │   ├── enemy/
│       │           │   │   └── player/
│       │           │   ├── bullet/
│       │           │   ├── bulletfactory/
│       │           │   ├── item/
│       │           │   ├── spawn/
│       │           │   ├── trajectory/
│       │           │   └── Game.java
│       │           ├── ui/
│       │           ├── utils/
│       │           └── Main.java
│       └── resources/
│           ├── config/
│           ├── images/
│           │   ├── Aircraft/
│           │   ├── Background/
│           │   ├── Bullet/
│           │   └── Item/
│           └── sounds/
├── .editorconfig
├── .envrc
├── .gitattribute
├── .gitignore
├── flake.lock
├── flake.nix
├── Justfile
├── pom.xml
└── README.md
```

## Environment

| Tool    | Version  |
| ------- | -------- |
| `java`  | `21.0.8` |
| `maven` | `3.9.11` |

## Development

Ensure `java` and `maven` are installed.

Run commands in [Justfile](https://github.com/Fovir-GitHub/thunder-fighter/blob/main/Justfile) to start development.

To run the project:

```bash
mvn compile
mvn javafx:run

Or run via your IDE by setting Main.java as the main class
```
