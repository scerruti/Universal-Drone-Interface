# Tello-SDK: Telemetry & Sensors

| Method                | Description                | Units         |
|-----------------------|----------------------------|--------------|
| getBattery()          | Battery percentage         | %            |
| getSpeed()            | Current speed              | cm/s         |
| getTime()             | Flight time                | s            |
| getHeight()           | Height above ground        | cm           |
| getTemp()             | Temperature                | °C           |
| getBarometer()        | Barometric pressure        | hPa          |
| getAttitude()         | Attitude (pitch, roll, yaw)| degrees      |
| getAcceleration()     | Acceleration               | m/s²         |
| getTof()              | Time-of-flight distance    | cm           |
| getSN()               | Serial number              | -            |

# JCoDroneEdu: Telemetry & Sensors

| Method                | Description                | Units         |
|-----------------------|----------------------------|--------------|
| getBattery()          | Battery percentage         | %            |
| getHeight([unit])     | Height above ground        | cm/m/in      |
| getFrontRange([unit]) | Front range sensor         | cm/m/in      |
| getFlightState()      | Flight state (string)      | -            |
| getMovementState()    | Movement state (string)    | -            |
| getErrorData()        | Error data (array)         | -            |
| getErrors()           | Error data (object)        | -            |
| getTrim()             | Trim values                | -            |
| getDroneStatus()      | Drone status object        | -            |

# Notes
- JCoDroneEdu allows unit selection for many telemetry methods (e.g., cm, m, in).
- Tello-SDK uses fixed units (mostly cm, °C, hPa).
- Both libraries provide access to battery, height, and some form of error/status data.
