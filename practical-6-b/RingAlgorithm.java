import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node implements Runnable {
    private final int nodeId;
    private final List<Node> nodes;
    private boolean isElectionInProgress = false;
    private boolean isCoordinator = false;
    private int data;

    public Node(int nodeId, List<Node> nodes) {
        this.nodeId = nodeId;
        this.nodes = nodes;
    }

    public int getNodeId() {
        return nodeId;
    }

    public boolean isCoordinator() {
        return isCoordinator;
    }

    public void setCoordinator(boolean isCoordinator) {
        this.isCoordinator = isCoordinator;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @Override
    public void run() {
        while (true) {
            if (isElectionInProgress) {
                if (isCoordinator) {
                    // Perform coordinator-specific tasks with the data
                    System.out.println("Node " + nodeId + " is the coordinator. Data: " + data);
                } else {
                    // Wait for the election to complete
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                // Wait for the election to start
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startElection() {
        isElectionInProgress = true;
        isCoordinator = true;
        passElectionMessage();
    }

    private void passElectionMessage() {
        int nextNodeIndex = (nodeId + 1) % nodes.size();
        Node nextNode = nodes.get(nextNodeIndex);
        nextNode.receiveElectionMessage();
    }

    public void receiveElectionMessage() {
        isElectionInProgress = true;
        if (!isCoordinator) {
            passElectionMessage();
        }
    }

    public void declareAsCoordinator() {
        isElectionInProgress = false;
    }
}

public class RingElectionAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Take user input for the number of nodes
        System.out.print("Enter the number of nodes: ");
        int totalNodes = scanner.nextInt();

        // Create the nodes
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < totalNodes; i++) {
            nodes.add(new Node(i, nodes));
        }

        // Start the nodes
        for (Node node : nodes) {
            new Thread(node).start();
        }

        // Take user input for the starting node ID
        System.out.print("Enter the ID of the starting node: ");
        int startingNodeId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Validate the starting node ID
        if (startingNodeId < 0 || startingNodeId >= totalNodes) {
            System.out.println("Invalid node ID. Exiting the program.");
            System.exit(0);
        }

        // Take user input for the data to send
        System.out.print("Enter the data to send: ");
        int data = scanner.nextInt();
        scanner.close();

        // Set the data for the starting node
        nodes.get(startingNodeId).setData(data);

        // Start the election
        nodes.get(startingNodeId).startElection();

        // Simulate the election completion
        int coordinatorNodeIndex = (startingNodeId + totalNodes - 1) % totalNodes;
        nodes.get(coordinatorNodeIndex).declareAsCoordinator();

        // Display the coordinator node
        System.out.println("Election completed. Node " + coordinatorNodeIndex + " is the coordinator.");
    }
}
