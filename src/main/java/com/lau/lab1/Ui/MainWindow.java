package com.lau.lab1.Ui;

import com.lau.lab1.Service.ProbeService;
import com.lau.lab1.Service.Service;
import com.lau.lab1.domain.Distance;
import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.Style;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;


public class MainWindow {

    public static final String mainFxmlFile = "/JavaFxUI/MainWindow.fxml";
    public TableView<Proba> tableProbe;
    public TableColumn<Proba, Distance> columnDistanta;
    public TableColumn<Proba, Style> columnStil;
    public TableColumn<Proba, Integer> columnNumarParticipanti;
    public TableView<Participant> tableParticipanti;
    public TableColumn<Participant, String> columnName;
    public TableColumn<Participant, Integer> columnVarsta;
    public TableColumn<Participant, String> columnProbeInscrise;
    public Button addBtn;
    public Button deselectBtn;
    public Button deleteBtn;
    public Button editBtn;
    public Button logoutBtn;
    private Service service;
    private ObservableList<Participant> participants;
    private EventHandler<ActionEvent> onLogout;
    private EventHandler<ActionEvent> onClose;

    public void setOnClose(EventHandler<ActionEvent> onClose) {
        this.onClose = onClose;
    }

    static Stage createWindow(Service service, EventHandler<ActionEvent> onLogout) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(mainFxmlFile));
        //Parent root = fxmlLoader.load();
        Pane pane = fxmlLoader.load();
        MainWindow mainWindow = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("Main Window");
        stage.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
        mainWindow.initialize(service);
        mainWindow.setOnLogout(x->{
            stage.setOnCloseRequest(null);
            stage.close();
            onLogout.handle(x);
        });

        return stage;
    }

    private void initialize(Service service) {
        this.service = service;
        service.getParticipantService().addEventHandler(this::updateParticipant);
        service.getProbaService().addEventHandler(this::updateProba);

        tableParticipanti.setItems(FXCollections.observableArrayList());
        tableProbe.setItems(FXCollections.observableArrayList());

        participants = FXCollections.observableArrayList();
        columnDistanta.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getDistance()));
        columnStil.setCellValueFactory(x-> new ReadOnlyObjectWrapper<>(x.getValue().getStyle()));
        columnNumarParticipanti.setCellValueFactory(x-> {
            int count = 0;
            for (Participant participant : participants){
                if (participant.getProbe().contains(x.getValue()))
                    count++;
            }
            return new ReadOnlyObjectWrapper<>(count);
        });
        columnName.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getNume()));
        columnVarsta.setCellValueFactory(x->new ReadOnlyObjectWrapper<>(x.getValue().getVarsta()));
        columnProbeInscrise.setCellValueFactory(x->{
            String probe = x.getValue().getProbe().toString().substring(1);
            return new ReadOnlyObjectWrapper<>(probe.substring(0,probe.length()-1));
        });

        updateParticipant(null);
        updateProba(null);
        tableProbe.getSelectionModel().getSelectedItems().addListener(this::selectionChanged);

        deselectBtn.setOnAction(this::deselectPressed);
        logoutBtn.setOnAction(this::logoutPressed);

        addBtn.setOnAction(this::addPressed);
        deleteBtn.setOnAction(this::removePressed);

    }

    private void removePressed(ActionEvent event) {
        service.getParticipantService().remove(tableParticipanti.getSelectionModel().getSelectedItem().getId());
    }

    private void addPressed(ActionEvent event) {
        try {
            AddParticipantForm.create(service, null);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't open a new window!", ButtonType.CANCEL);
            alert.showAndWait();
        }
    }

    private void selectionChanged(ListChangeListener.Change<? extends Proba> c) {
            updateParticipant(null);
        }

        private void deselectPressed(ActionEvent event){
            tableProbe.getSelectionModel().clearSelection();
        }

        private void updateProba(ActionEvent event) {
        tableProbe.getItems().clear();
        for(Proba proba : service.getProbaService().getAll())
            tableProbe.getItems().add(proba);
    }

    private void updateParticipant(ActionEvent event) {
        participants.clear();
        for (Participant participant : service.getParticipantService().getAll()) {
            participants.add(participant);
        }
        tableParticipanti.getItems().clear();
        if (tableProbe.getSelectionModel().getSelectedItems().size() == 0) {
            for (Participant participant : participants)
                tableParticipanti.getItems().add(participant);
        } else {
            for (Participant participant : participants) {
                for (Proba proba : tableProbe.getSelectionModel().getSelectedItems())
                    if (participant.getProbe().contains(proba)) {
                        tableParticipanti.getItems().add(participant);
                        break;
                    }
            }
        }
    }


    private void setOnLogout(EventHandler<ActionEvent> onLogout) {
        this.onLogout = onLogout;
    }
    private void logoutPressed(ActionEvent event){
        onLogout.handle(event);
    }
}
