package repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import exceptions.FileProcessingException;
import exceptions.PlayerNotFoundException;
import model.Player;
import utilities.JSONConverter;
import utilities.PersistenceFile;

import java.util.*;

public class PlayerRepositoryImp implements Repository<Player, Integer> {
    private final String filePath;
    private List<Player> players;


    public PlayerRepositoryImp() {
        this.filePath = "data/player.json";
    }

    @Override
    public Integer create(Player player) {

        try {
            LinkedHashSet<Player> playersSet;
            // Generates a LinkedHashSet of player from the list obtained from the json file
            playersSet = new LinkedHashSet<>(JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class));
            player.setIdPlayer(generatePlayerID());
            playersSet.add(player);
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(playersSet));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
        return player.getIdPlayer();
    }

    @Override
    public Player find(Integer id) throws PlayerNotFoundException, FileProcessingException {

        try {
            players = JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class);
            for (Player player : players) {
                if (player.getIdPlayer().equals(id)) {
                    return player;
                }
            }
            throw new PlayerNotFoundException(id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void update(Player modifiedPlayer) throws PlayerNotFoundException, FileProcessingException {
        try {
            players = JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class);
            boolean playerUpdated = false;

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getIdPlayer().equals(modifiedPlayer.getIdPlayer())) {
                    players.set(i, modifiedPlayer);
                    playerUpdated = true;
                    break;
                }
            }
            if (!playerUpdated) {
                throw new PlayerNotFoundException(modifiedPlayer.getIdPlayer());
            }
            // Only write if the player was updated
            PersistenceFile.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void delete(Integer id) throws PlayerNotFoundException, FileProcessingException {

        try {
            boolean playerDeleted = false;
            players = JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class);

            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).getIdPlayer().equals(id)) {
                    players.remove(i);
                    playerDeleted = true;
                    break;
                }
            }
            if (!playerDeleted) {
                throw new PlayerNotFoundException(id);
            }

            PersistenceFile.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public List<Player> getAll() throws PlayerNotFoundException, FileProcessingException {
        try {
            players = JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class);

            if (players == null || players.isEmpty()) {
                throw new PlayerNotFoundException("There are no matches saved in JSON.");
            }
            return players;

        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    public Integer generatePlayerID() throws JsonProcessingException {
        players = JSONConverter.fromJsonArrayToList(getUpdatedData(), Player.class);
        Integer lastId = 0;
        for (Player player : players) {
            if (player.getIdPlayer() > lastId) {
                lastId = player.getIdPlayer();
            }
        }
        return ++lastId;
    }

    private String getUpdatedData() {
        return PersistenceFile.readFile(filePath);
    }
}