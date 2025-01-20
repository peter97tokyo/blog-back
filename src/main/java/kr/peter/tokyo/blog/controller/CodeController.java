package kr.peter.tokyo.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.peter.tokyo.blog.entity.Code;
import kr.peter.tokyo.blog.service.CodeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/codes")
public class CodeController {

    @Autowired
    private CodeService codeService;

    // Create or Update
    @PostMapping
    public ResponseEntity<?> createOrUpdateCode(@RequestBody Code code) {
        Map<String, Object> response = new HashMap<>();
        Code savedCode = codeService.saveCode(code);
        response.put("status", "result.success");
        if(savedCode == null){
            response.put("status", "result.fail");
        }
        return ResponseEntity.ok().body(response);
    }

    // Read All
    @GetMapping
    public ResponseEntity<List<Code>> getAllCodes() {
        List<Code> codes = codeService.getAllCodes();
        return ResponseEntity.ok(codes);
    }

    // Read by ID
    @GetMapping("/{id}")
    public ResponseEntity<Code> getCodeById(@PathVariable Long id) {
        Optional<Code> code = codeService.getCodeById(id);
        return code.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        codeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }
}

