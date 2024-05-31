package general.network.requestDecorators;

import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.abstractions.SendableDecorator;

public class KeyRequest extends SendableDecorator {
    private static final long serialVersionUID = 100003L;
//    private Request request;
//    private Sendable request;
    private int key;

    public KeyRequest(Sendable sendable, int key) {
        super(sendable);
        this.key = key;
    }

    //    public KeyRequest(Sendable request, int key) {
//        this.request = request;
//        this.key = key;
//    }
//    @Override
//    public String message() {
//        return request.message();
//    }

    public int key() {
        return key;
    }
}
