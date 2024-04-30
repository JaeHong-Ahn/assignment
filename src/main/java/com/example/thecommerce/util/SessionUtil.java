package com.example.thecommerce.util;

import com.example.thecommerce.exception.CustomException;
import com.example.thecommerce.exception.ErrorCode;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionUtil {

    public static void validateSession(HttpSession session) {
        if (session == null || session.getAttribute("user") == null) {
            throw new CustomException(ErrorCode.NOT_AUTHENTICATED);
        }
    }
}