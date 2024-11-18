package exceptions;

public class DataEntryCancelledException extends Exception {

    public DataEntryCancelledException(String message) {
        super(message);
    }

    public DataEntryCancelledException() {
        super("Data entry has cancelled");
    }
}
