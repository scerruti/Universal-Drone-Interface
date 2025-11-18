# Standard Drone API Proposal (Updated)

## Key Principles
- Abstracts main flight, telemetry, and communication features common to both Tello-SDK and JCoDroneEdu.
- Handles non-equivalent features (e.g., speed control) with clear, unit-based or level-based methods.
- Provides explicit altitude control methods.

---

## Interface Example


```java
public interface StandardDrone {
    // Connection
    void connect() throws Exception;
    void disconnect();

    // Flight (Absolute Movement)
    void moveTo(double x, double y, double z, String unit); // Absolute position, if supported
    Position getCurrentPosition(String unit);               // Returns current position estimate, if supported

    // Flight (Dead Reckoning)
    void moveForward(double distance, String unit, double speed, String speedUnit);
    void moveBackward(double distance, String unit, double speed, String speedUnit);
    void moveLeft(double distance, String unit, double speed, String speedUnit);
    void moveRight(double distance, String unit, double speed, String speedUnit);
    void moveUp(double distance, String unit);
    void moveDown(double distance, String unit);
    void rotateLeft(int degrees);
    void rotateRight(int degrees);
    void hover(double durationSeconds);

    // Speed (explicitly non-equivalent)
    void setSpeed(double speed, String unit); // For drones supporting absolute speed
    void setSpeedLevel(int level);            // For drones supporting speed levels
    double getSpeed(String unit);             // Returns speed in requested unit, if available
    int getSpeedLevel();                      // Returns current speed level, if available

    // Telemetry
    int getBattery();
    double getHeight(String unit);
    double getTemperature();
    String getFlightState();
    String getMovementState();

    // Video/Camera (optional)
    void startVideo();
    void stopVideo();
}

class Position {
    public double x, y, z;
    public String unit;
    // Optionally: confidence, timestamp, etc.
}
```

---

## Notes
- `setSpeed` and `setSpeedLevel` are both present to handle library differences.
- `moveUp` and `moveDown` provide explicit altitude control.
- All distance/speed/height methods accept units for flexibility.
- Platform-specific features can be added via extension interfaces.
- Implementations should document which methods are supported or throw `UnsupportedOperationException` if not available.
