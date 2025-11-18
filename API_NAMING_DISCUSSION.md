# API Naming Discussion: Relative Position Change

## Context
Both Tello (camera-based) and CoDrone EDU (flow sensor) can provide information to estimate relative position change, but the mechanism and reliability differ.

- **CoDrone EDU**: Flow sensor provides direct, built-in displacement (x/y) data.
- **Tello**: Camera can be used for displacement estimation, but requires custom vision processing.

## Naming Considerations
- `getRelativePositionChange()` is too verbose and unclear.
- Better alternatives: `getDisplacement()`, `getDeltaPosition()`, `getMotionEstimate()`.
- These names are sensor-agnostic and clearly express the concept of relative movement.

## Decision
- Defer final naming. Documented here for future API design discussion.
- When implementing, ensure documentation clarifies platform-specific behavior and limitations.

---

# Action Items
- Update the standard API proposal after team consensus on naming.
- Ensure method returns a well-defined structure (e.g., DeltaPosition {dx, dy, dz, units, confidence?}).
- Document which drones support this natively and which require additional processing.
