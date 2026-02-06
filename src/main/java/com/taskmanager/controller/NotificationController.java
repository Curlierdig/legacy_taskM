package com.taskmanager.controller;

import com.taskmanager.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/notificaciones")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping
    public String showNotifications(Authentication authentication, Model model) {
        String userId = authentication.getName();
        model.addAttribute("notifications", notificationService.getNotificationsByUserId(userId));
        model.addAttribute("unreadCount", notificationService.getUnreadCount(userId));
        return "notificaciones";
    }

    @PostMapping("/leer/{id}")
    public String markAsRead(@PathVariable String id, RedirectAttributes redirectAttributes) {
        notificationService.markAsRead(id);
        return "redirect:/notificaciones";
    }

    @PostMapping("/leer-todas")
    public String markAllAsRead(Authentication authentication, RedirectAttributes redirectAttributes) {
        notificationService.markAllAsRead(authentication.getName());
        redirectAttributes.addFlashAttribute("success", "Todas las notificaciones marcadas como leídas");
        return "redirect:/notificaciones";
    }

    @PostMapping("/eliminar/{id}")
    public String deleteNotification(@PathVariable String id, RedirectAttributes redirectAttributes) {
        notificationService.delete(id);
        redirectAttributes.addFlashAttribute("success", "Notificación eliminada");
        return "redirect:/notificaciones";
    }
}
