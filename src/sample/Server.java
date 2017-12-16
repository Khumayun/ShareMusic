package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Server {

    protected LinkedList<MediaFile> myMusicList = new LinkedList<>();

    public void startServer() throws Exception
    {

        waiter w = new waiter();
        Thread thread = new Thread(w);
        thread.start();


    }
}