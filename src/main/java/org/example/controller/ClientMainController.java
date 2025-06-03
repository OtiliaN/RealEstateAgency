package org.example.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.domain.Property;
import org.example.domain.PropertyType;
import org.example.domain.TransactionType;
import org.example.service.ServiceManager;
import org.example.utils.PropertyObserver;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ClientMainController implements PropertyObserver {
    @FXML
    private FlowPane propertyFlowPane;
    @FXML
    private ComboBox<String> cityCombo;
    @FXML
    private ComboBox<String> zoneCombo;
    @FXML
    private ComboBox<TransactionType> transactionTypeCombo;
    @FXML
    private ComboBox<PropertyType> propertyTypeCombo;
    @FXML
    private TextField minPriceField;
    @FXML
    private TextField maxPriceField;
    @FXML
    private Button logoutButton;

    private ServiceManager serviceManager;

    public void setServiceManager(ServiceManager serviceManager) {
        this.serviceManager = serviceManager;
        serviceManager.agentService.addObserver(this);
        loadAllFilters();
        displayProperties(serviceManager.propertyService.findAllProperties());
    }

    private void loadAllFilters() {
        List<Property> allProps = serviceManager.propertyService.findAllProperties();

        cityCombo.setItems(FXCollections.observableArrayList(
                allProps.stream().map(Property::getCity).distinct().toList()));

        zoneCombo.setItems(FXCollections.observableArrayList(
                allProps.stream().map(Property::getNeighborhood).distinct().toList()));

        transactionTypeCombo.setItems(FXCollections.observableArrayList(TransactionType.values()));
        propertyTypeCombo.setItems(FXCollections.observableArrayList(PropertyType.values()));
    }

    @FXML
    private void handleFilter() {
        String city = cityCombo.getValue();
        String neighborhood = zoneCombo.getValue();
        TransactionType transactionType = transactionTypeCombo.getValue();
        PropertyType propertyType = propertyTypeCombo.getValue();
        String minPriceStr = minPriceField.getText();
        String maxPriceStr = maxPriceField.getText();

        Integer minPrice = null, maxPrice = null;
        try {
            if (minPriceStr != null && !minPriceStr.isBlank()) minPrice = Integer.parseInt(minPriceStr);
        } catch (NumberFormatException ignored) {}
        try {
            if (maxPriceStr != null && !maxPriceStr.isBlank()) maxPrice = Integer.parseInt(maxPriceStr);
        } catch (NumberFormatException ignored) {}

        List<Property> filtered = serviceManager.propertyService.searchProperties(
                city, neighborhood, propertyType, transactionType, minPrice, maxPrice);

        displayProperties(filtered);
    }

    @FXML
    private void handleResetFilters() {
        cityCombo.getSelectionModel().clearSelection();
        zoneCombo.getSelectionModel().clearSelection();
        transactionTypeCombo.getSelectionModel().clearSelection();
        propertyTypeCombo.getSelectionModel().clearSelection();
        minPriceField.clear();
        maxPriceField.clear();

        displayProperties(serviceManager.propertyService.findAllProperties());
    }

    private void displayProperties(List<Property> properties) {
        propertyFlowPane.getChildren().clear();
        for (Property prop : properties) {
            VBox card = new VBox(5);
            card.setStyle("-fx-border-color: gray; -fx-border-radius: 10; -fx-padding: 10; -fx-background-color: #fdf0f5; -fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 5, 0.0, 0, 2);");
            card.getChildren().addAll(
                    new Text("Oraș: " + prop.getCity()),
                    new Text("Cartier: " + prop.getNeighborhood()),
                    new Text("Preț: " + prop.getPrice() + " EUR"),
                    new Text("Tip tranzacție: " + prop.getTransactionType()),
                    new Text("Tip proprietate: " + prop.getPropertyType()),
                    new Text("Descriere: " + prop.getDescription()),
                    new Text("Agent: " + prop.getAgent().getName() + ", Tel: " + prop.getAgent().getPhone())
            );
            card.setPrefWidth(250);
            card.setMaxWidth(250);
            propertyFlowPane.getChildren().add(card);
        }
    }

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
            Scene loginScene = new Scene(loader.load(), 430, 410);

            MainViewController controller = loader.getController();
            controller.setServiceManager(serviceManager);

            Stage stage = new Stage();
            stage.setScene(loginScene);
            stage.setTitle("Login");
            stage.setResizable(false);
            stage.show();

            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPropertyListChanged() {
        javafx.application.Platform.runLater(() -> {
            displayProperties(serviceManager.propertyService.findAllProperties());
            loadAllFilters();
        });
    }


    public void cleanup() {
        serviceManager.agentService.removeObserver(this);
    }
}