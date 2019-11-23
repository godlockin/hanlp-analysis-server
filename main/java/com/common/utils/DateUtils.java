package com.common.utils;

import com.common.constants.ResultEnum;
import com.exception.HanlpServiceException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.BiFunction;

public class DateUtils {

    public static final String DATE_YYYYMMDD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_YYYYMM = "yyyyMM";
    public static final String DEFAULT_DATE_FORMAT = DATE_YYYYMMDD_HHMMSS;

    public enum DATE_TYPE {
        YEAR("year")
        , MONTH("month")
        , WEEK("week")
        , DAY("day")
        , HOUR("hour")
        , MINUTE("minute")
        , SECOND("month")
        , MILLISECOND("millisecond")
        ;

        private String name;
        DATE_TYPE(String name) {
            this.name = name;
        }
    }

    private static final Map<DATE_TYPE, BiFunction<Date, Integer, Date>> functionMap = new HashMap() {{
        put(DATE_TYPE.YEAR, addYears());
        put(DATE_TYPE.MONTH, addMonths());
        put(DATE_TYPE.WEEK, addWeeks());
        put(DATE_TYPE.DAY, addDays());
        put(DATE_TYPE.HOUR, addHours());
        put(DATE_TYPE.MINUTE, addMinutes());
        put(DATE_TYPE.SECOND, addSeconds());
        put(DATE_TYPE.MILLISECOND, addMilliseconds());
    }};

    public static Date parseDate(String date) throws HanlpServiceException {
        return parseDate(date, DEFAULT_DATE_FORMAT);
    }

    public static Date parseDate(String date, String format) throws HanlpServiceException {
        try {
            return new SimpleDateFormat(Optional.ofNullable(format).orElse(DEFAULT_DATE_FORMAT)).parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new HanlpServiceException(ResultEnum.PARAMETER_CHECK);
        }
    }

    public static String formatDate(Date date) {
        return formatDate(date, DEFAULT_DATE_FORMAT);
    }

    public static String formatDate(Date date, String format) {
        return new SimpleDateFormat(Optional.ofNullable(format).orElse(DEFAULT_DATE_FORMAT))
                .format(Optional.ofNullable(date).orElse(new Date()));
    }

    public static Date getDateDiff(int num, DATE_TYPE timeUnit) {
        return getDateDiff(new Date(), num, timeUnit);
    }

    public static Date getDateDiff(Date base, int num, DATE_TYPE timeUnit) {
        return functionMap.getOrDefault(timeUnit, addDays())
                .apply(Optional.ofNullable(base).orElse(new Date()), num);
    }

    private static BiFunction<Date, Integer, Date> addYears() {
        return org.apache.commons.lang3.time.DateUtils::addYears;
    }

    private static BiFunction<Date, Integer, Date> addMonths() {
        return org.apache.commons.lang3.time.DateUtils::addMonths;
    }

    private static BiFunction<Date, Integer, Date> addWeeks() {
        return org.apache.commons.lang3.time.DateUtils::addWeeks;
    }

    private static BiFunction<Date, Integer, Date> addDays() {
        return org.apache.commons.lang3.time.DateUtils::addDays;
    }

    private static BiFunction<Date, Integer, Date> addHours() {
        return org.apache.commons.lang3.time.DateUtils::addHours;
    }

    private static BiFunction<Date, Integer, Date> addMinutes() {
        return org.apache.commons.lang3.time.DateUtils::addMinutes;
    }

    private static BiFunction<Date, Integer, Date> addSeconds() {
        return org.apache.commons.lang3.time.DateUtils::addSeconds;
    }

    private static BiFunction<Date, Integer, Date> addMilliseconds() {
        return org.apache.commons.lang3.time.DateUtils::addMilliseconds;
    }

    public static String getDateTime() {
        return formatDate(new Date());
    }

    public static Date getCleanDate() {
        return getCleanDate(new Date());
    }

    public static Date getCleanDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Optional.ofNullable(date).orElse(new Date()));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
