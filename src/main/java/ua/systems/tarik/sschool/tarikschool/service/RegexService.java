package ua.systems.tarik.sschool.tarikschool.service;

import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

@AllArgsConstructor
public class RegexService {

    private final String PHONE;

    private final String EMAIL;

    public boolean isEmail(String email) {
        return Pattern.compile(EMAIL).matcher(email).matches();
    }

    public boolean isPhone(String phone) {
        return Pattern.compile(PHONE).matcher(phone).matches();
    }
}
