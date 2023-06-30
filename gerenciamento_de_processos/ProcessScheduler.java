public class ProcessScheduler {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler(3);
        scheduler.createProcess(1, "Process1", 1, true, 8);
        scheduler.createProcess(2, "Process2", 2, false, 6);
        scheduler.createProcess(3, "Process3", 1, false, 4);
        scheduler.createProcess(4, "Process4", 2, true, 2);

        scheduler.printReadyQueue();
        scheduler.run();

        System.out.println("Average wait time: " + scheduler.getAverageWaitTime());
        scheduler.printTurnaroundTimes();
    }
}