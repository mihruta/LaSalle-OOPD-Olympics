package business.model;

/**
 * Stores the attribute weights used in a competition.
 */
public class CompetitionWeights {

    private final double speed;
    private final double endurance;
    private final double strength;
    private final double technique;
    private final double precision;

    /**
     * Creates the weights for the five athlete attributes.
     *
     * @param speed the weight of speed
     * @param endurance the weight of endurance
     * @param strength the weight of strength
     * @param technique the weight of technique
     * @param precision the weight of precision
     */
    public CompetitionWeights(double speed, double endurance, double strength, double technique, double precision) {
        this.speed = speed;
        this.endurance = endurance;
        this.strength = strength;
        this.technique = technique;
        this.precision = precision;
    }

    /**
     * Returns the speed weight.
     *
     * @return the speed weight
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Returns the endurance weight.
     *
     * @return the endurance weight
     */
    public double getEndurance() {
        return this.endurance;
    }

    /**
     * Returns the strength weight.
     *
     * @return the strength weight
     */
    public double getStrength() {
        return this.strength;
    }

    /**
     * Returns the technique weight.
     *
     * @return the technique weight
     */
    public double getTechnique() {
        return this.technique;
    }

    /**
     * Returns the precision weight.
     *
     * @return the precision weight
     */
    public double getPrecision() {
        return this.precision;
    }

    /**
     * Checks if all weights are between 0 and 1
     * and their sum is 1, allowing a small difference.
     *
     * @return true if the weights are valid, false otherwise
     */
    public boolean isValid() {
        double sum = this.speed + this.endurance + this.strength
                + this.technique + this.precision;

        double tolerance = 0.000001;

        if (this.speed >= 0.0 && this.speed <= 1.0
                && this.endurance >= 0.0 && this.endurance <= 1.0
                && this.strength >= 0.0 && this.strength <= 1.0
                && this.technique >= 0.0 && this.technique <= 1.0
                && this.precision >= 0.0 && this.precision <= 1.0
                && Math.abs(sum - 1.0) < tolerance) {
            return true;
        } else {
            return false;
        }
    }
}
