package kr.peter.tokyo.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.peter.tokyo.blog.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
    User findByEmail(String email);
}
