package app.moz.controller;


import app.moz.dto.EmailRequest;
import app.moz.service.EmailSenderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class EmailController {

    private final EmailSenderService emailSenderService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        emailSenderService.sendEmail(emailRequest.getToEmail(), emailRequest.getSubject(), emailRequest.getBody());
    }
}
