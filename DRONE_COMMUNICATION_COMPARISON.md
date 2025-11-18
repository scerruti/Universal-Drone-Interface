# Tello-SDK: Communication Overview

- Communication is handled via the `TelloCommunication` class.
- Uses UDP protocol to send commands and receive responses from the drone.
- `connect()` and `disconnect()` manage the connection state.
- `enterCommandMode()` switches the drone to SDK command mode.
- `setStationMode(ssid, password)` configures WiFi station mode.
- `startKeepAlive()` and `stopKeepAlive()` manage keep-alive messages.
- Status monitoring via `startStatusMonitor()` and `stopStatusMonitor()`.

# JCoDroneEdu: Communication Overview

- Communication is managed by `LinkController`, `Receiver`, and related classes.
- Uses serial (USB) or Bluetooth for communication with the drone and controller.
- `pair()` and `connect()` establish the link.
- `sendRequest(DataType)` and `sendRequestWait(DataType)` request specific data from the drone.
- `sendControl(...)` methods send direct control commands.
- `setLinkMode()` and `setControllerMode()` configure communication modes.
- `getReceiver()` provides access to the low-level receiver for incoming data.

# Notes
- Tello-SDK is network-based (UDP/WiFi), while JCoDroneEdu is serial/Bluetooth-based.
- Both libraries provide methods for connection management, command sending, and status monitoring.
