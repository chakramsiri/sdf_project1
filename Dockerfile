# Use a base image with Java JDK already installed
FROM eclipse-temurin:17

# Set working directory inside container
WORKDIR /app

# Copy Java source files into the container
COPY arbitraryarithmetic/ ./arbitraryarithmetic/
COPY MyInfArith.java .

# Compile the Java code
RUN javac arbitraryarithmetic/AInteger.java arbitraryarithmetic/AFloat.java MyInfArith.java

# Run the main class directly (uses current dir by default)
CMD ["java", "MyInfArith"]