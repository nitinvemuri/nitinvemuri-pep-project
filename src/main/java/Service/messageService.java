package Service;
import Model.Message;
import Model.Account;

import java.util.List;

import org.mockito.internal.matchers.And;

import DAO.accountDAO;
import DAO.messageDAO;

public class messageService {
    private final messageDAO MessageDAO;

    public messageService() {
        this.MessageDAO = new messageDAO();
    }

    public Message createMessage(Message message) {
        accountDAO AccountDAO = new accountDAO();
        if (isMessageValid(message) && AccountDAO.doesIDexist(message.getPosted_by())) {
            return MessageDAO.insertMessage(message);
        }
        return null;
    }

    public Message getMessageByID(int message_id) {
        return MessageDAO.getMessageByID(message_id);
    }

    public Message deleteMessage(int message_id) {
        Message message = getMessageByID(message_id);
        if (message != null ) {
            MessageDAO.removeMessage(message_id);
        }
        return message;
    }

    public List<Message>getAllMessages() {
        return MessageDAO.getAllMessages();
    }

    public List<Message> getMessageByAccountID(int account_id) {
        return MessageDAO.getMessagesByAccountId(account_id);
    }

    public Message updateMessage(Message message) {
        Message messageExists = getMessageByID(message.getMessage_id());
        if ((messageExists != null) && isMessageValid(messageExists)) {
            MessageDAO.updateMessage(message.getMessage_id(), message.getPosted_by(), message.getMessage_id(), message.getTime_posted_epoch());
            return MessageDAO.getMessageByID(message.getMessage_id());
        }
        return null;
    }

    private boolean isMessageValid(Message message) {
        String messageText = message.getMessage_text();
        return !messageText.isBlank() && messageText.length() <= 255;
    }

}
