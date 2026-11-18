package business;

import business.model.Athlete;
import business.model.Competition;
import business.model.CompetitionResult;
import business.model.CompetitionWeights;
import business.model.Delegation;
import business.model.AthleteResult;
import business.model.SimulationReport;

import persistence.AthleteFileManager;
import persistence.CompetitionFileManager;
import persistence.DelegationFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

/**
 * Simulates competitions and updates delegation medals.
 */
public class GamesManager {

    private final DelegationFileManager delegationFileManager;
    private final AthleteFileManager athleteFileManager;
    private final CompetitionFileManager competitionFileManager;

    /**
     * Creates the games manager with the required file managers.
     *
     * @param delegationFileManager the manager used to load and save delegations
     * @param athleteFileManager the manager used to load athletes
     * @param competitionFileManager the manager used to load and save competitions
     */
    public GamesManager(
            DelegationFileManager delegationFileManager,
            AthleteFileManager athleteFileManager,
            CompetitionFileManager competitionFileManager
    ) {
        this.delegationFileManager = delegationFileManager;
        this.athleteFileManager = athleteFileManager;
        this.competitionFileManager = competitionFileManager;
    }

    /**
     * Checks if there are competitions to simulate.
     *
     * @return true if at least one competition exists
     * @throws DataAccessException if the competitions file cannot be read
     * @throws InvalidDataException if the competitions data is invalid
     */
    public boolean isAbleToSimulate() throws DataAccessException, InvalidDataException {
        ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();

        return !competitions.isEmpty();
    }

    /**
     * Simulates all competitions and updates delegation medals.
     *
     * @throws DataAccessException if the data cannot be read or saved
     * @throws InvalidDataException if there are no competitions or the stored data is invalid
     */
    public void simulateCompetitions() throws DataAccessException, InvalidDataException {

        ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();

        if (competitions.isEmpty()) {
            throw new InvalidDataException("There are no competitions to simulate.");
        }

        resetSimulation();

        SimulationReport report = simulateNextCompetition();

        while (report != null) {
            report = simulateNextCompetition();
        }
    }

    /**
     * Simulates the first competition without saved results.
     *
     * @return the simulation report, or null if no competitions remain to simulate
     * @throws DataAccessException if the data cannot be read or saved
     * @throws InvalidDataException if the data or participant references are invalid
     */
    public SimulationReport simulateNextCompetition() throws DataAccessException, InvalidDataException {

        ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();

        for (Competition competition : competitions) {
            if (competition.getResults() == null) {
                validateSimulationData(competitions);

                SimulationReport report = simulateCompetition(competition);

                CompetitionResult result = report.getMedalists();

                competition.setResults(result);

                updateMedals(result);

                competitionFileManager.saveCompetitions(competitions);

                return report;
            }
        }

        return null;
    }

    /**
     * Clears competition results and resets delegation medals.
     *
     * @throws DataAccessException if the data cannot be read or saved
     * @throws InvalidDataException if the stored data is invalid
     */
    public void resetSimulation() throws DataAccessException, InvalidDataException {

        ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();
        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        validateSimulationData(competitions);

        for (Competition competition : competitions) {
            competition.clearResults();
        }

        for (Delegation delegation : delegations) {
            delegation.resetMedals();
        }

        if (!competitions.isEmpty()) {
            competitionFileManager.saveCompetitions(competitions);
        }

        delegationFileManager.saveDelegations(delegations);
    }

    /**
     * Simulates one competition using the TIMED algorithm.
     *
     * @param competition the competition to simulate
     * @return the participant results and medal winners
     * @throws DataAccessException if the athletes file cannot be read
     * @throws InvalidDataException if the data or participants are invalid
     */
    private SimulationReport simulateCompetition(Competition competition) throws DataAccessException, InvalidDataException {

        if (competition.getAthleteIds().size() < 3) {
            throw new InvalidDataException("A competition must have at least 3 athletes.");
        }

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();

        ArrayList<AthleteResult> athleteResults = new ArrayList<>();

        for (String athleteId : competition.getAthleteIds()) {
            Athlete athlete = findAthlete(athleteId, athletes);

            double luck = Math.random();
            double time = calculateTime(athlete, competition.getWeights(), luck);

            AthleteResult athleteResult = new AthleteResult(athlete, luck, time);

            int position = 0;
            while (position < athleteResults.size() && time >= athleteResults.get(position).getTime()) {
                position++;
            }

            athleteResults.add(position, athleteResult);
        }

        CompetitionResult medalists = new CompetitionResult(
                athleteResults.get(0).getAthlete().getId(),
                athleteResults.get(1).getAthlete().getId(),
                athleteResults.get(2).getAthlete().getId()
        );

        return new SimulationReport(competition, athleteResults, medalists);
    }

    /**
     * Calculates an athlete's time using the competition weights and luck.
     *
     * @param athlete the athlete participating in the competition
     * @param weights the weights of the competition
     * @param luck the athlete's luck factor between 0.0 and 1.0
     * @return the calculated time
     */
    private double calculateTime(Athlete athlete, CompetitionWeights weights, double luck) {
        double weightedSum = (
                        (athlete.getSpeed() * weights.getSpeed())
                        + (athlete.getEndurance() * weights.getEndurance())
                        + (athlete.getStrength() * weights.getStrength())
                        + (athlete.getTechnique() * weights.getTechnique())
                        + (athlete.getPrecision() * weights.getPrecision())
        );

        return 1.0 - luck / 10.0 - weightedSum;
    }

    /**
     * Finds an athlete in the given list.
     *
     * @param id the athlete's identifier
     * @param athletes the loaded athletes
     * @return the athlete with the given identifier
     * @throws InvalidDataException if the athlete does not exist
     */
    private Athlete findAthlete(String id, ArrayList<Athlete> athletes) throws InvalidDataException {

        for (Athlete athlete : athletes) {
            if (athlete.getId().equals(id)) {
                return athlete;
            }
        }

        throw new InvalidDataException("Athlete \"" + id + "\" does not exist.");
    }

    /**
     * Finds a delegation in the given list.
     *
     * @param code the delegation's code
     * @param delegations the loaded delegations
     * @return the delegation with the given code
     * @throws InvalidDataException if the delegation does not exist
     */
    private Delegation findDelegation(String code, ArrayList<Delegation> delegations) throws InvalidDataException {

        for (Delegation delegation : delegations) {
            if (delegation.getCode().equals(code)) {
                return delegation;
            }
        }

        throw new InvalidDataException("Delegation \"" + code + "\" does not exist.");
    }

    /**
     * Checks that all participants and their delegations exist.
     *
     * @param competitions the competitions to simulate
     * @throws DataAccessException if the data cannot be read
     * @throws InvalidDataException if the data or references are invalid
     */
    private void validateSimulationData(ArrayList<Competition> competitions) throws DataAccessException, InvalidDataException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        for (Competition competition : competitions) {
            for (String athleteId : competition.getAthleteIds()) {
                Athlete athlete = findAthlete(athleteId, athletes);

                findDelegation(athlete.getDelegationCode(), delegations);
            }
        }
    }

    /**
     * Adds medals to the winners' delegations and saves them.
     *
     * @param result the medal winners of one competition
     * @throws DataAccessException if the data cannot be read or saved
     * @throws InvalidDataException if the data or winner references are invalid
     */
    private void updateMedals(CompetitionResult result) throws DataAccessException, InvalidDataException {

        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        Athlete goldAthlete = findAthlete(result.getGoldMedalist(), athletes);
        Athlete silverAthlete = findAthlete(result.getSilverMedalist(), athletes);
        Athlete bronzeAthlete = findAthlete(result.getBronzeMedalist(), athletes);

        Delegation goldDelegation = findDelegation(goldAthlete.getDelegationCode(), delegations);
        Delegation silverDelegation = findDelegation(silverAthlete.getDelegationCode(), delegations);
        Delegation bronzeDelegation = findDelegation(bronzeAthlete.getDelegationCode(), delegations);

        goldDelegation.addGoldMedals();
        silverDelegation.addSilverMedals();
        bronzeDelegation.addBronzeMedals();

        delegationFileManager.saveDelegations(delegations);
    }
}
