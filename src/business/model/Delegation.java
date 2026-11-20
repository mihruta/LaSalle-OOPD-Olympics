package business.model;

/**
 * Represents a delegation taking part in the Olympic Games.
 * Stores its information and the number of medals won.
 */
public class Delegation {

    private final String code;
    private final String name;
    private int goldMedals;
    private int silverMedals;
    private int bronzeMedals;

    /**
     * Creates a delegation with its information and medals.
     *
     * @param code the delegation's code
     * @param name the delegation's name
     * @param goldMedals the number of gold medals
     * @param silverMedals the number of silver medals
     * @param bronzeMedals the number of bronze medals
     */
    public Delegation(String code, String name, int goldMedals, int silverMedals, int bronzeMedals) {
        this.code = code;
        this.name = name;
        this.goldMedals = goldMedals;
        this.silverMedals = silverMedals;
        this.bronzeMedals = bronzeMedals;
    }

    /**
     * Returns the delegation's code.
     *
     * @return the delegation's code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the delegation's name.
     *
     * @return the delegation's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the number of gold medals.
     *
     * @return the number of gold medals
     */
    public int getGoldMedals() {
        return this.goldMedals;
    }

    /**
     * Returns the number of silver medals.
     *
     * @return the number of silver medals
     */
    public int getSilverMedals() {
        return this.silverMedals;
    }

    /**
     * Returns the number of bronze medals.
     *
     * @return the number of bronze medals
     */
    public int getBronzeMedals() {
        return this.bronzeMedals;
    }

    /**
     * Adds one gold medal to the delegation.
     */
    public void addGoldMedals() {
        this.goldMedals += 1;
    }

    /**
     * Adds one silver medal to the delegation.
     */
    public void addSilverMedals() {
        this.silverMedals += 1;
    }

    /**
     * Adds one bronze medal to the delegation.
     */
    public void addBronzeMedals() {
        this.bronzeMedals += 1;
    }

    /**
     * Resets all medal counts to zero.
     */
    public void resetMedals() {
        this.goldMedals = 0;
        this.silverMedals = 0;
        this.bronzeMedals = 0;
    }
}
