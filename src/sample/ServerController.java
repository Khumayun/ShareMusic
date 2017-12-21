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


public class ServerController extends Server {

    public Button downloadSelected, downloadAll, refreshList;
    public ListView<String> serverList;
    ObservableList<String> serverItems = FXCollections.observableArrayList();
    LinkedList<MediaFile> tmpMusicList;
    int serverItemIndex = 0;


    public void handleDownloadSelected()
    {
        ObservableList<String> tmpItem;
        int tmpIndex;
        tmpItem = serverList.getSelectionModel().getSelectedItems();
        tmpIndex = serverList.getSelectionModel().getSelectedIndex();
        System.out.println("item " + tmpItem);
        System.out.println("index " + tmpIndex);
        System.out.println("index " + tmpIndex);
    }

    public void handleDownloadAll()
    {
        System.out.println("In Download All!!!");
    }

    public void handleRefreshList()
    {
        System.out.println("In Refresh List!!!");
    }

}