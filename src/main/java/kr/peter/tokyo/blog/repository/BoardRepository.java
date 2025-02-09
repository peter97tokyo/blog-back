package kr.peter.tokyo.blog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.peter.tokyo.blog.entity.Board;


public interface BoardRepository extends JpaRepository<Board, Long> {

    Page<Board> findAll(Pageable pageable);
    
}
