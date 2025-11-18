# Tello-SDK: Functionality Overview

## Flight Commands
- connect()
- disconnect()
- takeOff()
- land()
- doFlip(TelloFlip)
- setSpeed(Integer)
- forward(Integer)
- backward(Integer)
- right(Integer)
- left(Integer)
- rotateRight(Integer)
- rotateLeft(Integer)
- up(Integer)
- down(Integer)
- goTo(Integer, Integer, Integer, Integer)
- flyRC(Integer, Integer, Integer, Integer)
- stop()
- emergency()
- curve(Integer, Integer, Integer, Integer, Integer, Integer, Integer)

## Communication
- enterCommandMode()
- setStationMode(String, String)
- setMissionMode(boolean, MissionDetectionCamera)
- startKeepAlive()
- stopKeepAlive()

## Telemetry
- getBattery()
- getSpeed()
- getTime()
- getHeight()
- getTemp()
- getBarometer()
- getAttitude()
- getAcceleration()
- getTof()
- getSN()
- getSDK()

## Video/Status
- streamOn()
- streamOff()
- startStatusMonitor()
- stopStatusMonitor()

## Other
- setLogLevel(Level)
- getConnection()

---

# JCoDroneEdu: Functionality Overview

## Flight Commands
- pair()
- connect()
- disconnect()
- takeoff()
- land()
- emergencyStop()
- hover()
- setPitch(int)
- setRoll(int)
- setYaw(int)
- setThrottle(int)
- move()
- moveForward(double, String, double)
- moveBackward(double, String, double)
- moveLeft(double, String, double)
- moveRight(double, String, double)
- moveDistance(double, double, double, double)
- turn(int, Double)
- turnDegree(int, double, double)
- turnLeft(int, double)
- turnRight(int, double)
- changeSpeed(int)
- setHeadlessMode(boolean)

## Communication
- sendRequest(DataType)
- sendRequestWait(DataType)
- sendControl(int, int, int, int)
- sendControlWhile(int, int, int, int, long)
- sendControlPosition(float, float, float, float, int, int)
- setLinkMode(LinkController.LinkMode)
- setControllerMode(LinkController.ControllerMode)

## Telemetry
- getBattery()
- getFlightState()
- getMovementState()
- getErrorData()
- getErrors()
- getHeight()
- getFrontRange()
- getTrim()
- getDroneStatus()

## Other
- loadColorClassifier(String, boolean)
- unloadColorClassifier()
- appendColorData(String, double[][], String)
- loadClassifier(String)
- loadColorData(String, boolean)
- newColorData(String, double[][], String)
- predictColors(double[])
- setBacklight(boolean)
- clearBias()
- clearTrim()
- setDefault()
- resetGyro()
- setTrim(int, int)
- resetTrim()
- clearCounter()
- getFlightController()
- getLinkController()
- getSettingsController()
- getLinkManager()
- getReceiver()

---

# Notes
- Units for telemetry (height, distance, etc.) in JCoDroneEdu are often configurable (e.g., meters, centimeters).
- Tello-SDK uses integer values for most movement commands (likely centimeters).
- Both libraries provide extensive telemetry and control, but JCoDroneEdu exposes more granular control and additional features (e.g., color classification, headless mode).
