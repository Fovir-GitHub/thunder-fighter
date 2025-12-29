# Thunder Fighter

## Introduction

This project aims to develop a classical game Thunder Fighter with `JavaFX`.

## Installation

To install the game, please follow the guide on [Latest Release Page](https://github.com/Fovir-GitHub/thunder-fighter/releases/latest).

## Project Structure

```text
./
├── .github/
│   └── workflows/
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
│           │   └── Bullet/
│           └── sounds/
├── .editorconfig
├── .envrc
├── .gitattribute
├── .gitignore
├── flake.lock
├── flake.nix
├── icon.ico
├── Justfile
├── LICENSE
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

## Acknowledgement

- [JavaFX](https://github.com/openjdk/jfx): Open source, next generation client application platform for desktop, mobile and embedded systems based on `JavaSE`.
- [xychenger/Thunder-fighter](https://github.com/xychenger/Thunder-fighter): Provide image resources.
- [tomskarning/thunder-strike-fighter](https://github.com/tomskarning/thunder-strike-fighter): Provide the application icon.
