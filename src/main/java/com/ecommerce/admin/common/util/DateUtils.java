package com.ecommerce.admin.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期时间工具类
 * 提供日期格式化、解析、转换、比较等常用时间操作方法
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    /**
     * 常用日期时间格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 时间格式：HH:mm:ss
     */
    public static final String TIME_FORMAT = "HH:mm:ss";

    /**
     * 常用日期时间格式化器
     */
    public static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    /**
     * 日期格式化器
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_FORMAT);

    /**
     * 时间格式化器
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT);

    /**
     * 获取当前时间字符串 (yyyy-MM-dd HH:mm:ss)
     *
     * @return 当前时间字符串
     */
    public static String now() {
        return LocalDateTime.now().format(DATETIME_FORMATTER);
    }

    /**
     * 获取当前日期字符串 (yyyy-MM-dd)
     *
     * @return 当前日期字符串
     */
    public static String today() {
        return LocalDate.now().format(DATE_FORMATTER);
    }

    /**
     * 获取当前时间字符串 (HH:mm:ss)
     *
     * @return 当前时间字符串
     */
    public static String currentTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }

    /**
     * 格式化LocalDateTime为指定格式字符串
     *
     * @param dateTime   LocalDateTime对象
     * @param formatter  日期时间格式化器
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, DateTimeFormatter formatter) {
        if (dateTime == null || formatter == null) {
            return null;
        }
        return dateTime.format(formatter);
    }

    /**
     * 格式化LocalDateTime为yyyy-MM-dd HH:mm:ss格式
     *
     * @param dateTime LocalDateTime对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DATETIME_FORMATTER);
    }

    /**
     * 格式化LocalDate为yyyy-MM-dd格式
     *
     * @param date LocalDate对象
     * @return 格式化后的字符串
     */
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(DATE_FORMATTER);
    }

    /**
     * 解析字符串为LocalDateTime
     *
     * @param dateTimeStr 日期时间字符串
     * @param formatter   日期时间格式化器
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, DateTimeFormatter formatter) {
        if (dateTimeStr == null || formatter == null || dateTimeStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 解析yyyy-MM-dd HH:mm:ss格式字符串为LocalDateTime
     *
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeStr) {
        return parseLocalDateTime(dateTimeStr, DATETIME_FORMATTER);
    }

    /**
     * 解析yyyy-MM-dd格式字符串为LocalDate
     *
     * @param dateStr 日期字符串
     * @return LocalDate对象
     */
    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    /**
     * Date转换为LocalDateTime
     *
     * @param date Date对象
     * @return LocalDateTime对象
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    /**
     * LocalDateTime转换为Date
     *
     * @param localDateTime LocalDateTime对象
     * @return Date对象
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zoneId);
        Instant instant = zonedDateTime.toInstant();
        return Date.from(instant);
    }

    /**
     * 计算两个LocalDateTime之间的时间差（秒）
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差（秒）
     */
    public static long betweenSeconds(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.SECONDS.between(start, end);
    }

    /**
     * 计算两个LocalDateTime之间的时间差（分钟）
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差（分钟）
     */
    public static long betweenMinutes(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(start, end);
    }

    /**
     * 计算两个LocalDateTime之间的时间差（小时）
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差（小时）
     */
    public static long betweenHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.HOURS.between(start, end);
    }

    /**
     * 计算两个LocalDateTime之间的时间差（天）
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 时间差（天）
     */
    public static long betweenDays(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 给指定时间添加秒数
     *
     * @param dateTime LocalDateTime对象
     * @param seconds  要添加的秒数
     * @return 添加后的LocalDateTime对象
     */
    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusSeconds(seconds);
    }

    /**
     * 给指定时间添加分钟数
     *
     * @param dateTime LocalDateTime对象
     * @param minutes  要添加的分钟数
     * @return 添加后的LocalDateTime对象
     */
    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusMinutes(minutes);
    }

    /**
     * 给指定时间添加小时数
     *
     * @param dateTime LocalDateTime对象
     * @param hours    要添加的小时数
     * @return 添加后的LocalDateTime对象
     */
    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusHours(hours);
    }

    /**
     * 给指定时间添加天数
     *
     * @param dateTime LocalDateTime对象
     * @param days     要添加的天数
     * @return 添加后的LocalDateTime对象
     */
    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.plusDays(days);
    }

    /**
     * 给指定时间减去秒数
     *
     * @param dateTime LocalDateTime对象
     * @param seconds  要减去的秒数
     * @return 减去后的LocalDateTime对象
     */
    public static LocalDateTime minusSeconds(LocalDateTime dateTime, long seconds) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.minusSeconds(seconds);
    }

    /**
     * 给指定时间减去分钟数
     *
     * @param dateTime LocalDateTime对象
     * @param minutes  要减去的分钟数
     * @return 减去后的LocalDateTime对象
     */
    public static LocalDateTime minusMinutes(LocalDateTime dateTime, long minutes) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.minusMinutes(minutes);
    }

    /**
     * 给指定时间减去小时数
     *
     * @param dateTime LocalDateTime对象
     * @param hours    要减去的小时数
     * @return 减去后的LocalDateTime对象
     */
    public static LocalDateTime minusHours(LocalDateTime dateTime, long hours) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.minusHours(hours);
    }

    /**
     * 给指定时间减去天数
     *
     * @param dateTime LocalDateTime对象
     * @param days     要减去的天数
     * @return 减去后的LocalDateTime对象
     */
    public static LocalDateTime minusDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.minusDays(days);
    }

    /**
     * 判断时间是否在指定范围内
     *
     * @param dateTime  要判断的时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 是否在范围内
     */
    public static boolean isBetween(LocalDateTime dateTime, LocalDateTime startTime, LocalDateTime endTime) {
        if (dateTime == null || startTime == null || endTime == null) {
            return false;
        }
        return dateTime.isAfter(startTime) && dateTime.isBefore(endTime);
    }

    /**
     * 判断时间是否在指定范围或等于边界
     *
     * @param dateTime  要判断的时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 是否在范围内或等于边界
     */
    public static boolean isBetweenOrEqual(LocalDateTime dateTime, LocalDateTime startTime, LocalDateTime endTime) {
        if (dateTime == null || startTime == null || endTime == null) {
            return false;
        }
        return !dateTime.isBefore(startTime) && !dateTime.isAfter(endTime);
    }

    /**
     * 获取当天的开始时间 (00:00:00)
     *
     * @return 当天开始时间
     */
    public static LocalDateTime getDayStart() {
        return LocalDateTime.now().with(LocalTime.MIN);
    }

    /**
     * 获取当天的结束时间 (23:59:59)
     *
     * @return 当天结束时间
     */
    public static LocalDateTime getDayEnd() {
        return LocalDateTime.now().with(LocalTime.MAX);
    }

    /**
     * 获取本周的开始时间 (周一 00:00:00)
     *
     * @return 本周开始时间
     */
    public static LocalDateTime getWeekStart() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int daysToMonday = dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue();
        return now.minusDays(daysToMonday).with(LocalTime.MIN);
    }

    /**
     * 获取本周的结束时间 (周日 23:59:59)
     *
     * @return 本周结束时间
     */
    public static LocalDateTime getWeekEnd() {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        int daysToSunday = DayOfWeek.SUNDAY.getValue() - dayOfWeek.getValue();
        return now.plusDays(daysToSunday).with(LocalTime.MAX);
    }

    /**
     * 获取本月的开始时间 (1号 00:00:00)
     *
     * @return 本月开始时间
     */
    public static LocalDateTime getMonthStart() {
        return LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN);
    }

    /**
     * 获取本月的结束时间 (月末最后一天 23:59:59)
     *
     * @return 本月结束时间
     */
    public static LocalDateTime getMonthEnd() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.now().withDayOfMonth(now.getMonth().length(now.isLeapYear())).with(LocalTime.MAX);
    }
}
