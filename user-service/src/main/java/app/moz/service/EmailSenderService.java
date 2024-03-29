package app.moz.service;


import app.moz.dto.ClientBookedEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
            maxAttempts = 10,
            backoff = @Backoff(delay = 2000, maxDelay = 60000, multiplier = 2)
    )
    public void sendEmail(
            ClientBookedEvent clientBookedEvent
    ) throws Exception {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");


            SimpleMailMessage message = new SimpleMailMessage();
            helper.setFrom("mozztechsolutions88@gmail.com");
            helper.setTo(clientBookedEvent.getToEmail());
            helper.setText(createFormattedEmail(clientBookedEvent), true);
            helper.setSubject("Booking Confirmation");


            javaMailSender.send(mimeMessage);
            log.info("Mail sent successfully! to - the CLIENT 🟢🟢 {}", clientBookedEvent.getToEmail());

            log.info("sent link : {}", clientBookedEvent.getLink());


        } catch (Exception e) {
            log.error("error sending email : {}", e.getMessage());

            throw e;
        }
    }

    @KafkaListener(topics = "userNotificationTopic")
    @Retryable(
            value = {Exception.class},
            maxAttempts = 10,
            backoff = @Backoff(delay = 2000, maxDelay = 60000, multiplier = 2)
    )
    public void sendEmailToUser(
            ClientBookedEvent clientBookedEvent
    ) throws Exception {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");


            SimpleMailMessage message = new SimpleMailMessage();
            helper.setFrom("mozztechsolutions88@gmail.com");
            helper.setTo(clientBookedEvent.getUserEmail());
            helper.setText(createFormattedEmailToUser(clientBookedEvent), true);
            helper.setSubject("Booking Confirmation");


            javaMailSender.send(mimeMessage);
            log.info("Mail sent successfully the USER! sec 2 🔴🔴 - {}", clientBookedEvent.getUserEmail());
            log.info("sent link : {}", clientBookedEvent.getLink());


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
    private String createFormattedEmail(ClientBookedEvent clientBookedEvent) {

        String email = "http://localhost:3000/details/" + clientBookedEvent.getClientId() + "/" + clientBookedEvent.getLink();

        String formattedEmail = "Hello " + clientBookedEvent.getUserFirstName() + ",<br/><br/>"
                + "Thank you for booking with us!<br/><br/>"
                + "Booking Details:<br/>"
                + "Name: " + clientBookedEvent.getName() + "<br/>"
                + "Email: " + clientBookedEvent.getToEmail() + "<br/>"
                + "You can view your booking details <a href='" + email + "'>here</a>.<br/><br/>"
                + "We look forward to serving you.<br/><br/>"
                + "Best regards,<br/>"
                + "Mozz Tech Solutions";

        return formattedEmail;
    }

    private String createFormattedEmailToUser(ClientBookedEvent clientBookedEvent) {

        String email = "http://localhost:3000/details/" + clientBookedEvent.getClientId() + "/" + clientBookedEvent.getLink();

        String formattedEmail = "Hello " + clientBookedEvent.getUserFirstName() + ",<br/><br/>"
                + "Good news! A new client has booked with us:<br/><br/>"
                + "Client Name: " + clientBookedEvent.getName() + "<br/>"
                + "Client Email: " + clientBookedEvent.getToEmail() + "<br/><br/>"
                + "You can view the booking details <a href='" + email + "'>here</a>.<br/><br/>"
                + "Thank you for choosing our services!<br/><br/>"
                + "Best regards,<br/>"
                + "Mozz Tech Solutions";


        return formattedEmail;
    }
}
