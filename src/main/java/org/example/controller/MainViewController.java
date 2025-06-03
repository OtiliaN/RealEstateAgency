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
import java.util.Optional;

public class MainViewController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    private ServiceManager serviceManager;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Optional<Client> client = serviceManager.authenticationService.loginClient(username, password);
        if (client.isPresent()) {
            showAlert(Alert.AlertType.INFORMATION, "Login reușit", "Bine ai venit, " + client.get().getName() + "!");
            openClientWindow(event, client.get());
        } else {
            errorLabel.setText("Username sau parolă greșite!");
        }
    }

    @FXML
    private void handleSignUp(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/signup_view.fxml"));
            Scene scene = new Scene(loader.load(), 600, 600);

            SignUpController signUpController = loader.getController();
            signUpController.setServiceManager(serviceManager);

            Stage signUpStage = new Stage();
            signUpStage.setScene(scene);
            signUpStage.setTitle("Înregistrare Client");
            signUpStage.setResizable(false);
            signUpStage.show();


            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdmin(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_login_view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 400);

            AdminLoginController adminLoginController = loader.getController();
            adminLoginController.setServiceManager(serviceManager);

            Stage loginAdminStage = new Stage();
            loginAdminStage.setScene(scene);
            loginAdminStage.setTitle("Login Admin");
            loginAdminStage.setResizable(false);
            loginAdminStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAgent(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/agent_login_view.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);

            AgentLoginController agentLoginController = loader.getController();
            agentLoginController.setServiceManager(serviceManager);

            Stage loginAgentStage = new Stage();
            loginAgentStage.setScene(scene);
            loginAgentStage.setTitle("Login Agent");
            loginAgentStage.setResizable(false);
            loginAgentStage.show();



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openClientWindow(ActionEvent event, Client client) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client_main_view.fxml"));
            Scene scene = new Scene(loader.load(), 900, 600);

            ClientMainController controller = loader.getController();
            controller.setServiceManager(serviceManager);



            Stage mainStage = new Stage();
            mainStage.setScene(scene);
            mainStage.setTitle("Client - ImobAgency");
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}