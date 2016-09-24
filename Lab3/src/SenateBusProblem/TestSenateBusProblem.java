package SenateBusProblem;

import java.util.Scanner;

/**
 *
 * @author Yasanka Jayawardane
 */
public class TestSenateBusProblem {

    private static final SharedData sharedData = new SharedData();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("To run the program with default set mean values, press 1");
        System.out.println("To configure set mean values, press 2");
        int userResponse = sc.nextInt();
        if (userResponse == 1) {
            //run program with default parameters
            testMethod(30000, 1200000);
        } else if (userResponse == 2) {
            long riderSetMean = 30000;
            long busSetMean = 1200000;

            try {
                System.out.println("Enter rider arrival set mean values, in milliseconds :");
                riderSetMean = sc.nextLong();
                System.out.println("Enter bus arrival set mean values, in milliseconds :");
                busSetMean = sc.nextLong();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input entered");
            } finally {
                //run program with default parameters
                testMethod(riderSetMean, busSetMean);
            }
        }

    }

    public static void testMethod(long riderSetMean, long busSetMean) {
        new RiderThreadRunner(sharedData, riderSetMean).start();
        new BusThreadRunner(sharedData, busSetMean).start();
    }
}
