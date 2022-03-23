package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleEmailService {

    private final JavaMailSender javaMailSender;

    public void send(final Mail mail) {
        log.info("Starting email preparation...");
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            log.info("Email has been sent.");
        } catch ( NullPointerException e) {
            log.error("Failed to process email sending: " + e.getMessage(), e);
        }
    }


    private SimpleMailMessage createMailMessage(final Mail mail) {
        log.info("New start of sending new e-mail");
        SimpleMailMessage mailMessage = new SimpleMailMessage();

            mailMessage.setTo(mail.getMailTo());
            mailMessage.setSubject(mail.getSubject());
            mailMessage.setText(mail.getMessage());
            mailMessage.setCc(mail.getToCc());

       /* Optional<String> opt = Optional.of(mail.getToCc());
        mail.getToCc() = String.valueOf(Optional.ofNullable(mail.getToCc()).isPresent());*/
        try {
            if (!mail.getToCc().isEmpty()) {
                mailMessage.setCc(mail.getToCc());
            }
            log.info("New e-mail sent correctly");

        } catch (NullPointerException e) {
            log.error("Another fail of e-mail send attempt:" + e.getMessage(), e);
        }

        return mailMessage;
        }
    }

