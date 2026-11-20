package presentation;

import business.AthleteManager;
import business.DelegationManager;

import business.model.Athlete;
import business.model.AthleteProfile;
import business.model.Delegation;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

/**
 * Handles the athletes submenu.
 */
public class AthleteMenu {

    private final AthleteManager athleteManager;
    private final DelegationManager delegationManager;
    private final ConsoleIO consoleIO;

    /**
     * Creates the athletes menu.
     *
     * @param athleteManager    the athlete business manager
     * @param delegationManager the delegation business manager
     * @param consoleIO         the shared console input handler
     */
    public AthleteMenu(AthleteManager athleteManager, DelegationManager delegationManager, ConsoleIO consoleIO) {
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
            System.out.println("Athletes submenu:");
            System.out.println();
            System.out.println("\t1) Register a new athlete");
            System.out.println("\t2) List and search athletes");
            System.out.println("\t3) Delete an athlete");
            System.out.println();
            System.out.println("\t0) Back");
            System.out.println();
            System.out.print("Choose an option: ");

            option = consoleIO.readOption();

            try {
                switch (option) {
                    case 1:
                        registerAthlete();
                        break;

                    case 2:
                        showAthletes();
                        break;

                    case 3:
                        deleteAthlete();
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
     * Searches for athletes and displays the selected athlete.
     *
     * @throws DataAccessException  if the data cannot be read
     * @throws InvalidDataException if the stored data is invalid
     */
    private void showAthletes() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("List and search athletes");
        System.out.println("------------------------");
        System.out.println();

        Athlete athlete = selectAthlete();

        if (athlete == null) {
            return;
        }

        showAthleteDetails(athlete);
        consoleIO.waitForEnter();
    }

    /**
     * Searches for athletes and lets the user select one.
     *
     * @return the selected athlete, or null if none is selected
     * @throws DataAccessException  if the data cannot be read
     * @throws InvalidDataException if the stored data is invalid
     */
    private Athlete selectAthlete() throws DataAccessException, InvalidDataException {

        System.out.print("Enter a search term or press ENTER to show all athletes: ");

        String query = consoleIO.readLine().trim();
        ArrayList<Athlete> athletes = athleteManager.searchAthletes(query);

        String title;
        if (query.isEmpty()) {
            title = "All athletes";
        } else {
            title = "Search results for \"" + query + "\"";
        }

        System.out.println();
        System.out.println(title);
        System.out.println("-".repeat(title.length()));
        System.out.println();

        if (athletes.isEmpty()) {
            System.out.println("No athletes found.");
            consoleIO.waitForEnter();
            return null;
        }

        ArrayList<Delegation> delegations = delegationManager.getDelegations();
        for (int i = 0; i < athletes.size(); i++) {
            Athlete athlete = athletes.get(i);
            Delegation delegation = findDelegation(athlete.getDelegationCode(), delegations);

            System.out.printf("\t%d) %s - %s\n", i + 1, athlete.getName(), delegation.getName());
        }

        System.out.println();
        System.out.println("\t0) Back");
        System.out.println();

        int option;

        do {
            System.out.print("Choose an athlete: ");
            option = consoleIO.readOption();

            if (option < 0 || option > athletes.size()) {
                System.out.println("Invalid athlete number.");
            }
        } while (option < 0 || option > athletes.size());

        if (option == 0) {
            return null;
        }

        return athletes.get(option - 1);
    }

    /**
     * Finds a delegation in the given list.
     *
     * @param code        the delegation code
     * @param delegations the available delegations
     * @return the matching delegation
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
     * Shows an athlete's personal information and attributes.
     *
     * @param athlete the selected athlete
     * @throws DataAccessException  if the delegations cannot be read
     * @throws InvalidDataException if the stored data is invalid
     */
    private void showAthleteDetails(Athlete athlete) throws DataAccessException, InvalidDataException {
        ArrayList<Delegation> delegations = delegationManager.getDelegations();
        Delegation delegation = findDelegation(athlete.getDelegationCode(), delegations);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println();
        System.out.println("Athlete details");
        System.out.println("---------------");
        System.out.println();

        System.out.printf("-| %s | %s |-\n", athlete.getId(), athlete.getName());

        System.out.println();
        System.out.printf("Delegation: %s %s\n", delegation.getCode(), delegation.getName());
        System.out.printf("Date of birth: %s\n", formatter.format(athlete.getBirthDate()));
        System.out.printf("Profile: %s\n", getProfileName(athlete.getProfile()));

        System.out.println();
        System.out.println("Attributes");
        System.out.println("----------");

        showAthleteAttributes(athlete);
    }

    /**
     * Returns the profile name for display.
     *
     * @param profile the athlete profile
     * @return the profile name with an uppercase first letter
     */
    private String getProfileName(AthleteProfile profile) {
        String name = profile.name().toLowerCase(Locale.ROOT);
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }

    /**
     * Shows the five athlete attributes.
     *
     * @param athlete the athlete to display
     */
    private void showAthleteAttributes(Athlete athlete) {
        System.out.printf("Speed:      %d%n", athlete.getSpeed());
        System.out.printf("Endurance:  %d%n", athlete.getEndurance());
        System.out.printf("Strength:   %d%n", athlete.getStrength());
        System.out.printf("Technique:  %d%n", athlete.getTechnique());
        System.out.printf("Precision:  %d%n", athlete.getPrecision());
    }

    /**
     * Reads a valid birth date.
     *
     * @return the birth date entered by the user
     */
    private Date readBirthDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);

        while (true) {
            System.out.print("Date of birth (DD/MM/YYYY): ");
            String input = consoleIO.readLine().trim();

            try {
                Date birthDate = formatter.parse(input);

                if (!formatter.format(birthDate).equals(input)) {
                    throw new ParseException("Invalid date format.", 0);
                }

                return birthDate;
            } catch (ParseException e) {
                System.out.println();
                System.out.println("Error: The date of birth is not valid.");
                System.out.println("Please enter a valid date using the format DD/MM/YYYY.");
                System.out.println();
            }
        }
    }

    /**
     * Lets the user select a delegation for registration.
     *
     * @return the selected delegation, or null if cancelled or unavailable
     * @throws DataAccessException  if the delegations cannot be read
     * @throws InvalidDataException if the stored delegations are invalid
     */
    private Delegation selectDelegation() throws DataAccessException, InvalidDataException {
        ArrayList<Delegation> delegations = delegationManager.getDelegations();

        if (delegations.isEmpty()) {
            System.out.println("No delegations are available.");
            consoleIO.waitForEnter();
            return null;
        }

        System.out.println();
        System.out.println("Select the athlete's delegation:");
        System.out.println();

        for (int i = 0; i < delegations.size(); i++) {
            Delegation delegation = delegations.get(i);
            System.out.printf("\t%d) %s %s\n", i + 1, delegation.getCode(), delegation.getName());
        }

        System.out.println();
        System.out.println("\t0) Cancel");
        System.out.println();

        int option;

        do {
            System.out.print("Choose a delegation: ");
            option = consoleIO.readOption();

            if (option < 0 || option > delegations.size()) {
                System.out.println("Invalid delegation number.");
            }
        } while (option < 0 || option > delegations.size());

        if (option == 0) {
            return null;
        }

        return delegations.get(option - 1);
    }

    /**
     * Lets the user select an athlete profile.
     *
     * @return the selected profile, or null if cancelled
     */
    private AthleteProfile selectAthleteProfile() {
        AthleteProfile[] profiles = AthleteProfile.values();

        System.out.println();
        System.out.println("Select the athlete's profile:");
        System.out.println();

        for (int i = 0; i < profiles.length; i++) {
            System.out.printf("\t%d) %s\n", i + 1, getProfileName(profiles[i]));
        }

        System.out.println();
        System.out.println("\t0) Cancel");
        System.out.println();

        int option;

        do {
            System.out.print("Choose a profile: ");
            option = consoleIO.readOption();

            if (option < 0 || option > profiles.length) {
                System.out.println("Invalid profile number.");
            }
        } while (option < 0 || option > profiles.length);

        if (option == 0) {
            return null;
        }

        return profiles[option - 1];
    }

    /**
     * Shows the saved athlete after registration.
     *
     * @param athlete    the registered athlete
     * @param delegation the selected delegation
     */
    private void showRegistrationResult(Athlete athlete, Delegation delegation) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println();
        System.out.printf("-| %s | %s |-\n", athlete.getId(), athlete.getName());

        System.out.println();
        System.out.printf("Date of birth: %s\n", formatter.format(athlete.getBirthDate()));
        System.out.printf("Delegation: %s %s\n", delegation.getCode(), delegation.getName());
        System.out.printf("Profile: %s\n", getProfileName(athlete.getProfile()));

        System.out.println();
        System.out.println("Generated attributes");
        System.out.println("--------------------");

        showAthleteAttributes(athlete);

        System.out.println();
        System.out.println("The athlete has been stored successfully.");
    }

    /**
     * Registers a new athlete and displays the saved information.
     *
     * @throws DataAccessException  if the data cannot be read or saved
     * @throws InvalidDataException if registration or stored data is invalid
     */
    private void registerAthlete() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("Register a new athlete");
        System.out.println("----------------------");
        System.out.println();

        System.out.print("Athlete name: ");
        String name = consoleIO.readLine().trim();

        if (name.isEmpty()) {
            System.out.println("Error: The athlete name cannot be empty.");
            consoleIO.waitForEnter();
            return;
        }

        Date birthDate = readBirthDate();

        Delegation delegation = selectDelegation();

        if (delegation == null) {
            return;
        }

        System.out.println();
        System.out.println("Selected delegation:");
        System.out.printf("-| %s | %s |-\n", delegation.getCode(), delegation.getName());

        AthleteProfile profile = selectAthleteProfile();

        if (profile == null) {
            return;
        }

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        System.out.println();
        System.out.println("Athlete information");
        System.out.println("-------------------");
        System.out.println();

        System.out.println("Name: " + name);
        System.out.printf("Date of birth: %s\n", formatter.format(birthDate));
        System.out.printf("Delegation: %s %s\n", delegation.getCode(), delegation.getName());
        System.out.printf("Profile: %s\n", getProfileName(profile));

        System.out.println();
        System.out.println("Registering athlete...");

        athleteManager.createAthlete(name, birthDate, delegation.getCode(), profile);

        System.out.println();
        System.out.println("Athlete registered successfully.");

        ArrayList<Athlete> athletes = athleteManager.searchAthletes(name);
        for (Athlete athlete : athletes) {
            if (athlete.getName().equalsIgnoreCase(name) && athlete.getDelegationCode().equals(delegation.getCode())) {
                showRegistrationResult(athlete, delegation);
                consoleIO.waitForEnter();
                return;
            }
        }

        throw new InvalidDataException("The athlete was saved, but its details could not be found.");
    }

    /**
     * Deletes the selected athlete after confirmation.
     *
     * @throws DataAccessException  if the data cannot be read or saved
     * @throws InvalidDataException if the stored data is invalid
     */
    private void deleteAthlete() throws DataAccessException, InvalidDataException {

        System.out.println();
        System.out.println("Delete an athlete");
        System.out.println("-----------------");
        System.out.println();

        Athlete athlete = selectAthlete();

        if (athlete == null) {
            return;
        }

        showAthleteDetails(athlete);

        System.out.println();
        System.out.println("Warning: Deleting this athlete may also affect other information\nstored in the system.");
        System.out.println();

        boolean confirmed = consoleIO.confirmAction("Are you sure you want to delete this athlete?");

        if (!confirmed) {
            System.out.println();
            System.out.println("Deletion cancelled.");
            consoleIO.waitForEnter();
            return;
        }

        boolean deleted = athleteManager.deleteAthlete(athlete.getId());

        System.out.println();

        if (deleted) {
            System.out.printf("The athlete \"%s\" has been removed from the system.\n", athlete.getName());
        } else {
            System.out.println("Error: The athlete no longer exists in the system.");
        }

        consoleIO.waitForEnter();
    }
}