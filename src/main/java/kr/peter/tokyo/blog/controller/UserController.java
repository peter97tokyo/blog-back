package kr.peter.tokyo.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import io.jsonwebtoken.Claims;
import kr.peter.tokyo.blog.entity.User;
import kr.peter.tokyo.blog.service.UserService;
import kr.peter.tokyo.blog.util.JwtUtil;
import kr.peter.tokyo.blog.util.ShaUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user) {

        String username = user.getUsername();
        String password = ShaUtil.encrypt(user.getPassword());

        if(password == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user.fail");
        }
        user.setPassword(password);
        
        Optional<User> checkedUser = userService.findByUsername(username);
        if (!checkedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user.already");
        }
        User savedUser = null;
        savedUser = userService.saveUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("username", savedUser.getUsername());
        response.put("role", savedUser.getRole());
        response.put("status", "user.created");

        return ResponseEntity.ok(savedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        String username = user.getUsername();
        String password = ShaUtil.encrypt(user.getPassword());
        if (password == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user.fail");
        }
        Optional<User> checkedUser = userService.findByUsername(username);
        if (checkedUser.isEmpty()) {
            return ResponseEntity.ok().body("user.empty");
        }

        User foundUser = checkedUser.get(); 
        if (!foundUser.getPassword().equals(password)) {
            return ResponseEntity.ok().body("user.badPassword");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("username", foundUser.getUsername());
        response.put("role", foundUser.getRole());
        response.put("status", "user.login");
        String token = jwtUtil.generateToken(foundUser.getUsername(), "admin");
        response.put("token", token);
        return ResponseEntity.ok(response); 
    }

    @GetMapping("/dashboard")
    public ResponseEntity<?> adminDashboard(@RequestHeader("Authorization") String token) {
        token = token.substring(7);

        Claims claims = jwtUtil.extractClaims(token);

        if ("admin".equals(claims.get("role"))) {
            return ResponseEntity.ok().body("jwt.admin");
        }
        if ("user".equals(claims.get("role"))) {
            return ResponseEntity.ok().body("jwt.user");
        }
        return ResponseEntity.ok().body("jwt.nothing");
    }

}
