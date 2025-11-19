package org.universaldrone;

/**
 * A universal interface for controlling different types of drones.
 * 
 * <p>This interface provides a common way to control any drone, whether it's a
 * Tello drone, a CoDrone EDU, or any other type of drone. Think of it like a
 * universal remote control that works with many different devices.</p>
 * 
 * <p>The interface includes methods for:</p>
 * <ul>
 *   <li>Connecting to and disconnecting from the drone</li>
 *   <li>Taking off and landing</li>
 *   <li>Moving the drone in different directions</li>
 *   <li>Rotating the drone</li>
 *   <li>Getting information about the drone (battery, height, temperature)</li>
 *   <li>Controlling the drone's camera and video</li>
 * </ul>
 * 
 * <h2>Example Usage</h2>
 * <pre>{@code
 * // Create a drone object (actual implementation depends on drone type)
 * UniversalDrone drone = new TelloDroneAdapter();
 * 
 * try {
 *     // Connect to the drone
 *     drone.connect();
 *     
 *     // Take off and fly in a square pattern
 *     drone.takeOff();
 *     for (int i = 0; i < 4; i++) {
 *         drone.moveForward(50, "cm", 20, "cm/s");
 *         drone.rotateRight(90);
 *     }
 *     
 *     // Check battery level
 *     int battery = drone.getBattery();
 *     System.out.println("Battery: " + battery + "%");
 *     
 *     // Land and disconnect
 *     drone.land();
 *     drone.disconnect();
 * } catch (Exception e) {
 *     System.err.println("Error: " + e.getMessage());
 * }
 * }</pre>
 * 
 * @author Universal Drone Interface
 * @version 1.0
 */
public interface UniversalDrone {
    
    // ========== Connection Methods ==========
    
    /**
     * Connects to the drone.
     * 
     * <p>This method establishes a connection with the drone so you can send it
     * commands. You must call this before using any other methods. Think of it
     * like turning on your phone's Bluetooth to connect to a speaker.</p>
     * 
     * @throws Exception if the connection fails (e.g., drone is off, out of range,
     *                   or already connected to another device)
     */
    void connect() throws Exception;
    
    /**
     * Disconnects from the drone.
     * 
     * <p>This method closes the connection with the drone. Always call this when
     * you're done flying to free up resources and allow other devices to connect.</p>
     */
    void disconnect();
    
    // ========== Takeoff and Landing ==========
    
    /**
     * Makes the drone take off from the ground.
     * 
     * <p>The drone will rise to a safe hovering height (usually about 1 meter or
     * 3 feet). Make sure there's enough space above the drone before taking off.</p>
     * 
     * @throws Exception if takeoff fails (e.g., low battery, motors blocked,
     *                   or drone is already flying)
     */
    void takeOff() throws Exception;
    
    /**
     * Makes the drone land safely on the ground.
     * 
     * <p>The drone will slowly descend until it touches the ground and then turn
     * off its motors. Make sure the landing area is clear of obstacles.</p>
     * 
     * @throws Exception if landing fails or the drone is not flying
     */
    void land() throws Exception;
    
    // ========== Absolute Movement Methods ==========
    
    /**
     * Moves the drone to an exact position in 3D space.
     * 
     * <p>This method tells the drone to fly to a specific x, y, z coordinate.
     * Not all drones support this feature - some can only move relative to their
     * current position.</p>
     * 
     * @param x the target x-coordinate (horizontal, left-right)
     * @param y the target y-coordinate (horizontal, forward-backward)
     * @param z the target z-coordinate (vertical, height)
     * @param unit the unit of measurement ("m" for meters, "cm" for centimeters, etc.)
     * @throws Exception if the drone doesn't support absolute positioning or
     *                   if the target position is unreachable
     */
    void moveTo(double x, double y, double z, String unit) throws Exception;
    
    /**
     * Gets the drone's current position in 3D space.
     * 
     * <p>This method returns where the drone is right now. Not all drones can
     * track their position - some only know how far they've moved from their
     * starting point.</p>
     * 
     * @param unit the unit of measurement you want ("m" for meters, "cm" for centimeters, etc.)
     * @return a Position object containing the x, y, z coordinates and unit
     * @throws Exception if the drone doesn't support position tracking
     */
    Position getCurrentPosition(String unit) throws Exception;
    
    // ========== Relative Movement Methods (Dead Reckoning) ==========
    
    /**
     * Moves the drone forward (in the direction it's facing).
     * 
     * <p>The drone will move straight ahead for the specified distance. If the
     * drone is facing north and you call moveForward(100, "cm", 20, "cm/s"),
     * it will move 100 centimeters north at a speed of 20 centimeters per second.</p>
     * 
     * @param distance how far to move
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @param speed how fast to move
     * @param speedUnit the unit for speed ("m/s", "cm/s", etc.)
     * @throws Exception if the movement fails or the parameters are invalid
     */
    void moveForward(double distance, String unit, double speed, String speedUnit) throws Exception;
    
    /**
     * Moves the drone backward (opposite to the direction it's facing).
     * 
     * <p>The drone will move straight back for the specified distance. This is
     * like moving forward, but in reverse. The drone doesn't turn around.</p>
     * 
     * @param distance how far to move
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @param speed how fast to move
     * @param speedUnit the unit for speed ("m/s", "cm/s", etc.)
     * @throws Exception if the movement fails or the parameters are invalid
     */
    void moveBackward(double distance, String unit, double speed, String speedUnit) throws Exception;
    
    /**
     * Moves the drone to the left (from its perspective).
     * 
     * <p>The drone will slide sideways to the left without rotating. Imagine
     * the drone is facing north - calling moveLeft will make it move west while
     * still facing north.</p>
     * 
     * @param distance how far to move
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @param speed how fast to move
     * @param speedUnit the unit for speed ("m/s", "cm/s", etc.)
     * @throws Exception if the movement fails or the parameters are invalid
     */
    void moveLeft(double distance, String unit, double speed, String speedUnit) throws Exception;
    
    /**
     * Moves the drone to the right (from its perspective).
     * 
     * <p>The drone will slide sideways to the right without rotating. This is
     * the opposite of moveLeft.</p>
     * 
     * @param distance how far to move
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @param speed how fast to move
     * @param speedUnit the unit for speed ("m/s", "cm/s", etc.)
     * @throws Exception if the movement fails or the parameters are invalid
     */
    void moveRight(double distance, String unit, double speed, String speedUnit) throws Exception;
    
    /**
     * Moves the drone upward (increases altitude).
     * 
     * <p>The drone will ascend straight up for the specified distance. Be careful
     * not to fly too high, especially indoors or near obstacles.</p>
     * 
     * @param distance how far to move upward
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @throws Exception if the movement fails or the drone reaches its maximum height
     */
    void moveUp(double distance, String unit) throws Exception;
    
    /**
     * Moves the drone downward (decreases altitude).
     * 
     * <p>The drone will descend straight down for the specified distance. Make sure
     * there's nothing below the drone before moving down.</p>
     * 
     * @param distance how far to move downward
     * @param unit the unit for distance ("m", "cm", "in", etc.)
     * @throws Exception if the movement fails or the drone reaches the ground
     */
    void moveDown(double distance, String unit) throws Exception;
    
    /**
     * Rotates the drone to the left (counterclockwise).
     * 
     * <p>The drone will spin in place to the left. If the drone is facing north,
     * calling rotateLeft(90) will make it face west. The drone stays in the same
     * position - it just changes direction.</p>
     * 
     * @param degrees how many degrees to rotate (0-360)
     * @throws Exception if the rotation fails or the angle is invalid
     */
    void rotateLeft(int degrees) throws Exception;
    
    /**
     * Rotates the drone to the right (clockwise).
     * 
     * <p>The drone will spin in place to the right. If the drone is facing north,
     * calling rotateRight(90) will make it face east.</p>
     * 
     * @param degrees how many degrees to rotate (0-360)
     * @throws Exception if the rotation fails or the angle is invalid
     */
    void rotateRight(int degrees) throws Exception;
    
    /**
     * Makes the drone hover in place for a certain amount of time.
     * 
     * <p>The drone will stay in its current position without moving. This is useful
     * when you want to pause between movements or wait for something to happen.</p>
     * 
     * @param durationSeconds how long to hover, in seconds
     * @throws Exception if hovering fails or the duration is invalid
     */
    void hover(double durationSeconds) throws Exception;
    
    // ========== Speed Control Methods ==========
    
    /**
     * Sets the drone's movement speed to an exact value.
     * 
     * <p>This method sets how fast the drone moves. Some drones let you set an
     * exact speed in meters per second or centimeters per second. After calling
     * this method, all movement commands will use this speed.</p>
     * 
     * <p><strong>Note:</strong> Not all drones support setting exact speeds.
     * Some drones only support speed levels (see setSpeedLevel).</p>
     * 
     * @param speed the desired speed value
     * @param unit the unit for speed ("m/s", "cm/s", etc.)
     * @throws Exception if the drone doesn't support exact speed control or
     *                   the speed value is invalid
     */
    void setSpeed(double speed, String unit) throws Exception;
    
    /**
     * Sets the drone's movement speed using a level system.
     * 
     * <p>This method sets how fast the drone moves using levels like 1, 2, 3, etc.
     * Some drones use levels instead of exact speeds. Level 1 might be slow, level 5
     * might be medium, and level 10 might be fast. Check your drone's documentation
     * for what levels mean.</p>
     * 
     * <p><strong>Note:</strong> Not all drones support speed levels.
     * Some drones only support exact speeds (see setSpeed).</p>
     * 
     * @param level the speed level (range depends on the drone, often 1-10)
     * @throws Exception if the drone doesn't support speed levels or
     *                   the level is out of range
     */
    void setSpeedLevel(int level) throws Exception;
    
    /**
     * Gets the drone's current movement speed.
     * 
     * <p>This method returns how fast the drone is currently set to move.</p>
     * 
     * @param unit the unit you want the speed in ("m/s", "cm/s", etc.)
     * @return the current speed in the requested unit
     * @throws Exception if the drone doesn't support speed reporting or doesn't
     *                   use exact speed values
     */
    double getSpeed(String unit) throws Exception;
    
    /**
     * Gets the drone's current speed level.
     * 
     * <p>This method returns the current speed level if the drone uses a level
     * system for speed control.</p>
     * 
     * @return the current speed level
     * @throws Exception if the drone doesn't support speed levels
     */
    int getSpeedLevel() throws Exception;
    
    // ========== Telemetry Methods ==========
    
    /**
     * Gets the drone's current battery level.
     * 
     * <p>Returns the battery percentage remaining. Always check the battery before
     * flying! If the battery gets too low, the drone might land automatically or
     * lose connection.</p>
     * 
     * @return the battery percentage (0-100)
     * @throws Exception if the battery level cannot be read
     */
    int getBattery() throws Exception;
    
    /**
     * Gets the drone's current height above the ground.
     * 
     * <p>This method returns how high the drone is flying. Most drones measure
     * height from where they took off, not from sea level.</p>
     * 
     * @param unit the unit you want the height in ("m", "cm", "in", etc.)
     * @return the current height in the requested unit
     * @throws Exception if the height cannot be read
     */
    double getHeight(String unit) throws Exception;
    
    /**
     * Gets the drone's current temperature.
     * 
     * <p>Returns the temperature of the drone's internal components. If the drone
     * gets too hot, it might shut down to protect itself.</p>
     * 
     * @return the temperature in degrees Celsius
     * @throws Exception if the temperature cannot be read
     */
    double getTemperature() throws Exception;
    
    /**
     * Gets the drone's current flight state.
     * 
     * <p>Returns a description of what the drone is doing right now. Examples:
     * "hovering", "flying", "landing", "on ground".</p>
     * 
     * @return a string describing the flight state
     * @throws Exception if the flight state cannot be read
     */
    String getFlightState() throws Exception;
    
    /**
     * Gets the drone's current movement state.
     * 
     * <p>Returns a description of how the drone is moving. Examples: "forward",
     * "rotating", "stationary", "backward".</p>
     * 
     * @return a string describing the movement state
     * @throws Exception if the movement state cannot be read
     */
    String getMovementState() throws Exception;
    
    // ========== Video and Camera Methods ==========
    
    /**
     * Starts the drone's video stream.
     * 
     * <p>If the drone has a camera, this method turns on the video feed so you
     * can see what the drone sees. Not all drones have cameras.</p>
     * 
     * @throws Exception if the drone doesn't have a camera or the video stream
     *                   cannot be started
     */
    void startVideo() throws Exception;
    
    /**
     * Stops the drone's video stream.
     * 
     * <p>Turns off the video feed from the drone's camera. This can help save
     * battery and bandwidth.</p>
     * 
     * @throws Exception if the video stream cannot be stopped
     */
    void stopVideo() throws Exception;
}
