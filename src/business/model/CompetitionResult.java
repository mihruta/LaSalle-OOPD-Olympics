package business.model;

public class CompetitionResult {

    private final String gold;
    private final String silver;
    private final String bronze;

    public CompetitionResult(String gold, String silver, String bronze) {
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
    }

    public String getGoldMedalist() {
        return this.gold;
    }

    public String getSilverMedalist() {
        return this.silver;
    }

    public String getBronzeMedalist() {
        return this.bronze;
    }
}
