package com.jun.springcloud;

import java.util.Arrays;
import java.util.List;

public class StringBuilderDemo {

    public void run() {
        System.out.println("----- StringBuilder Demo Start -----");
        List<String> trees = Arrays.asList("oak", "pine", "fir", "ash", "birch", "elm", "yew");
        System.out.println(listToString(trees));
        System.out.println(listWithSeparator(trees, "-"));
        if (isPalindrome("level")) {
            System.out.println("\"level\" is palindrome.");
        } else {
            System.out.println("\"level\" is not palindrome.");
        }
        if (isPalindrome("bus")) {
            System.out.println("\"bus\" is palindrome.");
        } else {
            System.out.println("\"bus\" is not palindrome.");
        }
        if (isPalindrome("level")) {
            System.out.println("\"level\" is palindrome.");
        } else {
            System.out.println("\"level\" is not palindrome.");
        }
        System.out.println("----- StringBuilder Demo End -----");
    }

    private String listToString(List<String> list) {
        StringBuilder stringBuilder = new StringBuilder(32);

        for (String string : list) {
            stringBuilder.append(string).append(" ");
        }

        return stringBuilder.toString();
    }

    private String listWithSeparator(List<String> list, String separator) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean first = true;

        for (String string : list) {
            if (first) {
                first = false;
            } else {
                stringBuilder.append(separator);
            }
            stringBuilder.append(string);
        }

        return stringBuilder.toString();
    }

    private boolean isPalindrome(String string) {
        StringBuilder stringBuilder = new StringBuilder(string);
        String reversedString = stringBuilder.reverse().toString();

        return reversedString.equalsIgnoreCase(string);
    }

}
