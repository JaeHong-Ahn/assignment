package com.example.thecommerce.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
@AllArgsConstructor
public class SuccessResponseBody {

    private final String code;
    private final String message;

    @Nullable
    private final Object data;
}
