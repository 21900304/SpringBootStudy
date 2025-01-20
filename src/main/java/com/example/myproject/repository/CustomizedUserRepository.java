package com.example.myproject.repository;

import com.example.myproject.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);

    List<User> findByUsernamejdbc(String username);

    Iterable<User> findByUsernameJdbc(String text);
}
