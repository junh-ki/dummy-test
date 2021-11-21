package com.jun.springcloud.string;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;
import org.apache.tomcat.util.json.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringTest {

    private Map<String, String> map;
    private final static String JSON_PATH = "./src/main/java/com/jun/springcloud/discrepancies.json";
    private final static String UK_US_INPUT_STRING =
            "Holiday Rentals & Lettings - Holiday rental - holiday holidays - traveller travellers - " +
            "Log Cabins & Lodges Log cabins log cabin - Beach Houses beach houses beach house";
    private final static String UK_US_EXPECTED_STRING =
            "Vacation rentals - Vacation rental - vacation vacations - traveler travelers - " +
            "Cabins Cabins cabin - Beach Rentals beach rentals beach rental";
    private final static String DE_CH_INPUT_STRING =
            "UrlaubstagUrlaubstageUrlaubUrlaubszeitUrlaubsortUrlaubsregionUrlaubsgästeUrlaubszielUrlaubsfotoUrlaubsdestinationUrlaubsplanungßUrlauberUrlaubsparadies";
    private final static String DE_CH_EXPECTED_STRING =
            "FerientagFerientageFerienFerienzeitFerienortFerienregionFeriengästeFerienzielFerienfotoFeriendestinationFerienplanungssReisendeFerienparadies";

    public void processLanguageDiscrepancies() {
        this.initializeMap(342l);

        String updatedContent = updateContent(DE_CH_INPUT_STRING, this.map);

        System.out.println("-----------------");
        System.out.println("Original String: ");
        System.out.println(DE_CH_INPUT_STRING);
        System.out.println();
        System.out.println("-----------------");
        System.out.println("Expected String: ");
        System.out.println(DE_CH_EXPECTED_STRING);
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
            System.out.println(e.getMessage());
            this.map = null;
        }
    }

    private String updateContent(String content, Map<String, String> discrepancies) {
        if (discrepancies == null || discrepancies.size() == 0) {
            return null;
        }

        /* Step 1: gather all possible cases of keys in a map and sort the key set by length */
        Map<String, String> discrepancyMap = getAllPossibleCasesOfPairs(discrepancies);
        String[] keywordArray = new ArrayList<>(discrepancyMap.keySet()).toArray(new String[0]);
        Arrays.sort(keywordArray, Comparator.comparingInt(String::length).reversed());

        /* Step 2: replace each key word to a regularly expressed index number */
        Map<Integer, String> replacementMap = new HashMap<>();
        int lastIndexToReplace = -1;
        for (int i = 0; i < keywordArray.length; i++) {
            String keyword = keywordArray[i];
            replacementMap.put(i, discrepancyMap.get(keyword));
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

    private Map<String, String> getAllPossibleCasesOfPairs(Map<String, String> discrepancies) {
        List<String> searchList = new ArrayList<>();
        List<String> replacementList = new ArrayList<>();
        for (Map.Entry<String, String> entry : discrepancies.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (!searchList.contains(key)) {
                searchList.add(key);
                replacementList.add(value);
            }
            /* Lower-case */
            String lowerKey = key.toLowerCase();
            String lowerValue = value.toLowerCase();
            if (!searchList.contains(lowerKey)) {
                searchList.add(lowerKey);
                replacementList.add(value.toLowerCase());
            }
            /* Upper-case */
            String upperKey = key.toUpperCase();
            if (!searchList.contains(upperKey)) {
                searchList.add(upperKey);
                replacementList.add(value.toUpperCase());
            }
            /* Camel-case*/
            String camelKey = CaseUtils.toCamelCase(key, true);
            if (!searchList.contains(camelKey)) {
                searchList.add(camelKey);
                replacementList.add(CaseUtils.toCamelCase(value, true));
            }
            /* First character capital */
            String firstUpperKey = lowerKey.substring(0, 1).toUpperCase() + lowerKey.substring(1);
            if (!searchList.contains(firstUpperKey)) {
                searchList.add(firstUpperKey);
                replacementList.add(lowerValue.substring(0, 1).toUpperCase() + lowerValue.substring(1));
            }
        }
        return zipToMap(searchList, replacementList);
    }

    private Map<String, String> zipToMap(List<String> keys, List<String> values) {
        return IntStream.range(0, keys.size()).boxed().collect(Collectors.toMap(keys::get, values::get));
    }

}
