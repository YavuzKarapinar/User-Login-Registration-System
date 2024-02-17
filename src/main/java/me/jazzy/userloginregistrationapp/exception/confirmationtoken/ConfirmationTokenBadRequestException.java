package me.jazzy.userloginregistrationapp.exception.confirmationtoken;

import me.jazzy.userloginregistrationapp.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class ConfirmationTokenBadRequestException extends BaseException {
    public ConfirmationTokenBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
