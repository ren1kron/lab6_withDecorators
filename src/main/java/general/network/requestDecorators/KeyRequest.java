package general.network.requestDecorators;

import general.network.Request;
import general.network.abstractions.Sendable;

public class KeyRequest implements Sendable {
    private Request request;
//    private Sendable request;
    private int key;
    public KeyRequest(Request request, int key) {
        this.request = request;
        this.key = key;
    }
    @Override
    public String message() {
        return request.message();
    }

    public int key() {
        return key;
    }
}
