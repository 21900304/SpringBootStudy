package com.example.myproject.repository;

import com.example.myproject.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

class CustomizedUserRepositoryImpl implements CustomizedUserRepository {


    //@Autowired
    JdbcTemplate jdbcTemplate;


    @PersistenceContext private EntityManager em;

    @Override
    public List<User> findByUsernameCustom(String username) {
        /*QUser qUser = QUser.user;
        JPAQuery<?> query = new JPAQuery<Void>(em);
        List<User> users = query.select(qUser)
                .from(qUser)
                .where(qUser.username.contains(username))
                .fetch();*/
        List<User> users = null;
        return users;
    }

    @Override
    public List<User> findByUsernamejdbc(String username) {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM USER WHERE username like ?",
                new Object[]{"%" + username + "%"},
                new BeanPropertyRowMapper(User.class));
        return users;
    }

    @Override
    public Iterable<User> findByUsernameJdbc(String text) {
        return null;
    }
}
