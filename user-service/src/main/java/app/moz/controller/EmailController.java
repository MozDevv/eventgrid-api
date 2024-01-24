package app.moz.controller;


import app.moz.dto.EmailRequest;
import app.moz.service.EmailSenderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
@Slf4j
public class EmailController {
/*
    private final EmailSenderService emailSenderService;

    @PostMapping("/email")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
    @TimeLimiter(name = "inventory")
    public CompletableFuture<ResponseEntity<String>> sendEmail(@RequestBody EmailRequest emailRequest) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                emailSenderService.sendEmail(emailRequest.getToEmail(), emailRequest.getSubject(), emailRequest.getBody());
                return ResponseEntity.ok("Message sent successfully");
            } catch (Exception e) {
                log.error("Error sending email - {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email -> " + e.getMessage());
            }
        });
    }

    public CompletableFuture<ResponseEntity<String>> fallBackMethod(EmailRequest emailRequest, RuntimeException runtimeException) {
        return CompletableFuture.completedFuture(
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Fallback: Failed to send email")
        );
    }
*/
}
