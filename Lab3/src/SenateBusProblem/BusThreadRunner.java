package SenateBusProblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Yasanka Jayawardane
 */
public class BusThreadRunner extends Thread {
    private final long setMean;
    private final SharedData sharedData;
    public BusThreadRunner(SharedData sharedData, long setMean){
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
            System.out.println("Next bus arrives in : "+sleepInterval +" ms");
            try {
                //bus thread start after sleepInterval ms
                Bus.sleep(sleepInterval);
                new Bus(sharedData).start();
            } catch (InterruptedException ex) {
                Logger.getLogger(BusThreadRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
