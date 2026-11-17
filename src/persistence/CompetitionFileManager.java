package persistence;

import business.model.Competition;

import com.google.gson.*;

import persistence.exceptions.DataAccessException;
import persistence.exceptions.InvalidDataException;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class CompetitionFileManager {

    private final String fileName;

    public CompetitionFileManager() {
        fileName = "data/competitions.json";
    }

    public ArrayList<Competition> loadCompetitions() throws DataAccessException, InvalidDataException {
        if (!Files.exists(Path.of(this.fileName))) {
            return new ArrayList<>();
        }
        try (Reader reader = Files.newBufferedReader(Path.of(this.fileName))) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm")
                    .serializeNulls()
                    .create();

            JsonElement json = JsonParser.parseReader(reader);

            if (!json.isJsonArray()) {
                throw new InvalidDataException("Competitions must be a JSON array.");
            }

            ArrayList<String> competitionIds = new ArrayList<>();

            for (JsonElement element : json.getAsJsonArray()) {
                if (!element.isJsonObject()) {
                    throw new InvalidDataException("Invalid competition object.");
                }

                JsonObject competitionJson = element.getAsJsonObject();

                for (String field : new String[]{"id", "format", "name", "dateTime"}) {
                    if (!competitionJson.has(field)
                            || !competitionJson.get(field).isJsonPrimitive()
                            || !competitionJson.get(field).getAsJsonPrimitive().isString()) {
                        throw new InvalidDataException("Invalid field: " + field);
                    }
                }

                String id = competitionJson.get("id").getAsString();

                if (competitionIds.contains(id)) {
                    throw new InvalidDataException("Duplicate competition ID: " + id);
                }

                competitionIds.add(id);

                if (!competitionJson.has("weights")
                        || !competitionJson.get("weights").isJsonObject()) {
                    throw new InvalidDataException("Invalid weights object.");
                }

                JsonObject weightsJson = competitionJson.getAsJsonObject("weights");

                double total = 0.0;

                for (String field : new String[]{"speed", "endurance", "strength", "technique", "precision"}) {
                    if (!weightsJson.has(field)
                            || !weightsJson.get(field).isJsonPrimitive()
                            || !weightsJson.get(field).getAsJsonPrimitive().isNumber()) {
                        throw new InvalidDataException("Invalid weight field: " + field);
                    }

                    double weight = weightsJson.get(field).getAsDouble();

                    if (weight < 0.0 || weight > 1.0) {
                        throw new InvalidDataException("Invalid weight value: " + field);
                    }
                    total += weight;
                }
                if (Math.abs(total - 1.0) > 0.000001) {
                    throw new InvalidDataException("Invalid weights sum.");
                }

                if (!competitionJson.has("athleteIds")
                        || !competitionJson.get("athleteIds").isJsonArray()) {
                    throw new InvalidDataException("Invalid athlete IDs array.");
                }

                JsonArray athleteIdsJson = competitionJson.getAsJsonArray("athleteIds");

                if (athleteIdsJson.size() < 3) {
                    throw new InvalidDataException("Competition must have at least 3 athletes.");
                }

                ArrayList<String> usedIds = new ArrayList<>();

                for (JsonElement athleteId : athleteIdsJson) {
                    if (!athleteId.isJsonPrimitive()
                            || !athleteId.getAsJsonPrimitive().isString()) {
                        throw new InvalidDataException("Invalid athlete ID.");
                    }

                    String athleteIdString = athleteId.getAsString();

                    if (usedIds.contains(athleteIdString)) {
                        throw new InvalidDataException("Duplicate athlete ID: " + athleteIdString);
                    }

                    usedIds.add(athleteIdString);
                }

                if (competitionJson.has("results")) {
                    JsonElement resultsJson = competitionJson.get("results");

                    if (!resultsJson.isJsonNull() && !resultsJson.isJsonObject()) {
                        throw new InvalidDataException("Invalid competition results.");
                    }

                    if (!resultsJson.isJsonNull()) {
                        JsonObject medalsJson = resultsJson.getAsJsonObject();

                        for (String medal : new String[]{"gold", "silver", "bronze"}) {
                            if (!medalsJson.has(medal)
                                    || !medalsJson.get(medal).isJsonPrimitive()
                                    || !medalsJson.get(medal).getAsJsonPrimitive().isString()) {
                                throw new InvalidDataException("Invalid medal field: " + medal);
                            }
                        }

                        String gold = medalsJson.get("gold").getAsString();
                        String silver = medalsJson.get("silver").getAsString();
                        String bronze = medalsJson.get("bronze").getAsString();

                        if (gold.equals(silver) || gold.equals(bronze) || silver.equals(bronze)) {
                            throw new InvalidDataException("Duplicate winner with medal.");
                        }

                        if (!usedIds.contains(gold)
                                || !usedIds.contains(silver)
                                || !usedIds.contains(bronze)) {
                            throw new InvalidDataException("Winner with medal is not in athlete IDs.");
                        }
                    }
                }

                String format = competitionJson.get("format").getAsString();

                if (!format.equals("TIMED")
                        && !format.equals("SCORED")
                        && !format.equals("MEASURED")) {
                    throw new InvalidDataException("Invalid competition format: " + format);
                }

                String dateTime = competitionJson.get("dateTime").getAsString();

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");

                    LocalDateTime.parse(dateTime, formatter);

                } catch (DateTimeParseException e) {
                    throw new InvalidDataException("Invalid competition dateTime: " + dateTime);
                }
            }

            Competition[] competitions = gson.fromJson(json, Competition[].class);

            ArrayList<Competition> result = new ArrayList<>();

            for (Competition competition : competitions) {
                result.add(competition);
            }

            return result;
        } catch (IOException e) {
            throw new DataAccessException("Cannot read competitions.json", e);
        } catch (JsonParseException e) {
            throw new InvalidDataException("Invalid JSON format.", e);
        }
    }

    public void saveCompetitions(ArrayList<Competition> competitions) throws DataAccessException {
        try (Writer writer = Files.newBufferedWriter(Path.of(this.fileName))) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm")
                    .serializeNulls()
                    .create();

            gson.toJson(competitions, writer);

        } catch (IOException e) {
            throw new DataAccessException("Cannot write in competitions.json", e);
        }
    }
}
