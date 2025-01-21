package com.example.myproject.Service;

import com.example.myproject.model.Board;
import com.example.myproject.model.Role;
import com.example.myproject.model.User;
import com.example.myproject.repository.BoardRepository;
import com.example.myproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

     // Transactional(propoagtion = Propogation.REQUIRED) transaction 중복 상황 : active transaction이 있다면 그대로 진행.
    // Transactional(isolation = Isolatin.DEFAULT) 여러명의 사용자가 동시에 회원 가입 할 때 : 여러 transacation이 발생할 때 설정하는 값.
    // proxy : 대리자, 동일한 클래스에서 호출 되었을 때 X 외부 클래스에서 호출이 되었을 때 active, 반드시 pubilc method
    // roleback : 에러가 발생하면 실행, runtime exception만 실행됨. 만약 Custom 하고 싶다면 -> rollbackfor, norollbackfor 사용
    @Transactional //Transaction : 데이터베이스의 상태를 변경하는 작업의 단위
    public User save(User user){

        String encodePassword = passwordEncoder.encode(user.getPassword()); //비밀 번호 암호화
        user.setPassword(encodePassword);
        user.setEnabled(true);

        Role role = new Role();
        role.setId(1L);
        user.getRoles().add(role);

        User savedUser = userRepository.save(user);

        /*if(true){
            throw new RuntimeException("예외 발생!"); // 사용자 저장까지만 DB에 반영, 가입글은 미반영
        }*/

        //사용자 가입 시 가입글 자동 작성
        Board board = new Board();
        board.setTitle("안녕하노");
        board.setContent("반갑노");
        board.setUser(savedUser);
        boardRepository.save(board);


        return savedUser; //user table에 저장하기.
    }

}
