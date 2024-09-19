/*
MD ISHTIYAQUE AHSAN
21COB300
GL5503
S. No. - 42
Implement Bankers Algorithm
*/
import java.util.*;
public class bankers {
    static boolean solved(int[] ans, int process, int count) {
        for (int i = 0; i < count; i++) {
            if (ans[i] == process) {
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        System.out.println("\nIMPLEMENTING BANKERS ALGORITHM\n");
        int p = 5, r = 3;
        int[][] allocated = {
            {0, 1, 0},  // P0 // Allocation Matrix
            {2, 0, 0},  // P1
            {3, 0, 2},  // P2
            {2, 1, 1},  // P3
            {0, 0, 2}   // P4
        };
        int[][] total = {
            {7, 5, 3},  // P0 // Total instances of the resources to be used by processes Matrix
            {3, 2, 2},  // P1
            {9, 0, 2},  // P2
            {2, 2, 2},  // P3
            {4, 3, 3}   // P4
        };
        int[] available = {3, 3, 2};  // Resource instances available free now
        int[][] needed = new int[p][r];  // Extra instances of the resources to be needed by processes

        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                needed[i][j] = total[i][j] - allocated[i][j];
            }
        }

        // Printing resources allocated, resource-totalUsed, and resources-needed for processes
        System.out.println("Process  Resources-Allocated  Resources-totalUsed   Resources-Needed");
        System.out.println("           R1   R2   R3           R1   R2    R3         R1   R2    R3");
        for (int i = 0; i < p; i++) {
            System.out.print("   " + i + "       ");
            for (int j = 0; j < r; j++) {
                System.out.print(allocated[i][j] + "    ");
            }
            System.out.print("        ");
            for (int j = 0; j < r; j++) {
                System.out.print(total[i][j] + "    ");
            }
            System.out.print("        ");
            for (int j = 0; j < r; j++) {
                System.out.print(needed[i][j] + "    ");
            }
            System.out.println("        ");
        }

        // Checking if the system is in deadlock or not
        boolean deadlock = true;
        for (int i = 0; i < p; i++) {
            for (int j = 0; j < r; j++) {
                if (needed[i][j] > available[j]) {
                    break;
                }
                if (j == r - 1) {
                    deadlock = false;
                    System.out.println("Deadlock can't exist as this process P" + i + " can get its required resources");
                }
            }
        }
        if (deadlock) {
            System.out.println("System is in Deadlock");
        } else {
            System.out.println("\nSystem is not in Deadlock\n");
        }

        // Proceeding to resource allocation if the system is not in deadlock
        int[] ans = new int[p];  // Contains the processes in order of their execution depending upon resource allocations
        int count = 0;   // Index count in ans
        int process = 0; // Process whose availability and need is compared

        while (count < p) {
            if (!solved(ans, process, count)) {
                // Process is not solved yet
                int rc = 0; // Resource count = no. of complete resources(all required instances) which can be given to this process now
                for (int j = 0; j < r; j++) {
                    if (needed[process][j] > available[j]) {
                        break;
                    }
                    rc++;
                }
                if (rc == r) {
                    ans[count++] = process;
                    System.out.print("P" + process + " is executed \nAvailability Status of Resources are: ");
                    // Free its resources
                    for (int j = 0; j < r; j++) {
                        available[j] += allocated[process][j];
                        System.out.print(available[j] + " ");
                    }
                    System.out.println();
                    // Now go for succeeding processes
                    process = 0;
                } else {
                    process++;
                }
            } else {
                process++;
            }
        }
        System.out.println("\nPrinting safe sequence of processes to be allocated the resources they needed");
        for (int i = 0; i < count; i++) {
            System.out.print("P" + ans[i]);
            if (i < count - 1) {
                System.out.print(" -> ");
            }
        }
    }

    
}
