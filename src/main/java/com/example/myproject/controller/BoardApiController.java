package com.example.myproject.controller;

import com.example.myproject.model.Board;
import com.example.myproject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import java.util.List;

@RestController
@RequestMapping("/api")
class BoardApiController {

    @Autowired
    private BoardRepository repository;

    @GetMapping("/boards") //조회
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
        @RequestParam(required = false, defaultValue = "") String content
    ) {
        if(StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }
    }

    @PostMapping("/boards") //값 넣기
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/boards/{id}")
    Board one(@PathVariable Long id) {
        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}") // id 조회 후 수정
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/boards/{id}") // id 조회 후 삭제
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}