package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Match;
import model.Player;
import service.JSONConverter;
import service.PersistenceFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PlayerRepositoryImp implements Repository<Player, Integer> {
    private PersistenceFile persistence;
    private String filePath;

    public PlayerRepositoryImp(PersistenceFile persistence, String filePath) {
        this.persistence = persistence;
        this.filePath = filePath;

        File file = new File(filePath);

        if (!file.exists()) {
            persistence.createFile(filePath);
        }
    }

    @Override
    public Integer create(Player player) {
        String data = persistence.readFile(filePath);
        List<Player> players = new ArrayList<>(); // Usar ArrayList en lugar de TreeSet
        Integer id = 0;

        try {
            if (!data.isEmpty()) {
                players = JSONConverter.fromJsonArrayToList(data, Player.class); // Cargar jugadores en ArrayList

                id = players.stream().mapToInt(Player::getId).max().orElse(0); // Obtener el id máximo
            }

            player.setId(id + 1);
            players.add(player); // Agregar el nuevo jugador a la lista

            persistence.writeFile(filePath, JSONConverter.toJson(players)); // Guardar la lista actualizada
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return player.getId(); // Retornar el id del nuevo jugador
    }

    @Override
    public Player find(Integer id) {/*
        for (Player p : players) {
            if (p.getId().equals(id)) {
                return p;
            }
        }*/
        return null;
    }

    @Override
    public void update(Player model) {/*
        for (Player p : players) {
            if (model.getId().equals(p.getId())) {
                p.setName(model.getName());
                p.setLastName(model.getLastName());
                p.setNationality(model.getNationality());
                p.setDateOfBirth(model.getDateOfBirth());
                p.setPoints(model.getPoints());
            }
        }*/
    }

    @Override
    public void delete(Integer id) {/*
        for (Player p : players) {
            if (p.getId().equals(id)) {
                players.remove(p);
            }
        }*/
    }

    @Override
    public List<Player> getAll() {
        String data = persistence.readFile(filePath);
        List<Player> players;

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Imprimir el error
            throw new RuntimeException("Error al parsear el JSON", e);
        }
        return players;
    }

}
