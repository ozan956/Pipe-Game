JAVAFX_LIB ?= /usr/share/openjfx/lib
JAVAFX_MODULES = javafx.controls
BUILD_DIR = out

.PHONY: build run clean check-javafx

check-javafx:
	@command -v javac >/dev/null || (echo "javac not found. Install a JDK first, for example: sudo apt install openjdk-21-jdk" && exit 1)
	@test -f "$(JAVAFX_LIB)/javafx.controls.jar" || (echo "JavaFX not found at $(JAVAFX_LIB). Install it with: sudo apt install openjfx" && echo "Or run with: make run JAVAFX_LIB=/path/to/javafx-sdk/lib" && exit 1)

build: check-javafx
	javac --module-path "$(JAVAFX_LIB)" --add-modules $(JAVAFX_MODULES) -d $(BUILD_DIR) src/Main.java

run: build
	java --module-path "$(JAVAFX_LIB)" --add-modules $(JAVAFX_MODULES) -cp "$(BUILD_DIR):src" Main

clean:
	rm -rf $(BUILD_DIR)
