package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;
import java.util.Optional;


public class ServerController extends Client_process {

    public Button downloadSelected, downloadAll, refreshList;
    public ListView<String> serverList;
    ObservableList<String> serverItems = FXCollections.observableArrayList();
    int clientItemIndex = 0;


    public void handleDownloadSelected()
    {
        ObservableList<String> tmpItem;
        int tmpIndex;
        tmpItem = serverList.getSelectionModel().getSelectedItems();
        tmpIndex = serverList.getSelectionModel().getSelectedIndex();
        System.out.println("item " + tmpItem);
        System.out.println("index " + tmpIndex);

        getMusic(tmpIndex);

    }

    public void handleDownloadAll()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Download All");
        alert.setHeaderText("Are you sure to download all?");
        alert.setContentText("Press OK to download all.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)  {
            getMusic(-2);
        }

    }

    public void handleRefreshList()
    {
        serverItems.clear();
        LinkedList<MediaFile> tmpMusicList = (LinkedList<MediaFile>) recievedMusicList.clone();

        while (!tmpMusicList.isEmpty()) {
            serverItems.add(tmpMusicList.getLast().getIndex(), tmpMusicList.getLast().getName());
            tmpMusicList.removeLast();
            clientItemIndex++;
            serverList.setItems(serverItems);
        }

    }

}