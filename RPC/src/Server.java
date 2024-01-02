import java.io.IOException;

public interface Server {

    // Stop the service
    public void stop();

    // Start the service
    public void start() throws IOException;

    // Register the service
    public void register(Class serviceInterface, Class impl);

    // Judge whether the server is running
    public boolean isRunning();

    // Obtain the port number of service
    public int getPort();
}
