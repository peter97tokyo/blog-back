package kr.peter.tokyo.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.peter.tokyo.blog.entity.Board;
import kr.peter.tokyo.blog.repository.BoardRepository;


@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board save(Board code) {
        return boardRepository.save(code);
    }

    public List<Board> list() {
        return boardRepository.findAll();
    }

    public Board boardById(Long id) {
        return boardRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<Board> page(int page, int size) {
        return boardRepository.findAll(PageRequest.of(page, size));
    }
}
