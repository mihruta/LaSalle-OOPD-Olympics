package business;

import business.model.Competition;
import business.model.CompetitionImportResult;

import persistence.CompetitionCSVManager;
import persistence.CompetitionFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

/**
 * Manages competitions in the Olympic Games.
 * Handles loading, importing, and deleting competitions.
 */
public class CompetitionManager {

    private final CompetitionFileManager competitionFileManager;
    private final CompetitionCSVManager competitionCSVManager;
    private final GamesManager gamesManager;

    /**
     * Creates a competition manager with the required managers.
     *
     * @param competitionFileManager the manager for competition files
     * @param competitionCSVManager the manager for CSV imports
     * @param gamesManager the manager for game simulations
     */
    public CompetitionManager(
            CompetitionFileManager competitionFileManager,
            CompetitionCSVManager competitionCSVManager,
            GamesManager gamesManager
    ) {
        this.competitionFileManager = competitionFileManager;
        this.competitionCSVManager = competitionCSVManager;
        this.gamesManager = gamesManager;
    }

    /**
     * Finds a competition by its ID.
     *
     * @param id the competition's ID
     * @return the competition, or null if not found
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the competition file cannot be read
     */
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

    /**
     * Returns all competitions stored in the system.
     *
     * @return the list of competitions
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the competition file cannot be read
     */
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

    /**
     * Deletes a competition by its ID.
     * Resets simulation results before removing the competition.
     *
     * @param id the ID of the competition to delete
     * @return true if deleted, false if not found
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the files cannot be read or saved
     */
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
