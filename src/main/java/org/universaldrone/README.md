# Universal Drone Interface - Java Implementation

This directory contains the Java implementation of the Universal Drone Interface, providing a standardized API for controlling various educational drones.

## Overview

The Universal Drone Interface provides two main interfaces:
- **UniversalDrone**: English version for controlling drones
- **DronUniversal**: Mexican Spanish version for controlling drones

Both interfaces provide identical functionality with comprehensive documentation in their respective languages.

## Classes and Interfaces

### Position.java
A helper class that represents a drone's position in 3D space with x, y, z coordinates and a unit of measurement.

### UniversalDrone.java (English)
The main interface for drone control with English documentation. Includes methods for:
- Connection management (connect, disconnect)
- Flight control (takeOff, land)
- Movement (moveForward, moveBackward, moveLeft, moveRight, moveUp, moveDown)
- Rotation (rotateLeft, rotateRight)
- Absolute positioning (moveTo, getCurrentPosition)
- Speed control (setSpeed, setSpeedLevel, getSpeed, getSpeedLevel)
- Telemetry (getBattery, getHeight, getTemperature, getFlightState, getMovementState)
- Video streaming (startVideo, stopVideo)

### DronUniversal.java (Mexican Spanish)
The Spanish language mirror of UniversalDrone with identical functionality but comprehensive Mexican Spanish documentation.

## Design Philosophy

The implementation follows a **composition-based design** pattern, avoiding inheritance beyond the base Object class. This approach:
- Reduces complexity
- Increases flexibility
- Makes the code easier to understand for students
- Aligns with modern software design best practices

## Educational Use

All documentation is written at a **ninth grade reading level** to support classroom learning. The Javadoc includes:
- Clear explanations of what each method does
- Real-world analogies to help students understand concepts
- Example code showing how to use the interface
- Notes about limitations and special cases

## Compiling the Code

To compile the Java files:
```bash
javac -d build src/main/java/org/universaldrone/*.java
```

## Generating Javadoc

To generate the HTML documentation:
```bash
javadoc -d docs/javadoc -encoding UTF-8 -charset UTF-8 src/main/java/org/universaldrone/*.java
```

## Example Usage

### English (UniversalDrone)
```java
UniversalDrone drone = new TelloDroneAdapter();

try {
    drone.connect();
    drone.takeOff();
    
    // Fly in a square
    for (int i = 0; i < 4; i++) {
        drone.moveForward(50, "cm", 20, "cm/s");
        drone.rotateRight(90);
    }
    
    System.out.println("Battery: " + drone.getBattery() + "%");
    drone.land();
    drone.disconnect();
} catch (Exception e) {
    System.err.println("Error: " + e.getMessage());
}
```

### Spanish (DronUniversal)
```java
DronUniversal dron = new AdaptadorDronTello();

try {
    dron.conectar();
    dron.despegar();
    
    // Volar en un cuadrado
    for (int i = 0; i < 4; i++) {
        dron.moverAdelante(50, "cm", 20, "cm/s");
        dron.rotarDerecha(90);
    }
    
    System.out.println("Batería: " + dron.obtenerBateria() + "%");
    dron.aterrizar();
    dron.desconectar();
} catch (Exception e) {
    System.err.println("Error: " + e.getMessage());
}
```

## Implementation Notes

- Implementations should throw `UnsupportedOperationException` for methods not supported by specific drones
- Distance units commonly used: "m" (meters), "cm" (centimeters), "in" (inches)
- Speed units commonly used: "m/s" (meters per second), "cm/s" (centimeters per second)
- Not all drones support all features (e.g., absolute positioning, video streaming)

## References

See the following files in the repository root for more information:
- `STANDARD_DRONE_API_PROPOSAL.md` - API design principles
- `DRONE_LIBRARIES_COMPARISON.md` - Comparison of Tello-SDK and JCoDroneEdu
- `DRONE_FLY_SQUARE_EXAMPLES.md` - Example code for different drone types

## License

Specify your license here (e.g., MIT, Apache 2.0, etc.)
