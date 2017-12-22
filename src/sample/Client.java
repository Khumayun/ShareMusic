package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    protected static InputStream in = null;
    protected FileOutputStream out = null;
    protected static DataInputStream din = null;
    protected static DataOutputStream dout = null;
    protected static LinkedList<MediaFile> recievedMusicList = null;
    private static String ipAddress;
    protected Scanner sc = new Scanner(System.in);
    private static int Oct_one = 192;
    private static int Oct_two = 168;
    private static int Oct_three = 0;
    private static int Oct_four = 12;
    InetAddress ipAddr = null;

    Client() {
        try {
            ipAddr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        int i = 0;
        byte[] bytes = ipAddr.getAddress();
        for (byte b : bytes) {
            switch (i)
            {
                case 0:
                    Oct_one = b & 0xFF;
                    i++;
                    break;
                case 1:
                    Oct_two = b & 0xFF;
                    i++;
                    break;
                case 2:
                    Oct_three = b & 0xFF;
                    i++;
                    break;
            }
        }
    }

    private int getThirdOctet() {
        byte[] bytes = ipAddr.getAddress();
        int i = 0;
        for (byte b : bytes) {
            i++;
            if(i != 3)
                continue;
            return (b & 0xFF);
        }
        return -1;
    }
    public void startClient() {
        Thread Client = new Thread(new Runnable() {
            @Override
            public void run() {
                ipAddress = String.valueOf(String.valueOf(Oct_one) + '.' +
                        String.valueOf(Oct_two) +
                        '.' + String.valueOf(Oct_three) +
                        '.' + String.valueOf(Oct_four));
                ExecutorService executor = Executors.newFixedThreadPool(5);
                while (Oct_four <= 255)
                {
                    ipAddress = String.valueOf(String.valueOf(Oct_one) + '.' +
                            String.valueOf(Oct_two) +
                            '.' + String.valueOf(Oct_three) +
                            '.' + String.valueOf(Oct_four));
                    //THREAD IN FOR(;;)
                    //{
                    //IsReacheable() -> new process().run();
                    //}
                    try {
                        if(InetAddress.getByName(ipAddress).isReachable(10)) {
                            connectToServer();
                            Thread processData = new Thread(new Client_process());
                            processData.start();
                            //executor.execute(processData);
                            //executor.shutdown();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(Oct_four == 255) {
                        System.out.println("No Servers Found !");
                        Oct_four = 0;
                    }else {
                        System.out.println("no server on: " + ipAddress);
                        Oct_four++;
                    }
                }
            }
        });
        Client.start();
    }
    private void getList(int len) throws IOException {
        String inputfilename;
        MediaFile tmpMediaFile = null;
        for(int i = 0; i < len; i++)
        {
            tmpMediaFile = new MediaFile();
            inputfilename = din.readUTF();
            tmpMediaFile.setName(inputfilename);
            recievedMusicList.addLast(tmpMediaFile);
            System.out.println(i + ") " + inputfilename);
        }
    }
    private void connectToServer() {
        recievedMusicList = new LinkedList<MediaFile>();
        try
        {
            Socket soc = new Socket(ipAddress, 11111);

            System.out.println("Client start !");
            OutputStream Streamout  = soc.getOutputStream();
            in = soc.getInputStream();
            din = new DataInputStream(in);
            dout = new DataOutputStream(Streamout);

            int listLength = din.readInt();
            System.out.println("list length: " + listLength);

            getList(listLength);

        }catch (Exception e)
        {
            //
        }
    }

}
