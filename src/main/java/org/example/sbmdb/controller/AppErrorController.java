package org.example.sbmdb.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    private static final String ERROR_VIEW = "error";

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        int statusCode = resolveStatus(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        String errorMessage = resolveMessage(statusCode, request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        model.addAttribute("status", statusCode);
        model.addAttribute("message", errorMessage);
        return ERROR_VIEW;
    }

    private int resolveStatus(Object status) {
        return status != null ? Integer.parseInt(status.toString()) : 500;
    }

    private String resolveMessage(int statusCode, Object message) {
        if (statusCode == 404) return "The page you're looking for doesn't exist.";
        if (message != null && !message.toString().isBlank()) return message.toString();
        return "An unexpected error occurred.";
    }
}