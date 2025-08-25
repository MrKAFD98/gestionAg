package com.agriapp.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UiDates {
    private static final SimpleDateFormat F = new SimpleDateFormat("yyyy-MM-dd");
    public static Date parse(String s) {
        if (s == null || s.isBlank()) return null;
        try { return F.parse(s); } catch (ParseException e) { return null; }
    }
    public static String fmt(Date d) { return d == null ? "" : F.format(d); }
}