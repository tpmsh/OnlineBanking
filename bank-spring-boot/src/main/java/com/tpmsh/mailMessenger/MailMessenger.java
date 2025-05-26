package com.tpmsh.mailMessenger;

import com.tpmsh.config.MailConfig;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailMessenger {
    public static void htmlEmailMessenger(String from, String toMail, String subject, String body) throws MessagingException {
        //Get Mail Config:
        JavaMailSender sender = MailConfig.getMailConfig();
        //Set Mime Message:
        MimeMessage message= sender.createMimeMessage();
        MimeMessageHelper htmlMessage = new MimeMessageHelper(message, true);

        //Set Mail Attributes / Properties:
        htmlMessage.setTo(toMail);
        htmlMessage.setFrom(from);
        htmlMessage.setSubject(subject);
        htmlMessage.setText(body, true);
        //Send Message:
//        sender.send(message);
    }
}
