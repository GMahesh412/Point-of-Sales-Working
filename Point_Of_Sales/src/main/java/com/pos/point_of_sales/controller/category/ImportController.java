package com.pos.point_of_sales.controller.category;

import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Supplier;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SupplierModel;
import com.pos.point_of_sales.populators.CategoryPopulator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
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
import java.io.IOException;
import java.net.URL;

import java.util.Date;
import java.sql.SQLException;
import java.time.LocalTime;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.interfaces.CategoryInterface.CATEGORYLIST;


/**
 * ImportController  class is for importing a new categories into the DB and
 * for importing categories from an Excel file.
 * @author Mahesh Yadav
 */
public class ImportController implements Initializable, ProductInterface
{
    private static final Logger LOG = LoggerFactory.getLogger(ImportController.class);

    @FXML
    private Button selectButton,uploadButton,cancelButton;
    @FXML
    private TextField excelTextField,typeField;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Label statusLabel;
    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SupplierModel supplierModel;
    private CategoryPopulator categoryPopulator;

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
    private void selectAction(ActionEvent event)
    {
        File selectedFile = browseFile();
        if (selectedFile != null)
        {
            excelTextField.setText(selectedFile.getPath());
        }
    }
    /**
     * Opens a file chooser dialog for browsing files.
     *
     * @return The selected file
     */
    private File browseFile()
    {
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
     * Handles the action event for importing categories from the selected file.
     */
    @FXML
    private void importAction() {
        String path = excelTextField.getText();
        if (!path.isEmpty())
        {
            try {
                readDataFromExcel(path);
                ((Stage) uploadButton.getScene().getWindow()).close();
            } catch (FileNotFoundException | SQLException e) {
                statusLabel.setText("File not found!");
                LOG.error("File not found: {}", e.getMessage());
            }
        } else {
            statusLabel.setText("Please select a file!");
        }
    }
    /**
     * it Reads data from the Excel file and saves categories to the database.
     * @param path The path of the Excel file
     * @throws FileNotFoundException if  file is not found
     * @throws SQLException          if  SQL exception occurs
     */
    private void readDataFromExcel(String path) throws FileNotFoundException, SQLException {
        ObservableList<Category> categoryList = categoryModel.getCategories();

        try {
            FileInputStream file = new FileInputStream(path);
            XSSFWorkbook wb = new XSSFWorkbook(file);
            XSSFSheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                String type = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();

                boolean matchFound = false;

                for (Category category : categoryList)
                {
                    if (category.getType().contentEquals(type))
                    {
                        matchFound = true;
                        LOG.warn("Category '{}' already exists or error importing categories.", type);
                        showAlert("Invalid Categories", "Please import valid Categories that do not exist.", "Error in importing Categories.");
                        break;
                    }
                }
                if (!matchFound)
                {
                    Category newCategory = new Category(type, description, new Date(System.currentTimeMillis()));
                    categoryModel.saveCategory(newCategory);
                    CATEGORYLIST.clear();
                    CATEGORYLIST.addAll(categoryModel.getCategories());
                    showAlert("Successful", "Imported Successfully", "Categories have been imported successfully.");
                    LOG.info("Category '{}' added successfully.", type);
                }
                LOG.info("Reading File Completed.");
            }
        } catch (FileNotFoundException e)
        {
            handleImportError("File not found!", "Error occurred while reading data from Excel: " + e.getMessage());
        } catch (IOException e)
        {
            handleImportError("IO Exception!", "Error occurred while reading data from Excel: " + e.getMessage());
        }
    }

    private void handleSQLException(SQLException ex) {
        LOG.error("SQL Exception occurred: {}", ex.getMessage());
        showAlert("SQL Exception", "Error occurred while accessing database.", "Please check the database connection.");
    }

    private void handleImportError(String title, String message) {
        statusLabel.setText(title);
        LOG.error(message);
        showAlert("Error", title, message);
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
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
        typeField.setText("");
        descriptionArea.setText("");
        statusLabel.setText("");
        excelTextField.clear();
        cancelButton.setText("");
        ((Stage) cancelButton.getScene().getWindow()).close();
        // nameField.setText("");
        //     importProductsBox.valueProperty().setValue(null);
    }
    /**
     * it Handles the closing of the window,when the action is triggered.
     * @param event The action event
     */
    @FXML
    public void closeAction(ActionEvent event)
    {
        ((Node) (event.getSource())).getScene().getWindow().hide();
    }
}











/*
package com.pos.point_of_sales.controller.category;

import com.pos.point_of_sales.entity.Category;
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
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;




public class ImportController implements Initializable, ProductInterface
{




    @FXML
    private Button selectButton,uploadButton,cancelButton;

    @FXML
    private TextField excelTextField,typeField;
    @FXML
    private TextArea descriptionArea;

    @FXML
    private Label statusLabel;

    private ProductModel productModel;
    private CategoryModel categoryModel;
    private SupplierModel supplierModel;


   public ImportController() {
       productModel = new ProductModel();
       categoryModel = new CategoryModel();
       supplierModel = new SupplierModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          // productModel = new ProductModel();
           categoryModel = new CategoryModel();
          // supplierModel = new SupplierModel();
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

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText("Imported Succesfull..");
                alert.setContentText("Categories are imported Succesfully...");
                alert.showAndWait();

            } catch (FileNotFoundException | SQLException e)
            {
                statusLabel.setText("File not found!");
                e.printStackTrace();
            }
        }
        else
        {
            statusLabel.setText("Please select a file!");
        }

    }


    private void readDataFromExcel(String path) throws FileNotFoundException, SQLException {
        List<Category> categoryList = categoryModel.getCategories();
        ObservableList<Supplier> suppliers = supplierModel.getSuppliers();

        try (FileInputStream file = new FileInputStream(path);
             XSSFWorkbook wb = new XSSFWorkbook(file))
        {

            XSSFSheet ws = wb.getSheetAt(0);
            Iterator<Row> rowIterator = ws.iterator();

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();
                String type = row.getCell(0).getStringCellValue();
                String description = row.getCell(1).getStringCellValue();
                //         String categoryType = row.getCell(4).getStringCellValue();
*/
/*                Category category1 = null;
                //               Supplier supplier = null;

                for (Category t : categoryList)
                {
                    if (t.getType().contentEquals(type))
                    {
                        category1 = t;
                    }
                }*//*


                Category category = new Category(type, description, new Date(System.currentTimeMillis()));
                categoryModel.saveCategory(category);
                categoryList.clear();
                categoryList.addAll(categoryModel.getCategories());

                System.out.println("Reading File Completed.");
            }
        } catch (Exception ex) {
            handleSQLException(ex);
        }
    }




    @FXML
    public void handleCancel(ActionEvent event)
    {
        typeField.setText("");
        descriptionArea.setText("");
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
