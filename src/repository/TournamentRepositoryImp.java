package repository;

import model.Tournament;

import java.util.List;

public class TournamentRepositoryImp implements Repository<Tournament, Integer> {

    @Override
    public Integer create(Tournament model) {
        return 0;
    }

    @Override
    public Tournament find(Integer id) {
        return null;
    }

    @Override
    public void update(Tournament model) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public List<Tournament> getAll() {
        return List.of();
    }
}
