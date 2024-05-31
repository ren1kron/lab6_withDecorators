package server.commandRealization.interfaces;

//import general.network.depricated.Request;
import general.network.abstractions.Sendable;
import general.network.requestDecorators.Response;

/**
 * All executable commands implement this interface
 * @author ren1kron
 */
public interface Executable {
    /**
     * Apply command
     *
     * @param arguments Arguments for applying command
     * @return result of executing command
     */
    Response apply(Sendable request);
}
