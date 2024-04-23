package com.example.thecommerce.controller;

import com.example.thecommerce.util.SuccessResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.thecommerce.util.DefaultHttpResponse.OK_WITH_NO_DATA;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/health-check")
    public ResponseEntity<SuccessResponseBody> healthCheck() {
        return OK_WITH_NO_DATA;
    }

}
