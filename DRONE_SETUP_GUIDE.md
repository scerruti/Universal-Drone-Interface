# Drone Setup Instructions

## Tello-SDK (Tello Drone)

### Requirements
- Tello drone powered on
- Computer connected to Tello WiFi network
- Java runtime (JDK 8+)

### Basic Setup Steps
1. Power on the Tello drone.
2. Connect your computer to the Tello WiFi network (SSID usually starts with "TELLO-").
3. In your Java code, use the following pattern:

```java
import tellolib.control.TelloControl;

TelloControl tello = TelloControl.getInstance();
tello.connect();
tello.enterCommandMode();
// Now you can send flight commands, e.g.:
tello.takeOff();
```

4. To land and disconnect:
```java
tello.land();
tello.disconnect();
```

---

## JCoDroneEdu (CoDrone EDU)

### Requirements
- CoDrone EDU powered on
- USB cable or Bluetooth connection to computer
- Java runtime (JDK 8+)

### Basic Setup Steps
1. Power on the CoDrone EDU.
2. Connect the drone to your computer via USB (recommended) or pair via Bluetooth.
3. In your Java code, use the following pattern:

```java
import com.otabi.jcodroneedu.Drone;

Drone drone = new Drone();
drone.pair(); // or drone.connect();
// Now you can send flight commands, e.g.:
drone.takeoff();
```

4. To land and disconnect:
```java
drone.land();
drone.disconnect();
```

---

# Notes
- Tello-SDK uses WiFi/UDP; JCoDroneEdu uses USB/Bluetooth serial.
- Always ensure the drone is on and the connection is established before sending commands.
- Refer to each library's documentation for advanced setup and troubleshooting.
