package com.example.OnlineExamination.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // Secured dashboard page
    }

    @GetMapping("/startExam")
    public String startExamPage() {
        return "questions"; // Load the questions page
    }

    @PostMapping("/submitExam")
    public String submitExam(@RequestParam("answers") String answersJson, Model model) {
        String[] correctAnswers = {
            "Central Processing Unit",
            "Paris",
            "Mars",
            "4",
            "James Gosling"
        };

        // Convert the received JSON to an array of user answers
        String[] userAnswers = answersJson.replace("[", "").replace("]", "").replace("\"", "").split(",");

        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (i < userAnswers.length && correctAnswers[i].trim().equalsIgnoreCase(userAnswers[i].trim())) {
                score++;
            }
        }

        model.addAttribute("totalMarks", correctAnswers.length);
        model.addAttribute("yourMarks", score);
        return "result"; // Redirect to the result page
    }
}
