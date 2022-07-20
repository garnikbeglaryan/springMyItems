package com.example.springmyitems.controller;


import com.example.springmyitems.entity.User;
import com.example.springmyitems.repository.ItemRepository;
import com.example.springmyitems.service.MailService;
import com.example.springmyitems.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/addUser")
    public String addUser() {
        return "saveUser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute User user, ModelMap map, Locale locale) throws MessagingException {

        List<String> errorMsgs = new ArrayList<>();
        if (user.getName() == null || user.getName().equals("")) {
            errorMsgs.add("name is required");
        }
        if (user.getSurname() == null || user.getSurname().equals("")) {
            errorMsgs.add("surname is required");
        }
        if (user.getEmail() == null || user.getEmail().equals("")) {
            errorMsgs.add("email is required");
        }
        if (!errorMsgs.isEmpty()) {
            map.addAttribute("errors", errorMsgs);
            return "saveUser";
        }
        user.setActive(false);
        user.setToken(UUID.randomUUID().toString());
        user.setTokenCreatedDate(LocalDateTime.now());

        userService.create(user);
        mailService.sendHtmlEmail(user.getEmail(),
                "Welcome " + user.getSurname(),
                user, " http://localhost:8080/user/activate?token=" + user.getToken(), "verifyTemplate", locale);
        return "redirect:/";
    }

    @GetMapping("/user/activate")
    public String activateUser(ModelMap map, @RequestParam String token) {
//        Optional<User> user = userService.findByToken(UUID.fromString(token));
        Optional<User> user = userService.findByToken(token);
        if (!user.isPresent()) {
            map.addAttribute("message", "User does not exists");
            return "activateUser";
        }
        User userFromDb = user.get();
        if (userFromDb.isActive()) {
            map.addAttribute("message", "User already active !");
            return "activateUser";
        }
        userFromDb.setActive(true);
        userFromDb.setToken(null);
        userFromDb.setTokenCreatedDate(null);
        userService.save(userFromDb);
        map.addAttribute("message", "User activated, please login!");
        return "activateUser";
    }

    @GetMapping("/editUser/{id}")
    public String editUserPage(ModelMap map, @PathVariable("id") int id) {
        map.addAttribute("user", userService.findById(id));
        return "saveUser";
    }
}

