package exceptions;

public class DuplicatePlayerException extends EntityNotFoundException {


    public DuplicatePlayerException(String dni) {
        super("There is already a player with that DNI: " + dni);
    }

}