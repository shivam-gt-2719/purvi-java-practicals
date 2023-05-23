import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote {
    int BiggerNum(int x, int y) throws RemoteException;
    // Add more remote methods as needed
}
