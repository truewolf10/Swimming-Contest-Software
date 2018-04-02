package com.lau.lab1.Ui;

import com.lau.lab1.Service.Service;
import com.lau.lab1.domain.Distance;
import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.Style;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class AddParticipantForm {

    public static final String addParticipantFormPath = "/JavaFxUI/addParticipantForm.fxml";
    public TextField nameField;
    public TextField varstaField;
    public ListView<Proba> listViewProbe;
    public ComboBox<Style> stilCombo;
    public ComboBox<Distance> distantaCombo;
    public Button adaugaProbaBtn;
    public Button stergeProbaBtn;
    public Button submitBtn;
    private Participant participant;
    private EventHandler<ActionEvent> onSubmit;
    private Service service;

    public static Stage create(Service service, Participant participant) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(addParticipantFormPath));
        //Parent root = fxmlLoader.load();
        Pane pane = fxmlLoader.load();
        AddParticipantForm addParticipantForm = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle((participant == null ? "Add" : "Edit") + "Participant");
        stage.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        addParticipantForm.setOnSubmit(x -> stage.close());
        addParticipantForm.initialize(service, participant);
        stage.showAndWait();
        return stage;
    }

    private void initialize(Service service, Participant participant) {
        this.service = service;
        this.participant = participant;
        listViewProbe.setItems(FXCollections.observableArrayList());
        if (participant != null)
            for (Proba proba : participant.getProbe())
                listViewProbe.getItems().add(proba);

        updateStyle();
        stilCombo.getSelectionModel().selectedIndexProperty().addListener(this::changedStil);
        distantaCombo.getSelectionModel().selectedIndexProperty().addListener(this::changedDistanta);
        adaugaProbaBtn.setDisable(true);
        adaugaProbaBtn.setOnAction(this::handleAdaugaPressed);
        stergeProbaBtn.setDisable(true);
        stergeProbaBtn.setOnAction(this::handleStergePressed);

        listViewProbe.getSelectionModel().getSelectedItems().addListener(this::probeListChanged);
        varstaField.textProperty().addListener(this::varstaChanged);

        varstaField.textProperty().addListener((x,y,z) -> valuesChanged());
        nameField.textProperty().addListener((x,y,z) -> valuesChanged());
        listViewProbe.getItems().addListener((ListChangeListener<? super Proba>) x->valuesChanged());

        submitBtn.setDisable(true);
        submitBtn.setOnAction(this::submitPressed);

    }

    private void submitPressed(ActionEvent event) {
        ArrayList<Proba> probaArrayList = new ArrayList<>(listViewProbe.getItems());
        if(participant == null)
            service.getParticipantService().add(nameField.getText(),
                Integer.valueOf(varstaField.getText()),
                probaArrayList);
        else{
            participant.setNume(nameField.getText());
            participant.setVarsta(Integer.valueOf(varstaField.getText()));
            participant.setProbe(probaArrayList);
            service.getParticipantService().edit(participant);
        }
        onSubmit.handle(new ActionEvent());
    }

    private void valuesChanged() {
        submitBtn.setDisable(false);
        if (nameField.getText().trim().length() < 3)
            submitBtn.setDisable(true);
        if (!varstaField.getText().matches("\\d{2}"))
            submitBtn.setDisable(true);
        if (listViewProbe.getItems().size() == 0)
            submitBtn.setDisable(true);
    }

    public void varstaChanged(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (varstaField.getText().trim().isEmpty()){
            return;
        }
        if (!varstaField.getText().matches("[1-9]\\d?"))
            varstaField.setText(oldValue);
    }

    public void probeListChanged(ListChangeListener.Change<? extends Proba> c) {
        stergeProbaBtn.setDisable(listViewProbe.getSelectionModel().getSelectedItems().size() == 0);
    }
    private void handleStergePressed(ActionEvent event) {
        listViewProbe.getItems().remove(listViewProbe.getSelectionModel().getSelectedItem());
        stilCombo.setValue(null);
        updateStyle();
    }

    public void handleAdaugaPressed(ActionEvent event) {
        listViewProbe.getItems().add(new Proba(null, distantaCombo.getValue(), stilCombo.getValue()));
        stilCombo.setValue(null);
        updateStyle();
    }

    public void changedStil(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        distantaCombo.setDisable(stilCombo.getValue() == null);
        updateDistance();
    }
    public void changedDistanta(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        adaugaProbaBtn.setDisable(distantaCombo.getValue() == null);
    }

    private void updateStyle(){
        stilCombo.getItems().clear();
        Iterable<Proba> probaIterable = service.getProbaService().getAll();
        for (Style style : Style.values())
            for (Proba proba : probaIterable)
                if (!listViewProbe.getItems().contains(proba) && proba.getStyle().equals(style)) {
                    stilCombo.getItems().add(style);
                    break;
                }
        distantaCombo.setValue(null);
        distantaCombo.setDisable(true);
    }


    private void updateDistance(){
        distantaCombo.getItems().clear();
        Iterable<Proba> probaIterable = service.getProbaService().getAll();
        Style selected = stilCombo.getValue();
        for (Distance distance : Distance.values())
            for (Proba proba : probaIterable)
                if (!listViewProbe.getItems().contains(proba) && proba.getDistance().equals(distance) && proba.getStyle().equals(selected)) {
                    distantaCombo.getItems().add(distance);
                    break;
                }
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public void setOnSubmit(EventHandler<ActionEvent> onSubmit) {
        this.onSubmit = onSubmit;
    }
}
