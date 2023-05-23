import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BerkeleyClockSync {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of nodes: ");
        int totalNodes = scanner.nextInt();
        int[] nodeTimes = new int[totalNodes];

        System.out.println("Enter the initial clock times (hours:minutes:seconds) for each node:");
        scanner.nextLine(); // Consume the newline character after reading the totalNodes
        for (int i = 0; i < totalNodes; i++) {
            System.out.print("Node " + i + " time: ");
            String timeString = scanner.nextLine();
            nodeTimes[i] = convertTimeToSeconds(timeString);
        }

        int[] updatedTimes = Arrays.copyOf(nodeTimes, totalNodes);

        // Initial clock synchronization
        synchronizeClocks(updatedTimes);

        // Perform iterations to refine the synchronization
        for (int i = 0; i < 5; i++) {
            int averageTime = calculateAverageTime(updatedTimes);
            adjustClocks(updatedTimes, averageTime);
            synchronizeClocks(updatedTimes);
        }

        // Print the final synchronized times
        System.out.println("Final Synchronized Times:");
        for (int i = 0; i < totalNodes; i++) {
            String timeString = convertSecondsToTime(updatedTimes[i]);
            System.out.println("Node " + i + " time: " + timeString);
        }

        scanner.close();
    }

    private static void synchronizeClocks(int[] updatedTimes) {
        System.arraycopy(NODE_TIMES, 0, updatedTimes, 0, NODE_TIMES.length);
    }

    private static int calculateAverageTime(int[] times) {
        int totalNodes = times.length;
        int sum = 0;
        for (int time : times) {
            sum += time;
        }
        return sum / totalNodes;
    }

    private static void adjustClocks(int[] times, int adjustment) {
        for (int i = 0; i < times.length; i++) {
            times[i] = times[i] + adjustment;
        }
    }
}