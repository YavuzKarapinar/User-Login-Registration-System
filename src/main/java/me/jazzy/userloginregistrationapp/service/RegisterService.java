package me.jazzy.userloginregistrationapp.service;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.model.ConfirmationToken;
import me.jazzy.userloginregistrationapp.model.RegisterRequest;
import me.jazzy.userloginregistrationapp.model.User;
import me.jazzy.userloginregistrationapp.model.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null)
            throw new IllegalStateException("Token already confirmed");

        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new IllegalStateException("Token already expired");

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return confirmationToken.getUser().getEmail() + " Confirmed";
    }
}
