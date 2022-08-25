package com.email.notify.service.service;

import com.email.notify.service.entity.Email;

import com.email.notify.service.exception.InValidContentTypeException;
import com.email.notify.service.exception.InvalidEmailException;
import com.email.notify.service.model.ContentType;
import com.email.notify.service.model.MailRequest;
import com.email.notify.service.model.MailResponse;
import com.email.notify.service.repository.MailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;

import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMultipart;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class MailService {
    @Autowired
    private MailRepository mailRepository;
    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;

    public MailResponse sendEmail(MailRequest mailRequest) throws Exception {


        String emailRegex = "^[a-zA-Z0-9_+&-]+(?:\\." + "[a-zA-Z0-9_+&-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
        String htmlRegex = "<(“[^”]”|'[^’]’|[^'”>])*>";
        Pattern htmlPattern = Pattern.compile(htmlRegex);
        Pattern pattern = Pattern.compile(emailRegex);
        if (htmlPattern.matcher(mailRequest.getMessageRequest().getBody()).find() && mailRequest.getMessageRequest().getContentType() != ContentType.HTML) {
            throw new InValidContentTypeException("ContentType Must Be Set HTML");
        }
        if (!htmlPattern.matcher(mailRequest.getMessageRequest().getBody()).find() && mailRequest.getMessageRequest().getContentType() != ContentType.TEXT) {
            throw new InValidContentTypeException("ContentType Must Be Set TEXT");
        }
        for (String ccEmail : mailRequest.getCc()) {
            Matcher matcher = pattern.matcher(ccEmail);
            if (!matcher.find()) {
                throw new InvalidEmailException("cc: " + ccEmail + " this is invalid email");
            }
        }
        for (String bccEmail : mailRequest.getBcc()) {
            Matcher matcher = pattern.matcher(bccEmail);
            if (!matcher.find()) {
                throw new InvalidEmailException("bcc: " + bccEmail + " this is invalid email");
            }
        }
        UUID uuid = UUID.randomUUID();

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        BodyPart messageBodyPart = new MimeBodyPart();
        MimeBodyPart attachmentPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();

        message.setFrom(new InternetAddress(mailRequest.getMessageRequest().getEmailFrom()));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(mailRequest.getMessageRequest().getEmailTo()));
        if (mailRequest.getCc().length > 0) {
            message.setRecipients(MimeMessage.RecipientType.CC, InternetAddress.parse(String.join(",", mailRequest.getCc())));
        }
        if (mailRequest.getBcc().length > 0) {
            message.setRecipients(MimeMessage.RecipientType.BCC, InternetAddress.parse(String.join(",", mailRequest.getBcc())));
        }
        message.setSubject(mailRequest.getMessageRequest().getSubject());
        if (mailRequest.getMessageRequest().getContentType() == ContentType.HTML) {
            messageBodyPart.setContent(mailRequest.getMessageRequest().getBody(), "text/html");
        } else {
            messageBodyPart.setText(mailRequest.getMessageRequest().getBody());
        }
        multipart.addBodyPart(messageBodyPart);
        if (!Objects.equals(mailRequest.getAttachmentUrl(), "")) {
            File f = new File(mailRequest.getAttachmentUrl());
            if (f.exists()) {
                attachmentPart.attachFile(f);
                multipart.addBodyPart(attachmentPart);
            } else {
                throw new FileNotFoundException("File Not Found");
            }
        }
        mailRepository.save(Email
                .builder().clientId(String.valueOf(uuid))
                .address(mailRequest.getAddress())
                .emailFrom(mailRequest.getMessageRequest().getEmailFrom())
                .emailTo(mailRequest.getMessageRequest().getEmailTo())
                .body(mailRequest.getMessageRequest().getBody())
                .contentType(mailRequest.getMessageRequest().getContentType())
                .cc(String.join(",", mailRequest.getCc()))
                .bcc(String.join(",", mailRequest.getBcc()))
                .build());
        try {
            message.setContent(multipart);
            Transport.send(message);
            return new MailResponse(false, "Mail Send");

        } catch (Exception e) {
            e.printStackTrace();
            return new MailResponse(false,  "Mail Not Send");
        }
    }

    public List<Email> getDetailsById(String emailFrom) {
        return this.mailRepository.getDetailByEmail(emailFrom);
    }
}
