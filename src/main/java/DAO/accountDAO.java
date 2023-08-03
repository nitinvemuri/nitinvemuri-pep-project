package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import Model.Account;
import Util.ConnectionUtil;

//get idfinished, get accountsfinished, get accountbyusernamefinished, finished getaccountbypassword,
//check if username exists finished,
//insertaccounts finished, updateaccount finished, removeaccounts finished,
//check if account id exists finished



public class accountDAO {
    public List<Account> getAccounts() {
        Connection connections = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Account account = new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
                accounts.add(account);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return accounts;
    }

    public List<Account> getAccountByID(int ID) {
        Connection connections = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE Account_ID = ?";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Account account = new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
                accounts.add(account);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return accounts;
    }


    //getaccountbyusername
    public List<Account> getAccountByUsername(String username) {
        Connection connections = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Account account = new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
                accounts.add(account);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return accounts;
    }

    public List<Account> getAccountByUsernameandPassword(String username, int password) {
        Connection connections = ConnectionUtil.getConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Account WHERE username = ? AND password = ? ";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            ResultSet results = preparedStatement.executeQuery();
            if(results.next()) {
                Account account = new Account(results.getInt("account_id"), results.getString("username"), results.getString("password"));
                accounts.add(account);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    //does id exist
    public boolean doesIDexist(int ID) {
        Connection connections = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            ResultSet results = preparedStatement.executeQuery();
           
            if (results.next()) {
                int count = results.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    public boolean doesUsernameExist(String username) {
        Connection connections = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT COUNT(*) FROM Account WHERE username = ?";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet results = preparedStatement.executeQuery();
           
            if (results.next()) {
                int count = results.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return false;
    }

    //insertaccounts
    public Account insertUsername(String username, int password) {
        Connection connections = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Account (username,passowrd) VALUES (?,?)";
            PreparedStatement preparedStatement = connections.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            ResultSet results = preparedStatement.executeQuery();
            int affRows = preparedStatement.executeUpdate();
            if (affRows > 0 ) {
                ResultSet genrResultSet = preparedStatement.getGeneratedKeys();
                if (genrResultSet.next()) {
                    int generateAccountId = genrResultSet.getInt(1);
                    return new Account(generateAccountId, username, username);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    //insertUpdate
    public void updateaccount(String username, int password, int user_id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Account SET username = ?, password = ? WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, password);
            preparedStatement.setInt(3, user_id);
            preparedStatement.execute();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    //removeAccount
    public void removeAccount(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM ACCOUNT WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
}


