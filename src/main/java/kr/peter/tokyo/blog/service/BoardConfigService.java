package kr.peter.tokyo.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.peter.tokyo.blog.entity.BoardConfig;
import kr.peter.tokyo.blog.repository.BoardConfigRepository;


@Service
public class BoardConfigService {

    @Autowired
    private BoardConfigRepository boardConfigRepository;

    public BoardConfig saveBoardConfig(BoardConfig code) {
        return boardConfigRepository.save(code);
    }

    public List<BoardConfig> getAllBoardConfigs() {
        return boardConfigRepository.findAll();
    }

    public BoardConfig getBoardConfigById(Long id) {
        return boardConfigRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public void deleteBoardConfig(Long id) {
        boardConfigRepository.deleteById(id);
    }
}
