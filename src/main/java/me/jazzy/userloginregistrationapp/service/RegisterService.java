package me.jazzy.userloginregistrationapp.service;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.model.RegisterRequest;
import me.jazzy.userloginregistrationapp.model.User;
import me.jazzy.userloginregistrationapp.model.UserRole;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;


    public String register(RegisterRequest request) {
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        );

        return userService.saveUser(user);
    }
}
