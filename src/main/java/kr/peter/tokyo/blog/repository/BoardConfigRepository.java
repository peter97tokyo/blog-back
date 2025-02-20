package kr.peter.tokyo.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import kr.peter.tokyo.blog.entity.BoardConfig;
import java.util.List;



public interface BoardConfigRepository extends JpaRepository<BoardConfig, Long> {

    Page<BoardConfig> findAll(Pageable pageable);
    
    List<BoardConfig> findByIsActive(boolean isActive);
}
