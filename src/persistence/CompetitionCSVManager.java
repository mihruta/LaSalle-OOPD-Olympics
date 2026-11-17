package persistence;

import business.model.Athlete;
import business.model.Competition;
import business.model.CompetitionFormat;
import business.model.CompetitionWeights;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

/**
 * Imports competitions from CSV files and validates their data.
 */
public class CompetitionCSVManager {

    private final AthleteFileManager athleteFileManager;
    private final CompetitionFileManager competitionFileManager;

    /**
     * Creates the CSV manager with the required file managers.
     *
     * @param athleteFileManager the manager used to load athletes
     * @param competitionFileManager the manager used to load and save competitions
     */
    public CompetitionCSVManager(
            AthleteFileManager athleteFileManager,
            CompetitionFileManager competitionFileManager
    ) {
        this.athleteFileManager = athleteFileManager;
        this.competitionFileManager = competitionFileManager;
    }

    /**
     * Imports valid competitions from a CSV file.
     *
     * @param fileName the path to the CSV file
     * @return import results and error messages
     */
    public ArrayList<String> importFile(String fileName) {
        ArrayList<String> messages = new ArrayList<>();
        ArrayList<String> successMessages = new ArrayList<>();

        try {
            ArrayList<Athlete> athletes = athleteFileManager.loadAthletes();
            ArrayList<Competition> competitions = competitionFileManager.loadCompetitions();

            try (BufferedReader reader = Files.newBufferedReader(Path.of(fileName))) {

                validateHeader(reader.readLine());

                String line;
                int rowNumber = 1;

                while ((line = reader.readLine()) != null) {
                    rowNumber++;

                    if (line.isBlank()) {
                        continue;
                    }

                    try {
                        String[] data = parseCSVRow(line);
                        ArrayList<String> errors = checkRow(data);

                        if (data.length == 10) {
                            errors.addAll(
                                    validateReferences(data, athletes, competitions)
                            );
                        }

                        if (errors.isEmpty()) {
                            Competition competition = createCompetition(data);
                            competitions.add(competition);

                            successMessages.add(
                                    "Row " + rowNumber + ": Competition \"" + competition.getId() + "\" loaded successfully."
                            );
                        } else {
                            for (String error : errors) {
                                messages.add("Row " + rowNumber + ": " + error);
                            }
                        }
                    } catch (InvalidDataException | ParseException e) {
                        messages.add("Row " + rowNumber + ": " + e.getMessage());
                    }
                }
            }

            if (!successMessages.isEmpty()) {
                competitionFileManager.saveCompetitions(competitions);
                messages.addAll(successMessages);
            } else {
                messages.add("No competitions were imported.");
            }
        } catch (IOException | InvalidPathException e) {
            messages.add(
                    "Cannot read CSV file \"" + fileName + "\"."
            );
        } catch (DataAccessException | InvalidDataException e) {
            messages.add("Import failed: " + e.getMessage());
        }

        return messages;
    }

    /**
     * Checks the CSV header and removes the UTF-8 BOM if present.
     *
     * @param line the first line of the CSV file
     * @throws InvalidDataException if the file is empty or the header is invalid
     */
    private void validateHeader(String line) throws InvalidDataException {
        if (line == null) {
            throw new InvalidDataException("The CSV file is empty.");
        }

        if (line.startsWith("\uFEFF")) {
            line = line.substring(1);
        }

        String[] expectedFields = {
                "id",
                "format",
                "name",
                "dateTime",
                "speedWeight",
                "enduranceWeight",
                "strengthWeight",
                "techniqueWeight",
                "precisionWeight",
                "athleteIds"
        };

        String[] fields = parseCSVRow(line);

        if (fields.length != expectedFields.length) {
            throw new InvalidDataException(
                    "The CSV header must contain exactly 10 columns."
            );
        }

        for (int i = 0; i < expectedFields.length; i++) {
            if (!fields[i].equals(expectedFields[i])) {
                throw new InvalidDataException(
                        "Invalid CSV header. Expected \"" + expectedFields[i] + "\" in column " + (i + 1) + "."
                );
            }
        }
    }

    /**
     * Splits one CSV line into fields and handles quoted values.
     *
     * @param line one line from the CSV file
     * @return the fields without surrounding spaces or quotes
     * @throws InvalidDataException if the quotes are invalid
     */
    private String[] parseCSVRow(String line) throws InvalidDataException {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder field = new StringBuilder();

        boolean insideQuotes = false;
        boolean quoteClosed = false;

        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);

            if (insideQuotes) {
                if (character == '"') {
                    if (i + 1 < line.length() && line.charAt(i + 1) == '"') {
                        field.append('"');
                        i++;
                    } else {
                        insideQuotes = false;
                        quoteClosed = true;
                    }
                } else {
                    field.append(character);
                }
            } else if (character == ',') {
                fields.add(field.toString().trim());
                field.setLength(0);
                quoteClosed = false;
            } else if (character == '"') {
                if (quoteClosed || !field.toString().isBlank()) {
                    throw new InvalidDataException("Unexpected quote in a CSV field.");
                }

                field.setLength(0);
                insideQuotes = true;
            } else if (quoteClosed) {
                if (!Character.isWhitespace(character)) {
                    throw new InvalidDataException("Unexpected text after a closing quote.");
                }
            } else {
                field.append(character);
            }
        }

        if (insideQuotes) {
            throw new InvalidDataException("Unclosed quote in the CSV row.");
        }

        fields.add(field.toString().trim());

        return fields.toArray(new String[0]);
    }

    /**
     * Checks the data in a competition CSV row.
     *
     * @param data the competition fields
     * @return the errors found in the row
     */
    private ArrayList<String> checkRow(String[] data) {
        ArrayList<String> errors = new ArrayList<>();

        if (data.length != 10) {
            errors.add("The row must contain exactly 10 fields.");
            return errors;
        }

        if (!data[0].matches("CMP-[0-9]+")) {
            errors.add(
                    "Invalid competition identifier \"" + data[0] + "\". Expected CMP- followed by digits."
            );
        }

        try {
            CompetitionFormat.valueOf(data[1]);
        } catch (IllegalArgumentException e) {
            errors.add(
                    "Invalid competition format \"" + data[1] + "\". Expected TIMED, SCORED or MEASURED."
            );
        }

        if (data[2].isBlank()) {
            errors.add("The competition name cannot be empty.");
        }

        try {
            parseDateTime(data[3]);
        } catch (ParseException e) {
            errors.add(
                    "Invalid start date and time \"" + data[3] + "\". Expected yyyy-MM-dd HH:mm."
            );
        }

        errors.addAll(validateWeights(data));

        errors.addAll(validateParticipants(data[9]));

        return errors;
    }

    /**
     * Checks the five competition weights and their sum.
     *
     * @param data the CSV row with exactly 10 fields
     * @return the errors found in the weights
     */
    private ArrayList<String> validateWeights(String[] data) {
        ArrayList<String> errors = new ArrayList<>();

        String[] weightNames = {
                "speed", "endurance", "strength", "technique", "precision"
        };

        double totalWeight = 0.0;
        boolean canCheckSum = true;

        for (int i = 0; i < weightNames.length; i++) {
            try {
                double weight = Double.parseDouble(data[i + 4]);

                if (!Double.isFinite(weight)) {
                    errors.add(
                            "The " + weightNames[i] + " weight must be a finite number."
                    );
                    canCheckSum = false;
                } else {
                    totalWeight += weight;

                    if (weight < 0.0 || weight > 1.0) {
                        errors.add(
                                "The " + weightNames[i] + " weight must be between 0.0 and 1.0."
                        );
                    }
                }
            } catch (NumberFormatException e) {
                errors.add(
                        "The " + weightNames[i] + " weight must be a number."
                );
                canCheckSum = false;
            }
        }

        if (canCheckSum && Math.abs(totalWeight - 1.0) >= 0.000001) {
            errors.add("The weights must add up to 1.0.");
        }

        return errors;
    }

    /**
     * Checks participant IDs for empty values and duplicates.
     *
     * @param value the participant IDs separated by |
     * @return the errors found in the participant list
     */
    private ArrayList<String> validateParticipants(String value) {
        ArrayList<String> errors = new ArrayList<>();
        ArrayList<String> athleteIds = new ArrayList<>();

        String[] participants = value.split("\\|", -1);

        for (String participant : participants) {
            String id = participant.trim();

            if (id.isEmpty()) {
                errors.add("An athlete identifier cannot be empty.");
            } else if (athleteIds.contains(id)) {
                errors.add("Duplicate athlete identifier \"" + id + "\".");
            } else {
                athleteIds.add(id);
            }
        }

        if (athleteIds.size() < 3) {
            errors.add("A competition must have at least 3 different athletes.");
        }

        return errors;
    }

    /**
     * Checks the competition ID and participant references.
     *
     * @param data the CSV row with exactly 10 fields
     * @param athletes the registered athletes
     * @param competitions the existing and accepted competitions
     * @return the errors found in the references
     */
    private ArrayList<String> validateReferences(String[] data, ArrayList<Athlete> athletes, ArrayList<Competition> competitions) {
        ArrayList<String> errors = new ArrayList<>();

        for (Competition competition : competitions) {
            if (competition.getId().equals(data[0])) {
                errors.add(
                        "Competition identifier \"" + data[0] + "\" already exists."
                );
                break;
            }
        }

        String[] participants = data[9].split("\\|", -1);
        ArrayList<String> checkedIds = new ArrayList<>();

        for (String participant : participants) {
            String id = participant.trim();

            if (id.isEmpty() || checkedIds.contains(id)) {
                continue;
            }

            checkedIds.add(id);

            boolean athleteExists = false;

            for (Athlete athlete : athletes) {
                if (athlete.getId().equals(id)) {
                    athleteExists = true;
                    break;
                }
            }

            if (!athleteExists) {
                errors.add(
                        "Athlete \"" + id + "\" does not exist in the system."
                );
            }
        }

        return errors;
    }

    /**
     * Creates a competition from a validated CSV row.
     *
     * @param data the competition fields
     * @return the new competition
     * @throws ParseException if the date and time are invalid
     */
    private Competition createCompetition(String[] data) throws ParseException {
        String id = data[0];
        CompetitionFormat format = CompetitionFormat.valueOf(data[1]);
        String name = data[2];
        Date dateTime = parseDateTime(data[3]);

        double speed = Double.parseDouble(data[4]);
        double endurance = Double.parseDouble(data[5]);
        double strength = Double.parseDouble(data[6]);
        double technique = Double.parseDouble(data[7]);
        double precision = Double.parseDouble(data[8]);

        CompetitionWeights weights = new CompetitionWeights(
                speed, endurance, strength, technique, precision
        );

        ArrayList<String> athleteIds = new ArrayList<>();
        String[] participants = data[9].split("\\|", -1);

        for (String participant : participants) {
            athleteIds.add(participant.trim());
        }

        return new Competition(
                id, name, format, dateTime, weights, athleteIds
        );
    }

    /**
     * Parses a competition date and time using the required format.
     *
     * @param value the date and time in yyyy-MM-dd HH:mm format
     * @return the parsed date and time
     * @throws ParseException if the value is invalid
     */
    private Date parseDateTime(String value) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        formatter.setLenient(false);

        Date dateTime = formatter.parse(value);

        if (!formatter.format(dateTime).equals(value)) {
            throw new ParseException("Invalid date and time format.", 0);
        }

        return dateTime;
    }
}
