package com.dfst.logging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LogEntry {

    private String message;
    private Calendar date;
    private SimpleDateFormat dateFormat;

    public LogEntry(String message) {
        this.message = message;
        date = Calendar.getInstance();
        dateFormat = new SimpleDateFormat();
    }

    public LogEntry(String date, String message) throws ParseException {
        this.message = message;
        this.date.setTime(dateFormat.parse(date));
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString(){
        return "Time: [" + dateFormat.format(date.getTime())+ "] :: " + message;
    }
}
