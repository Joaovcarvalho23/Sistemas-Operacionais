package gerenciamento_ES;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*Classe que implementa o algoritmo de escalonamento de braço de disco SCAN*/
class DiskScheduler {
    private int diskSize;
    private int initialHeadPosition;
    private int minBlock;
    private int maxBlock;
    private List<Integer> requestQueue;

    public DiskScheduler(int diskSize, int initialHeadPosition, int minBlock, int maxBlock) {
        this.diskSize = diskSize;
        this.initialHeadPosition = initialHeadPosition;
        this.minBlock = minBlock;
        this.maxBlock = maxBlock;
        this.requestQueue = new ArrayList<>();
    }

    public void generateRandomRequests(int numRequests) {
        Random rand = new Random();
        for (int i = 0; i < numRequests; i++) {
            int block = rand.nextInt(maxBlock - minBlock + 1) + minBlock;
            requestQueue.add(block);
        }
        Collections.sort(requestQueue);
    }

    public void scanAlgorithm() {
        int totalTime = 0;
        int currentHeadPosition = initialHeadPosition;
        int totalSeekTime = 0;

        System.out.println("Starting SCAN algorithm...");

        // Moving towards higher block numbers
        for (int i = 0; i < requestQueue.size(); i++) {
            int requestedBlock = requestQueue.get(i);
            int seekDistance = Math.abs(requestedBlock - currentHeadPosition);
            totalSeekTime += seekDistance;
            totalTime += seekDistance;

            System.out.println("Move from " + currentHeadPosition + " to " + requestedBlock + " (" + seekDistance + " u.t.)");
            currentHeadPosition = requestedBlock;
        }

        // Moving back to the beginning
        totalSeekTime += currentHeadPosition;
        totalTime += currentHeadPosition;

        System.out.println("Move from " + currentHeadPosition + " to " + minBlock + " (" + currentHeadPosition + " u.t.)");
        currentHeadPosition = minBlock;

        System.out.println("Total seek time: " + totalSeekTime + " u.t.");
        System.out.println("Total time taken: " + totalTime + " u.t.");
    }
}

public class Main_SCAN {
    public static void main(String[] args) {
        int diskSize = 200;
        int initialHeadPosition = 50;
        int minBlock = 0;
        int maxBlock = 199;
        int numRequests = 10;

        DiskScheduler diskScheduler = new DiskScheduler(diskSize, initialHeadPosition, minBlock, maxBlock);
        diskScheduler.generateRandomRequests(numRequests);
        diskScheduler.scanAlgorithm();
    }
}
