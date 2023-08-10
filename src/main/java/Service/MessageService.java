package Service;
import Model.Message;
import Model.Account;
import java.util.List;

import DAO.AccountDAO;
import DAO.MessageDAO;



public class MessageService {
    private final MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public Message createMessage(Message message) {
        AccountDAO accountDAO = new AccountDAO();
        if (isMessageValid(message) && accountDAO.doesAccountExistByTheAccountID(message.getPosted_by())) {
            return messageDAO.insertMessage(message);
        }
        return null;
    }

 
    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }


    public Message getMessageById(int messageId) {
        return messageDAO.getMessageById(messageId);
    }

    
    public Message deleteMessage(int messageId) {
        Message message = getMessageById(messageId);
        if (message != null) {
            messageDAO.deleteMessage(messageId);
            return message;
        }
        return message;
    }

    public Message updateMessage(Message message) {
        Message messageExists = getMessageById(message.getMessage_id());
        if ((messageExists != null) && isMessageValid(messageExists)) {
            messageDAO.updateMessageText(message.getMessage_id(), message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            return messageDAO.getMessageById(message.getMessage_id());
        }
        return null;
    }

    public List<Message> getMessagesByAccountId(int accountId) {
        return messageDAO.getMessagesByAccountId(accountId);
    }

    private boolean isMessageValid(Message message) {
        String messageText = message.getMessage_text();
        return !messageText.isBlank() && messageText.length() <= 254;
    }
}