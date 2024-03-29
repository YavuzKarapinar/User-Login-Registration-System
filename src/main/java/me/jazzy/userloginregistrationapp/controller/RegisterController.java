package me.jazzy.userloginregistrationapp.controller;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.model.RegisterRequest;
import me.jazzy.userloginregistrationapp.model.ResponseModel;
import me.jazzy.userloginregistrationapp.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/registration")
@AllArgsConstructor
public class RegisterController {

    private final RegisterService registerService;

    @PostMapping
    public ResponseEntity<ResponseModel> register(@RequestBody RegisterRequest request) {
        return new ResponseEntity<>(registerService.register(request), HttpStatus.OK);
    }

    @GetMapping("confirm/{token}")
    public ResponseEntity<ResponseModel> confirm(@PathVariable String token) {
        return new ResponseEntity<>(registerService.confirmToken(token), HttpStatus.OK);
    }

}
