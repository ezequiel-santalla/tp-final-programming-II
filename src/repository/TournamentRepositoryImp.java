package repository;

import model.Tournament;
import service.PersistenceFile;

import java.io.File;
import java.util.List;

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
