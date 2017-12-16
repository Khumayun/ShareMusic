package sample;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.List;

public class Controller extends Server {
    public void pressButton(ActionEvent event)
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
