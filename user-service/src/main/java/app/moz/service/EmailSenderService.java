package app.moz.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {


    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<String> sendEmail(
            String toEmail,
            String subject,
            String body
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mozztechsolutions88@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);



            javaMailSender.send(message);
            log.info("Mail sent successfully! to - {}", toEmail);
            log.info("sent : {}", message);
           return ResponseEntity.ok("Email sent Successfully");


        } catch (Exception e) {
            log.error("error sending email : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email");
        }
    }


}
