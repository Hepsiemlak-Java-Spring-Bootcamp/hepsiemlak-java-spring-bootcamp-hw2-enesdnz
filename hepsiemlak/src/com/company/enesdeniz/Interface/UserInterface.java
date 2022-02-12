package com.company.enesdeniz.Interface;

import com.company.enesdeniz.model.Advert;
import com.company.enesdeniz.model.Message;
import com.company.enesdeniz.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface UserInterface {

    User createUser(String type, String name, String email);

}
