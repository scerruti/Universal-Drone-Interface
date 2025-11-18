# Flying in a Square: Example Code

## Tello-SDK (Tello Drone)

```java
import tellolib.control.TelloControl;

public class FlySquareTello {
    public static void main(String[] args) {
        TelloControl tello = TelloControl.getInstance();
        tello.connect();
        tello.enterCommandMode();
        tello.takeOff();
        int distance = 50; // centimeters
        for (int i = 0; i < 4; i++) {
            tello.forward(distance);
            tello.rotateRight(90);
        }
        tello.land();
        tello.disconnect();
    }
}
```

---

## JCoDroneEdu (CoDrone EDU)

```java
import com.otabi.jcodroneedu.Drone;

public class FlySquareCoDrone {
    public static void main(String[] args) {
        Drone drone = new Drone();
        drone.pair(); // or drone.connect();
        drone.takeoff();
        double distance = 0.5; // meters
        for (int i = 0; i < 4; i++) {
            drone.moveForward(distance, "m", 0.3); // move forward 0.5 meters at 0.3 m/s
            drone.turnRight(90); // turn right 90 degrees
        }
        drone.land();
        drone.disconnect();
    }
}
```

---

# Notes
- Adjust `distance` and speed as needed for your environment.
- Always ensure a safe, open area for flight.
- Both examples assume blocking calls; in real use, you may need to add delays or check for completion depending on the library's behavior.
