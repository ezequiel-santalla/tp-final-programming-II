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
    private PersistenceFile persistence;
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
            throw new FileProcessingException("Error al procesar el archivo "+ filePath);
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
            throw new MatchNotFoundException("No se encontró ningún Partido con el id " + id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error al procesar el archivo "+ filePath);
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
                throw new MatchNotFoundException("No se encontró ningún Partido con el ID: " + modifiedMatch.getIdMatch());
            }
            // Solo escribe si se actualizo el partido
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error al procesar el archivo " + filePath);
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
                throw new MatchNotFoundException("No se encontró ningún Partido con el ID: " + id);
            }
            persistence.writeFile(filePath, JSONConverter.toJson(matches));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error al procesar el archivo " + filePath);
        }
    }

    @Override
    public List<Match> getAll() {
        String data = persistence.readFile(filePath);
        try {
            List<Match> matches = JSONConverter.fromJsonArrayToList(data, Match.class);
            if (matches == null || matches.isEmpty()) {
                throw new MatchNotFoundException("No hay partidos guardados json");
            }
            return matches;
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error al procesar el archivo " + filePath);
        }
    }
}
