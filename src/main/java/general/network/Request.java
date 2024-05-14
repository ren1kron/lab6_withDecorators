package general.network;

import general.models.abstractions.Element;
import general.network.abstractions.RequestStatus;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private RequestStatus status;
    private Element element;
    private int key;
    public Request(RequestStatus status, String message, Element element, int key) {
        this.status = status;
        this.message = message;
        this.element = element;
        this.key = key;
    }
    public Request(String commandWithKey, int key) {
//        this.message = commandWithKey;
//        this.key = key;
        this(RequestStatus.KEY_COMMAND, commandWithKey, null, key);
    }
    public Request(String message) {
//        this(message, 0);
        this(RequestStatus.NORMAL, message, null, 0);
    }
    public Request(String commandWithElement, int key, Element element) {
//        this.element = element;
//        this(RequestStatus.ELEMENT_COMMAND, null, null, 0);
        this(RequestStatus.ELEMENT_COMMAND, commandWithElement, element, key);
    }

    public String getMessage() {
        return message;
    }

    public Element getElement() {
        return element;
    }

    public int getKey() {
        return key;
    }

    public RequestStatus getStatus() {
        return status;
    }
}
