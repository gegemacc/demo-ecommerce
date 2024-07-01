package demo.demo_ecommerce.controller;

import demo.demo_ecommerce.model.User;
import demo.demo_ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public String signin(@RequestBody User loginRequest) {
        User user = userService.findByEmail(loginRequest.getEmail());
        if (user != null && userService.checkPassword(user, loginRequest.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid credentials";
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (IllegalArgumentException e) {
            // Log dell'errore
            System.out.println("Errore nella creazione dell'utente: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


