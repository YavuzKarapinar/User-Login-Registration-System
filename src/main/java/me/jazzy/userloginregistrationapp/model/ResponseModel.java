package me.jazzy.userloginregistrationapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseModel {
    private int status;
    private String message;
    private LocalDateTime time;
}
