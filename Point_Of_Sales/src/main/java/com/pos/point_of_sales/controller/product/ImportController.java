package com.pos.point_of_sales.controller.product;

import com.pos.point_of_sales.controller.barcode.BarcodeGenerator;
import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

import java.io.*;
import java.net.URL;
import com.opencsv.exceptions.CsvException;
import java.sql.SQLException;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;

/**
 * ImportController  class is for importing  new products into the DB and
 * for importing products from an Excel file.
 * @author Mahesh Yadav
 */
public class ImportController implements Initializable, ProductInterface {
    private static final Logger LOG = LoggerFactory.getLogger(ImportController.class);

    @FXML
    private Button selectButton, uploadButton, cancelButton;

    @FXML
    private TextField excelTextField;

    @FXML
    private Label statusLabel;

    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SupplierModel supplierModel;
    private BarcodeGenerator barcodeGenerator;
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productModel = new ProductModel();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        barcodeGenerator = new BarcodeGenerator();
    }
    /**
     * it Handles the action event for selecting a file.
     *
     * @param event The action event
     */
    @FXML
    private void selectAction(ActionEvent event) {
        File selectedFile = browseFile();
        if (selectedFile != null) {
            excelTextField.setText(selectedFile.getPath());
        }
    }
    /**
     * Opens a file chooser dialog for browsing files.
     * @return The selected file
     */
    private File browseFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel files", "*.xls", "*.xlsx"),
                new FileChooser.ExtensionFilter("CSV files", "*.csv")
        );

        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void importAction()
    {
        String path = excelTextField.getText();
        if (!path.isEmpty())
        {
            try {
                if (path.endsWith(".csv"))
                {
                    readDataFromCSV(path);
                } else {
                    readDataFromExcel(path);
                }
                ((Stage) uploadButton.getScene().getWindow()).close();
            } catch (FileNotFoundException e)
            {
                statusLabel.setText("File not found!");
                LOG.error("File not found!", e);
            } catch (IOException e)
            {
                statusLabel.setText("Error reading file!");
                LOG.error("IO exception occurred!", e);
            } catch (SQLException e) {
                statusLabel.setText("Database error occurred!");
                LOG.error("SQL exception occurred!", e);
            }
        } else
        {
            statusLabel.setText("Please select a file!");
        }
    }
    /**
     * it Reads data from the CSV file and saves products to the database.
     * and generates barcode as well.
     * @param path The path of the Excel file
     * @throws FileNotFoundException if  file is not found
     * @throws SQLException          if  SQL exception occurs
     */
    private void readDataFromCSV(String path) throws IOException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
        ObservableList<Product> productList = productModel.getProducts();

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null)
            {

               /* String[] parts = line.split("[,;]");
                String[] parts = line.split(";");*/

                String[] parts = line.split(",");
                String productName = parts[0];
                double price = Double.parseDouble(parts[1]);
                double quantity = Double.parseDouble(parts[2]);
                String description = parts[3];
                String categoryType = parts[4];
                String supplierType = parts[5];

                Category category = getCategoryByName(categoryList, categoryType);
                Supplier supplier = getSupplierByName(suppliersList, supplierType);

                if (category != null && supplier != null) {
                    boolean productExists = productList.stream().anyMatch(p -> p.getProductName().equals(productName));
                    if (!productExists) {
                        Product product = new Product(productName, price, quantity, description, category, supplier, new Date(System.currentTimeMillis()));
                        productModel.saveProduct(product);
                        PRODUCTLIST.add(product);
                        PRODUCTLIST.clear();
                        String code = barcodeGenerator.barCode();
                        product.setBarcode(code);
                        PRODUCTLIST.addAll(productModel.getProducts());
                        showAlert("Successful", "Imported Succesfully..", Alert.AlertType.INFORMATION);
                        LOG.info("Product '{}' imported successfully.", productName);
                        LOG.debug("Product '{}' imported successfully.", productName);
                    } else {
                        LOG.error("Error in importing Products..");
                        LOG.debug("Product '{}' already exists.", productName);
                        LOG.info("Product '{}' already exists.", productName);
                        LOG.error("Please import valid Products that are not existed..");
                        showAlert("Invalid Products", "Please import valid Products that are not existed..", Alert.AlertType.ERROR);
                    }
                } else {
                    LOG.error("Category or Supplier not found for product: {}", productName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * it Reads data from the Excel file and saves products to the database.
     * and generates barcode as well.
     * @param path The path of the Excel file
     * @throws FileNotFoundException if  file is not found
     * @throws SQLException          if  SQL exception occurs
     */
    private void readDataFromExcel(String path) throws IOException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
        ObservableList<Product> productList = productModel.getProducts();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            Workbook wb = new XSSFWorkbook(fileInputStream);
            Sheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String productName = row.getCell(0).getStringCellValue();
                double price = row.getCell(1).getNumericCellValue();
                double quantity = row.getCell(2).getNumericCellValue();
                String description = row.getCell(3).getStringCellValue();
                String categoryType = row.getCell(4).getStringCellValue();
                String supplierType = row.getCell(5).getStringCellValue();
                Category category = getCategoryByName(categoryList, categoryType);
                Supplier supplier = getSupplierByName(suppliersList, supplierType);
                boolean productExists = false;
                for (Product existingProduct : productList) {
                    if (existingProduct.getProductName().equals(productName)) {
                        productExists = true;
                        LOG.error("Error in importing Products..");
                        LOG.debug("Product '{}' already exists.", productName);
                        LOG.info("Product '{}' already exists.", productName);
                        LOG.error("Please import valid Products that are not existed..");
                        showAlert("Invalid Products", "Please import valid Products that are not existed..", Alert.AlertType.ERROR);
                        break;
                    }
                }
                if (!productExists) {
                    Product product = new Product(productName, price, quantity, description, category, supplier, new Date(System.currentTimeMillis()));
                    productModel.saveProduct(product);
                    PRODUCTLIST.add(product);
                    PRODUCTLIST.clear();
                    String code = barcodeGenerator.barCode();
                    product.setBarcode(code);
                    PRODUCTLIST.addAll(productModel.getProducts());
                    showAlert("Successful", "Imported Succesfully..", Alert.AlertType.INFORMATION);
                    LOG.info("Product '{}' imported successfully.", productName);
                    LOG.debug("Product '{}' imported successfully.", productName);

                }
            }
        } catch (SQLException ex) {
            handleSQLException(ex);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

    private Category getCategoryByName(List<Category> categories, String name) {
        for (Category category : categories) {
            if (category.getType().equals(name)) {
                return category;
            }
        }
        return null;
    }

    private Supplier getSupplierByName(List<Supplier> suppliers, String name) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(name)) {
                return supplier;
            }
        }
        return null;
    }

    /**
     * Opens a file chooser dialog for browsing files.
     *
     * @return The selected file
     *//*
    private File browseFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xls files", "*.xls"),
                new FileChooser.ExtensionFilter("xlsx files", "*.xlsx"),
                new FileChooser.ExtensionFilter("csv files", "*.csv")
        );

        return fileChooser.showOpenDialog(stage);
    }
    *//**
     * Handles the action event for importing products from the selected file.
     *//*
    @FXML
    private void importAction() {
        String path = excelTextField.getText();
        if (!path.isEmpty()) {
            try {
                readDataFromExcel(path);
                ((Stage) uploadButton.getScene().getWindow()).close();
            } catch (FileNotFoundException e) {
                statusLabel.setText("File not found!");
                LOG.error("File not found!", e);
            } catch (IOException e) {
                statusLabel.setText("Error reading file!");
                LOG.error("IO exception occurred!", e);
            } catch (SQLException e) {
                statusLabel.setText("Database error occurred!");
                LOG.error("SQL exception occurred!", e);
            }
        } else {
            statusLabel.setText("Please select a file!");
        }
    }
    *//**
     * it Reads data from the Excel file and saves products to the database.
     *
     * @param path The path of the Excel file
     * @throws FileNotFoundException if  file is not found
     * @throws SQLException          if  SQL exception occurs
     *//*
    private void readDataFromExcel(String path) throws IOException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
        ObservableList<Product> productList = productModel.getProducts();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String productName = row.getCell(0).getStringCellValue();
                double price = row.getCell(1).getNumericCellValue();
                double quantity = row.getCell(2).getNumericCellValue();
                String description = row.getCell(3).getStringCellValue();
                String categoryType = row.getCell(4).getStringCellValue();
                String supplierType = row.getCell(5).getStringCellValue();
                Category category = null;
                Supplier supplier = null;

                for (Category t : categoryList) {
                    if (t.getType().equals(categoryType)) {
                        category = t;
                        break;
                    }
                }
                for (Supplier s : suppliersList) {
                    if (s.getName().equals(supplierType)) {
                        supplier = s;
                        break;
                    }
                }
                boolean productExists = false;
                for (Product existingProduct : productList) {
                    if (existingProduct.getProductName().equals(productName)) {
                        productExists = true;
                        LOG.error("Error in importing Products..");
                        LOG.debug("Product '{}' already exists.", productName);
                        LOG.info("Product '{}' already exists.", productName);
                        LOG.error("Please import valid Products that are not existed..");
                        showAlert("Invalid Products", "Please import valid Products that are not existed..", Alert.AlertType.ERROR);
                        break;
                    }
                }
                if (!productExists) {
                    Product product = new Product(productName, price, quantity, description, category, supplier, new Date(System.currentTimeMillis()));
                    productModel.saveProduct(product);
                    PRODUCTLIST.add(product);
                    PRODUCTLIST.clear();
                    String code = barcodeGenerator.barCode();
                    product.setBarcode(code);
                    PRODUCTLIST.addAll(productModel.getProducts());
                    showAlert("Successful", "Imported Succesfully..", Alert.AlertType.INFORMATION);
                    LOG.info("Product '{}' imported successfully.", productName);
                    LOG.debug("Product '{}' imported successfully.", productName);

                }
            }
        } catch (SQLException ex) {
            handleSQLException(ex);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
    */
    /**
     * it Handles the cancel action event when it is triggered.
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
     * it Handles the closes the window action event when it is triggered.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event) {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}





/*
public class ImportController implements Initializable, ProductInterface
{
    private static final Logger LOG = LoggerFactory.getLogger(ImportController.class);
    @FXML
    private Button selectButton,uploadButton,cancelButton;

    @FXML
    private TextField excelTextField;

    @FXML
    private Label statusLabel;

    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SupplierModel supplierModel;
    private BarcodeGenerator barcodeGenerator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productModel = new ProductModel();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
        barcodeGenerator = new BarcodeGenerator();
    }

    @FXML
    private void selectAction(ActionEvent event) {
        File selectedFile = browseFile();
        if (selectedFile != null) {
            excelTextField.setText(selectedFile.getPath());
        }
    }

    private File browseFile() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xls files", "*.xls"),
                new FileChooser.ExtensionFilter("xlsx files", "*.xlsx"),
                new FileChooser.ExtensionFilter("csv files", "*.csv")
        );

        return fileChooser.showOpenDialog(stage);
    }

    @FXML
    private void importAction()
    {
        String path = excelTextField.getText();
        if (!path.isEmpty()) {
            try {
                readDataFromExcel(path);
                ((Stage) uploadButton.getScene().getWindow()).close();
            } catch (FileNotFoundException e)
            {
                statusLabel.setText("File not found!");
                handleFileNotFoundException(e);
            } catch (IOException e)
            {
                statusLabel.setText("Error reading file!");
                handleIOException(e);
            } catch (SQLException e)
            {
                statusLabel.setText("Database error occurred!");
                handleSQLException(e);
            }
        }

        else
        {
            statusLabel.setText("Please select a file!");
        }
    }


    private static final Logger logger = LoggerFactory.getLogger(JDBCConnectionFactory.class);

    private void handleFileNotFoundException(FileNotFoundException e) {
        logger.error("File not found!", e);
    }

    private void handleIOException(IOException e) {
        logger.error("IO exception occurred!", e);
    }

    private void handleSQLException(SQLException e) {
        logger.error("SQL exception occurred!", e);
    }

    private void readDataFromExcel(String path) throws IOException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        ObservableList<Supplier> suppliersList = supplierModel.getSuppliers();
        ObservableList<Product> productList = productModel.getProducts();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(fileInputStream);
            XSSFSheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String productName = row.getCell(0).getStringCellValue();
                double price = row.getCell(1).getNumericCellValue();
                double quantity = row.getCell(2).getNumericCellValue();
                String description = row.getCell(3).getStringCellValue();
                String categoryType = row.getCell(4).getStringCellValue();
                String supplierType = row.getCell(5).getStringCellValue();
                Category category = null;
                Supplier supplier = null;

                for (Category t : categoryList) {
                    if (t.getType().equals(categoryType)) {
                        category = t;
                        break;
                    }
                }
                for (Supplier s : suppliersList) {
                    if (s.getName().equals(supplierType)) {
                        supplier = s;
                        break;
                    }
                }
                boolean productExists = false;
                for (Product existingProduct : productList) {
                    if (existingProduct.getProductName().equals(productName))
                    {
                        productExists = true;
                        System.out.println("Product '" + productName + "' already exists.");
                        System.out.println("Products already existed or Error importing Products.");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Invalid Products");
                        alert.setHeaderText("Please import valid Products that are not existed..");
                        alert.setContentText("Error in importing Categories..");
                        alert.showAndWait();
                        break;
                    }
                }
                if (!productExists)
                {
                    Product product = new Product(productName, price, quantity, description, category, supplier, new Date(System.currentTimeMillis()));
                    productModel.saveProduct(product);
                    PRODUCTLIST.add(product);
                    PRODUCTLIST.clear();
                    String code= new BarcodeGenerator().barCode();
                    product.setBarcode(code);
                    // productModel.saveProduct(product);
                    PRODUCTLIST.addAll(productModel.getProducts());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setHeaderText("Imported Succesfully..");
                    alert.setContentText("Products are imported...");
                    alert.showAndWait();
                    System.out.println("Products are imported Successfully ...");
                    System.out.println("Product '" + productName + "' imported successfully.");
                }
            }
        } catch (SQLException ex) {
            handleSQLException(ex);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }

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
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
*/
