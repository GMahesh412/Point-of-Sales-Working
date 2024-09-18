package com.pos.point_of_sales.controller.barcode;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.pos.point_of_sales.controller.pos.ConfigReader;
import com.pos.point_of_sales.controller.pos.PosController;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SalesModel;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for barcode Scanning functionality for Products .
 * This is the main class for barcode scanning & reading ,getting that specific product details by scan.
 * @author Mahesh Yadav
 */
public class BarcodeScannerController implements Initializable {

    private static final Logger LOG = LoggerFactory.getLogger(BarcodeScannerController.class);

    @FXML
    private Button selectButton, cancelButton, scanButton, paymentButton;
    @FXML
    private ListView<String> productList;
    @FXML
    private TextField barcodeTextField;
    @FXML
    private Label statusLabel;
    @FXML
    private TableColumn<Item, Double> priceColumn, quantityColumn, cgstColumn, sgstColumn, discountColumn, totalColumn;
    private PosController posController;
    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SalesModel salesModel;
    private SupplierModel supplierModel;
    private Connection connection;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private TextField subTotalField, discountField, sgstField, cgstField, netPayableField;
    @FXML
    private ObservableList<Item> ITEMLIST;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Item> listTableView;
    private Item item;
    private Alert alert;
    @FXML
    private TableColumn<Product, String> productColumn, supplierColumn1, categoryColumn1, barcodeColumn;

    /**
     * Initializes the controller.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        posController = new PosController();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        productModel = new ProductModel();
        salesModel = new SalesModel();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pos.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ITEMLIST = FXCollections.observableArrayList();
    }

    /**
     * Action handler for the select button to choose a Barcode image.
     *
     * @param event The ActionEvent triggered by clicking the select button.
     */
    @FXML
    private void selectAction(ActionEvent event) {
        File selectedFile = browseImage();
        if (selectedFile != null) {
            barcodeTextField.setText(selectedFile.getPath());
        }
    }

    /**
     * Opens a file chooser dialog to select a barcode image file.
     *
     * @return The selected barcode image file .
     */
    private File browseImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Barcode Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * Reads the barcode from the selected image file.
     */
    @FXML
    public void readBarcode() {
        String imagePath = barcodeTextField.getText();
        LOG.debug("Image path: {}", imagePath);
        if (!imagePath.isEmpty()) {
            try {
                String barcodeData = readBarcodes();
                if (barcodeData != null && !barcodeData.isEmpty()) {
                    addProductFromBarcode(barcodeData);
                    ((Stage) scanButton.getScene().getWindow()).close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Product Scan is Success..");
                    alert.setContentText("Product Barcode scanned successfully...");
                    alert.showAndWait();
                } else {
                    statusLabel.setText("No barcode found in the image.");
                }
            } catch (Exception e) {
                statusLabel.setText("Error reading barcode: " + e.getMessage());
                LOG.error("Error reading barcode: {}", e.getMessage());
            }
        } else {
            statusLabel.setText("Please select a barcode image.");
            LOG.warn("Please select a barcode image.");
        }
    }

    /**
     * Adds the product information extracted from the barcode to the product list.
     *
     * @param barcodeData The data extracted from the barcode.
     */
    public void addProductFromBarcode(String barcodeData) {
        // barcode contains name,Price,qty,TotalPrice
        String[] productInfo = barcodeData.split(",");
        if (productInfo.length >= 4) {
            String productName = productInfo[0];
            double price = Double.parseDouble(productInfo[1]);
            double quantity = Double.parseDouble(productInfo[2]);
            double totalPrice = Double.parseDouble(productInfo[3]);

            String productString = String.format("%s - Price: %.2f, Quantity: %.2f, Total Price: %.2f", productName, price, quantity, totalPrice);

            productList.getItems().add(productString);
        } else {
            LOG.warn("Invalid barcode data format");
            LOG.info("Invalid barcode data format");
        }
    }

    /**
     * Reads the barcode data from the selected image file.
     *
     * @return The barcodeData.
     * @throws NotFoundException If the barcode is not found in the image.
     * @throws SQLException      If there is SQL exception.
     */
    @FXML
    private String readBarcodes() throws NotFoundException, SQLException {
        String imagePath = barcodeTextField.getText();
        if (!imagePath.isEmpty()) {
            try {
                BufferedImage bf = ImageIO.read(new FileInputStream(imagePath));
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);
                String barcodeData = result.getText();
                LOG.debug("Barcode reading success: {}", barcodeData);
                LOG.info("Barcode reading success: {}", barcodeData);

                // Retrieve product info using barcodeData
                Product product = productModel.getProductByBarcode(barcodeData);
                if (product != null) {
                    addProductToItemList(product);
                } else {
                    statusLabel.setText("Product not found for barcode: " + barcodeData);
                    LOG.warn("Product not found for barcode: {}", barcodeData);
                    LOG.info("Product not found for barcode: {}", barcodeData);
                }
            } catch (IOException | NotFoundException | SQLException e) {
                statusLabel.setText("Error reading barcode or retrieving product: " + e.getMessage());
                LOG.error("Error reading barcode or retrieving product: {}", e.getMessage());
            }
        } else {
            statusLabel.setText("Please enter the path to a barcode image...");
            LOG.warn("Please enter the path to a barcode image...");
        }
        return imagePath;
    }

    /**
     * Adds the product extracted from the barcode to the item list.
     *
     * @param product The product extracted from the barcode.
     */
    private void addProductToItemList(Product product) {
        if (ITEMLIST != null && ITEMLIST.size() > 0) {
            boolean alreadyAdded = ITEMLIST.stream().anyMatch(item -> item.getItemName().equals(product.getProductName()));
            if (alreadyAdded) {
                return;
            }
        }
        ITEMLIST.add(new Item(product.getProductName(), product.getPrice(), 1, 0, 0, 0, product.getPrice()));
        calculation();
        listTableView.setItems(ITEMLIST);
        listTableView.setEditable(true);
    }

    /**
     * this method Calculates the subtotal, taxes, discount, & net payable amount.
     */
    private void calculation() {
        try {
            Properties config = ConfigReader.readConfig();
            DecimalFormat df = new DecimalFormat("#.##");
            double cgstRate = Double.parseDouble(config.getProperty("cgstRate"));
            double sgstRate = Double.parseDouble(config.getProperty("sgstRate"));
            double discountAmount = Double.parseDouble(config.getProperty("discountAmount"));
            double netPayablePrice;
            double subTotalPrice = 0.0;
            subTotalPrice = listTableView.getItems().stream().map((item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
            if (subTotalPrice > 0) {
                paymentButton.setDisable(false);
                double sgst = (double) (subTotalPrice * sgstRate) / 100;
                double cgst = (double) (subTotalPrice * cgstRate) / 100;
                if (subTotalPrice >= 250) {
                    netPayablePrice = (double) (Math.round((subTotalPrice + sgst + cgst) - discountAmount));
                    discountField.setText(String.valueOf(discountAmount));
                } else {
                    netPayablePrice = (double) (Math.round((subTotalPrice + sgst + cgst)));
                    discountField.setText("0.00");
                }
                subTotalField.setText(String.valueOf((subTotalPrice)));
                sgstField.setText(String.valueOf(sgst));
                cgstField.setText(String.valueOf(cgst));
                discountField.setText(String.valueOf(discountAmount));
                netPayableField.setText(String.valueOf(netPayablePrice));
            }
        } catch (IOException e) {
            LOG.error("Error calculating price: {}", e.getMessage());
        }
    }

    /**
     * it Handles the cancel action.
     *
     * @param event The ActionEvent triggered by clicking the cancel button.
     */
    @FXML
    public void handleCancel(ActionEvent event) {
        statusLabel.setText("");
        alert.setContentText("");
        barcodeTextField.clear();
        cancelButton.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    /**
     *  it Closes the window.
     *
     * @param event The ActionEvent triggered by clicking the close button.
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}








/*
package com.pos.point_of_sales.controller.barcode;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.pos.point_of_sales.controller.pos.ConfigReader;
import com.pos.point_of_sales.controller.pos.PosController;
import com.pos.point_of_sales.entity.Item;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SalesModel;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.ResourceBundle;

import static com.pos.point_of_sales.interfaces.ProductInterface.PRODUCTLIST;

public class BarcodeScannerController implements Initializable {


    @FXML
    private Button selectButton, cancelButton,scanButton,paymentButton;
    @FXML
    private ListView<String> productList;

    @FXML
    private TextField barcodeTextField;

    @FXML
    private Label statusLabel;

    @FXML
    private TableColumn<Item, Double> priceColumn, quantityColumn, cgstColumn, sgstColumn, discountColumn, totalColumn;

    private PosController posController;
    private ProductModel  productModel;
    private CategoryModel categoryModel;
    private SalesModel salesModel;
    private SupplierModel supplierModel;
    private Connection  connection;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private TextField subTotalField, discountField, sgstField, cgstField, netPayableField;
    @FXML
    private ObservableList<Item> ITEMLIST;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    private TableView<Item> listTableView;
    private  Item item;
    private Alert alert;

    @FXML
    private TableColumn<Product, String> productColumn,supplierColumn1,categoryColumn1,barcodeColumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        posController = new PosController();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        productModel = new ProductModel();
        salesModel = new SalesModel();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pos.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ITEMLIST = FXCollections.observableArrayList();
    }
    @FXML
    private void selectAction(ActionEvent event)
    {
        File selectedFile = browseImage();
        if (selectedFile != null)
        {
            barcodeTextField.setText(selectedFile.getPath());
        }
    }
    private File browseImage()
    {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Barcode Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );
        return fileChooser.showOpenDialog(stage);
    }
    @FXML
    public void readBarcode()
    {

        String imagePath = barcodeTextField.getText();
        System.out.println(imagePath);
        if (!imagePath.isEmpty())
        {
            try {
                String barcodeData = readBarcodes();
                if (barcodeData != null && !barcodeData.isEmpty())
                {
                    addProductFromBarcode(barcodeData);
                    ((Stage) scanButton.getScene().getWindow()).close();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Product Scan is Success..");
                    alert.setContentText("Product Barcode scanned successfully...");
                    alert.showAndWait();
                } else {
                   statusLabel.setText("No barcode found in the image.");
                }
            } catch (Exception e) {
               statusLabel.setText("Error reading barcode: " + e.getMessage());
            }
        } else {
            statusLabel.setText("Please select a barcode image.");
            System.out.println("Please select a barcode image.");
        }
    }
    public void addProductFromBarcode(String barcodeData) {
        // barcode contains name,Price,qty,TotalPrice
        String[] productInfo = barcodeData.split(",");
        if (productInfo.length >= 4) {
            String productName = productInfo[0];
            double price = Double.parseDouble(productInfo[1]);
            double quantity = Double.parseDouble(productInfo[2]);
            double totalPrice = Double.parseDouble(productInfo[3]);

            String productString = String.format("%s - Price: %.2f, Quantity: %.2f, Total Price: %.2f",
                    productName, price, quantity, totalPrice);

            productList.getItems().add(productString);
        } else {
            System.out.println("Invalid barcode data format");
        }
    }
    @FXML
    private String readBarcodes() throws NotFoundException, SQLException {
        String imagePath = barcodeTextField.getText();
        Alert alert = new Alert(Alert.AlertType.WARNING);
        if (!imagePath.isEmpty())
        {
            try {
                BufferedImage bf = ImageIO.read(new FileInputStream(imagePath));
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
                MultiFormatReader reader = new MultiFormatReader();
                Result result = reader.decode(bitmap);
                String barcodeData = result.getText();
                System.out.println("Barcode reading success: " + barcodeData);
                // Retrieve product info using barcodeData
               Product product =  productModel.getProductByBarcode(barcodeData);
                if (product != null)
                {
                    addProductToItemList( product);
                  //  posController.addItemToList(product);
                   // listTableView.setItems(ITEMLIST);

                } else {
                    statusLabel.setText("Product not found for barcode: " + barcodeData);
                    System.out.println("Product not found for barcode: " + barcodeData);
                }
            } catch (IOException | NotFoundException | SQLException e) {
                statusLabel.setText("Error reading barcode or retrieving product: " + e.getMessage());
            }
        } else {
            statusLabel.setText("Please enter the path to a barcode image...");
            //alert.setContentText("Please enter the path to a barcode image...");
        }
        return imagePath;
    }

    private void addProductToItemList(Product product) {
        if (ITEMLIST != null && ITEMLIST.size() > 0) {
            boolean alreadyAdded = ITEMLIST.stream().anyMatch(item -> item.getItemName().equals(product.getProductName()));
            if (alreadyAdded)
            {
                return;
            }
        }
        ITEMLIST.add(new Item(product.getProductName(), product.getPrice(), 1, 0, 0, 0, product.getPrice()));
        calculation();
        listTableView.setItems(ITEMLIST);
        listTableView.setEditable(true);
    }

    private void calculation() {
        try {
            Properties config = ConfigReader.readConfig();
            DecimalFormat df = new DecimalFormat("#.##");
            double cgstRate = Double.parseDouble(config.getProperty("cgstRate"));
            double sgstRate = Double.parseDouble(config.getProperty("sgstRate"));
            double discountAmount = Double.parseDouble(config.getProperty("discountAmount"));
            double netPayablePrice;
            double subTotalPrice = 0.0;
         */
/*   if(listTableView.getItems()!=null && listTableView.getItems().size()>0)
            {
                subTotalPrice = listTableView.getItems().stream().map(
                        (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
            }*//*

            subTotalPrice = listTableView.getItems().stream().map(
                    (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
            if (subTotalPrice > 0)
            {
                paymentButton.setDisable(false);
                double sgst = (double) (subTotalPrice * sgstRate) / 100;
                double cgst = (double) (subTotalPrice * cgstRate) / 100;

                if (subTotalPrice >= 250)
                {
                    netPayablePrice = (double) (Math.round((subTotalPrice + sgst + cgst) - discountAmount));
                    discountField.setText(String.valueOf(discountAmount));
                } else
                {
                    netPayablePrice = (double) (Math.round((subTotalPrice + sgst + cgst)));
                    discountField.setText("0.00");
                }
                //netPayablePrice = Math.round(netPayablePrice);

                subTotalField.setText(String.valueOf((subTotalPrice)));
                sgstField.setText(String.valueOf(sgst));
                cgstField.setText(String.valueOf(cgst));
                discountField.setText(String.valueOf(discountAmount));
                netPayableField.setText(String.valueOf(netPayablePrice));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleCancel(ActionEvent event)
    {
        statusLabel.setText("");
        alert.setContentText("");
        barcodeTextField.clear();
        cancelButton.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}

*/
