package business.model;

public class CompetitionWeights {

    private double speed;
    private double endurance;
    private double strength;
    private double technique;
    private double precision;

    public CompetitionWeights(double speed, double endurance, double strength, double technique, double precision) {
        this.speed = speed;
        this.endurance = endurance;
        this.strength = strength;
        this.technique = technique;
        this.precision = precision;
    }

    public double getSpeed() {
        return this.speed;
    }

    public double getEndurance() {
        return this.endurance;
    }

    public double getStrength() {
        return this.strength;
    }

    public double getTechnique() {
        return this.technique;
    }

    public double getPrecision() {
        return this.precision;
    }

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
