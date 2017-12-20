package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;

public class Server {

    private static int clientCounter = 0;
    protected LinkedList<MediaFile> myMusicList = new LinkedList<>();
    protected ServerSocket soc = null;
    static Vector<waiter> ar = new Vector<waiter>();

    public void startServer() throws Exception
    {
        try {
            soc = new ServerSocket(11111);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread Server = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    Socket client = new Socket();

                    System.out.println("Server started !");

                    try {
                        client = soc.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Client connected !");
                    clientCounter++;
                    waiter clientManger = new waiter(client);
                    Thread talkWithClient = new Thread(clientManger);
                    ar.add(clientManger);
                    talkWithClient.start();
                }
            }
        });
        Server.start();


    }
}