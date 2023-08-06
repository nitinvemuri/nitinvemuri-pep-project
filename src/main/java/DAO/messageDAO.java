package DAO;

import Util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import Model.Message;;

public class messageDAO {
    public List<Message> getAllMessages() {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();try {
            String sql = "SELECT * FROM Message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Message message = new Message(results.getInt("message_id"), results.getInt("posted_by"), results.getString("message_text"), results.getLong("time_posted_epoch"));
                messages.add(message);
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return messages;
    }
    public Message getMessageByID(int id) {
        Connection connections = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet results = preparedStatement.executeQuery();
            while(results.next()) {
                Message message = new Message(results.getInt("message_id"), results.getInt("posted_by"), results.getString("message_text"), results.getLong("time_posted_epoch"));
                return message;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int account_id){
        Connection connections = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Message WHERE posted_by = ?";

            PreparedStatement preparedStatement = connections.prepareStatement(sql);
            preparedStatement.setInt(1, account_id);

            ResultSet results = preparedStatement.executeQuery();

            while(results.next()) {
                Message message = new Message(results.getInt("message_id"), results.getInt("posted_by"), results.getString("message_text"), results.getLong("time_posted_epoch"));
                messages.add(message);
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return messages;
    }

    public Message insertMessage(Message message) {
        Connection connections = ConnectionUtil.getConnection();
        try {
            String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
            PreparedStatement preparedStatement = connections.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            ResultSet results = preparedStatement.executeQuery();
            int affRows = preparedStatement.executeUpdate();
            if (affRows > 0 ) {
                ResultSet genrResultSet = preparedStatement.getGeneratedKeys();
                if (genrResultSet.next()) {
                    int generateMessageId = genrResultSet.getInt(1);
                    return new Message(generateMessageId, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());

                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void updateMessage(int messageId, int posted_by, String updatedText, Long time_posted_epoch) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "UPDATE Message SET message_text = ?, posted_by = ?, time_posted_epoch = ? WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, updatedText);
            preparedStatement.setInt(2, posted_by );
            preparedStatement.setLong(3, time_posted_epoch);
            preparedStatement.setInt(4, messageId);
            preparedStatement.execute();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public void removeMessage(int messageId) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "DELETE FROM Message WHERE message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, messageId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }
    public void updateMessage(int message_id, int posted_by, int message_id2, Object get) {
    }
}
