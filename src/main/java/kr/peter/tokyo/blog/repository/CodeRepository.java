package kr.peter.tokyo.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.peter.tokyo.blog.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{
    
    List<Code> findByParentAndGroupYn(Code parent, String groupYn);

    List<Code> findByParentIsNullAndGroupYn(String groupYn);
}
