package urbantech.business;

import java.util.ArrayList;

import urbantech.application.Login;
import urbantech.persistence.hsqldb.createAccountHSQLDB;
import urbantech.persistence.hsqldb.loginAccountHSQLDB;

public class AccountManager {
    private String path;
    private createAccountHSQLDB createAcc = null;
    private loginAccountHSQLDB loginAcc = Login.getAccount();

    public AccountManager(String path) {
        this.path = path;
    }

    // used for test
    public AccountManager(createAccountHSQLDB aDB, loginAccountHSQLDB lDB) {
        this.createAcc = aDB;
        this.loginAcc = lDB;
    }

    // part of create Acc
    public void CreateAcc(String username, String password) {
        createAcc = new createAccountHSQLDB(path, username, password);
    }

    public void addAddress(ValidateDeliveryAddress address) {
        if (!loginAcc.equals(null))
            loginAcc.addAddressToAccount(address);
    }

    public void addCard(CardPayment card) {
        if (!loginAcc.equals(null))
            loginAcc.addCardToAccount(card);
    }

    public boolean isCreateSuccess() {
        return createAcc.isCreateSuccess();
    }

    // part of login acc
    public void loginAcc(String path, String username, String password) {
        loginAcc = new loginAccountHSQLDB(path, username, password);
    }

    public boolean isLoginSuccess() {
        return loginAcc.isLoginSuccess();
    }

    public String getUserName(){
        return loginAcc.getUserName();
    }

    public ValidateDeliveryAddress getAddress() {
        ValidateDeliveryAddress result = null;
        if (!loginAcc.equals(null))
            result = loginAcc.getAddress();

        return result;
    }

    public ArrayList<CardPayment> getCards() {
        ArrayList<CardPayment> result = null;
        if (!loginAcc.equals(null))
            result = loginAcc.getCards();

        return result;
    }

    public void addReceipts(Receipt receipt) {
        if (!loginAcc.equals(null))
            loginAcc.addReceipt(receipt);
    }

    public ArrayList<Receipt> getReceipts() {
        ArrayList<Receipt> result = null;
        if (!loginAcc.equals(null))
            result = loginAcc.getReceipts();
        return result;
    }

    public void logOut() {
        loginAcc = Login.getAccount();
        loginAcc.logout();
    }


}
