package com.kent.logindemo.service;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kent.logindemo.config.MailConfig;

@Service
public class MailService implements InitializingBean {
    private final String from = "kent.develop.tw@gmail.com";

    private final MailConfig mailConfig;
    private JavaMailSenderImpl javaMailSender;

    public MailService(MailConfig mailConfig) {
        this.mailConfig = mailConfig;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailConfig.getHost());
        javaMailSender.setPort(mailConfig.getPort());
        javaMailSender.setUsername(mailConfig.getUsername());
        javaMailSender.setPassword(mailConfig.getPassword());

        Properties props = javaMailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", mailConfig.isAuthEnabled());
        props.put("mail.smtp.starttls.enable", mailConfig.isStarttlsEnabled());
        props.put("mail.transport.protocol", mailConfig.getProtocol());
    }

    public void sendEmail(String to, String subject, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
            System.out.println("Email sent to " + to + " successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // SimpleMailMessage message = new SimpleMailMessage();
        // message.setFrom(from);
        // message.setTo(to);
        // message.setSubject(subject);
        // message.setText(content);
        // javaMailSender.send(message);
        // System.out.println("Email sent to " + to + " successfully");
    }
}
