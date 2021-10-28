package com.jun.springcloud;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class StringTest {

    private Map<String, String> map;
    private final static String INPUT_STRING =
            "Holiday Rentals & Lettings - Holiday rental - holiday holidays - traveller travellers - " +
            "Log Cabins & Lodges log cabins log cabin - Beach Houses beach houses beach house";
    private final static String EXPECTED_STRING =
            "Vacation rentals - Vacation rental - vacation vacations - traveler travelers - " +
            "Cabins Cabins cabin - Beach Rentals beach rentals beach rental";

    public void processLanguageDiscrepancies() {
        this.initializeMap();
        String updatedContent = updateContent(INPUT_STRING, this.map);

        System.out.println("-----------------");
        System.out.println("Original String: ");
        System.out.println(INPUT_STRING);
        System.out.println();
        System.out.println("-----------------");
        System.out.println("Expected String: ");
        System.out.println(EXPECTED_STRING);
        System.out.println();
        System.out.println("-----------------");
        System.out.println("Modified String: ");
        System.out.println(updatedContent);
        System.out.println();

    }

    public void initializeMap() {
        this.map = new HashMap<>();
        this.map.put("Holiday Rentals & Lettings", "Vacation rentals");
        this.map.put("Holiday rental", "Vacation rental");
        this.map.put("holiday", "vacation");
        this.map.put("holidays", "vacations");
        this.map.put("traveller", "traveler");
        this.map.put("travellers", "travelers");
        this.map.put("Log Cabins & Lodges", "Cabins");
        this.map.put("log cabins", "Cabins");
        this.map.put("log cabin", "cabin");
        this.map.put("Beach Houses", "Beach Rentals");
        this.map.put("beach houses", "beach rentals");
        this.map.put("beach house", "beach rental");
        this.map.put("Beach", "Bitch");
        this.map.put("beach", "bitch");
    }

    private String updateContent(String content, Map<String, String> map) {
        String[] keywordArray = map.keySet().toArray(new String[]{});
        if (keywordArray.length != map.size()) {
            return null;
        }

        /* Step 1: bubble sort the keyword list (words from the master domain) by the length of keyword string */
        String[] searchList = sortedStringArrayByLength(keywordArray);
        Map<Integer, String> replacementMap = new HashMap<>();
        int lastIndexToReplace = -1;

        /* Step 2: replace each key word to a regularly expressed index number */
        for (int i = 0; i < searchList.length; i++) {
            String keyword = keywordArray[i];
            replacementMap.put(i, map.get(keyword));
            String prev = content;
            content = StringUtils.replace(content, keyword, "{" + i + "}");
            if (StringUtils.compare(prev, content) != 0) {
                lastIndexToReplace = i;
            }
        }

        if (lastIndexToReplace < 0) {
            return content;
        }

        /* Step 3: replace the regularly expressed index numbers to the string with the same index number in the replacement list */
        for (int i = 0; i <= lastIndexToReplace; i++) {
            String replacement = replacementMap.get(i);
            content = StringUtils.replace(content, "{" + i + "}", replacement);
        }

        return content;
    }

    private String[] sortedStringArrayByLength(String[] strArray) {
        for (int i = 0; i < strArray.length - 1; i++) {
            boolean isSorted = true;
            for (int j = i + 1; j < strArray.length; j++) {
                if (strArray[i].length() < strArray[j].length()) {
                    String temp = strArray[i];
                    strArray[i] = strArray[j];
                    strArray[j] = temp;
                    isSorted = false;
                }
            }
            if (isSorted) {
                break;
            }
        }
        return strArray;
    }

}
