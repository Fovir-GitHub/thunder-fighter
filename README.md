# Thunder Fighter

## Introduction

This project aims to develop a classical game Thunder Fighter with `JavaFX`.

## Project Structure

```text
./
├── src/
│   └── main/
│       ├── java/
│       │   └── org/
│       │       └── thunderfighter/
│       │           ├── core/
│       │           │   ├── abstractor/
│       │           │   ├── collision/
│       │           │   └── entity/
│       │           ├── game/
│       │           │   └── aircraft/
│       │           │       ├── enemy/
│       │           │       └── player/
│       │           ├── utils/
│       │           └── Main.java
│       └── resources/
│           ├── config/
│           ├── images/
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

## Configuration Files

All runtime configuration files are stored in:

```text
src/main/resources/config/
```

### YAML Files Overview

| File          | Description                                                                        |
| ------------- | ---------------------------------------------------------------------------------- |
| entity.yaml   | Entity architecture declaration: interfaces, abstract classes, subclasses          |
| game.yaml     | Global game rules, modes, player defaults, difficulty, audio settings              |
| aircraft.yaml | Player and Enemy attributes: HP, speed, score, attack patterns                     |
| bullet.yaml   | Bullet types and trajectories: player bullets, enemy bullets, boss bullet patterns |
| prop.yaml     | Props configuration: types, effects, duration, drop rates                          |
| ui.yaml       | UI texts, menu buttons, about info                                                 |

### Get Configuration Options

Refer to [utils/Config.java](https://github.com/Fovir-GitHub/thunder-fighter/blob/develop/src/main/java/org/thunderfighter/utils/Config.java), to get a configuration option, for example the speed of bullets, the following code can be used:

```java
int speed =
    (int)
        ((Map)
          ((Map) Config.getInstance().getConfigMapByKey("bullet").get("bullets"))
              .get("player"))
      .get("speed");
System.out.println(speed);
```

> **Use this in development.**

### Example: Loading YAML in Java

Using SnakeYAML:

```java
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

InputStream is = getClass().getClassLoader()
                        .getResourceAsStream("config/game.yaml");

Yaml yaml = new Yaml();
Map<String, Object> data = yaml.load(is);

// Example: Get player's max HP
int maxHp = (int) ((Map)data.get("player")).get("maxHp");
```

Add Maven Dependency:

```xml
<dependency>
    <groupId>org.yaml</groupId>
    <artifactId>snakeyaml</artifactId>
    <version>2.2</version>
</dependency>
```

#### Notes

- All game parameters are externalized in YAML.
- Modifying YAML files can adjust game behavior without changing Java code.
- Recommended workflow for new team members:
  1. Place new configuration in `src/main/resources/config/`
  2. Update `README.md` to describe the new file and its purpose
  3. Ensure Java code reads and parses it correctly via `ConfigLoader`

## Development

Ensure `java` and `maven` are installed.

Run commands in [Justfile](https://github.com/Fovir-GitHub/thunder-fighter/blob/main/Justfile) to start development.

To run the project:

```bash
mvn compile
mvn javafx:run

Or run via your IDE by setting Main.java as the main class
```
