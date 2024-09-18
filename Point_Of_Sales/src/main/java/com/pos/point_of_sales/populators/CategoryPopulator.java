package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.Category;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryPopulator {

    public  Category populateCategory(ResultSet resultSet) throws SQLException
    {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    public  PreparedStatement saveCategory(PreparedStatement preparedStatement, Category category) throws SQLException {
        java.util.Date utilDate = category.getCreateddate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        preparedStatement.setLong(1, category.getId());
        preparedStatement.setString(2,category.getType());
        preparedStatement.setString(3,category.getDescription());
        preparedStatement.setTimestamp(4,sqlTimestamp);
        return preparedStatement;
    }

    public  PreparedStatement categoryUpdate(PreparedStatement preparedStatement, Category category) throws SQLException {
        java.util.Date utilDate = category.getCreateddate();
        java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(utilDate.getTime());
        preparedStatement.setString(1, category.getType());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setTimestamp(3,sqlTimestamp);
        preparedStatement.setLong(4, category.getId());

        return preparedStatement;
    }

    public  void populatePreparedStatementForDelete(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setLong(1, category.getId());
    }

    public  void getCategory(PreparedStatement preparedStatement, Long id) throws SQLException {
        preparedStatement.setLong(1, id);
    }

}



















/*    public static void populateCategoryFromResultSet(ResultSet resultSet, ObservableList<Category> list) throws SQLException {
        Category category = null;
        category= createCategoryFromResultSet(resultSet);
         category = updateCategoryFromResultSet(resultSet);
         category = saveCategoryFromResultSet(resultSet);
         category = deleteCategoryFromResultSet(resultSet);
         category = getCategoryFromResultSet(resultSet);
        category = getCategoriesFromResultSet(resultSet);
        category = getTypesFromResultSet(resultSet);

        list.add(category);
    }*/
 /*   private static Category getTypesFromResultSet(ResultSet resultSet) throws SQLException {
        ObservableList<Category> list = FXCollections.observableArrayList();

        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        list.add(category);
        return category;
    }

    private static Category getCategoriesFromResultSet(ResultSet resultSet) throws SQLException
    {
        ObservableList<Category> list = FXCollections.observableArrayList();

        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        list.add(category);
        return category;
    }

    private static Category getCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    private static Category deleteCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    private static Category updateCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    public static Category createCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    public static Category saveCategoryFromResultSet(ResultSet resultSet) throws SQLException {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        return category;
    }

    public static void populatePreparedStatementForInsert(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setLong(1, category.getId());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getDescription());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }

    public static void populatePreparedStatementForUpdate(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getType());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setLong(3, category.getId());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }


    public static void populatePreparedStatementForDelete(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setLong(1, category.getId());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getDescription());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }

    public static void populatePreparedStatementForSave(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getType());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setLong(3, category.getId());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }
    public static void populatePreparedStatementForGetCategories(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setLong(1, category.getId());
        preparedStatement.setString(2, category.getType());
        preparedStatement.setString(3, category.getDescription());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }

    public static void populatePreparedStatementForGetCategory(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getType());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setLong(3, category.getId());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }


    public static void populatePreparedStatementForGetTypes(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setString(1, category.getType());
        preparedStatement.setString(2, category.getDescription());
        preparedStatement.setLong(3, category.getId());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }
}*/

/*public class CategoryPopulator
{
    ObservableList<Category> list = FXCollections.observableArrayList();
    private Category populateCategory(ResultSet resultSet) throws SQLException
    {
        Category category = new Category();
        category.setId(resultSet.getLong("id"));
        category.setType(resultSet.getString("type"));
        category.setDescription(resultSet.getString("description"));
        category.setCreateddate(resultSet.getDate("createddate"));
        list.add(category);
        return (Category) list;
    }

    private void populatePreparedStatement(PreparedStatement preparedStatement,Category category) throws SQLException {
        preparedStatement.setLong(1, category.getId());
        preparedStatement.setString(2,category.getType());
        preparedStatement.setString(3,category.getDescription());
        preparedStatement.setDate(4, (Date) category.getCreateddate());
    }
}*/
