package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Server {

    private static int clientCounter = 0;
    protected LinkedList<MediaFile> myMusicList = new LinkedList<>();
    protected ServerSocket soc = null;

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
                    Thread talkWithClient = new Thread(new waiter(client));
                    talkWithClient.start();
                }
            }
        });
        Server.start();


    }
}