package ua.systems.tarik.sschool.tarikschool.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.systems.tarik.sschool.tarikschool.service.MailService;

@Controller
@AllArgsConstructor
public class MainController {
    MailService mailService;

    @GetMapping("/")
    public String showIndex() {
        return "../static/index";
    }

    @PostMapping("/contact")
    public String proceedContactFormAndShowIndex(@RequestParam String username,
                                                 @RequestParam("userphone") String userPhone,
                                                 @RequestParam("useremail") String userEmail,
                                                 @RequestParam String message) {

        System.out.println("Username: " + username + "\n" +
                "UserPhone: " + userPhone + "\n" +
                "UserEmail: " + userEmail + "\n" +
                "Message: " + message
        );
        mailService.sendFeedbackMail(userEmail, userPhone, username, message);
        return "redirect:/";
    }

    @PostMapping("/callback")
    public String proceedCallBackFormAndShowIndex(@RequestParam String username,
                                                  @RequestParam("userphone") String userPhone) {

        System.out.println("Username: " + username + "\n" +
                "UserPhone: " + userPhone + "\n"
        );

        mailService.sendCallbackMail(username, userPhone);

        return "redirect:/";
    }
}
