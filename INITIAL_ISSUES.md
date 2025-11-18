# Universal-Drone-Interface: Initial Issue List

This issue tracks the initial set of tasks for bootstrapping the project and enabling agent-driven development.

## API Implementation
- [ ] Implement the `StandardDrone` Java interface as proposed in `STANDARD_DRONE_API_PROPOSAL.md`.
- [ ] Define the `Position` class/structure.
- [ ] Document which methods are supported by each SDK and which throw `UnsupportedOperationException`.

## SDK Feature Mapping
- [ ] Map Tello-SDK features to the standard API (see `DRONE_LIBRARIES_COMPARISON.md`).
- [ ] Map JCoDroneEdu features to the standard API.
- [ ] Document non-equivalent or missing features (see `API_EQUIVALENCE_ERRORS.md`).

## Documentation
- [ ] Refine and expand API proposal documentation.
- [ ] Improve setup and usage guides.
- [ ] Summarize API naming and equivalence discussions.

## Test/Example Code
- [ ] Add example code for flying a square using the standard API.
- [ ] Add test cases for each API method.

---

Please use the appropriate issue template when claiming or working on a task. Agents may reference this issue for initial work planning.
