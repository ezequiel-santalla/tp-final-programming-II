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
    private String data;


    public PlayerRepositoryImp() {
        this.filePath = "data/player.json";
    }

    @Override
    public Integer create(Player player) {
        String data = PersistenceFile.readFile(filePath);

        try {
            LinkedHashSet<Player> playersSet = new LinkedHashSet<>(JSONConverter.fromJsonArrayToList(data, Player.class));
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
        String data = PersistenceFile.readFile(filePath);

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);
            for (Player p : players) {
                if (p.getIdPlayer().equals(id)) {
                    return p;
                }
            }
            throw new PlayerNotFoundException(id);
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void update(Player modifiedPlayer) throws PlayerNotFoundException, FileProcessingException {
        String data = PersistenceFile.readFile(filePath);

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
                throw new PlayerNotFoundException(modifiedPlayer.getIdPlayer());
            }

            PersistenceFile.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public void delete(Integer id) throws PlayerNotFoundException, FileProcessingException {
        String data = PersistenceFile.readFile(filePath);

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
                throw new PlayerNotFoundException(id);
            }

            PersistenceFile.writeFile(filePath, JSONConverter.toJson(players));
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }

    @Override
    public List<Player> getAll() throws PlayerNotFoundException, FileProcessingException {
        String data = PersistenceFile.readFile(filePath);

        try {
            players = JSONConverter.fromJsonArrayToList(data, Player.class);

            if (players == null || players.isEmpty()) {
                throw new PlayerNotFoundException("There are no matches saved in JSON.");
            }
            return players;
        } catch (JsonProcessingException e) {
            throw new FileProcessingException(filePath);
        }
    }
    public Integer generatePlayerID() throws JsonProcessingException {
        data = PersistenceFile.readFile(filePath);
        players = JSONConverter.fromJsonArrayToList(data, Player.class);
        Integer lastId = 0;
        for(Player player : players){
            if(player.getIdPlayer()>lastId){
                lastId=player.getIdPlayer();
            }
        }
        return ++lastId;
    }
}
