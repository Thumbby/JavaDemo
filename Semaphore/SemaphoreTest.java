import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    public static void main(String[] args){
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(5);
        for(int i=0;i<20;i++){
            final int id = i;
            Runnable run = () -> {
                try{
                    // Acquire the semaphore
                    semaphore.acquire();
                    System.out.println("Accessing " + id);
                    // Simulate thread's working procedure
                    Thread.sleep((long) (Math.random() * 10000));
                    // Release the semaphore
                    semaphore.release();
                    System.out.println("Available semaphore:"+ semaphore.availablePermits());
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            };
            executorService.execute(run);
        }
        executorService.shutdown();
    }
}