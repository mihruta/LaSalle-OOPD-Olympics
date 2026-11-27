package business.simulation;

import business.model.Athlete;
import business.model.AthleteResult;
import business.model.CompetitionWeights;

/**
 * Simulates scored competitions using five judge scores.
 */
public class ScoredSimulation extends CompetitionSimulation {

    /**
     * Calculates five judge scores and their average.
     *
     * @param athlete the participating athlete
     * @param weights the competition attribute weights
     * @return the average score and the details of each judge
     */
    @Override
    public AthleteResult simulate(Athlete athlete, CompetitionWeights weights) {
        double weightedSum = calculateWeightedSum(athlete, weights);

        double[] randomFactors = new double[5];
        double[] individualResults = new double[5];
        double total = 0.0;

        for (int i = 0; i < individualResults.length; i++) {
            double judge = 1.0 + Math.random() * 4.0;
            double score = judge + 5.0 * weightedSum;

            randomFactors[i] = judge;
            individualResults[i] = score;
            total += score;
        }

        double average = total / individualResults.length;

        return new AthleteResult(athlete, average, randomFactors, individualResults);
    }

    /**
     * Indicates that a higher average score is better.
     *
     * @return true because the highest score wins
     */
    @Override
    protected boolean higherIsBetter() {
        return true;
    }
}