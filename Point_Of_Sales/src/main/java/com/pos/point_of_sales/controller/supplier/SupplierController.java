package com.pos.point_of_sales.controller.supplier;

import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.SupplierInterface;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * SupplierController class for managing Suppliers. Other database related operations of suppliers.
 * Includes deleteAction, editAction, importAction, addAction, logoutAction, and loadData in the UI.
 * Author: Mahesh Yadav
 */
public class SupplierController implements Initializable, SupplierInterface {

    private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

    @FXML
    private TextField supplierField, phoneField, stateField;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, Long> idColumn;

    @FXML
    private TableColumn<Supplier, String> nameColumn, phoneColumn, addressColumn, stateColumn;

    @FXML
    private TableColumn<Supplier, Date> createddate;

    @FXML
    private TextField searchField;

    @FXML
    private Button editButton, deleteButton;

    private SupplierModel model;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button menu;

    @FXML
    private VBox drawer;

    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model = new SupplierModel();

        drawerAction();
        try {
            loadData();
        } catch (SQLException e) {
            logger.error("Error loading data: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        createddate.setCellValueFactory(new PropertyValueFactory<>("createddate"));
        supplierTable.setItems(SUPPLIERLIST);

        filterData();

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(supplierTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(supplierTable.getSelectionModel().getSelectedItems()));
    }

    /**
     * Filters data in the table based on the Search field input.
     */
    private void filterData() {
        FilteredList<Supplier> searchedData = new FilteredList<>(SUPPLIERLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(supplier -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (supplier.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (supplier.getAddress().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (supplier.getPhone().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Supplier> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(supplierTable.comparatorProperty());
            supplierTable.setItems(sortedData);
        });
    }

    /**
     * Loads data into the table.
     *
     * @throws SQLException if an SQL exception occurs.
     */
    private void loadData() throws SQLException {
        if (!SUPPLIERLIST.isEmpty()) {
            SUPPLIERLIST.clear();
        }
        SUPPLIERLIST.addAll(model.getSuppliers());
    }

    /**
     * Initializes the drawer action.
     */
    private void drawerAction() {
        TranslateTransition openNav = new TranslateTransition(new Duration(350), drawer);
        openNav.setToX(0);
        TranslateTransition closeNav = new TranslateTransition(new Duration(350), drawer);
        menu.setOnAction((ActionEvent evt) -> {
            if (drawer.getTranslateX() != 0) {
                openNav.play();
                menu.getStyleClass().remove("hamburger-button");
                menu.getStyleClass().add("open-menu");
            } else {
                closeNav.setToX(-(drawer.getWidth()));
                closeNav.play();
                menu.getStyleClass().remove("open-menu");
                menu.getStyleClass().add("hamburger-button");
            }
        });
    }

    /**
     * Handles the admin action.
     *
     * @param event The action event.
     * @throws Exception If an exception occurs.
     */
    @FXML
    public void adminAction(ActionEvent event) throws Exception {
        windows("/fxml/Admin.fxml", "Admin", event);
    }

    @FXML
    public void productAction(ActionEvent event) throws Exception {

        windows("/fxml/Product.fxml", "Product", event);
    }

    @FXML
    public void categoryAction(ActionEvent event) throws Exception {

        windows("/fxml/Category.fxml", "Category", event);
    }

    @FXML
    public void purchaseAction(ActionEvent event) throws Exception {

        windows("/fxml/Purchase.fxml", "Purchase", event);
    }

    @FXML
    public void salesAction(ActionEvent event) throws Exception {

        windows("/fxml/Sales.fxml", "Sales", event);
    }

    @FXML
    public void reportAction(ActionEvent event) throws Exception {
        windows("/fxml/Report.fxml", "Report", event);
    }

    @FXML
    public void staffAction(ActionEvent event) throws Exception {
        windows("/fxml/Employee.fxml", "Employee", event);
    }

    /**
     * Handles the action event for importing suppliers from an Excel file.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void importAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier/Import.fxml"));
        Scene scene = new Scene(root);
        final Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Product");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        // stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for adding a new supplier.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void addAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/supplier/Add.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("New Supplier");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        //stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Handles the action event for editing a supplier.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void editAction(ActionEvent event) throws Exception {

        Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();
        int selectedSupplierId = supplierTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/supplier/Edit.fxml")));
        EditController controller = new EditController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Update Details");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        //stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setSupplier(selectedSupplier, selectedSupplierId);
        supplierTable.getSelectionModel().clearSelection();
    }
    /**
     * Handles the action event for deleting a supplier.
     * @param event The action event
     * @throws SQLException If an SQL exception occurs
     */
    @FXML
    public void deleteAction(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Remove");
        alert.setHeaderText("Remove Supplier");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Supplier selectedSupplier = supplierTable.getSelectionModel().getSelectedItem();

            model.deleteSupplier(selectedSupplier);
            SUPPLIERLIST.remove(selectedSupplier);
        }

        supplierTable.getSelectionModel().clearSelection();
    }


    /**
     * Handles the logout action.
     *
     * @param event The action event.
     * @throws Exception If an exception occurs.
     */
    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
        Stage stage = new Stage();
        root.setOnMousePressed((MouseEvent e) -> {
            xOffset = e.getSceneX();
            yOffset = e.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent e) -> {
            stage.setX(e.getScreenX() - xOffset);
            stage.setY(e.getScreenY() - yOffset);
        });
        Scene scene = new Scene(root);
        stage.setTitle("Inventory:: Version 1.0");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the windows action. For displaying the UI page and layouts of it.
     *
     * @param event The action event.
     * @throws Exception If an exception occurs.
     */
    private void windows(String path, String title, ActionEvent event) throws Exception {
        double width = ((Node) event.getSource()).getScene().getWidth();
        double height = ((Node) event.getSource()).getScene().getHeight();
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root, width, height);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates phone number format.
     */
    @FXML
    private void validatePhone() {
        String phone = phoneField.getText();
        if (!phone.matches("\\d{10}")) {
            showAlert("Invalid Phone Number", phone + "Please enter a valid 10-digit phone number.");
            // You can also clear the phoneField if needed: phoneField.clear();
        }
    }

    /**
     * it Shows an alert dialog.
     *
     * @param title   The title of the alert.
     * @param content The content of the alert.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
