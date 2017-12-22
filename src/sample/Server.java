package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Vector;
import java.util.List;

public class Server {

    private static int clientCounter = 0;
    protected static LinkedList<MediaFile> myMusicList = null;
    protected ServerSocket soc = null;
    protected static Vector<waiter> ar = new Vector<waiter>();
    protected int counter = 0;



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

    public void scanList() {
        // The name of the file to open.
        String fileName = "list.txt";

        // This will reference one line at a time
        String line;
        boolean exist = false;
        myMusicList = new LinkedList<>();

        while (!exist)
        {
            try {
                // FileReader reads text files in the default encoding.
                FileReader fileReader =
                        new FileReader(fileName);

                // Always wrap FileReader in BufferedReader.
                BufferedReader bufferedReader =
                        new BufferedReader(fileReader);


                while ((line = bufferedReader.readLine()) != null) {
                    MediaFile tmp = new MediaFile();

                    exist = true;
                    tmp.setPath(line);
                    tmp.setName(line.substring(line.lastIndexOf('\\') + 1, line.length()));
                    tmp.setIndex(counter);
                    myMusicList.addLast(tmp);
                    counter++;
                }

                // Always close files.
                bufferedReader.close();
            } catch (FileNotFoundException ex) {

                Writer writer = null;

                try {
                    writer = new BufferedWriter(new OutputStreamWriter(
                            new FileOutputStream("list.txt"), "utf-8"));
                } catch (IOException exc) {
                    exc.printStackTrace();
                } finally {
                    try {
                        writer.close();
                    } catch (Exception exc) {
                        //
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    //protected int getNumberOfItems() { return 0; }

	
	public void fileEdit(ActionEvent event)
    {
        FileChooser fc = new FileChooser();
        fc.setTitle("Share the list");
        List<File> files = fc.showOpenMultipleDialog(((Node) event.getSource()).getScene().getWindow());
        for(File e : files)
        {
            System.out.println("Absolute path: " + e.getAbsolutePath());

            BufferedWriter writer = null;

            try {
                writer = new BufferedWriter(new FileWriter("list.txt", true));
                BufferedReader br = new BufferedReader(new FileReader("list.txt"));
                if (br.readLine() != null) {
                    writer.append('\n');
                }
                br.close();
                writer.append(e.getAbsolutePath());
                MediaFile tmpMediaFile = new MediaFile();
                tmpMediaFile.setPath(e.getAbsolutePath());
                tmpMediaFile.setName(e.getAbsolutePath().substring(e.getAbsolutePath().lastIndexOf('\\') + 1, e.getAbsolutePath().length()));
                tmpMediaFile.setIndex(myMusicList.getLast().getIndex() + 1);
                myMusicList.addLast(tmpMediaFile);
            } catch (IOException exc) {
                // report
            } finally {
                try {
                    writer.close();
                } catch (Exception exc) {}
            }

        }
    }
}