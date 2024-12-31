# Makefile for Java project compilation

# Java compiler
JC = javac

# Java runtime
JVM = java

# Source files
SOURCES = Frontend.java Backend.java FrontendInterface.java \
          BackendInterface.java GraphADT.java BaseGraph.java \
          MapADT.java ClosestConnectionInterface.java \
          PlaceholderMap.java DijkstraGraph.java

# Class files
CLASSES = $(SOURCES:.java=.class)

# Default target
default: compile

# Compile all Java source files
compile: $(SOURCES)
	$(JC) $(SOURCES)

# Run the program (Frontend is main class)
run: compile
	$(JVM) Frontend

# Clean up compiled files
clean:
	rm -f *.class


