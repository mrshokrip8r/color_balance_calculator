package com.example.colorbalancecalculator.service;

import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CalculateColorBalanceService {
    String calculateColorBalance(MultipartFile file) throws IOException, InvalidDnaSequenceException;
}
