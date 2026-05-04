# Pipe Game

A small JavaFX puzzle game built as a CSE1241 course project in 2021.

The goal is to move the pipe tiles until the ball can travel from the blue start
tile to the red end tile. Each level is a 4x4 board loaded from a plain text
file.

## Project Status

This is an old university assignment, lightly cleaned up so it is easier to
read, run, and keep in version control.

## Authors

- Ozan Durgut
- Ibrahim Tinas

## Repository Layout

- `src/Main.java` - JavaFX application and game logic.
- `src/*.png` - game assets.
- `*.txt` - level definitions. Valid level files in the project root are loaded
  automatically.

## Running

The project needs JavaFX on the compile and runtime module path. On Ubuntu:

```bash
sudo apt install openjdk-21-jdk openjfx
make run
```

If JavaFX is installed somewhere else, pass its `lib` directory:

```bash
make run JAVAFX_LIB=/path/to/javafx-sdk/lib
```

Manual commands:

```bash
javac --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d out src/Main.java
java --module-path /usr/share/openjfx/lib --add-modules javafx.controls -cp out:src Main
```

The old Eclipse workflow also works:

1. Import the project into Eclipse.
2. Make sure JavaFX is available on the build path.
3. Run `Main.java`.

## Notes

- Level files are discovered from the repository root at startup. Any valid
  `.txt` file with 16 block rows can be used as a level, regardless of its file
  name.
- Files with numbers in their names are ordered by that number first, then by
  file name. Other valid `.txt` files are ordered after the numbered ones.
- The code intentionally keeps the original single-file assignment structure,
  with small cleanup around level loading, menu state, and completed-level flow.
