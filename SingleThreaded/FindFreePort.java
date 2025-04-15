import java.net.ServerSocket;

public class FindFreePort {
    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(0)) {
            int port = socket.getLocalPort();
            System.out.println("Free port found: " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

