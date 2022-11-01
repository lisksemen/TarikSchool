package ua.systems.tarik.sschool.tarikschool.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.systems.tarik.sschool.tarikschool.bot.TelegramBot;

@Component
@AllArgsConstructor
public class TelegramService {

    private TelegramBot telegramBot;

    public void sendFeedbackMessage(String name, String phone, String email, String message) {
        StringBuilder text = new StringBuilder("New feedback form submission!\n");

        text.append("Name: ").append(name).append("\n").
                append("Phone: ").append(phone).append("\n").
                append("Email: ").append(email).append("\n").
                append("Message:\n").append(message).append("\n");

        try {
            telegramBot.sendMessage(text + "");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendCallbackMessage(String name, String phone) {
        StringBuilder text = new StringBuilder("New callback form submission!\n");

        text
                .append("Name: ").append(name).append("\n").
                append("Phone: ").append(phone).append("\n");

        try {
            telegramBot.sendMessage(text + "");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
