package Triping.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;

public class EmailBuilder {

    static public SimpleMailMessage build(String recipientAddress, String from, String subject, String body) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom(from);
        email.setSubject(subject);
        email.setText(body);
        return email;
    }
}
