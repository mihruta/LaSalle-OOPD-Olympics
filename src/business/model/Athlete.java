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
        // TODO: Implement getId
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        // TODO: Implement getName
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Date getBirthDate() {
        // TODO: Implement getBirthDate
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getDelegationCode() {
        // TODO: Implement getDelegationCode
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public AthleteProfile getProfile() {
        // TODO: Implement getProfile
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getSpeed() {
        // TODO: Implement getSpeed
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getEndurance() {
        // TODO: Implement getEndurance
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getStrength() {
        // TODO: Implement getStrength
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getTechnique() {
        // TODO: Implement getTechnique
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getPrecision() {
        // TODO: Implement getPrecision
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
