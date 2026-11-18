package business.model;

import java.util.ArrayList;

/**
 * Stores the full simulation report of one competition.
 */
public class SimulationReport {

    private final Competition competition;
    private final ArrayList<AthleteResult> athleteResults;
    private final CompetitionResult medalists;

    /**
     * Creates a competition simulation report.
     *
     * @param competition the simulated competition
     * @param athleteResults the athlete results ordered by time
     * @param medalists the gold, silver and bronze medal winners
     */
    public SimulationReport(
            Competition competition,
            ArrayList<AthleteResult> athleteResults,
            CompetitionResult medalists
    ) {
        this.competition = competition;
        this.athleteResults = athleteResults;
        this.medalists = medalists;
    }

    /**
     * Returns the simulated competition.
     *
     * @return the simulated competition
     */
    public Competition getCompetition() {
        return competition;
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