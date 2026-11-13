package business.model;

public class Delegation {

    private String code;
    private String name;
    private int goldMedals;
    private int silverMedals;
    private int bronzeMedals;

    public Delegation(String code, String name, int goldMedals, int silverMedals, int bronzeMedals) {
        this.code = code;
        this.name = name;
        this.goldMedals = goldMedals;
        this.silverMedals = silverMedals;
        this.bronzeMedals = bronzeMedals;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getGoldMedals() {
        return this.goldMedals;
    }

    public int getSilverMedals() {
        return this.silverMedals;
    }

    public int getBronzeMedals() {
        return this.bronzeMedals;
    }

    public void addGoldMedals() {
        this.goldMedals += 1;
    }

    public void addSilverMedals() {
        this.silverMedals += 1;
    }

    public void addBronzeMedals() {
        this.bronzeMedals += 1;
    }

    public void resetMedals() {
        this.goldMedals = 0;
        this.silverMedals = 0;
        this.bronzeMedals = 0;
    }
}
