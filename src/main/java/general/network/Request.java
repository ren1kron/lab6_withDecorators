package general.network;

import general.network.abstractions.ResponseStatus;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
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
