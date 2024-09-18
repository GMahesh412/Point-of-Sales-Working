package com.pos.point_of_sales.interfaces;

import com.pos.point_of_sales.entity.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 * Represents an interface for managing products in the point of sales system.
 * @author Mahesh Yadav
 */
public interface ProductInterface {

    /**
     * The list of products.
     */
    public ObservableList<Product> PRODUCTLIST = FXCollections.observableArrayList();
}
