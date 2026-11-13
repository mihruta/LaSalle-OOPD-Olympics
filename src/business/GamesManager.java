package business;

import business.model.Athlete;
import business.model.Competition;
import business.model.CompetitionResult;
import business.model.CompetitionWeights;

import persistence.AthleteFileManager;
import persistence.CompetitionFileManager;
import persistence.DelegationFileManager;

public class GamesManager {

    private DelegationFileManager delegationFileManager;
    private AthleteFileManager athleteFileManager;
    private CompetitionFileManager competitionFileManager;

    public GamesManager(
            DelegationFileManager delegationFileManager,
            AthleteFileManager athleteFileManager,
            CompetitionFileManager competitionFileManager
    ) {
        this.delegationFileManager = delegationFileManager;
        this.athleteFileManager = athleteFileManager;
        this.competitionFileManager = competitionFileManager;
    }

    public boolean isAbleToSimulate() {
        // TODO: Implement isAbleToSimulate
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void simulateCompetitions() {
        // TODO: Implement simulateCompetitions
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void resetSimulation() {
        // TODO: Implement resetSimulation
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private CompetitionResult simulateCompetition(Competition competition) {
        // TODO: Implement simulateCompetition
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private double calculateTime(Athlete athlete, CompetitionWeights weights, double luck) {
        // TODO: Implement calculateTime
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private void updateMedals(CompetitionResult result) {
        // TODO: Implement updateMedals
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
