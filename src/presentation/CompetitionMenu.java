package presentation;

import business.AthleteManager;
import business.CompetitionManager;
import business.DelegationManager;

import business.model.Athlete;
import business.model.Competition;
import business.model.CompetitionResult;
import business.model.CompetitionWeights;
import business.model.Delegation;
import business.model.CompetitionImportResult;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Handles the competitions submenu.
 */
public class CompetitionMenu {

    private final CompetitionManager competitionManager;
    private final AthleteManager athleteManager;
    private final DelegationManager delegationManager;
    private final ConsoleIO consoleIO;

    /**
     * Creates the competitions menu.
     *
     * @param competitionManager the competition business manager
     * @param athleteManager     the athlete business manager
     * @param delegationManager  the delegation business manager
     * @param consoleIO          the shared console input handler
     */
    public CompetitionMenu(CompetitionManager competitionManager, AthleteManager athleteManager, DelegationManager delegationManager, ConsoleIO consoleIO) {
        this.competitionManager = competitionManager;
        this.athleteManager = athleteManager;
        this.delegationManager = delegationManager;
        this.consoleIO = consoleIO;
    }

    /**
     * Runs the submenu until the user chooses to go back.
     */
    public void run() {
        int option;

        do {
            System.out.println();
            System.out.println("Competitions submenu:");
            System.out.println();
            System.out.println("\t1) Load new competitions");
            System.out.println("\t2) List available competitions");
            System.out.println("\t3) Delete a competition");
            System.out.println();
            System.out.println("\t0) Back");
            System.out.println();
            System.out.print("Choose an option: ");

            option = consoleIO.readOption();

            try {
                switch (option) {
                    case 1:
                        loadCompetitions();
                        break;

                    case 2:
                        showCompetitions();
                        break;

                    case 3:
                        deleteCompetition();
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Invalid option. Please choose a number between 0 and 3.");
                        break;
                }
            } catch (DataAccessException | InvalidDataException e) {
                System.out.println("Error: " + e.getMessage());
                consoleIO.waitForEnter();
            }
        } while (option != 0);
    }

    /**
     * Lists competitions and displays the selected competition.
     *
     * @throws DataAccessException  if the data cannot be read
     * @throws InvalidDataException if the stored data is invalid
     */
    private void showCompetitions() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("Available competitions");
        System.out.println("----------------------");
        System.out.println();

        Competition competition = selectCompetition();
        if (competition == null) {
            return;
        }

        showCompetitionDetails(competition);
        consoleIO.waitForEnter();
    }

    /**
     * Lists competitions and lets the user select one.
     *
     * @return the selected competition, or null if none is selected
     * @throws DataAccessException  if the competitions cannot be read
     * @throws InvalidDataException if the stored competitions are invalid
     */
    private Competition selectCompetition() throws DataAccessException, InvalidDataException {

        ArrayList<Competition> competitions = competitionManager.getCompetitions();

        if (competitions.isEmpty()) {
            System.out.println("No competitions are available.");
            consoleIO.waitForEnter();
            return null;
        }

        for (int i = 0; i < competitions.size(); i++) {
            Competition competition = competitions.get(i);

            System.out.printf("\t%d) %s - %s\n", i + 1, competition.getName(), competition.getFormat());
        }

        System.out.println();
        System.out.println("\t0) Back");
        System.out.println();

        int option;

        do {
            System.out.print("Choose a competition: ");
            option = consoleIO.readOption();

            if (option < 0 || option > competitions.size()) {
                System.out.println("Invalid competition number.");
            }
        } while (option < 0 || option > competitions.size());

        if (option == 0) {
            return null;
        }

        return competitions.get(option - 1);
    }

    /**
     * Shows a competition's information, results and participants.
     *
     * @param competition the selected competition
     * @throws DataAccessException  if the data cannot be read
     * @throws InvalidDataException if the data or references are invalid
     */
    private void showCompetitionDetails(Competition competition) throws DataAccessException, InvalidDataException {

        ArrayList<Athlete> athletes = athleteManager.searchAthletes("");

        ArrayList<Delegation> delegations = delegationManager.getDelegations();

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        System.out.println();
        System.out.println("Competition details");
        System.out.println("-------------------");
        System.out.println();

        System.out.printf("-| %s | %s |-\n", competition.getId(), competition.getName());

        System.out.println();
        System.out.printf("Format: %s\n", competition.getFormat());
        System.out.printf("Date and time: %s\n", formatter.format(competition.getDateTime()));

        System.out.println();
        System.out.println("Attribute weights");
        System.out.println("-----------------");

        CompetitionWeights weights = competition.getWeights();

        System.out.printf(Locale.ROOT, "Speed:      %.2f%n", weights.getSpeed());
        System.out.printf(Locale.ROOT, "Endurance:  %.2f%n", weights.getEndurance());
        System.out.printf(Locale.ROOT, "Strength:   %.2f%n", weights.getStrength());
        System.out.printf(Locale.ROOT, "Technique:  %.2f%n", weights.getTechnique());
        System.out.printf(Locale.ROOT, "Precision:  %.2f%n", weights.getPrecision());

        System.out.println();
        System.out.println("Competition result");
        System.out.println("------------------");

        showCompetitionResults(competition.getResults(), athletes, delegations);

        System.out.println();
        System.out.println("Participants");
        System.out.println("------------");
        System.out.println();

        ArrayList<String> athleteIds = competition.getAthleteIds();

        for (int i = 0; i < athleteIds.size(); i++) {
            String label = getAthleteLabel(athleteIds.get(i), athletes, delegations);

            System.out.printf("\t%d) %s\n", i + 1, label);
        }
    }

    /**
     * Shows the medal winners or the absence of results.
     *
     * @param result      the saved result, or null if not simulated
     * @param athletes    the available athletes
     * @param delegations the available delegations
     * @throws InvalidDataException if a winner or delegation is missing
     */
    private void showCompetitionResults(CompetitionResult result, ArrayList<Athlete> athletes, ArrayList<Delegation> delegations) throws InvalidDataException {

        if (result == null) {
            System.out.println("This competition has not been simulated yet.");
            return;
        }

        String gold = getAthleteLabel(result.getGoldMedalist(), athletes, delegations);
        String silver = getAthleteLabel(result.getSilverMedalist(), athletes, delegations);
        String bronze = getAthleteLabel(result.getBronzeMedalist(), athletes, delegations);

        System.out.printf("Gold:   %s\n", gold);
        System.out.printf("Silver: %s\n", silver);
        System.out.printf("Bronze: %s\n", bronze);
    }

    /**
     * Builds an athlete's name and delegation label.
     *
     * @param id          the athlete identifier
     * @param athletes    the available athletes
     * @param delegations the available delegations
     * @return the athlete name followed by the delegation name
     * @throws InvalidDataException if the athlete or delegation is missing
     */
    private String getAthleteLabel(String id, ArrayList<Athlete> athletes, ArrayList<Delegation> delegations) throws InvalidDataException {

        Athlete selectedAthlete = null;
        for (Athlete athlete : athletes) {
            if (athlete.getId().equals(id)) {
                selectedAthlete = athlete;
                break;
            }
        }

        if (selectedAthlete == null) {
            throw new InvalidDataException("Athlete \"" + id + "\" does not exist.");
        }

        for (Delegation delegation : delegations) {
            if (delegation.getCode().equals(selectedAthlete.getDelegationCode())) {
                return selectedAthlete.getName() + " - " + delegation.getName();
            }
        }

        throw new InvalidDataException("Delegation \"" + selectedAthlete.getDelegationCode() + "\" does not exist.");
    }

    /**
     * Deletes the selected competition after confirmation.
     *
     * @throws DataAccessException  if the data cannot be read or saved
     * @throws InvalidDataException if the stored data is invalid
     */
    private void deleteCompetition() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("Delete a competition");
        System.out.println("--------------------");
        System.out.println();

        Competition competition = selectCompetition();

        if (competition == null) {
            return;
        }

        showCompetitionDetails(competition);

        System.out.println();
        System.out.println("Deleting this competition will also remove its results and update any related");
        System.out.println("information stored in the system.");
        System.out.println();

        boolean confirmed = consoleIO.confirmAction("Are you sure you want to delete this competition?");

        if (!confirmed) {
            System.out.println();
            System.out.println("Deletion cancelled.");
            consoleIO.waitForEnter();
            return;
        }

        boolean deleted = competitionManager.deleteCompetition(competition.getId());

        System.out.println();

        if (deleted) {
            System.out.println("The competition \"" + competition.getName() + "\" has been removed from the system.");
        } else {
            System.out.println("Error: The competition no longer exists in the system.");
        }

        consoleIO.waitForEnter();
    }

    /**
     * Imports competitions from the selected CSV file.
     *
     * @throws DataAccessException  if the data cannot be read or saved
     * @throws InvalidDataException if the stored data or CSV header is invalid
     */
    private void loadCompetitions() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("Load new competitions");
        System.out.println("---------------------");
        System.out.println();

        System.out.print("Enter the name of the CSV file: ");
        String fileName = consoleIO.readLine().trim();

        if (fileName.isEmpty()) {
            System.out.println("Error: The CSV file name cannot be empty.");
            consoleIO.waitForEnter();
            return;
        }

        System.out.println();
        System.out.println("Opening file \"" + fileName + "\"...");
        System.out.println("Validating competitions...");

        ArrayList<CompetitionImportResult> results = competitionManager.importCompetitions(fileName);

        if (results.isEmpty()) {
            System.out.println();
            System.out.println("No competitions were found in the CSV file.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                showImportResult(results.get(i), i + 1, results.size());
            }
        }

        consoleIO.waitForEnter();
    }

    /**
     * Shows the import result of one competition.
     *
     * @param result   the processed CSV row
     * @param position the competition number in the report
     * @param total    the total number of processed rows
     */
    private void showImportResult(CompetitionImportResult result, int position, int total) {
        String title = "Competition " + position + " of " + total;

        System.out.println(position == 1 ? "" : "\n");
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        System.out.println();

        String[] fields = result.getFields();

        if (fields.length == 10) {
            showImportFields(fields);
        } else {
            System.out.println("CSV line: " + result.getLineNumber());
        }

        System.out.println();

        if (result.hasErrors()) {
            System.out.println("Error: The competition could not be loaded.");

            for (String error : result.getErrors()) {
                System.out.println(error);
            }
        } else {
            System.out.println("Competition loaded successfully.");
        }
    }

    /**
     * Shows the ten CSV fields using the import report format.
     *
     * @param fields the parsed row containing exactly ten fields
     */
    private void showImportFields(String[] fields) {
        System.out.println("Identifier: " + fields[0]);
        System.out.println("Name: " + fields[2]);
        System.out.println("Format: " + fields[1]);
        System.out.println("Start date and time: " + fields[3]);

        String[] weights = new String[5];

        for (int i = 0; i < weights.length; i++) {
            weights[i] = formatImportWeight(fields[i + 4]);
        }

        System.out.println("Weights: " + String.join(", ", weights));

        String[] participants = fields[9].split("\\|", -1);

        for (int i = 0; i < participants.length; i++) {
            participants[i] = participants[i].trim();
        }

        System.out.println("Athletes: " + String.join(", ", participants));
    }

    /**
     * Formats a numeric weight for display.
     *
     * @param value the original CSV value
     * @return the formatted weight, or the original invalid value
     */
    private String formatImportWeight(String value) {
        double weight;

        try {
            weight = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return value;
        }

        if (!Double.isFinite(weight)) {
            return value;
        }

        return String.format(Locale.ROOT, "%.2f", weight);
    }
}