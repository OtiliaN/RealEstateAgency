package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.MainViewController;
import org.example.domain.Admin;
import org.example.repository.impl.HibernateAdminRepository;
import org.example.repository.impl.HibernateAgentRepository;
import org.example.repository.impl.HibernateClientRepository;
import org.example.repository.impl.HibernatePropertyRepository;
import org.example.service.*;
import org.mindrot.jbcrypt.BCrypt;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        var adminRepo = new HibernateAdminRepository();
        var agentRepo = new HibernateAgentRepository();
        var clientRepo = new HibernateClientRepository();
        var propertyRepo = new HibernatePropertyRepository();


        var authenticationService = new AuthenticationService(adminRepo, agentRepo, clientRepo);
        var adminService = new AdminService(agentRepo);
        var agentService = new AgentService(propertyRepo);
        var propertyService = new PropertyService(propertyRepo);
        var clientService = new ClientService(clientRepo);


        var serviceManager = new ServiceManager(authenticationService, adminService, agentService, propertyService, clientService);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
        Scene scene = new Scene(loader.load(), 450, 430);

        MainViewController controller = loader.getController();
        controller.setServiceManager(serviceManager);

        primaryStage.setScene(scene);
        primaryStage.setTitle("ImobAgency - Login Client");
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}