package com.taskmanager.controller;

import com.taskmanager.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/historial")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping
    public String showHistory(@RequestParam(required = false) String filter, Model model) {
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("history", historyService.getHistoryByEntityType(filter.toUpperCase()));
            model.addAttribute("currentFilter", filter);
        } else {
            model.addAttribute("history", historyService.getRecentHistory());
        }
        return "historial";
    }
}
