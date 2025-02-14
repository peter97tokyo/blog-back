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

    public Code save(Code code) {
        return codeRepository.save(code);
    }

    public List<Code> listAll() {
        return codeRepository.findAll();
    }

    public List<Code> childrenByParentAndGroupYn(Code parent, String groupYn) {
        return codeRepository.findByParentAndGroupYn(parent, groupYn);
    }

    public List<Code> parents(String groupYn) {
        return codeRepository.findByParentIsNullAndGroupYn(groupYn);
    }

    public Code codeById(Long id) {
        return codeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid id: " + id));
    }

    public void delete(Long id) {
        codeRepository.deleteById(id);
    }

    public List<Code> childrenByParent(Code parent) {
        return codeRepository.findByParent(parent);
    }

    public Code codeByCodeKey(String codeKey) {
        return codeRepository.findByCodeKey(codeKey);
    }
}
