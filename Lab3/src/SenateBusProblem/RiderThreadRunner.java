package SenateBusProblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yasanka Jayawardane
 */
public class RiderThreadRunner extends Thread {
    private final long setMean;
    private final SharedData sharedData;
    
    public RiderThreadRunner(SharedData sharedData, long setMean){
        this.sharedData = sharedData;
        this.setMean = setMean;
    }
    
    @Override
    public void run() {
        this.runMethod();
    }
    
    public void runMethod(){
        long sleepInterval = 0;
         while(true){
            sleepInterval = (long) ( -(Math.log(Math.random()))*setMean);
            System.out.println("Next rider arrives in : "+sleepInterval+" ms");
            try { 
                //rider thread starts after sleepInterval ms
                Rider.sleep(sleepInterval);
                new Rider(sharedData).start();
            } catch (InterruptedException ex) {
                Logger.getLogger(BusThreadRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
