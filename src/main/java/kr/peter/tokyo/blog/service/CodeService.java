package kr.peter.tokyo.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.peter.tokyo.blog.entity.Code;
import kr.peter.tokyo.blog.repository.CodeRepository;


@Service
public class CodeService {

    @Autowired
    private CodeRepository codeRepository;

    // Create or Update
    public Code saveCode(Code code) {
        return codeRepository.save(code);
    }

    // Read All
    public List<Code> getAllCodes() {
        return codeRepository.findAll();
    }

    public List<Code> getChildCodes(Code parent, String groupYn) {
        return codeRepository.findByParentAndGroupYn(parent, groupYn);
    }

    public List<Code> getParentCodes(String groupYn) {
        return codeRepository.findByParentIsNullAndGroupYn(groupYn);
    }

    public Code getCodeById(Long id) {
        return codeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    // Delete
    public void deleteCode(Long id) {
        codeRepository.deleteById(id);
    }
}
