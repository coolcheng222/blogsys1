package com.sealll.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author sealll
 * @time 2021/5/12 20:10
 */
@ResponseStatus(code= HttpStatus.BAD_REQUEST)
public class ClauseNotFoundException extends Exception{
    public ClauseNotFoundException() {
    }

    public ClauseNotFoundException(String message) {
        super(message);
    }

    public ClauseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
