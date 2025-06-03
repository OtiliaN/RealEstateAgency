package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Client;
import org.example.service.ServiceManager;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField fullNameField;
    @FXML
    private Label errorLabel;

    private ServiceManager serviceManager;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = fullNameField.getText();

        if (username.isBlank() || password.isBlank() || name.isBlank()) {
            errorLabel.setText("Completează toate câmpurile!");
            return;
        }
        if (serviceManager.clientService.findByUsername(username).isPresent()) {
            errorLabel.setText("Acest username există deja!");
            return;
        }

        Client client = new Client();
        client.setUsername(username);
        client.setPassword(password);
        client.setName(name);
        serviceManager.clientService.signUp(client);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            MainViewController controller = loader.getController();
            controller.setServiceManager(serviceManager);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("ImobAgency - Login Client");
            stage.show();

            // Închide fereastra curentă de signup
            Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            signUpStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToMain(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene scene = new Scene(loader.load(), 430, 410);

            MainViewController controller = loader.getController();
            controller.setServiceManager(serviceManager);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("ImobAgency - Login");
            stage.show();


            Stage signUpStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            signUpStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}