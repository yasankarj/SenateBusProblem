package SenateBusProblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YRJayawardane
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
            to enter the queue (to update waiting_riders_count value)
            */
            sharedData.getMutex().acquire();
            /*
            New rider acuires the riders mutex
            */
            System.out.println("Riders mutex aquired by a Rider " +this.getId() );
            
        
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        sharedData.getWaitingRidersCount().incrementAndGet();
        /*
        New rider enters the queue by incrementing waiting_riders_count value    
        */
        
        sharedData.getMutex().release();
        /*
        New rider releases the riders mutex after entering the queue
        */
        
        System.out.println("Riders mutex released by Rider " +this.getId());
        
        try {
            /*
            Now the rider wait to aqcuire the bus semaphore to enter the bus.
            It has to wait till Bus signals to acquire the lock.
            */
            sharedData.getBus().acquire();
            /*
            Rider is able to acquire the bus semaphore
            */
            
            System.out.println("Bus semaphore acquired by a Rider");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Rider.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(this.getId()+" Rider boards bus");
        sharedData.getBoarded().release();
        /*
        Rider signals that he has boarded
        */
        System.out.println("Boarded semaphore released by Rider");
    }
}
