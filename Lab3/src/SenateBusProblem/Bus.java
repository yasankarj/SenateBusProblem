package SenateBusProblem;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author YRJayawardane
 */
public class Bus extends Thread{
    
    private final int SEAT_LIMIT = 50;  //maximum number of passengers bus can take
    private final SharedData sharedData;    //shared semaphores
    
    public Bus(SharedData sharedData) {
        this.sharedData = sharedData;
    }
    
    @Override
    public void run() {
        this.runMethod();
    }
    
    private void runMethod(){
        try {
            sharedData.getMutex().acquire();    //Bus waits to acquire riders mutex so that it can start boarding
            //Riders cannot enter the bus stop, till the bus releases lock
            System.out.println("Riders Queue mutex aquired by Bus "  +this.getId());
        } catch (InterruptedException ex) {
            Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //if there are more riders than SEAT_LIMIT we board SEAT_LIMIT passengers
        int boardingRidersCount = Math.min(sharedData.getWaitingRidersCount().get(), SEAT_LIMIT);
        
        System.out.format("\nRiders count in the Queue: %d\n",sharedData.getWaitingRidersCount().get());
        System.out.format("\nRiders count who can board: %d\n", boardingRidersCount);
        /*
        The loop signals each rider in turn and waits for her to board.
        By controlling the number of signals,
        the bus prevents more than 50 riders from boarding.
        */
        for(int i=0; i < boardingRidersCount; i++){
            sharedData.getBus().release();      //signals each rider that they can get into the bus
            System.out.println("Bus semaphore released by Bus " +this.getId());
            try {
                sharedData.getBoarded().acquire();
                /*
                Bus aquires boarded semaphore to say rider has boarded
                */
                System.out.println("Boarded semaphore aquired by Bus " +this.getId());
            } catch (InterruptedException ex) {
                Logger.getLogger(Bus.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /*
        Updates the waiting riders count and relase the lock so that passengers can come to the bus stop.
        Then the bus is fine to depart
        */
        int n = sharedData.getWaitingRidersCount().get();
        sharedData.getWaitingRidersCount().set(Math.max(n - 50, 0));
        sharedData.getMutex().release();
        
        System.out.println("Riders Queue mutex released by Bus "+this.getId());
        System.out.println("Bus Departs "+this.getId());
    }
}
