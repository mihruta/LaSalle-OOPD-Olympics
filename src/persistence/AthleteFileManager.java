package persistence;

import business.model.Athlete;
import com.google.gson.*;
import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;

public class AthleteFileManager {

    private final String fileName;

    public AthleteFileManager() {
        fileName = "data/athletes.json";
    }

    public ArrayList<Athlete> loadAthletes() throws DataAccessException, InvalidDataException {
        if (!Files.exists(Path.of(this.fileName))) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(Path.of(this.fileName))) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            JsonElement json = JsonParser.parseReader(reader);

            if (!json.isJsonArray()) {
                throw new InvalidDataException("Athletes must be a JSON array.");
            }

            for (JsonElement element : json.getAsJsonArray()) {
                // Checking if it's object
                if (!element.isJsonObject()) {
                    throw new InvalidDataException("Invalid athlete object.");
                }

                JsonObject athleteJson = element.getAsJsonObject();

                // Check required string fields
                for (String field : new String[]{"id", "profile", "name", "birthDate", "delegationCode"}) {
                    if (!athleteJson.has(field)
                            || !athleteJson.get(field).isJsonPrimitive()
                            || !athleteJson.get(field).getAsJsonPrimitive().isString()) {
                        throw new InvalidDataException("Invalid field: " + field);
                    }
                }

                // Check required number fields
                for (String field : new String[]{"speed", "endurance", "strength", "technique", "precision"}) {
                    if (!athleteJson.has(field)
                            || !athleteJson.get(field).isJsonPrimitive()
                            || !athleteJson.get(field).getAsJsonPrimitive().isNumber()) {
                        throw new InvalidDataException("Invalid field: " + field);
                    }
                }
            }

            Athlete[] athletes = gson.fromJson(json, Athlete[].class);

            ArrayList<Athlete> result = new ArrayList<>();
            HashSet<String> ids = new HashSet<>();

            for (Athlete athlete : athletes) {

                String id = athlete.getId();
                if (id == null || id.isBlank()) {
                    throw new InvalidDataException("ID is missing.");
                }

                String name = athlete.getName();
                if (name == null || name.isBlank()) {
                    throw new InvalidDataException("Name is missing.");
                }

                String delegationCode = athlete.getDelegationCode();
                if (delegationCode == null || delegationCode.isBlank()) {
                    throw new InvalidDataException("Delegation code is missing.");
                }

                if (!ids.add(id)) {
                    throw new InvalidDataException("Duplicate ID.");
                }

                if (athlete.getProfile() == null) {
                    throw new InvalidDataException("Invalid athlete profile.");
                }

                int speed = athlete.getSpeed();
                int endurance = athlete.getEndurance();
                int strength = athlete.getStrength();
                int technique = athlete.getTechnique();
                int precision = athlete.getPrecision();

                if ((speed <= 0 || speed > 100)
                                || (endurance <= 0 || endurance > 100)
                                || (strength <= 0 || strength > 100)
                                || (technique <= 0 || technique > 100)
                                || (precision <= 0 || precision > 100)) {
                    throw new InvalidDataException("Invalid athlete stats.");
                }

                result.add(athlete);
            }

            return result;
        } catch (IOException e) {
            throw new DataAccessException("Cannot read athletes.json.", e);
        } catch (JsonParseException e) {
            throw new InvalidDataException("Invalid JSON format.", e);
        }
    }

    public void saveAthletes(ArrayList<Athlete> athletes) throws DataAccessException {
        try (Writer writer = Files.newBufferedWriter(Path.of(this.fileName))) {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

            gson.toJson(athletes, writer);

        } catch (IOException e) {
            throw new DataAccessException("Cannot write in athletes.json", e);
        }
    }
}
