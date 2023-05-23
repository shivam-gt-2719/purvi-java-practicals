import java.io.*;
import java.net.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class HostnameResolver {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\n Enter the IP address to Resolve its Name to Host name:");
        String ipAddress = br.readLine();
        
        try {
            InetAddress inetAddress = InetAddress.getByName(ipAddress);
            String hostname = inetAddress.getHostName();

            System.out.println("IP Address: " + ipAddress);
            System.out.println("Hostname  : " + hostname);
        } catch (UnknownHostException e) {
            System.err.println("Unable to resolve the hostname for IP address: " + ipAddress);
            e.printStackTrace();
        }
    }
}
