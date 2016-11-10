package com.moez.QKSMS.common.utils;


import com.moez.QKSMS.common.dbhelper.FilterDbHelper;
import com.moez.QKSMS.data.SimpleMessage;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangqian on 2016/10/29.
 * garbage tool class
 */
public class GarbageUtils {
    public static final String TAG = "GARBAGE_UTILS";

    public static final int CONTENT_PLAIN = 1;
    public static final int CONTENT_SIMPLE = 2;
    public static final int CONTENT_REGEX = 3;

    public static final char[] REGEX_CHARS = new char[] {
        '\\', '^', '$', '|', '?', '.', '+', '*', '(', ')', '[', ']', '{', '}', '<', '>'
    };

    public static boolean isGarbageMessage(FilterDbHelper db, SimpleMessage msg) {
        return !hasMatch(db.getWhiteList(), msg.getAddress()) &&
                (hasMatch(db.getBlackList(), msg.getAddress()) || hasMatch(db.getKeywordList(), msg.getBody()));
    }

    public static boolean hasMatch(ArrayList<String> needles, String subject) {
        for (String needle : needles) {
            Pattern pattern = Pattern.compile(needle);
            Matcher matcher = pattern.matcher(subject);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static String complieFilter(String filter, int contentType) {
        String result = filter;
        switch (contentType) {
            case CONTENT_PLAIN:
                for (char regex : REGEX_CHARS) {
                    result = result.replace("" + regex, "\\" + regex);
                }
                break;
            case CONTENT_SIMPLE:
                for (char regex : REGEX_CHARS) {
                    if (regex != '*') {
                        result = result.replace("" + regex, "\\" + regex);
                    }
                }
                result = result.replace("*", ".*");
                break;
            case CONTENT_REGEX:
            default:
        }
        return result;
    }
}
