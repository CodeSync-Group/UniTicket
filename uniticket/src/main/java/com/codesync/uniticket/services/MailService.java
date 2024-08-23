package com.codesync.uniticket.services;

import com.codesync.uniticket.dtos.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
public class MailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.email.name}")
    private String emailExchange;

    @Value("${rabbitmq.binding.email.name}")
    private String emailRoutingKey;

    @Value("${MAIL_USER}")
    private String MAIL_USER;

    @RabbitListener(queues = "email_queue")
    public void sendEmail(EmailDetails emailDetails) throws Exception {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            switch (emailDetails.getType()) {
                case "NEW_TICKET":
                    message.setSubject("Ticket #" + emailDetails.getTicketId() + " creado correctamente");
                    break;
                case "PASSWORD_CHANGED":
                    message.setSubject("Cambio de contraseña realizado");
                    break;
            }

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailDetails.getRecipient());
            helper.setText(emailDetails.getTemplate(), true);
            helper.setFrom(new InternetAddress(MAIL_USER, "Mensajeria UniTicket"));
            javaMailSender.send(message);
            System.out.println("Mail sent successfully");
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void newTicketMail(String email, String firstname, String lastname, String ticketId) throws Exception {
        String template = loadEmailTemplate("newTicket.html");
        template = template.replace("{user}", firstname + " " + lastname);
        template = template.replace("{ticketId}", ticketId);

        rabbitTemplate.convertAndSend(emailExchange,
                emailRoutingKey,
                EmailDetails.builder()
                        .recipient(email)
                        .template(template)
                        .type("NEW_TICKET")
                        .ticketId(ticketId)
                        .build());
    }

    public void updatedTicketMail(String email, String firstname, String lastname, String status, String ticketId) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        String template = loadEmailTemplate("updatedTicket.html");
        template = template.replace("{user}", firstname + " " + lastname);
        template = template.replace("{ticketId}", ticketId);
        template = template.replace("{newStatus}", status);


        try {
            message.setSubject("Ticket #" + ticketId + " modificado recientemente");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setText(template, true);
            helper.setFrom(new InternetAddress(MAIL_USER, "Mensajeria UniTicket"));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void resetPasswordMail(String email, String firstname, String lastname, String url) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        String template = loadEmailTemplate("resetPassword.html");
        template = template.replace("{user}", firstname + " " + lastname);
        template = template.replace("{url}", url);


        try {
            message.setSubject(firstname + " " + lastname + ", ahora es más fácil volver a acceder a UniTicket");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setText(template, true);
            helper.setFrom(new InternetAddress(MAIL_USER, "Mensajeria UniTicket"));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void passwordChangedMail(String email, String firstname, String lastname) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        String template = loadEmailTemplate("passwordChanged.html");
        template = template.replace("{user}", firstname + " " + lastname);


        try {
            message.setSubject("Cambio de contraseña realizado");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setText(template, true);
            helper.setFrom(new InternetAddress(MAIL_USER, "Mensajeria UniTicket"));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void newAnalystAssigned(String email, String firstname, String lastname, String ticketId) throws Exception {
        MimeMessage message = javaMailSender.createMimeMessage();

        String template = loadEmailTemplate("analystAssigned.html");
        template = template.replace("{user}", firstname + " " + lastname);
        template = template.replace("{ticketId}", ticketId);


        try {
            message.setSubject("Ticket #" + ticketId + " asignado a su perfil");
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setText(template, true);
            helper.setFrom(new InternetAddress(MAIL_USER, "Mensajeria UniTicket"));
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new Exception(e.getMessage());
        }
    }

    public String loadEmailTemplate(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("static/" + filename);
        InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
        return FileCopyUtils.copyToString(reader);
    }
}
