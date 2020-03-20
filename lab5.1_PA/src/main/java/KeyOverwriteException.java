public class KeyOverwriteException extends Exception {
    public KeyOverwriteException(String message) {
        super("KeyName already exists: " + message + ". If you want to modify the tag value use modifyTag(String key, String value) method or delete it first using delete(String key) method");
    }
}
