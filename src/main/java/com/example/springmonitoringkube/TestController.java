package com.example.springmonitoringkube;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/data")
    public String getData() {
        return "message from k8";
    }
}
