import java.rmi.Naming;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            RemoteInterface remoteObj = (RemoteInterface) Naming.lookup("RemoteObject");
            Scanner input = new Scanner(System.in);			
			int x,y;
			System.out.println("Enter int 1: ");
			x = input.nextInt();
			System.out.println("Enter int 2: ");
			y = input.nextInt();
            System.out.println(remoteObj.BiggerNum(x, y)); // Invoke remote method
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
