package general.network.abstractions;

import java.io.Serializable;

public enum RequestStatus implements Serializable {
    NORMAL,
    KEY_COMMAND,
    ELEMENT_COMMAND,
    ERROR
}
