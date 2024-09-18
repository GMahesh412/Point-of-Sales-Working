package com.pos.point_of_sales.populators;

import com.pos.point_of_sales.entity.CardPayment;
import com.pos.point_of_sales.entity.PaymentTransaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentTransactionPopulator {

        public PaymentTransaction populatePayment(ResultSet resultSet) throws SQLException
        {
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction.setTransactionId(resultSet.getString("transactionId"));
            paymentTransaction.setCardNumber(resultSet.getString("cardNumber"));
            paymentTransaction.setCvv(resultSet.getString("cvv"));
            paymentTransaction.setExpiryDate(resultSet.getString("expiryDate"));
            paymentTransaction.setTotalAmount(resultSet.getDouble("totalAmount"));
            paymentTransaction.setMerchantName(resultSet.getString("merchantName"));
            paymentTransaction.setOrderNumber(resultSet.getString("orderNumber"));
            paymentTransaction.setPaymentStatus(resultSet.getString("paymentStatus"));
            return paymentTransaction;
        }

        public PreparedStatement payment(PreparedStatement preparedStatement, PaymentTransaction paymentTransaction) throws SQLException {
            preparedStatement.setString(1, paymentTransaction.getTransactionId());
            preparedStatement.setString(2,paymentTransaction.getCardNumber());
            preparedStatement.setString(3,paymentTransaction.getCvv());
            preparedStatement.setString(4,paymentTransaction.getExpiryDate());
            preparedStatement.setDouble(5, paymentTransaction.getTotalAmount());
            preparedStatement.setString(6,paymentTransaction.getMerchantName());
            preparedStatement.setString(7,paymentTransaction.getOrderNumber());
            preparedStatement.setString(8,paymentTransaction.getPaymentStatus());
            return preparedStatement;
        }

        public  PreparedStatement paymentUpdate(PreparedStatement preparedStatement, PaymentTransaction paymentTransaction) throws SQLException {
            preparedStatement.setString(1, paymentTransaction.getTransactionId());
            preparedStatement.setString(2,paymentTransaction.getCardNumber());
            preparedStatement.setString(3,paymentTransaction.getCvv());
            preparedStatement.setString(4,paymentTransaction.getExpiryDate());
            preparedStatement.setDouble(5, paymentTransaction.getTotalAmount());
            preparedStatement.setString(6,paymentTransaction.getMerchantName());
            preparedStatement.setString(7,paymentTransaction.getOrderNumber());
            preparedStatement.setString(8,paymentTransaction.getPaymentStatus());
            return preparedStatement;
        }

        public  void deletePayment(PreparedStatement preparedStatement, PaymentTransaction paymentTransaction) throws SQLException {
            preparedStatement.setString(1, paymentTransaction.getTransactionId());
        }

        public  void getPayment(PreparedStatement preparedStatement, String id) throws SQLException {
            preparedStatement.setString(1, id);

        }


        public void savePayment(PreparedStatement preparedStatement, PaymentTransaction paymentTransaction) throws SQLException {
            preparedStatement.setString(1, paymentTransaction.getTransactionId());
            preparedStatement.setString(2,paymentTransaction.getCardNumber());
            preparedStatement.setString(3,paymentTransaction.getCvv());
            preparedStatement.setString(4,paymentTransaction.getExpiryDate());
            preparedStatement.setDouble(5, paymentTransaction.getTotalAmount());
            preparedStatement.setString(6,paymentTransaction.getMerchantName());
            preparedStatement.setString(7,paymentTransaction.getOrderNumber());
            preparedStatement.setString(8,paymentTransaction.getPaymentStatus());
        }



    }

