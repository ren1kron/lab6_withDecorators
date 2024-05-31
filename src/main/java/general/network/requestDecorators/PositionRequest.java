package general.network.requestDecorators;

import general.models.Position;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.abstractions.SendableDecorator;

public class PositionRequest extends SendableDecorator {
    private static final long serialVersionUID = 1002L;
//    private Sendable request;
//    private Sendable request;
    private Position position;

    public PositionRequest(Sendable sendable, Position position) {
        super(sendable);
        this.position = position;
    }

//    public PositionRequest(Sendable request, Position position) {
//        this.request = request;
//        this.position = position;
//    }
//    @Override
//    public String message() {
//        return request.message();
//    }

    public Position getPosition() {
        return position;
    }
}
