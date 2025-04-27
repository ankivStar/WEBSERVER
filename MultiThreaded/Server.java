import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer(){
        // return new Consumer<Socket>() {
        //     @Override
        //     public void accept(Socket clientSocket){
        //         try{
        //             PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true); 
        //             toClient.println("Hello from the server");
        //             toClient.close();
        //             clientSocket.close();
        //         }catch(IOException ex){
        //             ex.printStackTrace();
        //         }
        //     }
        // };

        return (clientSocket)->{
            try{
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true); 
                toClient.println("Hello from the server");
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = fromClient.readLine();
                System.out.println("Client says: " + clientMessage);
                toClient.close();
                clientSocket.close();
                fromClient.close();
            }catch(IOException ex){
                ex.printStackTrace();
            }
        };
    }

    public static void main(String args[]){
        int port = 8010;
        Server server = new Server();
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(10000);
            System.out.println("Server is listening on port"+port);
            while(true){
                Socket acceptedSocket = serverSocket.accept();
                Thread thread = new Thread(()-> server.getConsumer().accept(acceptedSocket)); 
                thread.start();
            }
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
        
    }
}
