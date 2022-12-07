package ua.systems.tarik.sschool.tarikschool.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import ua.systems.tarik.sschool.tarikschool.service.CaptchaService;
import ua.systems.tarik.sschool.tarikschool.service.MailService;
import ua.systems.tarik.sschool.tarikschool.service.RegexService;
import ua.systems.tarik.sschool.tarikschool.service.TelegramService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class MainController {

    RegexService regexService;

    MailService mailService;

    TelegramService telegramService;

    CaptchaService captchaService;

    @GetMapping("/")
    public String showIndex(@RequestHeader(value = "User-Agent") String userAgent, HttpServletRequest request) {
        String IP = request.getRemoteAddr();
        if (!Objects.equals(IP, "127.0.0.1"))
            telegramService.notifyAboutUser(IP, userAgent);

        return "index";
    }

    @PostMapping("/order")
    public ResponseEntity<?> proceedOrderFormAndShowIndex(@RequestParam String username,
                                                          @RequestParam("userphone") String userPhone,
                                                          @RequestParam("usermail") String userEmail,
                                                          @RequestParam String message,
                                                          @RequestParam("g-recaptcha-response") String recaptcha,
                                                          @RequestHeader(value = "User-Agent") String userAgent,
                                                          HttpServletRequest request) {

        String IP = request.getRemoteAddr();
        captchaService.processResponse(recaptcha, IP);

        if (regexService.isEmail(userEmail) && regexService.isPhone(userPhone)) {
            telegramService.sendFeedbackMessage(userEmail, userPhone, username, message, IP, userAgent);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/callback")
    public ResponseEntity<?> proceedCallBackFormAndShowIndex(@RequestParam String username,
                                                             @RequestParam("userphone") String userPhone,
                                                             @RequestParam("g-recaptcha-response") String recaptcha,
                                                             @RequestHeader(value = "User-Agent") String userAgent,
                                                             HttpServletRequest request) {
        String IP = request.getRemoteAddr();
        captchaService.processResponse(recaptcha, IP);

        if (regexService.isPhone(userPhone)) {
            telegramService.sendCallbackMessage(username, userPhone, IP, userAgent);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
