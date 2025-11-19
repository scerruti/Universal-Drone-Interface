package org.universaldrone;

/**
 * Represents a drone's position in 3D space.
 * This class stores the x, y, and z coordinates along with the unit of measurement.
 * 
 * <p>The position can be used to track where a drone is located or to tell it
 * where to move. Think of x and y as the position on a flat map, and z as how
 * high the drone is in the air.</p>
 * 
 * @author Universal Drone Interface
 * @version 1.0
 */
public class Position {
    /**
     * The x-coordinate (horizontal position, left-right).
     */
    public double x;
    
    /**
     * The y-coordinate (horizontal position, forward-backward).
     */
    public double y;
    
    /**
     * The z-coordinate (vertical position, height).
     */
    public double z;
    
    /**
     * The unit of measurement for the coordinates.
     * Common values: "m" (meters), "cm" (centimeters), "in" (inches).
     */
    public String unit;
    
    /**
     * Creates a new Position with all coordinates set to zero.
     * The unit is set to meters ("m") by default.
     */
    public Position() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        this.unit = "m";
    }
    
    /**
     * Creates a new Position with the specified coordinates and unit.
     * 
     * @param x the x-coordinate (horizontal position, left-right)
     * @param y the y-coordinate (horizontal position, forward-backward)
     * @param z the z-coordinate (vertical position, height)
     * @param unit the unit of measurement (e.g., "m", "cm", "in")
     */
    public Position(double x, double y, double z, String unit) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.unit = unit;
    }
    
    /**
     * Returns a string representation of this position.
     * 
     * @return a string showing the x, y, z coordinates and unit
     */
    @Override
    public String toString() {
        return String.format("Position(x=%.2f, y=%.2f, z=%.2f, unit=%s)", x, y, z, unit);
    }
}
