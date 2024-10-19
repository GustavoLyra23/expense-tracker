package org.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternUtil {

    public static Matcher regex(String command, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(command);
    }


}
