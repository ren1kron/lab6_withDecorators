package general.network.requestDecorators;

import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.abstractions.SendableDecorator;

public class Response extends SendableDecorator {
    private static final long serialVersionUID = 1001L;
    private boolean status;
//    private Sendable request;
//    private Sendable request;
//    public Response(boolean status, Sendable request) {
//        this.status = status;
//        this.request = request;
//    }
    public Response(boolean status, Sendable sendable) {
        super(sendable);
        this.status = status;
    }
    public Response(Request request) {
        this(true, request);
    }

//    @Override
//    public String message() {
//        return request.message();
//    }

    public boolean isError() {
        return status;
    }
}


