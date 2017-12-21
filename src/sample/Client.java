package sample;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Client {

    protected static InputStream in = null;
    protected static DataInputStream din = null;
    protected static DataOutputStream dout = null;
    protected static LinkedList<MediaFile> recievedMusicList = null;
    private static String ipAddress;
    protected FileOutputStream out = null;
    protected Scanner sc = new Scanner(System.in);
    private static int Oct_one = 127;
    private static int Oct_two = 0;
    private static int Oct_three = 0;
    private static int Oct_four = 1;

    Client() throws IOException {

    }

    public void startClient()
    {
        Thread Client = new Thread(new Runnable() {
            @Override
            public void run() {
                ipAddress = String.valueOf(String.valueOf(Oct_one) + '.' +
                        String.valueOf(Oct_two) +
                        '.' + String.valueOf(Oct_three) +
                        '.' + String.valueOf(Oct_four));

                //while (Oct_four <= 255 && Oct_three <= 255)
                //{
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
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(Oct_four == 255) {
                    System.out.println("1no server on: " + ipAddress);
                    Oct_three++;
                    Oct_four = 0;
                } else if(Oct_four == 255 && Oct_three == 255) {
                    //e.printStackTrace();
                    System.out.println("No Servers Found !");
                }else {
                    System.out.println("no server on: " + ipAddress);
                    Oct_four++;
                }

                //}
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
