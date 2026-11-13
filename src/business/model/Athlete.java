package business.model;

import java.util.Date;

public class Athlete {

    private String id;
    private String name;
    private Date birthDate;
    private String delegationCode;
    private AthleteProfile profile;
    private int speed;
    private int endurance;
    private int strength;
    private int technique;
    private int precision;

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

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public String getDelegationCode() {
        return this.delegationCode;
    }

    public AthleteProfile getProfile() {
        return this.profile;
    }

    public int getSpeed() {
        return this.speed;
    }

    public int getEndurance() {
        return this.endurance;
    }

    public int getStrength() {
        return this.strength;
    }

    public int getTechnique() {
        return this.technique;
    }

    public int getPrecision() {
        return this.precision;
    }
}
