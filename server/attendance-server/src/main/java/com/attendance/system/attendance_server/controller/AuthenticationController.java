// src/main/java/com/attendance/system/attendance_server/controller/AuthenticationController.java
package com.attendance.system.attendance_server.controller;

import com.attendance.system.attendance_server.model.LoginRequest;
import com.attendance.system.attendance_server.security.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    private final JwtUtil jwtUtil;
    private final Properties users;

    public AuthenticationController(JwtUtil jwtUtil,
                                    @Value("classpath:users.properties") Resource usersResource) throws Exception {
        this.jwtUtil = jwtUtil;
        this.users = new Properties();
        this.users.load(usersResource.getInputStream());
    }

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        String stored = users.getProperty(req.getUsername());
        if (stored != null && stored.equals(req.getPassword())) {
            String token = jwtUtil.generateToken(req.getUsername());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(401)
                    .body(Map.of("error", "Invalid credentials"));
        }
    }
}
