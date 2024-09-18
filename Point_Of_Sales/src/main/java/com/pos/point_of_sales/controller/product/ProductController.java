package com.pos.point_of_sales.controller.product;

import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Sale;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SalesModel;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.SQLException;
import java.text.DateFormatSymbols;

/**
 * ProductController class for managing products.  other database related operations of products.
 * deleteAction,editAction, importAction, addAction and loadProductSalesChart in the UI.
 *  * @author Mahesh Yadav
 */
public class ProductController implements Initializable, ProductInterface {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Long> idColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn,nameColumn, supplierColumn, descriptionColumn, barcodeColumn,entrydateColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn, quantityColumn;
    @FXML
    private TextField searchField,excelTextField;
    private ProductModel productModel;
    @FXML
    private Button editButton, deleteButton,saveButton,cancelButton;
    @FXML
    private Label statusLabel;

    @FXML
    private LineChart<String, Number> productChart;
    @FXML
    CategoryAxis pxAxis;

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button menu;
    @FXML
    private VBox drawer;

    private SalesModel salesModel;
/*    @FXML
    private TableColumn<Product, Date> entrydateColumn;*/
    private Product product;
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productModel = new ProductModel();
        salesModel = new SalesModel();
        product = new Product();

        drawerAction();
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        categoryColumn.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getCategory().getType()));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        supplierColumn.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getSupplier().getName()));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        entrydateColumn.setCellValueFactory(new PropertyValueFactory<>("entrydate"));
     /*   entrydateColumn.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getEntrydate().toString()));*/
        productTable.setItems(PRODUCTLIST);

        filterData();

        productTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    try {
                        loadProductSalesChart(newValue);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

        editButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));
        deleteButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTable.getSelectionModel().getSelectedItems()));
    }
    /**
     * Filters data in the table based on the Search field input.
     */
    private void filterData() {

        FilteredList<Product> searchedData = new FilteredList<>(PRODUCTLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (String.valueOf(product.getBarcode()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (String.valueOf(product.getId()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    if (product.getProductName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    // Check if the description contains the search value
                    if (product.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Product> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(productTable.comparatorProperty());
            productTable.setItems(sortedData);
        });
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
     * Loads data into the table.
     *
     * @throws SQLException if an SQL exception occurs.
     */
    private void loadData() throws SQLException {

        if (!PRODUCTLIST.isEmpty()) {
            PRODUCTLIST.clear();
        }
        PRODUCTLIST.addAll(productModel.getProducts());
    }
    /**
     * Loads the sales chart for the selected product.
     *
     * @param p The selected product
     * @throws SQLException If an SQL exception occurs
     */
    private void loadProductSalesChart(Product p) throws SQLException {
        if (p != null) {
            String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
            ObservableList lists = FXCollections.observableArrayList(months);
            pxAxis.setCategories(lists);
            productChart.getData().clear();
            List<Sale> sales = salesModel.getSaleByProductId(p.getId());
            XYChart.Series series = new XYChart.Series();
            series.setName(p.getProductName());

            for (Sale s : sales) {
                Date month = (s.getDatetime());
                series.getData().add(new XYChart.Data(month, s.getTotal()));
            }
            productChart.getData().addAll(series);
        }
    }

    /**
     * Converts a date string to the month name.
     *
     * @param date The date string
     * @return The month name
     */
    private String convertDate(String date) {
        int d = Integer.parseInt(date.substring(5, 7));
        return new DateFormatSymbols().getMonths()[d - 1];
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

    /**
     * Handles the action event for navigating to the category page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void categoryAction(ActionEvent event) throws Exception {
        windows("/fxml/Category.fxml", "Category", event);
    }

    /**
     * Handles the action event for navigating to the purchase page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void purchaseAction(ActionEvent event) throws Exception {
        windows("/fxml/Purchase.fxml", "Purchase", event);
    }

    /**
     * Handles the action event for navigating to the sales page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void salesAction(ActionEvent event) throws Exception {
        windows("/fxml/Sales.fxml", "Sales", event);
    }

    /**
     * Handles the action event for navigating to the report page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void reportAction(ActionEvent event) throws Exception {
        windows("/fxml/Report.fxml", "Report", event);
    }

    /**
     * Handles the action event for navigating to the supplier page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void supplierAction(ActionEvent event) throws Exception {
        windows("/fxml/Supplier.fxml", "Supplier", event);
    }

    /**
     * Handles the action event for navigating to the staff page.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
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
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for importing products from an Excel file.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void importAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/product/Import.fxml"));
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
        stage.setTitle("Import Products");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for adding a new product.
     *
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void addAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/product/Add.fxml"));
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
        stage.setTitle("New Product");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for editing a product.
     * @param event The action event
     * @throws Exception If an error occurs
     */
    @FXML
    public void editAction(ActionEvent event) throws Exception {
        Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
        int selectedProductId = productTable.getSelectionModel().getSelectedIndex();
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/product/Edit.fxml")));
        EditController controller = new EditController();
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Product");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setProduct(selectedProduct, selectedProductId);
        productTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action event for deleting a product.
     * @param event The action event
     * @throws SQLException If an SQL exception occurs
     */
    @FXML
    public void deleteAction(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete Product");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            productModel.deleteProduct(selectedProduct);
            PRODUCTLIST.remove(selectedProduct);
        }

        productTable.getSelectionModel().clearSelection();
    }

    /**
     * Handles the action event for canceling an operation.
     * @param event The action event
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        statusLabel.setText("");
        excelTextField.clear();
        cancelButton.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    /**
     * Opens a new window with the specified path and title.
     *
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
        stage.setScene(scene);
        stage.show();
    }

}
