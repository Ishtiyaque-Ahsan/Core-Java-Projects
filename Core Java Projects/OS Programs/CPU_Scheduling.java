
/*
MD ISHTIYAQUE AHSAN , A3CO
21COB300 , GL5503
S. No - 42

Taking pid , AT and BT and Priority of the processes as input
SJf Scheduling (Non-Preemptive) is implemented here
Priority Scheduling (Preemptive) is implemented here
Round Robin Scheduling  is implemented here

*/
import java.util.*;

public class CPU_Scheduling {
    public static int gantt_time_idx = 0;
    public static int maxx(int[] prio, int k, int[] BT_temp, int np) {
        int a = -6;
        int pr_idx = -2;
        int em = 0; // count no. of processes till time  = k
        for (int i = 0; i <= k; i++) {
            if (prio[i] != 0) {
                em++;
                if (prio[i] > a && BT_temp[i] != 0) {
                    // for those index in priority array whose BT is not zero yet and have some priority > 0
                    a = prio[i];
                    pr_idx = i;
                }
            }
        }
        if (pr_idx == -2) {
            // if all the processes are executed i.e., BT_temp[i] = 0
            if (em == np)
                return -2;
            else
                return -10; // there are processes available after some time of rest
        }
        return pr_idx; // index of max priority process available is returned here
    }

    public static void print(int[] arr, int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + "        ");
        }
        System.out.println();
    }
     public static void sort(int[] arr, int n) {
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    public static void printBT(int[] arr) {
        for (int i = 0; i < 50; i++) {
            if (arr[i] != -1 && arr[i] != 0)
                System.out.print(arr[i] + "        "); // print only those values of BT[] which have some non-value burst time value
        }
        System.out.println();
    }

    public static void printpr(int[] arr) {
        int i = 0;
        System.out.print("  ");
        while (arr[i] != -1) {
            System.out.print(arr[i] + " | ");
            i++;
        }
        System.out.println();
        
    }

    public static int minn(int[] BT_temp, int k) {
        int a = 999;
        int minBT_temp_idx = -10;
        for (int i = 0; i <= k; i++) {
            if (BT_temp[i] < a && BT_temp[i] != 0 && BT_temp[i] != -1) {
                a = BT_temp[i];
                minBT_temp_idx = i;
            }
        }
        return minBT_temp_idx;
    }

    public static void SJF_Sch(int[] BT, int[] AT, int[] CT, int[] Pr, int np, int[] pid,int[] gantt_time) {
        int[] BT_temp = Arrays.copyOf(BT, 50); // temp BT used to decrease BT of a process when its any portion is done
        int TimeNow = 0; // this will keep track of the time in Gantt chart
        int k = 0;
         gantt_time_idx = 0;
        gantt_time[gantt_time_idx++] = TimeNow;
        for (int i = 0; i < np; i++) {
            System.out.println("Iteration " + (i + 1));
            int process_AT = minn(BT_temp, k); // process_AT here is the index of the process in BT[] and its AT also

            // To resolve the case where the CPU sits idle for a moment
            if (process_AT == -10) { // CPU has no process to execute at this time = k
                int s = k;
                while (BT_temp[s] <= 0) {
                    s++;
                } // now BT_temp[s] > 0, and s is the arrival Time of the next Process

                process_AT = s;
                TimeNow = s;
            }
            int c = 0;
            for (int j = 0; j <= process_AT; j++) {
                if (BT[j] > 0) {
                    c++;
                }
            } // c is the sequential number/name of the process at index process_AT in BT[]
            c = c - 1; // counting processes from P0
            System.out.println("Process no. that has done is P" + pid[process_AT]);
            Pr[i] = pid[process_AT];
            CT[c] = TimeNow + BT_temp[process_AT];
            System.out.println("CT of this Process is " + CT[c]);
            TimeNow = CT[c];
            System.out.println("Time passed till now " + TimeNow);
            gantt_time[gantt_time_idx++] = TimeNow;
            k = CT[c];
            if (k >= 50) {
                k = 49;
            }
            BT_temp[process_AT] = 0;
            System.out.println();
        }
    }

    public static void Priority_Sch(int[] BT, int[] AT, int[] Priority, int[] CT, int[] Pr, int np, int[] pid,int[] gantt_time) {
        int[] BT_temp = Arrays.copyOf(BT, 50); // temp BT used to decrease BT of a process when its any portion is done
        int TimeNow = 0; // this will keep track of the time in the Gantt chart
        int k = 0;
         gantt_time_idx = 0;
        gantt_time[gantt_time_idx++] = TimeNow;
        int count = 0; // no. of iterations or no. of blocks in Gantt array
        // count is from 0 to CT of the last process done increasing sequentially
        while (true) {
            int process_AT = maxx(Priority, k, BT_temp, np); // process_AT here is the index of the process in BT[] and Process's AT also
            if (process_AT == -2) // break out of the loop if all the processes are completely executed, i.e., BT_temp[i] = 0
                break;

            // To resolve the case where the CPU sits idle for a moment
            if (process_AT == -10) { // CPU has no process to execute at this time = k
                int s = k;
                while (BT_temp[s] <= 0) {
                    s++;
                } // now BT_temp[s] > 0, and s is the arrival Time of the next Process
                process_AT = s;
                TimeNow = s;
            }
            System.out.println();
            int c = 0;
            for (int j = 0; j <= process_AT; j++) {
                if (BT[j] > 0) {
                    c++;
                }
            } // c is the sequential number/name of the process at index process_AT in BT[]
            c = c - 1; // counting processes from P0
            System.out.println("Iteration " + (count + 1));
            System.out.println("Process no. that has done is P" + pid[process_AT]);
            Pr[count] = pid[process_AT]; // adding Process c in Gantt array
            count++;
            BT_temp[process_AT]--;
            TimeNow++;
            if (BT_temp[process_AT] == 0) {
                CT[c] = TimeNow;
            }
            System.out.println("Time passed till now " + TimeNow);
            k = TimeNow;
            gantt_time[gantt_time_idx++] = TimeNow;
            if (k >= 50) {
                k = 49;
            }
        }
    }

    public static void RoundRobin_Sch(int[] BT, int[] AT, int TQ, int[] CT, int[] Pr, int np, int[] pid, int[] gantt_time) {
        int[] BT_temp = Arrays.copyOf(BT, 50); // temp BT used to decrease BT of a process when any portion is done
        int TimeNow = 0; // this will keep track of the time in the Gantt chart
        int k = 0;
         gantt_time_idx = 0;
        gantt_time[gantt_time_idx++] = TimeNow;
        Queue<Integer> q = new LinkedList<>();
        int e = 0; // index to push processes in queue P0, P1, ...(initial pushes of all processes)
        int pr_exe = 0; // sequential name of the process being popped from the queue and executing
        int pr_idx = 0; // sequential index in Pr array
        int process_AT = AT[0];
        q.add(0); 
        TimeNow = AT[0];
        e++;
        int Process_id = 0;
        while (!q.isEmpty()) {
            pr_exe = q.poll(); 
            Process_id = pid[AT[pr_exe]]; 
            Pr[pr_idx] = Process_id;
            System.out.println("Process no. that has done is P" + Process_id);
            process_AT = AT[pr_exe];
            if (BT_temp[process_AT] >= TQ) {
                TimeNow += TQ;
                BT_temp[process_AT] -= TQ;
            } else {
                TimeNow += BT_temp[process_AT];
                BT_temp[process_AT] = 0;
            }
            System.out.println("Time passed till now " + TimeNow);
            
            while (AT[e] <= TimeNow && e < np) {
                q.add(e);
                e++;
            }
            if (BT_temp[process_AT] > 0) {
                q.add(pr_exe);
            }
            if (BT_temp[process_AT] == 0) {
                CT[pr_exe] = TimeNow;
            }
            pr_idx++;
            // To resolve the case where the CPU sits idle for a moment
            if (q.isEmpty() && e < np) { // not all np processes have started for their execution
                // CPU has no process to execute at this time = TimeNow
                int s = TimeNow;
                while (BT_temp[s] <= 0) {
                    s++;
                } // now BT_temp[s] > 0, and s is the arrival Time of the next Process
                TimeNow = s;
                q.add(e);
                e++;
            }
            gantt_time[gantt_time_idx++] = TimeNow;
        }
    }

    public static void main(String[] args) {
        int np = 6;
        Scanner scanner = new Scanner(System.in);
        // System.out.print("Enter the number of processes: ");
        // np = scanner.nextInt();

        int TQ = 2;
        // System.out.print("\nEnter Time Quantum for Round Robin: ");
        // TQ = scanner.nextInt();

        int[] AT = new int[15];
        int[] pid = new int[50];
        Arrays.fill(pid, -1);
        int[] BT = new int[50];
        int[] Priority = new int[50];

        // Assuming max AT = 50
        
        // System.out.println("\nEnter Arrival Time , pid & Priority & Burst Time (BT) of the processes:");
        // for (int i = 0; i < np; i++) {
        //     AT[i] = scanner.nextInt();
        //     pid[AT[i]] = scanner.nextInt();
        //     Priority[AT[i]] = scanner.nextInt();
        //     BT[AT[i]] = scanner.nextInt();
        // }
        

        // Initialize sample values for AT, PID, Priority, and BT
        AT[0] = 4;
        AT[1] = 3;
        AT[2] = 8;
        AT[3] = 0;
        AT[4] = 6;
        AT[5] = 1;

        pid[4] = 1;
        pid[3] = 2;
        pid[8] = 3;
        pid[0] = 4;
        pid[6] = 5;
        pid[1] = 6;

        BT[4] = 8;
        BT[3] = 3;
        BT[8] = 5;
        BT[0] = 6;
        BT[6] = 2;
        BT[1] = 4;

        Priority[4] = 1;
        Priority[3] = 2;
        Priority[8] = 3;
        Priority[0] = 4;
        Priority[6] = 5;
        Priority[1] = 6;
        int[] gantt_time = new int[200];
        sort(AT, np);

        System.out.println("\nPrinting the given input in formatted form:");
        System.out.println("  Process-id   AT    Priority       BT ");
        for (int i = 0; i < np; i++) {
            System.out.printf("     %d           %d       %d        %d%n", pid[AT[i]], AT[i], Priority[AT[i]], BT[AT[i]]);
        }

        System.out.println();
        int[] CT = new int[15];
        int[] TAT = new int[15];
        int[] WT = new int[15];
        int[] Process_Gantt = new int[100];
        Arrays.fill(Process_Gantt, -1);

        for (int z = 0; z < 3; z++) {
            if (z == 0) {
                System.out.println("\nApplying SJF (Non-Preemptive) Scheduling: ");
                SJF_Sch(BT, AT, CT, Process_Gantt, np, pid,gantt_time);
            }
            if (z == 1) {
                System.out.println("\nApplying Priority (Preemptive) Scheduling: ");
                Priority_Sch(BT, AT, Priority, CT, Process_Gantt, np, pid,gantt_time);
            }
            if (z == 2) {
                System.out.println("\nApplying Round-Robin Scheduling: ");
                RoundRobin_Sch(BT, AT, TQ, CT, Process_Gantt, np, pid,gantt_time);
            }
            float avgTAT = 0;
            float avgWT = 0;
            for (int i = 0; i < np; i++) {
                TAT[i] = ((CT[i]) - (AT[i]));
                avgTAT += TAT[i];
            }

            for (int i = 0; i < np; i++) {
                WT[i] = ((TAT[i]) - (BT[AT[i]]));
                avgWT += WT[i];
            }
            float avgt = avgTAT / np;
            float avgw = avgWT / np;

            System.out.print("Printing Processes array:  ");
            for (int i = 0; i < 50; i++) {
                if (pid[i] != -1)
                    System.out.print(pid[i] + "        "); // print only those values of BT[] which have some non-value burst time value
            }
            System.out.println();
            System.out.print("\nPrinting AT array:         ");
            print(AT, np);
            System.out.print("Printing BT array:         ");
            printBT(BT);
            System.out.print("Printing CT array:         ");
            print(CT, np);
            System.out.print("Printing TAT array:        ");
            print(TAT, np);
            System.out.print("Printing WT array:         ");
            print(WT, np);

            System.out.println("Avg TAT is: " + avgt + "   Avg WT is: " + avgw);
            System.out.println("\nPrinting Processes execution Sequence in Gantt Array:  ");
            for(int i=0;i<130;i++)
                System.out.print("-");

            System.out.println();
             printpr(Process_Gantt);  
            // System.out.println();
            for(int i=0;i<130;i++)
                System.out.print("-");

            System.out.println();
            for(int i=0;i<gantt_time_idx;i++){
                if((gantt_time[i]/10)==0)
                System.out.print(""+gantt_time[i]+"   ");
                if((gantt_time[i]/10)>=1)
                System.out.print(""+gantt_time[i]+"  ");
            }
            System.out.println();
            for (int y = 0; y < 200; y++) { 
                gantt_time[y] = 0;
            }
            for (int y = 0; y < 100; y++) { // for resetting CT[] and Process_Gantt[] array for the next schedulings
                if (y < 15) {
                    CT[y] = 0;
                }
                Process_Gantt[y] = -1;
            }
        }
    }
}

