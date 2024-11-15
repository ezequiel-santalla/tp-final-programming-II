package exceptions;

public class PlayerNotFoundException extends EntityNotFoundException {


    public PlayerNotFoundException(Integer id) {
        super("No player was found with the ID: " + id);
    }

    public PlayerNotFoundException(String message) {
        super(message);
    }
}