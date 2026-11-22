package business.model;

/**
 * Stores the medal winners of a competition.
 */
public class CompetitionResult {

    private final String gold;
    private final String silver;
    private final String bronze;

    /**
     * Creates a competition result with three medal winners.
     *
     * @param gold the ID of the gold medalist
     * @param silver the ID of the silver medalist
     * @param bronze the ID of the bronze medalist
     */
    public CompetitionResult(String gold, String silver, String bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    /**
     * Returns the gold medalist's ID.
     *
     * @return the gold medalist's ID
     */
    public String getGoldMedalist() {
        return this.gold;
    }

    /**
     * Returns the silver medalist's ID.
     *
     * @return the silver medalist's ID
     */
    public String getSilverMedalist() {
        return this.silver;
    }

    /**
     * Returns the bronze medalist's ID.
     *
     * @return the bronze medalist's ID
     */
    public String getBronzeMedalist() {
        return this.bronze;
    }
}
