package business;

import business.model.Delegation;
import business.model.Athlete;

import persistence.DelegationFileManager;
import persistence.AthleteFileManager;

import java.util.ArrayList;

public class DelegationManager {

    private DelegationFileManager delegationFileManager;
    private AthleteFileManager athleteFileManager;

    public DelegationManager(DelegationFileManager delegationFileManager, AthleteFileManager athleteFileManager) {
        this.delegationFileManager = delegationFileManager;
        this.athleteFileManager = athleteFileManager;
    }

    public ArrayList<Delegation> getDelegations() {
        // TODO: Implement getDelegations
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Delegation getDelegation(String code) {
        // TODO: Implement getDelegation
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<Athlete> getAthletes(String code) {
        // TODO: Implement getAthletes
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public ArrayList<Delegation> getDelegationsByMedals() {
        // TODO: Implement getDelegationsByMedals
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
