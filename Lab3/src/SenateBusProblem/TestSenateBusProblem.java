package SenateBusProblem;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author YRJayawardane
 */
public class TestSenateBusProblem {
    
    private static final SharedData sharedData = new SharedData();
    private static final ScheduledExecutorService busScheduler = Executors.newScheduledThreadPool(1);
    private static final ScheduledExecutorService riderScheduler = Executors.newScheduledThreadPool(1);
    
    public static void main(String[] args){
               
        for (int i = 0; i < 75; i++) {
            createRiderThread();
        }
        createBusThread();
        createBusThread();
        
        
    }
    
    public static void simulateBusArrivalActivity(long x){
       final Runnable simulateBusArrival = new Runnable() {
       @Override
       public void run() {
           createBusThread();
       }
     };
        
        final ScheduledFuture<?> busHandler =
       busScheduler.schedule(simulateBusArrival, x, TimeUnit.SECONDS);
        
    }
    
    public static void simulateRiderArrivalActivity(long x){
       final Runnable simulateBusArrival = new Runnable() {
       @Override
       public void run() {
           createRiderThread();
       }
     };
        
       riderScheduler.schedule(simulateBusArrival, x, TimeUnit.SECONDS);
        
    }
    
    public static void createBusThread(){
        new Bus(sharedData).start();
    }
    
    public static void createRiderThread(){
        new Rider(sharedData).start();
    }
}
