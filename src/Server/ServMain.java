package Server;

import Client.Talker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServMain {
    public static void main(String[] args) {
        Socket socket = null;
        ServerSocket serv = null;
//        Talker mess1 = new Talker();


        try  {
            serv = new ServerSocket(8900);
            System.out.println("Server is running!");

            socket = serv.accept();
            System.out.println("Client has connect!");

//            Scanner in = new Scanner(socket.getInputStream());
            DataInputStream in = new DataInputStream (socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
//            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);

            while (true){
                String str = in.readUTF();
                if(str.equals("/end")){
                    break;
                }
                System.out.println("Client:" + str);
                out.writeUTF("Echo " + str);
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
