package business;

import business.model.Delegation;
import business.model.Athlete;

import persistence.DelegationFileManager;
import persistence.AthleteFileManager;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.util.ArrayList;

public class DelegationManager {

    private final DelegationFileManager delegationFileManager;
    private final AthleteFileManager athleteFileManager;

    public DelegationManager(DelegationFileManager delegationFileManager, AthleteFileManager athleteFileManager) {
        this.delegationFileManager = delegationFileManager;
        this.athleteFileManager = athleteFileManager;
    }

    public ArrayList<Delegation> getDelegations() throws InvalidDataException, DataAccessException {

        ArrayList<Delegation> delegations = delegationFileManager.loadDelegations();

        delegations.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));

        return delegations;
    }

    public Delegation getDelegation(String code) throws InvalidDataException, DataAccessException {
        ArrayList<Delegation> delegations = getDelegations();

        for (Delegation delegation : delegations) {
            if (code.equals(delegation.getCode())) {
                return delegation;
            }
        }
        return null;
    }

    public ArrayList<Athlete> getAthletes(String code) throws InvalidDataException, DataAccessException {
        ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
        ArrayList<Athlete> result = new ArrayList<>();

        for (Athlete athlete : athletes) {
            if (code.equals(athlete.getDelegationCode())) {
                result.add(athlete);
            }
        }
        return result;
    }

    public ArrayList<Delegation> getDelegationsByMedals() throws InvalidDataException, DataAccessException {
        ArrayList<Delegation> delegations = getDelegations();

        delegations.sort((a, b) -> {
            if (a.getGoldMedals() != b.getGoldMedals()) {
                return Integer.compare(b.getGoldMedals(), a.getGoldMedals());
            }

            if (a.getSilverMedals() != b.getSilverMedals()) {
                return Integer.compare(b.getSilverMedals(), a.getSilverMedals());
            }

            if (a.getBronzeMedals() != b.getBronzeMedals()) {
                return Integer.compare(b.getBronzeMedals(), a.getBronzeMedals());
            }

            return a.getName().compareToIgnoreCase(b.getName());
        });

        return delegations;
    }
}
