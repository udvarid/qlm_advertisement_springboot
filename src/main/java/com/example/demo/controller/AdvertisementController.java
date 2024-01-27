package com.example.demo.controller;

import com.example.demo.model.AdvertisementImpl;
import com.example.demo.service.AdvertisementManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdvertisementController {
    private final AdvertisementManager manager;

    public AdvertisementController(AdvertisementManager manager) {
        this.manager = manager;
    }

    @GetMapping("/")
    public String viewHomePage(Model model) {
        model.addAttribute("advertisements", manager.getStatistics());
        model.addAttribute("day", manager.getDay());
        model.addAttribute("period", manager.getPeriod());
        return "index";
    }

    @GetMapping("/addNew")
    public String addnew(Model model) {
        AdvertisementImpl advertisement = new AdvertisementImpl();
        model.addAttribute("advertisement", advertisement);
        return "addNew";
    }

    @PostMapping("/save")
    public String saveAdvertisement(@ModelAttribute("advertisement") AdvertisementImpl advertisement) {
        manager.registerNewAdvertisement(advertisement);
        return "redirect:/";
    }

    @GetMapping("/nextDay")
    public String shiftDay(Model model) {
        manager.stepDay();
        return "redirect:/";
    }

    @GetMapping("/nextShow")
    public String askNextShow(Model model) {
        manager.askNewAdvertisement();
        return "redirect:/";
    }

    @GetMapping("/restart")
    public String restart(Model model) {
        manager.setToDefault();
        return "redirect:/";
    }
    @GetMapping("/restartFull")
    public String restartFull(Model model) {
        manager.setToDefaultWithoutAdvertisement();
        return "redirect:/";
    }

}
