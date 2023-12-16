package com.example.colorbalancecalculator.utility;


import com.example.colorbalancecalculator.exception.InvalidDnaSequenceException;

public class Validation {

    protected static void checkInvalidDnaBase(String input) throws InvalidDnaSequenceException {
        if (!input.matches("^(?:[GTCA] )*[GTCA]$")) {
            throw new InvalidDnaSequenceException("Each DNA sequence should only contain \"G, T, C, A\" characters (case insensitive), separated by a space.</br> " +
                    "Lines should not contain any numbers.");
        }
    }

    protected static void checkInvalidDnaSequenceLength(int length, String line) throws InvalidDnaSequenceException {
        if (length != line.length()) {
            throw new InvalidDnaSequenceException("All sequences line must have the same length.");
        }
    }
}