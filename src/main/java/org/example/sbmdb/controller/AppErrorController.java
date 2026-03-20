package org.example.sbmdb.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        int statusCode = status != null ? Integer.parseInt(status.toString()) : 500;
        String errorMessage = statusCode == 404
                ? "The page you're looking for doesn't exist."
                : message != null && !message.toString().isBlank()
                ? message.toString()
                : "An unexpected error occurred.";

        model.addAttribute("status", statusCode);
        model.addAttribute("message", errorMessage);
        return "error";
    }
}