package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import model.Player;
import service.JSONConverter;
import service.PersistenceFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        List<Player> players = new ArrayList<>();
        Integer id = 0;

        try {
            if (!data.isEmpty()) {
                players = JSONConverter.fromJsonArrayToList(data, Player.class); // Load players into the array list

                id = players.stream().mapToInt(Player::getId).max().orElse(0); // Get max id
            }

            player.setId(id + 1);
            players.add(player); // Add the new player to the list

            persistence.writeFile(filePath, JSONConverter.toJson(players)); // save the updated list
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return player.getId(); // Return id of the new player
    }

    @Override
    public Player find(Integer id) {
        return null;
    }

    @Override
    public void update(Player model) {
    }

    @Override
    public void delete(Integer id) {
    }

    @Override
    public List<Player> getAll() {
        String data = persistence.readFile(filePath);
        List<Player> players;

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error parsing JSON", e);
        }
        return players;
    }

}
