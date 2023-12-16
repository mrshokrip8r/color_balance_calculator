package com.example.colorbalancecalculator;

import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;
import com.example.colorbalancecalculator.service.CalculateColorBalanceService;
import com.example.colorbalancecalculator.utility.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ColorBalanceCalculatorApplicationTests {
    @Autowired
    CalculateColorBalanceService calculateColorBalanceService;

    @Test
    void testReadInputFileSuccess() {
        MultipartFile testMultipartFile;
        List<String[]> testInputFile;
        try {
            testMultipartFile = new MockMultipartFile("testFile1.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile1.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            testInputFile = Util.readInputFile(testMultipartFile);
        } catch (IOException | InvalidDnaSequenceException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("[C, A, G, T]", Arrays.toString(testInputFile.get(0)));
        Assertions.assertEquals("[T, C, A, C]", Arrays.toString(testInputFile.get(1)));
    }

    @Test
    void testReadInputFileInvalidDnaSequencesContainNumber() {
        InvalidDnaSequenceException exception = Assertions.assertThrows(InvalidDnaSequenceException.class, () -> {
            MultipartFile testMultipartFile = new MockMultipartFile("testFile2.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile2.txt")));

            Util.readInputFile(testMultipartFile);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Each DNA sequence should only contain \"G, T, C, A\" characters (case insensitive), separated by a space." +
                "</br> Lines should not contains any numbers.", exception.getMessage());
    }

    @Test
    void testReadInputFileInvalidDnaSequencesWithoutSpace() {
        InvalidDnaSequenceException exception = Assertions.assertThrows(InvalidDnaSequenceException.class, () -> {
            MultipartFile testMultipartFile = new MockMultipartFile("testFile3.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile3.txt")));

            Util.readInputFile(testMultipartFile);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("Each DNA sequence should only contain \"G, T, C, A\" characters (case insensitive), separated by a space." +
                "</br> Lines should not contains any numbers.", exception.getMessage());
    }

    @Test
    void testReadInputFileInvalidDnaSequencesNotSameLength() {
        InvalidDnaSequenceException exception = Assertions.assertThrows(InvalidDnaSequenceException.class, () -> {
            MultipartFile testMultipartFile = new MockMultipartFile("testFile4.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile4.txt")));

            Util.readInputFile(testMultipartFile);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals("All sequences line must have the same length.", exception.getMessage());
    }

    @Test
    void testCreateDnaSequenceSuccess() {
        MultipartFile testMultipartFile;
        try {
            testMultipartFile = new MockMultipartFile("testFile1.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile1.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[][] testDnaSequence;
        try {
            List<String[]> testInputLines = Util.readInputFile(testMultipartFile);
            testDnaSequence = Util.createDnaSequences(testInputLines);
        } catch (IOException | InvalidDnaSequenceException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("[C, A, G, T]", Arrays.toString(testDnaSequence[0]));
        Assertions.assertEquals("[T, C, A, C]", Arrays.toString(testDnaSequence[1]));
    }

    @Test
    void testCalculateColorBalanceService() {
        MultipartFile testMultipartFile;
        try {
            testMultipartFile = new MockMultipartFile("testFile5.xlsx",
                    new FileInputStream(new File("src/test/resources/testFile5.txt")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String testOutput;
        try {
            testOutput = calculateColorBalanceService.calculateColorBalance(testMultipartFile);
        } catch (IOException | InvalidDnaSequenceException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertEquals("""
                red / green
                  2 / 1
                  0 / 3
                  1 / 4
                  4 / 1
                  4 / 3
                """, testOutput);
    }
}