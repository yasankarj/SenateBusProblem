package SenateBusProblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yasanka Jayawardane
 */
public class Rider extends Thread{
    private final SharedData sharedData;    //holds shared semaphores
    
    public Rider(SharedData sharedData) {
        this.sharedData = sharedData;
    }
        
    @Override
    public void run(){
       this.runMethod();
    }
    
    private void runMethod(){
        try {
            /*
            New rider that comes to the bus stop waits on the riders mutex 
           */
            sharedData.getMutex().acquire();
            System.out.println("Riders mutex aquired by a Rider " +this.getId() );
            
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sharedData.getWaitingRidersCount().incrementAndGet();
        /*
        New rider enters the queue by incrementing waitingRidersCount value    
        */
        
        sharedData.getMutex().release();
        /*
        After updating the waitingRidersCount, rider releases the riders mutex.
        */
        
        System.out.println("Riders mutex released by Rider " +this.getId());
        
        try {
            /*
            Rider waits to aqcuire the bus semaphore to enter the bus.
            It has to wait till Bus signals to acquire the lock.
            */
            sharedData.getBus().acquire();
            System.out.println("Bus semaphore acquired by a Rider");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.getId()+" Rider boards bus");
        sharedData.getBoarded().release();
        /*
        Rider signals that he has boarded
        */
        System.out.println("Boarded semaphore released by Rider " +this.getId());
    }
}
