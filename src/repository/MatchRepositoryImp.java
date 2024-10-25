package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Match;
import service.JSONConverter;
import service.PersistenceFile;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class MatchRepositoryImp implements Repository<Match, Integer> {
    private PersistenceFile persistence = new PersistenceFile();
    private String filePath;

    public MatchRepositoryImp(PersistenceFile persistence, String filePath) {
        this.persistence = persistence;
        this.filePath = filePath;
    }

    //agrega el objeto a la lista, convierte la lista nuevamente a json y sobreescribe el archivo
    @Override
    public Integer create(Match match) {
        String data = persistence.readFile(filePath);

        Integer id = 0;
        try {
            //obtiene una lista de objetos ordenados por id a partir del archivo json
            TreeSet<Match> matches = new TreeSet<>(JSONConverter.fromJsonArrayToList(data, Match.class));
            //obtiene el id del ultimo, le agrega 1 y lo asigna al nuevo elemnento
            if (!matches.isEmpty()) {
                id = matches.last().getIdMatch();
            }
            match.setIdMatch(id + 1);
            matches.add(match);
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Match find(Integer id) {
        Match matchFound = null;
        String data = persistence.readFile(filePath);
        List<Match> matches;
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (Match match : matches) {
                if (match.getIdMatch().equals(id)) {
                    matchFound = match;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return matchFound;
    }

    @Override
    public void update(Match modifiedMatch) {
        String data = persistence.readFile(filePath);
        List<Match> matches;
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i).getIdMatch().equals(modifiedMatch.getIdMatch())) {
                    matches.set(i, modifiedMatch);
                    break;
                }
            }
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String data = persistence.readFile(filePath);
        List<Match> matches;
        try {
            matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            for (int i = 0; i < matches.size(); i++) {
                if (matches.get(i).getIdMatch().equals(id)) {
                    matches.remove(i);
                    break;
                }
            }
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Match> getAll() {
        String data = persistence.readFile(filePath);
        List<Match> matches = new ArrayList<>();
        try {
            matches = JSONConverter.fromJsonArrayToList(filePath, Match.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return matches;
    }
}
