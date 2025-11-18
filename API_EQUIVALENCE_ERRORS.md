# API Equivalence Error: setSpeed vs getSpeed

## Issue
- `setSpeed` and `getSpeed` are not equivalent between Tello-SDK and JCoDroneEdu.
- Tello-SDK uses absolute speed values (cm/s), while JCoDroneEdu uses speed levels (not direct speed values).
- This difference must be handled in any standard API abstraction.

---

# Investigating Altitude Change Commands

## Tello-SDK
- To change altitude:
  - `up(Integer distance)`: Moves the drone up by the specified distance (cm).
  - `down(Integer distance)`: Moves the drone down by the specified distance (cm).

## JCoDroneEdu
- To change altitude:
  - `moveDistance(double positionX, double positionY, double positionZ, double velocity)`: Can be used to move to a specific position, including altitude (Z).
  - There may also be direct methods for vertical movement, such as `moveUp` or `moveDown`, or by setting throttle and calling `move()`.

---

# Notes
- Both libraries allow altitude changes, but the method names and parameters differ.
- Tello-SDK uses simple up/down commands with distance in cm.
- JCoDroneEdu may require position or throttle-based movement for altitude changes.
- Standard API should provide `moveUp(distance, unit)` and `moveDown(distance, unit)` for clarity.
