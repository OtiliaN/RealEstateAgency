package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.domain.Agent;
import org.example.service.ServiceManager;

public class AdminMainController {
    @FXML private TableView<Agent> agentTable;
    @FXML private TableColumn<Agent, String> usernameColumn;
    @FXML private TableColumn<Agent, String> nameColumn;
    @FXML private TableColumn<Agent, String> phoneColumn;

    @FXML private TextField nameField;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField phoneField;
    @FXML private Label errorLabel;
    @FXML private Button logoutButton;

    private ServiceManager serviceManager;
    private ObservableList<Agent> agents;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        loadAgents();
        setupTableSelection();
    }

    private void loadAgents() {
        agents = FXCollections.observableArrayList(serviceManager.adminService.findAllAgents());
        agentTable.setItems(agents);
        usernameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getUsername()));
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        phoneColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPhone()));
    }

    private void setupTableSelection() {
        agentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                nameField.setText(newSel.getName());
                usernameField.setText(newSel.getUsername());
                phoneField.setText(newSel.getPhone());
                passwordField.clear();
            }
        });
    }

    @FXML
    private void handleAddAgent() {
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();

        if (name.isBlank() || username.isBlank() || password.isBlank() || phone.isBlank()) {
            errorLabel.setText("Completează toate câmpurile!");
            return;
        }

        if (serviceManager.adminService.findAgentByUsername(username).isPresent()) {
            errorLabel.setText("Există deja un agent cu acest username!");
            return;
        }

        Agent agent = new Agent(name, username, password, phone);
        serviceManager.adminService.addAgent(agent);
        agents.add(agent);
        clearFields();
        errorLabel.setText("Agent adăugat cu succes!");
    }

    @FXML
    private void handleDeleteAgent() {
        Agent selected = agentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Selectează un agent pentru ștergere!");
            return;
        }
        serviceManager.adminService.deleteAgent(selected.getId());
        agents.remove(selected);
        clearFields();
        errorLabel.setText("Agent șters!");
    }

    @FXML
    private void handleUpdateAgent() {
        Agent selected = agentTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            errorLabel.setText("Selectează un agent pentru update!");
            return;
        }
        String name = nameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String phone = phoneField.getText();

        if (name.isBlank() || username.isBlank() || phone.isBlank()) {
            errorLabel.setText("Completează toate câmpurile!");
            return;
        }

        selected.setName(name);
        selected.setUsername(username);
        selected.setPhone(phone);
        if (!password.isBlank()) {
            selected.setPassword(password);
        }
        serviceManager.adminService.updateAgent(selected);
        agentTable.refresh();
        clearFields();
        errorLabel.setText("Agent actualizat!");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene scene = new Scene(loader.load(), 430, 410);

            MainViewController controller = loader.getController();
            controller.setServiceManager(serviceManager);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.clear();
        usernameField.clear();
        passwordField.clear();
        phoneField.clear();
        agentTable.getSelectionModel().clearSelection();
    }
}