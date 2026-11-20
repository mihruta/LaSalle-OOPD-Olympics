package business;

import business.model.Athlete;
import business.model.AthleteProfile;
import business.model.Competition;
import business.model.Delegation;

import persistence.AthleteFileManager;
import persistence.CompetitionFileManager;
import persistence.DelegationFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Manages athletes in the Olympic Games.
 * Handles registration, searching, and deletion.
 */
public class AthleteManager {

    private final AthleteFileManager athleteFileManager;
    private final DelegationFileManager delegationFileManager;
    private final CompetitionFileManager competitionFileManager;
    private final GamesManager gamesManager;

    /**
     * Creates an athlete manager with the required file managers.
     *
     * @param athleteFileManager the manager for athlete files
     * @param delegationFileManager the manager for delegation files
     * @param competitionFileManager the manager for competition files
     * @param gamesManager the manager for game simulations
     */
    public AthleteManager(
            AthleteFileManager athleteFileManager, DelegationFileManager delegationFileManager,
            CompetitionFileManager competitionFileManager, GamesManager gamesManager
    ) {
        this.athleteFileManager = athleteFileManager;
        this.delegationFileManager = delegationFileManager;
        this.competitionFileManager = competitionFileManager;
        this.gamesManager = gamesManager;
    }

    /**
     * Creates a new athlete with a unique ID and random attributes.
     *
     * @param name the athlete's name
     * @param birthDate the athlete's date of birth
     * @param delegationCode the athlete's delegation code
     * @param profile the athlete's profile
     * @throws InvalidDataException if the delegation does not exist or the athlete already exists
     * @throws DataAccessException if the files cannot be read or saved
     */
    public void createAthlete(String name, Date birthDate, String delegationCode, AthleteProfile profile)
            throws InvalidDataException, DataAccessException {

        if (!isDelegationExist(delegationCode)) {
            throw new InvalidDataException("Delegation does not exist.");
        }

        if (isAthleteDuplicate(name, delegationCode)) {
            throw new InvalidDataException("Athlete already exists in this delegation.");
        }

        String newId = generateAthleteId();

        int speed = generateRandomAttribute();
        int endurance = generateRandomAttribute();
        int strength = generateRandomAttribute();
        int technique = generateRandomAttribute();
        int precision = generateRandomAttribute();

        Athlete newAthlete = new Athlete(
                newId, name, birthDate, delegationCode, profile,
                speed, endurance, strength, technique, precision
        );

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();

        athletes.add(newAthlete);
        athleteFileManager.saveAthletes(athletes);
    }

    /**
     * Searches for athletes by name, ignoring uppercase and lowercase letters.
     *
     * @param query the name or part of the name to search for
     * @return the list of matching athletes
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the athlete file cannot be read
     */
    public ArrayList<Athlete> searchAthletes(String query)
            throws InvalidDataException, DataAccessException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
        ArrayList<Athlete> result = new ArrayList<>();

        for (Athlete athlete : athletes) {
            if (athlete.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(athlete);
            }
        }

        return result;
    }

    /**
     * Deletes an athlete by ID.
     * Also updates competitions and resets results if needed.
     *
     * @param id the ID of the athlete to delete
     * @return true if the athlete was deleted, false if not found
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the files cannot be read or saved
     */
    public boolean deleteAthlete(String id)
            throws InvalidDataException, DataAccessException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();

        Athlete athleteToDelete = null;

        for (Athlete athlete : athletes) {
            if (athlete.getId().equals(id)) {
                athleteToDelete = athlete;
                break;
            }
        }

        if (athleteToDelete == null) {
            return false;
        }

        ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();

        boolean changed = false;

        for (Competition competition : competitions) {
            if (competition.getAthleteIds().contains(id)) {
                changed = true;
                break;
            }
        }

        if (changed) {
            gamesManager.resetSimulation();

            competitions = competitionFileManager.loadCompetitions();

            for (int i = competitions.size() - 1; i >= 0; i--) {
                Competition competition = competitions.get(i);

                if (competition.getAthleteIds().remove(id)) {
                    if (competition.getAthleteIds().size() < 3) {
                        competitions.remove(i);
                    }
                }
            }

            competitionFileManager.saveCompetitions(competitions);
        }

        athletes.remove(athleteToDelete);
        athleteFileManager.saveAthletes(athletes);

        return true;
    }

    /**
     * Checks if an athlete with the same name exists in a delegation.
     *
     * @param name the athlete's name
     * @param delegationCode the delegation's code
     * @return true if the athlete already exists, false otherwise
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the athlete file cannot be read
     */
    private boolean isAthleteDuplicate(String name, String delegationCode)
            throws InvalidDataException, DataAccessException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();

        for (Athlete athlete : athletes) {
            if (athlete.getName().equalsIgnoreCase(name)
                    && athlete.getDelegationCode().equals(delegationCode)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Generates a new athlete ID using the highest existing ID.
     *
     * @return the new athlete ID
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the athlete file cannot be read
     */
    private String generateAthleteId()
            throws InvalidDataException, DataAccessException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();

        int maxId = 0;

        for (Athlete athlete : athletes) {
            String id = athlete.getId();
            int number = Integer.parseInt(id.substring(4));

            if (number > maxId) {
                maxId = number;
            }
        }
        return String.format("ATH-%03d", maxId + 1);
    }

    /**
     * Checks if a delegation with the given code exists.
     *
     * @param code the delegation's code
     * @return true if the delegation exists, false otherwise
     * @throws InvalidDataException if the stored data is invalid
     * @throws DataAccessException if the delegation file cannot be read
     */
    private boolean isDelegationExist(String code)
            throws InvalidDataException, DataAccessException {

        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        for (Delegation delegation : delegations) {
            if (delegation.getCode().equals(code)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Generates a random attribute value from 1 to 100.
     *
     * @return a random attribute value
     */
    private int generateRandomAttribute() {
        return (int) (Math.random() * 100) + 1;
    }
}
