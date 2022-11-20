package ua.systems.tarik.sschool.tarikschool.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestOperations;
import ua.systems.tarik.sschool.tarikschool.config.CaptchaConfig;
import ua.systems.tarik.sschool.tarikschool.model.GoogleResponse;

import java.net.URI;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class CaptchaService {

    private static final Pattern RESPONSE_PATTERN = Pattern.compile("[A-Za-z0-9_-]+");
    private CaptchaConfig captchaSettings;
    private RestOperations restTemplate;

    public void processResponse(String response, String IP) {
        if (!responseSanityCheck(response)) {
            throw new RuntimeException("Response contains invalid characters");
        }

        URI verifyUri = URI.create(String.format(
                "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s&remoteip=%s",
                captchaSettings.getSecret(), response, IP));

        GoogleResponse googleResponse = restTemplate.getForObject(verifyUri, GoogleResponse.class);


        if (googleResponse == null || !googleResponse.isSuccess()) {
            throw new RuntimeException("reCaptcha was not successfully validated");
        }
    }

    private boolean responseSanityCheck(String response) {
        return StringUtils.hasLength(response) && RESPONSE_PATTERN.matcher(response).matches();
    }
}
