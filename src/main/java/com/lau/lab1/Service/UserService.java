package com.lau.lab1.Service;

import com.lau.lab1.domain.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.security.auth.login.LoginException;

public class UserService extends BaseService<User, Integer> {

    public void login(String username, String password) throws LoginException{
        User user = new User(null, username, password, 0);
        boolean succes = context.getModel().getUserRepository().login(user);
        if (!succes)
            throw new LoginException();

    }

    @Override
    public void add(Object... objects) {
        throw new NotImplementedException();
    }

    @Override
    public void remove(Integer key) {
        throw new NotImplementedException();
    }

    @Override
    public void edit(User proba) {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<User> getAll() {
        throw new NotImplementedException();
    }


}
