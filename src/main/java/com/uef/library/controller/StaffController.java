package com.uef.library.controller;

import com.uef.library.model.User;
import com.uef.library.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class StaffController {

    private final UserService userService;

    @GetMapping("/home")
    @PreAuthorize("hasAnyRole('STAFF', 'ADMIN')")
    public String homePage(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        User user = userService.findByUsername(username).orElse(null);

        String fullName = "bạn";
        if (user != null && user.getUserDetail() != null) {
            String fetchedName = user.getUserDetail().getFullName();
            if (fetchedName != null && !fetchedName.equalsIgnoreCase("Chưa cập nhật")) {
                fullName = fetchedName;
            }
        }

        model.addAttribute("fullName", fullName);
        return "staff/index";
    }
}
