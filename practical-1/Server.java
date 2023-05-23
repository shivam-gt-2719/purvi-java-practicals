import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            RemoteInterface remoteObj = new RemoteImplementation();
            LocateRegistry.createRegistry(Registry.REGISTRY_PORT); // Start RMI registry
            Naming.rebind("RemoteObject", remoteObj); // Bind the remote object
            System.out.println("Server started.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
