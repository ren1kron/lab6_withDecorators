package general.network.requestDecorators.abstractions;

import general.network.abstractions.Sendable;

public abstract class SendableDecorator implements Sendable {
    protected Sendable sendable;
    public SendableDecorator(Sendable sendable) {
        this.sendable = sendable;
    }
    @Override
    public String message() {
        return sendable.message();
    }
}
