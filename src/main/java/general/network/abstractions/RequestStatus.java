package general.network.abstractions;

import java.io.Serializable;

public enum RequestStatus implements Serializable {
    NORMAL,
    KEY_COMMAND,
    KEY_ELEMENT_COMMAND,
    ELEMENT_COMMAND,
    POSITION_COMMAND,
    ERROR
}
