package Service;
import Model.Message;
import Model.Account;

import java.util.List;

import org.mockito.internal.matchers.And;

import DAO.AccountDAO;

public class AccountService {

    private final AccountDAO AccountDAO;

    public AccountService() {
        this.AccountDAO = new AccountDAO();
    }

    public Account accountRegister(String username, String password) {
       
       if (username.isBlank() || password.length() < 4) {
        return null;
       }

       if (AccountDAO.getAccountByUsername(username)!=null) {
        return null;
       }

       Account registerNewAccount = AccountDAO.insertUsername(username, password);
       if (registerNewAccount != null) {
        return registerNewAccount;
       } else{
        return null;
       }


    }
    
    public Account Login(String username, String password) {
        Account account = AccountDAO.getAccountByUsernameandPassword(username, password);
        if (account != null) {
            return account;
        }
        return null;
    }

    
}
