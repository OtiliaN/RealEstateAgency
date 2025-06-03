package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import org.example.domain.Admin;
import org.example.service.ServiceManager;

import java.util.Optional;

public class AdminLoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private Button loginButton;

    private ServiceManager serviceManager;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Optional<Admin> admin = serviceManager.authenticationService.loginAdmin(username, password);
        if (admin.isPresent()) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/admin_main_view.fxml"));
                Scene scene = new Scene(loader.load(), 800, 520);
                AdminMainController controller = loader.getController();
                controller.setServiceManager(serviceManager);

                Stage stage = (Stage) loginButton.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Admin - Management Agenți");
                stage.setResizable(false);
                stage.show();
            } catch (Exception e) {
                errorLabel.setText("Eroare la deschiderea ferestrei principale.");
                e.printStackTrace();
            }
        } else {
            errorLabel.setText("Username sau parolă greșite!");
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene scene = new Scene(loader.load(), 430, 410);


            MainViewController controller = loader.getController();
            controller.setServiceManager(serviceManager);

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("ImobAgency - Login");
            stage.setResizable(true);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}