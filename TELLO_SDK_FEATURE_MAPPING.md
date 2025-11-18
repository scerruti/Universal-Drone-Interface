# Tello-SDK Feature Mapping to Standard API

This document maps Tello-SDK features to the proposed Standard Drone API as defined in `STANDARD_DRONE_API_PROPOSAL.md`.

## Mapping Overview

The Standard Drone API is designed to abstract common functionality across multiple drone SDKs. This mapping identifies which Tello-SDK features correspond to standard API methods, notes any non-equivalences, and documents gaps or unsupported features.

---

## 1. Connection Methods

### StandardDrone API
- `void connect() throws Exception`
- `void disconnect()`

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `connect()` | `connect()` | Establishes UDP connection to drone | ✅ Supported |
| `disconnect()` | `disconnect()` | Closes connection | ✅ Supported |

**Additional Tello-SDK Methods (No Standard API Equivalent):**
- `enterCommandMode()` - Switches drone to SDK command mode (required before other commands)
- `setStationMode(String ssid, String password)` - WiFi station mode configuration
- `startKeepAlive()` / `stopKeepAlive()` - Maintains connection with keep-alive packets

**Implementation Notes:**
- Tello-SDK's `connect()` may need to be followed by `enterCommandMode()` for proper initialization
- Consider wrapping both in the standard API implementation

---

## 2. Basic Flight Commands

### StandardDrone API
- `void takeoff()` (implicit from DRONE_LIBRARIES_COMPARISON.md)
- `void land()` (implicit from DRONE_LIBRARIES_COMPARISON.md)
- `void hover(double durationSeconds)`

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `takeoff()` | `takeOff()` | Initiates takeoff sequence | ✅ Supported |
| `land()` | `land()` | Initiates landing sequence | ✅ Supported |
| `hover()` | No direct equivalent | Can be simulated with `stop()` + delay | ⚠️ Partial |
| `emergency()` | `emergency()` | Emergency stop (not in standard API) | N/A |

**Additional Tello-SDK Methods (No Standard API Equivalent):**
- `emergency()` - Stops all motors immediately (emergency landing)
- `doFlip(TelloFlip)` - Performs aerobatic flip

**Implementation Notes:**
- `hover(durationSeconds)` can be implemented by calling `stop()` followed by a timed delay
- Consider adding `emergency()` to standard API for safety

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

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `moveForward()` | `forward(Integer cm)` | Distance in cm, speed set separately via `setSpeed()` | ✅ Supported |
| `moveBackward()` | `backward(Integer cm)` | Distance in cm, speed set separately via `setSpeed()` | ✅ Supported |
| `moveLeft()` | `left(Integer cm)` | Distance in cm, speed set separately via `setSpeed()` | ✅ Supported |
| `moveRight()` | `right(Integer cm)` | Distance in cm, speed set separately via `setSpeed()` | ✅ Supported |
| `moveUp()` | `up(Integer cm)` | Distance in cm | ✅ Supported |
| `moveDown()` | `down(Integer cm)` | Distance in cm | ✅ Supported |
| `rotateLeft()` | `rotateLeft(Integer degrees)` | Rotation angle in degrees | ✅ Supported |
| `rotateRight()` | `rotateRight(Integer degrees)` | Rotation angle in degrees | ✅ Supported |

**Implementation Notes:**
- Tello-SDK uses fixed units (cm for distance, cm/s for speed)
- Unit conversion required in standard API implementation
- Tello-SDK separates speed control from movement commands
- Speed parameter in standard API should be applied via `setSpeed()` before movement

**Limitations:**
- Tello-SDK does not support setting speed on a per-command basis
- Speed must be set globally before issuing movement commands
- Movement commands are blocking (wait for completion)

---

## 4. Absolute Position Movement

### StandardDrone API
- `void moveTo(double x, double y, double z, String unit)`
- `Position getCurrentPosition(String unit)`

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `moveTo()` | `goTo(Integer x, Integer y, Integer z, Integer speed)` | Mission Pad required; coordinates relative to pad | ⚠️ Conditional |
| `getCurrentPosition()` | No direct equivalent | Would require mission pad and state parsing | ❌ Not Supported |

**Additional Tello-SDK Methods:**
- `goTo(Integer x, Integer y, Integer z, Integer speed)` - Requires mission pads to be detected
- `curve(Integer x1, Integer y1, Integer z1, Integer x2, Integer y2, Integer z2, Integer speed)` - Curved flight path with mission pads

**Implementation Notes:**
- Absolute positioning requires Tello mission pads (physical markers)
- Without mission pads, these methods should throw `UnsupportedOperationException`
- Position tracking is not available without external systems

---

## 5. Direct Control (RC Mode)

### StandardDrone API
- Not explicitly defined in standard API

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| N/A | `flyRC(Integer roll, Integer pitch, Integer throttle, Integer yaw)` | Direct RC-style control | N/A |
| N/A | `stop()` | Stops current movement and hovers | N/A |

**Additional Tello-SDK Methods (No Standard API Equivalent):**
- `flyRC(Integer roll, Integer pitch, Integer throttle, Integer yaw)` - Low-level RC control
- `stop()` - Immediate stop and hover

**Implementation Notes:**
- Consider adding RC control mode to standard API for advanced users
- `stop()` is useful for emergency situations

---

## 6. Speed Control

### StandardDrone API
- `void setSpeed(double speed, String unit)` - For absolute speed values
- `void setSpeedLevel(int level)` - For speed levels
- `double getSpeed(String unit)` - Returns current speed
- `int getSpeedLevel()` - Returns current speed level

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `setSpeed()` | `setSpeed(Integer cmps)` | Speed in cm/s (10-100 range) | ✅ Supported |
| `setSpeedLevel()` | N/A | Tello uses absolute values, not levels | ❌ Not Supported |
| `getSpeed()` | `getSpeed()` | Returns current speed in cm/s | ✅ Supported |
| `getSpeedLevel()` | N/A | Tello uses absolute values, not levels | ❌ Not Supported |

**Non-Equivalence Notes:**
- **CRITICAL:** Tello-SDK uses absolute speed values (cm/s), NOT speed levels
- This is a fundamental difference from JCoDroneEdu (which uses levels)
- See `API_EQUIVALENCE_ERRORS.md` for detailed discussion
- Range: 10-100 cm/s for Tello

**Implementation Notes:**
- `setSpeed(speed, unit)` should convert to cm/s and call `setSpeed(Integer)`
- `setSpeedLevel(level)` should map level (1-10?) to cm/s range (10-100)
- `getSpeedLevel()` should calculate level from absolute speed
- Document the speed range and level mapping in implementation

---

## 7. Telemetry Methods

### StandardDrone API
- `int getBattery()`
- `double getHeight(String unit)`
- `double getTemperature()`
- `String getFlightState()`
- `String getMovementState()`

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `getBattery()` | `getBattery()` | Returns battery percentage (0-100) | ✅ Supported |
| `getHeight()` | `getHeight()` | Returns height in cm | ✅ Supported |
| `getTemperature()` | `getTemp()` | Returns temperature in °C | ✅ Supported |
| `getFlightState()` | No direct equivalent | Could derive from connection state | ⚠️ Partial |
| `getMovementState()` | No direct equivalent | No explicit movement state | ❌ Not Supported |

**Additional Tello-SDK Telemetry Methods (No Standard API Equivalent):**
- `getSpeed()` - Current speed (cm/s)
- `getTime()` - Flight time (seconds)
- `getBarometer()` - Barometric pressure (hPa)
- `getAttitude()` - Attitude: pitch, roll, yaw (degrees)
- `getAcceleration()` - Acceleration (m/s²)
- `getTof()` - Time-of-flight distance sensor (cm)
- `getSN()` - Drone serial number
- `getSDK()` - SDK version

**Implementation Notes:**
- Height is in cm, requires conversion for other units
- Flight state can be approximated (e.g., "connected", "flying", "landed")
- Movement state is not tracked by Tello-SDK
- Consider adding extended telemetry methods in implementation-specific interface

---

## 8. Video/Camera Methods

### StandardDrone API
- `void startVideo()`
- `void stopVideo()`

### Tello-SDK Mapping
| Standard API Method | Tello-SDK Method | Notes | Support Status |
|---------------------|------------------|-------|----------------|
| `startVideo()` | `streamOn()` | Starts video stream | ✅ Supported |
| `stopVideo()` | `streamOff()` | Stops video stream | ✅ Supported |

**Additional Tello-SDK Methods (No Standard API Equivalent):**
- `startStatusMonitor()` / `stopStatusMonitor()` - Status telemetry streaming

**Implementation Notes:**
- Video streaming is UDP-based
- Actual video frame handling not defined in standard API
- Consider adding video frame callback/listener mechanism

---

## 9. Mission Pad / Advanced Features

### StandardDrone API
- Not defined

### Tello-SDK Mapping
- `setMissionMode(boolean enabled, MissionDetectionCamera camera)` - Enable mission pad detection

**Implementation Notes:**
- Mission pads enable absolute positioning and advanced navigation
- Not part of standard API
- Required for `goTo()` and `curve()` commands
- Should be documented as Tello-specific extension

---

## 10. Utility / Logging Methods

### StandardDrone API
- Not defined

### Tello-SDK Mapping
- `setLogLevel(Level)` - Configure logging verbosity
- `getConnection()` - Get connection state/object

**Implementation Notes:**
- These are utility methods for debugging and monitoring
- Not part of the standard API abstraction
- Could be exposed through implementation-specific interfaces
- Logging configuration is typically handled outside the drone API

---

## Summary

### Fully Supported Features
- ✅ Connection management (connect, disconnect)
- ✅ Basic flight (takeoff, land)
- ✅ Dead reckoning movement (forward, backward, left, right, up, down)
- ✅ Rotation (left, right)
- ✅ Speed control (absolute values)
- ✅ Basic telemetry (battery, height, temperature)
- ✅ Video streaming (start, stop)

### Partially Supported Features
- ⚠️ Hover (can be simulated with stop + delay)
- ⚠️ Flight state (can be approximated)
- ⚠️ Absolute positioning (requires mission pads)

### Not Supported Features
- ❌ Speed levels (uses absolute values instead)
- ❌ Movement state tracking
- ❌ Position tracking (without mission pads)
- ❌ getCurrentPosition() (without mission pads)

### Non-Equivalences
1. **Speed Control:** Tello uses absolute cm/s values (10-100), not levels
   - Standard API has both `setSpeed()` and `setSpeedLevel()`
   - Tello only supports `setSpeed()` with absolute values
   
2. **Speed in Movement Commands:** Tello sets speed globally, not per-command
   - Standard API specifies speed per movement command
   - Implementation must call `setSpeed()` before each movement
   
3. **Absolute Positioning:** Requires mission pads
   - `moveTo()` and `getCurrentPosition()` conditional on hardware setup
   - Should throw `UnsupportedOperationException` without mission pads

### Gaps in Standard API
The following Tello-SDK features have no standard API equivalent:
- Emergency stop
- Aerobatic flips
- RC-style direct control
- Advanced telemetry (barometer, attitude, acceleration, ToF, serial number, SDK version)
- Mission pad configuration
- Communication mode settings (station mode, keep-alive)
- Status monitoring
- Utility methods (logging, connection state)

### Recommendations
1. Document which methods throw `UnsupportedOperationException`
2. Implement speed level mapping (level → cm/s conversion)
3. Consider adding emergency stop to standard API
4. Add extension interface for Tello-specific features
5. Document mission pad requirements for absolute positioning
6. Implement hover() via stop() with delay

---

## References
- `STANDARD_DRONE_API_PROPOSAL.md` - Standard API definition
- `DRONE_LIBRARIES_COMPARISON.md` - Feature comparison across SDKs
- `API_EQUIVALENCE_ERRORS.md` - Known non-equivalences
- `DRONE_TELEMETRY_COMPARISON.md` - Telemetry method comparison
- `INITIAL_ISSUES.md` - Project tasks and goals
