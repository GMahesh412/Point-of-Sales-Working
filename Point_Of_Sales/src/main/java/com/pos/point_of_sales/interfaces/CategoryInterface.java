package com.pos.point_of_sales.interfaces;

import com.pos.point_of_sales.entity.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents an interface for managing categories in the point of sales system.
 * @author Mahesh Yadav
 */
public interface CategoryInterface {

    /** 
     * The list of categories.
     */
    public ObservableList<Category> CATEGORYLIST = FXCollections.observableArrayList();
}
