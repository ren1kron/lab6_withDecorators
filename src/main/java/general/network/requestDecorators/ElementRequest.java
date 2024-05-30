package general.network.requestDecorators;

import general.models.abstractions.Element;
import general.network.Request;
import general.network.abstractions.Sendable;

public class ElementRequest implements Sendable {
    private Request request;
//    private Sendable request;
    private Element element;

    public ElementRequest(Request request, Element element) {
        this.request = request;
        this.element = element;
    }

    @Override
    public String message() {
        return request.message();
    }

    public Element element() {
        return element;
    }
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
