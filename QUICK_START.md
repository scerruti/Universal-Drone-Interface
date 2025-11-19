# Universal Drone Interface - Quick Start Guide

## What's New

The Universal Drone Interface now includes core Java interfaces for drone control!

## Files Created

### Java Source Code (`src/main/java/org/universaldrone/`)
- **Position.java** - Represents 3D coordinates with units
- **UniversalDrone.java** - English interface for drone control
- **DronUniversal.java** - Spanish (Mexican) interface for drone control
- **README.md** - Detailed usage documentation

### Documentation (Root directory)
- **IMPLEMENTATION_VALIDATION.md** - Verification checklist
- **SECURITY_SUMMARY.md** - Security analysis results

## Quick Reference

### Method Categories

**Connection (2 methods)**
- `connect()` / `conectar()` - Connect to drone
- `disconnect()` / `desconectar()` - Disconnect from drone

**Flight Control (2 methods)**
- `takeOff()` / `despegar()` - Take off from ground
- `land()` / `aterrizar()` - Land safely

**Movement (6 methods)**
- `moveForward()` / `moverAdelante()` - Move forward
- `moveBackward()` / `moverAtras()` - Move backward
- `moveLeft()` / `moverIzquierda()` - Move left
- `moveRight()` / `moverDerecha()` - Move right
- `moveUp()` / `moverArriba()` - Move up
- `moveDown()` / `moverAbajo()` - Move down

**Rotation (3 methods)**
- `rotateLeft()` / `rotarIzquierda()` - Rotate left
- `rotateRight()` / `rotarDerecha()` - Rotate right
- `hover()` / `mantener()` - Hover in place

**Positioning (2 methods)**
- `moveTo()` / `moverA()` - Move to absolute position
- `getCurrentPosition()` / `obtenerPosicionActual()` - Get current position

**Speed Control (4 methods)**
- `setSpeed()` / `establecerVelocidad()` - Set exact speed
- `setSpeedLevel()` / `establecerNivelVelocidad()` - Set speed level
- `getSpeed()` / `obtenerVelocidad()` - Get current speed
- `getSpeedLevel()` / `obtenerNivelVelocidad()` - Get speed level

**Telemetry (5 methods)**
- `getBattery()` / `obtenerBateria()` - Get battery percentage
- `getHeight()` / `obtenerAltura()` - Get height above ground
- `getTemperature()` / `obtenerTemperatura()` - Get temperature
- `getFlightState()` / `obtenerEstadoVuelo()` - Get flight state
- `getMovementState()` / `obtenerEstadoMovimiento()` - Get movement state

**Video (2 methods)**
- `startVideo()` / `iniciarVideo()` - Start video stream
- `stopVideo()` / `detenerVideo()` - Stop video stream

**Total: 26 methods**

## Simple Example (English)

```java
UniversalDrone drone = new TelloDroneAdapter();
drone.connect();
drone.takeOff();
drone.moveForward(100, "cm", 20, "cm/s");
drone.land();
drone.disconnect();
```

## Ejemplo Simple (Español)

```java
DronUniversal dron = new AdaptadorDronTello();
dron.conectar();
dron.despegar();
dron.moverAdelante(100, "cm", 20, "cm/s");
dron.aterrizar();
dron.desconectar();
```

## Common Units

**Distance:** "m" (meters), "cm" (centimeters), "in" (inches)
**Speed:** "m/s" (meters/second), "cm/s" (centimeters/second)
**Angles:** degrees (0-360)

## Compiling

```bash
javac -d build src/main/java/org/universaldrone/*.java
```

## Generating Documentation

```bash
javadoc -d docs/javadoc -encoding UTF-8 -charset UTF-8 src/main/java/org/universaldrone/*.java
```

## Security

✅ All code passed CodeQL security analysis with 0 alerts.

## Educational Use

This interface is designed for educational use and is written at a 9th grade reading level. It includes:
- Clear explanations
- Real-world analogies
- Example code
- Comprehensive documentation

## Next Steps

To use these interfaces, you need to:
1. Create adapter classes that implement UniversalDrone or DronUniversal
2. Add the actual drone communication code (Tello-SDK, JCoDroneEdu, etc.)
3. Test with your specific drone hardware

See the README.md in `src/main/java/org/universaldrone/` for more details!
