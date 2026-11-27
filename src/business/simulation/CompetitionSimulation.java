package business.simulation;

import business.model.Athlete;
import business.model.AthleteAttributes;
import business.model.AthleteResult;
import business.model.CompetitionFormat;
import business.model.CompetitionWeights;
import business.model.profile.AthleteBehavior;

/**
 * Defines the common operations for competition simulations.
 */
public abstract class CompetitionSimulation {

    /**
     * Returns the simulation strategy for the given format.
     *
     * @param format the competition format
     * @return the simulation strategy
     */
    public static CompetitionSimulation forFormat(CompetitionFormat format) {
        return switch (format) {
            case TIMED -> new TimedSimulation();
            case SCORED -> new ScoredSimulation();
            case MEASURED -> new MeasuredSimulation();
        };
    }

    /**
     * Simulates one athlete's performance.
     *
     * @param athlete the participating athlete
     * @param weights the competition attribute weights
     * @return the athlete's result and performance details
     */
    public abstract AthleteResult simulate(Athlete athlete, CompetitionWeights weights);

    /**
     * Calculates the weighted sum using effective athlete attributes.
     *
     * @param athlete the participating athlete
     * @param weights the competition attribute weights
     * @return the weighted sum of normalized attributes
     */
    protected double calculateWeightedSum(Athlete athlete, CompetitionWeights weights) {
        AthleteBehavior behavior = AthleteBehavior.forProfile(athlete.getProfile());
        AthleteAttributes attributes = behavior.calculateAttributes(athlete);

        return (attributes.getSpeed() / 100.0) * weights.getSpeed() +
                (attributes.getEndurance() / 100.0) * weights.getEndurance() +
                (attributes.getStrength() / 100.0) * weights.getStrength() +
                (attributes.getTechnique() / 100.0) * weights.getTechnique() +
                (attributes.getPrecision() / 100.0) * weights.getPrecision();
    }

    /**
     * Compares two results so that the better result comes first.
     *
     * @param first  the first athlete result
     * @param second the second athlete result
     * @return a negative value if the first result is better,
     * zero if equal, or a positive value if worse
     */
    public int compareResults(AthleteResult first, AthleteResult second) {
        if (higherIsBetter()) {
            return Double.compare(second.getResult(), first.getResult());
        }

        return Double.compare(first.getResult(), second.getResult());
    }

    /**
     * Defines whether a higher result is better.
     *
     * @return true for higher results, false for lower results
     */
    protected abstract boolean higherIsBetter();
}