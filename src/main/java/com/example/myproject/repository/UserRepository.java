package com.example.myproject.repository;

import com.example.myproject.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>, CustomizedUserRepository {

    @EntityGraph(attributePaths = { "boards" }) //FetchType을 무시하고 한 번에 Data를 가져옴.
    List<User> findAll();
    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username); // JPQL Query

    @Query(value = "select * from User u where u.username like %?1%", nativeQuery = true)
    List<User> findByUsernameNativeQuery(String username); //Native Query

    //QueryDSL : SKIP
}


//custom query -> 복잡한 조건 데이터를 가져오기 위함