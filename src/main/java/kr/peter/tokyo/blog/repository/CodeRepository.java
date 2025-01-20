package kr.peter.tokyo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.peter.tokyo.blog.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{
    
}
