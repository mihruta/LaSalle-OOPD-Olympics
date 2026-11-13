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
        // TODO: Implement getId
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String getName() {
        // TODO: Implement getName
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CompetitionFormat getFormat() {
        // TODO: Implement getFormat
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Date getStartTime() {
        // TODO: Implement getStartTime
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CompetitionWeights getWeights() {
        // TODO: Implement getWeights
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<String> getAthleteIds() {
        // TODO: Implement getAthleteIds
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public CompetitionResult getResults() {
        // TODO: Implement getResults
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void setResults(CompetitionResult result) {
        // TODO: Implement setResults
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void clearResults() {
        // TODO: Implement clearResults
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
