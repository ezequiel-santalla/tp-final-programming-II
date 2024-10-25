package repository;

import model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerRepositoryImp implements Repository<Player, Integer> {
    private List<Player> players = new ArrayList<>();

    @Override
    public Integer create(Player model) {
        players.add(model);

        return model.getId();
    }

    @Override
    public Player find(Integer id) {
        for (Player p : players) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    @Override
    public void update(Player model) {
        for (Player p : players) {
            if (model.getId().equals(p.getId())) {
                p.setName(model.getName());
                p.setLastName(model.getLastName());
                p.setNationality(model.getNationality());
                p.setDateOfBirth(model.getDateOfBirth());
                p.setPoints(model.getPoints());
            }
        }
    }

    @Override
    public void delete(Integer id) {
        for (Player p : players) {
            if (p.getId().equals(id)) {
                players.remove(p);
            }
        }
    }

    @Override
    public List<Player> getAll() {
        return List.of();
    }
}
