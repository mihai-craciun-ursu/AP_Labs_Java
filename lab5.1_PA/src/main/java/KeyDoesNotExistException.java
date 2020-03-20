public class KeyDoesNotExistException extends Exception {
    public KeyDoesNotExistException(String message) {
        super("Key: " + message + "does not exist in the tag list of the document");
    }
}
