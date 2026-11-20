package business.model;

import java.util.ArrayList;

/**
 * Stores the fields and validation errors of one competition CSV row.
 */
public class CompetitionImportResult {

    private final int lineNumber;
    private final String[] fields;
    private final ArrayList<String> errors;

    /**
     * Creates the result of processing one CSV row.
     *
     * @param lineNumber the line number in the CSV file
     * @param fields the parsed CSV fields
     * @param errors the errors found in the row
     */
    public CompetitionImportResult(
            int lineNumber,
            String[] fields,
            ArrayList<String> errors
    ) {
        this.lineNumber = lineNumber;
        this.fields = fields;
        this.errors = errors;
    }

    /**
     * Returns the line number in the CSV file.
     *
     * @return the line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Returns the parsed CSV fields.
     *
     * @return the fields in CSV column order
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * Returns the errors found in the row.
     *
     * @return the validation errors
     */
    public ArrayList<String> getErrors() {
        return errors;
    }

    /**
     * Checks whether the row contains errors.
     *
     * @return true if any validation errors were found
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}