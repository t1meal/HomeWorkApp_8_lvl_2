package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ServMain {
    private Vector <ClientHandler> clients;

    public ServMain() {
        Socket socket = null;
        ServerSocket serv = null;
        clients = new Vector<>();

        try  {
            serv = new ServerSocket(8990);
            System.out.println("Server is running!");

            while (true){
                socket = serv.accept();
                System.out.println("Client has connect!");

                clients.add(new ClientHandler(this, socket));
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
    public void broadcastMsg(String msg){
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

}