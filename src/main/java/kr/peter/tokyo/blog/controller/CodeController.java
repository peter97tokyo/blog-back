package kr.peter.tokyo.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.peter.tokyo.blog.entity.Code;
import kr.peter.tokyo.blog.service.CodeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/codes")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping
    public ResponseEntity<?> save(@RequestParam(required = false) Long parent, @RequestBody Code code) {
        Map<String, Object> response = new HashMap<>();
        if (parent != null && parent != 0) {
            Code parentCode = codeService.getCodeById(parent);
            code.setParent(parentCode); 
        }
        Code savedCode = codeService.saveCode(code);
        response.put("status", "result.success");
        if(savedCode == null){
            response.put("status", "result.fail");
        }
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    public ResponseEntity<List<Code>> list(
        @RequestParam(required = false) Long parent,
        @RequestParam String groupYn) {
        List<Code> codes;
        if (parent == null) {
            codes = codeService.getParentCodes(groupYn);
        } else {
            Code parentCode = codeService.getCodeById(parent);
            codes = codeService.getChildCodes(parentCode, groupYn);
        }
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Code> detail(@PathVariable Long id) {
        Code code = codeService.getCodeById(id);
        return ResponseEntity.ok(code);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        codeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }
}

