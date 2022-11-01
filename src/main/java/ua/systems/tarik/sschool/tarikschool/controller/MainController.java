package ua.systems.tarik.sschool.tarikschool.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.systems.tarik.sschool.tarikschool.service.MailService;
import ua.systems.tarik.sschool.tarikschool.service.RegexService;
import ua.systems.tarik.sschool.tarikschool.service.TelegramService;

@Controller
@AllArgsConstructor
public class MainController {

    RegexService regexService;

    MailService mailService;

    TelegramService telegramService;

    @GetMapping("/")
    public String showIndex() {
        return "../static/index";
    }

    @PostMapping("/contact")
    public ResponseEntity<?> proceedContactFormAndShowIndex(@RequestParam String username,
                                                            @RequestParam("userphone") String userPhone,
                                                            @RequestParam("useremail") String userEmail,
                                                            @RequestParam String message) {


        if (regexService.isEmail(userEmail) && regexService.isPhone(userPhone)) {
            telegramService.sendFeedbackMessage(userEmail, userPhone, username, message);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/callback")
    public ResponseEntity<?> proceedCallBackFormAndShowIndex(@RequestParam String username,
                                                             @RequestParam("userphone") String userPhone) {

        if (regexService.isPhone(userPhone)) {
            telegramService.sendCallbackMessage(username, userPhone);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
