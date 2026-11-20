package presentation;

import business.DelegationManager;
import business.model.Athlete;
import business.model.Delegation;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

/**
 * Handles the delegations submenu.
 */
public class DelegationMenu {

    private final DelegationManager delegationManager;
    private final ConsoleIO consoleIO;
    private final MedalTableView medalTableView;

    /**
     * Creates the delegations menu.
     *
     * @param delegationManager the delegation business manager
     * @param consoleIO         the shared console input handler
     * @param medalTableView    the shared medal table view
     */
    public DelegationMenu(DelegationManager delegationManager, ConsoleIO consoleIO, MedalTableView medalTableView) {
        this.delegationManager = delegationManager;
        this.consoleIO = consoleIO;
        this.medalTableView = medalTableView;
    }

    /**
     * Runs the submenu until the user chooses to go back.
     */
    public void run() {
        int option;

        do {
            System.out.println();
            System.out.println("Delegations submenu:");
            System.out.println();
            System.out.println("\t1) Show list of delegations");
            System.out.println("\t2) Show medals by delegation");
            System.out.println();
            System.out.println("\t0) Back");
            System.out.println();
            System.out.print("Choose an option: ");

            option = consoleIO.readOption();

            try {
                switch (option) {
                    case 1:
                        showDelegations();
                        break;

                    case 2:
                        ArrayList<Delegation> delegations = delegationManager.getDelegationsByMedals();
                        medalTableView.show(delegations);
                        consoleIO.waitForEnter();
                        break;

                    case 0:
                        break;

                    default:
                        System.out.println("Invalid option. Please choose a number between 0 and 2.");
                        break;
                }
            } catch (DataAccessException | InvalidDataException e) {
                System.out.println("Error: " + e.getMessage());
                consoleIO.waitForEnter();
            }
        } while (option != 0);
    }

    /**
     * Lists delegations and displays the selected delegation.
     *
     * @throws DataAccessException  if the data cannot be read
     * @throws InvalidDataException if the stored data is invalid
     */
    private void showDelegations() throws DataAccessException, InvalidDataException {

        ArrayList<Delegation> delegations = delegationManager.getDelegations();

        if (delegations.isEmpty()) {
            System.out.println("No delegations are available.");
            consoleIO.waitForEnter();
            return;
        }

        System.out.println();

        for (int i = 0; i < delegations.size(); i++) {
            Delegation delegation = delegations.get(i);
            System.out.printf("\t%d) %s %s\n", i + 1, delegation.getCode(), delegation.getName());
        }

        System.out.println();
        System.out.println("\t0) Back");
        System.out.println();

        int option;

        do {
            System.out.print("Choose an option: ");
            option = consoleIO.readOption();

            if (option < 0 || option > delegations.size()) {
                System.out.println("Invalid delegation number.");
            }
        } while (option < 0 || option > delegations.size());

        if (option == 0) {
            return;
        }

        Delegation delegation = delegations.get(option - 1);

        showDelegationDetails(delegation);
        consoleIO.waitForEnter();
    }

    /**
     * Shows a delegation's medals and athletes.
     *
     * @param delegation the selected delegation
     * @throws DataAccessException  if the athletes cannot be read
     * @throws InvalidDataException if the stored athletes are invalid
     */
    private void showDelegationDetails(Delegation delegation) throws DataAccessException, InvalidDataException {

        ArrayList<Athlete> athletes = delegationManager.getAthletes(delegation.getCode());

        System.out.println();
        System.out.printf("-| %s | %s |-\n", delegation.getCode(), delegation.getName());

        System.out.println("\tGold: " + delegation.getGoldMedals());
        System.out.println("\tSilver: " + delegation.getSilverMedals());
        System.out.println("\tBronze: " + delegation.getBronzeMedals());

        System.out.println();
        System.out.printf("\t%d athletes\n", athletes.size());
        System.out.println("\t----------");

        for (Athlete athlete : athletes) {
            System.out.println("\t" + athlete.getName());
        }
    }
}