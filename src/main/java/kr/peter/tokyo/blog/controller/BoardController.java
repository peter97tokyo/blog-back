package kr.peter.tokyo.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.peter.tokyo.blog.entity.BoardConfig;
import kr.peter.tokyo.blog.service.BoardConfigService;

@RestController
@RequestMapping("/api/boardConfigs")
public class BoardController {

    @Autowired
    private BoardConfigService boardConfigService;

    @PostMapping
    public ResponseEntity<BoardConfig> saveBoardConfig(@RequestBody BoardConfig boardConfig) {
        BoardConfig saved = boardConfigService.saveBoardConfig(boardConfig);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardConfig> getBoardConfig(@PathVariable Long id) {
        return ResponseEntity.ok(boardConfigService.getBoardConfigById(id));
    }

    @GetMapping
    public ResponseEntity<List<BoardConfig>> getAllBoardConfigs() {
        return ResponseEntity.ok(boardConfigService.getAllBoardConfigs());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoardConfig(@PathVariable Long id) {
        boardConfigService.deleteBoardConfig(id);
        return ResponseEntity.noContent().build();
    }
}

