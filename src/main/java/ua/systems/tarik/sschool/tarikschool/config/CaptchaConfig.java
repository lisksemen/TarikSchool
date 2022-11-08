package ua.systems.tarik.sschool.tarikschool.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class CaptchaConfig {
    private String site;
    private String secret;

}
