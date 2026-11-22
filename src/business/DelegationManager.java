package business;

import business.model.Delegation;
import business.model.Athlete;

import persistence.DelegationFileManager;
import persistence.AthleteFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

/**
 * Manages delegations and their athletes.
 * Handles searching and sorting delegations.
 */
public class DelegationManager {

    private final DelegationFileManager delegationFileManager;
    private final AthleteFileManager athleteFileManager;

    /**
     * Creates a delegation manager with the required file managers.
     *
     * @param delegationFileManager the manager for delegation files
     * @param athleteFileManager the manager for athlete files
     */
    public DelegationManager(DelegationFileManager delegationFileManager, AthleteFileManager athleteFileManager) {
        this.delegationFileManager = delegationFileManager;
        this.athleteFileManager = athleteFileManager;
    }

    /**
     * Returns all delegations sorted by name.
     *
     * @return the list of delegations in alphabetical order
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the delegation file cannot be read
     */
    public ArrayList<Delegation> getDelegations() throws InvalidDataException, DataAccessException {

        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        delegations.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        return delegations;
    }

    /**
     * Returns all athletes belonging to a delegation.
     *
     * @param code the delegation's code
     * @return the list of athletes in the delegation
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the athlete file cannot be read
     */
    public ArrayList<Athlete> getAthletes(String code) throws InvalidDataException, DataAccessException {
        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
        ArrayList<Athlete> result = new ArrayList<>();

        for (Athlete athlete : athletes) {
            if (code.equals(athlete.getDelegationCode())) {
                result.add(athlete);
            }
        }
        return result;
    }

    /**
     * Returns delegations sorted by their medals.
     * Sorts by gold, silver, bronze, and then by name.
     *
     * @return the list of delegations sorted by medals
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the delegation file cannot be read
     */
    public ArrayList<Delegation> getDelegationsByMedals() throws InvalidDataException, DataAccessException {
        ArrayList<Delegation> delegations = getDelegations();

        delegations.sort((a, b) -> {
            if (a.getGoldMedals() != b.getGoldMedals()) {
                return Integer.compare(b.getGoldMedals(), a.getGoldMedals());
            }

            if (a.getSilverMedals() != b.getSilverMedals()) {
                return Integer.compare(b.getSilverMedals(), a.getSilverMedals());
            }

            if (a.getBronzeMedals() != b.getBronzeMedals()) {
                return Integer.compare(b.getBronzeMedals(), a.getBronzeMedals());
            }

            return a.getName().compareToIgnoreCase(b.getName());
        });

        return delegations;
    }
}
