import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Process {
    private int id;
    private String name;
    private int priority;
    private boolean ioBound;
    private int totalCpuTime;
    private int remainingCpuTime;

    public Process(int id, String name, int priority, boolean ioBound, int totalCpuTime) {
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.ioBound = ioBound;
        this.totalCpuTime = totalCpuTime;
        this.remainingCpuTime = totalCpuTime;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }

    public boolean isIoBound() {
        return ioBound;
    }

    public int getTotalCpuTime() {
        return totalCpuTime;
    }

    public int getRemainingCpuTime() {
        return remainingCpuTime;
    }

    public void decrementRemainingCpuTime() {
        remainingCpuTime--;
    }
}

class Scheduler {
    private Queue<Process> readyQueue;
    private List<Process> processes;
    private int quantum;

    public Scheduler(int quantum) {
        this.readyQueue = new LinkedList<>();
        this.processes = new ArrayList<>();
        this.quantum = quantum;
    }

    public void createProcess(int id, String name, int priority, boolean ioBound, int totalCpuTime) {
        Process process = new Process(id, name, priority, ioBound, totalCpuTime);
        processes.add(process);
        readyQueue.add(process);
    }

    public void run() {
        while (!readyQueue.isEmpty()) {
            Process process = readyQueue.poll();
            System.out.println("Process " + process.getName() + " is running.");

            int remainingTime = process.getRemainingCpuTime();
            int timeSlice = Math.min(quantum, remainingTime);

            for (int i = 0; i < timeSlice; i++) {
                process.decrementRemainingCpuTime();
                System.out.println("Process " + process.getName() + " executed for 1 unit of time.");

                if (process.isIoBound() && Math.random() < 0.2) {
                    System.out.println("Process " + process.getName() + " performed I/O operation.");
                    break;
                }
            }

            if (process.getRemainingCpuTime() > 0) {
                readyQueue.add(process);
                System.out.println("Process " + process.getName() + " is preempted.");
            } else {
                System.out.println("Process " + process.getName() + " completed.");
            }
        }
    }

    public void printReadyQueue() {
        System.out.println("Processes in ready queue:");
        for (Process process : readyQueue) {
            System.out.println(process.getName());
        }
        System.out.println();
    }

    public double getAverageWaitTime() {
        int totalWaitTime = 0;
        for (Process process : processes) {
            totalWaitTime += process.getTotalCpuTime() - process.getRemainingCpuTime();
        }
        return (double) totalWaitTime / processes.size();
    }

    public void printTurnaroundTimes() {
        System.out.println("Turnaround times:");
        for (Process process : processes) {
            int turnaroundTime = process.getTotalCpuTime();
            System.out.println("Process " + process.getName() + ": " + turnaroundTime);
        }
    }
}