package org.ahlev.database.mapper;

import org.ahlev.database.model.User;

public interface UserMapper {

    void create(User user);

    User find(String name);

    void clear();
}