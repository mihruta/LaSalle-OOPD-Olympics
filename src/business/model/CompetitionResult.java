package business.model;

public class CompetitionResult {

    private String goldMedalist;
    private String silverMedalist;
    private String bronzeMedalist;

    public CompetitionResult(String goldMedalist, String silverMedalist, String bronzeMedalist) {
        this.goldMedalist = goldMedalist;
        this.silverMedalist = silverMedalist;
        this.bronzeMedalist = bronzeMedalist;
    }

    public String getGoldMedalist() {
        return this.goldMedalist;
    }

    public String getSilverMedalist() {
        return this.silverMedalist;
    }

    public String getBronzeMedalist() {
        return this.bronzeMedalist;
    }
}
