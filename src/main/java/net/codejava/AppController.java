package net.codejava;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegistration(User user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        repo.save(user);

        return "register_success";
    }

    // ✅ ADD THIS LOGIN MAPPING
    @GetMapping("/login")
    public String viewLoginPage() {
        return "login";
    }

	/*
	 * @GetMapping("/list_users") public String viewUsersList(Model model) {
	 * List<User> listUsers = repo.findAll(); model.addAttribute("listUsers",
	 * listUsers); return "users"; }
	 */
    @GetMapping("/list_users")
    public String viewUsersList(Model model, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String fullName = userDetails.getFullName();

        List<User> listUsers = repo.findAll();

        model.addAttribute("fullName", fullName);
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

}
