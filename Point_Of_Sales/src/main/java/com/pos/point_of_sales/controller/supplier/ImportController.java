package com.pos.point_of_sales.controller.supplier;

import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;
import javafx.application.Platform;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;

import java.util.Date;
import java.sql.SQLException;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static com.pos.point_of_sales.interfaces.CategoryInterface.CATEGORYLIST;
import static com.pos.point_of_sales.interfaces.SupplierInterface.SUPPLIERLIST;
/**
 * ImportController  class is for importing a new Suppliers into the DB and
 * for importing Suppliers from a file,selecting a file, browsing a file ,reading excel it includes.
 * @author Mahesh Yadav
 */
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
    /**
     * Initializes the controller class.
     *
     * @param location  The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resources The ResourceBundle used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        productModel = new ProductModel();
        categoryModel = new CategoryModel();
        supplierModel = new SupplierModel();
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
     *
     * @return The selected file
     */
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
    /**
     * Handles the action event for importing Suppliers from the selected file.
     */
    @FXML
    private void importAction() {
        String path = excelTextField.getText();
        if (!path.isEmpty()) {
            try {
                readDataFromExcel(path);
                ((Stage) uploadButton.getScene().getWindow()).close();
            } catch (FileNotFoundException | SQLException e)
            {
                LOG.error("Error occurred while importing suppliers: {}", e.getMessage());
                handleImportError("File not found!", "Error occurred while importing suppliers: " + e.getMessage());
            }
        } else {
            statusLabel.setText("Please select a file!");
        }
    }
    /**
     * it Reads data from the Excel file and saves Suppliers to the database.
     *
     * @param path The path of the Excel file
     * @throws FileNotFoundException if  file is not found
     * @throws SQLException          if  SQL exception occurs
     */
    private void readDataFromExcel(String path) throws FileNotFoundException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        List<Supplier> supplierList = supplierModel.getSuppliers();
        try {
            FileInputStream file = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String name = row.getCell(0).getStringCellValue().toString();
                String phone = row.getCell(1).getStringCellValue();
                String address = row.getCell(2).getStringCellValue();
                String state = row.getCell(3).getStringCellValue();

                if (isSupplierExisting(supplierList, name))
                {
                    handleImportError("Invalid Suppliers", "Please import valid Suppliers that do not exist.");
                    break;

                } else
                {
                    Supplier supplier = new Supplier(name, phone, address, state, new Date(System.currentTimeMillis()));
                    supplierModel.saveSupplier(supplier);
                    SUPPLIERLIST.clear();
                    SUPPLIERLIST.addAll(supplierModel.getSuppliers());
                    showAlert("Successful", "Imported Successfully.", "Suppliers are imported successfully.");
                    LOG.info("Suppliers are imported successfully.");
                    LOG.debug("Suppliers '{}' imported successfully.", name);
                }
                LOG.info("Reading File Completed.");
            }
        } catch (Exception ex) {
            handleImportError("Error occurred", "Error occurred while reading data from Excel: " + ex.getMessage());
        }
    }
    private boolean isSupplierExisting(List<Supplier> supplierList, String name) {
        for (Supplier supplier : supplierList) {
            if (supplier.getName().contentEquals(name)) {
                return true;
            }
        }
        return false;
    }

    private void handleImportError(String title, String message) {
        statusLabel.setText(title);
        LOG.error(message);
        showAlert("Error", title, message);
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    /**
     * it Handles the cancel action event when it is triggered.
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
        // importProductsBox.valueProperty().setValue(null);
    }
    /**
     * it Handles the closes the window action event when it is triggered.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}
