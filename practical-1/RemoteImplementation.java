import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RemoteImplementation extends UnicastRemoteObject implements RemoteInterface {
    public RemoteImplementation() throws RemoteException {
        // Constructor
    }

    @Override
    public int BiggerNum(int x, int y) throws RemoteException {
        // Method implementation
        if(x > y) {
            return x;
        }
        else {
            return y;
        }

    }
    // Implement more methods as needed
}
