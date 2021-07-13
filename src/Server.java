import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args) {
        ServerSocket serv = null;
        Socket socket = null;

        try {
            serv = new ServerSocket(8888);
            System.out.println("Server is running!");

            socket = serv.accept();
            System.out.println("Client has connect!");

            Scanner in = new Scanner(socket.getInputStream());

            while (true){
                String str = in.nextLine();
                System.out.println(str);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                serv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
