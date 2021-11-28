package Server;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler {
    private ServMain serv;
    private Socket socket;
    String nick;
    DataInputStream in;
    DataOutputStream out;
    File history;


    public ClientHandler(ServMain serv, Socket socket) {

        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.history = new File("history_" + this.nick + ".txt");


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true){                                   // цикл взаимодействия с авторизацией клиента
                            String str = in.readUTF();
                            if (str.startsWith("/auth")){
                                String[] tokens = str.split(" ");
                                String currentNick = AuthService.getNickFromLogAndPass(tokens[1], tokens[2]);
                                if (currentNick != null){
                                    if (!nickIsBusy(currentNick)){
                                        sendMsg("/authOk");
                                        nick = currentNick;
                                        serv.subscribe(ClientHandler.this);

                                        sendMsg(nick);          // отправка ника клиенту
                                        break;

                                    } else {
                                        sendMsg("Login is busy!");
                                    }
                                } else {
                                    sendMsg("Login and/or Pass is incorrect!");
                                }
                            }
                        }

                        while (true) {                                  // цикл общения с другими клиентами
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                out.writeUTF("/clientClosed");
                                System.out.println("Client is disconnect!");
                                break;
                            }
                            serv.broadcastMsg(nick + " : " + str);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        serv.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg){
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void disconnect(){
        try {
            out.writeUTF("/end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public boolean nickIsBusy (String currantNick){
        for (ClientHandler e: serv.getClients() ) {
            if (e.getNick().equals(currantNick)) {
                return true;
            }
        }
        return false;
    }
}
