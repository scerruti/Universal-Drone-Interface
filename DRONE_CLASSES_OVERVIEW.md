# Tello-SDK: Class Overview

## Main Classes
- `TelloControl`: High-level drone control and command interface
- `TelloDrone`: Represents the drone state
- `TelloCommunication`: Handles communication with the drone
- `TelloCamera`: Video and camera control
- `TelloConnection`: Connection state management
- `TelloControlInterface`: Interface for TelloControl
- `TelloCommandInterface`, `BasicTelloCommand`, `ComplexTelloCommand`: Command abstractions

## Telemetry/Status Classes
- `TelloConnection`: Enum for connection state
- `TelloModel`: Drone model information

## Other
- `MissionDetectionCamera`: Mission pad/camera integration
- `TelloFlip`: Flip command values

---

# JCoDroneEdu: Class Overview

## Main Classes
- `Drone`: Primary drone control and telemetry class
- `FlightController`: Flight event and state management
- `LinkController`: Communication link management
- `SettingsController`: Drone and controller settings
- `LinkManager`: Link and device info
- `Receiver`: Handles incoming data

## Telemetry/Status Classes
- `DroneStatus`: Drone status and state
- `ErrorData`: Error and fault information
- `InformationData`, `ButtonData`, `JoystickData`, `AddressData`, `CpuIdData`: Various telemetry and device data

## Other
- `AutonomousMethod`: Base for autonomous behaviors
- `BuzzerSequence`, `BuzzerSequenceRegistry`: Buzzer and sound control
- `ColorClassifier`: Color detection/classification

---

# Notes
- Both libraries have a modular structure, but JCoDroneEdu exposes more telemetry and device data classes.
- Tello-SDK is more focused on high-level command and control.
