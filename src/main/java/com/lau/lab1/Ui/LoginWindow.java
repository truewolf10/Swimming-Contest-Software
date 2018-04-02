package com.lau.lab1.Ui;

import com.lau.lab1.DatabaseConnection.DatabaseException;
import com.lau.lab1.Service.Service;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;

import javax.security.auth.login.LoginException;
import javax.swing.*;
import java.io.IOException;

public class LoginWindow {
    public TextField usernameField;
    public PasswordField passwordField;
    public CheckBox showPasswordCheckBox;
    public TextField passwordSecondField;
    public Button logInButton;
    private Service service;
    private EventHandler<ActionEvent> onClose;
    private EventHandler<ActionEvent> onLogout;

    public void setOnLogout(EventHandler<ActionEvent> onLogout) {
        this.onLogout = onLogout;
    }

    public void initialize(Service service) {
        this.service = service;
        passwordField.textProperty().bindBidirectional(passwordSecondField.textProperty());
        passwordSecondField.visibleProperty().bindBidirectional(showPasswordCheckBox.selectedProperty());
        logInButton.setOnAction(this::submitPressed);
        passwordField.textProperty().addListener(this::changed);
    }


    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        logInButton.setDisable(false);
        if (usernameField.getText().trim().isEmpty() || passwordField.getText().isEmpty())
            logInButton.setDisable(true);
    }

    private void submitPressed(ActionEvent event) {
        try{
            service.login(usernameField.getText(), passwordField.getText());
        }
        catch (LoginException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Wrong username or password", ButtonType.CANCEL);
            alert.showAndWait();
            return;
        }
        catch (DatabaseException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Connection failed!", ButtonType.CANCEL);
            alert.showAndWait();
            return;
        }
        try {
            MainWindow.createWindow(service, onLogout);
            onClose.handle(new ActionEvent());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't open a new window!", ButtonType.CANCEL);
            alert.showAndWait();
        }

}

    public void setOnClose(EventHandler<ActionEvent> actionEventEventHandler) {
        onClose = actionEventEventHandler;
    }
}
