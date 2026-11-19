package course.spring.cargolink.controller.web;

import course.spring.cargolink.dto.user.UserCredentialsDTO;
import course.spring.cargolink.dto.user.UserRegistrationDTO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface AuthController {

    String showLoginForm(Model model);

    String processLogin(
            @Valid @ModelAttribute("credentials") UserCredentialsDTO credentials,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpSession session);

    String showRegistrationForm(Model model);

    String processRegistration(
            @Valid @ModelAttribute("user") UserRegistrationDTO registration,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes);
}
