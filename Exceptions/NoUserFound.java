package Aashiqui.Exceptions;

public class NoUserFound extends Throwable {
    private String message;

    public NoUserFound(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
