package business;

import business.model.Competition;

import persistence.CompetitionCSVManager;
import persistence.CompetitionFileManager;

import java.util.ArrayList;

public class CompetitionManager {

    private CompetitionFileManager competitionFileManager;
    private CompetitionCSVManager competitionCSVManager;
    private GamesManager gamesManager;

    public CompetitionManager(
            CompetitionFileManager competitionFileManager,
            CompetitionCSVManager competitionCSVManager,
            GamesManager gamesManager
    ) {
        this.competitionFileManager = competitionFileManager;
        this.competitionCSVManager = competitionCSVManager;
        this.gamesManager = gamesManager;
    }

    public Competition getCompetition(String id) {
        // TODO: Implement getCompetition
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<Competition> getCompetitions() {
        // TODO: Implement getCompetitions
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<String> importCompetitions(String fileName) {
        // TODO: Implement importCompetitions
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean deleteCompetition(String id) {
        // TODO: Implement deleteCompetition
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
