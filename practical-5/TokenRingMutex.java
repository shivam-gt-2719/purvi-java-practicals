import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Node implements Runnable {
    private final int nodeId;
    private final List<Node> nodes;
    private boolean hasToken = false;
    private int data;

    public Node(int nodeId, List<Node> nodes) {
        this.nodeId = nodeId;
        this.nodes = nodes;
    }

    public int getNodeId() {
        return nodeId;
    }

    public boolean hasToken() {
        return hasToken;
    }

    public void setToken(boolean hasToken) {
        this.hasToken = hasToken;
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
            if (hasToken) {
                // Perform some work with the token and data
                System.out.println("Node " + nodeId + " has the token and data: " + data);
                // Release the token
                passToken();
            } else {
                // Wait for the token
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void passToken() {
        int nextNodeIndex = (nodeId + 1) % nodes.size();
        Node nextNode = nodes.get(nextNodeIndex);
        nextNode.setToken(true);
        nextNode.setData(data);
        setToken(false);
    }
}

public class TokenRingAlgorithm {
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

        // Set the token for the starting node
        nodes.get(startingNodeId).setToken(true);

        // Take user input for the data to send
        System.out.print("Enter the data to send: ");
        int data = scanner.nextInt();
        scanner.close();

        // Set the data for the starting node
        nodes.get(startingNodeId).setData(data);

        // Perform token ring algorithm
        System.out.println("Token Ring Algorithm started. Node " + startingNodeId + " has the token.");
    }
}
