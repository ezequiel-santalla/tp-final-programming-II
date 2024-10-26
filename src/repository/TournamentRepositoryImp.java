package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Tournament;
import service.JSONConverter;
import service.PersistenceFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TournamentRepositoryImp implements Repository<Tournament, Integer> {
    private PersistenceFile persistence = new PersistenceFile();
    private String filePath;

    public TournamentRepositoryImp(PersistenceFile persistence, String filePath) {
        this.persistence = persistence;
        this.filePath = filePath;
        File file = new File(filePath);
        if (!file.exists()) {
            persistence.createFile(filePath);
        }
    }

    @Override
    public Integer create(Tournament tournament) {
        String data = persistence.readFile(filePath);
        TreeSet<Tournament> tournaments = new TreeSet<>(Comparator.comparing(Tournament::getId));
        Integer id = 0;
        try {
            if (!data.isEmpty()) {
                tournaments = new TreeSet<>(JSONConverter.fromJsonArrayToList(data, Tournament.class));
                id = tournaments.last().getId();
            }
            tournament.setId(id + 1);
            tournaments.add(tournament);
            persistence.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return id + 1;
    }

    @Override
    public Tournament find(Integer id) {
        Tournament tournamentFound = null;
        String data = persistence.readFile(filePath);
        List<Tournament> tournaments;
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            for (Tournament tournament : tournaments) {
                if (tournament.getId().equals(id)) {
                    tournamentFound = tournament;
                    break;
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return tournamentFound;
    }

    @Override
    public void update(Tournament modifiedTournament) {
        String data = persistence.readFile(filePath);
        List<Tournament> tournaments;
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            for (int i = 0; i < tournaments.size(); i++) {
                if (tournaments.get(i).getId().equals(modifiedTournament.getId())) {
                    tournaments.set(i, modifiedTournament);
                    break;
                }
            }
            persistence.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        String data = persistence.readFile(filePath);
        List<Tournament> tournaments;
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
            tournaments.removeIf(tournament -> tournament.getId().equals(id));
            persistence.writeFile(filePath, JSONConverter.toJson(tournaments));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Tournament> getAll() {
        String data = persistence.readFile(filePath);
        List<Tournament> tournaments = new ArrayList<>();
        try {
            tournaments = JSONConverter.fromJsonArrayToList(data, Tournament.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return tournaments;
    }
}

