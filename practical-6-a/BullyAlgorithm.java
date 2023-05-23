import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node implements Runnable {
    private final int nodeId;
    private final List<Node> nodes;
    private boolean isActive = true;

    public Node(int nodeId, List<Node> nodes) {
        this.nodeId = nodeId;
        this.nodes = nodes;
    }

    public int getNodeId() {
        return nodeId;
    }

    public void deactivate() {
        isActive = false;
    }

    @Override
    public void run() {
        while (isActive) {
            // Perform some work
            System.out.println("Node " + nodeId + " is performing work.");

            // Check if this node is the coordinator (leader)
            if (isCoordinator()) {
                System.out.println("Node " + nodeId + " is the coordinator (leader).");
                // Perform coordinator-specific tasks
            }

            // Sleep to simulate work
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isCoordinator() {
        for (Node node : nodes) {
            if (node.getNodeId() > nodeId && node.isActive()) {
                return false;
            }
        }
        return true;
    }

    private boolean isActive() {
        return isActive;
    }
}

public class BullyAlgorithm {
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

        // Take user input for the failed node ID
        System.out.print("Enter the ID of the failed node: ");
        int failedNodeId = scanner.nextInt();
        scanner.close();

        // Validate the failed node ID
        if (failedNodeId < 0 || failedNodeId >= totalNodes) {
            System.out.println("Invalid node ID. Exiting the program.");
            System.exit(0);
        }

        // Simulate the node failure
        nodes.get(failedNodeId).deactivate();
        System.out.println("Node " + failedNodeId + " has failed.");

        // Perform leader election
        performLeaderElection(nodes);

        // Print the new leader
        System.out.println("New Leader: Node " + getLeaderId(nodes));
    }

    private static void performLeaderElection(List<Node> nodes) {
        for (Node node : nodes) {
            if (node.isActive()) {
                node.deactivate();
                System.out.println("Node " + node.getNodeId() + " initiated an election.");

                for (Node otherNode : nodes) {
                    if (otherNode.getNodeId() > node.getNodeId() && otherNode.isActive()) {
                        System.out.println("Node " + otherNode.getNodeId() + " receives election message from Node " + node.getNodeId());
                        performLeaderElection(nodes.subList(otherNode.getNodeId(), nodes.size()));
                        return;
                    }
                }

                // If no higher ID active nodes found, node becomes the leader
                node.activate();
                System.out.println("Node " + node.getNodeId() + " is the new leader.");
                return;
            }
        }
    }

    private static int getLeaderId(List<Node> nodes) {
        for (int i = nodes.size() - 1; i >= 0; i--) {
            if (nodes.get(i).isActive()) {
                return nodes.get(i).getNodeId();
            }
        }
        return -1; // No leader found
    }
}
