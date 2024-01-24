package app.moz.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {


    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(
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
        } catch (Exception e) {
            log.error("error sending email : {}", e.getMessage());
        }
    }


}
