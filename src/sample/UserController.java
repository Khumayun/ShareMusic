package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.*;
import java.util.Vector;


public class UserController extends Server {

    public Button share, refresh;
    public MenuItem searchServer;
    public ListView<String> userList;

    ObservableList<String> userItems = FXCollections.observableArrayList();

    LinkedList<MediaFile> tmpMusicList;
    int serverItemIndex = 0, userItemIndex = 0;


    public void handleSearchServer() throws IOException
    {
        Client visitor = new Client();
        visitor.startClient();

        FXMLLoader fxmlLoader = new FXMLLoader((getClass().getResource("server.fxml")));
        Parent root = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Search Server");
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void handleGetMusic()
    {
        int tmp = userList.getSelectionModel().getSelectedIndex();
        System.out.println(tmp);
    }

    public void handleShareClick(ActionEvent event) {

        Vector<waiter> tmpVectorList = (Vector<waiter>) ar.clone();
        tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        userItems.clear();
        fileEdit(event);

        //Display the list on User's window

        while (!tmpMusicList.isEmpty()) {
            //System.out.println(tmpMusicList.getFirst().getIndex());
            userItems.add(tmpMusicList.getFirst().getIndex(), tmpMusicList.getFirst().getName());
            tmpMusicList.removeFirst();
            userItemIndex++;
            userList.setItems(userItems);
        }
        tmpMusicList.clear();
    }

    public void handleRefreshMusic()
    {
        tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        userItems.clear();
        while (!tmpMusicList.isEmpty()) {
            //System.out.println(tmpMusicList.getFirst().getIndex());
            userItems.add(tmpMusicList.getFirst().getIndex(), tmpMusicList.getFirst().getName());
            tmpMusicList.removeFirst();
            userItemIndex++;
            userList.setItems(userItems);
        }
        tmpMusicList.clear();

    }

    /*public void handleDownloadSelClick()
    {
        ObservableList<String> tmp;
        tmp = serverList.getSelectionModel().getSelectedItems();
        System.out.println("Download Sel Click");
    }*/

    public void handleDownloadAllClick()
    {
        tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        userItems.clear();
        System.out.println("Download All Click");
    }
}