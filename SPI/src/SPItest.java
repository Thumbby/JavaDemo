import service.MyService;

import java.util.ServiceLoader;

public class SPItest {
    public static void main(String[] args){
        ServiceLoader<MyService> serviceLoader = ServiceLoader.load(MyService.class);

        for(MyService myService : serviceLoader){
            myService.startService();
            System.out.printf("The current service is %s with id %d\n", myService.getName(), myService.getId());
        }
    }
}
