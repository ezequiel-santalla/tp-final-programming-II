package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.FileProcessingException;
import exceptions.MatchNotFoundException;
import exceptions.PlayerNotFoundException;
import model.Match;
import model.Player;
import service.JSONConverter;
import service.PersistenceFile;
import java.util.List;
import java.util.TreeSet;

public class PlayerRepositoryImp implements Repository<Player, Integer> {
    private final PersistenceFile persistence;
    private final String filePath;

    public PlayerRepositoryImp(PersistenceFile persistence, String filePath) {
        this.persistence = persistence;
        this.filePath = filePath;
    }

    @Override
    public Integer create(Player player) {
        String data = persistence.readFile(filePath);

        Integer id = 0;

        try {
            TreeSet<Player> players = new TreeSet<>(JSONConverter.fromJsonArrayToList(data, Player.class));

            if (!players.isEmpty()) {
                id = players.last().getIdPlayer();
            }

            player.setIdPlayer(id + 1);
            players.add(player);
            persistence.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
        return id;
    }

    @Override
    public Player find(Integer id) {
        String data = persistence.readFile(filePath);
        List<Player> players;

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);
            for (Player p : players) {
                if (p.getIdPlayer().equals(id)) {
                    return p;
                }
            }
            throw new PlayerNotFoundException("No player was found with the ID: " + id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public void update(Player modifiedPlayer) {
        String data = persistence.readFile(filePath);
        List<Player> players;

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);
            boolean playerUpdated = false;

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getIdPlayer().equals(modifiedPlayer.getIdPlayer())) {
                    players.set(i, modifiedPlayer);
                    playerUpdated = true;
                    break;
                }
            }
            if (!playerUpdated) {
                throw new PlayerNotFoundException("No player was found with the ID: " + modifiedPlayer.getIdPlayer());
            }

            persistence.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public void delete(Integer id) {
        String data = persistence.readFile(filePath);
        List<Player> players;

        try {
            boolean playerDeleted = false;
            players = JSONConverter.fromJsonArrayToList(data, Player.class);

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getIdPlayer().equals(id)) {
                    players.remove(i);
                    playerDeleted = true;
                    break;
                }
            }
            if (!playerDeleted) {
                throw new PlayerNotFoundException("No player was found with the ID: " + id);
            }

            persistence.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }

    @Override
    public List<Player> getAll() {
        String data = persistence.readFile(filePath);

        try {
            List<Player> players = JSONConverter.fromJsonArrayToList(data, Player.class);

            if (players == null || players.isEmpty()) {
                throw new PlayerNotFoundException("There are no matches saved in JSON.");
            }
            return players;
        } catch (JsonProcessingException e) {
            throw new FileProcessingException("Error processing the file " + filePath);
        }
    }
}
