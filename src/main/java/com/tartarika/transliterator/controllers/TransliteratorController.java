package com.tartarika.transliterator.controllers;

import com.tartarika.transliterator.service.GeneralAlphabetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TransliteratorController.
 *
 * @author Aydar_Safiullin
 */
@Controller
@RequestMapping("/transliterator")
public class TransliteratorController {
    private String source;
    private String converted;
    private GeneralAlphabetService alphabetService;

    @Autowired
    public TransliteratorController(GeneralAlphabetService alphabetService) {
        this.alphabetService = alphabetService;
    }

    @GetMapping
    public String getMainPage(Model model) {
        if (!StringUtils.isAllEmpty(source, converted)) {
            model.addAttribute("source", source);
            model.addAttribute("converted", converted);
        }
        return "main";
    }

    @PostMapping("/changed")
    public String convertToLatinLetters(Model model, @RequestParam String sourceText) {
        String latinText = alphabetService.convertTextToLatinWriting(sourceText);
        source = sourceText;
        converted = latinText;
        return "redirect:/transliterator";
    }
}
