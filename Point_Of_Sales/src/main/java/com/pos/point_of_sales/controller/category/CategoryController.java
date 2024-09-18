package com.pos.point_of_sales.controller.category;

import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.interfaces.CategoryInterface;
import com.pos.point_of_sales.model.CategoryModel;
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
 * CategoryController class is for managing categories.
 * Performs actions like delete, edit, add, import categories corresponding to the database operations.
 * Author: Mahesh Yadav
 */

public class CategoryController implements Initializable, CategoryInterface {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);


    @FXML
    private TableView<Category> categoryTable;
    @FXML
    private TableColumn<Category, Long> idColumn;
    @FXML
    private TableColumn<Category, String> typeColumn, descriptionColumn;
    @FXML
    private TableColumn<Category, Date> createddate;
    @FXML
    private TextField searchField,excelTextField;
    @FXML
    private Button editButton, deleteButton,saveButton,cancelButton;
    @FXML
    private Label statusLabel;

    private CategoryModel categoryModel;
    private Category category;

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
        categoryModel = new CategoryModel();

        drawerAction();
        try {
            loadData();
        } catch (SQLException e) {
            LOG.error("Error loading data", e);
            throw new RuntimeException(e);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        createddate.setCellValueFactory(new PropertyValueFactory<>("createddate"));
        categoryTable.setItems(CATEGORYLIST);

        filterData();

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(categoryTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(categoryTable.getSelectionModel().getSelectedItems()));
    }
    /**
     * Filters data in the table based on the Search field input.
     */
    private void filterData() {
        FilteredList<Category> searchedData = new FilteredList<>(CATEGORYLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(category -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (category.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (category.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Category> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(categoryTable.comparatorProperty());
            categoryTable.setItems(sortedData);
        });
    }
    /**
     * Loads data into the table.
     *
     * @throws SQLException if an SQL exception occurs.
     */
    private void loadData() throws SQLException {
        try {
            if (!CATEGORYLIST.isEmpty()) {
                CATEGORYLIST.clear();
            }
            CATEGORYLIST.addAll(categoryModel.getCategories());
        } catch (SQLException e) {
            LOG.error("Error loading data", e);
            throw e;
        }
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
     * Handles the action event for navigating to the admin page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
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
    public void purchaseAction(ActionEvent event) throws Exception {

        windows("/fxml/Purchase.fxml", "Purchase", event);
    }

    @FXML
    public void salesAction(ActionEvent event) throws Exception {

        windows("/fxml/Sales.fxml", "Sales", event);
    }

    @FXML
    public void supplierAction(ActionEvent event) throws Exception {
        windows("/fxml/Supplier.fxml", "Supplier", event);
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
     * Handles the action event for logging out.
     *
     * @param event The action event
     * @throws Exception If an error occurs
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
       // stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Opens a new window with the specified path and title.
     * @param path  The path to the FXML file
     * @param title The title of the window
     * @param event The action event
     * @throws Exception If an error occurs
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
       // stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for importing Categories from an Excel file.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void importAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/category/Import.fxml"));
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
        stage.setTitle("Import Categories");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        //stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        loadData();
    }
    /**
     * Handles the action event for adding a new Category.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void addAction(ActionEvent event) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/category/Add.fxml"));
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
        stage.setTitle("Add Category");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
       // stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * edit action button , Handles the action event for editing categories from the selected category to be edited.
     * @param event The action event.
     * @throws Exception If an exception occurs.
     */
    @FXML
    public void editAction(ActionEvent event) throws Exception {
        Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();
        int selectedCategoryId = categoryTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/category/Edit.fxml")));
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
        stage.setTitle("Edit Category");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
       // stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setCategory(selectedCategory, selectedCategoryId);
        categoryTable.getSelectionModel().clearSelection();
    }
    /**
     * Delete button Handles the action event for deleting the categories from the selected category in the UI.
     * @param event The action event.
     * @throws Exception If an exception occurs.
     */
    @FXML
    public void deleteAction(ActionEvent event) throws SQLException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Category selectedCategory = categoryTable.getSelectionModel().getSelectedItem();

            categoryModel.deleteCategory(selectedCategory);
            CATEGORYLIST.remove(selectedCategory);
        }

        categoryTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action event for canceling an operation.
     * @param event The action event
     */
    @FXML
    public void handleCancel(ActionEvent event)
    {
        statusLabel.setText("");
        excelTextField.clear();
        cancelButton.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
        // nameField.setText("");
        //     importProductsBox.valueProperty().setValue(null);
    }
}
