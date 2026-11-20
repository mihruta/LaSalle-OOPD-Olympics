package persistence;

import business.model.Delegation;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import com.google.gson.*;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Loads and saves delegations in a JSON file.
 * Checks delegation data when reading the file.
 */
public class DelegationFileManager {

    private final String fileName;

    /**
     * Creates a file manager for delegations.json.
     */
    public DelegationFileManager() {
        this.fileName = "data/delegations.json";
    }

    /**
     * Loads delegations from the JSON file and checks their data.
     *
     * @return the list of delegations
     * @throws DataAccessException if the file cannot be read
     * @throws InvalidDataException if the delegation data is invalid
     */
    public ArrayList<Delegation> loadDelegations() throws DataAccessException, InvalidDataException {
        try (Reader reader = Files.newBufferedReader(Path.of(this.fileName))) {
            Gson gson = new Gson();

            // Parse JSON file
            JsonElement json = JsonParser.parseReader(reader);

            // Checking if JSON is an Array
            if (!json.isJsonArray()) {
                throw new InvalidDataException("Delegations must be a JSON array.");
            }

            // Loop is searching for wrong format in JSON
            for (JsonElement element : json.getAsJsonArray()) {
                // Checking if it's object
                if (!element.isJsonObject()) {
                    throw new InvalidDataException("Invalid delegation object.");
                }

                JsonObject delegationJson = element.getAsJsonObject();

                // Check required string fields
                for (String field : new String[]{"code", "name"}) {
                    if (!delegationJson.has(field)
                            || !delegationJson.get(field).isJsonPrimitive()
                            || !delegationJson.get(field).getAsJsonPrimitive().isString()) {
                        throw new InvalidDataException("Invalid field: " + field);
                    }
                }

                // Check required medal fields
                for (String field : new String[]{"goldMedals", "silverMedals", "bronzeMedals"}) {
                    if (!delegationJson.has(field)
                            || !delegationJson.get(field).isJsonPrimitive()
                            || !delegationJson.get(field).getAsJsonPrimitive().isNumber()) {
                        throw new InvalidDataException("Invalid field: " + field);
                    }
                }
            }

            Delegation[] delegations = gson.fromJson(json, Delegation[].class);

            ArrayList<Delegation> result = new ArrayList<>();
            HashSet<String> codes = new HashSet<>();

            for (Delegation delegation : delegations) {

                // Code check in delegation
                String code = delegation.getCode();
                if (code == null || code.isBlank()) {
                    throw new InvalidDataException("Delegation code is missing.");
                }

                // Name check in delegation
                String name = delegation.getName();
                if (name == null || name.isBlank()) {
                    throw new InvalidDataException("Delegation name is missing.");
                }

                // Checking if code is not matching JSON code format
                if (!code.matches("DLG-[A-Z]{3}")) {
                    throw new InvalidDataException("Delegation code is invalid.");
                }

                // Checking if same code exists
                if (!codes.add(code)) {
                    throw new InvalidDataException("Duplicate delegation code.");
                }

                // Checking if medal count is not below zero
                if (delegation.getBronzeMedals() < 0 ||
                        delegation.getSilverMedals() < 0 ||
                        delegation.getGoldMedals() < 0) {
                    throw new InvalidDataException("Invalid medal count.");
                }

                result.add(delegation);
            }
            return result;
        } catch (IOException e) {
            throw new DataAccessException("Cannot read delegations.json", e);

        } catch (JsonParseException e) {
            throw new InvalidDataException("Invalid JSON format.", e);
        }
    }

    /**
     * Saves the list of delegations to the JSON file.
     *
     * @param delegations the delegations to save
     * @throws DataAccessException if the file cannot be written
     */
    public void saveDelegations(ArrayList<Delegation> delegations) throws DataAccessException {
        try (Writer writer = Files.newBufferedWriter(Path.of(this.fileName))) {
            Gson gson = new Gson();

            gson.toJson(delegations, writer);

        } catch (IOException e) {
            throw new DataAccessException("Cannot write in delegations.json", e);
        }
    }
}