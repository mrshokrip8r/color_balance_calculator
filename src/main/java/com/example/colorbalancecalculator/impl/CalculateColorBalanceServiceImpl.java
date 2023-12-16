package com.example.colorbalancecalculator.impl;

import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;
import com.example.colorbalancecalculator.service.CalculateColorBalanceService;
import com.example.colorbalancecalculator.utility.Util;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class CalculateColorBalanceServiceImpl implements CalculateColorBalanceService {

    public static final Map<String, Integer[]> LASER_DNA_BASES_TABLE = Map.of(
            "G", new Integer[]{0, 0},
            "T", new Integer[]{0, 1},
            "C", new Integer[]{1, 0},
            "A", new Integer[]{1, 1}
    );

    @Override
    public String calculateColorBalance(MultipartFile file) throws IOException, InvalidDnaSequenceException {
        List<String[]> inputLines = Util.readInputFile(file);
        String[][] dnaSequences = Util.createDnaSequences(inputLines);
        return calculateColorBalance(dnaSequences);
    }

    /**
     * Calculates the color balance for each cycle in the given DNA sequences.
     * The color balance is represented as the sum of red and green values for each cycle.
     *
     * @param dnaSequences The matrix containing DNA sequences.
     * @return A formatted string representing the sum of red and green values for each cycle.
     */
    private String calculateColorBalance(String[][] dnaSequences) {
        StringBuilder output = new StringBuilder("red / green\n");
        for (int i = 0; i < dnaSequences[0].length; i++) {
            String[] column = Util.getColumn(dnaSequences, i);
            int sumRed = 0;
            int sumGreen = 0;
            for (String s : column) {
                sumRed += LASER_DNA_BASES_TABLE.get(s)[0];
                sumGreen += LASER_DNA_BASES_TABLE.get(s)[1];

            }
            output.append("  ")
                    .append(sumRed)
                    .append(" / ")
                    .append(sumGreen)
                    .append("\n");

        }
        return output.toString();
    }
}