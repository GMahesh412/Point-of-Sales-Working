package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.CardPayment;
import com.pos.point_of_sales.entity.Category;
import com.pos.point_of_sales.entity.Invoice;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardPayPopulator {


    public CardPayment populatePayment(ResultSet resultSet) throws SQLException
    {
        CardPayment  cardPayment = new CardPayment();
        cardPayment.setId(resultSet.getString("id"));
        cardPayment.setCardNumber(resultSet.getString("cardNumber"));
        cardPayment.setExpiryDate(resultSet.getString("expiryDate"));
        cardPayment.setCvv(resultSet.getString("cvv"));
        cardPayment.setAmount(resultSet.getDouble("amount"));
        return cardPayment;
    }

    public  PreparedStatement paymentUpdate(PreparedStatement preparedStatement, CardPayment cardPayment) throws SQLException {
        preparedStatement.setString(1, cardPayment.getId());
        preparedStatement.setString(2,cardPayment.getCardNumber());
        preparedStatement.setString(3,cardPayment.getExpiryDate());
        //  preparedStatement.setDate(3, (java.sql.Date) cardPayment.getExpiryDate());
        preparedStatement.setString(4, cardPayment.getCvv());
        preparedStatement.setDouble(5, cardPayment.getAmount());
        return preparedStatement;
    }

    public  void deletePayment(PreparedStatement preparedStatement, CardPayment cardPayment) throws SQLException {
        preparedStatement.setString(1, cardPayment.getId());
    }

    public void savePayment(PreparedStatement preparedStatement, CardPayment cardPayment) throws SQLException {
        preparedStatement.setString(1, cardPayment.getId());
        preparedStatement.setString(2,cardPayment.getCardNumber());
        preparedStatement.setString(3,cardPayment.getExpiryDate());
        //  preparedStatement.setDate(3, (java.sql.Date) cardPayment.getExpiryDate());
        preparedStatement.setString(4, cardPayment.getCvv());
        preparedStatement.setDouble(5, cardPayment.getAmount());
    }

}
