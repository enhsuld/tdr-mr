package com.peace.users.model;

import java.util.ArrayList;
import java.util.List;

import com.peace.users.model.*;

/**
 * Created by Chris on 7/22/14.
 */
public class AccountList {

    private List<User> accounts = new ArrayList<User>();

    public AccountList(List<User> list) {
        this.accounts = list;
    }

    public List<User> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<User> accounts) {
        this.accounts = accounts;
    }
}
