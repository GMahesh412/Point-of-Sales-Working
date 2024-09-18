package com.pos.point_of_sales.controller.admin;

import com.pos.point_of_sales.entity.Invoice;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.model.EmployeeModel;
import com.pos.point_of_sales.model.InvoiceModel;
import com.pos.point_of_sales.model.ProductModel;
import java.net.URL;

import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.util.*;

import com.pos.point_of_sales.model.SupplierModel;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The AdminController class controls the functionality of the admin interface.
 * It handles navigation, loading charts, and other admin-related tasks.
 * @author Mahesh_Yadav
 */
public class AdminController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private Button menu;
    @FXML
    private VBox drawer;

    @FXML
    private LineChart<String, Number> invoiceChart;
    @FXML
    CategoryAxis ixAxis;
    @FXML
    private BarChart<String, Double> productsChart;
    @FXML
    CategoryAxis pxAxis;

    @FXML
    private PieChart stockChart;

    private ProductModel productModel;
    private InvoiceModel invoiceModel;

    private EmployeeModel employeeModel;
    private SupplierModel supplierModel;

    private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productModel = new ProductModel();
        invoiceModel = new InvoiceModel();
        supplierModel = new SupplierModel();
        drawerAction();
        loadInvoiceChart();

        try {
            loadProductsChart();
        } catch (SQLException e) {
            LOG.error("Error loading products chart: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            loadStockChart();
        } catch (SQLException e) {
            LOG.error("Error loading stock chart: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

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
     * Loads the invoice chart with data.
     */
    private void loadInvoiceChart() {
        employeeModel = new EmployeeModel();
        String[] months = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
        ObservableList lists = FXCollections.observableArrayList(months);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        try {
            List<Invoice> invoiceDaoList = invoiceModel.getInvoicesbyEmp(employeeModel);
            Map<String, Integer> salesCountMap = new HashMap<>();

            for (Invoice i : invoiceDaoList) {
                String month = convertDate(i.getDatetime().toString());
                salesCountMap.put(month, salesCountMap.getOrDefault(month, 0) + 1);
            }

            for (String month : months) {
                Integer salesCount = salesCountMap.getOrDefault(month, 0);
                series.getData().add(new XYChart.Data<>(month, salesCount));
            }

        } catch (SQLException e) {
            LOG.error("Error loading invoice chart: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        series.setName("Sales Count");
        ixAxis.setCategories(lists);
        invoiceChart.getData().add(series);
    }

    /**
     * Loads the products chart with data.
     *
     * @throws SQLException if a database access error occurs
     */
    private void loadProductsChart() throws SQLException {
        ObservableList lists = FXCollections.observableArrayList();
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (Product p : productModel.getProducts()) {
            series.getData().add(new XYChart.Data<>(p.getProductName(), p.getQuantity()));
            lists.add(p.getProductName());
        }
        series.setName("Products");
        pxAxis.setCategories(lists);
        productsChart.getData().add(series);
    }

    /**
     * Converts a date string to month format.
     *
     * @param date the date string to convert
     * @return the month corresponding to the date
     */
    private String convertDate(String date) {
        int d = Integer.parseInt(date.substring(5, 7));
        return new DateFormatSymbols().getMonths()[d - 1];
    }

    /**
     * Loads the stock chart with data.
     *
     * @throws SQLException if a database access error occurs
     */
    private void loadStockChart() throws SQLException {
        ObservableList<PieChart.Data> lists = FXCollections.observableArrayList();
        for (Product p : productModel.getProducts()) {
            lists.add(new PieChart.Data(p.getProductName(), p.getQuantity()));
        }
        stockChart.getData().addAll(lists);
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

    @FXML
    public void logoutAction(ActionEvent event) throws Exception {
        ((Node) (event.getSource())).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/Login.fxml")));
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
