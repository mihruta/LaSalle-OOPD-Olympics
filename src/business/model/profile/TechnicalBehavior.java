package business.model.profile;

import business.model.Athlete;
import business.model.AthleteAttributes;

/**
 * Increases technique and precision while reducing strength.
 */
public class TechnicalBehavior extends AthleteBehavior {

    /**
     * Applies the TECHNICAL modifiers to the athlete's base attributes.
     *
     * @param athlete the athlete participating in the competition
     * @return the attributes used during the competition
     */
    @Override
    public AthleteAttributes calculateAttributes(Athlete athlete) {
        return new AthleteAttributes(
                athlete.getSpeed(),
                athlete.getEndurance(),
                adjust(athlete.getStrength(), 0.95),
                adjust(athlete.getTechnique(), 1.15),
                adjust(athlete.getPrecision(), 1.10)
        );
    }
}