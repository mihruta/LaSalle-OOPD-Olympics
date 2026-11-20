package presentation;

import business.model.Delegation;

import java.util.ArrayList;

/**
 * Displays the delegation medal table.
 */
public class MedalTableView {

    /**
     * Shows the delegations in the provided order.
     *
     * @param delegations the delegations ordered by medals
     */
    public void show(ArrayList<Delegation> delegations) {
        if (delegations.isEmpty()) {
            System.out.println("No delegations are available.");
            return;
        }

        String separator = "+-------+------------------------------------------------------+----+------+------+";

        System.out.println();
        System.out.println(separator);

        System.out.printf("| %-5s | %-52s |%4s|%6s|%6s|%n", "Code", "Delegation", "Gold", "Silver", "Bronze");

        System.out.println(separator);

        for (Delegation delegation : delegations) {
            String name = delegation.getName();

            if (name.length() > 50) {
                name = name.substring(0, 45) + "...";
            }

            System.out.printf(
                    "| %-7s | %-50s |%4d|%6d|%6d|%n",
                    delegation.getCode(),
                    name,
                    delegation.getGoldMedals(),
                    delegation.getSilverMedals(),
                    delegation.getBronzeMedals()
            );
        }

        System.out.println(separator);
    }
}