package business.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a competition in the Olympic Games.
 * Stores competition details, participants, and results.
 */
public class Competition {

    private final String id;
    private final String name;
    private final CompetitionFormat format;
    private final Date dateTime;
    private final CompetitionWeights weights;
    private final ArrayList<String> athleteIds;
    private CompetitionResult results;

    /**
     * Creates a competition with its details and participants.
     *
     * @param id the competition's ID
     * @param name the competition's name
     * @param format the competition's format
     * @param dateTime the competition's date and time
     * @param weights the weights used in the simulation
     * @param athleteIds the IDs of participating athletes
     */
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

    /**
     * Returns the competition's ID.
     *
     * @return the competition's ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the competition's name.
     *
     * @return the competition's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the competition's format.
     *
     * @return the competition's format
     */
    public CompetitionFormat getFormat() {
        return this.format;
    }

    /**
     * Returns the competition's date and time.
     *
     * @return the competition's date and time
     */
    public Date getDateTime() {
        return this.dateTime;
    }

    /**
     * Returns the competition's attribute weights.
     *
     * @return the attribute weights
     */
    public CompetitionWeights getWeights() {
        return this.weights;
    }

    /**
     * Returns the IDs of participating athletes.
     *
     * @return the list of athlete IDs
     */
    public ArrayList<String> getAthleteIds() {
        return this.athleteIds;
    }

    /**
     * Returns the competition's results.
     *
     * @return the results, or null if not simulated
     */
    public CompetitionResult getResults() {
        return this.results;
    }

    /**
     * Sets the competition's results.
     *
     * @param result the competition results
     */
    public void setResults(CompetitionResult result) {
        this.results = result;
    }

    /**
     * Clears the competition's results.
     */
    public void clearResults() {
        this.results = null;
    }
}
