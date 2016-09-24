package SenateBusProblem;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Yasanka Jayawardane
 */
public class SharedData {
    private final AtomicInteger waitingRidersCount = new AtomicInteger(0);    //the number of riders in the boarding area
    private final Semaphore mutex = new Semaphore(1);   //protects   waitingRidersCount, so that it controls access to passenger to get onboard
    private final Semaphore bus = new Semaphore(0);     //signals when the bus has arrived
    private final Semaphore boarded = new Semaphore(0);     //signals that a rider has boarded.

    public AtomicInteger getWaitingRidersCount() {
        return waitingRidersCount;
    }

    public Semaphore getMutex() {
        return mutex;
    }

    public Semaphore getBus() {
        return bus;
    }

    public Semaphore getBoarded() {
        return boarded;
    }
}
