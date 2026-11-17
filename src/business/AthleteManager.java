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

public class AthleteManager {

    private final AthleteFileManager athleteFileManager;
    private final DelegationFileManager delegationFileManager;
    private final CompetitionFileManager competitionFileManager;
    private final GamesManager gamesManager;

    public AthleteManager(
            AthleteFileManager athleteFileManager, DelegationFileManager delegationFileManager,
            CompetitionFileManager competitionFileManager, GamesManager gamesManager
    ) {
        this.athleteFileManager = athleteFileManager;
        this.delegationFileManager = delegationFileManager;
        this.competitionFileManager = competitionFileManager;
        this.gamesManager = gamesManager;
    }

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

    private int generateRandomAttribute() {
        return (int) (Math.random() * 100) + 1;
    }
}
