package general.network.abstractions;

import java.io.Serializable;

public interface Sendable extends Serializable {
    String message();
}
