package business.model;

import java.util.ArrayList;

/**
 * Stores the full simulation report of one competition.
 */
public class SimulationReport {

    private final ArrayList<AthleteResult> athleteResults;
    private final CompetitionResult medalists;

    /**
     * Creates a competition simulation report.
     *
     * @param athleteResults the athlete results ordered by time
     * @param medalists the gold, silver and bronze medal winners
     */
    public SimulationReport(
            ArrayList<AthleteResult> athleteResults,
            CompetitionResult medalists
    ) {
        this.athleteResults = athleteResults;
        this.medalists = medalists;
    }

    /**
     * Returns the athlete results ordered by time.
     *
     * @return the athlete results ordered by time
     */
    public ArrayList<AthleteResult> getAthleteResults() {
        return athleteResults;
    }

    /**
     * Returns the medal winners.
     *
     * @return the gold, silver and bronze medal winners
     */
    public CompetitionResult getMedalists() {
        return medalists;
    }
}