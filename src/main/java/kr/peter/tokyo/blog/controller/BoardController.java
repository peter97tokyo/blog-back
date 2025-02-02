package kr.peter.tokyo.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.peter.tokyo.blog.entity.BoardConfig;
import kr.peter.tokyo.blog.service.BoardConfigService;

@RestController
@RequestMapping("/api/boardConfigs")
public class BoardController {

    @Autowired
    private BoardConfigService boardConfigService;

    @PostMapping
    public ResponseEntity<BoardConfig> save(@RequestBody BoardConfig boardConfig) {
        BoardConfig saved = boardConfigService.saveBoardConfig(boardConfig);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardConfig> detail(@PathVariable Long id) {
        return ResponseEntity.ok(boardConfigService.getBoardConfigById(id));
    }

    @GetMapping
    public ResponseEntity<Page<BoardConfig>> list(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(boardConfigService.getBoardConfigs(page, size));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardConfigService.deleteBoardConfig(id);
        return ResponseEntity.noContent().build();
    }
}

