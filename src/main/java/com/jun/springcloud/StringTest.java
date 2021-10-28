package com.jun.springcloud;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;



public class StringTest {

    private Map<String, String> map;
    private final static String JSON_PATH = "./src/main/java/com/jun/springcloud/discrepancies.json";
    private final static String INPUT_STRING =
            "Holiday Rentals & Lettings - Holiday rental - holiday holidays - traveller travellers - " +
            "Log Cabins & Lodges Log cabins log cabin - Beach Houses beach houses beach house";
    private final static String EXPECTED_STRING =
            "Vacation rentals - Vacation rental - vacation vacations - traveler travelers - " +
            "Cabins Cabins cabin - Beach Rentals beach rentals beach rental";

    public void processLanguageDiscrepancies() {
        this.initializeMap(2l);

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

    public void initializeMap(Long domainId) {
        try {
            LinkedHashMap<String, Object> jsonMap = new JSONParser(new FileReader(JSON_PATH)).parseObject();
            if (jsonMap.containsKey(domainId.toString())) {
                this.map = (HashMap<String, String>) jsonMap.get(domainId.toString());
            } else {
                this.map = null;
            }
        } catch (Exception e) {
            if (e instanceof FileNotFoundException) {
                System.out.println(e.getMessage());
            }
            if (e instanceof ParseException) {
                System.out.println(e.getMessage());
            }
            this.map = null;
        }
    }

    private String updateContent(String content, Map<String, String> map) {
        String[] keywordArray = map.keySet().toArray(new String[]{});
        if (keywordArray.length != map.size()) {
            return null;
        }

        /* Step 1: sort the keyword list (words from the master domain) by the length of keyword string */
        Arrays.sort(keywordArray, Comparator.comparingInt(String::length).reversed());
        Map<Integer, String> replacementMap = new HashMap<>();
        int lastIndexToReplace = -1;

        /* Step 2: replace each key word to a regularly expressed index number */
        for (int i = 0; i < keywordArray.length; i++) {
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

}
