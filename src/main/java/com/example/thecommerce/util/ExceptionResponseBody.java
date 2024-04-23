package com.example.thecommerce.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public class ExceptionResponseBody {

    private final String code;
    private final Object message;

    @Nullable
    private final String detail;
}
