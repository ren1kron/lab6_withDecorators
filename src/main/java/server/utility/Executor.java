package server.utility;

import general.network.Request;

public interface Executor {
    Request execute(Request request);
}
