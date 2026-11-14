package business.model;

import java.util.ArrayList;
import java.util.Date;

public class Competition {

    private String id;
    private String name;
    private CompetitionFormat format;
    private Date startTime;
    private CompetitionWeights weights;
    private ArrayList<String> athleteIds;
    private CompetitionResult results;

    public Competition(
            String id, String name, CompetitionFormat format,
            Date startTime, CompetitionWeights weights,
            ArrayList<String> athleteIds
    ) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.startTime = startTime;
        this.weights = weights;
        this.athleteIds = athleteIds;
        this.results = null;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public CompetitionFormat getFormat() {
        return this.format;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public CompetitionWeights getWeights() {
        return this.weights;
    }

    public ArrayList<String> getAthleteIds() {
        return this.athleteIds;
    }

    public CompetitionResult getResults() {
        return this.results;
    }

    public void setResults(CompetitionResult result) {
        this.results = result;
    }

    public void clearResults() {
        this.results = null;
    }
}
