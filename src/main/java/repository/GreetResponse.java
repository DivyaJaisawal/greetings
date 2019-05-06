package repository;

import java.io.Serializable;

public class GreetResponse implements Serializable {

private String message;

    public GreetResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
