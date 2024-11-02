package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.FileProcessingException;
import exceptions.MatchNotFoundException;
import model.Match;
import service.JSONConverter;
import service.PersistenceFile;
import java.util.List;
import java.util.TreeSet;

public class MatchRepositoryImp implements Repository<Match, Integer> {
    private final PersistenceFile persistence;
    private final String filePath;

    public MatchRepositoryImp(PersistenceFile persistence, String filePath) {
        this.persistence = persistence;
        this.filePath = filePath;
    }

    // Add the object to the list, convert the list back to JSON, and overwrite the file.
    @Override
    public Integer create(Match match) {
        String data = persistence.readFile(filePath);

        Integer id = 0;
        try {
            // Gets a list of objects sorted by ID from the JSON file.
            TreeSet<Match> matches = new TreeSet<>(JSONConverter.fromJsonArrayToList(data, Match.class));
            // Gets the ID of the last one, adds 1 to it, and assigns it to the new element.
            if (!matches.isEmpty()) {
                id = matches.last().getIdMatch();
            }
            match.setIdMatch(id + 1);
            matches.add(match);
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
        return id;
    }

    @Override
    public Match find(Integer id) {
        String data = persistence.readFile(filePath);
        List<Match> matches;
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (Match match : matches) {
                if (match.getIdMatch().equals(id)) {
                    return match;
                }
            }
            throw new MatchNotFoundException("No match was found with the given ID: " + id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public void update(Match modifiedMatch) {
        String data = persistence.readFile(filePath);
        List<Match> matches;
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
            // Solo escribe si se actualizó el partido
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public void delete(Integer id) {
        String data = persistence.readFile(filePath);
        List<Match> matches;
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
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public List<Match> getAll() {
        String data = persistence.readFile(filePath);
        try {
            List<Match> matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            if (matches == null || matches.isEmpty()) {
                throw new MatchNotFoundException("There are no matches saved in JSON");
            }
            return matches;
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }
}
