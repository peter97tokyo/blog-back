package kr.peter.tokyo.blog.service;

import java.util.List;
import java.util.Optional;

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

    // Read by ID
    public Optional<Code> getCodeById(Long id) {
        return codeRepository.findById(id);
    }

    // Delete
    public void deleteCode(Long id) {
        codeRepository.deleteById(id);
    }
}
