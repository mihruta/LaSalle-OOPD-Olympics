package business.model;

import java.util.Date;

/**
 * Represents an athlete taking part in the Olympic Games.
 * Stores personal information, a profile, and five attributes.
 */
public class Athlete {

    private final String id;
    private final String name;
    private final Date birthDate;
    private final String delegationCode;
    private final AthleteProfile profile;
    private final int speed;
    private final int endurance;
    private final int strength;
    private final int technique;
    private final int precision;

    /**
     * Creates an athlete with personal information and attributes.
     *
     * @param id the athlete's ID
     * @param name the athlete's name
     * @param birthDate the athlete's date of birth
     * @param delegationCode the athlete's delegation code
     * @param profile the athlete's profile
     * @param speed the athlete's speed
     * @param endurance the athlete's endurance
     * @param strength the athlete's strength
     * @param technique the athlete's technique
     * @param precision the athlete's precision
     */
    public Athlete(
            String id, String name, Date birthDate, String delegationCode,
            AthleteProfile profile, int speed, int endurance,
            int strength, int technique, int precision
    ) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.delegationCode = delegationCode;
        this.profile = profile;
        this.speed = speed;
        this.endurance = endurance;
        this.strength = strength;
        this.technique = technique;
        this.precision = precision;
    }

    /**
     * Returns the athlete's ID.
     *
     * @return the athlete's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the athlete's name.
     *
     * @return the athlete's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the athlete's date of birth.
     *
     * @return the date of birth
     */
    public Date getBirthDate() {
        return this.birthDate;
    }

    /**
     * Returns the athlete's delegation code.
     *
     * @return the delegation code
     */
    public String getDelegationCode() {
        return this.delegationCode;
    }

    /**
     * Returns the athlete's profile.
     *
     * @return the athlete's profile
     */
    public AthleteProfile getProfile() {
        return this.profile;
    }

    /**
     * Returns the athlete's speed.
     *
     * @return the speed value
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Returns the athlete's endurance.
     *
     * @return the endurance value
     */
    public int getEndurance() {
        return this.endurance;
    }

    /**
     * Returns the athlete's strength.
     *
     * @return the strength value
     */
    public int getStrength() {
        return this.strength;
    }

    /**
     * Returns the athlete's technique.
     *
     * @return the technique value
     */
    public int getTechnique() {
        return this.technique;
    }

    /**
     * Returns the athlete's precision.
     *
     * @return the precision value
     */
    public int getPrecision() {
        return this.precision;
    }
}
