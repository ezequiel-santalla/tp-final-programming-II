package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.FileProcessingException;
import exceptions.MatchNotFoundException;
import model.Match;
import utilities.JSONConverter;
import utilities.PersistenceFile;

import java.util.Collections;
import java.util.List;

public class MatchRepositoryImp implements Repository<Match, Integer> {
    private final String filePath;
    private List<Match> matches;

    public MatchRepositoryImp() {
        this.filePath = "data/match.json";
    }

    // Add the object to the list, convert the list back to JSON, and overwrite the file.
    @Override
    public Integer create(Match match) {
        String data = PersistenceFile.readFile(filePath);
        Integer id = 0;
        try {
            // Gets a list of objects from the JSON file.
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            // Sort the list by ID
            Collections.sort(matches);
            // Gets the ID of the last one, adds 1 to it, and assigns it to the new element.
            if (!matches.isEmpty()) {
                id = matches.getLast().getIdMatch();
            }
            match.setIdMatch(++id);
            matches.add(match);
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
        return id;
    }

    @Override
    public Match find(Integer id)  throws MatchNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (Match match : matches) {
                if (match.getIdMatch().equals(id)) {
                    return match;
                }
            }
            throw new MatchNotFoundException("No match was found with the given ID: " + id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void update(Match modifiedMatch) throws MatchNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            boolean matchUpdated = false;
            for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i).getIdMatch().equals(modifiedMatch.getIdMatch())) {
                    matches.set(i, modifiedMatch);
                    matchUpdated = true;
                    break;
                }
            }
            if (!matchUpdated) {
                throw new MatchNotFoundException("No match was found with the ID: " + modifiedMatch.getIdMatch());
            }
            // Only write if the math was updated
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void delete(Integer id) throws MatchNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            boolean matchDeleted = false;
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i).getIdMatch().equals(id)) {
                    matches.remove(i);
                    matchDeleted = true;
                    break;
                }
            }
            if (!matchDeleted) {
                throw new MatchNotFoundException("No match was found with the given ID: " + id);
            }
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public List<Match> getAll() throws MatchNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            if (matches == null || matches.isEmpty()) {
                throw new MatchNotFoundException("There are no matches saved in JSON");
            }
            return matches;
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }
}