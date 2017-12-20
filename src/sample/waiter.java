package sample;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class waiter extends Server implements Runnable {
    private static Socket client = new Socket();
    private static OutputStream out;
    private static FileInputStream fin;
    private DataOutputStream dout;
    private int counter = 0;

    waiter(Socket clientSoc)
    {
        client = clientSoc;
    }

    private void scanList() {
        // The name of the file to open.
        String fileName = "list.txt";

        // This will reference one line at a time
        String line;
        boolean exist = false;

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

    private void sendList() {
        LinkedList<MediaFile> tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        for(int i = 0; i < counter; i++)
        {
            try {
                dout.writeUTF(tmpMusicList.getFirst().getName());
                tmpMusicList.removeFirst();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void sendMusic() {
        LinkedList<MediaFile> tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        for(int i = 0; i < counter; i++)
        {
            try {
                fin = new FileInputStream(new File(tmpMusicList.getFirst().getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] buffer = new byte[1024];
            int len;
            int data = 0;

            try {
                while((len = fin.read(buffer)) > 0)
                    data++;
            } catch (IOException e) {
                e.printStackTrace();
            }

            int datas = data;
            try {
                fin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fin = new FileInputStream(tmpMusicList.getFirst().getPath());
                System.out.println(tmpMusicList.getFirst().getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                dout.writeInt(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dout.writeUTF(tmpMusicList.getFirst().getName());
                if(!tmpMusicList.isEmpty()) tmpMusicList.removeFirst();
            } catch (IOException e) {
                e.printStackTrace();
            }

            len = 0;

            for(; data > 0; data--)
            {
                try {
                    len = fin.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.write(buffer, 0, len);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("size: " + datas + "kb");
        }
    }
    private void sendMusic(int id) {
        LinkedList<MediaFile> tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        for(int i = 0; i < counter; i++)
        {
            if(i < id)
            {
                if(!tmpMusicList.isEmpty()) tmpMusicList.removeFirst();
                continue;
            }else
            {
                try {
                    fin = new FileInputStream(new File(tmpMusicList.getFirst().getPath()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                byte[] buffer = new byte[1024];
                int len;
                int data = 0;

                try {
                    while((len = fin.read(buffer)) > 0)
                        data++;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int datas = data;
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    fin = new FileInputStream(tmpMusicList.getFirst().getPath());
                    System.out.println(tmpMusicList.getFirst().getPath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    dout.writeInt(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    dout.writeUTF(tmpMusicList.getFirst().getName());
                    if(!myMusicList.isEmpty()) tmpMusicList.removeFirst();
                    i = counter; //break
                } catch (IOException e) {
                    e.printStackTrace();
                }

                len = 0;

                for(; data > 0; data--)
                {
                    try {
                        len = fin.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.write(buffer, 0, len);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("size: " + datas + "kb");

            }
        }
    }
    private int waitMediaID() {
        InputStream in;
        DataInputStream din;
        int ID = -1;
        //wait until I get the ID
        try {
            in = client.getInputStream();
            din = new DataInputStream(in);
            ID = din.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ID;
    }

    @Override
    public void run() {

        this.scanList();

        try {
            out = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dout = new DataOutputStream(out);
        try {
            dout.writeInt(counter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.sendList();

        while(true)
        {
            try
            {
                int id = waitMediaID();
                if(id == -1)
                    sendMusic();
                else
                    sendMusic(id);
            }catch (Exception exc)
            {
                exc.printStackTrace();
            }
        }
    }
}