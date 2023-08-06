package Controller;
import java.util.List;
import io.javalin.Javalin;
import io.javalin.http.Context;
import Model.Account; 
import Model.Message;
import DAO.accountDAO;
import DAO.messageDAO;
import Service.accountService;
import Service.messageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private final messageService MessageService;
    private final accountService AccountService;
    private final ObjectMapper objectMapper;

    public SocialMediaController() {
        MessageService = new messageService();
        AccountService = new accountService();
        objectMapper = new ObjectMapper();
    }
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::registerUserHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }
    private void registerUserHandler(Context ctx) {
        try {
            Account account = objectMapper.readValue(ctx.body(), Account.class);
          
            account.setAccount_id(0);
            Account registeredAccount = accountService.registerAccount(account.username, account.password);
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


}