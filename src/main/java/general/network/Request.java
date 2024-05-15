package general.network;

import general.models.Position;
import general.models.abstractions.Element;
import general.network.abstractions.RequestStatus;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String message;
    private RequestStatus status;
    private Element element;
    private int key;
    private Position position;

    private Request(RequestStatus status, String message, Element element, int key, Position position) {
        this.status = status;
        this.message = message;
        this.element = element;
        this.key = key;
        this.position = position;
    }
//    public Request(RequestStatus status, String message, Element element, int key) {
//        this.status = status;
//        this.message = message;
//        this.element = element;
//        this.key = key;
//    }
    private Request(RequestStatus status, String message, Element element, int key) {
        this(status, message, element, key, null);
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
    public Request(String commandWithKeyAndElement, int key, Element element) {
//        this.element = element;
//        this(RequestStatus.KEY_ELEMENT_COMMAND, null, null, 0);
        this(RequestStatus.KEY_ELEMENT_COMMAND, commandWithKeyAndElement, element, key);
    }
    public Request(String commandWithElement, Element element) {
        this(RequestStatus.ELEMENT_COMMAND, commandWithElement, element, 0);
    }
    public Request(String commandWithPosition, Position position) {
        this(RequestStatus.POSITION_COMMAND, commandWithPosition,null, 0, position);
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

    public Position getPosition() {
        return position;
    }
}
