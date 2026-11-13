package persistence;

import business.model.Competition;

import java.util.ArrayList;

public class CompetitionCSVManager {

    private AthleteFileManager athleteFileManager;
    private CompetitionFileManager competitionFileManager;

    public CompetitionCSVManager(
            AthleteFileManager athleteFileManager,
            CompetitionFileManager competitionFileManager
    ) {
        this.athleteFileManager = athleteFileManager;
        this.competitionFileManager = competitionFileManager;
    }

    public ArrayList<String> importFile(String fileName) {
        // TODO: Implement importFile
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private ArrayList<String> checkRow(String[] data) {
        // TODO: Implement checkRow
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Competition createCompetition(String[] data) {
        // TODO: Implement createCompetition
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
