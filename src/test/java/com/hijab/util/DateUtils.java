package com.hijab.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class DateUtils {
    private static int finalIndex;

    public static boolean parseAndCompareUpToMinutes(String date1, String date2) {
        return LocalDateTime
                    .parse(String.join("T", date1.split(" ")))
                    .truncatedTo(ChronoUnit.MINUTES)
                    .isEqual(LocalDateTime
                        .parse(String.join("T", date2.split(" ")))
                        .truncatedTo(ChronoUnit.MINUTES));
    }

    public static String generateRandomDate(String min, String max) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        long minDate = LocalDateTime.parse(min).toEpochSecond(ZoneOffset.UTC);
        long maxDate = LocalDateTime.parse(max).toEpochSecond(ZoneOffset.UTC);
        long randomDate = ThreadLocalRandom.current().nextLong(minDate, maxDate);

        return LocalDateTime.ofEpochSecond(randomDate, 0, ZoneOffset.UTC).format(formatter);
    }

    public static List<Map<String, String>> searchClosestBoards(List<Map<String, String>> givenBoards, String key) {
        List<String> dateList = givenBoards.stream()
                                        .map(board -> board.get("createdAt").split(" ")[0])
                                        .distinct()
                                        .sorted(Comparator.reverseOrder())
                                        .toList();
        String[] dateArray = dateList.toArray(new String[0]);
        int result = Arrays.binarySearch(dateArray, key, Comparator.reverseOrder());
        
        if (result < 0) {
            int insertionPoint = -(result + 1);
            if (insertionPoint == 0) {
                finalIndex = 0;
            } else if(insertionPoint == dateList.size()) {
                finalIndex = dateList.size() - 1;
            } else {
                finalIndex = insertionPoint - 1;
            }
        } else {
            finalIndex = result;
        }

        return givenBoards.stream()
                        .filter(board -> board.get("createdAt").contains(dateList.get(finalIndex)))
                        .toList();
    }

    public static List<Map<String, String>> searchClosestComments(List<Map<String, String>> givenComments, String key) {
        List<String> dateList = givenComments.stream()
                                        .map(comment -> comment.get("createdAt").split(" ")[0])
                                        .distinct()
                                        .sorted()
                                        .toList();
        String[] dateArray = dateList.toArray(new String[0]);
        int result = Arrays.binarySearch(dateArray, key);
        
        if (result < 0) {
            int insertionPoint = -(result + 1);
            if (insertionPoint == 0) {
                finalIndex = 0;
            } else if(insertionPoint == dateList.size()) {
                finalIndex = dateList.size() - 1;
            } else {
                finalIndex = insertionPoint - 1;
            }
        } else {
            finalIndex = result;
        }

        return givenComments.stream()
                        .filter(comment -> comment.get("createdAt").contains(dateList.get(finalIndex)))
                        .toList();
    }
}
