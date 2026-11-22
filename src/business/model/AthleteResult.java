package business.model;

/**
 * Stores one athlete's result in a simulation.
 */
public class AthleteResult {

    private final Athlete athlete;
    private final double luck;
    private final double time;

    /**
     * Creates an athlete's simulation result.
     *
     * @param athlete the participating athlete
     * @param luck the luck used in the calculation
     * @param time the calculated time
     */
    public AthleteResult(Athlete athlete, double luck, double time) {
        this.athlete = athlete;
        this.luck = luck;
        this.time = time;
    }

    /**
     * Returns the participating athlete.
     *
     * @return the participating athlete
     */
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * Returns the luck used in the calculation.
     *
     * @return the luck used in the calculation
     */
    public double getLuck() {
        return luck;
    }

    /**
     * Returns the calculated time.
     *
     * @return the calculated time
     */
    public double getTime() {
        return time;
    }
}