package business.model.profile;

import business.model.Athlete;
import business.model.AthleteAttributes;
import business.model.AthleteProfile;

/**
 * Defines how an athlete profile calculates competition attributes.
 */
public abstract class AthleteBehavior {

    /**
     * Returns the behavior for the given athlete profile.
     *
     * @param profile the athlete's profile
     * @return the behavior for the profile
     */
    public static AthleteBehavior forProfile(AthleteProfile profile) {
        return switch (profile) {
            case BALANCED -> new BalancedBehavior();
            case POWER -> new PowerBehavior();
            case ENDURANCE -> new EnduranceBehavior();
            case TECHNICAL -> new TechnicalBehavior();
        };
    }

    /**
     * Calculates temporary attributes from the athlete's base attributes.
     *
     * @param athlete the athlete participating in a competition
     * @return the attributes used during the competition
     */
    public abstract AthleteAttributes calculateAttributes(Athlete athlete);

    /**
     * Applies a multiplier, rounds the result, and limits it to 1 through 100.
     *
     * @param value      the original attribute value
     * @param multiplier the multiplier applied to the value
     * @return the rounded attribute value between 1 and 100
     */
    protected int adjust(int value, double multiplier) {
        int percentage = (int) Math.round(multiplier * 100);
        int adjustedValue = (value * percentage + 50) / 100;

        if (adjustedValue > 100) {
            return 100;
        }

        if (adjustedValue < 1) {
            return 1;
        }

        return adjustedValue;
    }
}