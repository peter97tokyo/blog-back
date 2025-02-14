package kr.peter.tokyo.blog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.peter.tokyo.blog.entity.Code;
import kr.peter.tokyo.blog.service.CodeService;


import java.util.List;


@RestController
@RequestMapping("/api/codes")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Code code) {
        Code parentCode = null;
        if (code.getParent() != null && code.getParent().getId() != null && code.getId() == code.getParent().getId()) {
            code.setParent(null);
        }    
        if (code.getParent() != null && code.getParent().getId() != null && code.getParent().getId() != 0) {
            try {
                Long parentId = code.getParent().getId();
                parentCode = codeService.codeById(parentId);
            } catch (Exception e) {
            }
        }
        code.setParent(parentCode);

        Code savedCode = codeService.save(code);
        if(savedCode == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400
        }
        return ResponseEntity.ok().body(null); // 200
    }

    @GetMapping
    public ResponseEntity<List<Code>> list(
        @RequestParam(required = false) Long parent,
        @RequestParam String groupYn) {
        List<Code> codes;
        if (parent == null) {
            codes = codeService.parents(groupYn);
        } else {
            Code parentCode = codeService.codeById(parent);
            codes = codeService.childrenByParentAndGroupYn(parentCode, groupYn);
        }
        return ResponseEntity.ok(codes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Code> detail(@PathVariable Long id) {
        Code code = codeService.codeById(id);
        return ResponseEntity.ok(code);
    }

    @GetMapping("/children/{codeKey}")
    public ResponseEntity<List<Code>> childByCodeKey(@PathVariable String codeKey) {
        List<Code> codes;
        Code parent = codeService.codeByCodeKey(codeKey);
        codes = codeService.childrenByParent(parent);
        return ResponseEntity.ok(codes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        codeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

