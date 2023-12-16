package com.example.colorbalancecalculator.utility;

import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    /**
     * Creates a matrix representing DNA sequences from the provided list of string arrays.
     *
     * @param inputLines The list of string arrays containing DNA sequences.
     * @return A matrix where each row represents a DNA sequence.
     */
    public static String[][] createDnaSequences(List<String[]> inputLines) {
        return inputLines.toArray(String[][]::new);
    }

    /**
     * Reads the content of the input file, validates each DNA sequence, and returns a list of string arrays.
     *
     * @param file The input file containing DNA sequences.
     * @return A list of string arrays, where each array represents a valid DNA sequence.
     * @throws IOException                 If there's an issue reading the input file.
     * @throws InvalidDnaSequenceException If the content of the file does not match to the expected DNA sequence format.
     */
    public static List<String[]> readInputFile(MultipartFile file) throws IOException, InvalidDnaSequenceException {
        List<String[]> content = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

        String line = reader.readLine();
        int length = line.trim().length();
        do {
            Validation.checkInvalidDnaBase(line.toUpperCase().trim());
            Validation.checkInvalidDnaSequenceLength(length, line.trim());

            String[] lineArray = line.toUpperCase().trim().split(" ");

            content.add(lineArray);
        } while ((line = reader.readLine()) != null);
        reader.close();
        return content;
    }

    public static String[] getColumn(String[][] matrix, int columnIndex) {
        return Arrays.stream(matrix)
                .map(row -> row[columnIndex])
                .toArray(String[]::new);
    }
}
