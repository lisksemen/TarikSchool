package ua.systems.tarik.sschool.tarikschool.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.systems.tarik.sschool.tarikschool.bot.TelegramBot;

@Component
@AllArgsConstructor
public class TelegramService {

    private TelegramBot telegramBot;

    public void sendFeedbackMessage(String name, String phone, String email,
                                    String message, String IP, String userAgent) {
        StringBuilder text = new StringBuilder("New feedback form submission!\n");

        text.
                append("Name: ").append(name).append("\n").
                append("Phone: ").append(phone).append("\n").
                append("Email: ").append(email).append("\n").
                append("Message:\n").append(message).append("\n").
                append("IP:").append(IP).append("\n").
                append("User-Agent:").append(userAgent).append("\n");

        try {
            telegramBot.sendMessage(text + "");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCallbackMessage(String name, String phone, String IP, String userAgent) {
        StringBuilder text = new StringBuilder("New callback form submission!\n");

        text.
                append("Name: ").append(name).append("\n").
                append("Phone: ").append(phone).append("\n").
                append("IP:").append(IP).append("\n").
                append("User-Agent:").append(userAgent).append("\n");

        try {
            telegramBot.sendMessage(text + "");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void notifyAboutUser(String IP, String userAgent) {
        StringBuilder text = new StringBuilder("New site visit!\n");

        text.
                append("IP:").append(IP).append("\n").
                append("User-Agent:").append(userAgent).append("\n");

        try {
            telegramBot.sendMessage(text + "");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
