package kr.peter.tokyo.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import kr.peter.tokyo.blog.entity.BoardConfig;
import kr.peter.tokyo.blog.repository.BoardConfigRepository;


@Service
public class BoardConfigService {

    @Autowired
    private BoardConfigRepository boardConfigRepository;

    public BoardConfig save(BoardConfig code) {
        return boardConfigRepository.save(code);
    }

    public List<BoardConfig> list() {
        return boardConfigRepository.findAll();
    }

    public BoardConfig boardConfigById(Long id) {
        return boardConfigRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public void delete(Long id) {
        boardConfigRepository.deleteById(id);
    }

    public Page<BoardConfig> page(int page, int size) {
        return boardConfigRepository.findAll(PageRequest.of(page, size));
    }

    public List<BoardConfig> listByActive(boolean isActive) {
        return boardConfigRepository.findByIsActive(isActive);
    }
}
