package exceptions;

public class PlayerNotFoundException extends EntityNotFoundException {


    public PlayerNotFoundException(String message) {
        super(message);
    }
}