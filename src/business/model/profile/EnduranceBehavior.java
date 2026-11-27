package business.model.profile;

import business.model.Athlete;
import business.model.AthleteAttributes;

/**
 * Uses the highest physical attribute for endurance and strength,
 * and the lowest physical attribute for speed.
 */
public class EnduranceBehavior extends AthleteBehavior {

    /**
     * Applies the ENDURANCE rules to the athlete's base attributes.
     *
     * @param athlete the athlete participating in the competition
     * @return the attributes used during the competition
     */
    @Override
    public AthleteAttributes calculateAttributes(Athlete athlete) {
        int minimum = Math.min(athlete.getSpeed(), Math.min(athlete.getEndurance(), athlete.getStrength()));

        int maximum = Math.max(athlete.getSpeed(), Math.max(athlete.getEndurance(), athlete.getStrength()));

        return new AthleteAttributes(minimum, maximum, maximum, athlete.getTechnique(), athlete.getPrecision());
    }
}