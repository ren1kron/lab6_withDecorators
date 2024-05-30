package general.network.requestDecorators;

import general.network.Request;
import general.network.abstractions.Sendable;

public class ErrorRequest implements Sendable {
    private boolean status;
    private Request request;
//    private Sendable request;
    public ErrorRequest(boolean status, Request request) {
        this.status = status;
        this.request = request;
    }
    public ErrorRequest(Request request) {
        this(true, request);
    }
    @Override
    public String message() {
        return request.message();
    }

    public boolean isError() {
        return status;
    }
}


