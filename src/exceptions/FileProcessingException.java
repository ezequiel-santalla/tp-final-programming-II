package exceptions;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException() {
        super();
    }

    public FileProcessingException(String message) {
        super("Error processing the file " + message);
    }

}