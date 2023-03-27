package Aashiqui.Exceptions;

public class InvalidData extends Throwable {
    private String message;

    public InvalidData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
