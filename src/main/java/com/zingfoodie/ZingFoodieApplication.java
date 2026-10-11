package com.zingfoodie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@SpringBootApplication
@RestController
public class ZingFoodieApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZingFoodieApplication.class, args);
    }

    @GetMapping("/diagnostic")
    public Map<String, String> diagnostic() {
        return Map.of("status", "ok", "message", "Main application routes work");
    }
}
