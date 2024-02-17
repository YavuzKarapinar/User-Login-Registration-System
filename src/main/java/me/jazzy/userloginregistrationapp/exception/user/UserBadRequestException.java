package me.jazzy.userloginregistrationapp.exception.user;

import me.jazzy.userloginregistrationapp.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class UserBadRequestException extends BaseException {
    public UserBadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
