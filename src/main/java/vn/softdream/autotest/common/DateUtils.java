package vn.softdream.autotest.common;

import vn.softdream.autotest.common.datetime.InstantBuilder;
import vn.softdream.autotest.common.datetime.LocalDateTimeBuilder;

import java.time.Instant;
import java.time.LocalDateTime;

@SuppressWarnings("unused")
public class DateUtils {

    public static InstantBuilder now() {
        return new InstantBuilder(Instant.now());
    }

    public static InstantBuilder now(String zoneID) {
        return new InstantBuilder(Instant.now(), zoneID);
    }

    public static InstantBuilder ofInstant(Instant instant) {
        return new InstantBuilder(instant);
    }

    public static InstantBuilder ofInstant(LocalDateTime localDateTime) {
        return new InstantBuilder(localDateTime);
    }

    public static InstantBuilder ofInstant(LocalDateTime localDateTime, String zoneID) {
        return new InstantBuilder(localDateTime, zoneID);
    }

    public static InstantBuilder ofInstant(String dateTime, String formatter) {
        return new InstantBuilder(dateTime, formatter);
    }

    public static InstantBuilder ofInstant(String dateTime, String formatter, String zoneID) {
        return new InstantBuilder(dateTime, formatter, zoneID);
    }

    public static LocalDateTimeBuilder nowLocal() {
        return new LocalDateTimeBuilder(LocalDateTime.now());
    }

    public static LocalDateTimeBuilder nowLocal(String zoneID) {
        return new LocalDateTimeBuilder(LocalDateTime.now(), zoneID);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(Instant instant) {
        return new LocalDateTimeBuilder(instant);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(Instant instant, String zoneID) {
        return new LocalDateTimeBuilder(instant, zoneID);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(LocalDateTime localDateTime) {
        return new LocalDateTimeBuilder(localDateTime);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(LocalDateTime localDateTime, String zoneID) {
        return new LocalDateTimeBuilder(localDateTime, zoneID);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(String dateTime, String formatter) {
        return new LocalDateTimeBuilder(dateTime, formatter);
    }

    public static LocalDateTimeBuilder ofLocalDateTime(String dateTime, String formatter, String zoneID) {
        return new LocalDateTimeBuilder(dateTime, formatter, zoneID);
    }
}
