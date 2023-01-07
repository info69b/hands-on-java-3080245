package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(db_file);
      System.out.println("We are connected!");
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
  public static void main(String[] args) {
    Customer customer = getCustomer("ojamblinbx@ycombinator.com");
    Account account = getAccount(customer.getAccountID());

    System.out.println(customer.getName());
    System.out.println(account.getBalance());
    System.out.println(account.getId());
    System.out.println(account.getType());

    // customer.setPassword("mypassword");
    System.out.println(customer.getPassword());
    System.out.println(customer.getId());
  }
}
