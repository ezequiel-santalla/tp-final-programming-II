package exceptions;

public class TournamentNotFoundException extends RuntimeException {

  public TournamentNotFoundException(String message) {
    super(message);
  }

  @Override
  public String getMessage() {
    return super.getMessage();
  }
}