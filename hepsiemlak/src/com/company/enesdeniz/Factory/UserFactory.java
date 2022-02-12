package com.company.enesdeniz.Factory;

import com.company.enesdeniz.Interface.UserInterface;
import com.company.enesdeniz.model.User;

public class UserFactory implements UserInterface {

    @Override
    public User createUser(String type, String name, String email){

        User user = new User(type, name, email);

        return user;
    }



}
