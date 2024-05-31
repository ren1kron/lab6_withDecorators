package general.network.requestDecorators;

import general.models.abstractions.Element;
import general.network.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.abstractions.SendableDecorator;

public class ElementRequest extends SendableDecorator {
    private static final long serialVersionUID = 1004L;
//    private Request request;
//    private Sendable request;
    private Element element;

    public ElementRequest(Sendable sendable, Element element) {
        super(sendable);
        this.element = element;
    }
//    public ElementRequest(Sendable request, Element element) {
//        this.request = request;
//        this.element = element;
//    }
//    @Override
//    public String message() {
//        return request.message();
//    }
    public Element element() {
        return element;
    }
//    public int key() {
//        if (sendable)
//    }
//    private Request request;
//    private final Element element;
//
//    public ElementRequest(Request request, Element element) {
//        this.element = element;
//    }
//
//    @Override
//    public String getMessage() {
//        return request.getMessage();
//    }
//
//    public Element getElement() {
//        return element;
//    }
}
