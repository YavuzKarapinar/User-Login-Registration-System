package me.jazzy.userloginregistrationapp.exception.confirmationtoken;

import me.jazzy.userloginregistrationapp.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class ConfirmationTokenNotFoundException extends BaseException {
    public ConfirmationTokenNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
