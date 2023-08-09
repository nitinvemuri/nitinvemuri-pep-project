package Service;
import Model.Message;
import Model.Account;

import java.util.List;

import org.mockito.internal.matchers.And;

import DAO.accountDAO;
import DAO.messageDAO;

public class accountService {

    private final accountDAO AccountDAO;

    public accountService() {
        this.AccountDAO = new accountDAO();
    }

    public Account accountRegister(String username, String password) {
       
       if (username.isBlank() || password.length() < 4) {
        return null;
       }

       if (AccountDAO.getAccountByUsername(username)!=null) {
        return null;
       }

       Account registerAccount = AccountDAO.insertUsername(username, password);
       if (registerAccount != null) {
        return registerAccount;
       } else{
        return null;
       }


    }
    
    public Account realLogin(String username, String password) {
        Account account = AccountDAO.getAccountByUsernameandPassword(username, password);
        if (account != null) {
            return account;
        }
        return null;
    }

    
}
