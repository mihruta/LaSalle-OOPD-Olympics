package presentation;

import business.AthleteManager;
import business.CompetitionManager;
import business.DelegationManager;
import business.GamesManager;

import java.util.Scanner;

public class Menu {

    private DelegationManager delegationManager;
    private AthleteManager athleteManager;
    private CompetitionManager competitionManager;
    private GamesManager gamesManager;
    private Scanner scanner;

    public Menu(
            DelegationManager delegationManager, AthleteManager athleteManager,
            CompetitionManager competitionManager, GamesManager gamesManager
    ) {
        this.delegationManager = delegationManager;
        this.athleteManager = athleteManager;
        this.competitionManager = competitionManager;
        this.gamesManager = gamesManager;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        // TODO: Implement run
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void delegationMenu() {
        // TODO: Implement delegationMenu
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void athleteMenu() {
        // TODO: Implement athleteMenu
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void competitionMenu() {
        // TODO: Implement competitionMenu
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void simulationMenu() {
        // TODO: Implement simulationMenu
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private int readOption() {
        // TODO: Implement readOption
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
