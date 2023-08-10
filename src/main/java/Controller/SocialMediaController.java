package Controller;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import DAO.AccountDAO;
import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;

import java.util.ArrayList;
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
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getAllMessagesFromUserHandler);

        return app;
    }

    private void createMessageHandler(Context ctx) {
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

    private void deleteMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(messageId);

        if (deletedMessage != null) {
            ctx.json(deletedMessage).status(200);
        } else {
            ctx.status(200).result("");
        }
      
    }

    private void getAllMessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        if (messages != null ) {
            ctx.json(messages).status(200);
        } else {
            ctx.json(messages).status(200);
        }
       

    }

    private void getMessageByIdHandler(Context ctx) {
        int messageId = Integer.parseInt(ctx.pathParam("message_id"));
        Message message = messageService.getMessageById(messageId);
        if (message != null) {
            ctx.json(message).status(200);
        } if (message == null) {
            ctx.status(200);
        } else {
            ctx.status(200);
        }
    }

   

    private void updateMessageByIdHandler(Context ctx) {
        try {
            Message message = objectMapper.readValue(ctx.body(), Message.class);
            int id = Integer.parseInt(ctx.pathParam("message_id"));
            Message updatedMessage = messageService.updateMessageById(id,message);
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

    private void getAllMessagesFromUserHandler(Context ctx) {
        String userID  = ctx.pathParam("account_id");
        int id = Integer.parseInt(userID);
        ArrayList<Message> userMessages = (ArrayList<Message>) messageService.getMessagesByAccountId(id);

        if (userMessages!=null) {
            ctx.json(userMessages).status(200);
        } else {
            ctx.status(500);
        }
    }
}