package com.pos.point_of_sales.controller.pos;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.pos.point_of_sales.controller.admin.AdminController;
import com.pos.point_of_sales.controller.barcode.BarcodeScannerController;
import com.pos.point_of_sales.controller.payment.PaymentTransactionController;
import com.pos.point_of_sales.entity.*;
import com.pos.point_of_sales.interfaces.ProductInterface;
import com.pos.point_of_sales.model.CategoryModel;
import com.pos.point_of_sales.model.ProductModel;
import com.pos.point_of_sales.model.SalesModel;
import com.pos.point_of_sales.model.SupplierModel;
import com.pos.point_of_sales.populators.ProductPopulator;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

import javax.imageio.ImageIO;

public class PosController implements Initializable, ProductInterface {

    private static final Logger LOG = LoggerFactory.getLogger(PosController.class);

    @FXML
    private Label statusLabel;
    @FXML
    private TextField barcodeTextField;
    @FXML
    private TableView<Product> productTableView;
    @FXML
    public TableView<Item> listTableView;
    @FXML
    private TableColumn<Product, String> productColumn,supplierColumn1,categoryColumn1,barcodeColumn;
    @FXML
    private TableColumn<Product, Long> idColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn1, quantityColumn1;
    @FXML
    private TableColumn<Item, String> itemColumn;
    @FXML
    private TableColumn<Item, Double> priceColumn, quantityColumn, cgstColumn, sgstColumn, discountColumn, totalColumn;
    @FXML
    private TextField searchField, productField, priceField, quantityField,stockField;
    @FXML
    private TextField subTotalField, discountField, sgstField, cgstField, netPayableField;
    @FXML
    private Button addButton, resetButton, paymentButton,cardPayButton,scanButton;
    @FXML
    private Label quantityLabel;
    @FXML
    private ObservableList<Item> ITEMLIST;
    private ProductModel productModel;
    private double xOffset = 0;
    private double yOffset = 0;
    String barcode;
    @FXML
    private ListView<String> productList;
    private SalesModel salesModel;
    private  Product product;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ITEMLIST = FXCollections.observableArrayList();
        productModel = new ProductModel();
        salesModel = new SalesModel();
        product = new Product();
        listTableView.setEditable(true);
        setupQuantityColumnEditing();
        try {
            loadData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        barcodeColumn.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        supplierColumn1.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getSupplier().getName()));
        categoryColumn1.setCellValueFactory((TableColumn.CellDataFeatures<Product, String> p)
                -> new SimpleStringProperty(p.getValue().getCategory().getType()));
        priceColumn1.setCellValueFactory(new PropertyValueFactory<>("Price"));
        quantityColumn1.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        productTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue));
        productTableView.setItems(PRODUCTLIST);
        filterData();
        itemColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        quantityColumn.setEditable(true);
           /*
        sgstColumn.setCellValueFactory(new PropertyValueFactory<>("sgst"));
        cgstColumn.setCellValueFactory(new PropertyValueFactory<>("cgst"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));*/
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
        listTableView.setItems(ITEMLIST);
        addButton
                .disableProperty()
                .bind(Bindings.isEmpty(productTableView.getSelectionModel().getSelectedItems()));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(event -> removeSelected());
        contextMenu.getItems().add(deleteItem);

        // Attach context menu to table view , this is to delete a row if you want to remove from listTableView after selection
        listTableView.setContextMenu(contextMenu);

        // Handle right-click event to show context menu //To delete a row from listTableView
        listTableView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(listTableView, event.getScreenX(), event.getScreenY());
            }
        });
        /*resetButton
                .disableProperty()
                .bind(Bindings.isEmpty(listTableView.getSelectionModel().getSelectedItems()));*/

        itemColumn.setCellFactory(column -> {
            return new TableCell<Item, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });
        priceColumn.setCellFactory(column -> {
            return new TableCell<Item, Double>() {
                @Override
                protected void updateItem(Double price, boolean empty) {
                    super.updateItem(price, empty);
                    if (price == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(price));
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        quantityColumn.setCellFactory(column -> {
            return new TableCell<Item, Double>() {
                @Override
                protected void updateItem(Double quantity, boolean empty) {
                    super.updateItem(quantity, empty);
                    if (quantity == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(quantity));
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        totalColumn.setCellFactory(column -> {
            return new TableCell<Item, Double>() {
                @Override
                protected void updateItem(Double total, boolean empty) {
                    super.updateItem(total, empty);
                    if (total == null || empty) {
                        setText(null);
                    } else {
                        setText(String.valueOf(total));
                        setAlignment(Pos.CENTER);
                    }
                }
            };
        });

        productTableView.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 2)
            {
                Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();
                if (selectedProduct != null)
                {
                    addItemToList(selectedProduct);
                    setupQuantityColumnEditing();
                    calculation();
                }
            }
        });

        scanButton.setOnMouseClicked(event ->
        {
            if (event.getClickCount() == 1)
            {
                Product barcodeData = productTableView.getSelectionModel().getSelectedItem();
                if (barcodeData != null)
                {
                    addItemToList(barcodeData);
                    setupQuantityColumnEditing();
                    calculation();
                }
            }
        });
    }

  private void setupQuantityColumnEditing() {
      listTableView.setOnMouseClicked(mouseEvent -> {
          if (mouseEvent.getClickCount() == 1) {
              quantityColumn.setCellFactory(column -> new TableCell<Item, Double>() {
                  private TextField textField;

                  protected void updateItem(Double quantity, boolean empty) {
                      super.updateItem(quantity, empty);
                      if (empty || quantity == null) {
                          setText(null);
                      } else {
                          setText(String.valueOf(quantity));
                          setAlignment(Pos.CENTER);
                      }
                  }
                  public void startEdit() {
                      if (!isEmpty()) {
                          super.startEdit();
                          createTextField();
                          setText(null);
                          setGraphic(textField);
                          textField.requestFocus();
                      }
                  }
                  public void cancelEdit() {
                      super.cancelEdit();
                      setText(String.valueOf(getItem()));
                      setGraphic(null);
                  }

                    public void commitEdit(Double newValue) {
                      super.commitEdit(newValue);

                      if (!ITEMLIST.isEmpty()) {
                          ITEMLIST.remove(0);
                      }
                      //  newValue is not null
                      if (newValue == null) {
                          return;
                      }
                      boolean flag = true;
                      Product product = new Product();

                      Item currentItem = (Item) getTableRow().getItem();
                      if (currentItem == null)
                      {
                          return;
                      }
                      // Set the qty of  current item
                      currentItem.setQuantity(newValue);
                      double quantity = newValue;
                    //  String productName = productField.getText();
                      String productName = currentItem.getItemName();
                      double unitPrice = currentItem.getUnitPrice();
                      double sgst = parseDoubleOrDefault(sgstField.getText(), 0.0);
                      double cgst = parseDoubleOrDefault(cgstField.getText(), 0.0);
                      double discount = parseDoubleOrDefault(discountField.getText(), 0.0);
                      double total;
                      boolean variantProd = product.getVariantProd();
                      if (variantProd)
                      {
                          total = unitPrice * quantity / 1000;
                      } else {
                          // calculation for non-variantproduct
                          total = unitPrice * quantity;
                      }
                      ITEMLIST.add(new Item(productName, unitPrice, quantity, Math.round(sgst), Math.round(cgst), discount, total));
                      calculation();
                  }
                  /* to parse double or return a default value if i/p null or empty */
                  private double parseDoubleOrDefault(String input, double defaultValue) {
                      if (input == null || input.isEmpty()) {
                          return defaultValue;
                      }
                      try {
                          return Double.parseDouble(input);
                      } catch (NumberFormatException e) {
                          return defaultValue;
                      }
                  }

                  private void createTextField() {
                      textField = new TextField(getString());
                      textField.setOnAction(event -> {
                          commitEdit(Double.valueOf(textField.getText()));
                          getTableView().refresh();
                      });
                      textField.setOnKeyPressed(event -> {
                          if (event.getCode() == KeyCode.ESCAPE) {
                              cancelEdit();
                          }
                      });
                  }
                  private String getString() {
                      return getItem() == null ? "" : getItem().toString();
                  }
              });
          }
      });
  }


    public void addItemToList(Product product)
    {
        if (ITEMLIST != null && ITEMLIST.size() > 0)
        { boolean alreadyAdded = ITEMLIST.stream()
                .anyMatch(item -> item.getItemName().equals(product.getProductName()));
            if (alreadyAdded)
            {
                return;
            }
        }
        ITEMLIST.add(new Item(product.getProductName(), product.getPrice(), 1, 0, 0, 0, product.getPrice()));
        listTableView.setItems(ITEMLIST);
        listTableView.editableProperty();
        quantityColumn.setEditable(true);
        calculation();
    }
    private void filterData() {
        FilteredList<Product> searchedData = new FilteredList<>(PRODUCTLIST, e -> true);
        searchField.setOnKeyReleased(e -> {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(product -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    // Check if barcode contains search value
                    if (String.valueOf(product.getBarcode()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    // Check if the ID contains the search value
                    if (String.valueOf(product.getId()).contains(lowerCaseFilter)) {
                        return true;
                    }
                    // Check if the product name contains the search value
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
            sortedData.comparatorProperty().bind(productTableView.comparatorProperty());
            productTableView.setItems(sortedData);
        });
    }
    public void loadData() throws SQLException {
        if (!PRODUCTLIST.isEmpty()) {
            PRODUCTLIST.clear();
        }
        PRODUCTLIST.addAll(productModel.getProducts());
    }
    private void showDetails(Product product) {
        if (product != null) {
            quantityField.setDisable(false);
            productField.setText(product.getProductName());
            priceField.setText(String.valueOf(product.getPrice()));
            double quantity = product.getQuantity();
            if (quantity > 0) {
                quantityField.setEditable(true);
                quantityField.setStyle(null);
            } else {
                quantityField.setEditable(false);
                quantityField.setStyle("-fx-background-color: red;");
            }
            stockField.setText(String.valueOf(quantity));
            // quantityLabel.setText("Stock: " + String.valueOf(quantity));
        } else {
            productField.setText("");
            priceField.setText("");
            stockField.setText("");
            // quantityLabel.setText("");
        }
    }
    private void resetProductTableSelection() {
        productTableView.getSelectionModel().clearSelection();
    }
    private void resetItemTable() {
        ITEMLIST.clear();
    }
    private void resetAdd() {
        productField.setText("");
        priceField.setText("");
        quantityField.setText("");
        resetQuantityField();
        stockField.setText("");
        //quantityLabel.setText("");
    }
    private void resetInvoice() {
        subTotalField.setText("0.00");
        sgstField.setText("0.00");
        cgstField.setText("0.00");
        discountField.setText("0.00");
        netPayableField.setText("0.00");
    }
    private void resetQuantityField() {
        quantityField.setDisable(false);
    }
    private void resetPaymentButton() {
        paymentButton.setDisable(false);
    }
    private void resetcardPayButton() {
        cardPayButton.setDisable(false);
    }
    private void resetInterface() throws SQLException {
        loadData();
        resetPaymentButton();
        resetcardPayButton();
        resetProductTableSelection();
        resetItemTable();
        resetQuantityField();
        resetAdd();
        resetInvoice();
    }
    @FXML
    public void resetAction(ActionEvent event) throws SQLException {
        resetInterface();
    }

    /**
     * it handles the Add Qty to the UI  action.
     *
     * @param event -the action event
     * @throws SQLException if an SQL error occurs
     */
    @FXML
    public void addAction(ActionEvent event) throws SQLException {
        boolean flag = true;
        product = new Product();
        if (validateInput())
        {
            String productName = productField.getText();
            double unitPrice = Double.parseDouble(priceField.getText());
            double quantity = Double.parseDouble(quantityField.getText());
            double sgst = Double.parseDouble(sgstField.getText());
            double cgst = Double.parseDouble(cgstField.getText());
            double discount = Double.parseDouble(discountField.getText());
            double total;
            boolean variantProd = product.getVariantProd();
            if (variantProd) {
                total = unitPrice * quantity / 1000.0;
            } else {
                // calculation for non-variantproduct
                total = unitPrice * quantity;
            }
            ITEMLIST.add(new Item(productName, unitPrice, quantity, Math.round(sgst), Math.round(cgst), discount, total));
            calculation();
            resetAdd();
            productTableView.getSelectionModel().clearSelection();
        }
    }

    /**
     * performs calculations for the payment.
     */
    private void calculation() {
        try {
            Properties config = ConfigReader.readConfig();
            DecimalFormat decfor = new DecimalFormat("0.00");
            double cgstRate = Double.parseDouble(config.getProperty("cgstRate"));
            double sgstRate = Double.parseDouble(config.getProperty("sgstRate"));
            double discountAmount = Double.parseDouble(config.getProperty("discountAmount"));
            double netPayablePrice;
            double subTotalPrice = 0.0;
            subTotalPrice = listTableView.getItems().stream().map(
                    (item) -> item.getTotal()).reduce(subTotalPrice, (accumulator, _item) -> accumulator + _item);
            if (subTotalPrice > 0) {
                paymentButton.setDisable(false);
                double sgst = (double) (subTotalPrice * sgstRate) / 100;
                double cgst = (double) (subTotalPrice * cgstRate) / 100;
                if (subTotalPrice >= 250) {
                    netPayablePrice = (double) (Math.ceil((subTotalPrice + sgst + cgst) - discountAmount));
                    discountField.setText(String.valueOf(discountAmount));
                } else {
                    netPayablePrice = (double) (Math.ceil(subTotalPrice + sgst + cgst));
                    discountAmount = 0.0;
                    discountField.setText(String.valueOf(discountAmount));
                }
                subTotalField.setText(String.valueOf((subTotalPrice)));
                sgstField.setText(String.valueOf(decfor.format(sgst)));
                cgstField.setText(String.valueOf(decfor.format(cgst)));
                discountField.setText(String.valueOf(discountAmount));
                netPayableField.setText(String.valueOf(netPayablePrice));
                listTableView.setEditable(true);
                quantityField.setEditable(true);
            }
        } catch (IOException e) {
            LOG.error("An error occurred while reading configuration: {}", e.getMessage());
        }
    }

    /**
     * Performs the card payment action.
     *
     * @param event the action event
     * @throws SQLException if an SQL error occurs
     */
    @FXML
    public void cardPayAction(ActionEvent event) throws SQLException {
        try {
            Payment payment = new Payment(
                    Double.parseDouble(subTotalField.getText().trim()),
                    Double.parseDouble(sgstField.getText().trim()),
                    Double.parseDouble(cgstField.getText().trim()),
                    Double.parseDouble(discountField.getText().trim()),
                    Double.parseDouble(netPayableField.getText().trim())
            );
            ObservableList<Item> sold = listTableView.getItems();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Card_pay.fxml"));
            PaymentTransactionController controller = new PaymentTransactionController();
            loader.setController(controller);
            controller.setData(Double.parseDouble(netPayableField.getText().trim()), sold, payment, barcode);

            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400, 300);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(false);
            stage.setTitle("Card Payment Panel");
            Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
            stage.getIcons().add(image);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            resetInterface();
        } catch (IOException e) {
            LOG.error("Error loading FXML file: {}", e.getMessage());
        }
    }

    /**
     * Handles the action event for processing payment.
     *
     * @param event The action event
     * @throws Exception If an error occurs during payment processing
     */
    @FXML
    public void paymentAction(ActionEvent event) throws Exception {
        try {
            Payment payment = new Payment(
                    Double.parseDouble(subTotalField.getText().trim()),
                    Double.parseDouble(sgstField.getText().trim()),
                    Double.parseDouble(cgstField.getText().trim()),
                    Double.parseDouble(discountField.getText().trim()),
                    Double.parseDouble(netPayableField.getText().trim())
            );

            ObservableList<Item> sold = listTableView.getItems();
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/fxml/Invoice.fxml")));
            InvoiceController controller = new InvoiceController();
            loader.setController(controller);
            controller.setData(Double.parseDouble(netPayableField.getText().trim()), sold, payment);
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 320, 200);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.setMaximized(false);
            stage.setTitle("Cash Payment Panel");
            Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
            stage.getIcons().add(image);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            resetInterface();
        } catch (IOException | NumberFormatException e)
        {
            LOG.error("Error occurred during payment processing: {}", e.getMessage());
            showAlert("Error", "Payment Error", "An error occurred during payment processing.");
        }
    }

    private void removeSelected() {
        int index = listTableView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            listTableView.getItems().remove(index);
            calculation();
        }
    }

    private boolean validateInput()
    {
        String errorMessage = "";
        if (quantityField.getText() == null || quantityField.getText().length() == 0)
        {
            errorMessage += "Quantity not supplied!\n";
        } else {
            double quantity = Double.parseDouble(quantityField.getText());
            double available = Double.parseDouble(stockField.getText());
            if (quantity > available)
            {
                errorMessage += "Out of Stock!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Please input the valid number of products");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            quantityField.setText("");
            return false;
        }
    }

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
        //stage.getIcons().add(new Image("/images/tdsitelogo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Handles the action event for barcode scanning.
     *
     * @param event The action event
     */
    @FXML
    private void barcodeAction(ActionEvent event) {
        Stage stage = new Stage();
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        Label scanLabel = new Label("Scan the Barcode");
        scanLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        scanLabel.setAlignment(Pos.CENTER);

        // TextField for barcode path
        TextField barcodeTextField = new TextField();
        barcodeTextField.setPromptText("Select barcode image file path");
        HBox.setHgrow(barcodeTextField, Priority.ALWAYS);

        Button selectButton = new Button("SELECT");
        selectButton.setOnAction(e -> {
            File selectedFile = browseImage();
            if (selectedFile != null) {
                barcodeTextField.setText(selectedFile.getPath());
                barcodeTextField.setEditable(false);
            }
        });
        HBox fileSelectionBox = new HBox(barcodeTextField, selectButton);
        fileSelectionBox.setSpacing(10);
        fileSelectionBox.setAlignment(Pos.CENTER);

        // Button to trigger barcode scanning
        Button doneButton = new Button("DONE");
        doneButton.setOnAction(e -> {
            readBarcode(barcodeTextField.getText());
            stage.close();
        });

        // Adding nodes to the VBox
        root.getChildren().addAll(scanLabel, fileSelectionBox, doneButton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 400, 120);
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMaximized(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Scan Products");
        Image image = new Image(getClass().getResourceAsStream("/images/tdsitelogo.png"));
        stage.getIcons().add(image);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    /**
     * Reads the barcode from the specified image file path.
     *
     * @param imagePath The file path of the barcode image
     */
    @FXML
    private void readBarcode(String imagePath) {
        if (!imagePath.isEmpty()) {
            try {
                String barcodeData = barcodeDecode(imagePath);
                if (barcodeData != null && !barcodeData.isEmpty()) {
                    Product product = productModel.getProductByBarcode(barcodeData);
                    if (product != null) {
                        addItemToList(product);
                        showAlert("Barcode Reading Successful", "Product Scan is Success..", barcodeData + " is read/decode Successfully...");
                    } else {
                        statusLabel.setText("Product not found for barcode: " + imagePath + barcodeData);
                        showAlert("Warning", "Product Not Found", "Product not found for barcode: " + barcodeData);
                    }
                } else {
                    statusLabel.setText("No barcode found in the image.");
                    showAlert("Warning", "No Barcode Found", "No barcode found in the image.");
                }
            } catch (Exception e) {
                statusLabel.setText("Error reading barcode: " + e.getMessage());
                showAlert("Error", "Barcode Reading Error", "Error reading barcode: " + e.getMessage());
                LOG.error("Error reading barcode: {}", e.getMessage());
            }
        } else {
            statusLabel.setText("Please select a barcode image.");
            showAlert("Warning", "No Image Selected", "Please select a barcode image.");
        }
    }

    /**
     * Decodes the barcode from the specified image file path.
     *
     * @param imagePath The file path of the barcode image
     * @return The decoded barcodeData
     * @throws IOException       if an I/O error occurs
     * @throws NotFoundException if the barcode is not found
     */
    private String barcodeDecode(String imagePath) throws IOException, NotFoundException {
        BufferedImage bf = ImageIO.read(new FileInputStream(imagePath));
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bf)));
        MultiFormatReader reader = new MultiFormatReader();
        Result result = reader.decode(bitmap);
        return result.getText();
    }

    /**
     * it Opens a file chooser dialog for selecting an image file.
     *
     * @return The selected image file
     */
    private File browseImage() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Barcode Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg")
        );
        return fileChooser.showOpenDialog(stage);
    }
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}