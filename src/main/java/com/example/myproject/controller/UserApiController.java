package com.example.myproject.controller;

import com.example.myproject.model.Board;
import com.example.myproject.model.User;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api")
@Slf4j
class UserApiController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/users")
    Iterable<User> all(@RequestParam(required = false) String method, @RequestParam(required = false) String text) {
        Iterable<User> users = null;
        /*log.debug("getBoards().size(); 호출 전");
        log.debug("getBoards().size(); {}",users.get(0).getBoards().size());
        log.debug("getBoards().size(); 호출 후");*/
        if("query".equals(method)) { //JPQL로 조회하는 경우
            users = repository.findByUsernameQuery(text);
        } else if("nativeQuery".equals(method)) { //nativeQuery로 조회하는 경우
            users = repository.findByUsernameNativeQuery(text);
        } /*else if("querydsl".equals(method)) {
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);
            users = repository.findAll(predicate); // queryDSL로 조회하는 경우.
        }*/ /*else if("querydslCustom".equals(method)) {
            users = repository.findByUsernameCustom(text);
        }*/ else if("jdbc".equals(method)) {
            users = repository.findByUsernameJdbc(text);
        } else{
            users = repository.findAll();
        }


        return users;
    } //모든 user를 불러오면 성능 상 문제가 생긴다.

    @PostMapping("/users")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/users/{id}")
    User one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/users/{id}")
    User replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
//                    user.setBoards(newUser.getBoards());
                    user.getBoards().clear();
                    user.getBoards().addAll(newUser.getBoards());
                    for(Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}