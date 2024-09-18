package com.pos.point_of_sales.model;

import com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory;
import com.pos.point_of_sales.dao.ProductDao;
import com.pos.point_of_sales.entity.Product;
import com.pos.point_of_sales.populators.ProductPopulator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pos.point_of_sales.controller.jdbc.JDBCConnectionFactory.handleSQLException;
import static com.pos.point_of_sales.queries.ProductQueries.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ProductModel class implements the ProductDao interface to provide data access methods
 * related to products. It handles database operations for managing product records.
 *
 * @author Mahesh Yadav
 */
public class ProductModel implements ProductDao {

    private static final Logger LOG = LoggerFactory.getLogger(ProductModel.class);

    private CategoryModel categoryModel;
    private SupplierModel supplierModel;
    private ProductPopulator productPopulator = new ProductPopulator();
    private Product product = new Product();
    private ProductModel productModel;

    private PreparedStatement preparedStatement = null;
    private Connection connection = null;
    private ResultSet resultSet = null;

    /**
     * Retrieves all products from the DB.
     *
     * @return an ObservableList of Product objects representing all products
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ObservableList<Product> getProducts() throws SQLException {
        ObservableList<Product> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_ALL_PRODUCTS);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(productPopulator.populateProduct(resultSet));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving products: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
        return list;
    }

    /**
     * Retrieves a product by its barcode from the db.
     *
     * @param barcodeData the barcode of the product to retrieve
     * @return the Product object representing the product with the given barcode
     * @throws SQLException
     */
    public Product getProductByBarcode(String barcodeData) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_BARCODE);
            preparedStatement.setString(1, barcodeData);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = productPopulator.populateProduct(resultSet);
            } else {
                LOG.error("Product not found for barcode: {}", barcodeData);
                return null;
            }
        } catch (SQLException e)
        {
            LOG.error("Error occurred while retrieving product by barcode: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
        return product;
    }

    /**
     * Retrieves a product by its ID from the database.
     *
     * @param id the ID of the product to retrieve
     * @return the Product object representing the product with the given ID
     * @throws SQLException
     */
    @Override
    public Product getProduct(long id) throws SQLException {
        try {
            product = new Product();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_ID);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = productPopulator.populateProduct(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving product by ID: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return product;
    }

    /**
     * Retrieves a product by its name from the db.
     *
     * @param productName the name of the product to retrieve
     * @return the Product object representing the product with the given name
     * @throws SQLException
     */
    @Override
    public Product getProductByName(String productName) throws SQLException {
        Product product = null;
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(GET_PROD_BY_NAME);
            preparedStatement.setString(1, productName);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = productPopulator.populateProduct(resultSet);
            }
        } finally {
            if (resultSet != null && connection != null && preparedStatement !=null) {
                resultSet.close();
                connection.close();
                preparedStatement.close();
            }
        }
        return product;
    }

    /**
     * Saves a product record to the database.
     *
     * @param product the Product object to Save
     * @throws SQLException iff db access error occurs
     */
    @Override
    public void saveProduct(Product product) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SAVE_PRODUCT);
            productPopulator.populatePreparedStatementForSave(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while saving product: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
    }

    /**
     * Updates a product record in the database.
     *
     * @param product the Product object to update
     * @throws SQLException
     */
    @Override
    public void updateProduct(Product product) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(UPDATE_PRODUCT);
            productPopulator.updateProduct(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while updating product: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null){
                connection.close();
            }
        }
    }

    /**
     * Selects a variant product from the db.
     *
     * @param variantProd the variant product to select
     * @throws SQLException
     */
    public void selectVarProduct(double variantProd) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PRODUCT_BY_VARPROD);
            preparedStatement.setDouble(1, variantProd);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                product = productPopulator.populateProduct(resultSet);
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while selecting variant product: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Increases the quantity of a product in the db.
     *
     * @param product the Product object to increase quantity
     * @throws SQLException
     */
    @Override
    public void increaseProduct(Product product) throws SQLException {
        productPopulator = new ProductPopulator();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(INCREASE_PRODUCT);
            productPopulator.increaseProductQuantity(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while increasing product quantity: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Decreases the quantity of a product in the db.
     *
     * @param product the Product object to decrease quantity
     * @throws SQLException
     */
    @Override
    public void decreaseProduct(Product product) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DECREASE_PRODUCT);
            productPopulator.decreaseProductQuantity(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while decreasing product quantity: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Deletes a product record from the database.
     *
     * @param product the Product object to delete
     * @throws SQLException
     */
    @Override
    public void deleteProduct(Product product) throws SQLException {
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DELETE_PRODUCT);
            productPopulator.deleteProduct(preparedStatement, product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOG.error("Error occurred while deleting product: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    /**
     * Retrieves the names of all products from the database.
     *
     * @return an ObservableList of strings representing the names of all products
     * @throws SQLException
     */
    @Override
    public ObservableList<String> getProductNames() throws SQLException {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(SELECT_PRODUCT_NAMES);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            LOG.error("Error occurred while retrieving product names: {}", e.getMessage());
            handleSQLException(e);
        } finally {
            if (connection != null)
            {
                connection.close();
            }
        }
        return list;
    }
}








/*

    public  void barCode(){
        Connection connection = null;
        try {
            connection = JDBCConnectionFactory.getConnection();
            ProductModel productModel = new ProductModel();
            List<Product> products = productModel.getProducts();
            for (Product product : products) {
                String productId = String.valueOf(product.getId());
                String barcodeNumber = generateBarcode(Long.valueOf(productId));
                updateBarcodeInDatabase(connection, barcodeNumber, Long.valueOf(productId));
                System.out.println("Generated barcode for product ID " + productId + ": " + barcodeNumber);
            }
        } catch (Exception e) {
         System.out.println("Error while creating barcode..");
        }

    }

*/
/*    public static String barCode(){
        Connection connection = null;
        try
        {
            connection = JDBCConnectionFactory.getConnection();
            ProductModel productModel = new ProductModel();
            List<Product> products = productModel.getProducts();
            for (Product product : products)
            {
                String productId = String.valueOf(product.getId());
                String barcodeNumber = generateBarcode(Long.valueOf(productId));
                updateBarcodeInDatabase(connection, productId, Long.valueOf((barcodeNumber)));
                System.out.println("Generated barcode for product ID " + productId + ": " + barcodeNumber);
            }
        } catch (SQLException | RuntimeException e)
        {
            handleException(e);
        }

        return null;
    }*//*



   private static String generateBarcode(Long productId) {
        String barcodeContent = Long.toString(productId);
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        //BarcodeFormat barcodeFormat = BarcodeFormat.EAN_13;
        int width = 200;
        int height = 50;
        try {
            //EAN13Writer barcodeWriter = new EAN13Writer();
            Code128Writer writer = new Code128Writer() ;
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 10);
            BitMatrix bitMatrix = writer.encode(barcodeContent, barcodeFormat, width, height, hints);


            // Define the file path where you want to save the barcode image
            String filePath = "D:/POS_jdbc/Point_Of_Sales/src/main/resources/Barcodes/barcode_" + productId + ".jpg";

            // Create a file object
            File file = new File(filePath);

            // Write the BitMatrix to the file as PNG image
            MatrixToImageWriter.writeToStream(bitMatrix, "JPG", new FileOutputStream(file));

            return barcodeContent;
        } catch (IOException e)
        {
            throw new RuntimeException("Error generating barcode: " + e.getMessage(), e);
        }
    }

*/
/*     private static String generateBarcode(Long productId)
    {
        String barcodeContent = Long.toString(productId);
        BarcodeFormat barcodeFormat = BarcodeFormat.CODE_128;
        int width = 200;
        int height = 50;
        try {
            Code128Writer barcodeWriter = new Code128Writer();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 10);
            BitMatrix bitMatrix = barcodeWriter.encode(barcodeContent, barcodeFormat, width, height, hints);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
            return barcodeContent;
        } catch (IOException e) {
            throw new RuntimeException("Error generating barcode: " + e.getMessage(), e);
        }
    }*//*

    private static void updateBarcodeInDatabase(Connection conn, String barcode, Long id) {
        String sql = "UPDATE Products SET barcode = ? WHERE id = ?";
        try
        {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, barcode);
            pstmt.setLong(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e)
        {
            throw new RuntimeException("Error updating barcode in database: " + e.getMessage(), e);
        }
    }
    private static void handleException(Exception e) {
        System.err.println("An error occurred: " + e.getMessage());
        e.printStackTrace();
    }
}
*/




/*

    @Override
    public void increaseProduct(Product product) throws SQLException {
        Connection connection = null;
        productPopulator  = new ProductPopulator();
        PreparedStatement preparedStatement = null;
        try {
            product = new Product();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(ProductHelper.INCREASE_PRODUCT);
            productPopulator.populatePreparedStatement(preparedStatement,product);
            preparedStatement.executeUpdate();
           */
/* preparedStatement.setInt(1, (int) product.getQuantity());
            preparedStatement.setLong(2, product.getId());
            preparedStatement.executeUpdate();*//*


        } catch (SQLException e) {
            handleSQLException(e);
        }finally {
            connection.close();
        }
    }

    @Override
    public void decreaseProduct(Product product) throws SQLException {
        Connection connection = null;
        productPopulator  = new ProductPopulator();
        PreparedStatement preparedStatement = null;
        try
        {
            product = new Product();
            connection = JDBCConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(DECREASE_PRODUCT);
            productPopulator.populatePreparedStatement(preparedStatement,product);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
        }finally {
            connection.close();
        }
    }*/
