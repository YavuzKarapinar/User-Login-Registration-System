package me.jazzy.userloginregistrationapp.exception.user;

import me.jazzy.userloginregistrationapp.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
