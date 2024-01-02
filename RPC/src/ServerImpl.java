import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerImpl implements Server{

    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static final HashMap<String, Class> registryCenter = new HashMap<>();

    private static boolean running = false;

    private static int port;

    public ServerImpl(int port){
        this.port = port;
    }

    @Override
    public void stop() {
        this.running = false;
        executorService.shutdown();
    }

    public static class ServiceTask implements Runnable{

        Socket client = null;

        public ServiceTask(Socket client){
            this.client = client;
        }

        public void run(){

            ObjectInputStream input = null;
            ObjectOutputStream output = null;

            try{
                // Unserialization to obtain the content from client
                // Use the reflection to obtain the service and method
                input = new ObjectInputStream(client.getInputStream());
                String serviceName = input.readUTF();
                String methodName = input.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) input.readObject();
                Object[] arguments = (Object[]) input.readObject();
                Class serviceClass = registryCenter.get(serviceName);

                if(serviceClass==null){
                    throw new ClassNotFoundException(serviceName + " not found.");
                }

                Method method = serviceClass.getMethod(methodName, parameterTypes);
                Object result = method.invoke(serviceClass.newInstance(), arguments);

                // Unserialization and send the result to the client
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);

            } catch (Exception e){
                e.printStackTrace();
            } finally {

                if(output!=null){
                    try{
                        output.close();
                    } catch (IOException e){
                        e.printStackTrace();;
                    }
                }

                if(input!=null){
                    try{
                        input.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }

                if(client!=null){
                    try{
                        client.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void start() throws IOException {

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(port));
        System.out.println("Start the server...");

        try{
            // Keep listening to the client
            // When receiving a tcp link request, encapsulate into a task operated by ThreadPool
            while(true){
                executorService.execute(new ServiceTask(serverSocket.accept()));
            }
        } finally {
            serverSocket.close();
        }
    }

    @Override
    public void register(Class serviceInterface, Class serviceImpl) {
        System.out.println("Register service " + serviceInterface.getName() +"...");
        registryCenter.put(serviceInterface.getName(), serviceImpl);
    }

    @Override
    public boolean isRunning() {
        return this.running;
    }

    @Override
    public int getPort() {
        return this.port;
    }


}
