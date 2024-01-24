package app.moz.service;


import app.moz.dto.ClientBookedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailSenderService {


    @Autowired
    private JavaMailSender javaMailSender;

    @KafkaListener(topics = "notificationTopic")
    @Retryable(
            value = {Exception.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000)
    )
    public void sendEmail(
            ClientBookedEvent clientBookedEvent
    ) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("mozztechsolutions88@gmail.com");
            message.setTo(clientBookedEvent.getToEmail());
            message.setText(clientBookedEvent.getLink());
            message.setSubject("Using Kafka broker!");


            javaMailSender.send(message);
            log.info("Mail sent successfully! to - {}", clientBookedEvent.getToEmail());
            log.info("sent : {}", message);


        } catch (Exception e) {
            log.error("error sending email : {}", e.getMessage());

            throw e;
        }
    }

    @Recover
    public void recover(Exception e, ClientBookedEvent clientBookedEvent) {
        // This method is called when the maximum number of retry attempts is reached
        log.error("Maximum retry attempts reached. Could not send email: {}", e.getMessage());
        // Additional recovery logic can be implemented here
    }


}
