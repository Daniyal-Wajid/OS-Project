package Process;

import javax.swing.*;
import java.util.*;

public class Scheduler {

    public static PCB performFCFS(Queue<PCB> readyQueue, Map<Integer, PCB> processTable, JFrame frame) {
        if (readyQueue.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Ready Queue is empty.");
            return null;
        }

        // Sort the readyQueue based on arrival time before processing
        List<PCB> sortedProcesses = new ArrayList<>(readyQueue);
        sortedProcesses.sort(Comparator.comparingInt(PCB::getArrivalTime));

        // Process each process in arrival order
        for (PCB process : sortedProcesses) {
            readyQueue.remove(process); // Remove the process from the ready queue
            process.setState("Running");
            JOptionPane.showMessageDialog(frame, "Running Process: " + process.getProcessID());

            // Simulate process completion
            process.setState("Completed");
            processTable.remove(process.getProcessID()); // Remove from process table after completion
        }

        // Return the last process completed (optional)
        return sortedProcesses.isEmpty() ? null : sortedProcesses.get(sortedProcesses.size() - 1);
    }

    public static PCB performSJF(Queue<PCB> readyQueue, Map<Integer, PCB> processTable, JFrame frame) {
        if (readyQueue.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Ready Queue is empty.");
            return null;
        }

        PCB shortestProcess = readyQueue.stream()
                .min(Comparator.comparingInt(PCB::getBurstTime))
                .orElse(null);

        if (shortestProcess != null) {
            readyQueue.remove(shortestProcess);
            shortestProcess.setState("Running");
            JOptionPane.showMessageDialog(frame, "Running Process: " + shortestProcess.getProcessID());
            shortestProcess.setState("Completed");
            processTable.remove(shortestProcess.getProcessID());
            return shortestProcess;
        }
        return null;
    }
}
