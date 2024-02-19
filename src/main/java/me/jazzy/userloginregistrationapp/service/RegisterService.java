package me.jazzy.userloginregistrationapp.service;

import lombok.AllArgsConstructor;
import me.jazzy.userloginregistrationapp.exception.confirmationtoken.ConfirmationTokenBadRequestException;
import me.jazzy.userloginregistrationapp.exception.confirmationtoken.ConfirmationTokenNotFoundException;
import me.jazzy.userloginregistrationapp.exception.user.UserBadRequestException;
import me.jazzy.userloginregistrationapp.model.*;
import me.jazzy.userloginregistrationapp.validator.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegisterService {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailValidator emailValidator;


    public ResponseModel register(RegisterRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());

        if(!isEmailValid)
            throw new UserBadRequestException("Email is not valid");

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        );

        return new ResponseModel(
                HttpStatus.OK.value(),
                userService.saveUser(user),
                LocalDateTime.now()
        );

    }

    @Transactional
    public ResponseModel confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Token not found"));

        if(confirmationToken.getConfirmedAt() != null)
            throw new ConfirmationTokenBadRequestException("Token already confirmed");

        if(confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ConfirmationTokenBadRequestException("Token already expired");

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return new ResponseModel(
                HttpStatus.OK.value(),
                confirmationToken.getUser().getEmail() + " Confirmed",
                LocalDateTime.now()
        );
    }
}
