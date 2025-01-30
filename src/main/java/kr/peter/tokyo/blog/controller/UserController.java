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

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody User user) {
        String username = user.getUsername();
        String password = ShaUtil.encrypt(user.getPassword());
        if(password == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400 
        }
        user.setPassword(password);
        User checkedUser = userService.findByUsername(username);
        if (checkedUser != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // 409
        }
        User savedUser = null;
        savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        String username = user.getUsername();
        String password = ShaUtil.encrypt(user.getPassword());
        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 400
        }
        User checkedUser = userService.findByUsername(username);
        if (checkedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404
        }
        if (!checkedUser.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null); // 401
        }
        if(!checkedUser.getRole().equals("admin")){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403
        }
        Map<Object, Object> response = new HashMap<>();
        String token = jwtUtil.generateToken(checkedUser.getUsername(), "admin");
        response.put("token", token);
        return ResponseEntity.ok().body(response);
    }   

    @GetMapping("/dashboard")
    public ResponseEntity<User> dashboard(@RequestHeader("Authorization") String token) {
        token = token.substring(7);
        Claims claims = jwtUtil.extractClaims(token);
        if ("admin".equals(claims.get("role"))) {
            return ResponseEntity.ok().body(null);
        }
        if ("user".equals(claims.get("role"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 404
    }

}
