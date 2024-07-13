package com.spotify.ultils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class GenericsHelper {
    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS", Locale.US);
        GregorianCalendar calendar = new GregorianCalendar(TimeZone.getTimeZone("US/Central"));
        calendar.setTimeInMillis(System.currentTimeMillis());
        return sdf.format(calendar.getTime());
    }

    public static double getDiffMilliSec(double currentMilSecStart, double currentMilSecEnd) {
        double milSecDif = currentMilSecEnd - currentMilSecStart;
        double timeDif = milSecDif / 1000;
        return timeDif;
    }
    public static String ObjectToJsonValue(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String result = mapper.writeValueAsString(object);
        return result;
    }


}
