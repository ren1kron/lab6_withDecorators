package general.network.requestDecorators;

import general.models.Position;
import general.network.Request;
import general.network.abstractions.Sendable;

public class PositionRequest implements Sendable {
    private Request request;
//    private Sendable request;
    private Position position;
    public PositionRequest(Request request, Position position) {
        this.request = request;
        this.position = position;
    }

    @Override
    public String message() {
        return request.message();
    }

    public Position getPosition() {
        return position;
    }
}
