package business.model.profile;

import business.model.Athlete;
import business.model.AthleteAttributes;

/**
 * Increases speed and strength while reducing endurance.
 */
public class PowerBehavior extends AthleteBehavior {

    /**
     * Applies the POWER modifiers to the athlete's base attributes.
     *
     * @param athlete the athlete participating in the competition
     * @return the attributes used during the competition
     */
    @Override
    public AthleteAttributes calculateAttributes(Athlete athlete) {
        return new AthleteAttributes(
                adjust(athlete.getSpeed(), 1.05),
                adjust(athlete.getEndurance(), 0.90),
                adjust(athlete.getStrength(), 1.15),
                athlete.getTechnique(),
                athlete.getPrecision()
        );
    }
}