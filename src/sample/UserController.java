package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.*;


public class UserController extends Server {

    public Button share, refresh, delete;
    public MenuItem searchServer;
    public ListView<String> userList;

    ObservableList<String> userItems = FXCollections.observableArrayList();

    LinkedList<MediaFile> tmpMusicList;
    int userItemIndex = 0;


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


    public void handleShareClick(ActionEvent event) {

        fileEdit(event);

        if(!myMusicList.isEmpty())
            tmpMusicList = (LinkedList<MediaFile>) myMusicList.clone();
        userItems.clear();

        //Display the list on User's window

        while (!tmpMusicList.isEmpty()) {
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

    public void handleDeleteClick() throws IOException
    {
        if(!myMusicList.isEmpty())
            userItems.remove(myMusicList.getFirst().getIndex(), myMusicList.getLast().getIndex());
        userList.setItems(userItems);

        while (!myMusicList.isEmpty()) {
            myMusicList.removeFirst();
            userItemIndex--;
        }
        PrintWriter writer = new PrintWriter("list.txt");
        writer.write("");
        writer.close();
        handleRefreshMusic();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Deletion Alert");
        alert.setHeaderText(null);
        alert.setContentText("Succesfully Deleted!");
        alert.showAndWait();
    }
}