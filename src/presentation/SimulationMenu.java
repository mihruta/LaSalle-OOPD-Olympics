package presentation;

import business.CompetitionManager;
import business.DelegationManager;
import business.GamesManager;

import business.model.Athlete;
import business.model.AthleteResult;
import business.model.Competition;
import business.model.CompetitionResult;
import business.model.Delegation;
import business.model.SimulationReport;
import business.model.CompetitionFormat;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Handles the Olympic Games simulation and displays its results.
 */
public class SimulationMenu {

    private final GamesManager gamesManager;
    private final CompetitionManager competitionManager;
    private final DelegationManager delegationManager;
    private final ConsoleIO consoleIO;
    private final MedalTableView medalTableView;

    /**
     * Creates the simulation menu.
     *
     * @param gamesManager       the simulation business manager
     * @param competitionManager the competition business manager
     * @param delegationManager  the delegation business manager
     * @param consoleIO          the shared console input handler
     * @param medalTableView     the shared medal table view
     */
    public SimulationMenu(
            GamesManager gamesManager,
            CompetitionManager competitionManager,
            DelegationManager delegationManager,
            ConsoleIO consoleIO,
            MedalTableView medalTableView
    ) {
        this.gamesManager = gamesManager;
        this.competitionManager = competitionManager;
        this.delegationManager = delegationManager;
        this.consoleIO = consoleIO;
        this.medalTableView = medalTableView;
    }

    /**
     * Checks whether there are competitions to simulate.
     *
     * @return true if at least one competition exists
     * @throws DataAccessException  if the competitions cannot be read
     * @throws InvalidDataException if the stored competitions are invalid
     */
    public boolean isAvailable() throws DataAccessException, InvalidDataException {
        return gamesManager.isAbleToSimulate();
    }

    /**
     * Starts a new simulation and displays each competition's results.
     */
    public void run() {
        try {
            if (!isAvailable()) {
                System.out.println("Error: There are no competitions to simulate.");
                consoleIO.waitForEnter();
                return;
            }

            ArrayList<Competition> competitions = competitionManager.getCompetitions();

            System.out.println();
            System.out.println("Simulate Olympic Games");
            System.out.println("----------------------");
            System.out.println("Starting Olympic Games simulation...\n");

            gamesManager.resetSimulation();

            for (int i = 0; i < competitions.size(); i++) {
                showCompetitionHeader(competitions.get(i), i + 1, competitions.size());

                SimulationReport report = gamesManager.simulateNextCompetition();

                if (report == null) {
                    throw new InvalidDataException("The simulation ended before all competitions were processed.");
                }

                showResults(report, competitions.get(i).getFormat());

                System.out.println();

                if (i < competitions.size() - 1) {
                    System.out.println("<Press ENTER to simulate the next competition>");
                } else {
                    System.out.println("<Press ENTER to finish the simulation>");
                }

                consoleIO.readLine();
            }

            ArrayList<Delegation> delegations = delegationManager.getDelegationsByMedals();

            System.out.println();
            System.out.println("Olympic Games simulation completed\n");
            System.out.println();
            System.out.println("Delegation medal classification");
            System.out.println("-------------------------------");

            medalTableView.show(delegations);
            consoleIO.waitForEnter();

        } catch (DataAccessException | InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
            consoleIO.waitForEnter();
        }
    }

    /**
     * Shows the competition information before simulation.
     *
     * @param competition the competition to simulate
     * @param position    the competition number
     * @param total       the total number of competitions
     */
    private void showCompetitionHeader(
            Competition competition,
            int position,
            int total
    ) {
        String title = "Competition " + position + " of " + total;

        System.out.println();
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        System.out.println();

        System.out.println("-| " + competition.getId() + " | " + competition.getName() + " |-");

        System.out.println();
        System.out.println("Format: " + competition.getFormat());
        System.out.println();
        System.out.println("Simulating competition...");
    }

    /**
     * Shows participant results and medal winners.
     *
     * @param report the report returned by the games manager
     * @param format the competition format
     * @throws DataAccessException if the delegations cannot be read
     * @throws InvalidDataException if a required reference is missing
     */
    private void showResults(SimulationReport report, CompetitionFormat format)
            throws DataAccessException, InvalidDataException {

        ArrayList<Delegation> delegations = delegationManager.getDelegations();
        ArrayList<AthleteResult> results = report.getAthleteResults();

        System.out.println();
        System.out.println("Results");
        System.out.println("-------");

        for (int i = 0; i < results.size(); i++) {
            AthleteResult result = results.get(i);

            String label = getAthleteLabel(
                    result.getAthlete().getId(),
                    results,
                    delegations
            );

            System.out.println();
            System.out.printf("\t%d) %s\n", i + 1, label);

            showPerformance(result, format);
        }

        showMedalResults(report, delegations);
    }

    /**
     * Shows the performance details for the competition format.
     *
     * @param result the athlete's simulation result
     * @param format the competition format
     */
    private void showPerformance(AthleteResult result, CompetitionFormat format) {
        double[] randomFactors = result.getRandomFactors();
        double[] individualResults = result.getIndividualResults();

        switch (format) {
            case TIMED -> {
                System.out.printf(Locale.ROOT, "\t   Luck: %.2f%n", randomFactors[0]);
                System.out.printf(Locale.ROOT, "\t   Time: %.2f seconds%n", result.getResult());
            }

            case SCORED -> {
                for (int i = 0; i < individualResults.length; i++) {
                    System.out.printf(Locale.ROOT, "\t   Judge %d: factor %.2f | Score: %.2f%n",
                            i + 1,
                            randomFactors[i],
                            individualResults[i]
                    );
                }

                System.out.printf(Locale.ROOT, "\t   Final average: %.2f%n", result.getResult());
            }

            case MEASURED -> {
                for (int i = 0; i < individualResults.length; i++) {
                    System.out.printf(Locale.ROOT, "\t   Attempt %d: luck %.2f | Result: %.2f%n",
                            i + 1,
                            randomFactors[i],
                            individualResults[i]
                    );
                }

                System.out.printf(Locale.ROOT, "\t   Best result: %.2f%n", result.getResult());
            }
        }
    }

    /**
     * Shows the medal winners from the simulation report.
     *
     * @param report      the competition simulation report
     * @param delegations the available delegations
     * @throws InvalidDataException if a winner or delegation is missing
     */
    private void showMedalResults(SimulationReport report, ArrayList<Delegation> delegations) throws InvalidDataException {

        CompetitionResult medalists = report.getMedalists();
        ArrayList<AthleteResult> results = report.getAthleteResults();

        String gold = getAthleteLabel(medalists.getGoldMedalist(), results, delegations);
        String silver = getAthleteLabel(medalists.getSilverMedalist(), results, delegations);
        String bronze = getAthleteLabel(medalists.getBronzeMedalist(), results, delegations);

        System.out.println();
        System.out.println("Medal results");
        System.out.println("-------------");
        System.out.println();

        System.out.println("\tGold:   " + gold);
        System.out.println("\tSilver: " + silver);
        System.out.println("\tBronze: " + bronze);
    }

    /**
     * Builds an athlete's name and delegation label.
     *
     * @param id          the athlete identifier
     * @param results     the participant results
     * @param delegations the available delegations
     * @return the athlete name followed by the delegation name
     * @throws InvalidDataException if the athlete or delegation is missing
     */
    private String getAthleteLabel(String id, ArrayList<AthleteResult> results, ArrayList<Delegation> delegations) throws InvalidDataException {

        Athlete selectedAthlete = null;

        for (AthleteResult result : results) {
            if (result.getAthlete().getId().equals(id)) {
                selectedAthlete = result.getAthlete();
                break;
            }
        }

        if (selectedAthlete == null) {
            throw new InvalidDataException("Athlete \"" + id + "\" is missing from the simulation report.");
        }

        for (Delegation delegation : delegations) {
            if (delegation.getCode().equals(
                    selectedAthlete.getDelegationCode()
            )) {
                return selectedAthlete.getName() + " - " + delegation.getName();
            }
        }

        throw new InvalidDataException("Delegation \"" + selectedAthlete.getDelegationCode() + "\" does not exist.");
    }
}