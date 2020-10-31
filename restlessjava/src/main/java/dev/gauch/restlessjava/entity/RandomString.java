package dev.gauch.restlessjava.entity;

import java.util.Random;

public class RandomString {

    private static final String LETTERS_NUMBERS_STRING = "abcdefghijklmnopqrstuvxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private RandomString() {}

    public static String getWithLettersAndNumbers(long n) {
        StringBuilder generatedString = new StringBuilder();

        for (int i = 0; i < n; i++) {
            int index = new Random().nextInt(LETTERS_NUMBERS_STRING.length());
            generatedString.append(LETTERS_NUMBERS_STRING.charAt(index));
        }

        return generatedString.toString();
    }
}