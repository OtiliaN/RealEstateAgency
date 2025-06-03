package org.example.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.domain.Agent;
import org.example.domain.Property;
import org.example.domain.PropertyType;
import org.example.domain.TransactionType;
import org.example.service.ServiceManager;
import org.example.utils.PropertyObserver;

public class AgentMainController implements PropertyObserver {
    @FXML private Label agentNameLabel;
    @FXML private FlowPane propertyFlowPane;
    @FXML private TextField cityField;
    @FXML private TextField neighborhoodField;
    @FXML private ComboBox<TransactionType> transactionTypeCombo;
    @FXML private ComboBox<PropertyType> propertyTypeCombo;
    @FXML private TextField priceField;
    @FXML private TextArea descriptionField;
    @FXML private Label errorLabel;
    @FXML private Button logoutButton;

    private ServiceManager serviceManager;
    private Agent agent;
    private ObservableList<Property> properties;
    private Property selectedProperty;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        serviceManager.agentService.addObserver(this);
        transactionTypeCombo.setItems(FXCollections.observableArrayList(TransactionType.values()));
        propertyTypeCombo.setItems(FXCollections.observableArrayList(PropertyType.values()));
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
        agentNameLabel.setText("Agent imobiliar: " + agent.getName());
        loadProperties();
    }

    private void loadProperties() {
        properties = FXCollections.observableArrayList(
                serviceManager.agentService.findAllProperties());
        displayProperties();
    }

    private void displayProperties() {
        propertyFlowPane.getChildren().clear();
        for (Property prop : properties) {
            VBox card = new VBox(6);
            card.setStyle("-fx-background-color: #f4faff; -fx-border-color: #a3c6e7; -fx-border-radius: 10; -fx-background-radius: 12; -fx-padding: 11;");
            Text t1 = new Text(prop.getCity() + ", " + prop.getNeighborhood());
            t1.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-fill: #4682b4;");
            Text t2 = new Text("Preț: " + prop.getPrice() + " EUR");
            t2.setStyle("-fx-font-size: 15px; -fx-fill: #5da4d6;");
            Text t3 = new Text("Tranzacție: " + prop.getTransactionType());
            Text t4 = new Text("Tip: " + prop.getPropertyType());
            Text t5 = new Text("Descriere: " + prop.getDescription());
            card.getChildren().addAll(t1, t2, t3, t4, t5);
            card.setPrefWidth(220);
            card.setOnMouseClicked(e -> selectProperty(prop));
            propertyFlowPane.getChildren().add(card);
        }
    }

    private void selectProperty(Property prop) {
        selectedProperty = prop;
        cityField.setText(prop.getCity());
        neighborhoodField.setText(prop.getNeighborhood());
        transactionTypeCombo.setValue(prop.getTransactionType());
        propertyTypeCombo.setValue(prop.getPropertyType());
        priceField.setText(String.valueOf(prop.getPrice()));
        descriptionField.setText(prop.getDescription());
        errorLabel.setText("");
    }

    @FXML
    private void handleAddProperty() {
        String city = cityField.getText();
        String neighborhood = neighborhoodField.getText();
        TransactionType transactionType = transactionTypeCombo.getValue();
        PropertyType propertyType = propertyTypeCombo.getValue();
        String priceText = priceField.getText();
        String description = descriptionField.getText();

        if (city.isBlank() || neighborhood.isBlank() || transactionType == null || propertyType == null || priceText.isBlank()) {
            errorLabel.setText("Completează toate câmpurile obligatorii!");
            return;
        }
        int price;
        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Prețul trebuie să fie un număr!");
            return;
        }

        Property property = new Property(city, neighborhood, transactionType, propertyType, price, description, agent);
        serviceManager.agentService.addProperty(property);

        clearFields();
        errorLabel.setText("Proprietate adăugată!");
    }

    @FXML
    private void handleUpdateProperty() {
        if (selectedProperty == null) {
            errorLabel.setText("Selectează o proprietate pentru update!");
            return;
        }
        String city = cityField.getText();
        String neighborhood = neighborhoodField.getText();
        TransactionType transactionType = transactionTypeCombo.getValue();
        PropertyType propertyType = propertyTypeCombo.getValue();
        String priceText = priceField.getText();
        String description = descriptionField.getText();

        if (city.isBlank() || neighborhood.isBlank() || transactionType == null || propertyType == null || priceText.isBlank()) {
            errorLabel.setText("Completează toate câmpurile obligatorii!");
            return;
        }
        int price;
        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            errorLabel.setText("Prețul trebuie să fie un număr!");
            return;
        }

        selectedProperty.setCity(city);
        selectedProperty.setNeighborhood(neighborhood);
        selectedProperty.setTransactionType(transactionType);
        selectedProperty.setPropertyType(propertyType);
        selectedProperty.setPrice(price);
        selectedProperty.setDescription(description);

        serviceManager.agentService.updateProperty(selectedProperty);
        clearFields();
        errorLabel.setText("Proprietate actualizată!");
    }

    @FXML
    private void handleDeleteProperty() {
        if (selectedProperty == null) {
            errorLabel.setText("Selectează o proprietate pentru ștergere!");
            return;
        }
        serviceManager.agentService.deleteProperty(selectedProperty.getId());

        clearFields();
        errorLabel.setText("Proprietate ștearsă!");
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene scene = new Scene(loader.load(), 430, 410);

            MainViewController mainController = loader.getController();
            mainController.setServiceManager(serviceManager);

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        cityField.clear();
        neighborhoodField.clear();
        transactionTypeCombo.setValue(null);
        propertyTypeCombo.setValue(null);
        priceField.clear();
        descriptionField.clear();
        selectedProperty = null;
    }

    @Override
    public void onPropertyListChanged() {

        Platform.runLater(this::loadProperties);
    }
}