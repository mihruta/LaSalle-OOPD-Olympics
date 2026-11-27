package business.simulation;

import business.model.Athlete;
import business.model.AthleteResult;
import business.model.CompetitionWeights;

/**
 * Simulates measured competitions using the best of three attempts.
 */
public class MeasuredSimulation extends CompetitionSimulation {

    /**
     * Calculates three attempts and selects the best result.
     *
     * @param athlete the participating athlete
     * @param weights the competition attribute weights
     * @return the best result and the details of each attempt
     */
    @Override
    public AthleteResult simulate(Athlete athlete, CompetitionWeights weights) {
        double weightedSum = calculateWeightedSum(athlete, weights);

        double[] randomFactors = new double[3];
        double[] individualResults = new double[3];
        double bestResult = 0.0;

        for (int i = 0; i < individualResults.length; i++) {
            double luck = Math.random();
            double score = 10.0 * luck * weightedSum;

            randomFactors[i] = luck;
            individualResults[i] = score;

            if (score > bestResult) {
                bestResult = score;
            }
        }

        return new AthleteResult(athlete, bestResult, randomFactors, individualResults);
    }

    /**
     * Indicates that a higher measured result is better.
     *
     * @return true because the highest result wins
     */
    @Override
    protected boolean higherIsBetter() {
        return true;
    }
}