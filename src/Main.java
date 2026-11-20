import business.AthleteManager;
import business.CompetitionManager;
import business.DelegationManager;
import business.GamesManager;

import persistence.AthleteFileManager;
import persistence.CompetitionCSVManager;
import persistence.CompetitionFileManager;
import persistence.DelegationFileManager;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import presentation.AthleteMenu;
import presentation.CompetitionMenu;
import presentation.ConsoleIO;
import presentation.DelegationMenu;
import presentation.MedalTableView;
import presentation.Menu;
import presentation.SimulationMenu;

/**
 * LA28 Games Lab - Olympic Games Simulator
 *
 * This project was developed with a lot of effort, dedication,
 * and teamwork. We worked hard to make the application complete,
 * well-organized, and easy to understand.
 *
 * We also wanted to follow a real team development process.
 * We planned and divided our work into GitHub issues, assigned
 * tasks, used separate branches, and reviewed each other's
 * pull requests before merging our changes.
 *
 * Our GitHub repository shows the development process,
 * including the tasks, contributions, and project history.
 * We hope it reflects the effort we put into this project.
 *
 * GitHub Repository:
 * https://github.com/mihruta/LaSalle-OOPD-Olympics
 *
 * This class initializes and starts the application.
 */
public class Main {

    /**
     * Checks the required data and starts the main menu.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        showWelcome();

        System.out.println("Verifying local files...");

        DelegationFileManager delegationFileManager = new DelegationFileManager();

        try {
            delegationFileManager.loadDelegations();

        } catch (DataAccessException e) {
            System.out.println();
            System.out.println("Error: The delegations.json file can’t be accessed.");
            System.out.println("Shutting down...");
            return;

        } catch (InvalidDataException e) {
            System.out.println();
            System.out.println("Error: The delegations.json file contains invalid data.");
            System.out.println("Shutting down...");
            return;
        }

        System.out.println("Files OK.");
        System.out.println("Starting program...");

        AthleteFileManager athleteFileManager = new AthleteFileManager();
        CompetitionFileManager competitionFileManager = new CompetitionFileManager();

        CompetitionCSVManager competitionCSVManager = new CompetitionCSVManager(
                athleteFileManager,
                competitionFileManager
        );

        GamesManager gamesManager = new GamesManager(
                delegationFileManager,
                athleteFileManager,
                competitionFileManager
        );

        DelegationManager delegationManager = new DelegationManager(
                delegationFileManager,
                athleteFileManager
        );

        AthleteManager athleteManager = new AthleteManager(
                athleteFileManager,
                delegationFileManager,
                competitionFileManager,
                gamesManager
        );

        CompetitionManager competitionManager = new CompetitionManager(
                competitionFileManager,
                competitionCSVManager,
                gamesManager
        );

        ConsoleIO consoleIO = new ConsoleIO();
        MedalTableView medalTableView = new MedalTableView();

        DelegationMenu delegationMenu = new DelegationMenu(
                delegationManager,
                consoleIO,
                medalTableView
        );

        AthleteMenu athleteMenu = new AthleteMenu(
                athleteManager,
                delegationManager,
                consoleIO
        );

        CompetitionMenu competitionMenu = new CompetitionMenu(
                competitionManager,
                athleteManager,
                delegationManager,
                consoleIO
        );

        SimulationMenu simulationMenu = new SimulationMenu(
                gamesManager,
                competitionManager,
                delegationManager,
                consoleIO,
                medalTableView
        );
        Menu menu = new Menu(
                delegationMenu,
                athleteMenu,
                competitionMenu,
                simulationMenu,
                consoleIO
        );

        menu.run();
    }

    /**
     * Shows the application logo and welcome message.
     */
    private static void showWelcome() {
        System.out.println("""
                 __     __   ____  ____     ___   __   _  _  ____  ____    __     __   ____
                (  )   / _\\ (___ \\/ _  \\   / __) / _\\ ( \\/ )(  __)/ ___)  (  )   / _\\ (  _ \\
                / (_/\\/    \\ / __/) _  (  ( (_ \\/    \\/ \\/ \\ ) _) \\___ \\  / (_/\\/    \\ ) _ (
                \\____/\\_/\\_/(____)\\____/   \\___/\\_/\\_/\\_)(_/(____)(____/  \\____/\\_/\\_/(____/
                """);

        System.out.println("Welcome to LA28 Games Lab.");
        System.out.println("The Olympics Simulator.");
        System.out.println();
    }
}