package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.FileProcessingException;
import exceptions.TournamentNotFoundException;
import model.Tournament;
import utilities.JSONConverter;
import utilities.PersistenceFile;

import java.util.*;

public class TournamentRepositoryImp implements Repository<Tournament, Integer> {
    private final String filePath;
    private List<Tournament> tournaments;

    public TournamentRepositoryImp() {
        this.filePath = "data/tournament.json";
    }

    @Override
    public Integer create(Tournament tournament) {
        String data = PersistenceFile.readFile(filePath);
        Integer id = 0;
        try {
            // Gets a list of objects from the JSON file.
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            // Sort the list by ID
            Collections.sort(tournaments);
            // Gets the ID of the last one, adds 1 to it, and assigns it to the new element.
            if (!tournaments.isEmpty()) {
                id = tournaments.getLast().getId();
            }
            tournament.setId(++id);
            tournaments.add(tournament);
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
        return id;
    }

    @Override
    public Tournament find(Integer id) throws TournamentNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            for (Tournament tournament : tournaments) {
                if (tournament.getId().equals(id)) {
                    return tournament;
                }
            }
            throw new TournamentNotFoundException("No match was found with the given ID: " + id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void update(Tournament modifiedTournament) throws TournamentNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            boolean tournamentUpdated = false;
            for (int i = 0; i < tournaments.size(); i++) {
                if (tournaments.get(i).getId().equals(modifiedTournament.getId())) {
                    tournaments.set(i, modifiedTournament);
                    tournamentUpdated = true;
                    break;
                }
            }
            if (!tournamentUpdated) {
                throw new TournamentNotFoundException("No match was found with the ID: " + modifiedTournament.getId());
            }
            // Only write if the tournament was updated
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void delete(Integer id) throws TournamentNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            boolean tournamentDeleted = false;
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            for (int i = 0; i < tournaments.size(); i++) {
                if (tournaments.get(i).getId().equals(id)) {
                    tournaments.remove(i);
                    tournamentDeleted = true;
                    break;
                }
            }
            if (!tournamentDeleted) {
                throw new TournamentNotFoundException("No tournament was found with the given ID: " + id);
            }
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public List<Tournament> getAll() throws TournamentNotFoundException {
        String data = PersistenceFile.readFile(filePath);
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            if (tournaments == null || tournaments.isEmpty()) {
                throw new TournamentNotFoundException("There are no tournaments saved in JSON");
            }
            return tournaments;

        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }
}

