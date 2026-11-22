package presentation;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

/**
 * Handles the main menu and opens the application sections.
 */
public class Menu {

    private final DelegationMenu delegationMenu;
    private final AthleteMenu athleteMenu;
    private final CompetitionMenu competitionMenu;
    private final SimulationMenu simulationMenu;
    private final ConsoleIO consoleIO;

    /**
     * Creates the main menu.
     *
     * @param delegationMenu the delegations section
     * @param athleteMenu the athletes section
     * @param competitionMenu the competitions section
     * @param simulationMenu the simulation section
     * @param consoleIO the shared console input handler
     */
    public Menu(
            DelegationMenu delegationMenu,
            AthleteMenu athleteMenu,
            CompetitionMenu competitionMenu,
            SimulationMenu simulationMenu,
            ConsoleIO consoleIO
    ) {
        this.delegationMenu = delegationMenu;
        this.athleteMenu = athleteMenu;
        this.competitionMenu = competitionMenu;
        this.simulationMenu = simulationMenu;
        this.consoleIO = consoleIO;
    }

    /**
     * Runs the main menu until the user chooses to exit.
     */
    public void run() {
        int option;

        do {
            showMainMenu();
            option = consoleIO.readOption();

            switch (option) {
                case 1:
                    delegationMenu.run();
                    break;

                case 2:
                    athleteMenu.run();
                    break;

                case 3:
                    competitionMenu.run();
                    break;

                case 4:
                    simulationMenu.run();
                    break;

                case 0:
                    System.out.println("\nWe hope to see you again!");
                    break;

                default:
                    System.out.println("Invalid option. Please choose a number between 0 and 4.");
                    break;
            }
        } while (option != 0);
    }

    /**
     * Shows the main options and the simulation availability.
     */
    private void showMainMenu() {
        boolean simulationAvailable = false;

        try {
            simulationAvailable = simulationMenu.isAvailable();
        } catch (DataAccessException | InvalidDataException e) {
            System.out.println("Error: Cannot check simulation availability. " + e.getMessage());
        }

        System.out.println();
        System.out.println("What do you want to do:");
        System.out.println();
        System.out.println("\t1) Delegations");
        System.out.println("\t2) Athletes");
        System.out.println("\t3) Competitions");

        if (simulationAvailable) {
            System.out.println("\t4) Simulate the Games");
        } else {
            System.out.println("\t4) Simulate the Games (unavailable)");
        }

        System.out.println();
        System.out.println("\t0) Exit");
        System.out.println();
        System.out.print("Choose an option: ");
    }
}