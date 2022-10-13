package ua.systems.tarik.sschool.tarikschool.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MailService {
    private JavaMailSender mailSender;

    private SimpleMailMessage mailMessage;

    public void sendFeedbackMail(String email, String phone, String name, String message) {
        Thread mailThread = new Thread(() -> feedbackMailMethod(email, phone, name, message));
        mailThread.start();
    }

    public void sendCallbackMail(String name, String phone) {
        Thread mailThread = new Thread(() -> callbackMailMethod(name, phone));
        mailThread.start();
    }

    private void feedbackMailMethod(String email, String phone, String name, String message) {
        mailMessage.setSubject("Feedback form submission");
        mailMessage.setText("Form has submitted recently!\n" +
                "Name: " + name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Message:\n'''\n" + message + "\n'''"
        );

        mailSender.send(mailMessage);
    }

    private void callbackMailMethod(String name, String phone) {
        mailMessage.setSubject("Callback form submission");
        mailMessage.setText("Form has submitted recently!\n" +
                "Name: " + name + "\n" +
                "Phone: " + phone + "\n"
        );

        mailSender.send(mailMessage);
    }

}
