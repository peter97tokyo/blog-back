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
        System.out.println("wonjun");
        System.out.println(code);
        Code parentCode = null;
        if (code.getParent() != null && code.getParent().getId() != null && code.getParent().getId() != 0) {
            try {
                Long parentId = code.getParent().getId();
                parentCode = codeService.getCodeById(parentId);
            } catch (Exception e) {
                // ID에 해당하는 Code 객체를 찾을 수 없는 경우 처리
                // 예: 로그 출력, 예외 던지기, parentCode를 null로 유지
                
            }
        }
        code.setParent(parentCode);

        Code savedCode = codeService.saveCode(code);
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

