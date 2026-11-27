package business.model.profile;

import business.model.Athlete;
import business.model.AthleteAttributes;

/**
 * Uses the athlete's base attributes without changes.
 */
public class BalancedBehavior extends AthleteBehavior {

    /**
     * Copies the athlete base attributes for a competition.
     *
     * @param athlete the athlete participating in the competition
     * @return the unchanged attributes
     */
    @Override
    public AthleteAttributes calculateAttributes(Athlete athlete) {
        return new AthleteAttributes(
                athlete.getSpeed(),
                athlete.getEndurance(),
                athlete.getStrength(),
                athlete.getTechnique(),
                athlete.getPrecision()
        );
    }
}