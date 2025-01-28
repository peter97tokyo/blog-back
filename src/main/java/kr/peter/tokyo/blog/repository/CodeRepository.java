package kr.peter.tokyo.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.peter.tokyo.blog.entity.Code;

public interface CodeRepository extends JpaRepository<Code, Long>{
    
    // 수정된 쿼리 메서드: 부모 코드와 groupYn을 기반으로 검색
    List<Code> findByParentAndGroupYn(Code parent, String groupYn);

    // 부모 코드가 NULL (최상위 코드)인 데이터 검색
    List<Code> findByParentIsNullAndGroupYn(String groupYn);
}
