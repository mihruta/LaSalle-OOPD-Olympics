package business.model;

/**
 * Stores an athlete's final result and individual performance details.
 */
public class AthleteResult {

    private final Athlete athlete;
    private final double result;
    private final double[] randomFactors;
    private final double[] individualResults;

    /**
     * Creates a result with the details of each score or attempt.
     *
     * @param athlete           the participating athlete
     * @param result            the final competition result
     * @param randomFactors     the random factor for each score or attempt
     * @param individualResults the individual scores or attempt results
     */
    public AthleteResult(Athlete athlete, double result, double[] randomFactors, double[] individualResults) {
        this.athlete = athlete;
        this.result = result;
        this.randomFactors = randomFactors.clone();
        this.individualResults = individualResults.clone();
    }

    /**
     * Returns the participating athlete.
     *
     * @return the participating athlete
     */
    public Athlete getAthlete() {
        return this.athlete;
    }

    /**
     * Returns the final competition result.
     *
     * @return the final result
     */
    public double getResult() {
        return this.result;
    }

    /**
     * Returns a copy of the random factors.
     *
     * @return the random factors in performance order
     */
    public double[] getRandomFactors() {
        return this.randomFactors.clone();
    }

    /**
     * Returns a copy of the individual results.
     *
     * @return the individual results in performance order
     */
    public double[] getIndividualResults() {
        return this.individualResults.clone();
    }
}