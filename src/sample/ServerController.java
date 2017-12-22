package sample;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.LinkedList;


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
        System.out.println("In Download All!!!");
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

        System.out.println("In Refresh List!!!");
    }

}