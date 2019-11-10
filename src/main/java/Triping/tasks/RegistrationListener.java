package Triping.tasks;

import Triping.models.User;
import Triping.services.IUserService;

import Triping.utils.EmailBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener {

    @Autowired
    private IUserService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    @EventListener
    @Async
    public void confirmRegistration(OnRegistrationCompleteEvent event) {
        //Create and persist token
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        userService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String from = env.getProperty("support.email");

        String subject = "Registration Confirmation";
        String confirmationUrl = event.getAppUrl() + "/verify?token=" + token;
        String body = "Please confirm your account at " + confirmationUrl;

        SimpleMailMessage email = EmailBuilder.build(recipientAddress, from, subject, body);

        try{
            mailSender.send(email);
        }
        catch(MailException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
