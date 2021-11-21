package com.jun.springcloud.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

    public void run() {
        System.out.println("----- Regex Test Start -----");
        String rgxLB = "(?<![A-z|À-ÿ|-])(";
        String rgxLA = ")(?![A-z|À-ÿ|-])";

        StringBuilder stringBuilder = new StringBuilder("adfs".length());
        String rgx = stringBuilder.append(rgxLB).append("Urlaub").append(rgxLA).toString();
        String exText = "The fat cat ran down the street.\n" +
                "It üUrlaub bUrlaub urlaubt urlaub3 CCUrlaub -urlaub _urlaub !urlaub .urlaub   urlaub~  \n" +
                "urlaub::  was searching for a mouse to eat.\n" +
                "Urlaubá Urlaub- Urlaubß, Urlaub Urlaubä Urlauba! Urlaubü urlaub?!! " +
                "urlaub_ urlaub- urlaub. urlaub, ist toll als Urlaubszeit fat fat bat cat urlaub\n";
        Pattern pattern = Pattern.compile(rgx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(exText);
        String resultText = matcher.replaceAll("TEST");
        System.out.println(resultText);
        System.out.println("----- Regex Test End -----");
        // /\W/g : match anything that is not a letter (white space + special characters ! . , ?...)
    }

}
