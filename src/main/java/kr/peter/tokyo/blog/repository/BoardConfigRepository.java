package kr.peter.tokyo.blog.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import kr.peter.tokyo.blog.entity.BoardConfig;


public interface BoardConfigRepository extends JpaRepository<BoardConfig, Long> {
    
}
