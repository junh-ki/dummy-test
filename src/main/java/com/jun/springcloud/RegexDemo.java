package com.jun.springcloud;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

    public void run() {
        //TODO: regex requirement would be:
        // no alphabets right before and after the match but you should allow
        // special characters like: . , ! ? etc
        // no alaph
        // "(^|\W)(Urlaub)(?![a-zA-Z])"

        System.out.println("----- Regex Test Start -----");
        //String rgx = "(Urlaub)+([ .,?!-])?";
        String rgx = "(?<=(^|\\W))(Urlaub)(?=[ .,?!-])";
        String exText = "The fat cat ran down the street.\n" +
                "It was searching for a mouse to eat.\n" +
                "Urlaub, Urlaub! Urlaub ist toll als Urlaubszeit fat fat bat cat";
        Pattern pattern = Pattern.compile(rgx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(exText);
        String resultText = matcher.replaceAll("TEST");
        System.out.println(resultText);
        System.out.println("----- Regex Test End -----");
        // /\W/g : match anything that is not a letter (white space + special characters ! . , ?...)
    }

}
