package exceptions;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException() {
        super();
    }

    public FileProcessingException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
