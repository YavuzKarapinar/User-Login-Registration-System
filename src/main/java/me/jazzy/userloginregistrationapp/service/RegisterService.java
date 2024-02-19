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
    private final EmailService emailService;

    public ResponseModel register(RegisterRequest request) {
        boolean isEmailValid = emailValidator.test(request.getEmail());

        if (!isEmailValid)
            throw new UserBadRequestException("Email is not valid");

        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                UserRole.USER
        );

        String token = userService.saveUser(user);
        String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;
        emailService.send(user.getEmail(), "Confirm your email", textMessage(link));

        return new ResponseModel(
                HttpStatus.OK.value(),
                token,
                LocalDateTime.now()
        );

    }

    @Transactional
    public ResponseModel confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Token not found"));

        if (confirmationToken.getConfirmedAt() != null)
            throw new ConfirmationTokenBadRequestException("Token already confirmed");

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now()))
            throw new ConfirmationTokenBadRequestException("Token already expired");

        confirmationTokenService.setConfirmedAt(confirmationToken);
        userService.enableUser(confirmationToken.getUser().getEmail());

        return new ResponseModel(
                HttpStatus.OK.value(),
                confirmationToken.getUser().getEmail() + " Confirmed",
                LocalDateTime.now()
        );
    }

    private String textMessage(String link) {
        return  "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Hesap Onaylama Kodu</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <p>Merhaba,</p>\n" +
                "    <p>Hesabınızı doğrulamak için aşağıdaki bağlantıya tıklayabilir veya tarayıcınızdaki adres çubuğuna yapıştırabilirsiniz:</p>\n" +
                "    <a href=\"" + link  + "\" style=\"color: #007bff; text-decoration: none;\">\n" +
                "        <strong>[ONAY_KODU]</strong>\n" +
                "    </a>\n" +
                "    <p>Bu linki kullanarak hesabınızı doğrulayabilirsiniz.</p>\n" +
                "    <p>Eğer bu işlemi siz yapmadıysanız, lütfen bu e-postayı dikkate almayınız.</p>\n" +
                "    <br>\n" +
                "    <p>Saygılarımla,<br>Yavuz Selim Karapınar</p>\n" +
                "</body>\n" +
                "</html>";

    }
}
