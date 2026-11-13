package business;

import business.model.Athlete;
import business.model.AthleteProfile;

import persistence.AthleteFileManager;
import persistence.CompetitionFileManager;
import persistence.DelegationFileManager;

import java.util.ArrayList;
import java.util.Date;

public class AthleteManager {

    private AthleteFileManager athleteFileManager;
    private DelegationFileManager delegationFileManager;
    private CompetitionFileManager competitionFileManager;
    private GamesManager gamesManager;

    public AthleteManager(
            AthleteFileManager athleteFileManager, DelegationFileManager delegationFileManager,
            CompetitionFileManager competitionFileManager, GamesManager gamesManager
    ) {
        this.athleteFileManager = athleteFileManager;
        this.delegationFileManager = delegationFileManager;
        this.competitionFileManager = competitionFileManager;
        this.gamesManager = gamesManager;
    }

    public void createAthlete(String name, Date birthDate, String delegationCode, AthleteProfile profile) {
        // TODO: Implement createAthlete
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<Athlete> searchAthletes(String query) {
        // TODO: Implement searchAthletes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean deleteAthlete(String id) {
        // TODO: Implement deleteAthlete
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private boolean isAthleteDuplicate(String name, String delegationCode) {
        // TODO: Implement isAthleteDuplicate
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private String generateAthleteId() {
        // TODO: Implement generateAthleteId
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private boolean isDelegationExist(String code) {
        // TODO: Implement isDelegationExist
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private int generateRandomAttribute() {
        // TODO: Implement generateRandomAttribute
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
