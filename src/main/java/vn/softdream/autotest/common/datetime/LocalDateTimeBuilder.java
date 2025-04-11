package vn.softdream.autotest.common.datetime;

import vn.softdream.autotest.constants.DatetimeConstants;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Locale;

@SuppressWarnings("unused")
public class LocalDateTimeBuilder {

    private final ZoneId currentZoneID;
    private LocalDateTime instanceLocalDateTime;

    public LocalDateTimeBuilder(LocalDateTime localDateTime) {
        this(localDateTime, DatetimeConstants.ZoneID.DEFAULT);
    }

    public LocalDateTimeBuilder(LocalDateTime localDateTime, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceLocalDateTime = localDateTime.atZone(this.currentZoneID).toLocalDateTime();
    }

    public LocalDateTimeBuilder(Instant instant) {
        this(instant, DatetimeConstants.ZoneID.DEFAULT);
    }

    public LocalDateTimeBuilder(Instant instant, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceLocalDateTime = LocalDateTime.ofInstant(instant, this.currentZoneID);
    }

    public LocalDateTimeBuilder(String localDateTime, String formatter) {
        this(localDateTime, formatter, DatetimeConstants.ZoneID.DEFAULT);
    }

    public LocalDateTimeBuilder(String localDateTime, String formatter, String zoneID) {
        this.currentZoneID = ZoneId.of(zoneID);
        this.instanceLocalDateTime = LocalDateTime.parse(localDateTime, DateTimeFormatter.ofPattern(formatter))
                .atZone(this.currentZoneID)
                .toLocalDateTime();
    }

    public LocalDateTimeBuilder plusYears(int yearsToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusYears(yearsToAdd);
        return this;
    }

    public LocalDateTimeBuilder plusMonths(int monthsToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusMonths(monthsToAdd);
        return this;
    }

    public LocalDateTimeBuilder plusDays(int daysToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusDays(daysToAdd);
        return this;
    }

    public LocalDateTimeBuilder plusHours(int hoursToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusHours(hoursToAdd);
        return this;
    }

    public LocalDateTimeBuilder plusMinutes(int minutesToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusMinutes(minutesToAdd);
        return this;
    }

    public LocalDateTimeBuilder plusSeconds(int secondsToAdd) {
        this.instanceLocalDateTime = this.instanceLocalDateTime.plusSeconds(secondsToAdd);
        return this;
    }

    private ZonedDateTime getZonedDateTime() {
        return this.instanceLocalDateTime.atZone(this.currentZoneID);
    }

    private ZonedDateTime getZonedDateTime(String zoneID) {
        return this.instanceLocalDateTime.atZone(ZoneId.of(zoneID));
    }

    public String getMonthStr() {
        return getZonedDateTime().getMonth().name();
    }

    public String getDayOfWeekStr() {
        return getZonedDateTime().getDayOfWeek().name();
    }

    public int getYear() {
        return getZonedDateTime().getYear();
    }

    public int getDayOfYear() {
        return getZonedDateTime().getDayOfYear();
    }

    public int getDaysOfYear() {
        return Year.of(getYear()).length();
    }

    public int getMonth() {
        return getZonedDateTime().getMonthValue();
    }

    public int getDayOfMonth() {
        return getZonedDateTime().getDayOfMonth();
    }

    public int getDaysOfMonth() {
        return getZonedDateTime().getMonth().length(Year.isLeap(getYear()));
    }

    public int getWeekOfYear() {
        return getZonedDateTime().get(WeekFields.of(Locale.getDefault()).weekOfYear());
    }

    public int getWeeksOfYear() {
        return getDaysOfYear() / 7;
    }

    public int getWeekOfMonth() {
        return getZonedDateTime().get(WeekFields.of(Locale.getDefault()).weekOfMonth());
    }

    public int getDayOfWeek() {
        return getZonedDateTime().getDayOfWeek().getValue();
    }

    public int getHour() {
        return getZonedDateTime().getHour();
    }

    public int getMinute() {
        return getZonedDateTime().getMinute();
    }

    public int getSecond() {
        return getZonedDateTime().getSecond();
    }

    public int getMilliSecond() {
        return getZonedDateTime().getNano() / 1_000_000;
    }

    public boolean isBefore(LocalDateTime localDateTimeToCompare) {
        return this.instanceLocalDateTime.isBefore(localDateTimeToCompare);
    }

    public boolean isAfter(LocalDateTime localDateTimeToCompare) {
        return this.instanceLocalDateTime.isAfter(localDateTimeToCompare);
    }

    public Instant toInstant() {
        return this.instanceLocalDateTime.atZone(this.currentZoneID).toInstant();
    }

    public Instant toInstant(String zoneID) {
        return this.instanceLocalDateTime.atZone(ZoneId.of(zoneID)).toInstant();
    }

    @Override
    public String toString() {
        return this.toString(DatetimeConstants.Formatter.DEFAULT);
    }

    public String toString(String formatter) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter).withZone(this.currentZoneID);
        return dateTimeFormatter.format(this.instanceLocalDateTime);
    }

    public String toStringWithZoneID(String zoneID) {
        return toStringWithZoneID(zoneID, DatetimeConstants.Formatter.DEFAULT);
    }

    public String toStringWithZoneID(String zoneID, String formatter) {
        ZoneId zone = ZoneId.of(zoneID);
        return DateTimeFormatter.ofPattern(formatter).format(this.instanceLocalDateTime.atZone(zone));
    }

    public LocalDateTime getInstance() {
        return this.instanceLocalDateTime;
    }
}
