package sample;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Client_process extends Client implements Runnable {
    private int ID = -1;

    private void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }

    @Override
    public void run() {

    }

    public void getMusic(int id) {

        setID(id);

        try {
            dout.writeInt(ID);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int data = 0;
        try {
            data = din.readInt();
            String filename = din.readUTF();
            System.out.println(filename);
            File file = new File(filename);
            out = new FileOutputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }


        int datas = data;
        byte[] buffer = new byte[1024];
        int len;

        for (; data > 0; data--) {
            try {
                len = in.read(buffer);
                out.write(buffer, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("size: " + datas + "kb");
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
