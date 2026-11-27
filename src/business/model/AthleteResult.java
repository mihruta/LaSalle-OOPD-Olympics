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
     * Creates a timed result for the existing simulation.
     *
     * @param athlete the participating athlete
     * @param luck    the luck used in the calculation
     * @param time    the calculated time
     */
    public AthleteResult(Athlete athlete, double luck, double time) {
        this(athlete, time, new double[]{luck}, new double[]{time});
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

    /**
     * Returns the luck for the existing timed simulation.
     *
     * @return the first random factor
     */
    public double getLuck() {
        return this.randomFactors[0];
    }

    /**
     * Returns the time for the existing timed simulation.
     *
     * @return the final result
     */
    public double getTime() {
        return this.result;
    }
}