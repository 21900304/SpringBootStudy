package com.example.myproject.repository;

import com.example.myproject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = { "boards" }) //FetchType을 무시하고 한 번에 Data를 가져옴.
    List<User> findAll();
    User findByUsername(String username);
}
