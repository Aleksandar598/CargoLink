package course.spring.cargolink.controller.web.impl;

import course.spring.cargolink.controller.web.AuthController;
import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthControllerImpl implements AuthController {

    @Override
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("credentials", new UserCredentialsDTO("", ""));
        return "login";
    }

    @Override
    @PostMapping("/login")
    public String processLogin(UserCredentialsDTO credentials, BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {

        return "dashboard";
    }

    @Override
    public String showRegistrationForm(Model model) {
        return "";
    }

    @Override
    public String processRegistration(UserRegistrationDTO registration, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        return "";
    }
}
