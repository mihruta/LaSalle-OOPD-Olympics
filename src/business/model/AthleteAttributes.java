package business.model;

/**
 * Stores the temporary athlete attributes used during a competition.
 */
public class AthleteAttributes {

    private final int speed;
    private final int endurance;
    private final int strength;
    private final int technique;
    private final int precision;

    /**
     * Creates the attributes used during a competition.
     *
     * @param speed the speed value
     * @param endurance the endurance value
     * @param strength the strength value
     * @param technique the technique value
     * @param precision the precision value
     */
    public AthleteAttributes(int speed, int endurance, int strength, int technique, int precision) {
        this.speed = speed;
        this.endurance = endurance;
        this.strength = strength;
        this.technique = technique;
        this.precision = precision;
    }

    /**
     * Returns the speed value.
     *
     * @return the speed value
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Returns the endurance value.
     *
     * @return the endurance value
     */
    public int getEndurance() {
        return this.endurance;
    }

    /**
     * Returns the strength value.
     *
     * @return the strength value
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Returns the technique value.
     *
     * @return the technique value
     */
    public int getTechnique() {
        return this.technique;
    }

    /**
     * Returns the precision value.
     *
     * @return the precision value
     */
    public int getPrecision() {
        return this.precision;
    }
}