package presentation;

import java.util.Scanner;

/**
 * Handles console input and common user prompts.
 */
public class ConsoleIO {

    private final Scanner scanner;

    /**
     * Creates the console input handler.
     */
    public ConsoleIO() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a line without removing spaces.
     *
     * @return the entered line
     */
    public String readLine() {
        return scanner.nextLine();
    }

    /**
     * Reads a menu option.
     *
     * @return the entered number, or -1 if the input is invalid
     */
    public int readOption() {
        String input = readLine().trim();

        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Waits for the user to press Enter.
     */
    public void waitForEnter() {
        System.out.println();
        System.out.println("<Press ENTER to continue>");
        readLine();
    }

    /**
     * Asks the user to confirm an action.
     *
     * @param message the confirmation question
     * @return true if confirmed, or false if cancelled
     */
    public boolean confirmAction(String message) {
        while (true) {
            System.out.print(message + " (Y/N): ");
            String answer = readLine().trim();

            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }

            if (answer.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Invalid answer. Please enter Y or N.");
        }
    }
}