import java.io.IOException;
import java.net.InetSocketAddress;

public class RPCtest {

    public static void main(String[] args) throws IOException{

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Server server = new ServerImpl(8088);
                    server.register(Service.class, ServiceImpl.class);
                    server.start();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }).start();

        Service service = Client.getRemoteProxyObj(Service.class, new InetSocketAddress("localhost", 8088));

        System.out.println(service.test("Client1"));

        System.out.println(service.test("Client2"));
    }
}
