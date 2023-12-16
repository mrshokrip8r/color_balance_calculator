package com.example.colorbalancecalculator.controller;

import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;
import com.example.colorbalancecalculator.service.CalculateColorBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Objects;

@Controller
public class DnaSequencesController {

    @Autowired
    private CalculateColorBalanceService calculateColorBalanceService;

    @GetMapping("/home")
    public String uploadFile() {
        return "uploadFile";
    }

    @PostMapping("/upload")
    public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, Model model) {
        ModelAndView modelAndView = new ModelAndView("uploadFile");

        if (!Objects.requireNonNull(file.getOriginalFilename()).isBlank()) {
            if (file.isEmpty()) {
                model.addAttribute("error", "The file is empty.");
            } else {
                try {
                    String output = calculateColorBalanceService.calculateColorBalance(file);
                    model.addAttribute("output", output);
                } catch (IOException e) {
                    model.addAttribute("error", "Please upload a valid text file.");
                } catch (InvalidDnaSequenceException e) {
                    model.addAttribute("error", "Please upload a valid text file.</br>" + e.getMessage());
                }
            }
        } else {
            model.addAttribute("error", "Please upload a text file.");
        }

        return modelAndView;
    }
}