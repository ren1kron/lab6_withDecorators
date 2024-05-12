package general.network;

import general.network.abstractions.ResponseStatus;

public class Request {
    private String message;
    private ResponseStatus status;

    public Request( ResponseStatus status, String message) {
        this.message = message;
        this.status = status;
    }
    public Request(String message) {
        this(ResponseStatus.NORMAL, message);
    }

    public String getMessage() {
        return message;
    }

    public ResponseStatus getStatus() {
        return status;
    }
}
