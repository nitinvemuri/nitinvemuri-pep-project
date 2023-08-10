package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import java.util.List;

import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    private final MessageService messageService;
    private final AccountService accountService;
    private final ObjectMapper objectMapper;

    public SocialMediaController() {
        messageService = new MessageService();
        accountService = new AccountService();
        objectMapper = new ObjectMapper();
    }

    public Javalin startAPI() {

        Javalin app = Javalin.create();
        app.post("/register", this::registerUserHandler);
        app.post("/login", this::loginUserHandler);
        app.post("/messages", this::createMessage);
        app.get("/messages", this::getAllMessages);
        app.get("/messages/{message_id}", this::getMessageById);
        app.delete("/messages/{message_id}", this::deleteMessageById);
        app.patch("/messages/{message_id}", this::updateMessageById);

        return app;
    }

    private void createMessage(Context ctx) {
        try {
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            Message createdMessage = messageService.createMessage(message);
            if (createdMessage != null) {
                String response = objectMapper.writeValueAsString(createdMessage);
                ctx.json(response).status(200);
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    


    }

    private void getAllMessages(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages).status(200);

    }

    private void getMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        ctx.json(message).status(200);
        
    }

    private void deleteMessageById(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);

        if (deletedMessage != null) {
            ctx.json(deletedMessage).status(200);
        } else {
            ctx.status(200).result("");
        }
      
    }

    private void updateMessageById(Context ctx) {
        try {
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            Message updatedMessage = messageService.updateMessage(message);
            if (updatedMessage != null) {
                String response = objectMapper.writeValueAsString(updatedMessage);
                ctx.result(response).status(200);
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    private void registerUserHandler(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            account.setAccount_id(0);
            Account registeredAccount = accountService.accountRegister(account.username, account.password);
            if (registeredAccount != null) {
                String response = objectMapper.writeValueAsString(registeredAccount);
                ctx.json(response).status(200);
            } else {
                ctx.status(400);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }

    private void loginUserHandler(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
            Account loggedInAccount = accountService.Login(account.username, account.password);
            if (loggedInAccount != null) {
                String response = objectMapper.writeValueAsString(loggedInAccount);
                ctx.json(response).status(200);
            } else {
                ctx.status(401);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            ctx.status(400);
        }
    }
}