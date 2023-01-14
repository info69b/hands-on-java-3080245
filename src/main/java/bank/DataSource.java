package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  
  // method that establish connection with the database

  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(db_file);
      // System.out.println("Connecion with the database is established!");
    } catch(SQLException e) {
      e.printStackTrace();
    }
    
    return connection;

  }

  // getCustomr method

  public static Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?";
    Customer customer = null;

    try(Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {

      statement.setString(1, username);
      try (ResultSet resultSet =  statement.executeQuery()) {
        customer = new Customer(
          resultSet.getInt("id"),
          resultSet.getString("name"),
          resultSet.getString("username"),
          resultSet.getString("password"),
          resultSet.getInt("account_id"));
      }


    }catch(SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  // getAccount method

  public static Account getAccount(int accountId) {
    String sql = "select * from accounts where id = ?";
    Account account = null;

    try(Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)){
      
      statement.setInt(1, accountId);

      try(ResultSet resultSet = statement.executeQuery()){
        account = new Account(
          resultSet.getInt("ID"),
          resultSet.getString("TYPE"),
          resultSet.getDouble("BALANCE"));
      }
    } catch(SQLException e){
        e.printStackTrace();
        }

      return account;
  }

  // Unility method that updates Account's balance

  public static void updateAccountBalance(int accountId, Double amount) {
    String sql = "update accounts set balance = ? where id = ?";
    try(Connection connection = connect(); PreparedStatement statement = connection.prepareStatement(sql)) {
      statement.setDouble(1, amount);
      statement.setInt(2, accountId);

      statement.executeUpdate();

    } catch(SQLException e) {
      e.printStackTrace();
    }
  }
}