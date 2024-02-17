package me.jazzy.userloginregistrationapp.controller;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.model.RegisterRequest;
import me.jazzy.userloginregistrationapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegisterController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(userService.saveUser(request));
    }

}
