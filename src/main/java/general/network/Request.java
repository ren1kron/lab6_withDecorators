package general.network;

import general.network.abstractions.Sendable;

import java.io.Serializable;

public record Request(String message) implements Sendable, Serializable {
    private static final long serialVersionUID = 100L;
}
