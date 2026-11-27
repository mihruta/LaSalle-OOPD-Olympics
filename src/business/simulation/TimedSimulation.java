package business.simulation;

import business.model.Athlete;
import business.model.AthleteResult;
import business.model.CompetitionWeights;

/**
 * Simulates timed competitions where the lowest time wins.
 */
public class TimedSimulation extends CompetitionSimulation {

    /**
     * Calculates the athlete's time using effective attributes and luck.
     *
     * @param athlete the participating athlete
     * @param weights the competition attribute weights
     * @return the time and the luck used in the calculation
     */
    @Override
    public AthleteResult simulate(Athlete athlete, CompetitionWeights weights) {
        double weightedSum = calculateWeightedSum(athlete, weights);
        double luck = Math.random();
        double time = 1.0 - luck * weightedSum;

        return new AthleteResult(athlete, time, new double[]{luck}, new double[]{time});
    }

    /**
     * Indicates that a lower time is better.
     *
     * @return false because the lowest time wins
     */
    @Override
    protected boolean higherIsBetter() {
        return false;
    }
}