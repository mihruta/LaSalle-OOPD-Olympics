package business.model;

import java.util.ArrayList;
import java.util.Date;

public class Competition {

    private final String id;
    private final String name;
    private final CompetitionFormat format;
    private final Date dateTime;
    private final CompetitionWeights weights;
    private final ArrayList<String> athleteIds;
    private CompetitionResult results;

    public Competition(
            String id, String name, CompetitionFormat format,
            Date dateTime, CompetitionWeights weights,
            ArrayList<String> athleteIds
    ) {
        this.id = id;
        this.name = name;
        this.format = format;
        this.dateTime = dateTime;
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

    public Date getDateTime() {
        return this.dateTime;
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
