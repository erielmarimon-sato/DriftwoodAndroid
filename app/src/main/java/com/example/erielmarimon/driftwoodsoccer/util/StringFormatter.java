package com.example.erielmarimon.driftwoodsoccer.util;

import java.util.Formatter;

/**
 * Created by Eriel.Marimon on 3/22/17.
 */

public class StringFormatter {
    public static String format(String format, Object... objects){
        Formatter formatter = new Formatter();
        String out = formatter.format(format, objects).toString();
        formatter.close();
        return out;
    }
}
