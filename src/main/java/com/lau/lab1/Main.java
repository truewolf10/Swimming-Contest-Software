package com.lau.lab1;

import com.lau.lab1.Service.Model;
import com.lau.lab1.Service.Service;
import com.lau.lab1.Service.UserService;
import com.lau.lab1.Ui.LoginWindow;
import com.lau.lab1.domain.Participant;
import com.lau.lab1.domain.Proba;
import com.lau.lab1.domain.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application{

    public static final String springModel = "classpath:spring-model.xml";
    public static final String loginFxmlPath = "/JavaFxUI/loginWindow.fxml";

    public static void main(String[] args) {
        launch(args);
//        DataSource dataSource = new DataSource();
//        UserRepository users = new UserRepository(dataSource);
//        ProbaRepository probe = new ProbaRepository(dataSource);
//        ParticipantRepository participants = new ParticipantRepository(dataSource);
//        ParticipantProbaRepositoryFactory factory = new ParticipantProbaRepositoryFactory(dataSource);
//        Model model = new Model(users, participants, factory, probe);




//        ApplicationContext context=new AnnotationConfigApplicationContext(MainBean.class);
//
//        Model model= context.getBean(Model.class);

//        for (User user : model.getUsers())
//            System.out.println(user);
//
//        System.out.println();
//
//        for (Proba proba : model.getProbe())
//            System.out.println(proba);
//
//        System.out.println();
//
//        for (Participant participant : model.getParticipants())
//            System.out.println(participant);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext context = new
                ClassPathXmlApplicationContext(springModel);
        Service service = context.getBean(Service.class);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(loginFxmlPath));
        //Parent root = fxmlLoader.load();
        Pane pane = fxmlLoader.load();
        LoginWindow loginWindow = fxmlLoader.getController();

        Stage stage = new Stage();
        stage.setTitle("Login User");
        stage.setScene(new Scene(pane, pane.getPrefWidth(), pane.getPrefHeight()));
        stage.setOnCloseRequest(windowEvent -> Platform.exit());
        stage.show();
        loginWindow.initialize(service);
        loginWindow.setOnClose(x->stage.hide());
        loginWindow.setOnLogout(x->stage.show());
    }
}
