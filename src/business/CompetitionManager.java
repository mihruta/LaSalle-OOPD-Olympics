package business;

import business.model.Competition;
import business.model.CompetitionImportResult;

import persistence.CompetitionCSVManager;
import persistence.CompetitionFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

public class CompetitionManager {

    private final CompetitionFileManager competitionFileManager;
    private final CompetitionCSVManager competitionCSVManager;
    private final GamesManager gamesManager;

    public CompetitionManager(
            CompetitionFileManager competitionFileManager,
            CompetitionCSVManager competitionCSVManager,
            GamesManager gamesManager
    ) {
        this.competitionFileManager = competitionFileManager;
        this.competitionCSVManager = competitionCSVManager;
        this.gamesManager = gamesManager;
    }

    public Competition getCompetition(String id)
            throws InvalidDataException, DataAccessException {

        ArrayList<Competition> competitions = getCompetitions();

        for (Competition competition : competitions) {
            if (competition.getId().equals(id)) {
                return competition;
            }
        }

        return null;
    }

    public ArrayList<Competition> getCompetitions()
            throws InvalidDataException, DataAccessException {

        return competitionFileManager.loadCompetitions();
    }

    /**
     * Imports competitions from a CSV file.
     *
     * @param fileName the path to the CSV file
     * @return the result for each processed CSV row
     * @throws DataAccessException if the data cannot be read or saved
     * @throws InvalidDataException if the stored data or CSV header is invalid
     */
    public ArrayList<CompetitionImportResult> importCompetitions(String fileName) throws InvalidDataException, DataAccessException {
        return competitionCSVManager.importFile(fileName);
    }

    public boolean deleteCompetition(String id)
            throws InvalidDataException, DataAccessException {

        Competition competitionToDelete = getCompetition(id);

        if (competitionToDelete == null) {
            return false;
        }

        gamesManager.resetSimulation();

        ArrayList<Competition> competitions = getCompetitions();

        for (int i = 0; i < competitions.size(); i++) {
            if (competitions.get(i).getId().equals(id)) {
                competitions.remove(i);
                break;
            }
        }

        competitionFileManager.saveCompetitions(competitions);

        return true;
    }
}
