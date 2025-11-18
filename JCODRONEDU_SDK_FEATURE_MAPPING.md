# JCoDroneEdu Feature Mapping to Standard API

This document maps JCoDroneEdu features to the proposed Standard Drone API as defined in `STANDARD_DRONE_API_PROPOSAL.md`.

## Mapping Overview

The Standard Drone API is designed to abstract common functionality across multiple drone SDKs. This mapping identifies which JCoDroneEdu features correspond to standard API methods, notes any non-equivalences, and documents gaps or unsupported features.

---

## 1. Connection Methods

### StandardDrone API
- `void connect() throws Exception`
- `void disconnect()`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `connect()` | `connect()` | Establishes connection to drone | ✅ Supported |
| `disconnect()` | `disconnect()` | Closes connection | ✅ Supported |

**Additional JCoDroneEdu Methods (No Standard API Equivalent):**
- `pair()` - Pairs with the drone before connecting
- `setLinkMode(LinkController.LinkMode)` - Sets communication link mode
- `setControllerMode(LinkController.ControllerMode)` - Sets controller mode

**Implementation Notes:**
- JCoDroneEdu requires `pair()` to be called before `connect()` in some cases
- Consider wrapping `pair()` + `connect()` in the standard API implementation
- Link mode and controller mode are drone-specific configurations

---

## 2. Basic Flight Commands

### StandardDrone API
- `void takeoff()` (implicit from DRONE_LIBRARIES_COMPARISON.md)
- `void land()` (implicit from DRONE_LIBRARIES_COMPARISON.md)
- `void hover(double durationSeconds)`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `takeoff()` | `takeoff()` | Initiates takeoff sequence | ✅ Supported |
| `land()` | `land()` | Initiates landing sequence | ✅ Supported |
| `hover()` | `hover()` | Hovers in place (no duration parameter) | ⚠️ Partial |
| `emergency()` | `emergencyStop()` | Emergency stop (not in standard API) | N/A |

**Additional JCoDroneEdu Methods (No Standard API Equivalent):**
- `emergencyStop()` - Stops all motors immediately (emergency landing)
- `setHeadlessMode(boolean)` - Enables/disables headless flight mode

**Implementation Notes:**
- `hover(durationSeconds)` can be implemented by calling `hover()` followed by a timed delay
- JCoDroneEdu's `hover()` has no duration parameter - it hovers indefinitely until next command
- Consider adding `emergency()` to standard API for safety
- Headless mode is a JCoDroneEdu-specific feature

---

## 3. Dead Reckoning Movement Commands

### StandardDrone API
- `void moveForward(double distance, String unit, double speed, String speedUnit)`
- `void moveBackward(double distance, String unit, double speed, String speedUnit)`
- `void moveLeft(double distance, String unit, double speed, String speedUnit)`
- `void moveRight(double distance, String unit, double speed, String speedUnit)`
- `void moveUp(double distance, String unit)`
- `void moveDown(double distance, String unit)`
- `void rotateLeft(int degrees)`
- `void rotateRight(int degrees)`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `moveForward()` | `moveForward(double distance, String unit, double velocity)` | Distance with configurable unit, velocity parameter | ✅ Supported |
| `moveBackward()` | `moveBackward(double distance, String unit, double velocity)` | Distance with configurable unit, velocity parameter | ✅ Supported |
| `moveLeft()` | `moveLeft(double distance, String unit, double velocity)` | Distance with configurable unit, velocity parameter | ✅ Supported |
| `moveRight()` | `moveRight(double distance, String unit, double velocity)` | Distance with configurable unit, velocity parameter | ✅ Supported |
| `moveUp()` | `moveDistance(0, 0, distance, velocity)` | Via 3D position movement | ⚠️ Partial |
| `moveDown()` | `moveDistance(0, 0, -distance, velocity)` | Via 3D position movement | ⚠️ Partial |
| `rotateLeft()` | `turnLeft(int degrees, double velocity)` | Rotation with velocity parameter | ✅ Supported |
| `rotateRight()` | `turnRight(int degrees, double velocity)` | Rotation with velocity parameter | ✅ Supported |

**Additional JCoDroneEdu Methods (No Standard API Equivalent):**
- `turn(int direction, Double velocity)` - Generic turn with direction
- `turnDegree(int direction, double degree, double velocity)` - Turn with precise degree control
- `moveDistance(double positionX, double positionY, double positionZ, double velocity)` - 3D movement
- `setPitch(int)`, `setRoll(int)`, `setYaw(int)`, `setThrottle(int)` - Direct control setters
- `move()` - Executes movement with previously set pitch/roll/yaw/throttle values

**Implementation Notes:**
- JCoDroneEdu supports configurable units (meters, centimeters, etc.) natively
- Speed/velocity is specified per-command (unlike Tello which uses global speed)
- Vertical movement (up/down) can be achieved via `moveDistance()` with Z-axis
- Direct control via pitch/roll/yaw/throttle provides fine-grained control
- `move()` method executes based on previously set control values

**Advantages over Tello-SDK:**
- Per-command speed/velocity specification
- Native unit support (no conversion needed)
- 3D movement with `moveDistance()` allows diagonal/complex movements

---

## 4. Absolute Position Movement

### StandardDrone API
- `void moveTo(double x, double y, double z, String unit)`
- `Position getCurrentPosition(String unit)`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `moveTo()` | `moveDistance(double positionX, double positionY, double positionZ, double velocity)` | Relative position movement | ⚠️ Partial |
| `getCurrentPosition()` | No direct equivalent | No position tracking API | ❌ Not Supported |

**Additional JCoDroneEdu Methods:**
- `moveDistance(double positionX, double positionY, double positionZ, double velocity)` - Moves relative to current position in 3D space

**Implementation Notes:**
- JCoDroneEdu's `moveDistance()` performs relative movement, not absolute positioning
- No absolute position tracking or coordinate system available
- For absolute positioning, would need to maintain position state externally
- `moveTo()` could be implemented by calculating delta from current tracked position
- `getCurrentPosition()` would require external position tracking
- Should throw `UnsupportedOperationException` unless position tracking is implemented

**Non-Equivalence Notes:**
- **CRITICAL:** JCoDroneEdu does not provide absolute positioning or position tracking
- Unlike Tello (with mission pads), JCoDroneEdu has no built-in positioning system
- All movement is relative to current position/orientation

---

## 5. Direct Control (RC Mode)

### StandardDrone API
- Not explicitly defined in standard API

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| N/A | `sendControl(int roll, int pitch, int yaw, int throttle)` | Direct RC-style control (single command) | N/A |
| N/A | `sendControlWhile(int roll, int pitch, int yaw, int throttle, long millisec)` | RC control for duration | N/A |
| N/A | `sendControlPosition(float posX, float posY, float posZ, float velocity, int heading, int rotationalVelocity)` | Position-based control | N/A |
| N/A | `setPitch(int)`, `setRoll(int)`, `setYaw(int)`, `setThrottle(int)` + `move()` | Set control values then execute | N/A |

**Additional JCoDroneEdu Methods (No Standard API Equivalent):**
- `sendControl(int, int, int, int)` - Immediate RC control
- `sendControlWhile(int, int, int, int, long)` - Timed RC control
- `sendControlPosition(...)` - Advanced position control
- `setPitch()`, `setRoll()`, `setYaw()`, `setThrottle()`, `move()` - Staged control

**Implementation Notes:**
- JCoDroneEdu provides extensive low-level control options
- Multiple approaches: immediate, timed, position-based, or staged
- More flexible than Tello's single `flyRC()` method
- Consider adding RC control mode to standard API for advanced users

---

## 6. Speed Control

### StandardDrone API
- `void setSpeed(double speed, String unit)` - For absolute speed values
- `void setSpeedLevel(int level)` - For speed levels
- `double getSpeed(String unit)` - Returns current speed
- `int getSpeedLevel()` - Returns current speed level

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `setSpeed()` | No direct equivalent | Speed set per-command, not globally | ❌ Not Supported |
| `setSpeedLevel()` | `changeSpeed(int level)` | Changes speed level (1-4) | ✅ Supported |
| `getSpeed()` | No direct equivalent | No speed query method | ❌ Not Supported |
| `getSpeedLevel()` | No direct equivalent | No speed query method | ❌ Not Supported |

**Additional JCoDroneEdu Methods:**
- `changeSpeed(int level)` - Sets speed level (1-4 scale)
- Speed/velocity parameters in individual movement commands

**Non-Equivalence Notes:**
- **CRITICAL:** JCoDroneEdu uses speed LEVELS (1-4), NOT absolute speed values
- This is opposite to Tello-SDK (which uses absolute cm/s values)
- Speed can be set globally via `changeSpeed(level)` OR per-command via velocity parameter
- No method to query current speed or speed level

**Implementation Notes:**
- `setSpeed(speed, unit)` NOT supported - JCoDroneEdu doesn't use absolute speed
- `setSpeedLevel(level)` should map to `changeSpeed(level)` with range validation
- Speed level range appears to be 1-4 (needs verification)
- `getSpeed()` and `getSpeedLevel()` should throw `UnsupportedOperationException`
- Document that speed is typically set per-command in JCoDroneEdu

**Comparison with Tello-SDK:**
- Tello: Absolute speed (cm/s), range 10-100, global setting
- JCoDroneEdu: Speed levels (1-4), can be global or per-command

---

## 7. Telemetry Methods

### StandardDrone API
- `int getBattery()`
- `double getHeight(String unit)`
- `double getTemperature()`
- `String getFlightState()`
- `String getMovementState()`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `getBattery()` | `getBattery()` | Returns battery percentage (0-100) | ✅ Supported |
| `getHeight()` | `getHeight()` | Returns height (unit from sensor reading) | ✅ Supported |
| `getTemperature()` | No direct equivalent | No temperature sensor | ❌ Not Supported |
| `getFlightState()` | `getFlightState()` | Returns flight state enum/string | ✅ Supported |
| `getMovementState()` | `getMovementState()` | Returns movement state enum/string | ✅ Supported |

**Additional JCoDroneEdu Telemetry Methods (No Standard API Equivalent):**
- `getFrontRange()` - Distance from front sensor (obstacle detection)
- `getTrim()` - Current trim values
- `getDroneStatus()` - Comprehensive drone status object
- `getErrorData()` - Error information
- `getErrors()` - List of current errors

**Implementation Notes:**
- Height may require unit conversion depending on sensor configuration
- Flight state and movement state are directly available (unlike Tello)
- No temperature sensor in JCoDroneEdu drones
- `getTemperature()` should throw `UnsupportedOperationException`
- Additional telemetry (front range, trim, errors) valuable for debugging

**Advantages over Tello-SDK:**
- Explicit flight state and movement state tracking
- Front range sensor for obstacle detection
- Error reporting system
- Comprehensive status object

---

## 8. Video/Camera Methods

### StandardDrone API
- `void startVideo()`
- `void stopVideo()`

### JCoDroneEdu Mapping
| Standard API Method | JCoDroneEdu Method | Notes | Support Status |
|---------------------|-------------------|-------|----------------|
| `startVideo()` | No direct equivalent | Video streaming not supported | ❌ Not Supported |
| `stopVideo()` | No direct equivalent | Video streaming not supported | ❌ Not Supported |

**Additional JCoDroneEdu Methods (No Standard API Equivalent):**
- `loadColorClassifier(String, boolean)` - Loads color classification model
- `unloadColorClassifier()` - Unloads color classifier
- `appendColorData(String, double[][], String)` - Adds color training data
- `loadClassifier(String)` - Loads general classifier
- `loadColorData(String, boolean)` - Loads color data
- `newColorData(String, double[][], String)` - Creates new color dataset
- `predictColors(double[])` - Predicts colors from sensor data

**Implementation Notes:**
- JCoDroneEdu drones do not have video cameras
- Video methods should throw `UnsupportedOperationException`
- Color classification is based on color sensors, not camera feed
- Color classification is a unique JCoDroneEdu feature for educational purposes

**Non-Equivalence Notes:**
- **CRITICAL:** JCoDroneEdu does not support video streaming
- Color sensors and ML-based color classification instead of camera
- Fundamentally different from Tello's camera-based video streaming

---

## 9. Sensor and Configuration Methods

### StandardDrone API
- Not defined

### JCoDroneEdu Mapping
**Sensor Configuration:**
- `clearBias()` - Clears sensor bias
- `clearTrim()` - Clears trim settings
- `setDefault()` - Resets to default settings
- `resetGyro()` - Resets gyroscope
- `setTrim(int, int)` - Sets trim values
- `resetTrim()` - Resets trim to default
- `clearCounter()` - Clears internal counters

**Controller Access:**
- `getFlightController()` - Access to flight control subsystem
- `getLinkController()` - Access to communication subsystem
- `getSettingsController()` - Access to settings subsystem
- `getLinkManager()` - Access to link management
- `getReceiver()` - Access to receiver subsystem

**Additional Features:**
- `setBacklight(boolean)` - Controls LED backlight

**Implementation Notes:**
- Extensive low-level configuration and calibration options
- Not part of standard API - drone-specific features
- Controller access methods provide advanced control for expert users
- Trim and bias calibration important for flight stability
- Should be documented as JCoDroneEdu-specific extensions

---

## 10. Communication Methods

### StandardDrone API
- Not defined (beyond connect/disconnect)

### JCoDroneEdu Mapping
**Low-Level Communication:**
- `sendRequest(DataType)` - Sends data request (non-blocking)
- `sendRequestWait(DataType)` - Sends data request (blocking)

**Implementation Notes:**
- Low-level communication methods for advanced users
- Allows direct protocol access
- Not appropriate for standard API
- Document as implementation-specific extensions

---

## Summary

### Fully Supported Features
- ✅ Connection management (connect, disconnect with pair() prerequisite)
- ✅ Basic flight (takeoff, land, hover)
- ✅ Dead reckoning movement (forward, backward, left, right with native unit support)
- ✅ Rotation (left, right with velocity parameter)
- ✅ Speed level control (changeSpeed)
- ✅ Telemetry (battery, height, flight state, movement state)

### Partially Supported Features
- ⚠️ Vertical movement (via moveDistance with Z-axis)
- ⚠️ Hover (no duration parameter, implemented with delay)
- ⚠️ Absolute positioning (requires external position tracking)

### Not Supported Features
- ❌ Absolute speed values (uses speed levels instead)
- ❌ Speed query methods (getSpeed, getSpeedLevel)
- ❌ Temperature sensor
- ❌ Video streaming
- ❌ Absolute position tracking (getCurrentPosition)

### Non-Equivalences

1. **Speed Control:** JCoDroneEdu uses speed LEVELS (1-4), not absolute values
   - Opposite of Tello-SDK (which uses absolute cm/s)
   - Standard API has both `setSpeed()` and `setSpeedLevel()`
   - JCoDroneEdu only supports `setSpeedLevel()` via `changeSpeed()`
   - Speed can also be set per-command (unlike Tello)

2. **Speed in Movement Commands:** JCoDroneEdu supports per-command velocity
   - Standard API specifies speed per movement command (matches JCoDroneEdu)
   - Advantage: More flexible than Tello's global speed
   - No need to change global speed before each movement

3. **Vertical Movement:** No dedicated up/down methods
   - Must use `moveDistance(0, 0, z, velocity)` for vertical movement
   - More complex but allows 3D diagonal movement

4. **Absolute Positioning:** No built-in position tracking
   - `moveTo()` and `getCurrentPosition()` not natively supported
   - Would require external position estimation/tracking system
   - Should throw `UnsupportedOperationException` without tracking

5. **Video/Camera:** No video streaming capability
   - Has color sensors with ML classification instead
   - Fundamental hardware difference from Tello
   - Video methods must throw `UnsupportedOperationException`

### Gaps in Standard API

The following JCoDroneEdu features have no standard API equivalent:

**Hardware-Specific:**
- Color sensor and ML-based color classification
- Front range sensor for obstacle detection
- LED backlight control
- Headless flight mode

**Flight Control:**
- Direct RC control with multiple approaches (sendControl, sendControlWhile, sendControlPosition)
- Staged control (setPitch/setRoll/setYaw/setThrottle + move)
- 3D position movement (moveDistance)

**Configuration:**
- Sensor calibration (bias, trim, gyro reset)
- Link mode and controller mode settings
- Advanced controller access (flight, link, settings controllers)

**Telemetry:**
- Front range sensor
- Trim values
- Error reporting system
- Comprehensive drone status

**Communication:**
- Low-level request/response methods
- Pairing mechanism

### Comparison: JCoDroneEdu vs Tello-SDK

| Feature | JCoDroneEdu | Tello-SDK |
|---------|-------------|-----------|
| Speed Control | Levels (1-4) | Absolute (10-100 cm/s) |
| Speed Scope | Per-command OR global | Global only |
| Unit Support | Native (m, cm, etc.) | Fixed (cm) |
| Vertical Movement | Via moveDistance(z) | Dedicated up/down |
| Position Tracking | None | Mission pads (optional) |
| Video | None | UDP stream |
| Camera | Color sensors | Video camera |
| RC Control | Multiple methods | Single flyRC |
| Flight State | Native support | Must approximate |
| Movement State | Native support | Not available |
| Obstacle Detection | Front range sensor | Time-of-flight (ToF) |
| Emergency Stop | emergencyStop() | emergency() |

### Recommendations

1. **Document Unsupported Methods:**
   - `setSpeed()` (absolute) → throws `UnsupportedOperationException`
   - `getSpeed()`, `getSpeedLevel()` → throws `UnsupportedOperationException`
   - `getTemperature()` → throws `UnsupportedOperationException`
   - `startVideo()`, `stopVideo()` → throws `UnsupportedOperationException`
   - `moveTo()`, `getCurrentPosition()` → throws `UnsupportedOperationException` (without external tracking)

2. **Implement Adapters:**
   - Map `setSpeedLevel(level)` to `changeSpeed(level)` with validation
   - Map `moveUp()/moveDown()` to `moveDistance(0, 0, ±z, velocity)`
   - Map `hover(duration)` to `hover()` + sleep(duration)

3. **Unit Conversion:**
   - JCoDroneEdu supports units natively - minimal conversion needed
   - Validate unit strings match JCoDroneEdu's expected format

4. **Extension Interface:**
   - Create JCoDroneEduExtensions interface for:
     - Color classification methods
     - Front range sensor
     - Trim and calibration
     - Direct control methods
     - Controller access
     - Headless mode
     - Error reporting

5. **Position Tracking (Optional):**
   - Implement dead-reckoning position tracker
   - Use movement commands to estimate position
   - Warning: Accumulates error over time
   - Enable `moveTo()` and `getCurrentPosition()` if implemented

6. **Speed Level Documentation:**
   - Document speed level range (1-4)
   - Document mapping between levels and actual speeds (if known)
   - Consider measuring/documenting actual speeds per level

7. **Testing Considerations:**
   - Test pairing + connection sequence
   - Test unit conversion accuracy
   - Test vertical movement via moveDistance
   - Test speed level changes
   - Test hover with duration
   - Verify exception throwing for unsupported methods

---

## Implementation Priority

### High Priority (Core Functionality)
1. Connection (pair + connect, disconnect)
2. Basic flight (takeoff, land, hover)
3. Horizontal movement (forward, backward, left, right)
4. Rotation (left, right)
5. Speed level control (setSpeedLevel → changeSpeed)
6. Basic telemetry (battery, height)

### Medium Priority (Enhanced Functionality)
7. Vertical movement (moveUp/moveDown → moveDistance)
8. Flight/movement state telemetry
9. Exception handling for unsupported methods
10. Unit conversion validation

### Low Priority (Advanced Features)
11. Extension interface for JCoDroneEdu-specific features
12. Optional position tracking implementation
13. Direct control wrapper methods
14. Color classification integration
15. Error reporting integration

---

## References
- `STANDARD_DRONE_API_PROPOSAL.md` - Standard API definition
- `DRONE_LIBRARIES_COMPARISON.md` - Feature comparison across SDKs
- `TELLO_SDK_FEATURE_MAPPING.md` - Similar mapping for Tello-SDK
- `API_EQUIVALENCE_ERRORS.md` - Known non-equivalences
- `INITIAL_ISSUES.md` - Project tasks and goals
