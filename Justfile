clean:
  # Clean cache.
  mvn clean

compile:
  # Compile the project.
  mvn compile

package:
  # Create a `.jar` package.
  mvn clean package

run:
  # Run the program.
  mvn javafx:run
