package mygame.exceptions;

public class PlayerNotFoundException extends Exception {
    public PlayerNotFoundException() {}
    public PlayerNotFoundException(String message) {
        super(message);
    }
}
