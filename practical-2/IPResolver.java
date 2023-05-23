import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPResolver {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n Enter the website url (like google.com) to Resolve its Name to Address:");
        String hostname = br.readLine();
        
        try {
            InetAddress inetAddress = InetAddress.getByName(hostname);
            String ipAddress = inetAddress.getHostAddress();

            System.out.println("Hostname  : " + hostname);
            System.out.println("IP Address: " + ipAddress);
        } catch (UnknownHostException e) {
            System.err.println("Unable to resolve the IP address for hostname: " + hostname);
            e.printStackTrace();
        }
    }
}
